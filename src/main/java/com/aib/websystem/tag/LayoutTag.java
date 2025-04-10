package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;

public class LayoutTag extends PageTag {

    @Override
    public void setPageContext(PageContext pageContext) {
        if (pageContext.getSession().getAttribute("current_account") == null) {
            try {
                ((HttpServletResponse) pageContext.getResponse()).sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.setPageContext(pageContext);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title + " | System");
    }

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        try {
            pageContext.include("/templates/layout_start.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.include("/templates/layout_end.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return super.doEndTag();
    }
}
