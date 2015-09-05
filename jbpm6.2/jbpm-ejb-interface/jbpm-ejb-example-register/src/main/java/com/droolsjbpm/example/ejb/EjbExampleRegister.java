package com.droolsjbpm.example.ejb;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class EjbExampleRegister {

	Logger log = Logger.getLogger(EjbExampleRegister.class.getCanonicalName());

	@PersistenceContext(unitName = "org.jbpm.domain")
	private EntityManager entityManager;

	@Produces
	public EntityManager entityManager() {
		return entityManager;
	}

	@Produces
	public EntityManagerFactory entityManagerFactory() {
		return entityManager.getEntityManagerFactory();
	}
	
	@PostConstruct
	public void start() {

		log.info("Registering the EJB Services war.");
		System.setProperty("org.jbpm.ht.callback", "jaas");

	}
}