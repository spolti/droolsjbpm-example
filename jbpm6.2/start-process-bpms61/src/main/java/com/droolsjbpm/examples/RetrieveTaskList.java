package com.droolsjbpm.examples;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;

import com.droolsjbpm.examples.runtimeEngine.StartRuntimeEngine;

public class RetrieveTaskList {


	static Logger log =  Logger.getLogger(RetrieveTaskList.class.getCanonicalName());
	private StartRuntimeEngine engine = new StartRuntimeEngine();
	
	public List<TaskSummary> retrieveTaskList(String user) throws MalformedURLException {

		TaskService taskService = engine.initializeEngine().getTaskService();
		List<TaskSummary> result = new ArrayList<TaskSummary>();
		String taskUserId = "fspolti";
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner(taskUserId, "en-UK");
		
		for (TaskSummary task : tasks) {
			  
			  result.add(task);
//			  taskService.claim(task.getProcessInstanceId(),taskUserId);
//			  taskService.start(task.getProcessInstanceId(),taskUserId);
//			  taskService.complete(task.getProcessInstanceId(),taskUserId, null);
			  
			
		}
		
		return result;
	}		
}