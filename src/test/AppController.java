package app.controller;

import app.dto.PreventiveAction;
//import app.service.CamundaPreventiveActionService;
import app.service.PreventiveActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {
//
//  @Autowired
//  private PreventiveActionService preventiveActionService;
//  @Autowired
//  private CamundaPreventiveActionService camundaPreventiveActionService;
//
//  //    @ApiOperation(value = "Just to test the sample test api of My App Service")
//  @GetMapping(path = "/preventiveAction/create", produces = "text/plain")
//  public String createPreventiveAction(@RequestParam String name) {
//    PreventiveAction preventiveAction = preventiveActionService.createPreventiveAction(name);
//    camundaPreventiveActionService.sendCreatePreventiveActionEvent(preventiveAction.getId().toString());
//    return "Status switch request processed";
//  }
//
//  @GetMapping(path = "/preventiveAction/switchStatus", produces = "text/plain")
//  public String publishObjectMessage(@RequestParam String id, @RequestParam String status) {
//    camundaPreventiveActionService.sendSwitchStatusEvent(id, Integer.valueOf(status));
//    return "Status update event was send successfully";
//  }
}
