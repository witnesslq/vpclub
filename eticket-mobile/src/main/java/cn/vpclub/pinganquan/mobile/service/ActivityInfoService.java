package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.repository.ActivityInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class ActivityInfoService {


    @Autowired
    private ActivityInfoRepository activityInfoRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private TicketCodeService ticketCodeService;

    @Autowired
    private TicketTypeService ticketTypeService;


    @Autowired
    private ActivityTicketService activityTicketService;


    @Cacheable(value = "ActivityInfoService", keyGenerator = "wiselyKeyGenerator")
    public ActivityInfo findById(String activityId) {
        return activityInfoRepository.findByIdAndDelFlag(activityId, 0);
    }


    /**
     * 抽券，返回券码对象
     * 此为抽奖的核心逻辑
     *
     * @param activityId
     * @return
     */

    public TicketCode drawTicketCode(String activityId) {
        // 从数据库中获取奖券类型Id列表
        List<String> ticketTypeIds = activityTicketService.findTicketTypeIdsByActivityId(activityId);
        // 随机选取一个奖券类型id
        Random random = new Random();
        int index = random.nextInt(ticketTypeIds.size());
        String ticketTypeId = ticketTypeIds.get(index); // 抽取的奖券id
        // 若取得的是空牌，则直接返回
        if (ticketTypeId.equals("0")) {
            return null;
        } else {
            // 从奖券队列中pop出一个奖券
            String key = "activityTicketTypeQueue:" + activityId + ":" + ticketTypeId;
            ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
            String ticketNo = listOperations.rightPop(key);
            if (ticketNo == null) {
                return null;
            } else {
                // 扣减券，包括扣减券剩余量和将券码标识为已用
                ticketTypeService.subtractTicket(ticketTypeId, ticketNo);
                TicketCode ticketCode = ticketCodeService.findByTicketTypeIdAndTicketNo(ticketTypeId,
                        ticketNo);
                if (ticketCode == null) {
                    throw new GenericException("严重错误：抽奖时找不到券码！");
                } else {
                    return ticketCode;
                }
            }
        }
    }


    /**
     * 抽券，返回券码对象。方案B
     * 此为抽奖的核心逻辑
     *
     * @param activityId
     * @return
     */
    public TicketCode drawTicketCode2(String activityId) {
        // 从数据库中获取奖券类型Id列表
        List<String> ticketTypeIds = activityTicketService.findTicketTypeIdsByActivityId(activityId);
        // 随机选取一个奖券类型id
        Random random = new Random();
        int index = random.nextInt(ticketTypeIds.size());
        String ticketTypeId = ticketTypeIds.get(index); // 抽取的奖券id
        // 若取得的是空牌，则直接返回
        if (ticketTypeId.equals("0")) {
            return null;
        } else {
            return ticketCodeService.popTicketCode(ticketTypeId);
        }
    }

    /**
     * 通过活动id 获取活动详情
     * @param activityId
     * @return
     */
    public ActivityInfo findActivityInfoById(String activityId)
    {
        return activityInfoRepository.findById(activityId);
    }

}