package com.placeflow.dto;

import com.placeflow.entity.Application.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ApplicationDTO {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;

    private LocalDate deadline;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private String location;

    public ApplicationDTO() {}

    public ApplicationDTO(Long id, String companyName, String role, LocalDate appliedDate, LocalDate deadline, ApplicationStatus status, String location) {
        this.id = id;
        this.companyName = companyName;
        this.role = role;
        this.appliedDate = appliedDate;
        this.deadline = deadline;
        this.status = status;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
