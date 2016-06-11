package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.repository.TicketCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class TicketCodeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private TicketCodeRepository repository;


    public TicketCode findByTicketTypeIdAndTicketNo(String ticketTypeId, String ticketNo) {
        return repository.findByTicketTypeIdAndTicketNoAndDelFlag(ticketTypeId, ticketNo, 0);
    }


    public TicketCode findFirstByTicketTypeId(String ticketTypeId) {
        return repository.findFirstByTicketTypeIdAndDelFlag(ticketTypeId, 0);
    }


    public boolean add(TicketCode entity) {
        TicketCode result = repository.save(entity);
        return result != null ? true : false;
    }


    /**
     * 获从未用的一张未用的券码
     *
     * @param ticketTypeId
     * @return
     */
    public TicketCode getAnUsedTicketCode(String ticketTypeId) {
        try {
            String sql = "Select id, ticket_type_Id, ticket_pic, ticket_no, ticket_type,ticket_psw, is_use from TICKET_CODE " +
                    "where ticket_type_Id=? And is_use=0  limit 0,1";
            TicketCode result = jdbcTemplate.queryForObject(sql, ticketCodeMapper, ticketTypeId);
            return result;
        } catch (EmptyResultDataAccessException exp) {
            return null;
        }
    }


    private RowMapper<TicketCode> ticketCodeMapper = new RowMapper<TicketCode>() {
        @Override
        public TicketCode mapRow(ResultSet resultSet, int i) throws SQLException {
            TicketCode dto = new TicketCode();
            dto.setId(resultSet.getString("id"));
            dto.setTicketTypeId(resultSet.getString("ticket_type_Id"));
            dto.setTicketPic(resultSet.getString("ticket_pic"));
            dto.setTicketNo(resultSet.getString("ticket_no"));
            dto.setTicketType(resultSet.getInt("ticket_type"));
            dto.setTicketPsw(resultSet.getString("ticket_psw"));
            dto.setUse(resultSet.getBoolean("is_use"));
            return dto;
        }
    };


    /**
     * 从某个券所属的券码池中弹出一个券码
     *
     * @param ticketTypeId
     */
    public TicketCode popTicketCode(String ticketTypeId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            TicketCode ticketCode = this.getAnUsedTicketCode(ticketTypeId);
            String updateSql = "update TICKET_CODE set is_use = is_use + 1 where id=?";
            jdbcTemplate.update(updateSql, ticketCode.getId());
            String selectSql = "select is_use from TICKET_CODE where id =?";
            Integer is_use = jdbcTemplate.queryForObject(selectSql, Integer.class, ticketCode.getId());
            if (is_use > 1) {
                throw new RuntimeException();  // 若>1，则视为异常
            } else {
                // 扣减券剩余量
                String sql1 = "update TICKET_TYPE set remain_num = remain_num -1 where id= ?";
                jdbcTemplate.update(sql1, ticketTypeId);
                // commit
                transactionManager.commit(status);
                return ticketCode;
            }
        } catch (RuntimeException ex) {
            // rollback
            transactionManager.rollback(status);
            return null;
        }
    }


}
