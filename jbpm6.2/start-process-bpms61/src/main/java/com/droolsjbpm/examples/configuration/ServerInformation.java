package com.droolsjbpm.examples.configuration;

public enum ServerInformation {

	DEPLOYMENT_ID("fspolti:fspolti-test:1.0"),
	SERVER_URL("http://localhost:8080/business-central"),
	USER("fspolti"),
	PASSWORD("Kt@25m69");
	
	private String ServerInformation;
		
	private ServerInformation(String ServerInformation){
		this.ServerInformation = ServerInformation;
	}
	
	public String getServerInformationValue(){
		return ServerInformation;
	}
}