package edu.wpi.cs.cloudcomputing.model;

import java.rmi.server.UID;

/**
 * Created by tonggezhu on 2/25/18.
 */
public class Report {
    private String reportId;
    private String fromUserId;
    private String toUserId;
    private String reason;
    private Boolean processed;

    public Report() {
    }

    public Report(String reportId, String fromUserId, String toUserId, String reason, Boolean processed) {
        this.reportId = reportId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.reason = reason;
        this.processed = processed;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reviewId) {
        this.reportId = reviewId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
