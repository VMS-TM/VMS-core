package vms.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import vms.services.absr.PostSearchService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class ScheduleTaskManagerImpl implements ScheduleTaskManager {

	private Map<String,ScheduledFuture> futures = new HashMap<>();

	@Autowired
	ThreadPoolTaskScheduler threadPoolTask;

	@Autowired
	PostSearchService postSearchService;

	@Override
	public void scheduleTask(Runnable task, String key, String cronExpr) {
		ScheduledFuture sf = threadPoolTask.schedule(task, new CronTrigger(cronExpr));
		futures.put(key,sf);
		System.out.println("Active count" + threadPoolTask.getActiveCount());
		System.out.println("Size of pool" + threadPoolTask.getPoolSize());
	}

	@Override
	public void stopTask(String key) {
		ScheduledFuture sf = futures.get(key);
		sf.cancel(true);
		System.out.println("Task stopped");
		System.out.println("Active count" + threadPoolTask.getActiveCount());
		System.out.println("Size of pool" + threadPoolTask.getPoolSize());
	}

	@Override
	public void stopAllTask() {

	}

	@Override
	public void reScheduleTask(String key, String cronExpr) {
		ScheduledFuture sf = futures.get(key);
		sf.cancel(true);

	}

	@Override
	public void removeTask() {

	}
}
