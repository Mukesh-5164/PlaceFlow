package com.placeflow.controller;

import com.placeflow.dto.StudyTaskDTO;
import com.placeflow.service.StudyTaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class StudyTaskController {

    private final StudyTaskService studyTaskService;

    public StudyTaskController(StudyTaskService studyTaskService) {
        this.studyTaskService = studyTaskService;
    }

    @GetMapping
    public ResponseEntity<List<StudyTaskDTO>> getAllTasks() {
        return ResponseEntity.ok(studyTaskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyTaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(studyTaskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<StudyTaskDTO> createTask(
            @Valid @RequestBody StudyTaskDTO dto) {
        StudyTaskDTO created = studyTaskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyTaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody StudyTaskDTO dto) {
        return ResponseEntity.ok(studyTaskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        studyTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
