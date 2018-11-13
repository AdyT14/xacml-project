package com.adyt.xacml.example.xacml.repository;

import com.adyt.xacml.example.xacml.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
