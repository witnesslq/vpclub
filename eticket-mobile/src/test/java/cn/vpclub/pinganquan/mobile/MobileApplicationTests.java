package cn.vpclub.pinganquan.mobile;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MobileApplication.class)
@WebAppConfiguration
public abstract class MobileApplicationTests {

    @Before
    public void contextLoads() {
    }


}
