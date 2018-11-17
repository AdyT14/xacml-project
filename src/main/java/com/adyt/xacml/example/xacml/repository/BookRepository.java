package com.adyt.xacml.example.xacml.repository;

import com.adyt.xacml.example.xacml.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by AdyT on 11/17/2018.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
