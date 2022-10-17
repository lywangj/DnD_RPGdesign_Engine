package com.company.Actions;

import com.company.EntLocation;
import com.company.Exceptions.ActionException;
import com.company.GameAction;
import com.company.GameEntity;

import java.util.HashMap;

public class Reverse extends Action {

    public Reverse(HashMap<String, EntLocation> entities,
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
                player.increaseHealth();
            } else if (entityList.containsKey(item)) {
                // consume one-way path from current Location to a next Location
                currLocation.addANextLocation(item);
            } else {
                GameEntity sub = findItemByName(item);
                currLocation.addAnItem(sub);
            }
        }
    }
}
