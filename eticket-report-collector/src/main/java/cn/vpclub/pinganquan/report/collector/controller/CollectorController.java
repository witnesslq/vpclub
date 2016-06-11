package cn.vpclub.pinganquan.report.collector.controller;

import cn.vpclub.pinganquan.report.collector.domain.DwEnterLog;
import cn.vpclub.pinganquan.report.collector.domain.DwTouchTicketLog;
import cn.vpclub.pinganquan.report.collector.domain.DwUserEventLog;
import cn.vpclub.pinganquan.report.collector.dto.RpcResult;
import cn.vpclub.pinganquan.report.collector.service.DwEnterLogService;
import cn.vpclub.pinganquan.report.collector.service.DwTouchTicketLogService;
import cn.vpclub.pinganquan.report.collector.service.DwUserEventLogService;
import cn.vpclub.pinganquan.report.collector.service.IpSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/5/10.
 */
@RestController
public class CollectorController {

    @Autowired
    private DwEnterLogService enterLogService;

    @Autowired
    private DwTouchTicketLogService touchTicketLogService;

    @Autowired
    private DwUserEventLogService userEventLogService;

    @Autowired
    private IpSourceService ipSourceService;

    @RequestMapping("/demo")
    public String demo(){
        return "redirect: " + "/demo/user-behavior.html";
    }

    /**
     * 上传进入AB面数据
     *
     * @param enterLog
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/collector/enterLog/add")
    public RpcResult add1(@Valid @RequestBody DwEnterLog enterLog, HttpServletRequest request) throws IOException {
        // ip和areaCode需要进行额外操作
        String ip = this.getRemoteAddress(request);
        String areaCode = ipSourceService.getSourceFromTaobao(ip);
        enterLog.setIp(ip);
        enterLog.setAreaCode(areaCode);

        enterLog.setClientOs("");
        enterLog.setClientBrand("");
        enterLog.setClientModel("");
        enterLog.setClientVersion("");

        String userAgent = enterLog.getClientUserAgent();

        if(userAgent != null && userAgent.indexOf("iPhone") > -1){

            enterLog.setClientOs("IOS");
            enterLog.setClientBrand("iPhone");

            String version = enterLogService.getIPhoneVersion(userAgent);
            enterLog.setClientVersion(version);

        }

        boolean result = enterLogService.add(enterLog);
        if (result == true) {
            return new RpcResult(1000, "操作成功", null);
        } else {
            return new RpcResult(1001, "操作失败", null);
        }
    }


    /**
     * 上传摸牌数据
     *
     * @param touchTicketLog
     * @return
     */
    @RequestMapping("/collector/touchTicketLog/add")
    public RpcResult add2(@Valid @RequestBody DwTouchTicketLog touchTicketLog) {

        boolean result = touchTicketLogService.add(touchTicketLog);
        if (result == true) {
            return new RpcResult(1000, "操作成功", null);
        } else {
            return new RpcResult(1001, "操作失败", null);
        }
    }

    /**
     * 上传用户事件数据
     *
     * @param userEventLog
     * @return
     */
    @RequestMapping("/collector/userEventLog/add")
    public RpcResult add3(@RequestParam("activityId") String activityId,
                          @RequestParam("activityName") String activityName,
                          @RequestParam("eventType") int eventType,
                          @RequestParam("userType") int userType,
                          @RequestParam("userName") String userName) {

        DwUserEventLog userEventLog = new DwUserEventLog();
        userEventLog.setActivityId(activityId);
        try {
            userEventLog.setActivityName(URLDecoder.decode(activityName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new RpcResult(1001, "操作失败", null);
        }
        userEventLog.setEventType(eventType);
        userEventLog.setUserType(userType);
        userEventLog.setUserName(userName);
        boolean result = userEventLogService.add(userEventLog);
        if (result == true) {
            return new RpcResult(1000, "操作成功", null);
        } else {
            return new RpcResult(1001, "操作失败", null);
        }
    }


    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
