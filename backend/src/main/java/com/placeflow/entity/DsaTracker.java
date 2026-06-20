package com.placeflow.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dsa_tracker")
public class DsaTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage;

    @Column(name = "last_practiced_date")
    private LocalDate lastPracticedDate;

    public DsaTracker() {}

    public DsaTracker(Long id, String topicName, Integer progressPercentage, LocalDate lastPracticedDate) {
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
