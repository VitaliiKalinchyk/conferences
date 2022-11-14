package ua.java.conferences.dao;

import ua.java.conferences.exception.DBException;

import java.util.List;

public interface EntityDAO<T> {

    boolean add(T var1) throws DBException;

    T get(T var1) throws DBException;

    List<T> getAll() throws DBException;

    boolean update(T var1) throws DBException;

    boolean delete(T var1) throws DBException;
}