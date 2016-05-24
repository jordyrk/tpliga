<%@ page contentType="text/html;charset=utf-8" language="java" pageEncoding="iso-8859-1" %>
<div class="topp">
    <h1 id="PlayerName">&nbsp;</h1>
    <img src="../bitmaps/footballcard/playeroutline.png">
    <span class="playerinfo"><span id="PlayerTeam">&nbsp;</span> - <span id="PlayerPosition">&nbsp;</span></span><br>
    <span id="PlayerPrice" class="price">&nbsp;</span>
</div>

<div class="bottom">
    <span>2011/2012 Premier League</span>
    <table>
        <tr>
            <td>Spilte kamper</td>
            <td id="PlayerMatches"></td>
        </tr>
        <tr>
            <td>Fra start</td>
            <td id="PlayerStartedGame"></td>
        </tr>

        <tr>
            <td>Antall poeng</td>
            <td id="PlayerPoints"></td>
        </tr>
        <tr>
            <td>Antall m&aring;l</td>
            <td id="PlayerGoals"></td>
        </tr>
        <tr>
            <td>Antall målgivende</td>
            <td id="PlayerAssists"></td>
        </tr>
        <tr>
            <td>Antall selvmål</td>
            <td id="PlayerOwnGoals"></td>
        </tr>

        <tr>
            <td>Reddet straffer</td>
            <td id="PlayerSavedPenalty"></td>
        </tr>
        <tr>
            <td>Gule kort</td>
            <td id="PlayerYellowCards"></td>
        </tr>
        <tr>
            <td>R&oslash;de kort</td>
            <td id="PlayerRedCards"></td>
        </tr>

    </table>
</div>