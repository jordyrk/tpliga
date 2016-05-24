package org.tpl.business.model.fantasy;

import java.util.ArrayList;
import java.util.List;

public enum Formation {


    FOURFOURTWO ("4-4-2",4,4,2,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,FormationPosition.DEFENDER4,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,FormationPosition.MIDFIELDER4,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2),
    THREEFIVETWO("3-5-2",3,5,2,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,FormationPosition.MIDFIELDER4,FormationPosition.MIDFIELDER5,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2),
    FOURTHREETHREE("4-3-3",4,3,3,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,FormationPosition.DEFENDER4,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2, FormationPosition.STRIKER3),
    FOURFIVEONE("4-5-1",4,5,1,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,FormationPosition.DEFENDER4,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,FormationPosition.MIDFIELDER4,FormationPosition.MIDFIELDER5,
            FormationPosition.STRIKER1),
    FIVETHREETWO("5-3-2",5,3,2,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,FormationPosition.DEFENDER4,FormationPosition.DEFENDER5,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2),
    FOURTWOFOUR("4-2-4",4,2,4,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,FormationPosition.DEFENDER4,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2,FormationPosition.STRIKER3,FormationPosition.STRIKER4),
    THREEFOURTHREE("3-4-3",3,4,3,
            FormationPosition.GOALKEEPER,
            FormationPosition.DEFENDER1,FormationPosition.DEFENDER2,FormationPosition.DEFENDER3,
            FormationPosition.MIDFIELDER1,FormationPosition.MIDFIELDER2,FormationPosition.MIDFIELDER3,FormationPosition.MIDFIELDER4,
            FormationPosition.STRIKER1,FormationPosition.STRIKER2, FormationPosition.STRIKER3);

    private String description;
    private int numberOfDefenders;
    private int numberOfMidfielders;
    private int numberOfStrikers;
    FormationPosition keeperPosition = FormationPosition.GOALKEEPER;
    List<FormationPosition> defenderPositions;
    List<FormationPosition> midfielderPositions;
    List<FormationPosition> strikerPositions;
    FormationPosition[] formationPositions;

    Formation(String description, int numberOfDefenders, int numberOfMidfielders, int numberOfStrikers, FormationPosition... formationPosition) {
        this.description = description;
        this.numberOfDefenders = numberOfDefenders;
        this.numberOfMidfielders = numberOfMidfielders;
        this.numberOfStrikers = numberOfStrikers;
        this.formationPositions = formationPosition;
        defenderPositions = new ArrayList<FormationPosition>();
        midfielderPositions = new ArrayList<FormationPosition>();
        strikerPositions = new ArrayList<FormationPosition>();

        defenderPositions.add(FormationPosition.DEFENDER1);
        defenderPositions.add(FormationPosition.DEFENDER2);
        defenderPositions.add(FormationPosition.DEFENDER3);
        if(numberOfDefenders >= 4){
            defenderPositions.add(FormationPosition.DEFENDER4);
        }
        if(numberOfDefenders == 5){
            defenderPositions.add(FormationPosition.DEFENDER5);
        }
        midfielderPositions.add(FormationPosition.MIDFIELDER1);
        midfielderPositions.add(FormationPosition.MIDFIELDER2);
        if(numberOfMidfielders >= 3){
            midfielderPositions.add(FormationPosition.MIDFIELDER3);
        }
        if(numberOfMidfielders >= 4){
            midfielderPositions.add(FormationPosition.MIDFIELDER4);
        }
        if(numberOfMidfielders == 5){
            midfielderPositions.add(FormationPosition.MIDFIELDER5);
        }
        strikerPositions.add(FormationPosition.STRIKER1);

        if(numberOfStrikers >= 2){
            strikerPositions.add(FormationPosition.STRIKER2);
        }
        if(numberOfStrikers >= 3){
            strikerPositions.add(FormationPosition.STRIKER3);
        }
        if(numberOfStrikers == 4){
            strikerPositions.add(FormationPosition.STRIKER4);
        }
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfDefenders() {
        return numberOfDefenders;
    }

    public int getNumberOfMidfielders() {
        return numberOfMidfielders;
    }

    public int getNumberOfStrikers() {
        return numberOfStrikers;
    }

    public FormationPosition getKeeperPosition() {
        return keeperPosition;
    }

    public List<FormationPosition> getDefenderPositions() {
        return defenderPositions;
    }

    public List<FormationPosition> getMidfielderPositions() {
        return midfielderPositions;
    }

    public List<FormationPosition> getStrikerPositions() {
        return strikerPositions;
    }

    public List<FormationPosition> getAllPositions(){
        List<FormationPosition> allPositions = new ArrayList<FormationPosition>();
        allPositions.add(keeperPosition);
        allPositions.addAll(defenderPositions);
        allPositions.addAll(midfielderPositions);
        allPositions.addAll(strikerPositions);
        return allPositions;

    }

    public boolean validPositionForFormation(FormationPosition formationPosition){
        for(FormationPosition fp: getAllPositions()){
            if (fp == formationPosition){
                return true;
            }
        }
        return false;
    }

    public FormationPosition getPosition(int number){
        return formationPositions[--number];
    }
}
