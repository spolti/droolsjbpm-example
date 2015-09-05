package com.droolsjbpm.examples;

import java.io.IOException;
import java.io.InputStream;

import org.drools.core.io.impl.UrlResource;
import org.drools.core.reteoo.KieComponentFactory;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestRules {

	private Person p = new Person();

	//@Test
	public void embedded() throws Exception {
		System.out.println("Using embedded approach.");

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();

		// this session name is declared in the kmodule.xm file
		KieSession session = kContainer.newKieSession("ksession-rules");

		p.setName("User");
		session.insert(p);
		session.fireAllRules();
		session.dispose();
	}

	@Test
	public void kieScanner() throws InterruptedException {
//		System.out.println("Using kieScanner approach.");
//
//		// load the jar from .m2
//		System.setProperty("kie.maven.settings.custom", "/home/fspolti/Downloads/Case01491584/settings.xml");
//		KieServices kieServices = KieServices.Factory.get();
//
//		ReleaseId releaseId = kieServices.newReleaseId("example", "test", "LATEST");
//		KieContainer kContainer = kieServices.newKieContainer(releaseId);
//
//		KieScanner kScanner = kieServices.newKieScanner(kContainer);
//
//		kScanner.scanNow();
//
//		KieBase kbase = kContainer.getKieBase();
//		KieSession ksession = kbase.newKieSession();
//		System.out.println("version: " + kContainer.getReleaseId().getVersion());
//
//		ksession.fireAllRules();
		
		
		KieContainer kieContainer = null;
		final String groupId = "example";
		final String artifactId = "test";
		KieServices kieServices = KieServices.Factory.get();
		kieContainer = kieServices.newKieContainer(kieServices.newReleaseId(groupId, artifactId, "LATEST"));
		
		KieBase kbase = kieContainer.getKieBase();
		KieSession ksession = kbase.newKieSession();
		ksession.fireAllRules();
//		
	}

	//@Test
	public void fromUrl() throws IOException {
		System.out.println("Using BC's maven URL approach.");

		String ARTIFACT_URL = "http://localhost:8080/business-central/maven2/example/example-load-rules/12/load-rules-12.jar";

		KieServices ks = KieServices.Factory.get();
		KieRepository kr = ks.getRepository();
		UrlResource urlResource = (UrlResource) ks.getResources().newUrlResource(ARTIFACT_URL);
		urlResource.setUsername("droolsUser");
		urlResource.setPassword("droolsPassword");
		urlResource.setBasicAuthentication("enabled");

		InputStream is = urlResource.getInputStream();

		KieModule kModule = kr.addKieModule(ks.getResources().newInputStreamResource(is));
		KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());

		KieBase kbase = kContainer.getKieBase();
		KieSession ksession = kbase.newKieSession();

		p.setName("User");
		ksession.insert(p);
		ksession.fireAllRules();
		ksession.dispose();

	}
}
