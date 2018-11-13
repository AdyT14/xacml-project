package com.adyt.xacml.example.xacml.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface DataService {

  ResponseEntity<?> getData(HttpServletRequest request);
}
