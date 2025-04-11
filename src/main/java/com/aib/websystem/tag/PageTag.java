package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import lombok.Setter;

public class PageTag extends BodyTagSupport {
    @Setter
    private String title;

    @Override
    public void setPageContext(PageContext pageContext) {
        if (pageContext.getSession().getAttribute("current_account") != null) {
            try {
                ((HttpServletResponse) pageContext.getResponse()).sendRedirect("/dashboard");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.pageContext = pageContext;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
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
            pageContext.include("/templates/page_end.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
