package com.aib.websystem.tag;

import java.io.StringWriter;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class BodyTag extends SimpleTagSupport {
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();

            // Include header
            out.print("<!doctype html>");
            out.print("<html>");
            out.print("<head>");
            out.print("<meta charset=\"UTF-8\" />");
            out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
            // out.print("<script src=\"https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4\"></script>");
            out.print("</head>");
            out.print("<body>");
            pageContext.include("page/header.jsp");

            // Include body
            StringWriter sw = new StringWriter();
            getJspBody().invoke(sw);
            out.println(sw.toString());

            // Include footer
            pageContext.include("page/footer.jsp");
            out.print("</body>");
            out.print("</html>");
        } catch (Exception e) {
        }
    }
}
