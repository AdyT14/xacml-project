package com.adyt.xacml.example.xacml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.adyt.xacml.example.xacml"})
@EntityScan("com.adyt.xacml.example.xacml.domain")
@EnableJpaRepositories("com.adyt.xacml.example.xacml.repository")
public class XacmlApplication {

  public static void main(String[] args) {
    SpringApplication.run(XacmlApplication.class, args);
  }
}
