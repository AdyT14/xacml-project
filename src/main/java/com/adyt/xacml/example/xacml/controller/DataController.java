package com.adyt.xacml.example.xacml.controller;

import com.adyt.xacml.example.xacml.service.DataService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/data")
public class DataController {

  private final DataService dataService;

  public DataController(DataService dataService) {
    this.dataService = dataService;
  }

  @GetMapping
  public ResponseEntity<?> getData(HttpServletRequest request) {
    return dataService.getData(request);
  }

}
