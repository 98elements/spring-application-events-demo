package com._98elements.springevents.transactional;

import com._98elements.springevents.EventListenerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com._98elements.springevents.ListenerType.NON_TRANSACTIONAL;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL_SET_ONLY_AFTER_ROLLBACK;
import static com._98elements.springevents.ListenerType.TRANSACTIONAL_WITH_FALLBACK_EXECUTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TransactionalEventTests extends EventListenerTest {

  @Autowired
  private TransactionalPublisher service;

  @Test
  void thatRollbackIsIgnoredByStandardListener() {
    catchThrowable(() -> service.publishEventAndRollback());
    assertThat(listenerTracker.firedListeners()).containsExactly(
      NON_TRANSACTIONAL, // this may come as a surprise
      TRANSACTIONAL_SET_ONLY_AFTER_ROLLBACK
    );
  }

  @Test
  void thatCommittedTransactionIsReceivedByAllListenersButExplicitlySetAfterRollback() {
    service.publishEventAndCommit();
    assertThat(listenerTracker.firedListeners()).containsExactlyInAnyOrder(
      NON_TRANSACTIONAL,
      TRANSACTIONAL,
      TRANSACTIONAL_WITH_FALLBACK_EXECUTION
    );
  }

  @Test
  void thatEventWithoutTransactionalContextIsNotProcessedByTransactionalListeners() {
    service.publishEventWithoutTransactionalContext();
    assertThat(listenerTracker.firedListeners()).containsExactlyInAnyOrder(
      NON_TRANSACTIONAL,
      TRANSACTIONAL_WITH_FALLBACK_EXECUTION
    );
  }
}
