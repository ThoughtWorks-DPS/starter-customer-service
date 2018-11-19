package io.twdps.starter.custservice.controller;

import io.twdps.starter.custservice.service.CustomerInfoService;
import io.twdps.starter.custservice.vo.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
public class CustomerInfoController {

  private static final Logger logger =
      LoggerFactory.getLogger(CustomerInfoController.class);

  @Autowired
  private CustomerInfoService customerInfoService;

  @GetMapping(value = "/customer/{customerId}", produces = "application/json")
  public Mono<ResponseEntity<CustomerInfo>> getCustomerInfo(@RequestHeader HttpHeaders headers,
                                                            @PathVariable int customerId) {
    processHeaders(headers);
    String traceId = MDC.get("x-request-id");
    logger.info("x-request-id:{}", traceId);
    logger.info("customer Id:{}", customerId);
    Mono<CustomerInfo> customerInfo = customerInfoService.getCustomerInfo(traceId, customerId);

    return customerInfo.map(c -> new ResponseEntity(c, HttpStatus.OK));
  }

  public void processHeaders(HttpHeaders headers) {
    Set<String> headerKeys = headers.keySet();

    String headerVal = headers.getFirst("end-user");
    if (headerVal != null) {
      MDC.put("end-user", headerVal);
    }
    for (String key : headerKeys) {
      if (key.startsWith("x-")) {
        MDC.put(key, headers.getFirst(key));
      }
    }
  }
}
