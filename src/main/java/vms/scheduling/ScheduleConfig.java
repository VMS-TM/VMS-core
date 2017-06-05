package vms.scheduletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import vms.services.absr.PropertyService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@ComponentScan("vms")
public class ScheduleConfig implements SchedulingConfigurer {

	@Autowired
	PropertyService propertyService;

	@Bean
	public MyScheduler getScheduler() {
		return new MyScheduler();
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.setScheduler (taskExecutor());
		scheduledTaskRegistrar.addCronTask (getScheduler(),propertyService.getPropertyByName("CronProperty").getValue());
	}
}
