package app.camunda.preventiveaction.delegate;

import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class EvaluatePerformanceJavaDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    //случайно оцениваем мероприятие эффективным или нет
    Random random = new Random();
    if(random.nextBoolean()) {
      delegateExecution.setVariable("status", 7);
    } else {
      delegateExecution.setVariable("status", 8);
    }
  }
}
