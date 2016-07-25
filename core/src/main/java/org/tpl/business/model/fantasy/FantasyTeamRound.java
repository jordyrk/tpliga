package org.tpl.business.model.fantasy;

import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
Created by jordyr, 04.des.2010

*/

public class FantasyTeamRound {
    private final int DEFAULT_START_SUM = 0;
    private final int PENALTY_START_SUM = -5;

    private FantasyTeam fantasyTeam;
    private FantasyRound fantasyRound;
    private List<Player> playerList;
    private boolean official;
    private boolean officialWhenRoundIsClosed = true;
    private int points = 0;
    private Formation formation = Formation.FOURFOURTWO;
    private Map <FormationPosition,Player> playerMap = new HashMap<FormationPosition, Player>();
    private Map<FormationPosition,Player> temporaryPlayerMap = new HashMap<FormationPosition, Player>();
    private int startSum;

    public void addPlayer(FormationPosition formationPosition,Player player){
        if(formation.validPositionForFormation(formationPosition)){
            playerMap.put(formationPosition,player);
        }
    }

    public FantasyTeam getFantasyTeam() {
        return fantasyTeam;
    }

    public void setFantasyTeam(FantasyTeam fantasyTeam) {
        this.fantasyTeam = fantasyTeam;
    }

    public FantasyRound getFantasyRound() {
        return fantasyRound;
    }

    public void setFantasyRound(FantasyRound fantasyRound) {
        this.fantasyRound = fantasyRound;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public boolean isOfficialWhenRoundIsClosed() {
        return officialWhenRoundIsClosed;
    }

    public void setOfficialWhenRoundIsClosed(boolean officialWhenRoundIsClosed) {
        this.officialWhenRoundIsClosed = officialWhenRoundIsClosed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTotalPrice(){
        int sum = 0;
        for(Player player : getPlayerList()){
            sum += player.getPrice();
        }
        return sum;
    }

    public Player getGoalkeeper(){

        for(Player player: this.getPlayerList()){
            if(player == null){
                continue;
            }
            if(player.getPlayerPosition() == PlayerPosition.GOALKEEPER){
                return player;
            }
        }
        return new Player();
    }

    public List<Player> getPlayerList() {
        playerList = new ArrayList<Player>();
        for(int i = 1; i< 12; i++ ){
            playerList.add(getPlayerByFormationPosition(formation.getPosition(i)));
        }
        return playerList;
    }
    public List<PlayerInFormation> getPlayerInFormationList() {
        List<PlayerInFormation> playerList = new ArrayList<PlayerInFormation>();
        for(int i = 1; i< 12; i++ ){
            playerList.add(new PlayerInFormation(getPlayerByFormationPosition(formation.getPosition(i)),formation.getPosition(i)));
        }
        return playerList;
    }

    public List<Player> getDefenders(){
        return getPlayers(PlayerPosition.DEFENDER);
    }

    public List<Player> getMidfielders(){
        return getPlayers(PlayerPosition.MIDFIELDER);
    }

    public List<Player> getStrikers(){
        return getPlayers(PlayerPosition.STRIKER);
    }

    private List<Player> getPlayers(PlayerPosition playerPosition){
        List<Player> players = new ArrayList<Player>();

        for(Player player: this.getPlayerList()){
            if(player.getPlayerPosition() == playerPosition){
                players.add(player);
            }
        }
        return players;
    }

    public boolean isPlayersSelected(){
        for(Player player :this.getPlayerList()){
            if(!player.isNew()){
                return true;
            }
        }
        return false;
    }

    public int getTeamPrice(){
        int sum = 0;
        for(Player player :this.getPlayerList()){
            sum += player.getPrice();
        }
        return sum;
    }

    public boolean hasPlayer(int playerId){
        for(Player player :this.getPlayerList()){
            if(player.getPlayerId() == playerId){
                return true;
            }
        }
        return false;
    }

    public boolean canAddPlayerWithTeamId(int teamId){
        int counter = 0;
        for(Player player :this.getPlayerList()){
            if(player != null && !player.isNew() && player.getTeam() != null && player.getTeam().getTeamId() == teamId){
                counter ++;
            }
        }
        if(counter >= 3){
            return false;
        }else{
            return true;
        }
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation newFormation) {
        updatePostions(newFormation);
        this.formation = newFormation;

    }

    public Player getPlayerByFormationPosition(FormationPosition formationPosition){
        Player player = playerMap.get(formationPosition);
        if(player == null){
            player = new Player();
        }
        return player;
    }

    private void updatePostions(Formation newFormation){
        //Minste formasjon er 3-2-1
        initBasicFormation();
        switch (newFormation){
            case FIVETHREETWO:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER5);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER2);
                break;
            }case FOURFOURTWO:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER2);
                break;

            }case FOURFIVEONE:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER5);
                break;
            }case THREEFIVETWO:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER5);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER2);
                break;

            }case FOURTHREETHREE:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.MIDFIELDER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER2);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER3);
                break;

            }case FOURTWOFOUR:{
                movePlayerFromTempMapToPlayerMap(FormationPosition.DEFENDER4);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER2);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER3);
                movePlayerFromTempMapToPlayerMap(FormationPosition.STRIKER4);
                break;
            }
        }
    }

    private void initBasicFormation() {
        movePlayerFromPlayerMapToTempMap(FormationPosition.DEFENDER4);
        movePlayerFromPlayerMapToTempMap(FormationPosition.DEFENDER5);
        movePlayerFromPlayerMapToTempMap(FormationPosition.MIDFIELDER3);
        movePlayerFromPlayerMapToTempMap(FormationPosition.MIDFIELDER4);
        movePlayerFromPlayerMapToTempMap(FormationPosition.MIDFIELDER5);
        movePlayerFromPlayerMapToTempMap(FormationPosition.STRIKER2);
        movePlayerFromPlayerMapToTempMap(FormationPosition.STRIKER3);
        movePlayerFromPlayerMapToTempMap(FormationPosition.STRIKER4);
    }

    private void movePlayerFromTempMapToPlayerMap(FormationPosition formationPosition) {
        Player player = temporaryPlayerMap.get(formationPosition);
        if(canAddPlayerToTeam(player)){
            playerMap.put(formationPosition, player);
        }
        temporaryPlayerMap.remove(formationPosition);
    }

    private boolean canAddPlayerToTeam(Player player) {
        boolean canAddPlayer = false;

        if(player != null && player.getTeam() != null){
            canAddPlayer = canAddPlayerWithTeamId(player.getTeam().getTeamId()) && isPriceWithinRange(player) && !hasPlayer(player.getPlayerId());
        }

        return canAddPlayer;
    }

    private boolean isPriceWithinRange(Player player) {
        return (player.getPrice() + getTeamPrice() <= fantasyRound.getFantasySeason().getMaxTeamPrice());
    }

    private void movePlayerFromPlayerMapToTempMap(FormationPosition formationPosition) {
        Player player = playerMap.get(formationPosition);
        if(player != null){
            temporaryPlayerMap.put(formationPosition, player);
        }
        playerMap.remove(formationPosition);
    }

    public void removePlayerFromTeam(FormationPosition formationPosition) {
        playerMap.remove(formationPosition);
    }

    public boolean isWrongPosition(Player player, String playerPosition) {
        if(playerPosition.contains(player.getPlayerPosition().toString())){
            return false;
        }
        return true;
    }

    public int getStartSum() {
        for(Player player : getPlayerList()){
            if(player.isNew()){
                return PENALTY_START_SUM;
            }
        }
        return DEFAULT_START_SUM;
    }
}
