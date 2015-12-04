package com.droolsjbpm.examples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.kie.remote.jaxb.gen.JaxbStringObjectPairArray;
import org.kie.remote.jaxb.gen.SetProcessInstanceVariablesCommand;
import org.kie.remote.jaxb.gen.util.JaxbStringObjectPair;

public class Main {

	public static void main (String args[]) throws MalformedURLException {
		
		ProcessInstance processInstance = startProcess();
		AuditService as = getAuditService();
		
		List<VariableInstanceLog> varLog = varLog(as, processInstance.getId());
        for( VariableInstanceLog origVarLog : varLog ) { 
            System.out.println("Variable name: " + origVarLog.getVariableId() + " Variable value: " + origVarLog.getValue());
        }
		
        //task start
        getTs().start(processInstance.getId(), "filippe");
       
        JaxbStringObjectPairArray varJaxbList = new JaxbStringObjectPairArray();
        JaxbStringObjectPair var1Jaxb = new JaxbStringObjectPair("var1", "newValueVar1");
        JaxbStringObjectPair var2Jaxb = new JaxbStringObjectPair("var2", "newValueVar2");
        SetProcessInstanceVariablesCommand cmd = new SetProcessInstanceVariablesCommand();
        
        cmd.setProcessInstanceId(processInstance.getId());
        varJaxbList.getItems().add(var1Jaxb);
        varJaxbList.getItems().add(var2Jaxb);
        cmd.setVariables(varJaxbList);
        
        engine().getKieSession().execute(cmd);
        getTs().complete(processInstance.getId(), "filippe", null);

	}
	
	/**
	 * @return a ProcessInstance
	 * @throws MalformedURLException
	 */
	public static ProcessInstance startProcess () throws MalformedURLException {
		
		String processDefinition = "project1.test";
		// populating the process variables
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("var1", "value1");
		params.put("var2", "value2");
		KieSession ksession = engine().getKieSession();
		return ksession.startProcess(processDefinition, params);
		
	}
	
	/**
	 * @return the AuditService
	 * @throws MalformedURLException
	 */
	public static AuditService getAuditService () throws MalformedURLException {
		return engine().getAuditService();
	}
	
	/**
	 * @param AuditService as
	 * @param ProcessInstance id
	 * @return the Process Variables of the given process
	 */
	@SuppressWarnings("unchecked")
	public static List<VariableInstanceLog> varLog (AuditService as, long id) {
		return (List<VariableInstanceLog>) as.findVariableInstances(id);
	}
	
	/**
	 * @return the Taskservice
	 * @throws MalformedURLException
	 */
	public static TaskService getTs () throws MalformedURLException {
		return engine().getTaskService();
	}
	
	
	/**
	 * @return the RuntimeEngine
	 * @throws MalformedURLException
	 */
	public static RuntimeEngine engine () throws MalformedURLException{
		RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(new URL("http://localhost:8080/business-central"))
				.addUserName("username").addPassword("password")
				.addDeploymentId("org.kie.example:project1:1.0.0-SNAPSHOT")
				.build();
		return engine;
	}
	
}