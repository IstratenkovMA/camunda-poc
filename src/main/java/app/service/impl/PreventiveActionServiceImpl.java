package app.service.impl;

import app.dto.PreventiveAction;
import app.dto.PreventiveActionStatus;
import app.repository.PreventiveActionRepository;
import app.service.PreventiveActionService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreventiveActionServiceImpl implements PreventiveActionService {

  @Autowired
  private PreventiveActionRepository preventiveActionRepository;

  @Override
  @Transactional
  public void switchStatus(PreventiveAction action, Integer newStatus) {
    action.setStatus(newStatus);
    preventiveActionRepository.save(action);
  }

  @Override
  @Transactional
  public void switchStatus(Long preventiveActionId, Integer newStatus) {
    Optional<PreventiveAction> actionOptional = preventiveActionRepository.findById(preventiveActionId);
    if (actionOptional.isPresent()) {
      PreventiveAction preventiveAction = actionOptional.get();
      switchStatus(preventiveAction, newStatus);
    }
  }

  @Override
  @Transactional
  public PreventiveAction createPreventiveAction(String name) {
    PreventiveAction preventiveAction = new PreventiveAction();
    preventiveAction.setName(name);
    preventiveAction.setStatus(PreventiveActionStatus.NEW.id);
    preventiveAction.setActive(true);
    return preventiveActionRepository.save(preventiveAction);
  }

  @Override
  @Transactional
  public Boolean deletePreventiveActionById(Long preventiveActionId) {
    preventiveActionRepository.deleteById(preventiveActionId);
    return true;
  }
}
