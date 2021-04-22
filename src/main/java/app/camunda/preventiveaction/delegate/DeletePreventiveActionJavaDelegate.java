package app.camunda.preventiveaction.delegate;

import app.service.PreventiveActionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletePreventiveActionJavaDelegate implements JavaDelegate {

  private static final Logger log = LoggerFactory.getLogger(DeletePreventiveActionJavaDelegate.class);

  @Autowired
  private PreventiveActionService preventiveActionService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    Long preventiveActionId = Long.valueOf(delegateExecution.getProcessBusinessKey());
    preventiveActionService.deletePreventiveActionById(preventiveActionId);
    logState(delegateExecution);
  }

  private void logState(DelegateExecution delegateExecution) {
    log.info("\n....preventive action deleted by camunda user task"
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
