package app.service;

import app.dto.PreventiveAction;

public interface PreventiveActionService {
  void switchStatus(PreventiveAction action, Integer newStatus);
  void switchStatus(Long preventiveActionId, Integer newStatus);

  PreventiveAction createPreventiveAction(String name);
  Boolean deletePreventiveActionById(Long preventiveActionId);
}
