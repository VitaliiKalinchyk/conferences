package ua.java.conferences.utils;

import jakarta.servlet.http.HttpServletRequest;

import static ua.java.conferences.actions.constants.Parameters.*;

public final class PaginationUtil {

    public static void paginate(int totalRecords, HttpServletRequest request) {
        int records = getInt(request.getParameter(RECORDS), 1, 5);
        int offset = getInt(request.getParameter(OFFSET), 0, 0);
        int pages = totalRecords / records + (totalRecords % records != 0 ? 1 : 0);
        int currentPage = (offset / records) + 1;
        int start = currentPage == pages ? Math.max(currentPage - 2, 1)
                                         : Math.max(currentPage - 1, 1);
        int end = Math.min(start + 2, pages);
        setAttributes(request, records, offset, pages, currentPage, start, end);
    }

    private static void setAttributes(HttpServletRequest request, int records, int offset, int pages, int currentPage,
                                      int start, int end) {
        request.setAttribute(RECORDS, records);
        request.setAttribute(OFFSET, offset);
        request.setAttribute(PAGES, pages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(START, start);
        request.setAttribute(END, end);
    }

    private static int getInt(String value, int min, int defaultValue) {
        try {
            int records = Integer.parseInt(value);
            if (records >= min) {
                return records;
            }
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            return defaultValue;
        }
        return defaultValue;
    }

    private PaginationUtil() {}
}