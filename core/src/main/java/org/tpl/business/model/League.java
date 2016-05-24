package org.tpl.business.model;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 20:23:01
 */
public class League {
    private int leagueId = -1;
    private String shortName;
    private String fullName;


    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
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
        if(leagueId == -1)return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        League league = (League) o;

        if (fullName != null ? !fullName.equals(league.fullName) : league.fullName != null) return false;
        if (shortName != null ? !shortName.equals(league.shortName) : league.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shortName != null ? shortName.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}
