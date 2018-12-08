package io.twdps.starter.custservice;

import io.twdps.starter.custservice.support.ApiTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(ApiTest.class)
public class CustserviceIntegrationTests {

  @Test
  public void endpointIsReachableAndCustomerExists() {

    // use test headers
    given().header("x-test", "0.0.7").header("HOST", "customerservice.dev.start.twdps.io").
        when().
        get("/customer/{id}", 3).
        then().statusCode(200).body("companyName", equalTo("company3"));
  }
}
