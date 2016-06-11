package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.ActivityTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface ActivityTicketRepository extends CrudRepository<ActivityTicket, String> {

    List<ActivityTicket> findByActivityIdAndDelFlag(String activityId, int delFlag);

}

