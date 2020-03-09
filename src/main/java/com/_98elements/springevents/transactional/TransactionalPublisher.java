package com._98elements.springevents.transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class TransactionalPublisher {

  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public void publishEventAndRollback() {
    eventPublisher.publishEvent(new Event());
    throw new RuntimeException("rollback");
  }

  @Transactional
  public void publishEventAndCommit() {
    eventPublisher.publishEvent(new Event());
  }

  public void publishEventWithoutTransactionalContext() {
    eventPublisher.publishEvent(new Event());
  }

  static class Event {}
}
