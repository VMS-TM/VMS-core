package vms.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import vms.services.absr.PostSearchService;

public class Task implements Runnable {

	private static int count = 0;
	@Autowired
	PostSearchService postSearchService;

	public void work(){
		System.out.println("Task" + count++);
	}

	@Override
	public void run() {
		work();
	}
}
