package com.placeflow.service;

import com.placeflow.dto.ApplicationDTO;
import com.placeflow.entity.Application;
import com.placeflow.entity.Application.ApplicationStatus;
import com.placeflow.exception.ResourceNotFoundException;
import com.placeflow.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<ApplicationDTO> getAllApplications(String status) {
        List<Application> applications;
        if (status != null && !status.isBlank()) {
            ApplicationStatus appStatus = ApplicationStatus.valueOf(status);
            applications = applicationRepository.findByStatus(appStatus);
        } else {
            applications = applicationRepository.findAll();
        }
        return applications.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ApplicationDTO getApplicationById(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));
        return toDTO(app);
    }

    public ApplicationDTO createApplication(ApplicationDTO dto) {
        Application app = toEntity(dto);
        return toDTO(applicationRepository.save(app));
    }

    public ApplicationDTO updateApplication(Long id, ApplicationDTO dto) {
        Application existing = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        existing.setCompanyName(dto.getCompanyName());
        existing.setRole(dto.getRole());
        existing.setAppliedDate(dto.getAppliedDate());
        existing.setDeadline(dto.getDeadline());
        existing.setStatus(dto.getStatus());
        existing.setLocation(dto.getLocation());

        return toDTO(applicationRepository.save(existing));
    }

    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application", "id", id);
        }
        applicationRepository.deleteById(id);
    }

    private ApplicationDTO toDTO(Application app) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(app.getId());
        dto.setCompanyName(app.getCompanyName());
        dto.setRole(app.getRole());
        dto.setAppliedDate(app.getAppliedDate());
        dto.setDeadline(app.getDeadline());
        dto.setStatus(app.getStatus());
        dto.setLocation(app.getLocation());
        return dto;
    }

    private Application toEntity(ApplicationDTO dto) {
        Application app = new Application();
        app.setCompanyName(dto.getCompanyName());
        app.setRole(dto.getRole());
        app.setAppliedDate(dto.getAppliedDate());
        app.setDeadline(dto.getDeadline());
        app.setStatus(dto.getStatus());
        app.setLocation(dto.getLocation());
        return app;
    }
}
