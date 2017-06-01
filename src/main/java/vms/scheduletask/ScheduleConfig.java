package vms.scheduletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import vms.services.absr.PropertyService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
@ComponentScan("vms")
public class ScheduleConfig implements SchedulingConfigurer {

	@Autowired
	PropertyService propertyService;

	@Bean
	public Task task() {
		return new Task();
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.setScheduler(taskExecutor());
		scheduledTaskRegistrar.addTriggerTask(
				() -> task().work(), //new Runnable and override method run()
				triggerContext -> { //new Trigger and override method nextExecutionTime()
					Calendar nextExecutionTime = new GregorianCalendar();
					Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
					if (lastActualExecutionTime == null) { nextExecutionTime.setTime(new Date()); } else { // Already executed, should be plus'ed
						nextExecutionTime.add(Calendar.MILLISECOND, Integer.parseInt((propertyService.getPropertyByName("rate").getValue())));
					}//you can get the value from wherever you want
					return nextExecutionTime.getTime();
				}
		);
	}
}
