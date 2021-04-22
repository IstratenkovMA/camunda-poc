package app.service;

import app.dto.PreventiveAction;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.model.bpmn.instance.UserTask;

public interface CamundaService {
  List<UserTask> getNextTasks(String processDefinitionId, String taskDefinitionKey, Map<String, Object> vars);
  List<PreventiveAction> findAllPreventiveActionsByStatus(Integer statusId);
}
