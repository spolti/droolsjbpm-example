package com.droolsjbpm.examples.runtimeManager;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.kie.api.runtime.manager.RuntimeManager;


//to solve:
//org.jboss.weld.exceptions.DeploymentException: WELD-001408 Unsatisfied dependencies for type [DeploymentService] with qualifiers [@Default] at injection point [[field] @Inject private org.jbpm.kie.services.impl.form.FormProviderServiceImpl.deploymentService]

@ApplicationScoped
public class CustomDeploymentService implements org.jbpm.services.api.DeploymentService {

	public void deploy(DeploymentUnit arg0) {
		// TODO Auto-generated method stub
		
	}

	public DeployedUnit getDeployedUnit(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<DeployedUnit> getDeployedUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	public RuntimeManager getRuntimeManager(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void undeploy(DeploymentUnit arg0) {
		// TODO Auto-generated method stub
		
	}

	public void activate(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deactivate(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean isDeployed(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}