package com.company.Actions;

import com.company.EntLocation;
import com.company.EntPlayer;

import java.util.HashMap;

public class Goto extends Action {

    public Goto(HashMap<String,EntLocation> entities,
                String currName, String toName, String name) {
        entityList = entities;
        currLocation = entityList.get(currName);
        playerName = name;
        implement(currName,toName);
    }

    public void implement(String currName, String toName) {
        EntPlayer player = currLocation.getPlayerByName(playerName);
        entityList.get(currName).getPlayers().remove(player);
        entityList.get(toName).addAnItem(player);
        currLocation = entityList.get(toName);
        printEnvironment();
    }

}
