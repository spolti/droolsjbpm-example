package com.droolsjbpm.examples.runtimeManager;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.jbpm.services.api.model.ProcessDefinition;
import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.jbpm.services.api.model.UserTaskInstanceDesc;
import org.jbpm.services.api.model.VariableDesc;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.query.QueryContext;
import org.kie.internal.query.QueryFilter;
import org.kie.internal.task.api.AuditTask;


//to solve:
//org.jboss.weld.exceptions.DeploymentException: WELD-001408 Unsatisfied dependencies for type [DeploymentService] with qualifiers [@Default] at injection point [[field] @Inject private org.jbpm.kie.services.impl.form.FormProviderServiceImpl.deploymentService]

@ApplicationScoped
public class CustomRuntimeDataService implements RuntimeDataService {

	public List<AuditTask> getAllAuditTask(String arg0, QueryFilter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public NodeInstanceDesc getNodeInstanceForWorkItem(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessDefinition getProcessById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<String> getProcessIds(String arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstanceDesc getProcessInstanceById(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeInstanceDesc> getProcessInstanceFullHistory(
			long arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeInstanceDesc> getProcessInstanceFullHistoryByType(
			long arg0, EntryType arg1, QueryContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeInstanceDesc> getProcessInstanceHistoryActive(
			long arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<NodeInstanceDesc> getProcessInstanceHistoryCompleted(
			long arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstances(QueryContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstances(
			List<Integer> arg0, String arg1, QueryContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstancesByDeploymentId(
			String arg0, List<Integer> arg1, QueryContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstancesByProcessDefinition(
			String arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstancesByProcessDefinition(
			String arg0, List<Integer> arg1, QueryContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstancesByProcessId(
			List<Integer> arg0, String arg1, String arg2, QueryContext arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessInstanceDesc> getProcessInstancesByProcessName(
			List<Integer> arg0, String arg1, String arg2, QueryContext arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessDefinition> getProcesses(QueryContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessDefinition> getProcessesByDeploymentId(
			String arg0, QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessDefinition getProcessesByDeploymentIdProcessId(String arg0,
			String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ProcessDefinition> getProcessesByFilter(String arg0,
			QueryContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserTaskInstanceDesc getTaskById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserTaskInstanceDesc getTaskByWorkItemId(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsBusinessAdministrator(
			String arg0, QueryFilter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String arg0,
			QueryFilter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String arg0,
			List<String> arg1, QueryFilter arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsPotentialOwner(String arg0,
			List<String> arg1, List<Status> arg2, QueryFilter arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByExpirationDateOptional(
			String arg0, List<Status> arg1, Date arg2, QueryFilter arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksAssignedAsPotentialOwnerByStatus(
			String arg0, List<Status> arg1, QueryFilter arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> getTasksByProcessInstanceId(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksByStatusByProcessInstanceId(Long arg0,
			List<Status> arg1, QueryFilter arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksOwned(String arg0, QueryFilter arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksOwnedByExpirationDateOptional(String arg0,
			List<Status> arg1, Date arg2, QueryFilter arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTasksOwnedByStatus(String arg0,
			List<Status> arg1, QueryFilter arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<VariableDesc> getVariableHistory(long arg0, String arg1,
			QueryContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<VariableDesc> getVariablesCurrentState(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
}