package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.ActivityParticipationInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface ActivityParticipationInfoRepository extends CrudRepository<ActivityParticipationInfo, String> {

    /**
     * 根据用户name和活动id来查找
     *
     * @return
     */
    ActivityParticipationInfo findByUserNameAndActivityId(String userName, String activityId);

}

