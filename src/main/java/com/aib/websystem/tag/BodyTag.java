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
            out.print("<!doctype html>");
            out.print("<html>");
            out.print("<head>");
            out.print("<title>" + title + "</title>");
            out.print("<meta charset=\"UTF-8\" />");
            out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
            out.print("<script src=\"https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4\"></script>");
            out.print("</head>");
            out.print("<body>");
            pageContext.include("pages/header.jsp");
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
            pageContext.include("pages/footer.jsp");
            out.print("</body>");
            out.print("</html>");
        } catch (IOException | ServletException e) {
        }
        return EVAL_PAGE;
    }
}
