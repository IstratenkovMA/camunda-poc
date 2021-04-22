package app.camunda.preventiveaction.listener;

import app.service.PreventiveActionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwitchStatusExecutionListener implements ExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(SwitchStatusExecutionListener.class);

  @Autowired
  private PreventiveActionService preventiveActionService;

  @Override
  public void notify(DelegateExecution delegateExecution) throws Exception {
    Long preventiveActionId = Long.valueOf(delegateExecution.getProcessBusinessKey());
    Integer statusId = Integer.valueOf(delegateExecution.getVariable("status").toString());
    preventiveActionService.switchStatus(preventiveActionId, statusId);
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
