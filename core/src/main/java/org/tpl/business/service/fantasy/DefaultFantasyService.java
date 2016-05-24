package org.tpl.business.service.fantasy;

import org.springframework.beans.factory.annotation.Autowired;
import org.tpl.business.model.*;
import org.tpl.business.model.fantasy.*;
import org.tpl.business.model.fantasy.util.FantasyRoundUtil;
import org.tpl.business.service.LeagueService;
import org.tpl.business.service.PlayerService;
import org.tpl.business.service.chat.ChatService;
import org.tpl.integration.dao.fantasy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Koren
 * Date: 08.aug.2010
 * Time: 18:18:57
 */
public class DefaultFantasyService implements FantasyService {

    @Autowired
    FantasyManagerDao fantasyManagerDao;

    @Autowired
    FantasySeasonDao fantasySeasonDao;

    @Autowired
    FantasyTeamDao fantasyTeamDao;

    @Autowired
    FantasyRoundDao fantasyRoundDao;

    @Autowired
    LeagueService leagueService;

    @Autowired
    PlayerService playerService;

    @Autowired
    FantasyCompetitionService fantasyCompetitionService;

    @Autowired
    ChatService chatService;

    public void saveOrUpdateFantasyManager(FantasyManager fantasyManager) {
        fantasyManagerDao.saveOrUpdateFantasyManager(fantasyManager);
    }

    public boolean isUserIdRegistered(String userId) {
        FantasyManager manager = fantasyManagerDao.getFantasyManagerByUserId(userId);
        if(manager == null) return false;
        else return true;
    }

    public FantasyManager getFantasyManagerById(int managerId) {
        return fantasyManagerDao.getFantasyManagerById(managerId);
    }

    public FantasyManager getFantasyManagerByUserId(String userId) {
        return fantasyManagerDao.getFantasyManagerByUserId(userId);
    }

    /**
     * Assumes manager has not been part of any leagues or cups
     * @param managerId
     */
    public void deleteFantasyManager(int managerId) {
        FantasyManager fantasyManager = getFantasyManagerById(managerId);
        List<FantasyTeam> fantasyTeamList = fantasyTeamDao.getFantasyTeamByManagerId(fantasyManager.getManagerId());
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            fantasyCompetitionService.removeTeamFromCompetitions(fantasyTeam.getTeamId());
            fantasyTeamDao.deleteFantasyTeam(fantasyTeam.getTeamId());
            fantasyTeamDao.deleteFantasyTeamRound(fantasyTeam.getTeamId());
            fantasyCompetitionService.removeTeamFromCompetitions(fantasyTeam.getTeamId());
        }
        chatService.deleteChatMessagesForManager(fantasyManager.getManagerId());
        fantasyManagerDao.deleteFantasyManager(fantasyManager.getUserId());
    }

    public void saveOrUpdateFantasySeason(FantasySeason fantasySeason) {
        if(fantasySeason.isNew()){
            FantasyCompetition fantasyCompetition = createDefaultCompetitionForSeason(fantasySeason);
            fantasySeason.setDefaultFantasyCompetition(fantasyCompetition);
            fantasySeason.setCurrentRound(new FantasyRound());
            fantasyCompetitionService.saveOrUpdateFantasyCompetition(fantasyCompetition);
            fantasySeasonDao.saveOrUpdateFantasySeason(fantasySeason);
            fantasyCompetitionService.saveOrUpdateFantasyCompetition(fantasyCompetition);
            List<FantasyRound> fantasyRounds = createRoundsForSeason(fantasySeason);
            saveFantasyRoundsForSeason(fantasySeason, fantasyRounds);
            List<FantasyTeam> fantasyTeamList = this.getAllFantasyTeams();
            for(FantasyTeam fantasyTeam : fantasyTeamList){
                createRoundsForFantasyTeam(fantasyTeam);
            }
        }

        fantasySeasonDao.saveOrUpdateFantasySeason(fantasySeason);
    }

    public FantasySeason getFantasySeasonById(int id) {
        FantasySeason fantasySeason = fantasySeasonDao.getFantasySeasonById(id);
        updateDependencies(fantasySeason);
        return fantasySeason;
    }

    public FantasySeason getDefaultFantasySeason() {
        FantasySeason fantasySeason = fantasySeasonDao.getDefaultFantasySeason();
        updateDependencies(fantasySeason);
        return fantasySeason;
    }

    public FantasySeason getFantasySeasonByRoundId(int fantasyRoundId) {
        FantasyRound fantasyRound = getFantasyRoundById(fantasyRoundId);
        return getFantasySeasonById(fantasyRound.getFantasySeason().getFantasySeasonId());
    }

    public List<FantasySeason> getAllFantasySeasons() {
        List<FantasySeason>  fantasySeasonList = fantasySeasonDao.getAllFantasySeasons();
        for(FantasySeason fantasySeason: fantasySeasonList){
            updateDependencies(fantasySeason);
        }
        return fantasySeasonList;
    }

    public void saveOrUpdateFantasyRound(FantasyRound fantasyRound) {
        fantasyRoundDao.saveOrUpdateFantasyRound(fantasyRound);
        updateOfficialFantasyTeams(fantasyRound.getFantasyRoundId());
    }

    public FantasyRound getFantasyRoundById(int fantasyRoundId) {
        FantasyRound fantasyRound =  fantasyRoundDao.getFantasyRoundById(fantasyRoundId);
	if(fantasyRound != null){
		List<Integer> matchIdList = getMatchIdsForRound(fantasyRoundId);
		for(Integer matchId : matchIdList){
		    Match match = leagueService.getMatchById(matchId);
		    fantasyRound.getMatchList().add(match);
		}
		fantasyRound.sortMatchList();
	}
        return fantasyRound;
    }

    public FantasyRound getFantasyRoundByNumberInDefaultSeason(int fantasyRoundNumber) {
        FantasySeason defaultFantasySeason = getDefaultFantasySeason();
        FantasyRound fantasyRoundBy = fantasyRoundDao.getFantasyRoundByNumberAndSeason(fantasyRoundNumber, defaultFantasySeason.getFantasySeasonId());
        return fantasyRoundBy;
    }

    public FantasyRound getCurrentFantasyRoundBySeasonId(int fantasySeasonId) {
        FantasySeason fantasySeason = fantasySeasonDao.getFantasySeasonById(fantasySeasonId);
        return this.getFantasyRoundById(fantasySeason.getCurrentRound().getFantasyRoundId());
    }

    public FantasyRound getPreviousFantasyRound(int fantasyRoundId, int fantasySeasonId) {
        FantasyRound currentRound = getFantasyRoundById(fantasyRoundId);
        FantasyRound previousRound = fantasyRoundDao.getFantasyRoundByNumberAndSeason(currentRound.getRound() -1, fantasySeasonId);
        return previousRound;
    }

    public List<FantasyRound> getFantasyRoundByFantasySeasonId(int fantasySeasonId) {
        return fantasyRoundDao.getFantasyRoundByFantasySeasonId(fantasySeasonId);
    }

    public List<FantasyRound> getFantasyRoundByFantasyLeagueId(int fantasyLeagueId) {
        return fantasyRoundDao.getFantasyRoundByFantasyLeagueId(fantasyLeagueId);
    }

    public List<FantasyRound> getFantasyRoundByFantasyCupId(int fantasyCupId) {
        return fantasyRoundDao.getFantasyRoundByFantasyCupId(fantasyCupId);
    }

    public List<FantasyRound> getFantasyRoundByFantasyCupGroupId(int fantasyCupGroupId) {
        return fantasyRoundDao.getFantasyRoundByFantasyCupGroupId(fantasyCupGroupId);
    }

    public boolean addMatchToRound(int leaguematchId, int fantasyRoundId) {
        return fantasyRoundDao.addMatchToRound(leaguematchId, fantasyRoundId);
    }

    public boolean removeMatchFromRound(int leaguematchId, int fantasyRoundId) {
        return fantasyRoundDao.removeMatchFromRound(leaguematchId, fantasyRoundId);
    }

    public List<Integer> getMatchIdsForRound(int fantasyRoundId) {
        return fantasyRoundDao.getMatchIdsForRound(fantasyRoundId);
    }

    public void saveOrUpdateFantasyTeam(FantasyTeam fantasyTeam) {
        if(fantasyTeam.isNew()){
            fantasyTeamDao.saveOrUpdateFantasyTeam(fantasyTeam);
            createRoundsForFantasyTeam(fantasyTeam);
        }
        else{
            fantasyTeamDao.saveOrUpdateFantasyTeam(fantasyTeam);
        }

    }

    public FantasyTeam getFantasyTeamById(int teamId) {
        FantasyTeam fantasyTeam =  fantasyTeamDao.getFantasyTeamById(teamId);
        updateDependencies(fantasyTeam);
        return fantasyTeam;
    }

    public List<FantasyTeam> getAllFantasyTeams() {
        List<FantasyTeam>  fantasyTeamList = fantasyTeamDao.getAllFantasyTeams();
        List<FantasyTeam>  activeTeamList = new ArrayList<FantasyTeam>();
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            updateDependencies(fantasyTeam);
            if(fantasyTeam.getFantasyManager().isActive()){
                activeTeamList.add(fantasyTeam);
            }
        }
        return activeTeamList;
    }

    public List<FantasyTeam> getFantasyTeamByManagerId(int managerId) {
        List<FantasyTeam>  fantasyTeamList = fantasyTeamDao.getFantasyTeamByManagerId(managerId);
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            updateDependencies(fantasyTeam);
        }
        return fantasyTeamList;
    }

    public List<FantasyTeam> getFantasyTeamByFantasyLeagueId(int leagueId) {
        List<FantasyTeam>  fantasyTeamList = fantasyTeamDao.getFantasyTeamByFantasyLeagueId(leagueId);
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            updateDependencies(fantasyTeam);
        }
        return fantasyTeamList;
    }

    public List<FantasyTeam> getFantasyTeamByFantasyCupId(int cupId) {
        List<FantasyTeam>  fantasyTeamList = fantasyTeamDao.getFantasyTeamByFantasyCupId(cupId);
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            updateDependencies(fantasyTeam);
        }
        return fantasyTeamList;
    }

    public List<FantasyTeam> getFantasyTeamByFantasyCupGroupId(int cupGroupId) {
        List<FantasyTeam>  fantasyTeamList = fantasyTeamDao.getFantasyTeamByFantasyCupGroupId(cupGroupId);
        for(FantasyTeam fantasyTeam: fantasyTeamList){
            updateDependencies(fantasyTeam);
        }
        return fantasyTeamList;
    }

    public void saveOrUpdateFantasyTeamRound(FantasyTeamRound fantasyTeamRound) {
        fantasyTeamDao.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
    }

    public FantasyTeamRound getFantasyTeamRoundByIds(int fantasyTeamId, int fantasyRoundId) {
        updateOfficialFantasyTeams(fantasyRoundId);
        FantasyTeamRound fantasyTeamRound = fantasyTeamDao.getFantasyTeamRoundByIds(fantasyTeamId, fantasyRoundId);
        updateDependencies(fantasyTeamRound);
        return fantasyTeamRound;
    }

    public List<FantasyTeamRound> getFantasyTeamRoundByTeamIdInCurrentSeason(int fantasyTeamId) {
        FantasySeason fantasySeason = getDefaultFantasySeason();

        List<FantasyRound> fantasyRoundList = this.getFantasyRoundByFantasySeasonId(fantasySeason.getFantasySeasonId());

        String range = new FantasyRoundUtil().createIdRange(fantasyRoundList);
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyTeamDao.getFantasyTeamRoundByRoundIdRange(range, fantasyTeamId, "ASC");
        for(FantasyTeamRound fantasyTeamRound : fantasyTeamRoundList){
            updateDependencies(fantasyTeamRound);
        }
        return fantasyTeamRoundList;
    }

    public List<FantasyTeamRound> getFantasyTeamRoundsByTeamIdUntilCurrentRound(int fantasyTeamId, int fantasySeasonId) {
        FantasySeason fantasySeason = this.getFantasySeasonById(fantasySeasonId);
        FantasyRound currentRound = fantasySeason.getCurrentRound();
        List<FantasyRound> fantasyRoundList = this.getFantasyRoundByFantasySeasonId(fantasySeasonId);

        String range = new FantasyRoundUtil().createIdRange(fantasyRoundList, currentRound.getFantasyRoundId());
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyTeamDao.getFantasyTeamRoundByRoundIdRange(range, fantasyTeamId, "DESC");
        for(FantasyTeamRound fantasyTeamRound : fantasyTeamRoundList){
            if(fantasyTeamRound.isPlayersSelected()){
                updateDependencies(fantasyTeamRound);

            }
        }
        return fantasyTeamRoundList;
    }

    public List<FantasyTeamRound> getFantasyTeamRoundsByFantasyRoundId(int fantasyRoundId) {
        updateOfficialFantasyTeams(fantasyRoundId);
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyTeamDao.getFantasyTeamRoundsByFantasyRoundId(fantasyRoundId);
        for(FantasyTeamRound fantasyTeamRound : fantasyTeamRoundList){
            updateDependencies(fantasyTeamRound);
        }
        return fantasyTeamRoundList;
    }

    public List<FantasyTeamRound> getOfficialFantasyTeamRounds(int fantasyRoundId) {
        updateOfficialFantasyTeams(fantasyRoundId);
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyTeamDao.getOfficialFantasyTeamRounds(fantasyRoundId);
        for(FantasyTeamRound fantasyTeamRound : fantasyTeamRoundList){
            updateDependencies(fantasyTeamRound);
        }
        return fantasyTeamRoundList;
    }

    @Override
    public int getNumberOfOfficialWhenRoundIsClosedTeams(int fantasyRoundId) {
        return fantasyTeamDao.getNumberOfOfficialWhenRoundIsClosedTeams(fantasyRoundId);
    }

    @Override
    public int getNumberOfOfficialTeams(int fantasyRoundId) {
        return fantasyTeamDao.getNumberOfOfficialTeams(fantasyRoundId);
    }

    public FantasyTeamRound getLastChangedTeamRound(int fantasyTeamId, int fantasySeasonId) {
        FantasySeason fantasySeason = this.getFantasySeasonById(fantasySeasonId);
        FantasyRound currentRound = fantasySeason.getCurrentRound();
        List<FantasyRound> fantasyRoundList = this.getFantasyRoundByFantasySeasonId(fantasySeasonId);

        String range = new FantasyRoundUtil().createIdRange(fantasyRoundList, currentRound.getFantasyRoundId());
        List<FantasyTeamRound> fantasyTeamRoundList = fantasyTeamDao.getFantasyTeamRoundByRoundIdRange(range, fantasyTeamId, "DESC");
        for(FantasyTeamRound fantasyTeamRound : fantasyTeamRoundList){
            if(fantasyTeamRound.isPlayersSelected()){
                updateDependencies(fantasyTeamRound);
                return fantasyTeamRound;
            }
        }
        return null;
    }

    public void updateFantasyTeamPoints(int fantasyRoundId) {
        List<FantasyTeamRound> fantasyTeamRoundList =this.getFantasyTeamRoundsByFantasyRoundId(fantasyRoundId);
        List<Integer> matchIdList = this.getMatchIdsForRound(fantasyRoundId);
        List<Match> matchList = leagueService.getMatchByIdRange(matchIdList);
        for(FantasyTeamRound fantasyTeamRound: fantasyTeamRoundList){
            int sum = fantasyTeamRound.getStartSum();
            List<Player> playerList = fantasyTeamRound.getPlayerList();
            for(Player player : playerList){
                Player updatedPlayer = playerService.getPlayerById(player.getPlayerId());
                int leagueRoundId = getLeagueRoundId(matchList, updatedPlayer);
                PlayerStats playerStats = leagueService.getPlayerStatsByPlayerAndRound(player.getPlayerId(), leagueRoundId);
                if(playerStats != null){
                    sum+= playerStats.getPoints();
                }
            }
            fantasyTeamRound.setPoints(sum);
            saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        }
    }

    public void updateOfficialFantasyTeams(int fantasyRoundId) {
        FantasyRound fantasyRound = this.getFantasyRoundById(fantasyRoundId);
        if(fantasyRound != null && ! fantasyRound.isNew() && fantasyRound.isDateExceeded()){
            fantasyTeamDao.updateFantasyTeamRoundsWhenRoundIsClosed(fantasyRoundId);
        }
    }

    private int getLeagueRoundId(List<Match> matchList, Player player) {
        if( player== null) return -1;
        for(Match match : matchList){
            if(match.getHomeTeam().equals(player.getTeam()) || match.getAwayTeam().equals(player.getTeam())){
                return match.getLeagueRound().getLeagueRoundId();
            }
        }
        return -1;
    }

    public List<FantasyManager> getAllFantasyManagers() {
        List<FantasyManager> list = fantasyManagerDao.getAllFantasyManagers();
        return list;
    }

    public List<FantasyManager> getAllActiveFantasyManagers() {
        return fantasyManagerDao.getAllActiveFantasyManagers();
    }

    private FantasyCompetition createDefaultCompetitionForSeason(FantasySeason fantasySeason) {
        FantasyCompetition fantasyCompetition = new FantasyCompetition();
        fantasyCompetition.setFantasySeason(fantasySeason);
        fantasyCompetition.setDefaultCompetition(true);
        fantasyCompetition.setName("Competetion for: " + fantasySeason.getName());
        return fantasyCompetition;
    }

    private void saveFantasyRoundsForSeason(FantasySeason fantasySeason, List<FantasyRound> fantasyRounds) {
        for(int i = 0; i < fantasyRounds.size(); i++){
            FantasyRound fantasyRound = fantasyRounds.get(i);
            fantasyRoundDao.saveOrUpdateFantasyRound(fantasyRound);
            if (i == 0){
                fantasySeason.setCurrentRound(fantasyRound);
            }
            for (int j = 0; j < fantasyRound.getMatchList().size(); j ++){
                addMatchToRound(fantasyRound.getMatchList().get(j).getMatchId(), fantasyRound.getFantasyRoundId());
            }
        }
    }

    private void createRoundsForFantasyTeam(FantasyTeam fantasyTeam) {
        FantasySeason fantasySeason = this.getDefaultFantasySeason();
        FantasyCompetition fantasyCompetition = fantasyCompetitionService.getDefaultFantasyCompetitionBySeasonId(fantasySeason.getFantasySeasonId());
        fantasyCompetitionService.addTeamToCompetetion(fantasyCompetition.getFantasyCompetitionId(), fantasyTeam.getTeamId());
        List<FantasyRound> fantasyRoundList = this.getFantasyRoundByFantasySeasonId(fantasySeason.getFantasySeasonId());
        for(FantasyRound fantasyRound: fantasyRoundList){
            FantasyTeamRound fantasyTeamRound = new FantasyTeamRound();
            fantasyTeamRound.setFantasyTeam(fantasyTeam);
            fantasyTeamRound.setFantasyRound(fantasyRound);
            this.saveOrUpdateFantasyTeamRound(fantasyTeamRound);
        }
    }

    private void updateDependencies(FantasyTeam fantasyTeam){
        if(fantasyTeam == null) return;
        FantasyManager fantasyManager = this.getFantasyManagerById(fantasyTeam.getFantasyManager().getManagerId());
        fantasyTeam.setFantasyManager(fantasyManager);
        //TODO Update multimedia object

    }

    private void updateDependencies(FantasySeason fantasySeason){
        FantasyCompetition fantasyCompetition = fantasyCompetitionService.getFantasyCompetitionById(fantasySeason.getDefaultFantasyCompetition().getFantasyCompetitionId());
        fantasySeason.setDefaultFantasyCompetition(fantasyCompetition);
        Season season = leagueService.getSeasonById(fantasySeason.getSeason().getSeasonId());
        fantasySeason.setSeason(season);
        FantasyRound fantasyRound = getFantasyRoundById(fantasySeason.getCurrentRound().getFantasyRoundId());
        fantasySeason.setCurrentRound(fantasyRound);

    }

    private void updateDependencies(FantasyTeamRound fantasyTeamRound){
        FantasyTeam fantasyTeam = this.getFantasyTeamById(fantasyTeamRound.getFantasyTeam().getTeamId());
        fantasyTeamRound.setFantasyTeam(fantasyTeam);
        FantasyRound fantasyRound = this.getFantasyRoundById(fantasyTeamRound.getFantasyRound().getFantasyRoundId());
        fantasyTeamRound.setFantasyRound(fantasyRound);
        Formation formation = fantasyTeamRound.getFormation();

        for(int i = 1; i < 12; i++){
            Player player = playerService.getPlayerById(fantasyTeamRound.getPlayerByFormationPosition(formation.getPosition(i)).getPlayerId());
            fantasyTeamRound.addPlayer(formation.getPosition(i),player);
        }
    }

    private List<FantasyRound> createRoundsForSeason(FantasySeason fantasySeason){
        int numberOfTeams = fantasySeason.getSeason().getNumberOfTeams();
        int numberOfRounds = (numberOfTeams*2)-2;
        List<FantasyRound> fantasyRounds = new ArrayList<FantasyRound>();
        List<LeagueRound> leagueRounds = leagueService.getLeagueRoundsBySeasonId(fantasySeason.getSeason().getSeasonId());
        for(int i = 0; i < numberOfRounds ; i++){
            FantasyRound fantasyRound = new FantasyRound();
            fantasyRound.setRound(i + 1);
            fantasyRound.setOpenForChange(true);
            fantasyRound.setFantasySeason(fantasySeason);
            LeagueRound leagueRound = null;
            for(int j = 0; j < leagueRounds.size() ; j++){
                leagueRound = leagueRounds.get(j);
                if(leagueRound.getRound() == (i+1)){
                    List<Match> matchList = leagueService.getMatchByLeagueRoundId(leagueRound.getLeagueRoundId());
                    fantasyRound.setMatchList(matchList);
                }
            }
            fantasyRounds.add(fantasyRound);
        }
        return fantasyRounds;
    }
}
