package com._98elements.springevents.custom;

import com._98elements.springevents.EventListenerTest;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com._98elements.springevents.ListenerType.WITH_ACCEPT_AND_REJECT_EVENT_TYPES;
import static com._98elements.springevents.ListenerType.WITH_ACCEPT_EVENT_TYPE;
import static com._98elements.springevents.ListenerType.WITH_SPEL_SPECIAL_CONDITION;
import static org.assertj.core.api.Assertions.assertThat;

class CustomEventTests extends EventListenerTest {

  @Autowired
  private InvitationService invitationService;

  @Test
  void thatFiresEventListenerMatchingSpelCondition() {
    // given
    final String customerId = AccountService.SPECIAL_CUSTOMER;

    // when
    invitationService.accept(customerId);

    // then
    assertThat(listenerTracker.firedListeners()).containsExactly(
      WITH_SPEL_SPECIAL_CONDITION,
      WITH_ACCEPT_EVENT_TYPE,
      WITH_ACCEPT_AND_REJECT_EVENT_TYPES
    );
  }

  @Test
  void thatFiresEventListenerMatchingEventType() {
    // given
    final String customerId = RandomString.make();

    // when
    invitationService.accept(customerId);

    // then
    assertThat(listenerTracker.firedListeners()).containsExactly(
      WITH_ACCEPT_EVENT_TYPE,
      WITH_ACCEPT_AND_REJECT_EVENT_TYPES
    );
  }

  @Test
  void thatFiresEventListenerMatchingMultipleEventTypes() {
    // given
    final String customerId = RandomString.make();

    // when
    invitationService.reject(customerId);

    // then
    assertThat(listenerTracker.firedListeners()).containsExactly(
      WITH_ACCEPT_AND_REJECT_EVENT_TYPES
    );
  }

}
