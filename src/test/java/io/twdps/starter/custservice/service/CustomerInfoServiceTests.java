package io.twdps.starter.custservice.service;

import io.twdps.starter.custservice.repository.CustomerInfoRepository;
import io.twdps.starter.custservice.vo.CustomerInfo;
import io.twdps.starter.errors.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerInfoServiceTests {

  private CustomerInfo customerInfo;

  @Mock
  private CustomerInfoRepository customerInfoRepository;

  @InjectMocks
  private CustomerInfoService customerInfoService;

  @BeforeEach
  public void setup() {
    customerInfo = new CustomerInfo("company1", "44-455551", "request-100", LocalDateTime.now().toString(), "DEFAULT-POD");
  }

  @Test
  public void shouldReturnValidCustomerInfo() {

    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 1)).thenReturn(Mono.just(customerInfo));

    Mono<CustomerInfo> customerInfoMono = customerInfoService.getCustomerInfo("request-100", 1);
    customerInfoMono.subscribe(ci -> {
      assertEquals(customerInfo.getClientTraceId(), ci.getClientTraceId());
      assertEquals(customerInfo.getCompanyName(), ci.getCompanyName());
    });
  }

  @Test
  public void shouldReturnNotFound() {
    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 33)).thenThrow(ResourceNotFoundException.class);
    assertThrows(ResourceNotFoundException.class, () -> customerInfoService.getCustomerInfo("request-100", 33));
  }

}
