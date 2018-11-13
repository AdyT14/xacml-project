package com.adyt.xacml.example.xacml.configuration;

import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wso2.balana.Balana;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

@Configuration
public class BalanaConfiguration {

  @Bean
  public Balana getBalanaInstance() {
    return Balana.getInstance();
  }

  @PostConstruct
  public void setPolicyDirectory() throws IOException {
    System.setProperty(FileBasedPolicyFinderModule.POLICY_DIR_PROPERTY,
        (new File(".")).getCanonicalPath() + File.separator + "resources");
  }

}
