package org.tpl.business.model.fantasy;

import org.tpl.business.model.Player;

public class PlayerInFormation extends Player{
    FormationPosition formationPosition;
    public PlayerInFormation(Player player, FormationPosition formationPosition) {
        super(player);
        this.formationPosition = formationPosition;
    }
    public FormationPosition getFormationPosition() {
        return formationPosition;
    }
}
