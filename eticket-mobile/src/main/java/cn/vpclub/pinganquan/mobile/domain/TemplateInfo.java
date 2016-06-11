package cn.vpclub.pinganquan.mobile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Entity
@Table
public class TemplateInfo {
    @Id
    private String id;


    public int delFlag;


    /**
     * 模板名称
     */
    private String templateName;


    /**
     * 模板路径
     */
    private String templateUrl;


    /**
     * A面缩略图
     */
    private String aFaceThumb;


    /**
     * B面缩略图
     */
    private String bFaceThumb;


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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getaFaceThumb() {
        return aFaceThumb;
    }

    public void setaFaceThumb(String aFaceThumb) {
        this.aFaceThumb = aFaceThumb;
    }

    public String getbFaceThumb() {
        return bFaceThumb;
    }

    public void setbFaceThumb(String bFaceThumb) {
        this.bFaceThumb = bFaceThumb;
    }
}
