package com.pedrohugo.alfredos_api.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class AnimalRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String category;

    @NotNull
    private Date dateOfBirth;

    @NotBlank
    private String status;
}
