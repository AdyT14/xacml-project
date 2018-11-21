package com.adyt.xacml.example.xacml.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class RequestModel {

  private String action;
  private Long subjectId;
  private String resource;
  private Set<String> fields = new HashSet<>();

}
