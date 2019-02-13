package io.twdps.starter.custservice;

import io.twdps.starter.custservice.support.ApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(ApiTest.class)
public class CustserviceIntegrationTests {

  private String testVersionHeader;
  private String testGatewayHost;

  @BeforeEach
  public void setup() {
    testVersionHeader = System.getProperty("testVersion");
    testGatewayHost = System.getProperty("testGatewayHost");
  }

  @Test
  public void endpointIsReachableAndCustomerExists() {

    // use test headers
    given().header("x-test", testVersionHeader).header("HOST", testGatewayHost).
        when().
        get("/customer/{id}", 3).
        then().statusCode(200).body("companyName", equalTo("company3"));
  }

  @Test
  public void endpointReturns404WhenNotFound() {
    given().header("x-test", testVersionHeader).header("HOST", testGatewayHost).
        when().
        get("/customer/{id}", 33).
        then().statusCode(404).
        body("errors[0].description",equalTo("404"),
            "errors[0].type",equalTo("Products Not Found for Customer ID:33"));

  }
}
