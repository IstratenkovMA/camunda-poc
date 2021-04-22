package app.service.impl;

import app.dto.PreventiveActionStatus;
import app.service.CamundaPreventiveActionService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaPreventiveActionServiceImpl implements CamundaPreventiveActionService {

  @Autowired
  private RuntimeService runtimeService;

  @Override
  public void sendSwitchStatusEvent(String preventiveActionId, Integer statusId) {
    String statusName = PreventiveActionStatus.parseOrder(statusId).toString();
    runtimeService
        .createMessageCorrelation("ChangeActionStatusTo" + statusName)
        .processInstanceBusinessKey(preventiveActionId)
        .setVariable("actionId", preventiveActionId)
        .setVariable("status", statusId)
        .correlate();
  }

  @Override
  public void sendCreatePreventiveActionEvent(String preventiveActionId) {
    runtimeService
        .createMessageCorrelation("CreatePreventiveAction")
        .processInstanceBusinessKey(preventiveActionId)
        .setVariable("actionId", preventiveActionId)
        .setVariable("status", 1)
        .correlate();
  }
}
