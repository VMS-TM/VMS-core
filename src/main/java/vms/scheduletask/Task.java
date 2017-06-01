package vms.scheduletask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import vms.services.absr.PostSearchService;

public class Task {

	@Autowired
	PostSearchService postSearchService;

	public void work(){
		System.out.println("Task");
	}
}
