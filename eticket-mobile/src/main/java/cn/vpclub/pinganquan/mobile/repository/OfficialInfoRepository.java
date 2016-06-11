package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.OfficialInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface OfficialInfoRepository extends CrudRepository<OfficialInfo, String> {

    /**
     * 活动id和文案类型查找
     *
     * @return
     */
    List<OfficialInfo> findByActivityIdAndOfficialTypeAndDelFlag(String activityId, int officialType, int delFlag);


}

