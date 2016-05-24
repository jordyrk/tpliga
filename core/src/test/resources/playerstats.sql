--Det må lages et view som henter inn runde via match for å kunne vise pr siste antall kamper. Viewet må også ta hensyn til sesong.
select player.firstName, player.lastName, player.position, team.shortName,
  sum(player_stats_season_view.points) as sumpoints , sum(player_stats_season_view.assists) as sumassists , sum(player_stats_season_view.goals) as sumgoals,
  sum(player_stats_season_view.missedpenalty) as summissedpenalty, sum(player_stats_season_view.owngoal) as sumowngoal, sum(player_stats_season_view.playedMinutes) as sumplayedMinutes,
  sum(player_stats_season_view.savedpenalty) as sumsavedpenalty, count(*) as sumPlayedMatches
  from player_stats_season_view, player, team
  where player_stats_season_view.playerid = player.id
  AND player.team_id = team.id
  group by player_stats_season_view.playerid

select player.firstName, player.lastName, player.position, team.shortName,
  sum(player_stats_season_view.points) as sumpoints , sum(player_stats_season_view.assists) as sumassists , sum(player_stats_season_view.goals) as sumgoals,
  sum(player_stats_season_view.missedpenalty) as summissedpenalty, sum(player_stats_season_view.owngoal) as sumowngoal, sum(player_stats_season_view.playedMinutes) as sumplayedMinutes,
  sum(player_stats_season_view.savedpenalty) as sumsavedpenalty, count(*) as sumPlayedMatches
  from player_stats_season_view
  LEFT OUTER JOIN player ON player_stats_season_view.playerid = player.id
  LEFT OUTER JOIN team ON player_stats_season_view

SELECT player_stats_season_view.*,player.*, team.*
  FROM player_stats_season_view, player, team
  WHERE player_stats_season_view.playerid = player.id
  AND player.team_id = team.id

SELECT sum(points) as sumpoints, playerId, teamId  FROM player_stats_season_view
  where leagueroundid in (30,31,32,33,34,35)
  and teamid = 2
  and position = 'STRIKER'
  and seasonid = 1
  group by player_stats_season_view.playerid
  order by sum(points) desc
  limit 10;


SELECT count(*) as playedmatches, playerId, teamId  FROM player_stats_season_view
  where leagueroundid in (30,31,32,33,34,35)
  and teamid = 2
  and position = 'STRIKER'
  and seasonid = 1
  group by player_stats_season_view.playerid
  order by count(*) desc
  limit 10;