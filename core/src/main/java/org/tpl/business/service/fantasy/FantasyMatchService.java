package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.model.fantasy.FantasyMatchType;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.search.FantasyMatchSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.util.List;

public interface FantasyMatchService {

    void saveOrUpdateFantasyMatch(FantasyMatch fantasyMatch);

    FantasyMatch getFantasyMatchById(int fantasyMatchId);

    List<FantasyMatch> getFantasyMatchByRoundId(int fantasyRoundId);

    List<FantasyMatch> getFantasyMatchByLeagueId(int fantasyLeagueId);

    List<FantasyMatch> getFantasyMatchByLeagueIdAndRoundId(int fantasyLeagueId, int fantasyRoundId);

    List<FantasyMatch> getFantasyMatchByCupId(int fantasyCupId);

    List<FantasyMatch> getFantasyMatchByCupIdAndRoundId(int fantasyCupId, int fantasyRoundId);

    List<FantasyMatch> getFantasyMatchByCupGroupId(int fantasyCupGroupId);

    List<FantasyMatch> getFantasyMatchByCupGroupIdAndRoundId(int fantasyCupGroupId, int fantasyRoundId);

    void deleteFantasyMatchForLeague(int fantasyLeagueId);

    void deleteFantasyMatchForCup(int fantasyCupId);

    void deleteFantasyMatchForCupGroup(int fantasyCupGroupId);

    void updateMatchScore(int fantasyRoundId);

    FantasyMatchSearchResult getFantasyMatchBySearchTerm(SearchTerm term, FantasyMatchType fantasyMatchType);

    boolean fantasyCupHasMatches(int fantasyCupId);

    boolean fantasyLeagueHasMatches(int fantasyLeagueId);

    boolean fantasyCupGroupHasMatches(int fantasyCupGroupId);

    List<FantasyMatch> getLastRoundMatchesForCup(int fantasyCupId);

    FantasyTeam determineCupMatchWinner(FantasyMatch fantasyMatch, int fantasyCupId);

    List<FantasyMatch> getFantasyMatches(int fantasySeasonId, boolean isPlayed);

    List<FantasyMatch> getNextMatches(int fantasyTeamId, int fantasyRoundId);

}
