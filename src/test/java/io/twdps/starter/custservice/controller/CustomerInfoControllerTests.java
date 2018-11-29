package io.twdps.starter.custservice.controller;


import io.twdps.starter.custservice.repository.CustomerInfoRepository;
import io.twdps.starter.custservice.service.CustomerInfoService;
import io.twdps.starter.custservice.vo.CustomerInfo;
import io.twdps.starter.errors.errorhandling.domain.ErrorsContext;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerInfoControllerTests {

  @LocalServerPort
  private int port;

  private WebClient webClient;

  private CustomerInfo customerInfo;

  @Mock
  private CustomerInfoRepository customerInfoRepository;

  @InjectMocks
  private CustomerInfoService customerInfoService;

  @Autowired
  private CustomerInfoController customerInfoController;

  @BeforeEach
  public void setup() {
    customerInfo = new CustomerInfo("company1", "44-455551", "request-100", LocalDateTime.now().toString(), "DEFAULT-POD");
    webClient = WebClient.builder()
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("x-request-id", "request-100").build();
  }

  @Test
  public void testContext() {
    assertNotNull(customerInfoController, "Null Controller");
    assertNotNull(webClient, "null webclient");
  }

  @Test
  public void shouldReturnOKDefaultPayload() {
    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 1)).thenReturn(Mono.just(customerInfo));

    Mono<CustomerInfo> customerInfoMono = webClient.get()
        .uri("http://localhost:" + port + "/customer/1")
        .retrieve().bodyToMono(CustomerInfo.class);
    customerInfoMono.subscribe(ci -> {
      assertEquals(customerInfo.getCompanyName(), ci.getCompanyName());
      assertEquals(customerInfo.getClientTraceId(), ci.getClientTraceId());
    });
  }

  @Test
  public void shouldReturnNotFound() {
    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 33)).thenThrow(ResourceNotFoundException.class);

    webClient.get()
        .uri("http://localhost:" + port + "/customer/33")
        .exchange().subscribe(re -> {
            assertEquals(HttpStatus.NOT_FOUND, re.statusCode());
            re.bodyToMono(ErrorsContext.class)
            .subscribe(ec -> {
              assertEquals(1, ec.getErrors().size());
            });
    });
  }

}
