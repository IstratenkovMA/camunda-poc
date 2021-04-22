package app.camunda.preventiveaction.delegate;

import app.service.PreventiveActionService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.feel.syntaxtree.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreventiveActionStatusSwitchDelegate implements JavaDelegate {

  @Autowired
  private PreventiveActionService preventiveActionService;

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    Integer status = Integer.valueOf(delegateExecution.getVariable("status").toString());
    String preventiveActionId = delegateExecution.getProcessBusinessKey();
    preventiveActionService.switchStatus(Long.valueOf(preventiveActionId), status);
  }
}
