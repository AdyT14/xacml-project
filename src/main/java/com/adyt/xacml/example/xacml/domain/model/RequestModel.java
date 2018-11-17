package com.adyt.xacml.example.xacml.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestModel {

  private String action;
  private Long subjectId;
  private String resource;

}
