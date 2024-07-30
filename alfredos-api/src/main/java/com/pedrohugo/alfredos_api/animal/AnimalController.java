package com.pedrohugo.alfredos_api.animal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/animals")
public class AnimalController {

    private final AnimalService service;

    @Operation(summary = "List all animals")
    @GetMapping
    public ResponseEntity<List<AnimalResponse>> list() {
        List<AnimalResponse> animals = service.list();
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

    @Operation(summary = "Get an animal by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the animal",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Animal not found",
                    content = @Content) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimalResponse> get(@PathVariable @NotNull Long id) {
        AnimalResponse animal = service.get(id);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    @Operation(summary = "Create an animal")
    @PostMapping
    public ResponseEntity<AnimalResponse> create(@RequestBody @Valid AnimalRequest request) {
        AnimalResponse animalCreated = service.create(request).convertToResponse();
        return new ResponseEntity<>(animalCreated, HttpStatus.OK);
    }

    @Operation(summary = "Update animal status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal status updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class)) }),
            @ApiResponse(responseCode = "404", description = "Animal not found",
                    content = @Content) })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<AnimalResponse> update(@PathVariable @NotNull Long id, @RequestBody @NotBlank String status) {
        AnimalResponse animalUpdated = service.updateStatus(id, StatusEnum.valueOf(status)).convertToResponse();
        return new ResponseEntity<>(animalUpdated, HttpStatus.OK);
    }
}
