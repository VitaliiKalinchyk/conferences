package ua.java.conferences.dao;

import ua.java.conferences.exception.DAOException;

import java.util.List;

public interface EntityDAO<T> {

    boolean add(T t) throws DAOException;

    T getById(int id) throws DAOException;

    List<T> getAll() throws DAOException;

    boolean update(T t) throws DAOException;

    boolean delete(T t) throws DAOException;
}