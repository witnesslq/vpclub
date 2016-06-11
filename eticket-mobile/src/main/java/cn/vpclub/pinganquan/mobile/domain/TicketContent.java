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
public class TicketContent {

    @Id
    private String id;

    /**
     * 券类型ID
     */
    @NotNull
    private String ticketTypeId;

    /**
     * 券详情
     */
    @NotNull
    private String ticketContent;

    /**
     * 1：券详情 2：微信端-B面用券说明 3：浏览器端-B面用券说明
     */
    @NotNull
    private int ticketContentType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public int getTicketContentType() {
        return ticketContentType;
    }

    public void setTicketContentType(int ticketContentType) {
        this.ticketContentType = ticketContentType;
    }

    public TicketContent() {
        this.setId(UUID.randomUUID().toString().replace("-", ""));
    }


}


