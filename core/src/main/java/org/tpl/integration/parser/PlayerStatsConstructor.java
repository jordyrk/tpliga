package org.tpl.integration.parser;

import org.tpl.business.model.*;
import org.tpl.business.service.fantasy.exception.PlayerStatsCreationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;
import java.util.List;

/**
 * User: Koren
 * Date: 27.jun.2010
 * Time: 23:01:04
 */
public interface PlayerStatsConstructor {

    public List<PlayerStats> getPlayerStats(Match match);
}
