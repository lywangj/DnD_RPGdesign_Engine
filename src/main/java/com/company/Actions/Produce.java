package com.company.Actions;

import com.company.*;
import com.company.Exceptions.*;

import java.util.HashMap;

public class Produce extends Action {

    public Produce(HashMap<String, EntLocation> entities,
                   String currName, String name, GameAction act) {
        entityList = entities;
        currLocation = entityList.get(currName);
        player = currLocation.getPlayerByName(name);
        action = act;
        try {
            implement();
        } catch (Exception e) {
            output = e.toString();
        }
    }

    public void implement() throws ActionException {
        for (String item : action.getProductions()) {
            if (item.equals("health")) {
                if (player.getHealthLevel()<3) {
                    player.increaseHealth();
                }
            } else if (entityList.containsKey(item)) {
                // produce one-way path from current Location to a next Location
                currLocation.addANextLocation(item);
            } else {
                GameEntity sub = findItemByName(item);
                currLocation.addAnItem(sub);
            }
        }
    }

}
