package com.placeflow.dto;

public class DashboardStatsDTO {

    private long totalApplications;
    private long upcomingDeadlines;
    private long pendingStudyTasks;
    private double averageDsaProgress;
    private long selectedApplications;
    private long rejectedApplications;
    private long interviewApplications;

    public DashboardStatsDTO() {}

    public DashboardStatsDTO(long totalApplications, long upcomingDeadlines, long pendingStudyTasks, double averageDsaProgress, long selectedApplications, long rejectedApplications, long interviewApplications) {
        this.totalApplications = totalApplications;
        this.upcomingDeadlines = upcomingDeadlines;
        this.pendingStudyTasks = pendingStudyTasks;
        this.averageDsaProgress = averageDsaProgress;
        this.selectedApplications = selectedApplications;
        this.rejectedApplications = rejectedApplications;
        this.interviewApplications = interviewApplications;
    }

    public long getTotalApplications() { return totalApplications; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

    public long getUpcomingDeadlines() { return upcomingDeadlines; }
    public void setUpcomingDeadlines(long upcomingDeadlines) { this.upcomingDeadlines = upcomingDeadlines; }

    public long getPendingStudyTasks() { return pendingStudyTasks; }
    public void setPendingStudyTasks(long pendingStudyTasks) { this.pendingStudyTasks = pendingStudyTasks; }

    public double getAverageDsaProgress() { return averageDsaProgress; }
    public void setAverageDsaProgress(double averageDsaProgress) { this.averageDsaProgress = averageDsaProgress; }

    public long getSelectedApplications() { return selectedApplications; }
    public void setSelectedApplications(long selectedApplications) { this.selectedApplications = selectedApplications; }

    public long getRejectedApplications() { return rejectedApplications; }
    public void setRejectedApplications(long rejectedApplications) { this.rejectedApplications = rejectedApplications; }

    public long getInterviewApplications() { return interviewApplications; }
    public void setInterviewApplications(long interviewApplications) { this.interviewApplications = interviewApplications; }
}
