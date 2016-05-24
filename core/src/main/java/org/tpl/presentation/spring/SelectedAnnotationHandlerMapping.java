package org.tpl.presentation.spring;

import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: jordyr
 * Date: 07.mai.2010
 * Time: 11:17:58
 * To change this template use File | Settings | File Templates.
 */
public class SelectedAnnotationHandlerMapping extends DefaultAnnotationHandlerMapping {
    private List<Pattern> urlPatterns;

    public void setUrls(List<String> urls) {
        urlPatterns = new ArrayList<Pattern>();
        for (String url : urls) {
            urlPatterns.add(Pattern.compile(url));
        }
    }

    public String[] getFiltered(String urls[]) {
        if (urls == null) {
            return null;
        }
        List<String> urlList = new ArrayList<String>();
        int i = urls.length;
        for (String str : urls) {
            for (Pattern urlPattern : urlPatterns) {
                try {
                    if (urlPattern.matcher(str).find()) {
                        urlList.add(str);
                        break;
                    }
                } catch (Exception e) {

                }
            }
        }
        return urlList.toArray(new String[urlList.size()]);
    }

    protected String[] determineUrlsForHandler(String s) {
        return getFiltered(super.determineUrlsForHandler(s));
    }
}

