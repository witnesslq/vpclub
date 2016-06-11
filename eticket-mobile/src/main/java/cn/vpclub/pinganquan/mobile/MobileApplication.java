package cn.vpclub.pinganquan.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class MobileApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MobileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MobileApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MobileApplication.class);
    }
}
