package org.tpl.integration.dao.fantasy;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.Whore;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jordyr on 5/4/14.
 */
public class JdbcWhoreDao extends SimpleJdbcDaoSupport implements WhoreDao {
    @Override
    public List<Whore> getWhores(int fantasyLeagueId, int fantasyRoundId) {
        String sql = "SELECT * from whore_view WHERE fantasyleagueid = ? AND fantasyroundid = ? ORDER BY competitionposition";
        return getJdbcTemplate().query(sql,new WhoreMapper(),fantasyLeagueId, fantasyRoundId);
    }

    private class WhoreMapper implements ParameterizedRowMapper<Whore> {
        public Whore mapRow(ResultSet rs, int i) throws SQLException {
            Whore whore = new Whore();
            FantasyLeague fantasyLeague = new FantasyLeague();
            fantasyLeague.setId(rs.getInt("fantasyleagueid"));
            whore.setFantasyLeague(fantasyLeague);
            FantasyTeam fantasyTeam = new FantasyTeam();
            fantasyTeam.setTeamId(rs.getInt("fantasyteamid"));
            whore.setFantasyTeam(fantasyTeam);

            whore.setCompetitionPoints(rs.getInt("competitionpoints"));
            whore.setCompetitionPosition(rs.getInt("competitionposition"));
            whore.setLeaguePoints(rs.getInt("leaguepoints"));
            whore.setLeaguePosition(rs.getInt("leagueposition"));

            return whore;
        }
    }
}
