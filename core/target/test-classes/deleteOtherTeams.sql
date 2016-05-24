
select * from  fantasy_team_round where fantasyteamid = 2 AND fantasyroundid=35;

update fantasy_team_round set
  player_1_id = -1,
  player_2_id = -1,
  player_3_id = -1,
  player_4_id = -1,
  player_5_id = -1,
  player_6_id = -1,
  player_7_id = -1,
  player_8_id = -1,
  player_9_id = -1,
  player_10_id = -1,
  player_11_id = -1
  where fantasyteamid = 2 AND fantasyroundid=35;