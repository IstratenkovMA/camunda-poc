package app.service.impl;

import app.dto.PreventiveAction;
import app.dto.PreventiveActionStatus;
import app.repository.PreventiveActionRepository;
import app.service.CamundaService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.javax.el.ExpressionFactory;
import org.camunda.bpm.engine.impl.juel.ExpressionFactoryImpl;
import org.camunda.bpm.engine.impl.juel.SimpleContext;
import org.camunda.bpm.engine.impl.juel.SimpleResolver;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamundaServiceImpl implements CamundaService {

  private static final Logger log = LoggerFactory.getLogger(CamundaServiceImpl.class);

  @Autowired
  private TaskService taskService;
  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private RepositoryService repositoryService;
  @Autowired
  private PreventiveActionRepository preventiveActionRepository;


  @Override
  public List<PreventiveAction> findAllPreventiveActionsByStatus(Integer statusId) {
    String string = PreventiveActionStatus.parseOrder(statusId).toString();
    List<Task> tasksInStatus = taskService.createTaskQuery().taskId(string).list();
    Set<String> processInstancesIds = tasksInStatus.stream().map(task -> task.getProcessInstanceId())
        .collect(Collectors.toSet());
    List<ProcessInstance> processes = runtimeService.createProcessInstanceQuery()
        .processInstanceIds(processInstancesIds).list();
    List<Long> preventiveActionsIds = processes.stream().map(process -> process.getBusinessKey())
        .map(businessKey -> Long.valueOf(businessKey)).collect(
            Collectors.toList());

    Iterable<PreventiveAction> preventiveActions = preventiveActionRepository
        .findAllById(preventiveActionsIds);
    return StreamSupport.stream(preventiveActions.spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public List<UserTask> getNextTasks(String processDefinitionId, String taskDefinitionKey, Map<String, Object> vars) {
    BpmnModelInstance modelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
    ModelElementInstance instance = modelInstance.getModelElementById(taskDefinitionKey);
    FlowNode flowNode = (FlowNode)instance;
    return getOutgoingTask(flowNode, vars);
  }
  private List<UserTask> getOutgoingTask(FlowNode node, Map<String, Object> vars) {
    List<UserTask> possibleTasks = new ArrayList<>();
    for(SequenceFlow sf: node.getOutgoing()) {
      if (sf.getName() != null) {
        log.info("Entering flow node {}", sf.getName());
      }
      boolean next = true;
      if (sf.getConditionExpression() != null) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext(new SimpleResolver());

        log.info("Generating map vars {}", vars.toString());
//        for (Map.Entry<String, Object> v : vars.entrySet()) {
//          if(v.getValue() instanceof Boolean) {
//            factory.createValueExpression(context, "${"+ v.getKey() +"}", Boolean.class).setValue(context, v.getValue());
//          }else if(v.getValue() instanceof java.util.Date) {
//            factory.createValueExpression(context, "${"+ v.getKey() +"}", java.util.Date.class).setValue(context, v.getValue());
//          }else {
//            factory.createValueExpression(context, "${"+ v.getKey() +"}", String.class).setValue(context, v.getValue());
//          }
//        }
//        ValueExpression expr1 = factory.createValueExpression(context, sf.getConditionExpression().getTextContent(), boolean.class);
//
//        next = (Boolean)expr1.getValue(context);
//        log.info("Evaluating expression {} to result {}", sf.getConditionExpression().getTextContent(), expr1.getValue(context));

      }

      if (next && sf.getTarget() != null) {
        if (sf.getTarget() instanceof UserTask) {
          log.info("Found next user task {}", sf.getTarget().getName());
          possibleTasks.add((UserTask)sf.getTarget());
          break;
        }
        //fixme maybe
//        possibleTasks.addAll(getOutgoing(sf.getTarget(), vars));
        possibleTasks.addAll(getOutgoingTask(sf.getTarget(), vars));
      }

    }
    return possibleTasks;
  }

//
//  private void examplesHeap() {
//    Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
//    taskService.complete(task.getId());
//
//
//
//
//    //todo ====================================================================================================
//
//
//    RuntimeService runtimeService = processEngineRule.getRuntimeService();
//    TaskService taskService = processEngineRule.getTaskService();
//
//    runtimeService.startProcessInstanceByKey("Instantiating_Process");
//
//    // now there should be two instances: one of Instantiating_Process and
//    // one of the process that was instantiated by message from the service task
//    assertEquals(2, runtimeService.createProcessInstanceQuery().count());
//
//    // the instantiating process has advanced to the user task
//    Task task = taskService.createTaskQuery().processDefinitionKey("Instantiating_Process").singleResult();
//    Assert.assertNotNull(task);
//    assertEquals("Wait in Instantiating Process", task.getName());
//
//    // the message start event process has advanced to the user task
//    task = taskService.createTaskQuery().processDefinitionKey("Message_Start_Process").singleResult();
//    Assert.assertNotNull(task);
//    assertEquals("Wait in Message Process", task.getName());
//
//
//    //todo ====================================================================================================
//
//
//    processEngine.getTaskService().complete(taskQuery.singleResult().getId(), variables);
//
//
//    //todo ====================================================================================================
//
//
//    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("invoice")
//        .name("BPMN API Invoice Process")
//        .startEvent()
//          .name("Invoice received")
//        .userTask()
//          .name("Assign Approver")
//          .camundaAssignee("demo")
//        .userTask()
//          .id("approveInvoice")
//          .name("Approve Invoice")
//        .exclusiveGateway()
//          .name("Invoice approved?")
//          .gatewayDirection(GatewayDirection.Diverging)
//        .condition("yes", "${approved}")
//        .userTask()
//          .name("Prepare Bank Transfer")
//          .camundaFormKey("embedded:app:forms/prepare-bank-transfer.html")
//          .camundaCandidateGroups("accounting")
//        .serviceTask()
//          .name("Archive Invoice")
//          .camundaClass("org.camunda.bpm.example.invoice.service.ArchiveInvoiceService")
//        .endEvent()
//          .name("Invoice processed")
//        .moveToLastGateway()
//        .condition("no", "${!approved}")
//        .userTask()
//          .name("Review Invoice")
//          .camundaAssignee("demo")
//        .exclusiveGateway()
//          .name("Review successful?")
//          .gatewayDirection(GatewayDirection.Diverging)
//        .condition("no", "${!clarified}")
//        .endEvent()
//          .name("Invoice not processed")
//        .moveToLastGateway()
//        .condition("yes", "${clarified}")
//        .connectTo("approveInvoice")
//        .done();
//
//    //todo ====================================================================================================
//
//
//  }
}
