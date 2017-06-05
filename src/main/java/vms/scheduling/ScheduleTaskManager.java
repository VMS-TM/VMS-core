package vms.scheduling;

import java.util.concurrent.Callable;

public interface ScheduleTaskManager {


	void scheduleTask(Runnable task, String key, String cronExpr);

	void stopTask(String key);

	void stopAllTask();

	void reScheduleTask(String key, String cronExpr);

	void removeTask();

}
