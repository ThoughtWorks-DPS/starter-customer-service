package io.twdps.starter.custservice;

import io.twdps.starter.custservice.controller.CustomerInfoController;
import io.twdps.starter.custservice.service.CustomerInfoService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustserviceApplicationTests {

  @Autowired
  private CustomerInfoService customerInfoService;

  @Autowired
  private CustomerInfoController controller;

  @Test
  public void contextLoads() {
  }

}
