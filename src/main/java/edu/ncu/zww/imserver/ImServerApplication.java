package edu.ncu.zww.imserver;

import edu.ncu.zww.imserver.controller.Server;
import edu.ncu.zww.imserver.controller.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class ImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
        new Server().start();


        //new Test().start();

    }

}

