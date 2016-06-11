package cn.vpclub.pinganquan.mobile.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/5/5.
 */
public class WinnigDetailDto {

    private String ticketId;

    private String ticketName;

    /**
     * 券简介图片(大图)
     */
    private String ticketBigPic;

    private String tickeCodeUrl;

    private String ticketBrief;


    /**
     * 详情（规格说明），对类型为1的ticketContent
     */
    private String ticketSpecifics;

    /**
     * 用券说明1,用于微信环境，对类型为2的ticketContent
     */
    private String usageGuide1;

    /**
     * 用券说明2，用于普通浏览器环境，对类型为3的ticketContent
     */
    private String usageGuide2;

    /**
     * 券码类型，
     * 1 为券码， 2 为券码的二维码
     */
    private int ticketCodeType;

    /**
     * 券码
     */
    @NotNull
    private String ticketNo;

    /**
     * 券码的二维码
     */
    @NotNull
    private String ticketNoPic;


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketBigPic() {
        return ticketBigPic;
    }

    public void setTicketBigPic(String ticketBigPic) {
        this.ticketBigPic = ticketBigPic;
    }

    public String getTicketBrief() {
        return ticketBrief;
    }

    public void setTicketBrief(String ticketBrief) {
        this.ticketBrief = ticketBrief;
    }

    public String getTicketSpecifics() {
        return ticketSpecifics;
    }

    public void setTicketSpecifics(String ticketSpecifics) {
        this.ticketSpecifics = ticketSpecifics;
    }

    public String getUsageGuide1() {
        return usageGuide1;
    }

    public void setUsageGuide1(String usageGuide1) {
        this.usageGuide1 = usageGuide1;
    }

    public String getUsageGuide2() {
        return usageGuide2;
    }

    public void setUsageGuide2(String usageGuide2) {
        this.usageGuide2 = usageGuide2;
    }

    public String getTickeCodeUrl() {
        return tickeCodeUrl;
    }

    public int getTicketCodeType() {
        return ticketCodeType;
    }

    public void setTicketCodeType(int ticketCodeType) {
        this.ticketCodeType = ticketCodeType;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketNoPic() {
        return ticketNoPic;
    }

    public void setTicketNoPic(String ticketNoPic) {
        this.ticketNoPic = ticketNoPic;
    }

    public void setTickeCodeUrl(String tickeCodeUrl) {
        this.tickeCodeUrl = tickeCodeUrl;
    }
}
