package com.megvii.insight.service.finance.component;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Spencer Gibb
 */
@Component
public class SampleBackground {

  @Autowired
  private Tracer tracer;
  private Random random = new Random();

  @Async
  public void background() throws InterruptedException {
    int millis = this.random.nextInt(1000);
    Thread.sleep(millis);
    this.tracer.currentSpan().tag("background-sleep-millis", String.valueOf(millis));
  }

}
