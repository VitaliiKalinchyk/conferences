package ua.java.conferences.services;

import ua.java.conferences.exceptions.ServiceException;

public interface Service<T> {
    T view(String idString) throws ServiceException;

    void delete(String idString) throws ServiceException;
}