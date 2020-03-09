package com._98elements.springevents;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListenerTracker {

  private final List<ListenerType> firedListeners = new ArrayList<>();

  public void register(final ListenerType listenerType) {
    firedListeners.add(listenerType);
  }

  public List<ListenerType> firedListeners() {
    return firedListeners;
  }

  public void reset() {
    firedListeners.clear();
  }
}
