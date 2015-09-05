package com.droolsjbpm.example.ejb;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.services.ejb.api.DeploymentServiceEJBRemote;
import org.jbpm.services.ejb.api.ProcessServiceEJBRemote;
import org.jbpm.services.ejb.api.RuntimeDataServiceEJBRemote;
import org.jbpm.services.ejb.api.UserTaskServiceEJBRemote;
import org.kie.internal.query.QueryContext;

//examples https://github.com/droolsjbpm/jbpm/blob/01e97a71a734e6864b5de6dc58a1503d29099422/jbpm-services/jbpm-services-ejb/jbpm-services-ejb-impl/src/test/java/org/jbpm/services/ejb/test/RuntimeDataServiceEJBIntegrationTest.java
//https://access.redhat.com/solutions/396853
@TransactionManagement(TransactionManagementType.BEAN)
public class EjbExampleRemote extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6515706066444669204L;

	Logger log = Logger.getLogger(EjbExampleRemote.class.getCanonicalName());

	@EJB(lookup = "ejb:/sample-war-ejb-app/ProcessServiceEJBImpl!org.jbpm.services.ejb.api.ProcessServiceEJBRemote")
	private ProcessServiceEJBRemote processService;

	@EJB(lookup = "ejb:/sample-war-ejb-app/UserTaskServiceEJBImpl!org.jbpm.services.ejb.api.UserTaskServiceEJBRemote")
	private UserTaskServiceEJBRemote userTaskService;

	@EJB(lookup = "ejb:/sample-war-ejb-app/RuntimeDataServiceEJBImpl!org.jbpm.services.ejb.api.RuntimeDataServiceEJBRemote")
	private RuntimeDataServiceEJBRemote runtimeDataService;

	@EJB(lookup = "ejb:/sample-war-ejb-app/DeploymentServiceEJBImpl!org.jbpm.services.ejb.api.DeploymentServiceEJBRemote")
	private DeploymentServiceEJBRemote deploymentService;
	
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Get method to start a new jbpm process using EJB
	 */
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) {
		
		log.info("Using Remote EJB lookup.");
		processService.startProcess("org.kie.example:project1:1.0.0-SNAPSHOT", "project1.EmailTest");

	}

	
}