package com.droolsjbpm.examples.correlationKey;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.internal.KieInternalServices;
import org.kie.internal.process.CorrelationAwareProcessRuntime;
import org.kie.internal.process.CorrelationKey;
import org.kie.internal.process.CorrelationKeyFactory;
import org.kie.services.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;


public class StartProcessRetrieveProcess {
	
	private static String USERNAME = "user";
	private static String PASSWORD = "password";
	
	@Test
	public void startProcessTest() throws MalformedURLException {
		CorrelationKeyFactory factory = KieInternalServices.Factory.get().newCorrelationKeyFactory();
		
		ProcessInstance processInstanceStart = startProcess(factory.newCorrelationKey("MyNewKey23"));
		Assert.assertNotNull(processInstanceStart);
		System.out.println("Starting Process: " + processInstanceStart);
		
		
		ProcessInstance processInstanceRetrieve = retrieveProcessInstance(factory.newCorrelationKey("MyNewKey23"));
		Assert.assertNotNull(processInstanceRetrieve);
		System.out.println("Retrieving Process: " + processInstanceRetrieve);
	}
	
	private static ProcessInstance startProcess(CorrelationKey correlationKey) throws MalformedURLException {
		String deploymentId = "org.kie.example:myProcess:1.0.0-SNAPSHOT";
		URL deploymentUrl = new URL("http://localhost:8080/business-central");
		
		RemoteRestRuntimeEngineFactory factory = RemoteRestRuntimeEngineFactory.newBuilder()
		           .addDeploymentId(deploymentId)
		           .addUrl(deploymentUrl)
		           .addUserName(USERNAME)
		           .addPassword(PASSWORD)
		           .build();

		RemoteRuntimeEngine engine = factory.newRuntimeEngine();
		
		TaskService taskService = engine.getTaskService();
		KieSession ksession = engine.getKieSession();		
		
		Map<String, Object> params = new HashMap<String, Object>(1);
		
		CorrelationAwareProcessRuntime capr = (CorrelationAwareProcessRuntime) ksession;
		return capr.startProcess("process1", correlationKey, params);
	}
	

	private static ProcessInstance retrieveProcessInstance(CorrelationKey correlationKey) throws MalformedURLException {
		String deploymentId = "org.kie.example:myProcess:1.0.0-SNAPSHOT";
		URL deploymentUrl = new URL("http://localhost:8080/business-central");
		
		RemoteRestRuntimeEngineFactory factory = RemoteRestRuntimeEngineFactory.newBuilder()
		           .addDeploymentId(deploymentId)
		           .addUrl(deploymentUrl)
		           .addUserName(USERNAME)
		           .addPassword(PASSWORD)
		           .build();
		
		
		RuntimeEngine engine = factory.newRuntimeEngine();
		
		TaskService taskService = engine.getTaskService();
		KieSession ksession = engine.getKieSession();

		CorrelationAwareProcessRuntime capr = (CorrelationAwareProcessRuntime) ksession;
		return capr.getProcessInstance(correlationKey);
	}
	
}