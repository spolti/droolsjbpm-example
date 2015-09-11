package com.droolsjbpm.examples;

import java.util.logging.Logger;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;

import com.droolsjbpm.examples.runtimeEngine.StartRuntimeEngine;


public class StartProcess {

	static Logger log =  Logger.getLogger(StartProcess.class.getCanonicalName());
	private StartRuntimeEngine engine = new StartRuntimeEngine();
	
	public ProcessInstance startProcess(String processDefinition) {
		
		ProcessInstance processInstance = null;
		
		try {

    		KieSession ksession = engine.initializeEngine().getKieSession();
    		processInstance = ksession.startProcess(processDefinition);
    		log.info("Process Result: " + processInstance);
    		
    		
    	} catch (Exception e) {
    		log.severe("Error: " + e);
    	}
		
		return processInstance;
	}
	
}