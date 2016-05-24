package org.tpl.taglib;

import no.kantega.commons.log.Log;
import no.kantega.publishing.common.data.Multimedia;
import no.kantega.publishing.common.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.service.fantasy.FantasyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FantasyTeamImageTag extends TagSupport {


    private int multiMediaId;
    private int height;
    private int width;
    private String cssClass;
    private String id;

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpServletRequest request  = (HttpServletRequest)pageContext.getRequest();

        String imgSrc = request.getContextPath() + "/";
        String alt = "";

        MultimediaService mms = new MultimediaService(request);

        Multimedia profileImage =  mms.getMultimedia(multiMediaId);
        if (profileImage != null) {
            imgSrc = profileImage.getUrl() + "?height="+height+"&amp;width="+width;
        }

        try {
            StringBuilder tag = new StringBuilder().append("<img src=\"").append(imgSrc).append("\" alt=\"").append(alt).append("\" title=\"").append(alt).append("\"");
            if (cssClass != null) {
                tag.append(" class=\"").append(cssClass).append("\"");
            }
            if (id != null) {
                tag.append(" id=\"").append(id).append("\"");
            }
            tag.append(">");

            out.write(tag.toString());
        } catch (IOException e) {
            Log.error(this.getClass().getName(), e, null, null);
        }

        return SKIP_BODY;
    }

    public void setMultiMediaId(int multiMediaId) {
        this.multiMediaId = multiMediaId;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setId(String id) {
        this.id = id;
    }
}
