package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.domain.TicketType;
import cn.vpclub.pinganquan.mobile.dto.PokerDetailDto;
import cn.vpclub.pinganquan.mobile.dto.PokerMoreDetailDto;
import cn.vpclub.pinganquan.mobile.dto.WinnigDetailDto;
import cn.vpclub.pinganquan.mobile.repository.TicketTypeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
@Transactional
public class TicketTypeService {

    private static final Logger logger = LoggerFactory.getLogger(TicketTypeService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TicketTypeRepository repository;


    /**
     * 根据id来查找券
     *
     * @param ticketTypeId
     * @return
     */
    public TicketType findById(String ticketTypeId) {
        return repository.findByIdAndDelFlag(ticketTypeId, 0);
    }


    /**
     * 根据活动id获取此活动的所包含的扑克的详情
     *
     * @param activityId
     * @return
     */
    public List<PokerDetailDto> getPokerDetails(String activityId) {
        String sql = "select a.id, a.ticket_name, a.ticke_image_url, a.ticket_act_price, a.remain_num, a.ticket_brief, " +
                "(a.stock_num - a.remain_num + a.virtual_num) as virtual_drawed_num " + // 虚拟用券数量=总量-剩余量 + 领券基数
                "from TICKET_TYPE a left join ACTIVITY_TICKET b  on a.id = b.ticket_type_id " +
                "where b.activity_id =? and b.del_flag=0 and a.del_flag=0";
        List<PokerDetailDto> result = jdbcTemplate.query(sql, ticketTypeDetailDtoRowMapper, activityId);
        return result;
    }


    /**
     * 获取poker详情
     *
     * @param ticketTypeId
     * @return
     */
    public PokerDetailDto getPokerDetail(String ticketTypeId) {
        String sql = "select id, ticket_name, ticke_image_url, ticket_act_price, " +
                "(stock_num - remain_num + virtual_num) as virtual_drawed_num, " + // 虚拟用券数量=总量-剩余量 + 领券基数
                "remain_num, ticket_brief from TICKET_TYPE " +
                "where id=? and TICKET_TYPE.del_flag=0";
        try {
            return jdbcTemplate.queryForObject(sql, ticketTypeDetailDtoRowMapper, ticketTypeId);
        } catch (EmptyResultDataAccessException exp) {
            return null;
        }
    }


    private RowMapper<PokerDetailDto> ticketTypeDetailDtoRowMapper = new RowMapper<PokerDetailDto>() {
        @Override
        public PokerDetailDto mapRow(ResultSet resultSet, int i) throws SQLException {
            PokerDetailDto dto = new PokerDetailDto();
            dto.setTicketId(resultSet.getString("id"));
            dto.setTicketName(resultSet.getString("ticket_name"));
            dto.setTickeImageUrl(resultSet.getString("ticke_image_url"));
            dto.setTicketActPrice(resultSet.getString("ticket_act_price"));
            dto.setRemainNum(resultSet.getInt("remain_num"));
            dto.setVirtualDrawedNum(resultSet.getInt("virtual_drawed_num"));
            dto.setTicketBrief(resultSet.getString("ticket_brief"));
            return dto;
        }
    };


    /**
     * 获取poker更多详情
     *
     * @param ticketTypeId
     * @return
     */
    @Cacheable(value = "TicketTypeService", keyGenerator = "wiselyKeyGenerator")
    public PokerMoreDetailDto getPokerMoreDetail(String ticketTypeId) {
        try {
            // 从券表中获表券信息
            String sql = "select id, ticket_name, ticket_big_pic from TICKET_TYPE " +
                    "where id = ? and del_flag=0";
            PokerMoreDetailDto result = jdbcTemplate.queryForObject(sql, pokerMoreDetailDtoMapper, ticketTypeId);
            // 从券详情表中获取详情
            String sql2 = "select ticket_content from TICKET_CONTENT " +
                    "where ticket_content_type=1 and ticket_type_Id = ?";
            try {
                String ticketContent = jdbcTemplate.queryForObject(sql2, String.class, ticketTypeId);
                result.setTicketSpecifics(ticketContent);
                return result;
            } catch (Exception exp) {
                throw new GenericException("没有找到券详情,ticketTypeId为：" + ticketTypeId);
            }
        } catch (EmptyResultDataAccessException exp) {
            return null;
        }

    }

    private RowMapper<PokerMoreDetailDto> pokerMoreDetailDtoMapper = new RowMapper<PokerMoreDetailDto>() {
        @Override
        public PokerMoreDetailDto mapRow(ResultSet resultSet, int i) throws SQLException {
            PokerMoreDetailDto dto = new PokerMoreDetailDto();
            dto.setTicketId(resultSet.getString("id"));
            dto.setTicketName(resultSet.getString("ticket_name"));
            dto.setTicketBigPic(resultSet.getString("ticket_big_pic"));
            return dto;
        }
    };


    /**
     * 获取中奖详情
     *
     * @param ticketTypeId
     * @return
     */
    public WinnigDetailDto getWinnigDetailDto(String ticketTypeId, String ticketNo) {
        try {
            // 从券表中获表券信息
            String sql = "select id, ticket_name, ticket_big_pic, ticke_code_url, ticket_brief from TICKET_TYPE " +
                    "where id = ? and del_flag=0";
            WinnigDetailDto result = jdbcTemplate.queryForObject(sql, winnigDetailDtoMapper, ticketTypeId);
            // 从券详情表中获取券详情
            String sql2 = "select ticket_content, ticket_content_type from TICKET_CONTENT " +
                    "where ticket_type_Id = ?";
            SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql2, ticketTypeId);
            while (rowSet2.next()) {
                if (rowSet2.getInt("ticket_content_type") == 1) {
                    result.setTicketSpecifics(rowSet2.getString("ticket_content"));
                } else if (rowSet2.getInt("ticket_content_type") == 2) {  // 2 为微信环境的用券说明
                    result.setUsageGuide1(rowSet2.getString("ticket_content"));
                } else if (rowSet2.getInt("ticket_content_type") == 3) {  // 3 为普通浏览器环境的用券说明
                    result.setUsageGuide2(rowSet2.getString("ticket_content"));
                }
            }
            // 从券码表中获取券码信息
            String sql3 = "select ticket_pic, ticket_no, ticket_type from TICKET_CODE " +
                    "where ticket_type_Id = ? and ticket_no = ? and del_flag=0";
            SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql3, ticketTypeId, ticketNo);
            while (rowSet3.next()) { //这里只会有一条数据
                result.setTicketCodeType(rowSet3.getInt("ticket_type"));
                result.setTicketNo(rowSet3.getString("ticket_no"));
                result.setTicketNoPic(rowSet3.getString("ticket_pic"));
            }
            return result;
        } catch (Exception exp) {
            return null;
        }
    }


    private RowMapper<WinnigDetailDto> winnigDetailDtoMapper = new RowMapper<WinnigDetailDto>() {
        @Override
        public WinnigDetailDto mapRow(ResultSet resultSet, int i) throws SQLException {
            WinnigDetailDto dto = new WinnigDetailDto();
            dto.setTicketId(resultSet.getString("id"));
            dto.setTicketName(resultSet.getString("ticket_name"));
            dto.setTicketBigPic(resultSet.getString("ticket_big_pic"));
            dto.setTickeCodeUrl(resultSet.getString("ticke_code_url"));
            dto.setTicketBrief(resultSet.getString("ticket_brief"));
            return dto;
        }
    };


    /**
     * 扣减券，包括扣减券剩余量和将券码标识为已用
     * 注意此类的注解，@Transactional，它表示开启事务
     *
     * @param ticketTypeId
     * @param ticketNo
     */
    public void subtractTicket(String ticketTypeId, String ticketNo) {
        // 扣减券剩余量
        String sql1 = "update TICKET_TYPE set remain_num = remain_num -1 where id= ?";
        jdbcTemplate.update(sql1, ticketTypeId);
        // 将券码标识为已用
        String sql2 = "update TICKET_CODE set is_use = 1 where ticket_type_Id = ? and ticket_no = ?";
        jdbcTemplate.update(sql2, ticketTypeId, ticketNo);
    }

    /**
     * 上报行为
     * @param url
     * @return
     */
    public JsonNode addEventLog(String url)
    {
        logger.info("上报行为,url:--->" + url);
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


