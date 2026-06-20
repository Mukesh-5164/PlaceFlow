package com.placeflow.controller;

import com.placeflow.dto.DsaTrackerDTO;
import com.placeflow.service.DsaTrackerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dsa")
public class DsaTrackerController {

    private final DsaTrackerService dsaTrackerService;

    public DsaTrackerController(DsaTrackerService dsaTrackerService) {
        this.dsaTrackerService = dsaTrackerService;
    }

    @GetMapping
    public ResponseEntity<List<DsaTrackerDTO>> getAllTopics() {
        return ResponseEntity.ok(dsaTrackerService.getAllTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DsaTrackerDTO> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(dsaTrackerService.getTopicById(id));
    }

    @PostMapping
    public ResponseEntity<DsaTrackerDTO> createTopic(
            @Valid @RequestBody DsaTrackerDTO dto) {
        DsaTrackerDTO created = dsaTrackerService.createTopic(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DsaTrackerDTO> updateTopic(
            @PathVariable Long id,
            @Valid @RequestBody DsaTrackerDTO dto) {
        return ResponseEntity.ok(dsaTrackerService.updateTopic(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        dsaTrackerService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
