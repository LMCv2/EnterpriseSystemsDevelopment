package com.aib.websystem.tag;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;

public class LayoutTag extends PageTag {
    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();
        try {
            // Include nav
            JspWriter out = pageContext.getOut();
            pageContext.include("/templates/nav.jsp");
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            // Include nav
            JspWriter out = pageContext.getOut();
            out.println("</div>");
            out.println("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doEndTag();
    }
}
