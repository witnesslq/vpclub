package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface ActivityInfoRepository extends CrudRepository<ActivityInfo, String> {

    ActivityInfo findByIdAndDelFlag(String id, int delFlag);

    ActivityInfo findById(String id);

}

