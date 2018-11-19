package io.twdps.starter.custservice.repository;

import io.twdps.starter.custservice.vo.CustomerInfo;
import io.twdps.starter.errors.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class CustomerInfoRepository {

  private static final Logger logger =
      LoggerFactory.getLogger(CustomerInfoRepository.class);

  @Value("${POD_NAME:POD_DEFAULT}")
  private String podId;

  public Mono<CustomerInfo> getCustomerInfo(String traceId, int id) {
    if (id < 20) {
      LocalDateTime localDateTime = LocalDateTime.now();
      return Mono.just(new CustomerInfo("company" + id, "44-45555" + id, traceId,
          localDateTime.toString(), podId));
    } else {
      //how to handle not found
      throw new ResourceNotFoundException("Products Not Found for Customer ID:" + id, "404");
    }

  }
}
