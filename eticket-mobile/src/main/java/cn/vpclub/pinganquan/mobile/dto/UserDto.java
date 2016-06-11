package cn.vpclub.pinganquan.mobile.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/5/13.
 */
public class UserDto {

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
     * '上级用户'
     */
    @NotNull
    private String fromUserName;

    /**
     * 1=短信，ID，2=微信openid
     */
    @NotNull
    private int fromUserType;


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

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(int fromUserType) {
        this.fromUserType = fromUserType;
    }

    public UserDto(String userName, int userType) {
        this.userName = userName;
        this.userType = userType;
    }


    public UserDto(String userName, int userType, String fromUserName, int fromUserType) {
        this.userName = userName;
        this.userType = userType;
        this.fromUserName = fromUserName;
        this.fromUserType = fromUserType;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", userType=" + userType +
                ", fromUserName='" + fromUserName + '\'' +
                ", fromUserType=" + fromUserType +
                '}';
    }
}
