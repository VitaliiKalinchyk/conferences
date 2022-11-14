package ua.java.conferences.entity.builder;

import ua.java.conferences.entity.Event;

import java.time.LocalDate;

public class EventBuilder {

    private final Event event;

    public EventBuilder() {
        this.event = new Event();
    }

    public EventBuilder(Event event) {
        this.event = event;
    }

    public EventBuilder setId(int id) {
        this.event.setId(id);
        return this;
    }

    public EventBuilder setTitle(String title) {
        this.event.setTitle(title);
        return this;
    }

    public EventBuilder setDate(LocalDate date) {
        this.event.setDate(date);
        return this;
    }

    public EventBuilder setLocation(String location) {
        this.event.setLocation(location);
        return this;
    }

    public EventBuilder setDescription(String description) {
        this.event.setDescription(description);
        return this;
    }

    public EventBuilder setVisitors(int visitors) {
        this.event.setVisitors(visitors);
        return this;
    }

    public Event get() {
        return this.event;
    }
}