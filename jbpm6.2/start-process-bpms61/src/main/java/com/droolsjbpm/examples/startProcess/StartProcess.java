package com.droolsjbpm.examples.startProcess;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;

public class StartProcess extends HttpServlet {
	

	static Logger log =  Logger.getLogger(StartProcess.class.getCanonicalName());	
	
	private static final long serialVersionUID = 1L;
	
	private static String DEPLOYMENT_ID = "example:example:1.0";
	private static String bcUrl = "http://localhost:8080/business-central";
	private static String USER = "user";
	private static String PASSWORD = "pass";	
	

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	    	
    	log.info("Trying to start a process on: " + bcUrl);
    	
    	try {
    		RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
    				.addUrl(new URL(bcUrl))
    				.addUserName(USER).addPassword(PASSWORD)
    				.addDeploymentId(DEPLOYMENT_ID)
    				.build();

    		KieSession ksession = engine.getKieSession();
    		ProcessInstance processInstance = ksession.startProcess("example.example");
    		log.info("Process Result: " + processInstance);
    		
    	} catch (Exception e) {
    		log.severe("Error: " + e);
    	}
    }
}