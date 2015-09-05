package com.droolsjbpm.example.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.ejb.api.DeploymentServiceEJBLocal;
import org.jbpm.services.ejb.api.ProcessServiceEJBLocal;
import org.jbpm.services.ejb.api.RuntimeDataServiceEJBLocal;
import org.jbpm.services.ejb.api.UserTaskServiceEJBLocal;
import org.jbpm.services.ejb.impl.tx.TransactionalCommandServiceEJBImpl;
import org.jbpm.shared.services.impl.TransactionalCommandService;


@Startup
public class EjbExampleLocal extends HttpServlet{

	Logger log = Logger.getLogger(EjbExampleLocal.class.getCanonicalName());

	@EJB
	private DeploymentServiceEJBLocal localDeploymentService;
	
	@EJB
	private ProcessServiceEJBLocal localProcessService;

	@EJB
	private RuntimeDataServiceEJBLocal localRuntimeDataService;
	
	@EJB
    private UserTaskServiceEJBLocal localUserTaskService;

	@EJB(beanInterface=TransactionalCommandServiceEJBImpl.class)
	private TransactionalCommandService commandService;
	
	private DeploymentUnit deploymentUnit;
	private String GROUP_ID;
	private String ARTIFACT_ID;
	private String VERSION;
	
	@PostConstruct
	public void start() {
		log.info("Using local EJB.");
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Get method to start a new jbpm process using EJB
	 */
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) {
		
		log.info("Using Remote EJB lookup.");
		localProcessService.startProcess("org.kie.example:project1:1.0.0-SNAPSHOT", "project1.EmailTest");
	
	}
	
}