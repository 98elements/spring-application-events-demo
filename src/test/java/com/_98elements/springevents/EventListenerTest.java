package com._98elements.springevents;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EventListenerTest {

  @Autowired
  protected ListenerTracker listenerTracker;

  @BeforeEach
  void resetListenerTracker() {
    listenerTracker.reset();
  }

}
