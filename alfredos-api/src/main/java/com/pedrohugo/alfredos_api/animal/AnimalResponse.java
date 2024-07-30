package com.pedrohugo.alfredos_api.animal;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnimalResponse {

    private long id;

    private String name;

    private String description;

    private String imageUrl;

    private String category;

    private String dateOfBirth;

    private int age;

    private String status;
}
