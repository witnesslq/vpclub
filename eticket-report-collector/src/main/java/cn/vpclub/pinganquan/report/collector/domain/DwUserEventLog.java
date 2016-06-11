package cn.vpclub.pinganquan.report.collector.domain;


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
public class DwUserEventLog {

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
     * 事件类型
     * 1=点击分享按钮
     */
    @NotNull
    private int eventType;


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

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * 构造函数
     */
    public DwUserEventLog() {
        this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
