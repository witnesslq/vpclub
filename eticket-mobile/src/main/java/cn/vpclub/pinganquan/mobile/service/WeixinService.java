package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.common.EncoderHelper;
import cn.vpclub.pinganquan.mobile.config.WeixinSettings;
import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiConfigDto;
import cn.vpclub.pinganquan.mobile.dto.WeixinJsapiShareMessageDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/5/21.
 */

@Service
public class WeixinService {

    private static final Logger logger = LoggerFactory.getLogger(WeixinService.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WeixinSettings weixinSettings;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityInfoService activityInfoService;


    /**
     * @return 获取access_token
     */
    public String getAccessToken() {
        try {
            String key = "weixin:access_token";
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String accessToken = ops.get(key);
            // 若缓存中有则直接返回，否则调用微信api去取一个新的
            if (accessToken != null) {
                return accessToken;
            } else {
                // 从微信api中获取openid
                String url = String.format("https://api.weixin.qq.com/cgi-bin/token?" +
                                "grant_type=client_credential&appid=%s&secret=%s",
                        weixinSettings.getAppid(), weixinSettings.getSecret());
                RestTemplate template = new RestTemplate();
                String result = template.getForObject(url, String.class);
                logger.info(result);
                JsonNode rootNode = objectMapper.readTree(result);
                String newAccessToken = rootNode.get("access_token").asText();
                // 存入redis中
                ops.set(key, newAccessToken, 7200, TimeUnit.SECONDS);
                return newAccessToken;
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage());
            throw null;
        }
    }


    /**
     * @return 获取JsapiTicket
     */
    public String getJsapiTicket() {
        try {
            String key = "weixin:jsapi_ticket";
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String jsapiTicket = ops.get(key);
            // 若缓存中有则直接返回，否则调用微信api去取一个新的
            if (jsapiTicket != null) {
                return jsapiTicket;
            } else {
                String accessToken = this.getAccessToken();
                // 获取JsapiTicket
                String url = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?" +
                        "access_token=%s&type=jsapi", accessToken);
                RestTemplate template = new RestTemplate();
                String result = template.getForObject(url, String.class);
                logger.info(result);
                JsonNode rootNode = objectMapper.readTree(result);
                String newJsapiTicket = rootNode.get("ticket").asText();
                // 存入redis中
                ops.set(key, newJsapiTicket, 7200, TimeUnit.SECONDS);
                return newJsapiTicket;
            }
        } catch (Exception exp) {
            logger.error(exp.getMessage());
            throw null;
        }
    }


    /**
     * 获取jsapi的配置值
     *
     * @param url
     * @return
     */
    public WeixinJsapiConfigDto getWeixinJsapiConfig(String url) {
        String jsapiTicket = this.getJsapiTicket();
        if (jsapiTicket == null) {
            return new WeixinJsapiConfigDto();
        } else {
            long timestamp = new Date().getTime() / 1000;
            String nonceStr = Integer.toString(new Random().nextInt(1000000));
            // 签名时，需要将jsapi_ticket、noncestr、timestamp、url等四部分拼接在一起
            String strToSign = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%d&url=%s",
                    jsapiTicket, nonceStr, timestamp, url);
            // logger.info(strToSign);
            String signature = EncoderHelper.SHA1(strToSign);
            //  logger.info(signature);

            WeixinJsapiConfigDto jsapiConfig = new WeixinJsapiConfigDto();
            jsapiConfig.setAppId(weixinSettings.getAppid());
            jsapiConfig.setTimestamp(timestamp);
            jsapiConfig.setNonceStr(nonceStr);
            jsapiConfig.setSignature(signature);
            return jsapiConfig;
        }
    }


    /**
     * 获取用于jsapi的分享消息对象
     *
     * @param activityId
     * @param url
     * @return
     */
    public WeixinJsapiShareMessageDto getWeixinJsapiShareMessage(String activityId, String url) {
        ActivityInfo activityInfo = activityInfoService.findById(activityId);
        if (activityInfo == null) {
            return new WeixinJsapiShareMessageDto();
        } else {
            WeixinJsapiShareMessageDto shareMessage = new WeixinJsapiShareMessageDto();
            shareMessage.setTitle(activityInfo.getShareTitle());
            shareMessage.setDesc(activityInfo.getShareContent());
            shareMessage.setImgUrl(activityInfo.getSharePic());
            shareMessage.setLink(url);
            return shareMessage;
        }
    }

    public String getWeixinAuthUrl(String activityId, String token){
        String encodedRedirectUrl = null;
        try {
            encodedRedirectUrl = URLEncoder.encode(weixinSettings.getRedirectUrl() + "?activityId=" + activityId + "&token=" + token, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String weixinAuthUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=123eex#wechat_redirect", weixinSettings.getAppid(), encodedRedirectUrl);

        logger.info("weixinAuthUrl:--->" + weixinAuthUrl);
        return weixinAuthUrl;
    }
}


