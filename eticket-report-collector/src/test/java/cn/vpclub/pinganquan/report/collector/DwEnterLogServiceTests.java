package cn.vpclub.pinganquan.report.collector;


import cn.vpclub.pinganquan.report.collector.domain.DwEnterLog;
import cn.vpclub.pinganquan.report.collector.service.DwEnterLogService;
import cn.vpclub.pinganquan.report.collector.service.DwUserEventLogService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class DwEnterLogServiceTests extends ReportCollectorApplicationTests {

    @Autowired
    private DwEnterLogService dwEnterLogService;


    @Test
    public void addTest() {
        DwEnterLog entity = new DwEnterLog();
        entity.setActivityId("a001");
        entity.setActivityName("xxx活动");
        entity.setUserType(1);
        entity.setUserName("15019460591");
        entity.setIp("183.14.225.208");
        entity.setAreaCode("广东省深圳市");
        entity.setClientUserAgent("xdsfswrwerewrewr");
        entity.setRedirectB(1);
        boolean result = dwEnterLogService.add(entity);
        Assert.assertTrue(result);
    }


}
