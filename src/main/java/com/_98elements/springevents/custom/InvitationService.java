package com._98elements.springevents.custom;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class InvitationService {
  private final ApplicationEventPublisher publisher;

  void accept(final String customerId) {
    publisher.publishEvent(new InvitationAcceptedEvent(customerId));
  }

  void reject(final String customerId) {
    publisher.publishEvent(new InvitationRejectedEvent(customerId));
  }

  @Value
  static class InvitationAcceptedEvent {
    String customerId;
  }

  @Value
  static class InvitationRejectedEvent {
    String customerId;
  }
}
