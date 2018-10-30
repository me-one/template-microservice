Server Monitor
=============

Monitor service provides [Hystrix Dashboard](https://github.com/Netflix-Skunkworks/hystrix-dashboard), which is a dashboard for monitoring micro services using Hystrix circuit breakers. The Hystrix Dashboard displays the health of each circuit breaker in an efficient manner. The [Netflix Turbine Stream Aggregator](https://github.com/Netflix/Turbine) connects to Hystrix-enabled servers and aggregates realtime streams from them.

### Running the Server Monitor

Pass the SERVER_CONFIG_PASSWORD from the [server-conf](/../server-conf/README.md) to access server-monitor.yml configuration file.

    $ java -jar insight-server-monitor/target/insight-server-monitor-0.0.1-SNAPSHOT.jar
           -DSERVER_CONFIG_PASSWORD=xxxx 

### Hystrix Dashboard and Monitoring Circuit Breaker status

View the [Hystrix Dashboard](https://cloud.spring.io/spring-cloud-netflix/multi/multi__hystrix_timeouts_and_ribbon_clients.html) with below URL:

http://localhost:8989/hystrix

##### For Monitoring with Hystrix Stream

Enter Host input: http://localhost:8301/actuator/hystrix.stream to monitor circuit breakers for service-finance application or  
                  http://localhost:8309/actuator/hystrix.stream for service-analytics application.

##### For Monitoring with Turbine Stream

Enter Host input: http://localhost:8989/turbine.stream?cluster=SERVICE-FINANCE  (or SERVICE-ANALYTICS to monitor service-analytics)  
Delay: 2000  
Title: My Dashboard Title  

Click "Monitor Stream" to view Hystrix Dashboard for the provided input stream.