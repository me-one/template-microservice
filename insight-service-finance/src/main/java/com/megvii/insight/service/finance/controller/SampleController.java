package com.megvii.insight.service.finance.controller;

import brave.Span;
import brave.Tracer;
import com.megvii.insight.service.finance.component.SampleBackground;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @author Spencer Gibb
 */
@RestController
@RequestMapping("/zipkin")
public class SampleController implements
    ApplicationListener<ServletWebServerInitializedEvent> {

  private static final Log log = LogFactory.getLog(SampleController.class);

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private Tracer tracer;
  @Autowired
  private SampleBackground controller;

  private Random random = new Random();

  private int port;

  @RequestMapping("/")
  public String hi() throws InterruptedException {
    Thread.sleep(this.random.nextInt(1000));
    log.info("Home page");
    String s = this.restTemplate.getForObject("http://localhost:" + this.port + "/zipkin/hi2", String.class);
    return "hi/" + s;
  }

  @RequestMapping("/call")
  public Callable<String> call() {
    return new Callable<String>() {
      @Override
      public String call() throws Exception {
        int millis = SampleController.this.random.nextInt(1000);
        Thread.sleep(millis);
        Span currentSpan = SampleController.this.tracer.currentSpan();
        currentSpan.tag("callable-sleep-millis", String.valueOf(millis));
        return "async hi: " + currentSpan;
      }
    };
  }

  @RequestMapping("/async")
  public String async() throws InterruptedException {
    log.info("async");
    this.controller.background();
    return "ho";
  }

  @RequestMapping("/hi2")
  public String hi2() throws InterruptedException {
    log.info("hi2");
    int millis = this.random.nextInt(1000);
    Thread.sleep(millis);
    this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));
    return "hi2";
  }

  @RequestMapping("/traced")
  public String traced() throws InterruptedException {
    Span span = this.tracer.nextSpan().name("http:customTraceEndpoint").start();
    int millis = this.random.nextInt(1000);
    log.info(String.format("Sleeping for [%d] millis", millis));
    Thread.sleep(millis);
    this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));

    String s = this.restTemplate.getForObject("http://localhost:" + this.port
        + "/zipkin/call", String.class);
    span.finish();
    return "traced/" + s;
  }

  @RequestMapping("/start")
  public String start() throws InterruptedException {
    int millis = this.random.nextInt(1000);
    log.info(String.format("Sleeping for [%d] millis", millis));
    Thread.sleep(millis);
    this.tracer.currentSpan().tag("random-sleep-millis", String.valueOf(millis));
    String s = this.restTemplate.getForObject("http://localhost:" + this.port
        + "/zipkin/call", String.class);
    return "start/" + s;
  }

  @GetMapping("/greeting")
  public String greeting() {
    tracer.currentSpan().tag("service.greeting", "hello_world");
    return "Hello World";
  }

  @Override
  public void onApplicationEvent(ServletWebServerInitializedEvent event) {
    this.port = event.getSource().getPort();
  }
}