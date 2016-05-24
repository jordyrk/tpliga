package org.tpl.business.model;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 19:58:08
 */
public class Team {
    private int teamId = -1;
    private int externalId;
    private String shortName;
    private String fullName;
    private List<String> aliases = LazyList.decorate(new ArrayList<String>(), FactoryUtils.instantiateFactory(String.class));
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isNew(){
        if(teamId == -1)return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (fullName != null ? !fullName.equals(team.fullName) : team.fullName != null) return false;
        if (shortName != null ? !shortName.equals(team.shortName) : team.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shortName != null ? shortName.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(String alias){
        if(aliases == null){
            aliases = new ArrayList<String>();
        }
        aliases.add(alias);
    }
}
