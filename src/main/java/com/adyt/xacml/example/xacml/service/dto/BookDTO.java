package com.adyt.xacml.example.xacml.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by AdyT on 11/21/2018.
 */
@Data
public class BookDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

}
