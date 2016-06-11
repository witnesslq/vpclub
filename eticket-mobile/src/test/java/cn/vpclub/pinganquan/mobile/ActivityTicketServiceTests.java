package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.service.ActivityTicketService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ActivityTicketServiceTests extends MobileApplicationTests {

    @Autowired
    private ActivityTicketService activityTicketService;





    @Test
    public void findTicketTypeIdsTest() {
        List<String> list = activityTicketService.findTicketTypeIdsByActivityId("a001");
        Assert.assertEquals(4, list.size());
    }

}
