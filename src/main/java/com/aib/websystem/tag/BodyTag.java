package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import lombok.Setter;

public class BodyTag extends BodyTagSupport {
    @Setter
    private String title;

    @Override
    public int doStartTag() throws JspException {
        try {
            // Include header
            JspWriter out = pageContext.getOut();
            out.print("<!DOCTYPE html>");
            out.print("<html lang=\"en\">");
            out.print("<head>");
            pageContext.getRequest().setAttribute("title", title);
            pageContext.include("templates/head.jsp");
            out.print("</head>");
            out.print("<body>");
            pageContext.include("templates/header.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            // Include footer
            JspWriter out = pageContext.getOut();
            pageContext.include("templates/footer.jsp");
            out.print("</body>");
            out.print("</html>");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
