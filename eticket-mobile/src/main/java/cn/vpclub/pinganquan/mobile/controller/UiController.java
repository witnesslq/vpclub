package cn.vpclub.pinganquan.mobile.controller;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.common.StringUtils;
import cn.vpclub.pinganquan.mobile.config.WeixinSettings;
import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.domain.TemplateInfo;
import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiConfigDto;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiShareMessageDto;
import cn.vpclub.pinganquan.mobile.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/19.
 */
@Controller
public class UiController {

    Logger logger = LoggerFactory.getLogger(UiController.class);

    @Autowired
    private WeixinSettings weixinSettings;


    @Value("${customer.client.url-base1}")
    private String urlBase1;


    @Value("${customer.client.url-base2}")
    private String urlBase2;

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TemplateInfoService templateInfoService;


    @Autowired
    private WeixinService weixinService;


    @RequestMapping("/ui/activity")
    public String index(@RequestParam("activityId") String activityId,
                        @RequestParam(value = "token", required = false) String token,
                        @RequestParam(value = "code", required = false) String code,
                        @CookieValue(value = "cookieToken", required = false) String cookieToken,
                        HttpServletRequest request, Model model, HttpServletResponse response){

        // 获取浏览器信息
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            throw new GenericException("非法请求，无浏览器标识");
        }

        // 获取模板信息
        TemplateInfo templateInfo = templateInfoService.findByActivityId(activityId);
        String redirect =  String.format("/activity-template/%s/index", templateInfo.getId());

        model.addAttribute("templateUrl", templateInfo.getTemplateUrl());
        model.addAttribute("urlBase1", urlBase1);
        model.addAttribute("urlBase2", urlBase2);
        model.addAttribute("nonce", Integer.toString(new Random().nextInt(1000000)));

        ActivityInfo activityInfo = activityInfoService.findActivityInfoById(activityId);
        String htmlTitle = activityInfo.getaTitle();
        model.addAttribute("htmlTitle", htmlTitle);

        String activityName = activityInfo.getActivityName();
        try {
            cookieService.add("activityName", URLEncoder.encode(activityName, "utf-8"), response);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        if (!userAgent.contains("MicroMessenger") && code == null)
        {
            logger.info("为普通浏览器");
            model.addAttribute("jsapiConfig", new WeixinJsapiConfigDto());
            model.addAttribute("jsapiShareMessage", new WeixinJsapiShareMessageDto());

            if (templateInfo == null) {
                throw new GenericException("找不到活动模板！");
            }
            return redirect;
        }

        logger.info("url::::::::::" + request.getRequestURL().toString() + "?" + request.getQueryString());
        logger.info("token-->{}", token);
        logger.info("code-->{}", code);
        logger.info("cookieToken-->{}", cookieToken);

        logger.info("validate:{}",token == null || token.equals("null") || StringUtils.isEmpty(cookieToken) || cookieToken.equals("null"));
        //第一人进来时token为空
        if (token == null || token.equals("null") || StringUtils.isEmpty(cookieToken) || cookieToken.equals("null")) {
            return "redirect:" + weixinService.getWeixinAuthUrl(activityId,token);
        }

        model.addAttribute("token", cookieToken);

        String currentPageUrl = request.getRequestURL().toString() + "?" + request.getQueryString();

        try {
            UserDto cookieUser = jwtService.getUserFromToken(cookieToken);
            logger.info("model.user--->{}" , cookieUser);

            UserDto user = jwtService.getUserFromToken(token);
            logger.info("url.user--->{}" , user);

            // 若url地址中的from不等于当前的用户名，则使用重定向来重写url中的参数
            if(!cookieUser.getUserName().equals(user.getFromUserName()))
            {
                return "redirect:" + weixinService.getWeixinAuthUrl(activityId,token);
            }
        }catch (GenericException e){
            logger.error(e.getMessage());
        }

        logger.info("currentPageUrl--->{}", currentPageUrl);
        // jsapi config
        WeixinJsapiConfigDto jsapiConfig = weixinService.getWeixinJsapiConfig(currentPageUrl);
        model.addAttribute("jsapiConfig", jsapiConfig);

        // jsapi share message
        WeixinJsapiShareMessageDto jsapiShareMessage = weixinService.getWeixinJsapiShareMessage(activityId, currentPageUrl);
        model.addAttribute("jsapiShareMessage", jsapiShareMessage);
        return redirect;
    }

}