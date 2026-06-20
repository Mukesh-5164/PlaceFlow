package com.placeflow.controller;

import com.placeflow.dto.DashboardStatsDTO;
import com.placeflow.entity.Application.ApplicationStatus;
import com.placeflow.entity.StudyTask.TaskStatus;
import com.placeflow.repository.ApplicationRepository;
import com.placeflow.repository.StudyTaskRepository;
import com.placeflow.service.DsaTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ApplicationRepository applicationRepository;
    private final StudyTaskRepository studyTaskRepository;
    private final DsaTrackerService dsaTrackerService;

    public DashboardController(ApplicationRepository applicationRepository,
                               StudyTaskRepository studyTaskRepository,
                               DsaTrackerService dsaTrackerService) {
        this.applicationRepository = applicationRepository;
        this.studyTaskRepository = studyTaskRepository;
        this.dsaTrackerService = dsaTrackerService;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        long totalApplications = applicationRepository.count();

        // Upcoming deadlines = applications with deadline in the next 7 days
        long upcomingDeadlines = applicationRepository
                .findByDeadlineBetween(LocalDate.now(), LocalDate.now().plusDays(7))
                .size();

        long pendingStudyTasks = studyTaskRepository.countByStatus(TaskStatus.Pending);

        double avgDsaProgress = dsaTrackerService.getAverageProgress();

        long selectedApplications = applicationRepository.countByStatus(ApplicationStatus.Selected);
        long rejectedApplications = applicationRepository.countByStatus(ApplicationStatus.Rejected);
        long interviewApplications = applicationRepository.countByStatus(ApplicationStatus.Interview);

        DashboardStatsDTO stats = new DashboardStatsDTO(
                totalApplications,
                upcomingDeadlines,
                pendingStudyTasks,
                avgDsaProgress,
                selectedApplications,
                rejectedApplications,
                interviewApplications
        );

        return ResponseEntity.ok(stats);
    }
}
