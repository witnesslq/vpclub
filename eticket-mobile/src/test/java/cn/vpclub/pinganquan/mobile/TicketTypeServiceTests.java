package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.TicketType;
import cn.vpclub.pinganquan.mobile.dto.PokerDetailDto;
import cn.vpclub.pinganquan.mobile.dto.PokerMoreDetailDto;
import cn.vpclub.pinganquan.mobile.dto.WinnigDetailDto;
import cn.vpclub.pinganquan.mobile.service.TicketTypeService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class TicketTypeServiceTests extends MobileApplicationTests {

    @Autowired
    private TicketTypeService ticketTypeService;

    @Test
    public void subtractTicketTest() {
        ticketTypeService.subtractTicket("t001", "n001");
        Assert.assertNotNull(true);
    }

    @Test
    public void findByIdTest() {
        TicketType ticketType = ticketTypeService.findById("t001");
        Assert.assertNotNull(ticketType);
    }

    @Test
    public void getPokerDetailTest() {
        PokerDetailDto dto = ticketTypeService.getPokerDetail("t001");
        Assert.assertNotNull(dto);
    }

    @Test
    public void getPokerDetailsTest() {
        List<PokerDetailDto> result = ticketTypeService.getPokerDetails("a001");
        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void getPokerMoreDetailTest() {
        PokerMoreDetailDto dto = ticketTypeService.getPokerMoreDetail("t001");
        Assert.assertNotNull(dto);
    }


    @Test
    public void getWinnigDetailDtoTest() {
        WinnigDetailDto dto = ticketTypeService.getWinnigDetailDto("t001", "ticketCode2");
        Assert.assertNotNull(dto);
    }


}
