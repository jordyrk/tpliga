package org.tpl.business.model.fantasy;

import org.tpl.business.model.Match;
import org.tpl.business.model.comparator.MatchComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/*
Created by jordyr, 25.nov.2010

*/

public class FantasyRound {
    private int fantasyRoundId = -1;
    private int round;
    private FantasySeason fantasySeason;
    private List<Match> matchList = new ArrayList<Match>();
    private boolean openForChange;
    private Date closeDate;


    public int getFantasyRoundId() {
        return fantasyRoundId;
    }

    public void setFantasyRoundId(int fantasyRoundId) {
        this.fantasyRoundId = fantasyRoundId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public FantasySeason getFantasySeason() {
        return fantasySeason;
    }

    public void setFantasySeason(FantasySeason fantasySeason) {
        this.fantasySeason = fantasySeason;
    }

      public boolean isNew(){
        return fantasyRoundId == -1;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

    public boolean isOpenForChange() {
        return openForChange;
    }

    public boolean isClosed(){
        return ! openForChange;
    }

    public void setOpenForChange(boolean openForChange) {
        this.openForChange = openForChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FantasyRound that = (FantasyRound) o;

        if (fantasyRoundId != that.fantasyRoundId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fantasyRoundId;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public boolean isDateExceeded() {

        return new Date().getTime() >= closeDate.getTime();
    }

    public void sortMatchList() {
        Collections.sort(matchList, new MatchComparator());
    }

    public boolean isFantasyRoundFirstInSeason() {
        return round == 1;
    }
}
