package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.domain.ActivityParticipationInfo;
import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.domain.TicketContent;
import cn.vpclub.pinganquan.mobile.domain.TicketType;
import cn.vpclub.pinganquan.mobile.dto.DrawResultDto;
import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.repository.ActivityParticipationInfoRepository;
import cn.vpclub.pinganquan.mobile.repository.TicketCodeRepository;
import cn.vpclub.pinganquan.mobile.repository.TicketContentRepository;
import cn.vpclub.pinganquan.mobile.repository.TicketTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class ActivityParticipationInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityParticipationInfoService.class);


    @Autowired
    private ActivityParticipationInfoRepository activityParticipationInfoRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TicketContentRepository ticketContentRepository;

    @Autowired
    private TicketCodeRepository ticketCodeRepository;


    public Boolean add(ActivityParticipationInfo entity) {
        ActivityParticipationInfo result = activityParticipationInfoRepository.save(entity);
        return result == null ? false : true;
    }


    /**
     * 是否已经参与过此活动
     *
     * @param userName
     * @param activityId
     * @return
     */
    public Boolean isParticipated(String userName, String activityId) {
        ActivityParticipationInfo result = activityParticipationInfoRepository.findByUserNameAndActivityId(userName, activityId);
        return result == null ? false : true;
    }


    /**
     * 根据活动id和用户名来查找抽奖结果
     *
     * @param activityId 活动id
     * @param userName   用户名
     * @return
     */
    public DrawResultDto findDrawResult(String activityId, String userName) {
        logger.info("userName-->{}", userName);
        if(userName.equals("")){
            return null;
        }
        ActivityParticipationInfo entity = activityParticipationInfoRepository.findByUserNameAndActivityId(userName, activityId);
        if (entity == null) {
            return null;
        } else {
            // 若TicketTypeId为0，说明没有中奖
            logger.info(userName + "若TicketTypeId为0，说明没有中奖userName:--." + entity.getTicketTypeId());
            if (entity.getTicketTypeId().equals("0")) {
                return new DrawResultDto(false, entity.getOfficialCo());
            } else {
                DrawResultDto drawResultDto = getInitialDrawResult(entity.getTicketTypeId(), entity.getTicketNo());
                drawResultDto.setStatus(true);
                drawResultDto.setCopyWriting(entity.getOfficialCo());
                return drawResultDto;
            }
        }
    }


    /**
     * 根据ticketTypeId和ticketNo来构建一个DrawResultDto对象
     *
     * @param ticketTypeId
     * @param ticketNo
     * @return
     */
    public DrawResultDto getInitialDrawResult(String ticketTypeId, String ticketNo) {
        DrawResultDto drawResultDto = new DrawResultDto();
        // 添加券信息
        TicketType ticketType = ticketTypeRepository.findByIdAndDelFlag(ticketTypeId, 0);
        if (ticketType != null) {
            drawResultDto.setTicketId(ticketTypeId);
            drawResultDto.setTickeImageUrl(ticketType.getTickeImageUrl());
            drawResultDto.setTicketName(ticketType.getTicketName());
        }
        // 添加券详情信息
        List<TicketContent> ticketContents = ticketContentRepository.findByTicketTypeId(ticketTypeId);
        for (TicketContent ticketContent : ticketContents) {
            if (ticketContent.getTicketContentType() == 2) { // 2 为微信环境的用券说明
                drawResultDto.setUsageGuide1(ticketContent.getTicketContent());
            } else if (ticketContent.getTicketContentType() == 3) { // 3 为普通浏览器环境的用券说明
                drawResultDto.setUsageGuide2(ticketContent.getTicketContent());
            }
        }
        // 添加券码信息
        TicketCode ticketCode = ticketCodeRepository.findByTicketTypeIdAndTicketNoAndDelFlag(ticketTypeId, ticketNo, 0);
        if (ticketCode != null) {
            drawResultDto.setTicketCodeType(ticketCode.getTicketType());
            drawResultDto.setTicketNo(ticketCode.getTicketNo());
            drawResultDto.setTicketNoPic(ticketCode.getTicketPic());
        }
        return drawResultDto;
    }


    /**
     * 添加参与记录
     *
     * @param activityId
     * @param user
     * @param copyWriting
     * @param ticketCode
     */

    public void addParticipationInfo(String activityId, UserDto user, String copyWriting, TicketCode ticketCode) {
        ActivityParticipationInfo participationInfo = new ActivityParticipationInfo();
        participationInfo.setActivityId(activityId);
        participationInfo.setUserName(user.getUserName());
        participationInfo.setUserType(user.getUserType());
        participationInfo.setFromUserName(user.getFromUserName());
        participationInfo.setFromUserType(user.getFromUserType());
        participationInfo.setOfficialCo(copyWriting); // 中奖文案
        if (ticketCode == null) {
            participationInfo.setTicketTypeId("0"); // 未中奖为0
            participationInfo.setTicketNo("");
            participationInfo.setTicketNoPic("");
            participationInfo.setTicketPsw("");
        } else {
            participationInfo.setTicketTypeId(ticketCode.getTicketTypeId());
            participationInfo.setTicketNo(ticketCode.getTicketNo());
            participationInfo.setTicketNoPic(ticketCode.getTicketPic());
            participationInfo.setTicketPsw(ticketCode.getTicketPsw());
        }
        boolean flag = this.add(participationInfo);
        if (flag == false) {
            logger.error("插入参与记录失败！");
        }
    }

}
