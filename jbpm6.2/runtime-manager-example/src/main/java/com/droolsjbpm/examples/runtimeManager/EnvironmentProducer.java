package com.droolsjbpm.examples.runtimeManager;


import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.jbpm.runtime.manager.impl.RuntimeEnvironmentBuilder;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.cdi.qualifier.Singleton;

@ApplicationScoped
public class EnvironmentProducer {
		
	private Logger log =  Logger.getLogger(ProcessMain.class.getCanonicalName());
	
	@PersistenceUnit(unitName = "org.jbpm.domain")
    private EntityManagerFactory emf;

	
    @Produces
    @Singleton
    public RuntimeEnvironment produceEnvironment(EntityManagerFactory emf) {
    	
    	log.info("Building the RuntimeEnvironment");
    	
        RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get()
                .newDefaultBuilder()
                .entityManagerFactory(emf)
                .addAsset(ResourceFactory.newClassPathResource("org.jbpm.test.v1.0.bpmn2"),
                        ResourceType.BPMN2).get();
        return environment;
    }
    
    @Produces
    public EntityManagerFactory getEntityManagerFactory() {
        if (this.emf == null) {
            this.emf = Persistence.createEntityManagerFactory("org.jbpm.domain");
        }
        return this.emf;
    }   

}