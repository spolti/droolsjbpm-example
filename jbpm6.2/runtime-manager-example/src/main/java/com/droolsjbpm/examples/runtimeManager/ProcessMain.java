package com.droolsjbpm.examples.runtimeManager;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.services.api.RuntimeDataService;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.query.QueryFilter;
import org.kie.internal.runtime.manager.context.EmptyContext;

@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class ProcessMain extends HttpServlet {

	private Logger log =  Logger.getLogger(ProcessMain.class.getCanonicalName());
	
	private static final long serialVersionUID = 1L;
	
    private RuntimeManager runtimeManager;
	
	private RuntimeEngine runtimeEngine;
    
	@Inject
	private EnvironmentProducer producer;
	
	@Inject
	private RuntimeDataService runtimeDataService;

	@PersistenceUnit(unitName = "org.jbpm.domain")
	private EntityManagerFactory emf;
	

    @PostConstruct
    public void configure() {
    	log.info("Building the runtimeManager");
    	runtimeManager = RuntimeManagerFactory.Factory.get().newPerRequestRuntimeManager(producer.produceEnvironment(emf));
    	//runtimeManager = RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(producer.produceEnvironment(emf));
 
    }
    
	@PreDestroy
	public void destroy(){
		log.info ("Destroying the RuntimeManager");
		runtimeManager.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 * Get method to start a new jbpm process
	 */
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) {
		
		log.info("Trying to start process");
		startProcess();
            	
	}
	
	public void startProcess() {
					
		KieSession ksession = getKieSession();
		long pid = -1;
		
		try {
			log.info("Session perRequest Strategy: " + ksession);
	
			ProcessInstance p = ksession.startProcess("project1.test");
			pid = p.getId();
			log.info("Using runtimeManager: " + runtimeManager.getIdentifier());
			log.info("Process started, ID: " + p.getProcessId() + ", PID: " + pid);

			
		} catch (Exception e){
			log.severe("Error to start the process: " + e.getStackTrace());
			
		} finally {
			runtimeManager.disposeRuntimeEngine(runtimeEngine);
			
		}		
	}	

	public void taskListPotentionalOwner (){
		log.info("Pagination 10 tasks: " + runtimeDataService.getTasksAssignedAsPotentialOwner("john", new QueryFilter(0, 10)));
	}
	
	
	/*
	 * (non-Javadoc)
	 * returns the KieSession
	 */
	private KieSession getKieSession(){
		runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		KieSession ksession = runtimeEngine.getKieSession();
		return ksession;
	}
	
}