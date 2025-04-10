package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

public class PageTag extends BodyTagSupport {
    @Setter
    private String title;

    @Override
    public int doStartTag() throws JspException {
        try {
            // if (pageContext.getSession().getAttribute("user") == null &&
            //         !((HttpServletRequest) pageContext.getRequest()).getRequestURI().endsWith("/sign-up.jsp") &&
            //         !((HttpServletRequest) pageContext.getRequest()).getRequestURI().endsWith("/sign-in.jsp")
            //         ) {
            //     ((jakarta.servlet.http.HttpServletResponse) pageContext.getResponse()).sendRedirect("index.jsp");
            //     return SKIP_PAGE;
            // }
            // Include header
            pageContext.getRequest().setAttribute("title", title);
            pageContext.include("/templates/page_start.jsp");
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
            pageContext.include("/templates/page_end.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
