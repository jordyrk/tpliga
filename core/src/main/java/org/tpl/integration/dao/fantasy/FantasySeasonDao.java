package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasySeason;

import java.util.List;
/*
Created by jordyr, 26.nov.2010

*/

public interface FantasySeasonDao {

    void saveOrUpdateFantasySeason(FantasySeason fantasySeason);

    FantasySeason getFantasySeasonById(int id);

    FantasySeason getDefaultFantasySeason();

    public List<FantasySeason> getAllFantasySeasons();
}
