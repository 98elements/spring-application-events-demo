package com._98elements.springevents.transactional;

import com._98elements.springevents.ListenerTracker;
import com._98elements.springevents.ListenerType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com._98elements.springevents.transactional.TransactionalPublisher.*;

@Service
@RequiredArgsConstructor
class TransactionalEventListeners {

  private final ListenerTracker listenerTracker;

  @EventListener
  void onEventWithoutTransaction(final Event event) {
    listenerTracker.register(ListenerType.NON_TRANSACTIONAL);
  }

  @TransactionalEventListener
  void onEventWithRequiredTransaction(final Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL);
  }

  @TransactionalEventListener(fallbackExecution = true)
  void onEventWithOptionalTransaction(final Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL_WITH_FALLBACK_EXECUTION);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  void onEventOnlyAfterRollback(final Event event) {
    listenerTracker.register(ListenerType.TRANSACTIONAL_SET_ONLY_AFTER_ROLLBACK);
  }
}
