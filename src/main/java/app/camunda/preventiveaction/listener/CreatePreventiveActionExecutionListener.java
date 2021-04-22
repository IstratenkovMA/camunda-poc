package app.camunda.preventiveaction.listener;

import app.dto.PreventiveAction;
import app.service.PreventiveActionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePreventiveActionExecutionListener implements ExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(CreatePreventiveActionExecutionListener.class);

  @Autowired
  private PreventiveActionService preventiveActionService;

  @Override
  public void notify(DelegateExecution delegateExecution) {
    Integer statusId = 1;
    delegateExecution.setVariable("status", statusId);
    PreventiveAction preventiveAction = preventiveActionService
        .createPreventiveAction("TestName");
    delegateExecution.setProcessBusinessKey(preventiveAction.getId().toString());
    logState(delegateExecution);
  }

  private void logState(DelegateExecution delegateExecution) {
    log.info("\n\n ... LoggerDelegate invoked by "
        + "\n processDefinitionId=" + delegateExecution.getProcessDefinitionId()
        + "\n activityId=" + delegateExecution.getCurrentActivityId()
        + "\n activityNameId=" + delegateExecution.getCurrentActivityName()
        + "\n processInstanceId=" + delegateExecution.getProcessInstanceId()
        + "\n executionId=" + delegateExecution.getId()
        + "\n preventiveActionId=" + delegateExecution.getProcessBusinessKey()
        + "\n preventiveActionStatus=" + delegateExecution.getVariable("status")
        + " \n\n"
    );
  }
}
