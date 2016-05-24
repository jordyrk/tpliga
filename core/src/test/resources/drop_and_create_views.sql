DROP VIEW fantasy_league_match_view ;
DROP VIEW fantasy_cup_match_view ;
DROP VIEW fantasy_cup_group_match_view ;

DROP VIEW player_team_round_view  ;
DROP VIEW player_stats_season_view;
DROP VIEW player_team_view;
DROP VIEW fantasy_competition_standing_view;
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

create view player_team_round_view as
select player.*, fantasy_team_round.fantasyteamid, fantasy_team_round.fantasyroundid from player, fantasy_team_round
  where (player.id in (select player_1_id from fantasy_team_round where fantasyteamid = 1 )  and player.id = fantasy_team_round.player_1_id )
  or (player.id in (select player_2_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_2_id )
  or (player.id in (select player_3_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_3_id )
  or (player.id in (select player_4_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_4_id )
  or (player.id in (select player_5_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_5_id )
  or (player.id in (select player_6_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_6_id )
  or (player.id in (select player_7_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_7_id )
  or (player.id in (select player_8_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_8_id )
  or (player.id in (select player_9_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_9_id )
  or (player.id in (select player_10_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_10_id )
  or (player.id in (select player_11_id from fantasy_team_round where fantasyteamid = 1)  and player.id = fantasy_team_round.player_11_id );

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