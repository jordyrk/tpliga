package org.tpl.business.util;

import java.util.List;

public class Splitter {

    public static String createCommaSeparetedString(List<String> list) {
        StringBuilder sb=  new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            String element = list.get(i);
            if(element != null && element.trim().length() > 0){
                if(i != 0){
                    sb.append(",");
                }
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }
}
