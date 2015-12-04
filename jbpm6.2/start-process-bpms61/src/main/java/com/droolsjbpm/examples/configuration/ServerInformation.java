package com.droolsjbpm.examples.configuration;

public enum ServerInformation {

	DEPLOYMENT_ID("org.kie.example:project1:1.0.0-SNAPSHOT"),
	SERVER_URL("http://localhost:8080/business-central"),
<<<<<<< HEAD
	USER("username"),
=======
	USER("fspolti"),
>>>>>>> 29db00380365e5d592d1faaad5e07f51409322f1
	PASSWORD("password");
	
	private String ServerInformation;
		
	private ServerInformation(String ServerInformation){
		this.ServerInformation = ServerInformation;
	}
	
	public String getServerInformationValue(){
		return ServerInformation;
	}
}
