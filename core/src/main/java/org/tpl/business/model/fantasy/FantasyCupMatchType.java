package org.tpl.business.model.fantasy;

public enum FantasyCupMatchType {
    FINAL("A",1), SEMIFINAL("B",2), QUARTERFINAL("C",4), EIGHTFINAL("D",8), SIXTEENFINAL("E",16), THIRTYTWOFINAL("F",32);

    String matchPrefix;
    int numberOfMatches;

    FantasyCupMatchType(String matchPrefix, int numberOfMatches) {
        this.matchPrefix = matchPrefix;
        this.numberOfMatches = numberOfMatches;
    }

    public String getMatchPrefix() {
        return matchPrefix;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public static FantasyCupMatchType getFantasyCupMatchType(int currentRoundNumber, int numberOfRounds) {
        FantasyCupMatchType fantasyCupMatchType = getStarting(numberOfRounds);
        for(int i = 0; i < currentRoundNumber; i++){
            fantasyCupMatchType = getNext(fantasyCupMatchType);
        }
        return fantasyCupMatchType;
    }

    public static FantasyCupMatchType getStarting(int numberOfRounds){
        switch (numberOfRounds){
            case 1: return FINAL;
            case 2: return SEMIFINAL;
            case 3: return QUARTERFINAL;
            case 4: return EIGHTFINAL;
            case 5: return SIXTEENFINAL;
            case 6: return THIRTYTWOFINAL;
            default:return null;

        }
    }

    public static FantasyCupMatchType getPrevious(FantasyCupMatchType fantasyCupMatchType){
        switch (fantasyCupMatchType){
            case FINAL: return SEMIFINAL;
            case SEMIFINAL: return QUARTERFINAL;
            case QUARTERFINAL: return EIGHTFINAL;
            case EIGHTFINAL: return SIXTEENFINAL;
            case SIXTEENFINAL: return THIRTYTWOFINAL;
            case THIRTYTWOFINAL:return null;
            default:return null;
        }
    }

    public static FantasyCupMatchType getNext(FantasyCupMatchType fantasyCupMatchType){
        switch (fantasyCupMatchType){
            case FINAL: return null;
            case SEMIFINAL: return FINAL;
            case QUARTERFINAL: return SEMIFINAL;
            case EIGHTFINAL: return QUARTERFINAL;
            case SIXTEENFINAL:return EIGHTFINAL;
            case THIRTYTWOFINAL: return SIXTEENFINAL;
            default:return null;
        }
    }
}
