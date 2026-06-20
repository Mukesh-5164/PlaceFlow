package com.placeflow.controller;

import com.placeflow.dto.ApplicationDTO;
import com.placeflow.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(applicationService.getAllApplications(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(
            @Valid @RequestBody ApplicationDTO dto) {
        ApplicationDTO created = applicationService.createApplication(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationDTO dto) {
        return ResponseEntity.ok(applicationService.updateApplication(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
