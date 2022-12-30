package ua.java.conferences.utils.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    private static final String LETTER = "Hello %s,<br>We have some important information for you:<br><br>" +
            "Topic for you report at %s (%s %s) was changed to %s<br>You can check this report " +
            "<a href=https://conferences.org/controller?action=view-event-by-speaker&event-id=%s>here</a>, <br><br>" +
            "Yours truly,<br>Conference Smart App team";

    @Test
    void testUrl() {
        assertEquals(LETTER, Email.MESSAGE_TOPIC_CHANGED);
    }
}