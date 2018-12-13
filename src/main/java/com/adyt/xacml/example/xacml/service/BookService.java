package com.adyt.xacml.example.xacml.service;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface BookService {

    ResponseEntity<?> getBooks(HttpServletRequest request);

    ResponseEntity<?> getBooksByField(HttpServletRequest request);

    ResponseEntity<?> getAllBooks();
}
