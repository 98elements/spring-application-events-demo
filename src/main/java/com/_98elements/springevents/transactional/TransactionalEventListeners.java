package com._98elements.springevents.transactional;

import com._98elements.springevents.ListenerTracker;
import com._98elements.springevents.ListenerType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class TransactionalEventListeners {

  private final ListenerTracker listenerTracker;

  @EventListener
  void onEventWithoutTransaction(final TransactionalPublisher.Event event) {
    listenerTracker.register(ListenerType.NON_TRANSACTIONAL_LISTENER);
  }

  @TransactionalEventListener
  void onEventWithRequiredTransaction(final TransactionalPublisher.Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL_LISTENER);
  }

  @TransactionalEventListener(fallbackExecution = true)
  void onEventWithOptionalTransaction(final TransactionalPublisher.Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL_LISTENER_WITH_FALLBACK_EXECUTION);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  void onEventOnlyAfterRollback(final TransactionalPublisher.Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL_LISTENER_ONLY_AFTER_ROLLBACK);
  }
}
