package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.HallOfFame;
import org.tpl.integration.dao.fantasy.HallOfFameDao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jordyr
 * Date: 10/28/13
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultHallOfFameService implements HallOfFameService {
    @Autowired
    HallOfFameDao hallOfFameDao;


    @Autowired
    FantasyService fantasyService;

    @Autowired


    @Override
    public List<HallOfFame> getHallOfFameList() {
        List<HallOfFame> hallOfFameList = hallOfFameDao.getHallOfFameList();
        for(HallOfFame hallOfFame: hallOfFameList){
            updateDependencies(hallOfFame);

        }

        return hallOfFameList;
    }

    private void updateDependencies(HallOfFame hallOfFame) {
        FantasySeason fantasySeason = fantasyService.getFantasySeasonById(hallOfFame.getFantasySeason().getFantasySeasonId());
        hallOfFame.setFantasySeason(fantasySeason);
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(hallOfFame.getFantasyTeam().getTeamId());
        hallOfFame.setFantasyTeam(fantasyTeam);

    }

}
