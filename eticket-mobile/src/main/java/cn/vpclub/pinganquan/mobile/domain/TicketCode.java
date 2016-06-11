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
public class TicketCode {
    @Id
    private String id;


    public int delFlag;

    /**
     * 券码
     */
    @NotNull
    private String ticketNo;

    /**
     * 券码的二维码
     */
    @NotNull
    private String ticketPic;

    /**
     * 1券码，2二维码
     */
    @NotNull
    private int ticketType;


    /**
     * 券类型ID
     */
    @NotNull
    private String ticketTypeId;


    /**
     * 券密码
     */
    private String ticketPsw;


    /**
     * 是否使用
     */
    private Boolean isUse;


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

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketPsw() {
        return ticketPsw;
    }

    public void setTicketPsw(String ticketPsw) {
        this.ticketPsw = ticketPsw;
    }

    public Boolean getUse() {
        return isUse;
    }

    public void setUse(Boolean use) {
        isUse = use;
    }

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticket_type) {
        this.ticketType = ticket_type;
    }

    public String getTicketPic() {
        return ticketPic;
    }

    public void setTicketPic(String ticketPic) {
        this.ticketPic = ticketPic;
    }

    public TicketCode() {
        this.setUse(false);
        this.setId(UUID.randomUUID().toString().replace("-", ""));
    }


}


