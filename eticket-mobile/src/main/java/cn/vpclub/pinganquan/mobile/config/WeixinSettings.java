package cn.vpclub.pinganquan.mobile.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/5/9.
 */

@Component
@ConfigurationProperties("weixin.oauth")
public class WeixinSettings {

    /**
     * appid
     */
    private String appid;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 回调地址
     */
    private String redirectUrl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
