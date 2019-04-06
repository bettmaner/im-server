package edu.ncu.zww.imserver;

import edu.ncu.zww.imserver.common.util.ApplicationContextUtil;
import edu.ncu.zww.imserver.controller.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class ImServerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ImServerApplication.class, args);
        ApplicationContextUtil.setApplicationContext(context);
        new Server().start();


        //new Test().start();

    }

}

