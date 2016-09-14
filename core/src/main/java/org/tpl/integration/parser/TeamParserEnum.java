package org.tpl.integration.parser;

/**
 * Created by jordyr on 9/14/16.
 */
public enum TeamParserEnum {
    HOME("home"),AWAY("away");
    private String location;

    TeamParserEnum(String location) {

        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
