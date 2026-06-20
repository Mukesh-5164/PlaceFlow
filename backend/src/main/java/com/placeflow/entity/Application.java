package com.placeflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "location")
    private String location;

    public enum ApplicationStatus {
        Applied,
        Online_Assessment,
        Interview,
        Rejected,
        Selected
    }

    public Application() {}

    public Application(Long id, String companyName, String role, LocalDate appliedDate, LocalDate deadline, ApplicationStatus status, String location) {
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
