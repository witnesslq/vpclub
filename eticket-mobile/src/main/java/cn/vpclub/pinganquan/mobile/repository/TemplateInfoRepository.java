package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.TemplateInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface TemplateInfoRepository extends CrudRepository<TemplateInfo, String> {

    TemplateInfo findByIdAndDelFlag(String id, int delFlag);

}

