package ua.java.conferences.utils;

import com.itextpdf.kernel.colors.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.kernel.geom.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import jakarta.servlet.ServletContext;
import org.slf4j.*;
import ua.java.conferences.dto.*;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

public class PdfUtil {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private static final String FONT = "fonts/arial.ttf";
    private static final Color LIGHT_GREY = new DeviceRgb(220, 220, 220);
    private static final int TITLE_SIZE = 20;
    private static final Paragraph LINE_SEPARATOR = new Paragraph(new Text("\n"));
    private static final String USER_TITLE = "USERS";
    private static final String USER_TITLE_UA = "КОРИСТУВАЧІ";
    private static final String[] USER_CELLS = new String[]{"ID", "Email", "Name", "Surname", "Role"};
    private static final String[] USER_CELLS_UA = new String[]{"ID", "Пошта", "Ім'я", "Прізвище", "Роль"};
    private static final String EVENT_TITLE = "CONFERENCES";
    private static final String EVENT_TITLE_UA = "КОНФЕРЕНЦІЇ";
    private static final String[] EVENT_CELLS =
            new String[]{"Title", "Date", "Location", "Reports", "Registered", "Visitors"};
    private static final String[] EVENT_CELLS_UA =
            new String[]{"Назва", "Дата", "Місце", "Доповідей", "Реєстрацій", "Відвідувачів"};

    public ByteArrayOutputStream createUsersPdf(List<UserDTO> users, ServletContext servletContext, String locale) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(servletContext, output);
        document.add(getTableTitle(locale.equals("en") ? USER_TITLE : USER_TITLE_UA));
        document.add(LINE_SEPARATOR);
        document.add(getUserTable(users, locale));
        document.close();
        return output;
    }

    public ByteArrayOutputStream createEventsPdf(List<EventDTO> events, ServletContext servletContext, String locale) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = getDocument(servletContext, output);
        document.add(getTableTitle(locale.equals("en") ? EVENT_TITLE : EVENT_TITLE_UA));
        document.add(LINE_SEPARATOR);
        document.add(getEventTable(events, locale));
        document.close();
        return output;
    }

    private static Document getDocument(ServletContext servletContext, ByteArrayOutputStream output) {
        PdfWriter writer = new PdfWriter(output);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4.rotate());
        PdfFont font = getPdfFont(servletContext);
        if (font != null) {
            document.setFont(font);
        }
        return document;
    }

    private static Paragraph getTableTitle(String tableTitle) {
        return new Paragraph(new Text(tableTitle))
                .setFontSize(TITLE_SIZE)
                .setTextAlignment(TextAlignment.CENTER);
    }

    private Table getUserTable(List<UserDTO> users, String locale) {
        Table table = new Table(new float[]{4, 12, 6, 6, 6});
        table.setWidth(UnitValue.createPercentValue(100));
        addUserTableHeader(table, locale.equals("en") ? USER_CELLS : USER_CELLS_UA);
        addUserTableRows(table, users);
        return table;
    }

    private Table getEventTable(List<EventDTO> events, String locale) {
        Table table = new Table(new float[]{7, 3, 3, 2, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        addUserTableHeader(table, locale.equals("en") ? EVENT_CELLS : EVENT_CELLS_UA);
        addEventTableRows(table, events);
        return table;
    }

    private void addUserTableHeader(Table table, String[] cells) {
        Stream.of(cells)
                .forEach(columnTitle -> {
                    Cell header = new Cell();
                    header.setBackgroundColor(LIGHT_GREY);
                    header.setBorder(new SolidBorder(1));
                    header.add(new Paragraph(columnTitle));
                    table.addCell(header);
                });
    }

    private void addUserTableRows(Table table, List<UserDTO> users) {
        users.forEach(user ->
                {
                    table.addCell(String.valueOf(user.getId()));
                    table.addCell(user.getEmail());
                    table.addCell(user.getName());
                    table.addCell(user.getSurname());
                    table.addCell(user.getRole());
                }
        );
    }

    private void addEventTableRows(Table table, List<EventDTO> events) {
        events.forEach(event ->
                {
                    table.addCell(event.getTitle());
                    table.addCell(event.getDate());
                    table.addCell(event.getLocation());
                    table.addCell(String.valueOf(event.getReports()));
                    table.addCell(String.valueOf(event.getRegistrations()));
                    table.addCell(String.valueOf(event.getVisitors()));
                }
        );
    }

    private static PdfFont getPdfFont(ServletContext servletContext) {
        try {
            URL resource = servletContext.getResource(FONT);
            String fontUrl = resource.getFile();
            return PdfFontFactory.createFont(fontUrl);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}