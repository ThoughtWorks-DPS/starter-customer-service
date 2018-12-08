package io.twdps.starter.custservice.support;

import io.restassured.RestAssured;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class ApiTest implements BeforeAllCallback, AfterAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    Optional<String> serviceUrl = Optional
        .ofNullable(System.getProperty("serviceURL"));

    RestAssured.baseURI = serviceUrl.orElseThrow(
        () -> new AssertionFailedError("Cannot find system property 'serviceURL'"));
    RestAssured.useRelaxedHTTPSValidation();
  }

  @Override
  public void afterAll(ExtensionContext context) {
    RestAssured.reset();
  }
}
