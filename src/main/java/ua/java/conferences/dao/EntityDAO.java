package ua.java.conferences.dao;

import ua.java.conferences.exceptions.DAOException;

import java.util.*;

public interface EntityDAO<T> {

    void add(T t) throws DAOException;

    Optional<T> getById(long id) throws DAOException;

    List<T> getAll() throws DAOException;

    void update(T t) throws DAOException;

    void delete(long id) throws DAOException;
}