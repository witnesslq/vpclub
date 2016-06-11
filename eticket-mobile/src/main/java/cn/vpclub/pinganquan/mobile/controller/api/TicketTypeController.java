package cn.vpclub.pinganquan.mobile.controller.api;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.common.StringUtils;
import cn.vpclub.pinganquan.mobile.dto.*;
import cn.vpclub.pinganquan.mobile.service.JwtService;
import cn.vpclub.pinganquan.mobile.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/5/5.
 */

@RestController
public class TicketTypeController {

    @Value("${customer.client.url-base2}")
    private String urlBase2;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TicketTypeService ticketTypeService;




    /**
     * 获取扑克详情
     *
     * @param ticketTypeId
     * @return
     */
    @RequestMapping("/api/ticketType/getPokerDetail")
    public RpcResult getPokerDetail(@RequestParam("ticketTypeId") String ticketTypeId) {
        PokerDetailDto dto = ticketTypeService.getPokerDetail(ticketTypeId);

        if (dto == null) {
            return new RpcResult(1001, "找不到券", null);
        } else {
            if(StringUtils.isNotEmpty(dto.getTicketBrief())){
                dto.setTicketBrief(StringUtils.decodeHtml(dto.getTicketBrief()));
            }
            return new RpcResult(1000, "操作成功", dto);
        }
    }


    /**
     * 获取扑克更多详情
     * @param ticketTypeId
     * @return
     */
    @RequestMapping("/api/ticketType/getPokerMoreDetail")
    public RpcResult getPokerMoreDetail(@RequestParam("ticketTypeId") String ticketTypeId,
                                        @RequestParam(value = "token", required = false) String token,
                                        @CookieValue(value = "activityId", required = false) String activityId,
                                        @CookieValue(value = "activityName", required = false) String activityName,
                                        HttpServletRequest request) {
        PokerMoreDetailDto dto = ticketTypeService.getPokerMoreDetail(ticketTypeId);

        if (dto == null) {
            return new RpcResult(1001, "找不到券", null);
        } else {
            if(StringUtils.isNotEmpty(dto.getTicketSpecifics())){
                dto.setTicketSpecifics(StringUtils.decodeHtml(dto.getTicketSpecifics()));
            }


            // 获取浏览器信息
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null) {
                throw new GenericException("非法请求，无浏览器标识");
            }
            if (!userAgent.contains("MicroMessenger"))
            {
                return new RpcResult(1000, "操作成功", dto);

            }

            //3.3.8.	上传用户事件数据:点击中奖详情
            UserDto user = jwtService.getUserFromToken(token);
            try {
                String url = String.format("/collector/userEventLog/add?activityId=%s&activityName=%s&userType=%s&userName=%s&eventType=%s"
                        , activityId, URLEncoder.encode(activityName,"utf-8"), user.getUserType(), user.getUserName(),2);

                ticketTypeService.addEventLog(urlBase2 + url);

                return new RpcResult(1000, "操作成功", dto);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new RpcResult(1001, "操作失败", null);
            }
        }
    }


    /**
     * 获取中奖详情
     *
     * @param ticketTypeId
     * @param ticketNo
     * @return
     */
    @RequestMapping("/api/ticketType/getWinnigDetail")
    public RpcResult getWinnigDetail(@RequestParam("ticketTypeId") String ticketTypeId,
                                     @RequestParam("ticketNo") String ticketNo,
                                     @CookieValue(value = "token", required = false) String token,
                                     @CookieValue(value = "activityId", required = false) String activityId,
                                     @CookieValue(value = "activityName", required = false) String activityName,
                                     HttpServletRequest request) {
        WinnigDetailDto dto = ticketTypeService.getWinnigDetailDto(ticketTypeId, ticketNo);

        if (dto == null) {
            return new RpcResult(1001, "找不到券", null);
        } else {
            if(StringUtils.isNotEmpty(dto.getTicketBrief())){
                dto.setTicketBrief(StringUtils.decodeHtml(dto.getTicketBrief()));
            }
            if(StringUtils.isNotEmpty(dto.getTicketSpecifics())){
                dto.setTicketSpecifics(StringUtils.decodeHtml(dto.getTicketSpecifics()));
            }
            if(StringUtils.isNotEmpty(dto.getUsageGuide1())){
                dto.setUsageGuide1(StringUtils.decodeHtml(dto.getUsageGuide1()));
            }
            if(StringUtils.isNotEmpty(dto.getUsageGuide2())){
                dto.setUsageGuide2(StringUtils.decodeHtml(dto.getUsageGuide2()));
            }

            // 获取浏览器信息
            String userAgent = request.getHeader("User-Agent");
            if (userAgent == null) {
                throw new GenericException("非法请求，无浏览器标识");
            }
            if (!userAgent.contains("MicroMessenger"))
            {
                return new RpcResult(1000, "操作成功", dto);
            }

            //3.3.8.	上传用户事件数据:点击中奖详情
            UserDto user = jwtService.getUserFromToken(token);
            try {
                String url = String.format("/collector/userEventLog/add?activityId=%s&activityName=%s&userType=%s&userName=%s&eventType=%s"
                        , activityId, URLEncoder.encode(activityName,"utf-8"), user.getUserType(), user.getUserName(),3);

                ticketTypeService.addEventLog(urlBase2 + url);

                return new RpcResult(1000, "操作成功", dto);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new RpcResult(1001, "操作失败", null);
            }

        }
    }

}
