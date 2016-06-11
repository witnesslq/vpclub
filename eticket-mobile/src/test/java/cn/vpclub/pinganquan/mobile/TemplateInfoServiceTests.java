package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.TemplateInfo;
import cn.vpclub.pinganquan.mobile.service.TemplateInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TemplateInfoServiceTests extends MobileApplicationTests {

    @Autowired
    private TemplateInfoService templateInfoService;

    @Test
    public void findByIdTest() {
        TemplateInfo entity = templateInfoService.findById("t0001");
        Assert.assertNotNull(entity);
    }


    @Test
    public void findByIdActivityId() {
        TemplateInfo entity = templateInfoService.findByActivityId("a001");
        Assert.assertNotNull(entity);
    }


}
