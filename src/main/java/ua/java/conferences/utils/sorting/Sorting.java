package ua.java.conferences.utils.sorting;

import ua.java.conferences.exceptions.WrongParameterException;

import java.util.Objects;

import static ua.java.conferences.utils.sorting.SortingSets.*;

public class Sorting {
    private String filter;
    private String sort;
    private String order;

    private Sorting() {}

    public static Sorting getUserSorting(String filter, String sort, String order) throws WrongParameterException {
        Sorting sorting = new Sorting();
        sorting.filter = setRoleFilter(filter);
        sorting.sort = setUserSortField(sort);
        sorting.order = setOrder(order);
        return sorting;
    }

    public static Sorting getEventSorting(String filter, String sort, String order) throws WrongParameterException {
        Sorting sorting = new Sorting();
        sorting.filter = setDateFilter(filter);
        sorting.sort = setEventSortField(sort);
        sorting.order = setOrder(order);
        return sorting;
    }

    public String getFilter() {
        return filter;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    private static String setDateFilter(String filter) throws WrongParameterException {
        if (getDateSet().contains(filter)) {
            return filter;
        } else {
            throw new WrongParameterException();
        }
    }

    private static String setUserSortField(String sort) throws WrongParameterException {
        if (getUserSortFieldsSet().contains(sort)) {
            return sort;
        } else {
            throw new WrongParameterException();
        }
    }

    private static String setOrder(String order) throws WrongParameterException {
        if (getOrderSet().contains(order)) {
            return order;
        } else {
            throw new WrongParameterException();
        }
    }

    private static String setEventSortField(String sort) throws WrongParameterException {
        if (getEventSortFieldsSet().contains(sort)) {
            return "event." + sort;
        } else {
            throw new WrongParameterException();
        }
    }

    private static String setRoleFilter(String filter) throws WrongParameterException {
        if (getRoleSet().contains(filter)) {
            return filter;
        } else {
            throw new WrongParameterException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sorting sorting = (Sorting) o;
        return filter.equals(sorting.filter) && sort.equals(sorting.sort) && order.equals(sorting.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, sort, order);
    }
}