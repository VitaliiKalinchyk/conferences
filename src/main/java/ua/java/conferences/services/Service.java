package ua.java.conferences.services;

import ua.java.conferences.exceptions.ServiceException;

import java.util.List;

public interface Service<T> {

    boolean add(T t) throws ServiceException;

    T getById(long id) throws ServiceException;

    List<T> getAll() throws ServiceException;

    boolean update(T t) throws ServiceException;

    boolean delete(long id) throws ServiceException;
}
