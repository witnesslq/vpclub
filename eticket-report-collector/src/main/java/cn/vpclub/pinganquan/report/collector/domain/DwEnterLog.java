package cn.vpclub.pinganquan.report.collector.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by hedehai on 2016/5/10.
 */

@Entity
@Table
public class DwEnterLog {


    @Id
    private String id;

    /**
     * 活动ID
     */
    @NotNull
    private String activityId;

    /**
     * 活动名称
     */
    @NotNull
    private String activityName;

    /**
     * user_type
     * 1=短信，ID，2=微信openid'
     */
    @NotNull
    private int userType;

    /**
     * 用户登录标识,用户登陆名
     */
    @NotNull
    private String userName;

    /**
     * 1=短信，ID，2=微信openid
     */
    @NotNull
    private int fromUserType;

    /**
     * '上级用户'
     */
    @NotNull
    private String fromUserName;

    /**
     * ip
     */
    private String ip;

    /**
     * 所属区域名称
     */
    private String areaCode;

    /**
     * 客户端浏览器信息
     */
    @NotNull
    private String clientUserAgent;

    /**
     * 是否是直接进入B面，
     * 1=是，0=不是
     * 换句话说，就是0为A面，1为B面
     */
    @Column(name = "redirect_b")
    private int redirectB;

// 以下几个字段暂时不需要 2016.5.11 何德海
//    /**
//     *  系统名称
//     *
//     */
//    private String clientOs;
//    /**
//     * 手机系统版本
//     */
//    private String clientVersion;
//
//    /**
//     * 手机品牌
//     */
//    private String clientBrand;

//    /**
//     * 手机型号
//     */
//    private String clientModel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getClientUserAgent() {
        return clientUserAgent;
    }

    public void setClientUserAgent(String clientUserAgent) {
        this.clientUserAgent = clientUserAgent;
    }

    public int getRedirectB() {
        return redirectB;
    }

    public void setRedirectB(int redirectB) {
        this.redirectB = redirectB;
    }

    public int getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(int fromUserType) {
        this.fromUserType = fromUserType;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    /**
     * 构造函数
     */
    public DwEnterLog() {
        this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
