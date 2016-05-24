package org.tpl.business.model;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 20:43:59
 */
public class PlayerStats extends AbstractPlayerStats{
    private Player player;
    private Match match;
    private Team team;
    private int temporarlyId;

    private static int counter;

    public PlayerStats() {
        temporarlyId = counter++;

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getTemporarlyId() {
        return temporarlyId;
    }
    public boolean isSave(){
        if (this.getTeam() == null || this.getPlayer() == null || this.getMatch() == null){
            return false;
        }
        if (this.getPlayedMinutes() < 0){
            return false;
        }
        if (this.getTeam().getTeamId() != this.getPlayer().getTeam().getTeamId()){
            return false;
        }
        if(player.isNew()){
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerStats that = (PlayerStats) o;

        if (player != null ? !player.equals(that.player) : that.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }
}
