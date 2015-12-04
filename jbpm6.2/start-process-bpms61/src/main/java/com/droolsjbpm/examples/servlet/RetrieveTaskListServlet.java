package com.droolsjbpm.examples.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kie.api.task.model.TaskSummary;

import com.droolsjbpm.examples.RetrieveTaskList;

public class RetrieveTaskListServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	
	static Logger log =  Logger.getLogger(RetrieveTaskListServlet.class.getCanonicalName());	
	private RetrieveTaskList tl = new RetrieveTaskList();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		List<TaskSummary> tasks =  tl.retrieveTaskList("fspolti");
		for (TaskSummary task : tasks) {
			log.info("task found: " + task.getProcessId() + "--> id: "  + task.getId());
		}
	}
	
}