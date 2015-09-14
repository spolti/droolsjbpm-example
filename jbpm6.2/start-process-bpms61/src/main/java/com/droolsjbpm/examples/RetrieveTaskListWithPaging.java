package com.droolsjbpm.examples;

import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

import org.kie.api.task.model.TaskSummary;
import org.kie.remote.jaxb.gen.GetTaskAssignedAsPotentialOwnerCommand;
import org.kie.remote.jaxb.gen.QueryFilter;

import com.droolsjbpm.examples.runtimeEngine.StartRuntimeEngine;

/*
 * Author @jesuino
 */

public class RetrieveTaskListWithPaging {
	
	static Logger log =  Logger.getLogger(RetrieveTaskList.class.getCanonicalName());
	private StartRuntimeEngine engine = new StartRuntimeEngine();

	public List<TaskSummary> retrieveTaskListWithPaging(String userId, String groupId, int offSet, int count) throws MalformedURLException {
		
		GetTaskAssignedAsPotentialOwnerCommand cmd = new GetTaskAssignedAsPotentialOwnerCommand();
		cmd.setUserId(userId);
		QueryFilter filter = new QueryFilter();
		filter.setOffset(offSet);
		filter.setCount(count);
		cmd.setFilter(filter);
		List<TaskSummary> result =  engine.initializeEngine().getKieSession().execute(cmd);
		return result;
	}
}