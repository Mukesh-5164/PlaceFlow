package com.placeflow.repository;

import com.placeflow.entity.StudyTask;
import com.placeflow.entity.StudyTask.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyTaskRepository extends JpaRepository<StudyTask, Long> {

    List<StudyTask> findByStatus(TaskStatus status);

    long countByStatus(TaskStatus status);
}
