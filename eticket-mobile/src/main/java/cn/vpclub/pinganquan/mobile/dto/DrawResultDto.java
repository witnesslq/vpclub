package cn.vpclub.pinganquan.mobile.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2016/5/12.
 * 抽奖结果，用于在扑克牌上显示抽奖结果
 */
public class DrawResultDto {

    /**
     * 状态。true为抽中奖，false为未中奖
     * 只有中奖的时候，ticketId等才有值
     */
    private boolean status;

    /**
     * 抽奖结果文案内容
     */
    private String copyWriting;


    /**
     * 券id
     */
    private String ticketId;

    /**
     * 券名称
     */
    private String ticketName;

    /**
     * 券图片地址
     */
    private String tickeImageUrl;


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



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCopyWriting() {
        return copyWriting;
    }

    public void setCopyWriting(String copyWriting) {
        this.copyWriting = copyWriting;
    }

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

    public String getTickeImageUrl() {
        return tickeImageUrl;
    }

    public void setTickeImageUrl(String tickeImageUrl) {
        this.tickeImageUrl = tickeImageUrl;
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

    public DrawResultDto() {
    }

    public DrawResultDto(boolean status, String copyWriting) {
        this.status = status;
        this.copyWriting = copyWriting;
    }


}
