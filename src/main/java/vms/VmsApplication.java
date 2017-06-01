package vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import vms.scheduletask.ScheduleConfig;
import vms.scheduletask.Task;

@EnableScheduling
@SpringBootApplication
public class VmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {VmsApplication.class, ScheduleConfig.class},args);
	}
}
