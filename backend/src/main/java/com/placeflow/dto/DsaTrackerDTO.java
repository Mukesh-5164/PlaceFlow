package com.placeflow.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class DsaTrackerDTO {

    private Long id;

    @NotBlank(message = "Topic name is required")
    private String topicName;

    @Min(value = 0, message = "Progress must be at least 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    private Integer progressPercentage;

    private LocalDate lastPracticedDate;

    public DsaTrackerDTO() {}

    public DsaTrackerDTO(Long id, String topicName, Integer progressPercentage, LocalDate lastPracticedDate) {
        this.id = id;
        this.topicName = topicName;
        this.progressPercentage = progressPercentage;
        this.lastPracticedDate = lastPracticedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }

    public LocalDate getLastPracticedDate() { return lastPracticedDate; }
    public void setLastPracticedDate(LocalDate lastPracticedDate) { this.lastPracticedDate = lastPracticedDate; }
}
