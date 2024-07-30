package com.pedrohugo.alfredos_api.animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "category")
    private String category;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "age")
    private int age;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public Animal(AnimalRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.imageUrl = request.getImageUrl();
        this.category = request.getCategory();
        this.dateOfBirth = LocalDateTime.ofInstant(request.getDateOfBirth().toInstant(), ZoneId.systemDefault());
        this.status = StatusEnum.valueOf(request.getStatus());
        this.age = Period.between(LocalDate.from(this.dateOfBirth), LocalDate.now()).getYears();
    }

    public AnimalResponse convertToResponse() {
        AnimalResponse animal = new AnimalResponse();
        animal.setId(this.getId());
        animal.setName(this.getName());
        animal.setDescription(this.getDescription());
        animal.setImageUrl(this.getImageUrl());
        animal.setCategory(this.getCategory());
        animal.setDateOfBirth(this.getDateOfBirth().toString());
        animal.setAge(this.getAge());
        animal.setStatus(this.getStatus().name());
        return animal;
    }
}
