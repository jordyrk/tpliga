package org.tpl.business.service.fantasy;

import org.tpl.business.model.fantasy.Whore;

import java.util.List;

/**
 * Created by jordyr on 5/4/14.
 */
public interface WhoreService {

    public List<Whore> getWhores(int fantasyLeagueId, int fantasyRoundId);
}
