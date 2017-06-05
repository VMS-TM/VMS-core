package vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import vms.scheduling.ScheduleConfig;

@EnableScheduling
@SpringBootApplication
@ComponentScan("vms")
public class VmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {VmsApplication.class, ScheduleConfig.class},args);
	}
}
