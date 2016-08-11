<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
{
"id": "${fantasyTeam.teamId}",
"name": "${fantasyTeam.name}",
"stadium": "${fantasyTeam.stadium}",
"teamSpirit": "${fantasyTeam.teamSpirit}",
"supporterClub": "${fantasyTeam.supporterClub}",
"latestNews": "${fantasyTeam.latestNews}",
"managerName": "${fantasyTeam.fantasyManager.managerName}",
"imageUrl": "http://www.tpliga.org${fantasyTeam.imageUrl}"
}