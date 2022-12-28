package ua.java.conferences.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.*;
import ua.java.conferences.dto.EventDTO;
import ua.java.conferences.dto.UserDTO;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class PdfUtil {
    private static final Logger logger = LoggerFactory.getLogger(PdfUtil.class);
    private static final BaseColor LIGHT_GREY = new BaseColor(220, 220, 220);
    private static final int TITLE_SIZE = 20;
    private static final String USER_TITLE = "USERS";
    private static final String[] USER_CELLS = new String[] {"ID", "Email", "Name", "Surname", "Role"};
    private static final String EVENT_TITLE = "EVENTS";
    private static final String[] EVENT_CELLS = new String[] {"Title", "Date", "Location", "Reports",
                                                                        "Registered", "Visitors"};

    public ByteArrayOutputStream createUsersPdf(List<UserDTO> users)  {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(getTableTitle(USER_TITLE));
            document.add(new Paragraph(Chunk.NEWLINE));
            document.add(getUserTable(users));
            document.close();
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        return output;
    }

    public ByteArrayOutputStream createEventsPdf(List<EventDTO> events)  {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(getTableTitle(EVENT_TITLE));
            document.add(new Paragraph(Chunk.NEWLINE));
            document.add(getEventTable(events));
            document.close();
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        return output;
    }

    private static Paragraph getTableTitle(String tableTitle) {
        Font font = FontFactory.getFont(FontFactory.COURIER, TITLE_SIZE, BaseColor.BLACK);
        Chunk chunk = new Chunk(tableTitle, font);
        Paragraph title = new Paragraph(chunk);
        title.setAlignment(Element.ALIGN_CENTER);
        return title;
    }

    private PdfPTable getUserTable(List<UserDTO> users) {
        PdfPTable table = new PdfPTable(new float[]{1,4,2,2,2});
        addUserTableHeader(table, USER_CELLS);
        addUserTableRows(table, users);
        return table;
    }

    private PdfPTable getEventTable(List<EventDTO> events) {
        PdfPTable table = new PdfPTable(new float[]{7,3,3,2,2,2});
        addUserTableHeader(table, EVENT_CELLS);
        addEventTableRows(table, events);
        return table;
    }

    private void addUserTableHeader(PdfPTable table, String[] cells) {
        Stream.of(cells)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(LIGHT_GREY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addUserTableRows(PdfPTable table, List<UserDTO> users) {
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

    private void addEventTableRows(PdfPTable table, List<EventDTO> events) {
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
}