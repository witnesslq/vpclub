package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.ActivityInfo;
import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.service.ActivityInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ActivityInfoServiceTests extends MobileApplicationTests {


    @Autowired
    private ActivityInfoService activityInfoService;

    @Test
    public void findByIdTest() {
        ActivityInfo entity = activityInfoService.findById("a001");
        Assert.assertNotNull(entity);
    }


    @Test
    public void findByIdTest2() {
        ActivityInfo entity = activityInfoService.findById("a003");
        Assert.assertNull(entity);
    }


    @Test
    public void drawTicketCode2Test() {
        TicketCode ticketCode = activityInfoService.drawTicketCode2("a001");
        Assert.assertNull(ticketCode);
    }


}
