package org.tpl.business.model.fantasy.util;
/*
Created by jordyr, 31.01.11

*/

import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasyTeamRound;

import java.util.List;

public class FantasyCompetitionStandingChart {

    private List<FantasyCompetitionStanding> fantasyCompetitionStandingList;
    private static int MIN_WIDTH = 300;
    private static int MIN_HEIGHT = 250;

    public FantasyCompetitionStandingChart(List<FantasyCompetitionStanding> fantasyCompetitionStandingList) {
        this.fantasyCompetitionStandingList = fantasyCompetitionStandingList;
    }

    private String getChartTitle(){
        return "chtt=Poeng oversikt";
    }

    private String getChartData(){
        StringBuilder sb = new StringBuilder();
        if(fantasyCompetitionStandingList != null && fantasyCompetitionStandingList.size()>0){
            for(int i = 0; i < fantasyCompetitionStandingList.size(); i++){
                if(i != 0){
                    sb.append(",");
                }
                sb.append(fantasyCompetitionStandingList.get(i).getPoints());
            }
        }
        return "chd=t1:" + sb.toString()+ "|-1";
    }

    private int getNumberOfRounds(){
        return fantasyCompetitionStandingList.size();
    }
    private int getWidth(){
        int width = MIN_WIDTH;
        if(fantasyCompetitionStandingList.size() > 10){
            width += (fantasyCompetitionStandingList.size() - 10) * 20;
        }
        return width;

    }

    private int getMinScale(){
        int min = 0;
        for(int i = 0; i < fantasyCompetitionStandingList.size(); i++){
            int points = fantasyCompetitionStandingList.get(i).getPoints();
            if(min > points){
                min = points;
            }
        }
        return min - 10;
    }
    private int getMaxScale(){
        int max = 0;
        for(int i = 0; i < fantasyCompetitionStandingList.size(); i++){
            int points = fantasyCompetitionStandingList.get(i).getPoints();
            if(max < points){
                max = points;
            }
        }
        return max + 10;
    }

    private String getColor(){
        return "chco=A2C180,3D7930";
    }

    private String getChartAxis(){
        return "chxt=y,x";
    }

    private String getChartSize(){

        return "chs=" + getWidth() + "x" +MIN_HEIGHT;
    }

    private String getChartBarSpacing(){
        return "chbh=a,5";
    }
    private String getChartType(){
        return "cht=bvg";
    }

    private String getChartZeroLine(){
        return "chp=0.05";
    }

    private String getChartScale(){
        return "chds=" + getMinScale() + ","+getMaxScale() + "&chxr=0," + getMinScale() + "," +getMaxScale() + "|1,1," + fantasyCompetitionStandingList.size();
    }

    private String getChartMargins(){
        return "chma=|28";
    }

    private String getChartMarkers(){
        //&chm=t1,000000,0,0,13,-1|t2,000000,0,1,13,-1
        StringBuilder sb = new StringBuilder("chm=");
        if(fantasyCompetitionStandingList != null && fantasyCompetitionStandingList.size()>0){
            for(int i = 0; i < fantasyCompetitionStandingList.size(); i++){
                int placement = -1;
                if(i != 0){
                    sb.append("|");
                }
                if(fantasyCompetitionStandingList.get(i).getPoints() < 0){
                    placement = 1;
                }
                sb.append("t" + fantasyCompetitionStandingList.get(i).getPoints() + ",000000,0,"+i+",13,"+ placement);
            }
        }
        return sb.toString();
    }

    public String getChartString(){
        return "http://chart.apis.google.com/chart?" + getChartAxis() +"&" + getChartBarSpacing() + "&" +getChartSize() + "&" + getChartType() + "&" +getColor() + "&" + getChartScale() + "&" + getChartData() + "&" + getChartMargins() + "&" + getChartTitle() + "&" + getChartMarkers();
    }
}
