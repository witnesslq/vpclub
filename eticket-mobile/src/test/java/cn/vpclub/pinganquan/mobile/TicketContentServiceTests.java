package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.TicketContent;
import cn.vpclub.pinganquan.mobile.repository.TicketContentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TicketContentServiceTests extends MobileApplicationTests {

    @Autowired
    private TicketContentRepository ticketContentRepository;


    @Test
    public void findByTicketTypeIdTest() {
        List<TicketContent> result = ticketContentRepository.findByTicketTypeId("t002");
        Assert.assertTrue(result.size() > 0);
    }


}
