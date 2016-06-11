package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.domain.TemplateInfo;
import cn.vpclub.pinganquan.mobile.repository.TemplateInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class TemplateInfoService {

    @Autowired
    private TemplateInfoRepository templateInfoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public TemplateInfo findById(String templateId) {
        return templateInfoRepository.findByIdAndDelFlag(templateId, 0);
    }


    /**
     * 根据activityId来查找模板信息
     *
     * @param activityId
     * @return
     */
    @Cacheable(value = "TemplateInfoService", keyGenerator = "wiselyKeyGenerator")
    public TemplateInfo findByActivityId(String activityId) {
        String sql = "select id, template_name, template_url, a_face_thumb, b_face_thumb " +
                "from TEMPLATE_INFO where del_flag=0 and  id = " +
                "(select template_id from ACTIVITY_INFO where id =? and del_flag=0)";
        try {
            TemplateInfo result = jdbcTemplate.queryForObject(sql, TemplateInfoRowMapper, activityId);
            return result;
        } catch (EmptyResultDataAccessException exp) {
            return null;
        }
    }


    private RowMapper<TemplateInfo> TemplateInfoRowMapper = new RowMapper<TemplateInfo>() {
        @Override
        public TemplateInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            TemplateInfo templateInfo = new TemplateInfo();

            templateInfo.setId(resultSet.getString("id"));
            templateInfo.setTemplateName(resultSet.getString("template_name"));
            templateInfo.setTemplateUrl(resultSet.getString("template_url"));
            templateInfo.setaFaceThumb(resultSet.getString("a_face_thumb"));
            templateInfo.setbFaceThumb(resultSet.getString("b_face_thumb"));
            return templateInfo;
        }
    };


}