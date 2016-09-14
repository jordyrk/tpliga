package org.tpl.business.model.statsfc;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jordyr on 9/4/16.
 */
public class DataHolder {

    private static Map<Integer,Map<String,Object>> dataMap = new HashMap<>();

    public static void clear(){
        dataMap = new HashMap<>();
    }

    public static void add(int matchId, Map<String,Object> data){
        dataMap.put(matchId,data);
    }

    public static Map<String,Object> get(int matchId){
        return dataMap.get(matchId);
    }
}
