package cn.vpclub.pinganquan.mobile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Administrator on 2016/5/3.
 */
@Entity
@Table
public class ActivityParticipationInfo {

    @Id
    private String id;


    public int delFlag;


    /**
     * 活动ID
     */
    @NotNull
    private String activityId;


    /**
     * 用户登录标识
     */
    @NotNull
    private String userName;

    /**
     * 用户登录类型，1=短信，ID，2=微信openid
     */
    @NotNull
    private int userType;


    /**
     * 上级用户
     */
    @NotNull
    private String fromUserName;

    /**
     * 1=短信，ID，2=微信openid
     */
    @NotNull
    private int fromUserType;


    /**
     * 券类型主键ID,未中奖为0
     */
    @NotNull
    private String ticketTypeId;

    /**
     * 券码
     */
    @NotNull
    private String ticketNo;

    /**
     * 券码的二维码图片
     */
    @NotNull
    private String ticketNoPic;

    /**
     * 券密码
     */
    @NotNull
    private String ticketPsw;


    /**
     * 文案内容
     */
    @NotNull
    private String officialCo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
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

    public String getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketPsw() {
        return ticketPsw;
    }

    public void setTicketPsw(String ticketPsw) {
        this.ticketPsw = ticketPsw;
    }

    public String getTicketNoPic() {
        return ticketNoPic;
    }

    public void setTicketNoPic(String ticketNoPic) {
        this.ticketNoPic = ticketNoPic;
    }

    public String getOfficialCo() {
        return officialCo;
    }

    public void setOfficialCo(String officialCo) {
        this.officialCo = officialCo;
    }

    public ActivityParticipationInfo() {
        this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
