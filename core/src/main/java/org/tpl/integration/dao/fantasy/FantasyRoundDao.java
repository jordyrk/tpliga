package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyRound;

import java.util.List;
/*
Created by jordyr, 26.nov.2010

*/

public interface FantasyRoundDao {

    void saveOrUpdateFantasyRound(FantasyRound fantasyRound);

    FantasyRound getFantasyRoundById(int fantasyRoundId);

    boolean addMatchToRound(int leaguematchId, int fantasyRoundId);

    boolean removeMatchFromRound(int leaguematchId, int fantasyRoundId);

    List<Integer> getMatchIdsForRound(int fantasyRoundId);

    List<FantasyRound> getFantasyRoundByFantasySeasonId(int fantasySeasonId);

    List<FantasyRound> getFantasyRoundByFantasyLeagueId(int fantasyLeagueId);

    List<FantasyRound> getFantasyRoundByFantasyCupId(int fantasyCupId);

    List<FantasyRound> getFantasyRoundByFantasyCupGroupId(int fantasyCupGroupId);

    FantasyRound getFantasyRoundByNumberAndSeason(int round, int fantasySeasonId);
}
