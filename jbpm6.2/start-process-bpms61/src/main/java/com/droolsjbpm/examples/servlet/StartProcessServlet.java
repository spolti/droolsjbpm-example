package com.droolsjbpm.examples.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kie.api.runtime.process.ProcessInstance;

import com.droolsjbpm.examples.StartProcess;
import com.droolsjbpm.examples.configuration.ServerInformation;

public class StartProcessServlet extends HttpServlet {

	static Logger log =  Logger.getLogger(StartProcessServlet.class.getCanonicalName());	
	
	private static final long serialVersionUID = 1L;
	
	private StartProcess sp = new StartProcess();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	    	
    	log.info("Trying to start a process on: " + ServerInformation.SERVER_URL.getServerInformationValue());;
    	ProcessInstance pr = sp.startProcess("project1.test");
    	log.info("Process Started: "+ pr.getProcessId());
    	
    }
}