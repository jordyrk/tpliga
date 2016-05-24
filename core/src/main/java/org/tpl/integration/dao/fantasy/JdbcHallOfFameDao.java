package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.tpl.business.model.fantasy.FantasyManager;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.HallOfFame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jordyr
 * Date: 10/28/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class JdbcHallOfFameDao extends SimpleJdbcDaoSupport implements HallOfFameDao {
    private HallOfFameRowMapper hallOfFameRowMapper = new HallOfFameRowMapper();

    @Override
    public List<HallOfFame> getHallOfFameList() {
        String sql = "select * from halloffame_view order by points desc limit 20";
        return getJdbcTemplate().query(sql,hallOfFameRowMapper);
    }

    private class HallOfFameRowMapper implements ParameterizedRowMapper<HallOfFame> {
        public HallOfFame mapRow(ResultSet rs, int i) throws SQLException {
            HallOfFame hallOfFame = new HallOfFame();
            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            FantasySeason fantasySeason = new FantasySeason();
            fantasySeason.setFantasySeasonId(rs.getInt("fantasyseasonid"));
            int points = rs.getInt("points");
            hallOfFame.setFantasySeason(fantasySeason);
            hallOfFame.setFantasyTeam(fantasyTeam);
            hallOfFame.setPoints(points);


            return hallOfFame;

        }

    }

}