package io.twdps.starter.custservice.repository;

import io.twdps.starter.custservice.vo.CustomerInfo;
import io.twdps.starter.errors.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerInfoRepositoryTests {

  private CustomerInfo customerInfo;
  private CustomerInfoRepository customerInfoRepository;

  @BeforeEach
  public void setup() {
    customerInfo = new CustomerInfo("company1", "44-455551", "request-100", LocalDateTime.now().toString(), "DEFAULT-POD");
    customerInfoRepository = new CustomerInfoRepository();
  }

  @Test
  public void shouldReturnMatchingCustomerInfo() {
    Mono<CustomerInfo> returnedInfoMono = customerInfoRepository.getCustomerInfo("request-100", 1);
    returnedInfoMono.subscribe(ci -> {
      assertEquals(customerInfo.getClientTraceId(), ci.getClientTraceId());
      assertEquals(customerInfo.getCompanyName(), ci.getCompanyName());
    });
  }

  @Test
  public void shouldThrowNotFound() {
    assertThrows(ResourceNotFoundException.class, () -> customerInfoRepository.getCustomerInfo("request-100", 22));
  }
}
