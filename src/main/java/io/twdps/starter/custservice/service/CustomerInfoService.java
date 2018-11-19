package io.twdps.starter.custservice.service;

import io.twdps.starter.custservice.repository.CustomerInfoRepository;
import io.twdps.starter.custservice.vo.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerInfoService {

  private static final Logger logger =
      LoggerFactory.getLogger(CustomerInfoService.class);

  @Autowired
  private CustomerInfoRepository customerInfoRepository;

  public Mono<CustomerInfo> getCustomerInfo(String traceId, int id) {
    return customerInfoRepository.getCustomerInfo(traceId, id);
  }


}
