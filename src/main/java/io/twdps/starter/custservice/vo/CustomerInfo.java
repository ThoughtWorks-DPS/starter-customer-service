package io.twdps.starter.custservice.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerInfo {

  private String companyName;
  private String taxId;
  private String clientTraceId;
  private String productRetrieveTime;
  private String podId;

}