package ua.java.conferences.services;

import ua.java.conferences.exceptions.ServiceException;

public interface Service<T> {
    T view(long id) throws ServiceException;

    void delete(long id) throws ServiceException;
}