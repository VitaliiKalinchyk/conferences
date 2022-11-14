package ua.java.conferences.dao;

import ua.java.conferences.entity.*;
import ua.java.conferences.exception.DBException;

import java.util.List;

public interface EventDAO extends EntityDAO<Event> {

    boolean setVisitors(Event var1, int var2) throws DBException;

    List<Event> getEventsByUser(User var1) throws DBException;

    List<Event> getEventsBySpeaker(User var1) throws DBException;

    Event getEventByReport(Report var1) throws DBException;
}