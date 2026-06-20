package com.placeflow.repository;

import com.placeflow.entity.Application;
import com.placeflow.entity.Application.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByDeadlineBetween(LocalDate start, LocalDate end);

    long countByStatus(ApplicationStatus status);

    List<Application> findByDeadlineAfterOrderByDeadlineAsc(LocalDate date);
}
