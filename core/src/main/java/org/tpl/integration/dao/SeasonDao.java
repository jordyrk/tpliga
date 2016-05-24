package org.tpl.integration.dao;

import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Season;

import java.util.List;
/*
Created by jordyr, 16.okt.2010

*/

public interface SeasonDao {
    public List<Season> getSeasonsByLeagueId(int leagueId);

    public List<Season> getAllSeasons();

    void saveOrUpdateSeason(Season season);

    Season getSeasonById(int id);

    Season getDefaultSeason();

    void setDefaultSeason(Season season);

    void saveOrUpdateLeagueRound(LeagueRound leagueRound);

    List<LeagueRound> getAllLeagueRounds();

    LeagueRound getLeagueRoundById(int leagueRoundId);

    List<LeagueRound> getLeagueRoundBySeasonId(int seasonId);

    LeagueRound getLeagueRoundByRoundAndSeason(Season season, int round);

    LeagueRound getFirstLeagueRoundForSeason(int seasonId);


}
