<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    {
    "playerId": ${player.playerId},
    "firstName": "${player.firstName}",
    "lastName": "${player.lastName}",
    "playerPosition": "${player.playerPosition}",
    "price": "${player.price}",
    "team": "${player.team.shortName}",
    "shirtNumber": "${player.shirtNumber}",
    "externalId": "${player.externalId}",
    "goals": "${sumPlayerStats.goals}" ,
    "assists":"${sumPlayerStats.assists}" ,
    "ownGoal":"${sumPlayerStats.ownGoal}" ,
    "missedPenalty":"${sumPlayerStats.missedPenalty}" ,
    "savedPenalty":"${sumPlayerStats.savedPenalty}" ,
    "yellowCard":"${sumPlayerStats.yellowCard}" ,
    "redCard":"${sumPlayerStats.sumRedCard}" ,
    "wholeGame":"${sumPlayerStats.sumWholeGame}" ,
    "startedGame":"${sumPlayerStats.sumStartedGame}" ,
    "playedMatches":"${sumPlayerStats.playedMatches}" ,
    "playedMinutes":"${sumPlayerStats.playedMinutes}" ,
    "points":"${sumPlayerStats.points}"
    }


