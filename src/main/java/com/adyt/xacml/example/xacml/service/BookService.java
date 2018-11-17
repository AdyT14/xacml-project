package com.adyt.xacml.example.xacml.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface BookService {

  ResponseEntity<?> getBooks(HttpServletRequest request);
}
