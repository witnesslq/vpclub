package cn.vpclub.pinganquan.report.collector;


import cn.vpclub.pinganquan.report.collector.service.IpSourceService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/3.
 */
public class IpSourceServiceTests extends ReportCollectorApplicationTests {

    @Autowired
    private IpSourceService ipSourceService;

    @Test
    public void getSourceFromSinaTest() throws IOException {
        String address = ipSourceService.getSourceFromSina("183.14.225.208");
        Assert.assertEquals(address, "广东深圳");
    }

    @Test
    public void getSourceFromTaobaoTest() throws IOException {
        String address = ipSourceService.getSourceFromTaobao("183.14.225.208");
        Assert.assertEquals(address, "广东省深圳市");
    }
}
