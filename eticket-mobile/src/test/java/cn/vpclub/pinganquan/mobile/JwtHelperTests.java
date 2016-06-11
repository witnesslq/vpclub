package cn.vpclub.pinganquan.mobile;

import cn.vpclub.pinganquan.mobile.dto.UserDto;
import cn.vpclub.pinganquan.mobile.service.JwtService;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/5/11.
 */
public class JwtHelperTests extends MobileApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(JwtHelperTests.class);

    @Autowired
    private JwtService jwtService;


    @Test
    public void putUserIntoTokenTest() {
//        UserDto user = new UserDto("oslJouoslJouPTQ2HvAYgwPWR6zPvv2SuUMDg_YhmFdJFebd1ncKyjvU", 2, "oslJouPTQ2HvAYgwPWR6zPvv2SuU", 2);
//        UserDto user = new UserDto("oslJouPTQ2HvAYgwPWR6zPvv2SuU", 2, "oslJouPTQ2HvAYgwPWR6zPvv2SuU", 2);
        UserDto user = new UserDto("13088840047", 2, "13088840047", 2);
//        UserDto user = new UserDto("oslJouMDg_YhmFdJFebd1ncKyjvU", 2, "", 1);
        String token = jwtService.putUserIntoToken(user);
        logger.info("http://q.55dota.com/ui/activity?activityId=b4c91394c72244a2bd15dc7e1082c2a7&token={}",token);
    }


    @Test
    public void getUsernameFromTokenTest() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE0NjU1OTE4MjYsImZyb21Vc2VyVHlwZSI6MiwidXNlck5hbWUiOiJvc2xKb3VQVFEySHZBWWd3UFdSNnpQdnYyU3VVIiwiZnJvbVVzZXJOYW1lIjoib3NsSm91UFRRMkh2QVlnd1BXUjZ6UHZ2MlN1VSIsInVzZXJUeXBlIjoyfQ.BmqM0UOvhK92vg3oB62TAANxBgi_hRFULhSAU-4k2TY";

        UserDto userDto = jwtService.getUserFromToken(token);
        logger.info(userDto.toString());
    }

}