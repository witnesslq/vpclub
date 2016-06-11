package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import cn.vpclub.pinganquan.mobile.service.TicketCodeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TicketCodeServiceTests extends MobileApplicationTests {

    @Autowired
    private TicketCodeService ticketCodeService;


    @Test
    public void findFirstByTicketTypeIdTest() {
        TicketCode result = ticketCodeService.findFirstByTicketTypeId("001");
        Assert.assertNotNull(result);
    }

    @Test
    public void getAnUsedTicketCodeTest() {
        TicketCode result = ticketCodeService.getAnUsedTicketCode("t001");
        Assert.assertNotNull(result);
    }

    @Test
    public void popTicketCodeTest() {
        TicketCode result = ticketCodeService.popTicketCode("t001");
        Assert.assertNotNull(result);
    }


}
