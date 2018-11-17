package com.adyt.xacml.example.xacml.configuration;

import com.adyt.xacml.example.xacml.repository.UserRepository;
import com.adyt.xacml.example.xacml.xacml.SampleAttributeFinderModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wso2.balana.Balana;
import org.wso2.balana.PDP;
import org.wso2.balana.PDPConfig;
import org.wso2.balana.finder.AttributeFinder;
import org.wso2.balana.finder.AttributeFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    /**
     * Returns a new PDP instance with new XACML policies
     *
     * @return a  PDP instance
     */
    @Bean
    public PDP getPDP(Balana balana, UserRepository userRepository) {
        PDPConfig pdpConfig = balana.getPdpConfig();

        //Registering new AttributeFinder. Default PDPConfig will be changed
        AttributeFinder attributeFinder = pdpConfig.getAttributeFinder();
        List<AttributeFinderModule> finderModules = attributeFinder.getModules();
        finderModules.add(new SampleAttributeFinderModule(userRepository));
        attributeFinder.setModules(finderModules);
        return new PDP(new PDPConfig(
                attributeFinder,
                pdpConfig.getPolicyFinder(),
                null,
                true));
    }

}
