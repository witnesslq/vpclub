package cn.vpclub.pinganquan.mobile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Entity
@Table
public class OfficialInfo {
    @Id
    private String id;


    public int delFlag;


    /**
     * 活动ID
     */
    private String activityId;

    /**
     * '文案库内容
     */
    private String officialCo;

    /**
     * 文案库内容
     * 1：中奖文案    2：未中奖文案
     */
    private int officialType;


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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getOfficialCo() {
        return officialCo;
    }

    public void setOfficialCo(String officialCo) {
        this.officialCo = officialCo;
    }

    public int getOfficialType() {
        return officialType;
    }

    public void setOfficialType(int officialType) {
        this.officialType = officialType;
    }
}


