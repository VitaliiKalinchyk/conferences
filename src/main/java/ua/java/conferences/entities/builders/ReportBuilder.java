package ua.java.conferences.entities.builders;

import ua.java.conferences.entities.Report;

public class ReportBuilder {

    private final Report report;

    public ReportBuilder() {
        this.report = new Report();
    }

    public ReportBuilder setId(long id) {
        this.report.setId(id);
        return this;
    }

    public ReportBuilder setTopic(String topic) {
        this.report.setTopic(topic);
        return this;
    }

    public ReportBuilder setAccepted(boolean b) {
        this.report.setAccepted(b);
        return this;
    }

    public ReportBuilder setApproved(boolean b) {
        this.report.setApproved(b);
        return this;
    }

    public Report get() {
        return this.report;
    }
}