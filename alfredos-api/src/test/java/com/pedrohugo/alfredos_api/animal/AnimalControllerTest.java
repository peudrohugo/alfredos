package com.pedrohugo.alfredos_api.animal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AnimalController.class)
class AnimalControllerTest {

    @MockBean
    AnimalService service;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mock;

    @Test
    @DisplayName("Should create an animal successfully")
    void create() throws Exception {
        AnimalRequest request = this.createAnimal();

        Animal animal = new Animal(request);

        when(service.create(any(AnimalRequest.class))).thenReturn(animal);

        mock.perform(post("/animals")
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.category").value(request.getCategory()));
    }

    @Test
    @DisplayName("Should display an error for the missing fields")
    void tryToCreateWithMissingFields() throws Exception {
        AnimalRequest request = new AnimalRequest();
        request.setName("Alfredo");

        Animal animal = new Animal();
        animal.setName(request.getName());

        when(service.create(any(AnimalRequest.class))).thenReturn(animal);

        mock.perform(post("/animals")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should update animal status")
    void updateStatus() throws Exception {
        AnimalRequest request = this.createAnimal();

        Animal animal = new Animal(request);
        animal.setStatus(StatusEnum.AVAILABLE);

        when(service.updateStatus(1L, StatusEnum.valueOf("AVAILABLE"))).thenReturn(animal);

        mock.perform(patch("/animals/1")
                        .content("AVAILABLE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    private AnimalRequest createAnimal() {
        AnimalRequest request = new AnimalRequest();
        request.setName("Alfredo");
        request.setDescription("Extremely messy and spoiled dog");
        request.setImageUrl("cachorro-poodle-3.png");
        request.setCategory("DOG");
        request.setDateOfBirth(new Date());
        request.setStatus("ADOPTED");

        return request;
    }
}