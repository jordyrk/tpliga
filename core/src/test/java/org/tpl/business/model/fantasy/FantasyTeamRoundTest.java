package org.tpl.business.model.fantasy;



import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.Team;

import static junit.framework.Assert.assertEquals;

public class FantasyTeamRoundTest {

    private FantasyTeamRound fantasyTeamRound;

    @Before
    public void setUp() throws Exception {
        fantasyTeamRound = new FantasyTeamRound();
    }

    @Test
    public void shouldHaveCorrectNumberOfPlayersInAllPositions() throws Exception {
        fantasyTeamRound.setFormation(Formation.FIVETHREETWO);
        fantasyTeamRound.setFantasyRound(new FantasyRound());
        fantasyTeamRound.getFantasyRound().setFantasySeason(new FantasySeason());
        fantasyTeamRound.addPlayer(FormationPosition.DEFENDER1,createPlayer(PlayerPosition.DEFENDER, 1,1));
        fantasyTeamRound.addPlayer(FormationPosition.DEFENDER2,createPlayer(PlayerPosition.DEFENDER, 1,2));
        fantasyTeamRound.addPlayer(FormationPosition.DEFENDER3,createPlayer(PlayerPosition.DEFENDER, 1,3));
        fantasyTeamRound.addPlayer(FormationPosition.DEFENDER4,createPlayer(PlayerPosition.DEFENDER, 2,4));
        fantasyTeamRound.addPlayer(FormationPosition.DEFENDER5,createPlayer(PlayerPosition.DEFENDER, 2,5));
        fantasyTeamRound.addPlayer(FormationPosition.MIDFIELDER1,createPlayer(PlayerPosition.MIDFIELDER,2,6));
        fantasyTeamRound.addPlayer(FormationPosition.MIDFIELDER2,createPlayer(PlayerPosition.MIDFIELDER,3,7));
        fantasyTeamRound.addPlayer(FormationPosition.MIDFIELDER3,createPlayer(PlayerPosition.MIDFIELDER,3,8));
        fantasyTeamRound.addPlayer(FormationPosition.STRIKER1,createPlayer(PlayerPosition.STRIKER,4,11));
        fantasyTeamRound.addPlayer(FormationPosition.STRIKER2,createPlayer(PlayerPosition.STRIKER,4,12));


        fantasyTeamRound.setFormation(Formation.FOURFOURTWO);
        fantasyTeamRound.addPlayer(FormationPosition.MIDFIELDER4,createPlayer(PlayerPosition.MIDFIELDER,3,9));
        assertEquals(4,fantasyTeamRound.getDefenders().size());
        assertEquals(4,fantasyTeamRound.getMidfielders().size());
        assertEquals(2,fantasyTeamRound.getStrikers().size());

        fantasyTeamRound.setFormation(Formation.THREEFIVETWO);
        fantasyTeamRound.addPlayer(FormationPosition.MIDFIELDER5,createPlayer(PlayerPosition.MIDFIELDER,4,10));
        assertEquals(3,fantasyTeamRound.getDefenders().size());
        assertEquals(5,fantasyTeamRound.getMidfielders().size());
        assertEquals(2,fantasyTeamRound.getStrikers().size());

        fantasyTeamRound.setFormation(Formation.FOURFIVEONE);
        assertEquals(4,fantasyTeamRound.getDefenders().size());
        assertEquals(5,fantasyTeamRound.getMidfielders().size());
        assertEquals(1,fantasyTeamRound.getStrikers().size());

        fantasyTeamRound.setFormation(Formation.FOURTWOFOUR);
        fantasyTeamRound.addPlayer(FormationPosition.STRIKER3,createPlayer(PlayerPosition.STRIKER,5,13));
        fantasyTeamRound.addPlayer(FormationPosition.STRIKER4,createPlayer(PlayerPosition.STRIKER,5,14));
        assertEquals(4,fantasyTeamRound.getDefenders().size());
        assertEquals(2,fantasyTeamRound.getMidfielders().size());
        assertEquals(4,fantasyTeamRound.getStrikers().size());

        fantasyTeamRound.setFormation(Formation.FIVETHREETWO);
        assertEquals(5,fantasyTeamRound.getDefenders().size());
        assertEquals(3,fantasyTeamRound.getMidfielders().size());
        assertEquals(2,fantasyTeamRound.getStrikers().size());

        fantasyTeamRound.setFormation(Formation.FOURTHREETHREE);
        assertEquals(4,fantasyTeamRound.getDefenders().size());
        assertEquals(3,fantasyTeamRound.getMidfielders().size());
        assertEquals(3,fantasyTeamRound.getStrikers().size());
    }

    @Test
    public void shouldAddOnePlayer() throws Exception {
        Player player = createPlayer(PlayerPosition.GOALKEEPER, 1,1);
        fantasyTeamRound.addPlayer(FormationPosition.GOALKEEPER,player);
    }

    @Test
    public void shouldNotAddPlayerWhenPositionIsInvalid() throws Exception {
        fantasyTeamRound.setFormation(Formation.FIVETHREETWO);
        Player player = createPlayer(PlayerPosition.STRIKER, 1,1);
        fantasyTeamRound.addPlayer(FormationPosition.STRIKER3,player);
        assertEquals(0, fantasyTeamRound.getTeamPrice());
    }

    private Player createPlayer(PlayerPosition playerPosition, int teamId, int playerId){
        Player player = new Player();
        player.setPlayerPosition(playerPosition);
        player.setTeam(new Team());
        player.getTeam().setTeamId(teamId);
        player.setPrice(0);
        player.setPlayerId(playerId);
        return player;
    }
}
