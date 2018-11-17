package com.adyt.xacml.example.xacml.xacml;

import com.adyt.xacml.example.xacml.domain.User;
import com.adyt.xacml.example.xacml.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.wso2.balana.attr.AttributeValue;
import org.wso2.balana.attr.BagAttribute;
import org.wso2.balana.attr.StringAttribute;
import org.wso2.balana.cond.EvaluationResult;
import org.wso2.balana.ctx.EvaluationCtx;
import org.wso2.balana.finder.AttributeFinderModule;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by AdyT on 11/17/2018.
 */
@Component
public class SampleAttributeFinderModule extends AttributeFinderModule {

    private final UserRepository userRepository;
    private URI defaultSubjectId;

    public SampleAttributeFinderModule(UserRepository userRepository) {
        this.userRepository = userRepository;
        try {
            defaultSubjectId = new URI("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedCategories() {
        Set<String> categories = new HashSet<>();
        categories.add("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
        return categories;
    }

    @Override
    public Set getSupportedIds() {
        Set<String> ids = new HashSet<>();
        ids.add("http://xacml-example.com/id/role");
        return ids;
    }

    @Override
    public EvaluationResult findAttribute(URI attributeType, URI attributeId, String issuer, URI category, EvaluationCtx context) {
        String roleName = null;
        List<AttributeValue> attributeValues = new ArrayList<>();
        EvaluationResult result = context.getAttribute(attributeType, defaultSubjectId, issuer, category);
        if(result != null && result.getAttributeValue() != null && result.getAttributeValue().isBag()){
            BagAttribute bagAttribute = (BagAttribute) result.getAttributeValue();
            if(bagAttribute.size() > 0){
                String userId = ((AttributeValue) bagAttribute.iterator().next()).encode();
                roleName = this.findRole(Long.valueOf(userId));
            }
        }
        if(roleName != null){
            attributeValues.add(new StringAttribute(roleName));
        }
        return new EvaluationResult(new BagAttribute(attributeType, attributeValues));
    }

    // Find role for current user id
    private String findRole(Long userId) {
        return userRepository.findById(userId).map(User::getRole).orElse(null);
    }

    @Override
    public boolean isDesignatorSupported() {
        return true;
    }
}
