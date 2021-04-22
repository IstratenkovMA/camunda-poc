package app.service;

public interface CamundaPreventiveActionService {

  void sendSwitchStatusEvent(String preventiveActionId, Integer statusId);

  void sendCreatePreventiveActionEvent(String preventiveActionId);
}
