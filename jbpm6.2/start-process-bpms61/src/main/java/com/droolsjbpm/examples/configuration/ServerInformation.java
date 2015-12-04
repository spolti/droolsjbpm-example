package com.droolsjbpm.examples.configuration;

public enum ServerInformation {

	DEPLOYMENT_ID("org.kie.example:project1:1.0.0-SNAPSHOT"),
	SERVER_URL("http://localhost:8080/business-central"),
	USER("username"),
	PASSWORD("password");
	
	private String ServerInformation;
		
	private ServerInformation(String ServerInformation){
		this.ServerInformation = ServerInformation;
	}
	
	public String getServerInformationValue(){
		return ServerInformation;
	}
}