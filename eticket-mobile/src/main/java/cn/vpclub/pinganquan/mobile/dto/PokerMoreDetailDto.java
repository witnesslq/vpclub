package cn.vpclub.pinganquan.mobile.dto;

/**
 * Created by Administrator on 2016/5/5.
 */
public class PokerMoreDetailDto {

    private String ticketId;

    private String ticketName;

    /**
     * 券简介图片(大图)
     */
    private String ticketBigPic;


    // 详情（规格说明），对类型为1的ticketnContent
    private String ticketSpecifics;


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


    public String getTicketSpecifics() {
        return ticketSpecifics;
    }

    public void setTicketSpecifics(String ticketSpecifics) {
        this.ticketSpecifics = ticketSpecifics;
    }

    public String getTicketBigPic() {
        return ticketBigPic;
    }

    public void setTicketBigPic(String ticketBigPic) {
        this.ticketBigPic = ticketBigPic;
    }
}
