package cn.vpclub.pinganquan.report.collector;


import cn.vpclub.pinganquan.report.collector.domain.DwTouchTicketLog;
import cn.vpclub.pinganquan.report.collector.service.DwTouchTicketLogService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class DwTouchTicketLogServiceTests extends ReportCollectorApplicationTests {

    @Autowired
    private DwTouchTicketLogService dwTouchTicketLogService;


    @Test
    public void addTest() {
        DwTouchTicketLog entity = new DwTouchTicketLog();
        entity.setActivityId("a001");
        entity.setUserType(1);
        entity.setUserName("15019460591");
        entity.setTicketTypeId("t001");
        boolean result = dwTouchTicketLogService.add(entity);
        Assert.assertTrue(result);
    }


}
