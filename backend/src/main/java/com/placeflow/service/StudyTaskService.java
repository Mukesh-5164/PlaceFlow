package com.placeflow.service;

import com.placeflow.dto.StudyTaskDTO;
import com.placeflow.entity.StudyTask;
import com.placeflow.entity.StudyTask.TaskStatus;
import com.placeflow.exception.ResourceNotFoundException;
import com.placeflow.repository.StudyTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyTaskService {

    private final StudyTaskRepository studyTaskRepository;

    public StudyTaskService(StudyTaskRepository studyTaskRepository) {
        this.studyTaskRepository = studyTaskRepository;
    }

    public List<StudyTaskDTO> getAllTasks() {
        return studyTaskRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public StudyTaskDTO getTaskById(Long id) {
        StudyTask task = studyTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study Task", "id", id));
        return toDTO(task);
    }

    public StudyTaskDTO createTask(StudyTaskDTO dto) {
        StudyTask task = toEntity(dto);
        return toDTO(studyTaskRepository.save(task));
    }

    public StudyTaskDTO updateTask(Long id, StudyTaskDTO dto) {
        StudyTask existing = studyTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study Task", "id", id));

        existing.setTaskName(dto.getTaskName());
        existing.setDueDate(dto.getDueDate());
        existing.setStatus(dto.getStatus());

        return toDTO(studyTaskRepository.save(existing));
    }

    public void deleteTask(Long id) {
        if (!studyTaskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Study Task", "id", id);
        }
        studyTaskRepository.deleteById(id);
    }

    public long countPendingTasks() {
        return studyTaskRepository.countByStatus(TaskStatus.Pending);
    }

    private StudyTaskDTO toDTO(StudyTask task) {
        StudyTaskDTO dto = new StudyTaskDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        return dto;
    }

    private StudyTask toEntity(StudyTaskDTO dto) {
        StudyTask task = new StudyTask();
        task.setTaskName(dto.getTaskName());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        return task;
    }
}
