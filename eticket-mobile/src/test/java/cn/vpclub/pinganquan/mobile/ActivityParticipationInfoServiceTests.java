package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.domain.ActivityParticipationInfo;
import cn.vpclub.pinganquan.mobile.dto.DrawResultDto;
import cn.vpclub.pinganquan.mobile.service.ActivityParticipationInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ActivityParticipationInfoServiceTests extends MobileApplicationTests {

    @Autowired
    private ActivityParticipationInfoService service;

    @Test
    public void add() {
        ActivityParticipationInfo entity = new ActivityParticipationInfo();
        entity.setActivityId("001");
        Random random = new Random();
        entity.setUserName(Integer.toString(random.nextInt(10000)));

        boolean result = service.add(entity);
        Assert.assertTrue(result);
    }


    @Test
    public void isParticipatedTest() {
        Boolean flag = service.isParticipated("15019460591", "a001");
        Assert.assertTrue(flag);
    }

    @Test
    public void getInitialDrawResultTest() {
        DrawResultDto result = service.getInitialDrawResult("t002", "ticketCode2");
        Assert.assertNotNull(result);
    }


}
