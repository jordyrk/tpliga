package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasyMatch;
import org.tpl.business.model.fantasy.FantasyMatchType;
import org.tpl.business.model.search.FantasyMatchSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.util.List;

public interface FantasyMatchDao {

    void saveOrUpdateFantasyMatch(FantasyMatch fantasyMatch);

    FantasyMatch getFantasyMatchById(int fantasyMatchId);

    List<FantasyMatch> getMatchByRoundId(int fantasyRoundId);

    List<FantasyMatch> getMatchByLeagueId(int fantasyLeagueId);

    List<FantasyMatch> getMatchByLeagueIdAndRoundId(int fantasyLeagueId,int fantasyRoundId);

    List<FantasyMatch> getMatchByCupId(int fantasyCupId);

    List<FantasyMatch> getMatchByCupIdAndRoundId(int fantasyCupId,int fantasyRoundId);

    List<FantasyMatch> getMatchByCupGroupId(int fantasyCupGroupId);

    List<FantasyMatch> getMatchByCupGroupIdAndRoundId(int fantasyCupGroupId,int fantasyRoundId);

    FantasyMatchSearchResult getFantasyMatchBySearchTerm(SearchTerm term, FantasyMatchType fantasyMatchType);

    boolean fantasyCupHasMatches(int fantasyCupId);

    boolean fantasyLeagueHasMatches(int fantasyLeagueId);

    boolean fantasyCupGroupHasMatches(int fantasyCupGroupId);

    int getMaxRoundIdForCupInMatch(int fantasyCupId);

    void deleteFantasyMatch(int id);

    List<FantasyMatch> getFantasyMatchByRoundsAndIsPlayed(String commaSeparatedStringFromRounds, boolean played);
}
