package app.camunda.weather;

import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckWeatherDelegate implements JavaDelegate {

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    Random random = new Random();
    delegateExecution.setVariable("weatherOk", random.nextBoolean());
  }
}
