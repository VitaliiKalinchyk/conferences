package ua.java.conferences.entity;

import java.util.Objects;

public class Report {

    private int id;

    private String topic;

    private boolean accepted;

    private boolean approved;

    public Report() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean b) {
        this.accepted = b;
    }

    public boolean isApproved() {
        return this.approved;
    }

    public void setApproved(boolean b) {
        this.approved = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", accepted=" + accepted +
                ", approved=" + approved +
                '}';
    }
}