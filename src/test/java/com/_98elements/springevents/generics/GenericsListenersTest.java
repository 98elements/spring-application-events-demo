package com._98elements.springevents.generics;

import com._98elements.springevents.EventListenerTest;
import com._98elements.springevents.ListenerTracker;
import com._98elements.springevents.ListenerType;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

class GenericsListenersTest extends EventListenerTest {

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Test
  void thatHandlesGenericEventDespiteGenericsTypeErasure() {
    eventPublisher.publishEvent(new EntityCreatedEvent<>(new Invitation()));

    assertThat(listenerTracker.firedListeners()).containsExactly(
      ListenerType.GENERIC_INVITATION_CREATED
    );
  }

  @Value
  static class EntityCreatedEvent<T> implements ResolvableTypeProvider {
    T entity;

    @Override
    public ResolvableType getResolvableType() {
      final var instanceDifferentiator = ResolvableType.forClass(entity.getClass());
      return ResolvableType.forClassWithGenerics(getClass(), instanceDifferentiator);
    }
  }

  @Value
  static class Invitation {}

  @Value
  static class Person {}

  @TestConfiguration
  static class GenericListenersConfiguration {

    @Component
    @RequiredArgsConstructor
    private static class EntityCreatedListener {

      private final ListenerTracker listenerTracker;

      @EventListener
      void onInvitationCreated(final EntityCreatedEvent<Invitation> event) {
        listenerTracker.register(ListenerType.GENERIC_INVITATION_CREATED);
      }

      @EventListener
      void onPersonCreated(final EntityCreatedEvent<Person> event) {
        listenerTracker.register(ListenerType.GENERIC_PERSON_CREATED);
      }
    }
  }
}
