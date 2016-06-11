package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.service.OfficialInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/3.
 */
public class OfficialInfoServiceTests extends MobileApplicationTests {

    @Autowired
    private OfficialInfoService officialInfoService;


    @Test
    public void findWinningTest() {
       String copyWriting = officialInfoService.getWinningCopyWriting("a001");
        System.out.println(copyWriting);
        Assert.assertNotNull(copyWriting);
    }


    @Test
    public void findNotWinningTest() {
        String copyWriting = officialInfoService.getNotWinningCopyWriting("a001");
        System.out.println(copyWriting);
        Assert.assertNotNull(copyWriting);
    }


}
