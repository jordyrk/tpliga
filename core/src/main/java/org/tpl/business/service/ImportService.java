package org.tpl.business.service;

import org.tpl.business.model.*;
import org.tpl.business.model.statsfc.MonthEnum;
import org.tpl.integration.parser.MatchImportException;

import java.util.List;

/**
 * User: Koren
 * Date: 08.aug.2010
 * Time: 18:22:09
 */
public interface ImportService {
    List<Match> importMatches(Season season) throws MatchImportException;

    List<PlayerStats> importPlayerStats(Match match);

    void readDataForMonth(Season season, MonthEnum monthEnum) throws MatchImportException;
}
