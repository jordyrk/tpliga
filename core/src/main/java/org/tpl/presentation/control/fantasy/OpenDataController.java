package org.tpl.presentation.control.fantasy;

import no.kantega.publishing.common.data.Multimedia;
import no.kantega.publishing.common.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.comparator.FantasyRoundComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.business.service.fantasy.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class OpenDataController {
    @Autowired
    FantasyService fantasyService;

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyCupService fantasyCupService;

    @RequestMapping("/blottalag")
    public String viewOfficialTeam(@RequestParam (required = false)Integer forrigeRundeId, @RequestParam (required = false)Integer runde, ModelMap model){

        FantasyRound fantasyRound = getFantasyRound(forrigeRundeId);
        if (fantasyRound == null && runde != null){
            fantasyRound = fantasyService.getFantasyRoundByNumberInDefaultSeason(runde);
        }

        if (fantasyRound == null){
            fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        }

        addPreviousFantasyRoundToModel(model, fantasyRound);
        List<FantasyTeamRound> officialFantasyTeamRounds = fantasyService.getOfficialFantasyTeamRounds(fantasyRound.getFantasyRoundId());
        FantasyRound currentFantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        model.put("fantasyRound", fantasyRound);
        model.put("numberOfOfficialWhenRoundIsClosed", fantasyService.getNumberOfOfficialWhenRoundIsClosedTeams(fantasyRound.getFantasyRoundId()));
        model.put("currentFantasyRound", currentFantasyRound);
        model.put("officialFantasyTeamRounds", officialFantasyTeamRounds);
        return "json/viewofficialfantasyteams";

    }

    @RequestMapping("/blottalag/v2")
    public String viewOfficialTeamV2(@RequestParam (required = false)Integer forrigeRundeId, @RequestParam (required = false)Integer runde, ModelMap model){
        FantasyRound fantasyRound = getFantasyRound(forrigeRundeId);
        if (fantasyRound == null && runde != null){
            fantasyRound = fantasyService.getFantasyRoundByNumberInDefaultSeason(runde);
        }

        if (fantasyRound == null){
            fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        }

        addPreviousFantasyRoundToModel(model, fantasyRound);
        List<FantasyTeamRound> officialFantasyTeamRounds = fantasyService.getOfficialFantasyTeamRounds(fantasyRound.getFantasyRoundId());
        FantasyRound currentFantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        List<BlottaLagWrapper> blottaLagWrapperList = new ArrayList<BlottaLagWrapper>();
        for(FantasyTeamRound fantasyTeamRound: officialFantasyTeamRounds){
            BlottaLagWrapper blottaLagWrapper = new BlottaLagWrapper();
            blottaLagWrapper.setFantasyTeamRound(fantasyTeamRound);
            List<FantasyMatch> nextMatches = fantasyMatchService.getNextMatches(fantasyTeamRound.getFantasyTeam().getTeamId(), fantasyRound.getFantasyRoundId());
            if(nextMatches != null && nextMatches.size()> 0){
                FantasyMatch fantasyMatch = nextMatches.get(0);
                if(fantasyMatch.getHomeTeam().getTeamId() != fantasyTeamRound.getFantasyTeam().getTeamId()){
                    blottaLagWrapper.setOppositionTeamId(fantasyMatch.getHomeTeam().getTeamId());
                }else{
                    blottaLagWrapper.setOppositionTeamId(fantasyMatch.getAwayTeam().getTeamId());
                }
            }
            blottaLagWrapperList.add(blottaLagWrapper);
        }

        model.put("fantasyRound", fantasyRound);
        model.put("numberOfOfficialWhenRoundIsClosed", fantasyService.getNumberOfOfficialWhenRoundIsClosedTeams(fantasyRound.getFantasyRoundId()));
        model.put("currentFantasyRound", currentFantasyRound);
        model.put("blottaLagWrapperList", blottaLagWrapperList);
        return "json/viewofficialfantasyteams_v2";

    }


    @RequestMapping("/antallblottalag")
    public String getNumberOfOfficalTeams(ModelMap model){
        FantasyRound fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasyService.getDefaultFantasySeason().getFantasySeasonId());
        int numberOfOfficialWhenRoundIsClosed = fantasyService.getNumberOfOfficialWhenRoundIsClosedTeams(fantasyRound.getFantasyRoundId());
        int numberOfOfficialTeams = fantasyService.getNumberOfOfficialTeams(fantasyRound.getFantasyRoundId());
        model.put("numberOfOfficialWhenRoundIsClosed",numberOfOfficialWhenRoundIsClosed);
        model.put("numberOfOfficialTeams",numberOfOfficialTeams);
        return "json/viewnumberofofficialfantasyteams";
    }

    @RequestMapping("/terminliste")
    public String getFixtures(ModelMap model){
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();

        List<FantasyLeague> fantasyLeagueList = fantasyLeagueService.getAllFantasyLeagues(fantasySeason.getFantasySeasonId());

        Map<String, List<FantasyMatch>> fixturesMap = new HashMap<String, List<FantasyMatch>>();

        FantasyRound fantasyRound = fantasyService.getCurrentFantasyRoundBySeasonId(fantasySeason.getFantasySeasonId());
        ComparisonTerm roundTerm = new ComparisonTerm("fantasyroundid", Operator.EQ, fantasyRound.getFantasyRoundId() );

        for(FantasyLeague fantasyLeague : fantasyLeagueList){
            ComparisonTerm leagueTerm = new ComparisonTerm("fantasyleagueid", Operator.EQ,fantasyLeague.getId());

            AndTerm andTerm = new AndTerm(roundTerm,leagueTerm);
            List<FantasyMatch> fantasyMatchList = fantasyMatchService.getFantasyMatchBySearchTerm(andTerm, FantasyMatchType.LEAGUE).getResults();
            fixturesMap.put(fantasyLeague.getName(), fantasyMatchList);

        }


        model.put("fixturesMap", fixturesMap );
        model.put("round", fantasyRound );

        return "json/viewfixtures";
    }

    @RequestMapping(value = "/open/viewallfantasyteams")
    public String viewAllTeams(ModelMap model, HttpServletRequest request){
        List<FantasyTeam> allFantasyTeams = fantasyService.getAllFantasyTeams();
        MultimediaService mms = new MultimediaService(request);

        for(FantasyTeam fantasyTeam: allFantasyTeams){
            Multimedia profileImage =  mms.getMultimedia(fantasyTeam.getMultiMediaImageId());
            if (profileImage != null) {
                String imgSrc = profileImage.getUrl();
                fantasyTeam.setImageUrl(imgSrc);
            }
        }

        model.put("allFantasyTeams", allFantasyTeams);
        return "json/viewallfantasyteams";
    }

    @RequestMapping(value = "/open/viewfantasyteam")
    public String viewFantasyTeam(@RequestParam int fantasyTeamId,ModelMap model, HttpServletRequest request){
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(fantasyTeamId);
        MultimediaService mms = new MultimediaService(request);
            Multimedia profileImage =  mms.getMultimedia(fantasyTeam.getMultiMediaImageId());
            if (profileImage != null) {
                String imgSrc = profileImage.getUrl();
                fantasyTeam.setImageUrl(imgSrc);
            }
        model.put("fantasyTeam", fantasyTeam);
        return "json/viewfantasyteam";
    }


    private void addPreviousFantasyRoundToModel(ModelMap model, FantasyRound fantasyRound) {
        FantasyRound previousFantasyRound = fantasyService.getPreviousFantasyRound(fantasyRound.getFantasyRoundId(),fantasyRound.getFantasySeason().getFantasySeasonId());
        if(previousFantasyRound != null){
            model.put("previousFantasyRound", previousFantasyRound);
        }
        else{
            FantasyRound emptyRound = new FantasyRound();
            emptyRound.setFantasyRoundId(0);
            model.put("previousFantasyRound",emptyRound );
        }
    }

    private FantasyRound getFantasyRound(Integer fantasyRoundId) {
        if(fantasyRoundId == null){
            return null;
        }
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        return fantasyRound;
    }
}
