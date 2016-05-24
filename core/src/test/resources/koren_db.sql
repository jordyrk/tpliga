CREATE TABLE league(
	id INTEGER NOT NULL AUTO_INCREMENT,
	shortname VARCHAR(255),
	fullname VARCHAR(255),
	PRIMARY KEY (id)
);
CREATE TABLE season(
    id INTEGER NOT NULL AUTO_INCREMENT,
	leagueid INTEGER NOT NULL REFERENCES league(id),
	numberofteams INTEGER,
	startdate DATE,
	enddate DATE,
    PRIMARY KEY (id)
);
CREATE TABLE team(
	id INTEGER NOT NULL AUTO_INCREMENT,
	externalId INTEGER,
	shortname VARCHAR(255),
	fullname  VARCHAR(255),
	alias  VARCHAR(255),
	PRIMARY KEY (id)
);
CREATE TABLE team_season(
	seasonid INTEGER NOT NULL REFERENCES season(id),
	teamid INTEGER NOT NULL REFERENCES team(id),
	position INTEGER,
	gamesplayed INTEGER,
	wins INTEGER,
	draws INTEGER,
	losses INTEGER,
	goalsscored INTEGER,
	goalsagainst INTEGER,
	points INTEGER,
	PRIMARY KEY (seasonid,teamid)
);

CREATE TABLE player(
	id INTEGER NOT NULL AUTO_INCREMENT,
	externalId INTEGER,
	firstname VARCHAR(255),
	lastname VARCHAR(255),
	alias VARCHAR(255),
	position VARCHAR(32),
	price INTEGER,
	shirtnumber INTEGER,
	soccernetId INTEGER,
	team_id INTEGER REFERENCES team(id),
	PRIMARY KEY (id)
);

CREATE TABLE league_match(
	id INTEGER NOT NULL AUTO_INCREMENT,
	externalId INTEGER,
	matchdate DATETIME,
	homegoals INTEGER,
	awaygoals INTEGER,

	homeshots INTEGER,
	awayshots INTEGER,
	homeshotsontarget INTEGER,
	awayshotsontarget INTEGER,
	homefouls INTEGER,

	awayfouls INTEGER,
	homeoffside INTEGER,
	awayoffside INTEGER,
	homepossesion INTEGER,
	awaypossesion INTEGER,

	homeyellow INTEGER,
	awayyellow INTEGER,
	homered INTEGER,
	awayred INTEGER,
	homesaves INTEGER,

	awaysaves INTEGER,
	hometeamid INTEGER NOT NULL REFERENCES team(id),
	awayteamid INTEGER NOT NULL REFERENCES team(id),
	leagueroundid INTEGER NOT NULL REFERENCES league_round(id),
	matchUrl VARCHAR(255),
	soccerNetId VARCHAR(255),
	fantasypremierleague_id INTEGER ,
	playerStatsUpdated INTEGER,
	PRIMARY KEY (id)
);

CREATE TABLE league_round(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
	seasonid INTEGER NOT NULL REFERENCES season(id),
	roundnumber INTEGER NOT NULL,
	UNIQUE (seasonid, roundnumber)
);


CREATE TABLE player_stats(
	matchid INTEGER NOT NULL REFERENCES league_match(id),
	playerid INTEGER NOT NULL REFERENCES player(id),
	goals INTEGER,
	assists INTEGER,
	owngoal INTEGER,
	missedpenalty INTEGER,
	savedpenalty INTEGER,
	yellowcard INTEGER,
	redcard INTEGER,
	wholegame INTEGER,
	startedgame INTEGER,
    playedMinutes INTEGER,
	points INTEGER,
	shots INTEGER,
	shotsOnGoal INTEGER,
	offsides INTEGER,
	foulsCommited INTEGER,
	foulsDrawn INTEGER,
	saves INTEGER,
	manofthematch INTEGER,
	easportsppi INTEGER,
	teamid INTEGER NOT NULL REFERENCES team(id),
	PRIMARY KEY (matchid,playerid)
);

CREATE TABLE default_season(
    seasonid INTEGER NOT NULL REFERENCES season(id),
    PRIMARY KEY (seasonid)
);
DROP VIEW player_stats_season_view;
CREATE VIEW player_stats_season_view AS
  SELECT player_stats.*, season.id AS seasonid, league_round.id AS leagueroundid,
  player.position
FROM player_stats
  LEFT OUTER JOIN league_match ON player_stats.matchid = league_match.id
  LEFT OUTER JOIN league_round ON league_match.leagueroundid = league_round.id
  LEFT OUTER JOIN season ON league_round.seasonid = season.id
  LEFT OUTER JOIN player ON player.id = player_stats.playerid;

CREATE VIEW player_team_view AS
  SELECT player.*, team.shortname, team.fullname
FROM player
  LEFT OUTER JOIN team ON player.team_id = team.id;

CREATE TABLE fantasy_season(
    id INTEGER NOT NULL AUTO_INCREMENT,
	seasonid INTEGER NOT NULL REFERENCES season(id),
	name VARCHAR(50),
	activeSeason INTEGER NOT NULL,
	maxTeamPrice INTEGER,
	startingNumberOfTransfers INTEGER,
	numberOfTransfersPerRound INTEGER,
	registrationActive INTEGER NOT NULL,
	defaultfantasycompetitionid INTEGER REFERENCES fantasy_competition(id),
	currentroundid INTEGER REFERENCES fantasy_round(id),
    PRIMARY KEY (id)
);

CREATE TABLE fantasy_manager(
    id  INTEGER NOT NULL AUTO_INCREMENT,
    userId varchar(64) NULL,
    managerName varchar(255) NULL,
    active INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE fantasy_team(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NULL,
  stadium VARCHAR(255) NULL,
  teamSpirit VARCHAR(500) NULL,
  supporterClub VARCHAR(255) NULL,
  latestNews VARCHAR(500) NULL,
  multiMediaImageId INTEGER NULL,
  managerid INTEGER NOT NULL REFERENCES fantasy_manager(id),
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_competition(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  fantasyseasonid INTEGER NOT NULL REFERENCES fantasy_season(id),
  numberofteams INTEGER,
  defaultcompetition INTEGER,
  PRIMARY KEY (id)
);

ALTER TABLE fantasy_round ADD COLUMN closeDate TIMESTAMP;
ALTER TABLE fantasy_round MODIFY closeDate TIMESTAMP ;
CREATE TABLE fantasy_round(
  id INTEGER NOT NULL AUTO_INCREMENT,
  round INTEGER NOT NULL,
  openforchange INTEGER,
  fantasyseasonid INTEGER NOT NULL REFERENCES fantasy_season(id),
  closeDate DATE,
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_round_match(
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  leaguematchid INTEGER NOT NULL REFERENCES league_match(id),
  PRIMARY KEY (fantasyroundid,leaguematchid)
);

CREATE TABLE fantasy_competition_team(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasycompetitionid INTEGER NOT NULL REFERENCES fantasy_competition(id),
  PRIMARY KEY (fantasyteamid,fantasycompetitionid)
);

CREATE TABLE fantasy_team_round(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  official INTEGER,
  officialwhenroundisclosed INTEGER,
  points INTEGER,
  formation VARCHAR (64),
  player_1_id INTEGER NULL REFERENCES player(id),
  player_2_id INTEGER NULL REFERENCES player(id),
  player_3_id INTEGER NULL REFERENCES player(id),
  player_4_id INTEGER NULL REFERENCES player(id),
  player_5_id INTEGER NULL REFERENCES player(id),
  player_6_id INTEGER NULL REFERENCES player(id),
  player_7_id INTEGER NULL REFERENCES player(id),
  player_8_id INTEGER NULL REFERENCES player(id),
  player_9_id INTEGER NULL REFERENCES player(id),
  player_10_id INTEGER NULL REFERENCES player(id),
  player_11_id INTEGER NULL REFERENCES player(id),
  PRIMARY KEY (fantasyteamid,fantasyroundid)
);

CREATE TABLE fantasy_rule(
  id INTEGER NOT NULL AUTO_INCREMENT,
  ruleType VARCHAR(64) NOT NULL,
  points INTEGER NOT NULL,
  qualifingNumber INTEGER NOT NULL,
  name VARCHAR(1024) NOT NULL,
  position VARCHAR(64) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE fantasy_competition_standings(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  fantasycompetitionid INTEGER NOT NULL REFERENCES fantasy_competition(id),
  accumulatedpoints INTEGER,
  position INTEGER,
  lastroundposition INTEGER,
  points INTEGER,
  avaragepoints INTEGER,
  minimumpoints INTEGER,
  maximumpoints INTEGER,
  PRIMARY KEY (fantasyteamid,fantasyroundid,fantasycompetitionid)
);
CREATE VIEW fantasy_competition_standing_view AS
SELECT
  SUM(fantasy_team_round.points) AS sumpoints,
  fantasy_team_round.fantasyteamid,
  fantasy_competition_team.fantasycompetitionid,
  fantasy_competition.name,
  fantasy_season.id AS fantasyseasonid
FROM fantasy_team_round
  LEFT OUTER JOIN fantasy_competition_team ON fantasy_competition_team.fantasyteamid = fantasy_team_round.fantasyteamid
  LEFT OUTER JOIN fantasy_competition ON fantasy_competition.id = fantasy_competition_team.fantasycompetitionid
  LEFT OUTER JOIN fantasy_season ON fantasy_season.id = fantasy_competition.fantasyseasonid
GROUP BY
  fantasy_competition.id,
  fantasy_team_round.fantasyteamid;

--TODO:How to set what teams will win the league, qualify or be delegated
CREATE TABLE fantasy_league(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  fantasyseasonid INTEGER NOT NULL REFERENCES fantasy_season(id),
  numberofteams INTEGER,
  numberOfTopTeams INTEGER,
  numberOfSecondTopTeams INTEGER,
  numberOfBottomTeams INTEGER,
  numberOfSecondBotttomTeams INTEGER,
  styletheme VARCHAR(64),
  homeandawaymatches INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_league_team(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasyleagueid INTEGER NOT NULL REFERENCES fantasy_league(id),
  PRIMARY KEY (fantasyteamid, fantasyleagueid)
);


CREATE TABLE fantasy_league_standings(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasyleagueid INTEGER NOT NULL REFERENCES fantasy_league(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  goalsscored INTEGER,
  goalsagainst INTEGER,
  matcheswon INTEGER,
  matchesdraw INTEGER,
  matcheslost INTEGER,
  position INTEGER,
  points INTEGER,
  lastroundposition INTEGER,
  PRIMARY KEY (fantasyteamid, fantasyleagueid, fantasyroundid)
);

CREATE TABLE fantasy_league_rounds(
  fantasyleagueid INTEGER NOT NULL REFERENCES fantasy_cup(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  PRIMARY KEY(fantasyleagueid, fantasyroundid)
);

CREATE TABLE fantasy_cup(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  fantasyseasonid INTEGER NOT NULL REFERENCES fantasy_season(id),
  numberofteams INTEGER,
  numberOfQualifiedTeamsFromGroups INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_cup_team(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasycupid INTEGER NOT NULL REFERENCES fantasy_cup(id),
  PRIMARY KEY (fantasyteamid, fantasycupid)
);

CREATE TABLE fantasy_cup_rounds(
  fantasycupid INTEGER NOT NULL REFERENCES fantasy_cup(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  PRIMARY KEY(fantasycupid, fantasyroundid)
);

--TODO:How to set what teams will win group
CREATE TABLE fantasy_cup_group(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  fantasycupid INTEGER NOT NULL REFERENCES fantasy_cup(id),
  numberofteams INTEGER,
  homeandawaymatches INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_cup_group_team(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasycupgroupid INTEGER NOT NULL REFERENCES fantasy_cup_group(id),
  PRIMARY KEY (fantasyteamid, fantasycupgroupid)
);

CREATE TABLE fantasy_cup_group_rounds(
  fantasycupgroupid INTEGER NOT NULL REFERENCES fantasy_cup_group(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  PRIMARY KEY(fantasycupgroupid, fantasyroundid)
);

CREATE TABLE fantasy_cup_group_standings(
  fantasyteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  fantasycupgroupid INTEGER NOT NULL REFERENCES fantasy_cup_group(id),
  fantasyroundid INTEGER NOT NULL REFERENCES fantasy_round(id),
  goalsscored INTEGER,
  goalsagainst INTEGER,
  matcheswon INTEGER,
  matchesdraw INTEGER,
  matcheslost INTEGER,
  position INTEGER,
  points INTEGER,
  lastroundposition INTEGER,
  PRIMARY KEY (fantasyteamid,fantasycupgroupid, fantasyroundid)
);

CREATE TABLE fantasy_match(
  id INTEGER NOT NULL AUTO_INCREMENT,
  hometeamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  awayteamid INTEGER NOT NULL REFERENCES fantasy_team(id),
  homegoals INTEGER,
  awaygoals INTEGER,
  played INTEGER,
  fantasyroundid  INTEGER NOT NULL REFERENCES fantasy_round(id),
  PRIMARY KEY (id)
);

CREATE TABLE fantasy_league_match(
  fantasymatchid INTEGER NOT NULL REFERENCES fantasy_match(id),
  fantasyleagueid INTEGER NOT NULL REFERENCES fantasy_league(id),
  PRIMARY KEY (fantasymatchid,fantasyleagueid)
);

CREATE TABLE fantasy_cup_match(
  fantasymatchid INTEGER NOT NULL REFERENCES fantasy_match(id),
  fantasycupid INTEGER NOT NULL REFERENCES fantasy_cup(id),
  PRIMARY KEY (fantasymatchid,fantasycupid)
);

CREATE TABLE fantasy_cup_group_match(
  fantasymatchid INTEGER NOT NULL REFERENCES fantasy_match(id),
  fantasycupgroupid INTEGER NOT NULL REFERENCES fantasy_cup_group(id),
  PRIMARY KEY (fantasymatchid,fantasycupgroupid)
);

CREATE VIEW fantasy_league_match_view AS
  SELECT fantasy_match.*, fantasy_league_match.fantasyleagueid
FROM fantasy_league_match
  LEFT OUTER JOIN fantasy_match ON fantasy_league_match.fantasymatchid = fantasy_match.id;

CREATE VIEW fantasy_cup_match_view AS
  SELECT fantasy_match.*, fantasy_cup_match.fantasycupid
FROM fantasy_cup_match
  LEFT OUTER JOIN fantasy_match ON fantasy_cup_match.fantasymatchid = fantasy_match.id;

CREATE VIEW fantasy_cup_group_match_view AS
  SELECT fantasy_match.*, fantasy_cup_group_match.fantasycupgroupid
FROM fantasy_cup_group_match
  LEFT OUTER JOIN fantasy_match ON fantasy_cup_group_match.fantasymatchid = fantasy_match.id;

CREATE TABLE chatmessage(
  id INTEGER NOT NULL AUTO_INCREMENT,
  managerId INTEGER NULL,
  message VARCHAR(1024),
  createdDate TIMESTAMP ,
  PRIMARY KEY (id)
);


CREATE VIEW player_team_round_view AS
SELECT player.id, fantasy_team_round.fantasyteamid, fantasy_team_round.fantasyroundid FROM player, fantasy_team_round
  WHERE  (player.id = fantasy_team_round.player_1_id
  OR player.id = fantasy_team_round.player_2_id
  OR player.id = fantasy_team_round.player_3_id
  OR player.id = fantasy_team_round.player_4_id
  OR player.id = fantasy_team_round.player_5_id
  OR player.id = fantasy_team_round.player_6_id
  OR player.id = fantasy_team_round.player_7_id
  OR player.id = fantasy_team_round.player_8_id
  OR player.id = fantasy_team_round.player_9_id
  OR player.id = fantasy_team_round.player_10_id
  OR player.id = fantasy_team_round.player_11_id);

CREATE VIEW halloffame_view AS
    SELECT fantasy_team_round.fantasyteamid, fantasy_team_round.points, fantasy_round.fantasyseasonid
    FROM fantasy_team_round
    LEFT OUTER JOIN fantasy_round ON fantasy_team_round.fantasyroundid= fantasy_round.id;

DROP VIEW whore_view;
CREATE VIEW whore_view AS
SELECT fantasy_league_standings.fantasyteamid,
        fantasy_league_standings.fantasyleagueid,
        fantasy_league_standings.fantasyroundid,
        fantasy_league_standings.position AS leagueposition,
        fantasy_league_standings.points AS leaguepoints,
        fantasy_competition_standings.accumulatedpoints AS competitionpoints,
        fantasy_competition_standings.position AS competitionposition
  FROM fantasy_league_standings
  LEFT OUTER JOIN fantasy_competition_standings
      ON fantasy_competition_standings.fantasyroundid= fantasy_league_standings.fantasyroundid
  WHERE fantasy_competition_standings.fantasyteamid = fantasy_league_standings.fantasyteamid;