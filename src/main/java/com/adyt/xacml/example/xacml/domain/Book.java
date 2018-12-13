package com.adyt.xacml.example.xacml.domain;

import javax.persistence.*;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "books")
@Data
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Lob
  private String description;

  private Double price;

  private String title;

  public static Set<String> getFields(){
    Set<String> fields = new HashSet<>();
    fields.add("description");
    fields.add("id");
    fields.add("price");
    fields.add("title");
    return fields;
  }

}
