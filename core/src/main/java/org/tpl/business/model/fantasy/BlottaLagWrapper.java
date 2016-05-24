package org.tpl.business.model.fantasy;

/**
 * Created with IntelliJ IDEA.
 * User: jordyr
 * Date: 12/28/13
 * Time: 8:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class BlottaLagWrapper {

    FantasyTeamRound fantasyTeamRound;
    int oppositionTeamId;


    public void setFantasyTeamRound(FantasyTeamRound fantasyTeamRound) {
        this.fantasyTeamRound = fantasyTeamRound;
    }

    public void setOppositionTeamId(int oppositionTeamId) {
        this.oppositionTeamId = oppositionTeamId;
    }

    public FantasyTeamRound getFantasyTeamRound() {
        return fantasyTeamRound;
    }

    public int getOppositionTeamId() {
        return oppositionTeamId;
    }
}


