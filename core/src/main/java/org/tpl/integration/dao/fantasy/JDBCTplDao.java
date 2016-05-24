package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.tpl.business.state.EditTeamState;

/**
 * User: Koren
 * Date: 08.aug.2010
 * Time: 18:19:49
 */
public class JDBCTplDao extends SimpleJdbcDaoSupport implements TplDao{
    public EditTeamState getEditTeamState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
