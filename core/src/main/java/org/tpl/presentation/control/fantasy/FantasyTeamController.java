package org.tpl.presentation.control.fantasy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tpl.business.model.Player;
import org.tpl.business.model.PlayerPosition;
import org.tpl.business.model.comparator.FantasyMatchByRoundIdReverseComparator;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.search.AndTerm;
import org.tpl.business.model.search.ComparisonTerm;
import org.tpl.business.model.search.Operator;
import org.tpl.business.model.search.OrTerm;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.fantasy.*;
import org.tpl.presentation.common.FantasyUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class FantasyTeamController {

    @Autowired
    FantasyMatchService fantasyMatchService;

    @Autowired
    FantasyService fantasyService;

    @Autowired
    PlayerService playerService;

    @Autowired
    FantasyLeagueService fantasyLeagueService;

    @Autowired
    FantasyUtil fantasyUtil;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    FantasyCupService fantasyCupService;

    @Autowired
    FantasyCupGroupService fantasyCupGroupService;

    private static final int NO_PLAYER_SELECTED = 1;



    @RequestMapping(value = "/fantasyteam/viewteam")
    public String viewTeam(@RequestParam int teamId, ModelMap model, HttpServletRequest request){
        List<FantasyTeam> fantasyTeamList = fantasyService.getAllFantasyTeams();
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(teamId);
        List<FantasyLeague> fantasyLeagueList = fantasyLeagueService.getFantasyLeaguesForFantasyTeamInCurrentSeason(teamId);
        FantasyTeam currentFantasyTeam = fantasyUtil.getFantasyTeamFromRequest(request);
        List<FantasyCompetition> fantasyCompetitionList = fantasyCompetitionService.getFantasyCompetitionForFantasyTeamInCurrentSeason(teamId);
        //List<FantasyCup> fantasyCupList = fantasyCupService.getFantasyCupForFantasyTeamInCurrentSeason(teamId);
        //model.put("fantasyCupList",fantasyCupList);
        model.put("fantasyLeagueList", fantasyLeagueList);
        model.put("fantasyTeamList", fantasyTeamList);
        model.put("currentFantasyTeam", currentFantasyTeam);
        model.put("fantasyCompetitionList", fantasyCompetitionList);
        model.put("fantasyTeam",fantasyTeam);
        return "fantasy/team/viewteam";
    }

    @RequestMapping(value = "/fantasyteam/viewcompetitionteamstats")
    public String viewTeamStats(@RequestParam int teamId,@RequestParam int fantasyCompetitionId, ModelMap model){
        FantasyCompetition fantasyCompetition = fantasyCompetitionService.getFantasyCompetitionById(fantasyCompetitionId);
        model.put("fantasyCompetition",fantasyCompetition);
        List<FantasyTeamRound> fantasyTeamRounds = fantasyService.getFantasyTeamRoundByTeamIdInCurrentSeason(teamId);
        model.put("fantasyTeamRounds",fantasyTeamRounds);
        FantasySeason season = fantasyService.getDefaultFantasySeason();
        FantasyCompetition competition = fantasyCompetitionService.getDefaultFantasyCompetitionBySeasonId(season.getFantasySeasonId());
        List<FantasyCompetitionStanding> fantasyCompetitionStandings = fantasyCompetitionService.getFantasyCompetitionStandingByTeamAndCompetition(competition.getFantasyCompetitionId(), teamId);
        model.put("fantasyCompetitionStandings",fantasyCompetitionStandings);
        return "fantasy/team/viewcompetitionteamstats";
    }

    @RequestMapping(value = "/fantasyteam/viewleagueteamstats")
    public String viewLeagueStats(@RequestParam int teamId,@RequestParam int fantasyLeagueId, ModelMap model){
        FantasyLeague fantasyLeague = fantasyLeagueService.getFantasyLeagueById(fantasyLeagueId);
        List<FantasyRound> fantasyRounds = fantasyService.getFantasyRoundByFantasySeasonId(fantasyLeague.getFantasySeason().getFantasySeasonId());
        List<FantasyLeagueStanding> adjustedFantasyLeagueStandingList = createAdjustedFantasyLeagueStandingList(teamId, fantasyLeagueId, fantasyRounds);
        List<FantasyMatch> fantasyMatchListAdjustedToRounds = createAdjustedFantasyMatchList(teamId, fantasyLeagueId, fantasyRounds);
        FantasyTeam fantasyTeam = fantasyService.getFantasyTeamById(teamId);
        model.put("fantasyTeam",fantasyTeam);
        model.put("fantasyRounds",fantasyRounds);
        model.put("fantasyMatches",fantasyMatchListAdjustedToRounds);
        model.put("fantasyLeague",fantasyLeague);
        model.put("fantasyLeagueStandings",adjustedFantasyLeagueStandingList);

        return "fantasy/team/viewleagueteamstats";
    }

    @RequestMapping(value = "/fantasyteamround/getfantasyteamround")
    public String getFantasyTeamRound(@RequestParam int fantasyRoundId, ModelMap model, HttpServletRequest request){
        HttpSession session = request.getSession();
        FantasyTeam fantasyTeam = fantasyUtil.getFantasyTeamFromRequest(request);
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyRoundId);
        FantasyTeamRound fantasyTeamRound = getFantasyTeamRound(session, fantasyTeam, fantasyRound, fantasyRound.getFantasySeason());
        model.put("players",fantasyTeamRound.getPlayerInFormationList());
        model.put("formation",fantasyTeamRound.getFormation().getDescription());
        model.put("official", fantasyService.getFantasyTeamRoundByIds(fantasyTeam.getTeamId(),fantasyRound.getFantasyRoundId()).isOfficial());
        model.put("officialwhenroundisclosed", fantasyTeamRound.isOfficialWhenRoundIsClosed());
        return "json/fantasyteamround";
    }

    @RequestMapping(value="/fantasyteamround/changeformation", method= RequestMethod.POST)
    public String changeFormation(@RequestParam(required = true) String formation,HttpServletRequest request, ModelMap model){
        //TODO: Do not change formation when round is closed
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(request.getSession());
        FantasySeason fantasySeason = fantasyService.getFantasySeasonByRoundId(fantasyTeamRound.getFantasyRound().getFantasyRoundId());
        fantasyTeamRound.getFantasyRound().setFantasySeason(fantasySeason);
        Formation newFormation = Formation.valueOf(formation);
        fantasyTeamRound.setFormation(newFormation);
        fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        fantasyUtil.addFantasyTeamRoundToSession(request.getSession(),fantasyTeamRound);
        return getFantasyTeamRound(fantasyTeamRound.getFantasyRound().getFantasyRoundId(),model,request);
    }

    @RequestMapping(value="/fantasyteamround/addPlayerToTeam")
    public String addPlayerToTeam(@RequestParam(required = true) String playerPosition, @RequestParam(required = true) Integer playerId,HttpServletRequest request, ModelMap model){
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(request.getSession());
        FormationPosition formationPosition = FormationPosition.valueOf(playerPosition);
        fantasyTeamRound = updateFantasyRound(fantasyTeamRound);
        model.put("playerbought", false);
        model.put("shouldupdateteam", false);
        if(fantasyTeamRound.getFantasyRound().isClosed()){
            model.put("message", "Runden er stengt");
            return "mypage/fantasyTeamRoundMessage";
        }

        if (playerId < NO_PLAYER_SELECTED){
            model.put("message", "Feil ved kj&oslash;p av spiller. Pr&oslash;v igjen!");
            return "mypage/fantasyTeamRoundMessage";
        }
        if (fantasyTeamRound.hasPlayer(playerId)){
            model.put("message", "Spilleren er allerede p&aring; laget ditt");
            return "mypage/fantasyTeamRoundMessage";
        }
        Player player = playerService.getPlayerById(playerId);
        if(fantasyTeamRound.isWrongPosition(player,  playerPosition)){
            model.put("message", "Spiller kan ikke benyttes p&aring; denne posisjonen");
            return "mypage/fantasyTeamRoundMessage";
        }

        if ( ! fantasyTeamRound.canAddPlayerWithTeamId(player.getTeam().getTeamId())){
            model.put("message", "Du har for mange spillere fra dette laget");
            return "mypage/fantasyTeamRoundMessage";
        }
        FantasySeason fantasySeason = fantasyService.getDefaultFantasySeason();
        if ((fantasyTeamRound.getTeamPrice() + player.getPrice()) > fantasySeason.getMaxTeamPrice() ){
            model.put("message", "Spilleren er for dyr");
            return "mypage/fantasyTeamRoundMessage";
        }

        fantasyTeamRound.addPlayer(formationPosition,player);
        fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        fantasyUtil.addFantasyTeamRoundToSession(request.getSession(),fantasyTeamRound);
        model.put("playerbought", true);
        model.put("message", "Spilleren er blitt en del av ditt lag");
        return "mypage/fantasyTeamRoundMessage";
    }

    @RequestMapping(value="/fantasyteamround/removePlayerFromTeam")
    public String removePlayerFromTeam(@RequestParam(required = true) String formationPosition,HttpServletRequest request, ModelMap model){
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(request.getSession());
        fantasyTeamRound = updateFantasyRound(fantasyTeamRound);
        if(fantasyTeamRound.getFantasyRound().isClosed()){
            model.put("removed", false);
            model.put("message", "Runden er stengt");
            model.put("shouldupdateteam", false);
            return "mypage/fantasyTeamRoundMessage";
        }
        fantasyTeamRound.removePlayerFromTeam(FormationPosition.valueOf(formationPosition));
        fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        fantasyUtil.addFantasyTeamRoundToSession(request.getSession(),fantasyTeamRound);
        model.put("removed", true);
        model.put("message", "Spilleren er solgt");
        addSumPriceToModel(model,fantasyTeamRound);
        return "mypage/fantasyTeamRoundMessage";
    }

    @RequestMapping("/fantasyteamround/changeofficial")
    public String changeOfficial(@RequestParam Boolean checked,HttpServletRequest request, ModelMap model){
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(request.getSession());
        if(checked != null){
            fantasyTeamRound.setOfficial(checked);
            fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
            if(checked){
                model.put("message", "Laget er blottet");
            }else{
                model.put("message", "Laget er ikke blottet");
            }
        }
        return "mypage/fantasyTeamRoundMessage";
    }

    @RequestMapping("/fantasyteamround/changeofficialwhenroundisclosed")
    public String changeOfficialWhenRoundIsClosed(@RequestParam Boolean checked,HttpServletRequest request, ModelMap model){
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(request.getSession());
        if(checked != null){
            fantasyTeamRound.setOfficialWhenRoundIsClosed(checked);
            fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
            if(checked){
                model.put("message", "Laget blottes n&aring;r runden stenges.");
            }else{
                model.put("message", "Laget er ikke blottet");
            }
        }
        return "mypage/fantasyTeamRoundMessage";
    }

    private List<FantasyMatch> createAdjustedFantasyMatchList(int teamId, int fantasyLeagueId, List<FantasyRound> fantasyRounds) {
        List<FantasyMatch> fantasyMatches = getMatchesForFantasyLeague(false, teamId, fantasyLeagueId);
        List<FantasyMatch> fantasyMatchListAdjustedToRounds = new ArrayList<FantasyMatch>();
        int matchIndex = 0;
        for(int i = 0 ; i < fantasyRounds.size(); i++){
            if(fantasyMatches.size() > matchIndex && fantasyRounds.get(i).getRound() == fantasyMatches.get(matchIndex).getFantasyRound().getRound()){
                fantasyMatchListAdjustedToRounds.add(fantasyMatches.get(matchIndex));
                matchIndex++;
            }else{
                fantasyMatchListAdjustedToRounds.add(null);
            }
        }
        return fantasyMatchListAdjustedToRounds;
    }

    private List<FantasyLeagueStanding> createAdjustedFantasyLeagueStandingList(int teamId, int fantasyLeagueId, List<FantasyRound> fantasyRounds) {
        List<FantasyLeagueStanding> fantasyLeagueStandings = fantasyLeagueService.getFantasyLeagueStandingByTeamAndLeague(fantasyLeagueId, teamId);
        List<FantasyLeagueStanding> fantasyLeagueStandingListAdjustedToRounds = new ArrayList<FantasyLeagueStanding>();
        int fantasyLeagueStandingIndex = 0;
        for(int i = 0 ; i < fantasyRounds.size(); i++){
            if(fantasyLeagueStandings.size() > fantasyLeagueStandingIndex && fantasyRounds.get(i).getRound() == fantasyLeagueStandings.get(fantasyLeagueStandingIndex).getFantasyRound().getRound()){
                fantasyLeagueStandingListAdjustedToRounds.add(fantasyLeagueStandings.get(fantasyLeagueStandingIndex));
                fantasyLeagueStandingIndex++;
            }else{
                fantasyLeagueStandingListAdjustedToRounds.add(null);
            }
        }
        return fantasyLeagueStandingListAdjustedToRounds;
    }

    @RequestMapping(value = "/fantasyteam/viewfixtures")
    public String viewFixtures(@RequestParam int teamId,@RequestParam(required = false) Integer fantasyLeagueId,@RequestParam(required = false) Integer fantasyCupId,  ModelMap model){
        List<FantasyMatch> fixtures = Collections.emptyList();
        if(fantasyLeagueId != null && fantasyLeagueId > 0){
            fixtures = getMatchesForFantasyLeague(true,teamId,fantasyLeagueId);
        }else if(fantasyCupId != null && fantasyCupId > 0){
            fixtures = getMatchesForFantasyCup(true,teamId,fantasyCupId);
        }

        model.put("fixtures", fixtures);
        return "fantasy/team/viewfixtures";
    }

    @RequestMapping(value = "/fantasyteam/viewplayedmatches")
    public String viewPlayedMatches(@RequestParam int teamId, @RequestParam(required = false) Integer fantasyLeagueId, @RequestParam(required = false) Integer fantasyCupId,  ModelMap model){

        List<FantasyMatch> playedMatches = Collections.emptyList();
        if(fantasyLeagueId != null && fantasyLeagueId > 0){
            playedMatches  = getMatchesForFantasyLeague(false,teamId,fantasyLeagueId);
        }else if(fantasyCupId != null && fantasyCupId > 0){
            playedMatches  = getMatchesForFantasyCup(false,teamId,fantasyCupId);
        }
        Collections.sort(playedMatches, new FantasyMatchByRoundIdReverseComparator());
        model.put("playedMatches", playedMatches);
        return "fantasy/team/viewplayedmatches";
    }

    private List<FantasyMatch> getMatchesForFantasyLeague(boolean fixtures, int teamId, int fantasyLeagueId){
        FantasyRound fantasyRound = fantasyLeagueService.getLastUpdatedFantasyRound(fantasyLeagueId);
        ComparisonTerm homeTerm = new ComparisonTerm("hometeamid", Operator.EQ, teamId);
        ComparisonTerm awayTerm = new ComparisonTerm("awayteamid", Operator.EQ, teamId);
        ComparisonTerm leagueTerm = new ComparisonTerm("fantasyleagueid", Operator.EQ, fantasyLeagueId);
        ComparisonTerm roundTerm = new ComparisonTerm("fantasyroundid", fixtures ? Operator.G : Operator.LE, fantasyRound.getFantasyRoundId());
        OrTerm orTerm = new OrTerm(homeTerm,awayTerm);
        AndTerm andTerm = new AndTerm(orTerm,leagueTerm);
        andTerm.addTerm(roundTerm);
        List<FantasyMatch> matches = fantasyMatchService.getFantasyMatchBySearchTerm(andTerm, FantasyMatchType.LEAGUE).getResults();
        return matches;

    }

    private List<FantasyMatch> getMatchesForFantasyCup(boolean fixtures, int teamId, int fantasyCupId){

        return null;

    }

    private FantasyTeamRound getFantasyTeamRound(HttpSession session, FantasyTeam fantasyTeam, FantasyRound fantasyRound, FantasySeason fantasySeason){
        FantasyTeamRound fantasyTeamRound = fantasyUtil.getFantasyTeamRoundFromSession(session);
        if(fantasyTeamRound == null){
            fantasyTeamRound = fantasyService.getFantasyTeamRoundByIds(fantasyTeam.getTeamId(), fantasyRound.getFantasyRoundId());
            if(fantasyTeamRound == null || !fantasyTeamRound.isPlayersSelected()){
                FantasyTeamRound tempFantasyTeamRound = fantasyService.getLastChangedTeamRound(fantasyTeam.getTeamId(), fantasySeason.getFantasySeasonId());
                if(tempFantasyTeamRound != null){
                    fantasyTeamRound = tempFantasyTeamRound;
                    fantasyTeamRound.setFantasyRound(fantasyRound);
                    fantasyTeamRound.setOfficial(false);
                    fantasyTeamRound.setPoints(0);
                    fantasyService.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
                }
            }
            fantasyUtil.addFantasyTeamRoundToSession(session,fantasyTeamRound);
        }
        return fantasyTeamRound;
    }

    private FantasyTeamRound updateFantasyRound(FantasyTeamRound fantasyTeamRound){
        FantasyRound fantasyRound = fantasyService.getFantasyRoundById(fantasyTeamRound.getFantasyRound().getFantasyRoundId());
        if(fantasyRound.isOpenForChange() && fantasyRound.isDateExceeded()){
            fantasyRound.setOpenForChange(false);
            fantasyService.saveOrUpdateFantasyRound(fantasyRound);
        }
        fantasyTeamRound.setFantasyRound(fantasyRound);
        return fantasyTeamRound;
    }

    private void addSumPriceToModel(ModelMap model, FantasyTeamRound fantasyTeamRound){
        model.put("totalPrice", fantasyTeamRound.getTotalPrice());
    }
}


