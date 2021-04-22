package app.service.impl;

import app.service.CamundaService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CamundaServiceImpl implements CamundaService {
  @Autowired
  private TaskService taskService;
  @Autowired
  private RuntimeService runtimeService;

  private void testTaskApi() {
    taskService.getSubTasks();
    runtimeService.get
    Task task = taskService.createTaskQuery().processInstanceBusinessKey("")// <- prev action id
        .singleResult();
    taskService.createTaskQuery().taskParentTaskId(task.getId()).list();
    taskService.
  }

  private void examplesHeap() {
    Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    taskService.complete(task.getId());




    //todo ====================================================================================================


    RuntimeService runtimeService = processEngineRule.getRuntimeService();
    TaskService taskService = processEngineRule.getTaskService();

    runtimeService.startProcessInstanceByKey("Instantiating_Process");

    // now there should be two instances: one of Instantiating_Process and
    // one of the process that was instantiated by message from the service task
    assertEquals(2, runtimeService.createProcessInstanceQuery().count());

    // the instantiating process has advanced to the user task
    Task task = taskService.createTaskQuery().processDefinitionKey("Instantiating_Process").singleResult();
    Assert.assertNotNull(task);
    assertEquals("Wait in Instantiating Process", task.getName());

    // the message start event process has advanced to the user task
    task = taskService.createTaskQuery().processDefinitionKey("Message_Start_Process").singleResult();
    Assert.assertNotNull(task);
    assertEquals("Wait in Message Process", task.getName());


    //todo ====================================================================================================


    processEngine.getTaskService().complete(taskQuery.singleResult().getId(), variables);


    //todo ====================================================================================================


    BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("invoice")
        .name("BPMN API Invoice Process")
        .startEvent()
          .name("Invoice received")
        .userTask()
          .name("Assign Approver")
          .camundaAssignee("demo")
        .userTask()
          .id("approveInvoice")
          .name("Approve Invoice")
        .exclusiveGateway()
          .name("Invoice approved?")
          .gatewayDirection(GatewayDirection.Diverging)
        .condition("yes", "${approved}")
        .userTask()
          .name("Prepare Bank Transfer")
          .camundaFormKey("embedded:app:forms/prepare-bank-transfer.html")
          .camundaCandidateGroups("accounting")
        .serviceTask()
          .name("Archive Invoice")
          .camundaClass("org.camunda.bpm.example.invoice.service.ArchiveInvoiceService")
        .endEvent()
          .name("Invoice processed")
        .moveToLastGateway()
        .condition("no", "${!approved}")
        .userTask()
          .name("Review Invoice")
          .camundaAssignee("demo")
        .exclusiveGateway()
          .name("Review successful?")
          .gatewayDirection(GatewayDirection.Diverging)
        .condition("no", "${!clarified}")
        .endEvent()
          .name("Invoice not processed")
        .moveToLastGateway()
        .condition("yes", "${clarified}")
        .connectTo("approveInvoice")
        .done();

    //todo ====================================================================================================


  }
}
