package org.tpl.integration.parser;

import org.tpl.business.model.Match;
import org.tpl.business.model.Season;

import java.util.List;

/**
 * User: Koren
 * Date: 14.aug.2010
 * Time: 08:08:00
 */
public interface MatchConstructor {

    List<Match> getMatches() throws MatchImportException;
}
