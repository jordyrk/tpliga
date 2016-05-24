package org.tpl.business.service.fantasy.score;

public class ScoreCalculator {

    protected double getStandardDeviation(double diff){
        if(diff < 2.5) return 0.5;
        else if(diff < 7.5) return 0.4;
        else if(diff < 11.5) return 0.35;
        else if(diff <= 12) return 0.34;
        else return 0.32;
    }

    protected int[] calculateBaseScore(double averageScore){
        if(averageScore <= 29.5)return new int[]{0,0};
        else if(averageScore>29.5 && averageScore <= 47)return new int[]{1,1};
        else return new int[]{2,2};
    }

    public int[] calculateScore(int homePoints, int awayPoints){

        double average = ((double)(homePoints + awayPoints)) / 2.0;
        int[] score = calculateBaseScore(average);
        if(homePoints == awayPoints){
            return score;
        }
        int diff = homePoints - awayPoints;
        if(diff < 0){
            diff *= -1;
        }

        double standardDeviation = getStandardDeviation(diff/2);

        if(homePoints > awayPoints){
            int additionalGoals = (int)(standardDeviation*(homePoints -average) );
            if(additionalGoals > 5){
                additionalGoals= 5;
            }
            score[0] += additionalGoals;
        }else{
             int additionalGoals = (int)(standardDeviation*(awayPoints -average));
            if(additionalGoals > 5){
                additionalGoals= 5;
            }
            score[1] += additionalGoals;
        }
        return score;
    }
}
