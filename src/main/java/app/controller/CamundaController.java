package app.controller;

import app.service.CamundaService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class CamundaController {
  @Autowired
  private CamundaService camundaService;

    //    @ApiOperation(value = "Just to test the sample test api of My App Service")
  @GetMapping(path = "/preventiveAction/getAvailableTransitions", produces = "text/plain")
  public String createPreventiveAction(@RequestParam String processDefinitionId, @RequestParam String taskDefinitionKey) {
    List<UserTask> nextTasks = camundaService
        .getNextTasks(processDefinitionId, taskDefinitionKey, Collections.emptyMap());
    String taskNames = nextTasks.stream().map(task -> task.getName())
        .collect(Collectors.joining(" | "));
    return taskNames;
  }

  @GetMapping(path = "/preventiveAction/getAllTasksByStatus", produces = "text/plain")
  public String findAllPreventiveActionsByStatus(@RequestParam Integer statusId) {
    String result = camundaService.findAllPreventiveActionsByStatus(statusId).stream()
        .map(action -> action.getStatus().toString() + action.getId().toString())
        .collect(Collectors.joining(" | "));
    return result;
  }
}
