package org.tpl.business.model.fantasy;

import org.tpl.business.model.Player;

public class PlayerUsage extends Player{

     private int numberOfUsages;


    public void addPlayer(Player player) {
        super.setAlias(player.getAliases());
        super.setExternalId(player.getExternalId());
        super.setFirstName(player.getFirstName());
        super.setLastName(player.getLastName());
        super.setPlayerId(player.getPlayerId());
        super.setPlayerPosition(player.getPlayerPosition());
        super.setPrice(player.getPrice());
        super.setShirtNumber(player.getShirtNumber());
        super.setSoccerNetId(player.getSoccerNetId());
        super.setTeam(player.getTeam());
    }

    public int getNumberOfUsages() {
        return numberOfUsages;
    }

    public void setNumberOfUsages(int numberOfUsages) {
        this.numberOfUsages = numberOfUsages;
    }
}
