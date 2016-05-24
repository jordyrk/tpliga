package org.tpl.business.model;


import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Koren
 * Date: 02.jun.2010
 * Time: 19:58:01
 */
public class Player {

    private int playerId = -1;
    private String firstName;
    private String lastName;
    private PlayerPosition playerPosition = PlayerPosition.UNKNOWN;
    private int price;
    private Team team;
    private int shirtNumber;
    private int externalId;
    private int soccerNetId;
    private List<String> aliases = LazyList.decorate(new ArrayList<String>(), FactoryUtils.instantiateFactory(String.class));

    public Player() {
    }

    public Player(Player player) {
        setAlias(player.getAliases());
        setExternalId(player.getExternalId());
        setFirstName(player.getFirstName());
        setLastName(player.getLastName());
        setPlayerId(player.getPlayerId());
        setPlayerPosition(player.getPlayerPosition());
        setPrice(player.getPrice());
        setShirtNumber(player.getShirtNumber());
        setSoccerNetId(player.getSoccerNetId());
        setTeam(player.getTeam());
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PlayerPosition getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(PlayerPosition playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public boolean isNew(){
        return playerId == -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerId != player.playerId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return playerId;
    }

    public void setAlias(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void addAlias(String alias){
        if(aliases == null){
            aliases = new ArrayList<String>();
        }
        aliases.add(alias);
    }

    public int getSoccerNetId() {
        return soccerNetId;
    }

    public void setSoccerNetId(int soccerNetId) {
        this.soccerNetId = soccerNetId;
    }
}
