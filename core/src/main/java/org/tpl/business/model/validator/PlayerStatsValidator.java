package org.tpl.business.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tpl.business.model.League;
import org.tpl.business.model.PlayerStats;
import org.tpl.business.service.LeagueService;
/*
Created by jordyr, 13.nov.2010

*/

public class PlayerStatsValidator implements Validator {

    @Autowired
    LeagueService leagueService;
    public boolean supports(Class type) {
        return PlayerStats.class.equals(type);
    }

    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "shortName", "league.shortName.empty");
        ValidationUtils.rejectIfEmpty(errors, "fullName", "league.fullName.empty");
        PlayerStats playerStats = (PlayerStats) o;
        if(playerStats.getPlayedMinutes() < 0){
            errors.rejectValue("playedMinutes","playerStats.playedMinutes.toSmall");
        }
        if(playerStats.getTeam().getTeamId() != playerStats.getPlayer().getTeam().getTeamId()){
            errors.rejectValue("team.teamId","playerStats.teamIsDifferent");
        }
    }

}
