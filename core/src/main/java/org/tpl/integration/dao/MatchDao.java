package org.tpl.integration.dao;

import org.tpl.business.model.Match;
import org.tpl.business.model.Player;
import org.tpl.business.model.fantasy.rule.PlayerRule;
import org.tpl.business.model.fantasy.rule.TeamRule;
import org.tpl.business.model.search.MatchSearchResult;
import org.tpl.business.model.search.SearchTerm;

import java.util.List;
/*
Created by jordyr, 16.okt.2010

*/

public interface MatchDao {

    void saveOrUpdateMatch(Match match);

    Match getMatchById(int id);

    MatchSearchResult getMatchBySearchTerm(SearchTerm term);

    int queryForInt(String field, int matchId );

    List<Match> getMatchesWithRounds(String commaSeparatedString, boolean isPlayerStatsSaved);
}
