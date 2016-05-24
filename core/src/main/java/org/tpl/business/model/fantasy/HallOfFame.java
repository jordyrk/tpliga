package org.tpl.business.model.fantasy;

/**
 * Created with IntelliJ IDEA.
 * User: jordyr
 * Date: 10/28/13
 * Time: 8:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class HallOfFame {
    private FantasyTeam fantasyTeam;
    private int points;
    private FantasySeason fantasySeason;

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public FantasySeason getFantasySeason() {
        return fantasySeason;
    }

    public void setFantasySeason(FantasySeason fantasySeason) {
        this.fantasySeason = fantasySeason;
    }
}
