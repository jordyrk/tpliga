package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.FantasyTeamRound;

import java.util.List;
/*
Created by jordyr, 26.nov.2010

*/

public interface FantasyTeamDao {

    void saveOrUpdateFantasyTeam(FantasyTeam fantasyTeam);

    FantasyTeam getFantasyTeamById(int teamId);

    List<FantasyTeam> getAllFantasyTeams();

    List<FantasyTeam> getFantasyTeamByManagerId(int managerId);

    List<FantasyTeam> getFantasyTeamByFantasyLeagueId(int leagueId);

    List<FantasyTeam> getFantasyTeamByFantasyCupId(int cupId);

    List<FantasyTeam> getFantasyTeamByFantasyCupGroupId(int cupGroupId);

    void saveOrUpdateFantasyTeamRound(FantasyTeamRound fantasyTeamRound);

    FantasyTeamRound getFantasyTeamRoundByIds(int fantasyTeamId, int fantasyRoundId);

    List<FantasyTeamRound> getFantasyTeamRoundByTeamId(int fantasyTeamId);

    List<FantasyTeamRound> getFantasyTeamRoundByRoundIdRange(String range, int fantasyTeamId, String order);

    List<FantasyTeamRound> getFantasyTeamRoundsByFantasyRoundId(int fantasyRoundId);

    List<FantasyTeamRound> getOfficialFantasyTeamRounds(int fantasyRoundId);

    int getNumberOfOfficialTeams(int fantasyRoundId);

    int getNumberOfOfficialWhenRoundIsClosedTeams(int fantasyRoundId);

    void deleteFantasyTeam(int teamId);

    void deleteFantasyTeamRound(int teamId);

    void updateFantasyTeamRoundsWhenRoundIsClosed(int fantasyRoundId);
}
