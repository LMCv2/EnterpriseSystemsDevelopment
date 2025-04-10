package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

public class BodyTag extends BodyTagSupport {
    @Setter
    private String title;

    @Override
    public int doStartTag() throws JspException {
        try {
            if (pageContext.getSession().getAttribute("user") == null &&
                    !((HttpServletRequest) pageContext.getRequest()).getRequestURI().endsWith("/sign-up.jsp") &&
                    !((HttpServletRequest) pageContext.getRequest()).getRequestURI().endsWith("/sign-in.jsp")
                    ) {
                ((jakarta.servlet.http.HttpServletResponse) pageContext.getResponse()).sendRedirect("index.jsp");
                return SKIP_PAGE;
            }
            // Include header
            JspWriter out = pageContext.getOut();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            pageContext.getRequest().setAttribute("title", title);
            pageContext.include("/templates/head.jsp");
            out.println("</head>");
            out.println("<body>");
            pageContext.include("/templates/header.jsp");
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
            pageContext.include("/templates/footer.jsp");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
