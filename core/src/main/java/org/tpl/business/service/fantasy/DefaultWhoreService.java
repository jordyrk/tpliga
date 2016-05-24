package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.Whore;
import org.tpl.integration.dao.fantasy.WhoreDao;

import java.util.List;

/**
 * Created by jordyr on 5/4/14.
 */
public class DefaultWhoreService implements WhoreService {

    @Autowired
    WhoreDao whoreDao;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyService fantasyService;


    @Override
    public List<Whore> getWhores(int fantasyLeagueId, int fantasyRoundId) {
        List<Whore> whoreList = whoreDao.getWhores(fantasyLeagueId, fantasyRoundId);
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        for(int i = 0; i < whoreList.size(); i++){
            Whore whore = whoreList.get(i);
            whore.setFantasyLeague(fantasyLeague);
            updateDependencies(whore);
            whore.setCompetitionPositionWithinLeague(i+1);
        }
        return whoreList;


    }

    private void updateDependencies(Whore whore) {
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(whore.getFantasyTeam().getTeamId());
        whore.setFantasyTeam(fantasyTeam);
    }
}
