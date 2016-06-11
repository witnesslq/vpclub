package cn.vpclub.pinganquan.report.collector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReportCollectorApplication.class)
@WebAppConfiguration
public abstract class ReportCollectorApplicationTests {

    @Before
    public void contextLoads() {
    }


}
