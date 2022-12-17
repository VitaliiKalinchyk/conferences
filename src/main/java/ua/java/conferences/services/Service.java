package ua.java.conferences.services;

import ua.java.conferences.exceptions.ServiceException;
import java.util.List;

public interface Service<T> {
    T getById(String idString) throws ServiceException;

    List<T> getAll() throws ServiceException;

    void update(T entity) throws ServiceException;

    void delete(String idString) throws ServiceException;
}