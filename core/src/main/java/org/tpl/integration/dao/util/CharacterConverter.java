package org.tpl.integration.dao.util;

import java.util.HashMap;

public class CharacterConverter {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    public CharacterConverter() {
        map.put(161,105);
        map.put(192,65);
        map.put(193,65);
        map.put(194,65);
        map.put(195,65);
        map.put(196,65);
        map.put(199,67);
        map.put(200,69);
        map.put(201,69);
        map.put(202,69);
        map.put(203,69);
        map.put(204,73);
        map.put(205,73);
        map.put(206,73);
        map.put(207,73);
        map.put(209,78);
        map.put(210,79);
        map.put(211,79);
        map.put(212,79);
        map.put(213,79);
        map.put(214,79);
        map.put(217,85);
        map.put(218,85);
        map.put(219,85);
        map.put(220,85);
        map.put(221,89);
        map.put(224,97);
        map.put(225,97);
        map.put(226,97);
        map.put(227,97);
        map.put(228,97);
        map.put(231,99);
        map.put(232,101);
        map.put(233,101);
        map.put(234,101);
        map.put(235,101);
        map.put(281,101);
        map.put(236,105);
        map.put(237,105);
        map.put(238,105);
        map.put(239,105);
        map.put(241,110);
        map.put(242,111);
        map.put(243,111);
        map.put(244,111);
        map.put(245,111);
        map.put(246,111);
        map.put(249,117);
        map.put(250,117);
        map.put(251,117);
        map.put(252,117);
        map.put(253,121);
        map.put(255,121);
    }

    public String convertString(String string){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < string.length(); i ++){
            char c = string.charAt(i);
            Integer integer = map.get((int)c);
            if(integer != null ){
                char newChar = (char) integer.intValue();
                sb.append(newChar);
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
