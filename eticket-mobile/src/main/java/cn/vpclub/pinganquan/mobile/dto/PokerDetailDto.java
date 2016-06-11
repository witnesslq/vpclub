package cn.vpclub.pinganquan.mobile.dto;

/**
 * Created by Administrator on 2016/5/5.
 */
public class PokerDetailDto {

    private String ticketId;

    private String ticketName;

    private String tickeImageUrl;

    private String ticketActPrice;

    // 剩余的券数量
    private int remainNum;

    // 虚拟用券数量
    private int virtualDrawedNum;


    private String ticketBrief;


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

    public String getTicketActPrice() {
        return ticketActPrice;
    }

    public void setTicketActPrice(String ticketActPrice) {
        this.ticketActPrice = ticketActPrice;
    }


    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    public String getTicketBrief() {
        return ticketBrief;
    }

    public void setTicketBrief(String ticketBrief) {
        this.ticketBrief = ticketBrief;
    }


    public int getVirtualDrawedNum() {
        return virtualDrawedNum;
    }

    public void setVirtualDrawedNum(int virtualDrawedNum) {
        this.virtualDrawedNum = virtualDrawedNum;
    }
}
