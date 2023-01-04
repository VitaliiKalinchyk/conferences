package ua.java.conferences.controller.tag;

import jakarta.servlet.jsp.*;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.LocalDate;

public class NowTag extends SimpleTagSupport {

    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.print(LocalDate.now());
    }
}