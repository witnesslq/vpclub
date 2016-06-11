package cn.vpclub.pinganquan.report.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class ReportCollectorApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ReportCollectorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ReportCollectorApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ReportCollectorApplication.class);
    }
}
