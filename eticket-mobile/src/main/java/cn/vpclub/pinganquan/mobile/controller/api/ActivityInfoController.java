package cn.vpclub.pinganquan.mobile.controller.api;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.common.StringUtils;
import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.dto.*;
import cn.vpclub.pinganquan.mobile.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/25.
 */
@RestController
public class ActivityInfoController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityInfoController.class);

    @Value("${customer.client.url-base2}")
    private String urlBase2;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private ActivityParticipationInfoService activityParticipationInfoService;

    @Autowired
    private TicketCodeService ticketCodeService;

    @Autowired
    private TicketTypeService ticketTypeService;


    @Autowired
    private ActivityTicketService activityTicketService;

    @Autowired
    private OfficialInfoService officialInfoService;


    /**
     * 获取活动信息
     *
     * @param activityId
     * @param preview    true为预览， false为非预览
     * @param token
     * @return
     */
    @RequestMapping("/api/activity")
    public RpcResult getActivity(@RequestParam("activityId") String activityId,
                                 @RequestParam(value = "preview", defaultValue = "false") boolean preview,
//                                 @CookieValue(value = "token", required = false) String token,
                                 @RequestParam(value = "token", required = false) String token,
                                 Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {


        logger.info("/api/activity:--->token--------" + token);


        // 获取活动信息
        ActivityInfo activityInfo = activityInfoService.findById(activityId);
        if (activityInfo == null) {
            return new RpcResult(1001, "未找到活动", null);
        }

        ActivityDto dto = new ActivityDto();
        dto.setId(activityInfo.getId());
        dto.setTemplateId(activityInfo.getTemplateId());
        dto.setActivtyName(activityInfo.getActivityName());
        dto.setLogoUrl(activityInfo.getLogoUrl());
        dto.setSloganUrl(activityInfo.getSloganUrl());
        dto.setBackgroundUrl(activityInfo.getbShareBtn());
        dto.setPokerBackUrl(activityInfo.getPokerBackUrl());
        dto.setEnterpriseUrl1(activityInfo.getEnterpriseUrl1());
        dto.setEnterpriseUrl2(activityInfo.getEnterpriseUrl2());
        dto.setEnterpriseUrl3(activityInfo.getEnterpriseUrl3());
        dto.setEnterpriseUrl4(activityInfo.getEnterpriseUrl4());
        dto.setaTitle(activityInfo.getaTitle());
        dto.setaSubtitle(activityInfo.getaSubtitle());
        dto.setaBtnTxt(activityInfo.getaBtnTxt());
        dto.setaDescription(activityInfo.getaDescription());
        dto.setbShareBtn(activityInfo.getbShareBtn());
        dto.setSharePic(activityInfo.getSharePic());
        dto.setShareTitle(activityInfo.getShareTitle());
        dto.setShareContent(activityInfo.getShareContent());
        dto.setActivityDescription(activityInfo.getActivityDescription());

        model.addAttribute("htmlTitle", activityInfo.getaTitle());

        Cookie cookie1 = new Cookie("activityId", activityId);
        cookie1.setPath("/");
        cookie1.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie1);

        String activityName = activityInfo.getActivityName();
        activityName = URLEncoder.encode(activityName, "utf-8");
        Cookie cookie2 = new Cookie("activityName", activityName);

        cookie2.setPath("/");
        cookie2.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie2);


        // 获取浏览器信息
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            throw new GenericException("非法请求，无浏览器标识");
        }

        UserDto user = null;
        String userName = "";
        // 从token中获取用户信息
        if(token != null)
        {
            user = jwtService.getUserFromToken(token);
            userName = user.getUserName();
            // 若为微信浏览器
            if (userAgent.toLowerCase().contains("MicroMessenger")){
                //3.3.8.	上传用户事件数据:进入活动页面
                String url = String.format("/collector/userEventLog/add?activityId=%s&activityName=%s&userType=%s&userName=%s&eventType=%s"
                        , activityId, URLEncoder.encode(activityInfo.getActivityName(),"utf-8"), user.getUserType(), user.getUserName(),4);
                ticketTypeService.addEventLog(urlBase2 + url);
            }
        }


        // 若为预览，则活动显示为进行为
        if (preview == true) {
            dto.setStatus(2);  //2：进行中
        } else {
            // 审核状态  0：修改     1：待审核      2：审核通过     3：审核不通过     4：下架     5：上架
            // 活动状态  1：未开始，  2：进行中， 3：已结束
            if (activityInfo.getActivityAudit() <= 3) {
                dto.setStatus(1);  //1：未开始
            } else if (activityInfo.getActivityAudit() == 4) {
                dto.setStatus(3);  //3：已结束
            } else {
                dto.setStatus(2);  //2：进行中
            }
        }
        // 获取ticketTypeIds
        List<PokerDetailDto> pokerDetails = ticketTypeService.getPokerDetails(activityId);
        if(pokerDetails != null && pokerDetails.size() > 0){
            for (PokerDetailDto pokerDetail : pokerDetails
                 ) {
                pokerDetail.setTicketBrief(StringUtils.decodeHtml(pokerDetail.getTicketBrief()));
            }
        }
        dto.setPokerDetails(pokerDetails);
        // 若为预览，则中奖结果为null
        if (preview == true) {
            dto.setDrawResult(null);
        } else {
            // 获取抽奖结果
            DrawResultDto drawResult = activityParticipationInfoService.findDrawResult(activityId, userName);

            drawResult = decodeHtml(drawResult);

            if(drawResult != null && drawResult.isStatus()){
                //3.3.7.	上传进入AB面数据,B面
                String url = String.format("/collector/enterLog/add?activityId=%s&activityName=%s&userType=%s&userName=%s&fromUserType=%s&fromUserName=%s&clientUserAgent=%s&redirectB=1"
                        , activityId, URLEncoder.encode(activityInfo.getActivityName(),"utf-8"), user.getUserType(), user.getUserName(),user.getFromUserType(),user.getFromUserName(),userAgent);
                ticketTypeService.addEventLog(urlBase2 + url);
            }

            dto.setDrawResult(drawResult);
        }
        return new RpcResult(1000, "操作成功", dto);

    }


    /**
     * 抽奖
     *
     * @return
     */
    @RequestMapping("/api/activity/draw")
    public RpcResult draw(@RequestParam("activityId") String activityId,
                          @RequestParam(value = "preview", defaultValue = "false") boolean preview,
//                          @CookieValue(value = "token", required = false) String token,
                          @RequestParam(value = "token") String token,
                          HttpServletRequest request) {
        logger.info("抽奖:/api/activity/draw");
        logger.info("activityId: -->" + activityId);
        logger.info("token:-->" + token);
//         若为预览，则直接返回一个抽奖结果
        if (preview == true) {
            DrawResultDto drawResultDto = previewDraw(activityId);
            return new RpcResult(1000, "操作成功", drawResultDto);
        } else {

            // 获取浏览器信息
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null) {
                throw new GenericException("非法请求，无浏览器标识");
            }
            // 若为微信浏览器
            if (!userAgent.toLowerCase().contains("micromessenger")){
                return new RpcResult(1001, "请在微信上进行抽奖！", null); //不支持非微信环境下抽奖
            }

            // 从token中获取用户信息
            UserDto user = jwtService.getUserFromToken(token);
            logger.info(user.toString());
            // 判断是否能参加活动,若已参加过活动了，则直接返回
            if (activityParticipationInfoService.isParticipated(user.getUserName(), activityId) == true) {
                return new RpcResult(1001, "你已经参与了活动！", null); //此请求为非法请求，在正常的流程中是不会发起这个请求的
            } else {
                // 抽券
                TicketCode ticketCode = activityInfoService.drawTicketCode(activityId);
                // 返回结果
                if (ticketCode == null) {
                    String copyWriting = officialInfoService.getNotWinningCopyWriting(activityId);
                    activityParticipationInfoService.addParticipationInfo(activityId, user, copyWriting, ticketCode);  // 添加参与记录
                    return new RpcResult(1000, "操作成功", new DrawResultDto(false, copyWriting));
                } else {
                    String copyWriting = officialInfoService.getWinningCopyWriting(activityId);
                    activityParticipationInfoService.addParticipationInfo(activityId, user, copyWriting, ticketCode);  // 添加参与记录
                    DrawResultDto drawResultDto = activityParticipationInfoService.getInitialDrawResult(
                            ticketCode.getTicketTypeId(), ticketCode.getTicketNo());

                    drawResultDto = decodeHtml(drawResultDto);

                    drawResultDto.setStatus(true);
                    drawResultDto.setCopyWriting(copyWriting);
                    return new RpcResult(1000, "操作成功", drawResultDto);
                }
            }
        }
    }


    /**
     * 预览时的抽奖
     *
     * @param activityId
     * @return
     */
    private DrawResultDto previewDraw(String activityId) {
        // 从数据库中获取奖券类型Id列表
        List<String> ticketTypeIds = activityTicketService.findTicketTypeIdsByActivityId(activityId);
        // 去除空牌
        List<String> newTicketTypeIds = new ArrayList<>();
        for (String ticketTypeId : ticketTypeIds) {
            if (!ticketTypeId.equals("0")) {   // 不为空牌,则添加. ticketTypeId=0表示空牌
                newTicketTypeIds.add(ticketTypeId);
            }
        }
        if (newTicketTypeIds.size() == 0) {
            throw new GenericException("严重错误：预览抽奖时找不到券！");
        } else {
            // 随机选取一个奖券类型id
            Random random = new Random();
            int index = random.nextInt(newTicketTypeIds.size());
            String ticketTypeId = newTicketTypeIds.get(index); // 抽取的奖券id
            // 取券的第一个券码
            TicketCode ticketCode = ticketCodeService.findFirstByTicketTypeId(ticketTypeId);
            if (ticketCode == null) {
                throw new GenericException("严重错误：预览抽奖时找不到券码！");
            } else {
                DrawResultDto drawResultDto = activityParticipationInfoService.getInitialDrawResult(
                        ticketTypeId, ticketCode.getTicketNo());

                drawResultDto = decodeHtml(drawResultDto);

                drawResultDto.setStatus(true);
                drawResultDto.setCopyWriting(officialInfoService.getWinningCopyWriting(activityId));
                return drawResultDto;
            }
        }
    }

    public static DrawResultDto decodeHtml(DrawResultDto drawResultDto)
    {
        if(drawResultDto != null){
            if(StringUtils.isNotEmpty(drawResultDto.getUsageGuide1())){
                drawResultDto.setUsageGuide1(StringUtils.decodeHtml(drawResultDto.getUsageGuide1()));
            }
            if(StringUtils.isNotEmpty(drawResultDto.getUsageGuide2())){
                drawResultDto.setUsageGuide2(StringUtils.decodeHtml(drawResultDto.getUsageGuide2()));
            }
        }

        return drawResultDto;
    }

}