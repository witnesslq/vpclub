package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.domain.ActivityTicket;
import cn.vpclub.pinganquan.mobile.repository.ActivityTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class ActivityTicketService {

    @Autowired
    private ActivityTicketRepository activityTicketRepository;


    /**
     * 根据活动id获取它所包含的券类型的id集合
     *
     * @param activityId
     * @return
     */
    @Cacheable(value = "ActivityTicketService", keyGenerator = "wiselyKeyGenerator")
    public List<String> findTicketTypeIdsByActivityId(String activityId) {
        List<String> ticketTypeIds = new ArrayList<>();

        List<ActivityTicket> activityTickets = activityTicketRepository.findByActivityIdAndDelFlag(activityId, 0);
        for (ActivityTicket ticketType : activityTickets) {
            ticketTypeIds.add(ticketType.getTicketTypeId());
        }
        return ticketTypeIds;
    }


}
