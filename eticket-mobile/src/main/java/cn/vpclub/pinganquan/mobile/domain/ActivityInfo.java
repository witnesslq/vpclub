package cn.vpclub.pinganquan.mobile.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/3.
 */
@Entity
@Table
public class ActivityInfo {

    @Id
    private String id;


    public int delFlag;


    /**
     * 模板标识
     */
    private String templateId;


    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 品牌图片路径
     */
    private String logoUrl;


    /**
     * slogan图片路径
     */
    private String sloganUrl;


    /**
     * 背景图片路径
     */
    private String backgroundUrl;


    /**
     * 扑克牌背面图片
     */
    private String pokerBackUrl;

    /**
     * 合作企业图片1
     */
    private String enterpriseUrl1;


    /**
     * 合作企业图片2
     */
    private String enterpriseUrl2;


    /**
     * 合作企业图片3
     */
    private String enterpriseUrl3;


    /**
     * 合作企业图片4
     */
    private String enterpriseUrl4;


    /**
     * A面主标题
     */
    private String aTitle;


    /**
     * A面副标题
     */
    private String aSubtitle;

    /**
     * A面翻牌按钮文案
     */
    private String aBtnTxt;

    /**
     * A面活动说明文案
     */
    private String aDescription;


    /**
     * B面分享按钮文案
     */
    private String bShareBtn;


    /**
     * 分享图文图片
     */
    private String sharePic;

    /**
     * 分享图文标题
     */
    private String shareTitle;


    /**
     * 分享图文文案
     */
    private String shareContent;

    /**
     * 审核
     * 0：修改
     * 1：待审核
     * 2：审核通过
     * 3：审核不通过
     * 4：下架
     * 5：上架',
     */
    private int activityAudit;

    /**
     * 审核意见
     */
    private String auditContent;


    /**
     * 活动说明
     */
    private String activityDescription;


    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSloganUrl() {
        return sloganUrl;
    }

    public void setSloganUrl(String sloganUrl) {
        this.sloganUrl = sloganUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public int getActivityAudit() {
        return activityAudit;
    }

    public void setActivityAudit(int activityAudit) {
        this.activityAudit = activityAudit;
    }

    public String getPokerBackUrl() {
        return pokerBackUrl;
    }

    public void setPokerBackUrl(String pokerBackUrl) {
        this.pokerBackUrl = pokerBackUrl;
    }

    public String getEnterpriseUrl1() {
        return enterpriseUrl1;
    }

    public void setEnterpriseUrl1(String enterpriseUrl1) {
        this.enterpriseUrl1 = enterpriseUrl1;
    }

    public String getEnterpriseUrl2() {
        return enterpriseUrl2;
    }

    public void setEnterpriseUrl2(String enterpriseUrl2) {
        this.enterpriseUrl2 = enterpriseUrl2;
    }

    public String getEnterpriseUrl3() {
        return enterpriseUrl3;
    }

    public void setEnterpriseUrl3(String enterpriseUrl3) {
        this.enterpriseUrl3 = enterpriseUrl3;
    }

    public String getEnterpriseUrl4() {
        return enterpriseUrl4;
    }

    public void setEnterpriseUrl4(String enterpriseUrl4) {
        this.enterpriseUrl4 = enterpriseUrl4;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getaSubtitle() {
        return aSubtitle;
    }

    public void setaSubtitle(String aSubtitle) {
        this.aSubtitle = aSubtitle;
    }

    public String getaBtnTxt() {
        return aBtnTxt;
    }

    public void setaBtnTxt(String aBtnTxt) {
        this.aBtnTxt = aBtnTxt;
    }

    public String getaDescription() {
        return aDescription;
    }

    public void setaDescription(String aDescription) {
        this.aDescription = aDescription;
    }

    public String getbShareBtn() {
        return bShareBtn;
    }

    public void setbShareBtn(String bShareBtn) {
        this.bShareBtn = bShareBtn;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

}
