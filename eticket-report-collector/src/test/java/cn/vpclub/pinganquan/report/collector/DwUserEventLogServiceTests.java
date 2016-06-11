package cn.vpclub.pinganquan.report.collector;


import cn.vpclub.pinganquan.report.collector.domain.DwEnterLog;
import cn.vpclub.pinganquan.report.collector.domain.DwUserEventLog;
import cn.vpclub.pinganquan.report.collector.service.DwUserEventLogService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class DwUserEventLogServiceTests extends ReportCollectorApplicationTests {

    @Autowired
    private DwUserEventLogService dwUserEventLogService;


    @Test
    public void addTest() {
        DwUserEventLog entity = new DwUserEventLog();
        entity.setActivityId("a001");
        entity.setActivityName("xxx活动");
        entity.setUserType(1);
        entity.setUserName("15019460591");
        entity.setEventType(1);
        boolean result = dwUserEventLogService.add(entity);
        Assert.assertTrue(result);
    }


}
