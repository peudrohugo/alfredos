package com.pedrohugo.alfredos_api.animal;

import com.pedrohugo.alfredos_api.exception.AnimalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository repository;

    public List<AnimalResponse> list() {
        return repository.findAll().stream().map(Animal::convertToResponse).collect(Collectors.toList());
    }

    public AnimalResponse get(Long id) {
        return repository.findById(id).map(Animal::convertToResponse).orElseThrow(() -> new AnimalNotFoundException("Animal not exist with id: " + id));
    }

    public Animal create(AnimalRequest request) {
        Animal toCreate = new Animal(request);
        return repository.save(toCreate);
    }

    public Animal updateStatus(Long id, StatusEnum status) {
        Animal animal = repository.findById(id).orElseThrow(() -> new AnimalNotFoundException("Animal not exist with id: " + id));

        switch (status) {
            case AVAILABLE:
                animal.setStatus(StatusEnum.AVAILABLE);
                break;
            case ADOPTED:
                animal.setStatus(StatusEnum.ADOPTED);
                break;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }

        return repository.save(animal);
    }
}
