package org.tpl.business.model.fantasy;

public enum FantasyMatchType {
    LEAGUE("fantasyleagueid","fantasy_league_match"),
    CUP("fantasycupid","fantasy_cup_match"),
    CUPGROUP("fantasycupgroupid","fantasy_cup_group_match"),
    UNKNOWN("","");
    private String field;
    private String table;

    FantasyMatchType(String field, String table) {
        this.field = field;
        this.table = table;
    }

    public String getField() {
        return field;
    }

    public String getTable() {
        return table;
    }
}
