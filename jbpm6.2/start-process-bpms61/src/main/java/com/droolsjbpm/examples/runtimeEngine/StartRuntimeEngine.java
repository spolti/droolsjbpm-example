package com.droolsjbpm.examples.runtimeEngine;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;

import com.droolsjbpm.examples.configuration.ServerInformation;


public class StartRuntimeEngine {

	private Logger log =  Logger.getLogger(StartRuntimeEngine.class.getCanonicalName());
		
	@PostConstruct
	public RuntimeEngine initializeEngine() throws MalformedURLException {
		
		log.info("Initializing Engine with the following parameters..." );
		log.info("Remote Endpoint: " + ServerInformation.SERVER_URL.getServerInformationValue());
		log.info("Remote user: " + ServerInformation.USER.getServerInformationValue());
		log.info("Deployment ID: " + ServerInformation.DEPLOYMENT_ID.getServerInformationValue());
		
		RuntimeEngine engine = RemoteRuntimeEngineFactory.newRestBuilder()
				.addUrl(new URL(ServerInformation.SERVER_URL.getServerInformationValue()))
				.addUserName(ServerInformation.USER.getServerInformationValue()).addPassword(ServerInformation.PASSWORD.getServerInformationValue())
				.addDeploymentId(ServerInformation.DEPLOYMENT_ID.getServerInformationValue())
				.build();
		
		return engine;
	}
}