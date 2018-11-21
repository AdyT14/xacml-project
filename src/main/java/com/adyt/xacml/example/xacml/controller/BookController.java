package com.adyt.xacml.example.xacml.controller;

import com.adyt.xacml.example.xacml.service.BookService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public ResponseEntity<?> getData(HttpServletRequest request) {
    return bookService.getBooks(request);
  }

  @GetMapping("/byfield")
  public ResponseEntity<?> getDataByField(HttpServletRequest request){
    return bookService.getBooksByField(request);
  }

}
