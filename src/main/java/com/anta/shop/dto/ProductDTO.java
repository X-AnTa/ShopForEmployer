package com.anta.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class ProductDTO {

    private int id;
    @NotBlank(message = "Field 'name' must be filled")
    private String name;
    private String description;
    @Positive(message = "Field 'price' must be greater than 0")
    private BigDecimal price;
    private Date dateOfCreation;
    private Date dateOfModification;
    private Set<String> currencies;
    private Set<String> descriptions;
}
