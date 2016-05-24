package org.tpl.integration.dao.fantasy;

import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.HallOfFame;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jordyr
 * Date: 10/28/13
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HallOfFameDao {
    List<HallOfFame> getHallOfFameList();


}
