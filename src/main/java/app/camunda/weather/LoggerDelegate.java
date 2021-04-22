package app.camunda.weather;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerDelegate implements JavaDelegate {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    log.info("\n\n ... LoggerDelegate invoked by "
        + "processDefinitionId=" + delegateExecution.getProcessDefinitionId()
        + ", activityId=" + delegateExecution.getCurrentActivityId()
        + ", activityNameId=" + delegateExecution.getCurrentActivityName()
        + ", processInstanceId=" + delegateExecution.getProcessInstanceId()
        + ", processBusinessKey=" + delegateExecution.getProcessBusinessKey()
        + ", executionId=" + delegateExecution.getId()
        + " \n\n"
    );
  }
}
