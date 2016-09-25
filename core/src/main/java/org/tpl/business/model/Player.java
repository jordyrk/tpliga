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
        this.firstName = washName(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = washName(lastName);
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

    public void setFullName(String fullName){
        if(fullName!= null && fullName.length() > 0){
            if(fullName.contains(" ")){
                int lastSpaceIndex = fullName.lastIndexOf(" ");
                setFirstName(fullName.substring(0, lastSpaceIndex));
                setLastName( fullName.substring(lastSpaceIndex+1));


            }else{
                setLastName(fullName);
            }
        }

    }

    private String washName(String input){
        if(input != null){
            //input = input.replaceAll("'","");
            input = input.replaceAll("\\u00e0","a");
            input = input.replaceAll("\\u0107","c");
            input = input.replaceAll("\\u0130","I");
            input = input.replaceAll("\\u0141","L");
            input = input.replaceAll("\\u0144","n");
            input = input.replaceAll("\\u0161","s");
            input = input.replaceAll("\\u011b","e");
            input = input.replaceAll("\\u011f","g");
            input = input.replaceAll("\\u010c","C");
            input = input.replaceAll("\\u00c9","E");
            input = input.replaceAll("\\u00d6","O");
            input = input.replaceAll("\\u00e1","a");
            input = input.replaceAll("\\u00e8","e");
            input = input.replaceAll("\\u00e9","e");
            input = input.replaceAll("\\u00ed","i");
            input = input.replaceAll("\\u00f0","d");
            input = input.replaceAll("\\u00f3","o");
            input = input.replaceAll("\\u00f6","o");
            input = input.replaceAll("\\u00f8","o");
            input = input.replaceAll("\\u00fa","u");
            input = input.replaceAll("\\u00fc","u");
            input = input.replaceAll("\\u00f1","n");

        }
        return input;




    }
}
