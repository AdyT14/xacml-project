package com.adyt.xacml.example.xacml.service.impl;

import com.adyt.xacml.example.xacml.service.DataService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {


  @Override
  public ResponseEntity<?> getData(HttpServletRequest request) {

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
