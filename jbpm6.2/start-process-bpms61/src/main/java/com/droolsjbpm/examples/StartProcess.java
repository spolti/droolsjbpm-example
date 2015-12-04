package com.droolsjbpm.examples;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.lang.model.util.ElementFilter;

import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.kie.api.command.Command;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.audit.AuditService;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.command.Context;
import org.kie.remote.client.jaxb.JaxbCommandsRequest;
import org.kie.remote.jaxb.gen.JaxbStringObjectPairArray;
import org.kie.remote.jaxb.gen.SetProcessInstanceVariablesCommand;
import org.kie.remote.jaxb.gen.StartProcessCommand;
import org.kie.remote.jaxb.gen.util.JaxbStringObjectPair;
import org.w3c.dom.Element;

import com.droolsjbpm.examples.runtimeEngine.StartRuntimeEngine;
import com.droolsjbpm.examples.servlet.MyType;

public class StartProcess {

	static Logger log = Logger.getLogger(StartProcess.class.getCanonicalName());
	private StartRuntimeEngine engine = new StartRuntimeEngine();

	public <T> ProcessInstance startProcess(String processDefinition) {
		
		ProcessInstance processInstance = null;
		
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("var1", "value1");
			params.put("var2", "value2");
			
    		KieSession ksession = engine.initializeEngine().getKieSession();
    		processInstance = ksession.startProcess(processDefinition, params);
    		    	
    		    		
    		log.info("Getting HT variables"); 	
    		AuditService taskAudit = engine.initializeEngine().getAuditService();
    	    List<VariableInstanceLog> varLog  = (List<VariableInstanceLog>) taskAudit.findVariableInstances(processInstance.getId());
    	    
            for( VariableInstanceLog origVarLog : varLog ) { 
                log.info("Variable name: " + origVarLog.getVariableId() + " Variable value: " + origVarLog.getValue());
            }
            
            log.info("Editing the task variable values");
			//ksession.execute(new SetProcessInstanceVariablesCommand());

     
            log.info("Get task State " + processInstance.getState());
            log.info("As I started the process we don't need to claim it, starting taskId: " + processInstance.getId());
            TaskService ts = engine.initializeEngine().getTaskService();
            ts.start(processInstance.getId(), "fspolti");

            List<VariableInstanceLog> varLog1  = (List<VariableInstanceLog>) taskAudit.findVariableInstances(processInstance.getId());
            
            for( VariableInstanceLog origVarLog : varLog1 ) { 
                log.info("Variable name: " + origVarLog.getVariableId() + " Variable value: " + origVarLog.getValue());
            }
    		
           // ts.complete(processInstance.getId(), "fspolti", params);
            
    	} catch (Exception e) {
    		log.severe("Error: " + e);
    	}
		
		return processInstance;
	}
	
}