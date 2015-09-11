package com.droolsjbpm.examples.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kie.api.task.model.TaskSummary;

import com.droolsjbpm.examples.RetrieveTaskListWithPaging;

public class RetrieveTaskListWithPagingServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log =  Logger.getLogger(RetrieveTaskListWithPagingServlet.class.getCanonicalName());	
	private RetrieveTaskListWithPaging tl = new RetrieveTaskListWithPaging();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		/*
		 * parameters: userId, groupID, startOf, count
		 */
		
		List<TaskSummary> tasks =  tl.retrieveTaskListWithPaging("fspolti", null, 0, 10);
		for (TaskSummary task : tasks) {
			log.info("task found: " + task.getProcessId() + "--> id: "  + task.getId());
		}
	}
	
}









