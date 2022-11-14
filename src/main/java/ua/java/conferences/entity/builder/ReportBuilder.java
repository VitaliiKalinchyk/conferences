package ua.java.conferences.entity.builder;

import ua.java.conferences.entity.Report;

public class ReportBuilder {

    private final Report report;

    public ReportBuilder() {
        this.report = new Report();
    }

    public ReportBuilder setId(int id) {
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