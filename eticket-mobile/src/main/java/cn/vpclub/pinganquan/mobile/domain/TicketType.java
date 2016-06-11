package cn.vpclub.pinganquan.mobile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Entity
@Table
public class TicketType {

    /**
     * 券类型主键ID
     */
    @Id
    private String id;


    public int delFlag;


    /**
     * 联盟方Id
     */
    private String cooperatorId;


    /**
     * 券名称
     */
    private String ticketName;


    /**
     * 券编号
     */
    private String ticketNum;

    /**
     * 券的面值
     */
    private float ticketActPrice;

    /**
     * 券简介图片
     */
    private String tickeImageUrl;


    /**
     * 券简介图片(大图)
     */
    private String ticketBigPic;


    /**
     * 券简介图片
     */
    private String tickeCodeUrl;


    /**
     * 券总量
     */
    private int stockNum;

    /**
     * 券剩余数量
     */
    private int remainNum;

    /**
     * 领券基数
     */
    private int virtualNum;


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

    public String getCooperatorId() {
        return cooperatorId;
    }

    public void setCooperatorId(String cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public float getTicketActPrice() {
        return ticketActPrice;
    }

    public void setTicketActPrice(float ticketActPrice) {
        this.ticketActPrice = ticketActPrice;
    }

    public String getTickeImageUrl() {
        return tickeImageUrl;
    }

    public void setTickeImageUrl(String tickeImageUrl) {
        this.tickeImageUrl = tickeImageUrl;
    }

    public String getTickeCodeUrl() {
        return tickeCodeUrl;
    }

    public void setTickeCodeUrl(String tickeCodeUrl) {
        this.tickeCodeUrl = tickeCodeUrl;
    }

    public int getVirtualNum() {
        return virtualNum;
    }

    public void setVirtualNum(int virtualNum) {
        this.virtualNum = virtualNum;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    public String getTicketBigPic() {
        return ticketBigPic;
    }

    public void setTicketBigPic(String ticketBigPic) {
        this.ticketBigPic = ticketBigPic;
    }
}


