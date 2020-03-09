package com._98elements.springevents.transactional;

import com._98elements.springevents.EventListenerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static com._98elements.springevents.ListenerType.NON_TRANSACTIONAL_LISTENER;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL_LISTENER;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL_LISTENER_ONLY_AFTER_ROLLBACK;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL_LISTENER_WITH_FALLBACK_EXECUTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@EnableTransactionManagement
class TransactionalEventTests extends EventListenerTest {

  @Autowired
  private TransactionalPublisher service;

  @Test
  void thatRollbackIsIgnoredByStandardListener() {
    catchThrowable(() -> service.publishEventAndRollback());
    assertThat(listenerTracker.firedListeners()).containsExactly(
      NON_TRANSACTIONAL_LISTENER, // this may come as a surprise
      TRANSACTIONAL_LISTENER_ONLY_AFTER_ROLLBACK
    );
  }

  @Test
  void thatCommittedTransactionIsReceivedByAllListeners() {
    service.publishEventAndCommit();
    assertThat(listenerTracker.firedListeners()).containsExactlyInAnyOrder(
      NON_TRANSACTIONAL_LISTENER,
      TRANSACTIONAL_LISTENER,
      TRANSACTIONAL_LISTENER_WITH_FALLBACK_EXECUTION
    );
  }

  @Test
  void thatUsingTransactionalListenerWithoutTransactionalContextFails() {
    service.publishEventWithoutTransactionalContext();
    assertThat(listenerTracker.firedListeners()).containsExactlyInAnyOrder(
      NON_TRANSACTIONAL_LISTENER,
      TRANSACTIONAL_LISTENER_WITH_FALLBACK_EXECUTION
    );
  }
}
