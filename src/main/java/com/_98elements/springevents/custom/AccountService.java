package com._98elements.springevents.custom;

import com._98elements.springevents.ListenerTracker;
import com._98elements.springevents.ListenerType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import static com._98elements.springevents.custom.InvitationService.InvitationAcceptedEvent;
import static com._98elements.springevents.custom.InvitationService.InvitationRejectedEvent;

@Service
@RequiredArgsConstructor
class AccountService {

  private final ListenerTracker listenerTracker;
  public static final String SPECIAL_CUSTOMER = "specialCustomer";

  @EventListener(condition = "#event.customerId == '" + SPECIAL_CUSTOMER + "'")
  @Order(1)
  void onMatchedSpelCondition(final InvitationAcceptedEvent event) {
    listenerTracker.register(ListenerType.WITH_SPEL_SPECIAL_CONDITION);
  }

  @EventListener
  @Order(2)
  void onSingleEventType(final InvitationAcceptedEvent event) {
    listenerTracker.register(ListenerType.WITH_ACCEPT_EVENT_TYPE);
  }

  @EventListener(classes = {InvitationAcceptedEvent.class, InvitationRejectedEvent.class})
  @Order(3)
  void onMultipleEventTypes() {
    listenerTracker.register(ListenerType.WITH_ACCEPT_AND_REJECT_EVENT_TYPES);
  }

}
