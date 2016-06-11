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
public class DwTouchTicketLog {

    @Id
    private String id;

    /**
     * 活动ID
     */
    @NotNull
    private String activityId;

    /**
     * user_type
     * 1=短信，ID，2=微信openid
     */
    @NotNull
    private int userType;

    /**
     * 用户登录标识,用户登陆名
     */
    @NotNull
    private String userName;

    /**
     * 券类型主键ID
     */
    @NotNull
    private String ticketTypeId;


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

    public String getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }


    /**
     * 构造函数
     */
    public DwTouchTicketLog() {
        this.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}
