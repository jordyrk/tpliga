package org.tpl.business.model.comparator;



import org.tpl.business.model.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    public int compare(Player o1, Player o2) {
        return (o1.getLastName()).compareTo(o2.getLastName());
    }
}
