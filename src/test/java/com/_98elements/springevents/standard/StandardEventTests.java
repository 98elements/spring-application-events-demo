package com._98elements.springevents.standard;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com._98elements.springevents.standard.StandardEventTests.StandardEventListenerConfiguration.StandardEventListeners;
import static com._98elements.springevents.standard.StandardEventTests.StandardEventListenerConfiguration.StandardEventListeners.STANDARD_EVENT_LISTENERS_BEAN_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StandardEventTests {

  @Autowired
  private StandardEventListeners listeners;

  @Test
  void thatContextRefreshedEventIsProcessed() {
    assertThat(listeners.numberOfBeans).isGreaterThan(1);
    assertThat(listeners.beanNames).contains(STANDARD_EVENT_LISTENERS_BEAN_NAME);
  }

  @TestConfiguration
  static class StandardEventListenerConfiguration {

    @Component(STANDARD_EVENT_LISTENERS_BEAN_NAME)
    @RequiredArgsConstructor
    static class StandardEventListeners {
      static final String STANDARD_EVENT_LISTENERS_BEAN_NAME = "standardEventListeners";

      private int numberOfBeans;
      private String[] beanNames;

      @EventListener
      void onContextRefreshed(final ContextRefreshedEvent event) {
        numberOfBeans = event.getApplicationContext().getBeanDefinitionCount();
        beanNames = event.getApplicationContext().getBeanDefinitionNames();
      }

    }
  }

}

