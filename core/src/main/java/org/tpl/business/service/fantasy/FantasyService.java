package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;

import java.util.List;

/**
 * User: Koren
 * Date: 08.aug.2010
 * Time: 18:18:01
 */
public interface FantasyService {

    void saveOrUpdateFantasyManager(FantasyManager fantasyManager);

    boolean isUserIdRegistered(String userId);

    FantasyManager getFantasyManagerById(int managerId);

    FantasyManager getFantasyManagerByUserId(String userId);

    void deleteFantasyManager(int managerId);

    void saveOrUpdateFantasySeason(FantasySeason fantasySeason);

    FantasySeason getFantasySeasonById(int id);

    FantasySeason getDefaultFantasySeason();

    FantasySeason getFantasySeasonByRoundId(int fantasyRoundId);

    List<FantasySeason> getAllFantasySeasons();

    void saveOrUpdateFantasyRound(FantasyRound fantasyRound);

    FantasyRound getFantasyRoundById(int fantasyRoundId);

    FantasyRound getFantasyRoundByNumberInDefaultSeason(int fantasyRoundNumber);

    FantasyRound getCurrentFantasyRoundBySeasonId(int fantasySeasonId);

    FantasyRound getPreviousFantasyRound(int fantasyRoundId, int fantasySeasonId);

    List<FantasyRound> getFantasyRoundByFantasySeasonId(int fantasySeasonId);

    List<FantasyRound> getFantasyRoundByFantasyLeagueId(int fantasyLeagueId);

    List<FantasyRound> getFantasyRoundByFantasyCupId(int fantasyCupId);

    List<FantasyRound> getFantasyRoundByFantasyCupGroupId(int fantasyCupGroupId);

    boolean addMatchToRound(int leaguematchId, int fantasyRoundId);

    boolean removeMatchFromRound(int leaguematchId, int fantasyRoundId);

    List<Integer> getMatchIdsForRound(int fantasyRoundId);

    void saveOrUpdateFantasyTeam(FantasyTeam fantasyTeam);

    FantasyTeam getFantasyTeamById(int teamId);

    List<FantasyTeam> getAllFantasyTeams();

    List<FantasyTeam> getFantasyTeamByManagerId(int managerId);

    List<FantasyTeam> getFantasyTeamByFantasyLeagueId(int leagueId);

    List<FantasyTeam> getFantasyTeamByFantasyCupId(int cupId);

    List<FantasyTeam> getFantasyTeamByFantasyCupGroupId(int cupGroupId);

    void saveOrUpdateFantasyTeamRound(FantasyTeamRound fantasyTeamRound);

    FantasyTeamRound getFantasyTeamRoundByIds(int fantasyTeamId, int fantasyRoundId);

    List<FantasyTeamRound> getFantasyTeamRoundByTeamIdInCurrentSeason(int fantasyTeamId);

    List<FantasyTeamRound> getFantasyTeamRoundsByTeamIdUntilCurrentRound(int fantasyTeamId, int fantasySeasonId);

    List<FantasyTeamRound> getFantasyTeamRoundsByFantasyRoundId(int fantasyRoundId);

    List<FantasyTeamRound> getOfficialFantasyTeamRounds(int fantasyRoundId);

    int getNumberOfOfficialWhenRoundIsClosedTeams(int fantasyRoundId);

    int getNumberOfOfficialTeams(int fantasyRoundId);

    FantasyTeamRound getLastChangedTeamRound(int fantasyTeamId, int fantasySeasonId);

    void updateFantasyTeamPoints(int fantasyRoundId);

    void updateOfficialFantasyTeams(int fantasyRoundId);

    List<FantasyManager> getAllFantasyManagers();

    List<FantasyManager> getAllActiveFantasyManagers();
}
