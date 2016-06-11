package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.common.EncoderHelper;
import cn.vpclub.pinganquan.mobile.service.WeixinService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/11.
 */
public class WeixinServiceTests extends MobileApplicationTests {


    @Autowired
    private WeixinService weixinService;


    @Test
    public void getAccessTokenTest() {
        String accessToken = weixinService.getAccessToken();
        Assert.assertNotNull(accessToken);
    }


    @Test
    public void getJsapiTicketTest() {
        String jsapiTicket = weixinService.getJsapiTicket();
        Assert.assertNotNull(jsapiTicket);
    }

    @Test
    public void getSha1Test() {
        String str = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMGTPGbCBrJs-C76coJ5keOcqMAtk1J3eaIQptXGzlSmmle8SrqAh6Vg6drcSir3fw&noncestr=707849&timestamp=1463840809337" +
                "&url=http://q.55dota.com/eticket-mobile/ui/activity?activityId=a001&fromUserName=1230600123&fromUserType=1";

        String signature = EncoderHelper.SHA1(str);

        Assert.assertNotNull(signature);
    }


}