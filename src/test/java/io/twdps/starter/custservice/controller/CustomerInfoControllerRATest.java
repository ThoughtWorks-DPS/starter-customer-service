package io.twdps.starter.custservice.controller;

import io.restassured.RestAssured;
import io.twdps.starter.custservice.repository.CustomerInfoRepository;
import io.twdps.starter.custservice.service.CustomerInfoService;
import io.twdps.starter.custservice.vo.CustomerInfo;
import io.twdps.starter.errors.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerInfoControllerRATest {

  private CustomerInfo customerInfo;

  @LocalServerPort
  private int port;

  @Mock
  private CustomerInfoRepository customerInfoRepository;

  @InjectMocks
  private CustomerInfoService customerInfoService;

  @Autowired
  private CustomerInfoController controller;

  @BeforeEach
  public void setup() {
    customerInfo = new CustomerInfo("company1", "44-455551", "request-100", LocalDateTime.now().toString(), "DEFAULT-POD");
  }

  @Test
  public void shouldReturnOKDefaultPayload() {
    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 1)).thenReturn(Mono.just(customerInfo));

    RestAssured.given().header("x-request-id", "request-100").
        when().
        get("http://localhost:" + port + "/customer/{id}", 1).
        then().statusCode(200).body("companyName", equalTo(customerInfo.getCompanyName()),
        "clientTraceId",equalTo(customerInfo.getClientTraceId()));

  }

  @Test
  public void return404NotFound() {
    Mockito.when(customerInfoRepository.getCustomerInfo("request-100", 33)).thenThrow(ResourceNotFoundException.class);
    RestAssured.given().header("HOST", "customerservice.dev.start.twdps.io").
        when().
        get("http://localhost:" + port + "/customer/{id}", 33).
        then().statusCode(404).
        body("errors[0].description",equalTo("404"),
            "errors[0].type",equalTo("Products Not Found for Customer ID:33"));

  }
}
