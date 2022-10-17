package com.company.Actions;

import com.company.*;
import com.company.Exceptions.ActionException;

import java.util.HashMap;

public class Consume extends Action {

    public Consume(HashMap<String, EntLocation> entities,
                   String currName, String name, GameAction act) {
        entityList = entities;
        currLocation = entityList.get(currName);
        playerName = name;
        player = currLocation.getPlayerByName(name);
        action = act;
        try {
            implement();
        } catch (Exception e) {
            output = e.toString();
        }
    }

    public void implement() throws ActionException {
        for (String item : action.getConsumptions()) {
            if (item.equals("health")) {
                player.decreaseHealth();
            } else if (entityList.containsKey(item)) {
                // consume one-way path from current Location to a next Location
                currLocation.getNextLocations().remove(item);
            } else if (player.getInvNameList().contains(item)) {
                EntArtefacts sub = player.getInvByName(item);
                player.getInventory().remove(sub);
                entityList.get("storeroom").addAnItem(sub);
            } else {
                GameEntity sub = findItemByName(item);
                entityList.get("storeroom").addAnItem(sub);
            }
        }
    }
}
