package com.company.Actions;

import com.company.EntArtefacts;
import com.company.EntLocation;

public class GetOrDrop extends Action {


    public GetOrDrop(String act,EntLocation location,
                     EntArtefacts subject,String name) {
        currLocation = location;
        playerName = name;
        player = currLocation.getPlayerByName(playerName);
        switch (act){
            case "get" -> implementGet(subject);
            case "drop" -> implementDrop(subject);
        }
    }

    public void implementGet(EntArtefacts item) {
        player.addAnArtefact(item);
        currLocation.getArtefacts().remove(item);
        output = "You picked up a "+item.getName();
    }

    public void implementDrop(EntArtefacts item) {
        player.getInventory().remove(item);
        currLocation.addAnItem(item);
        output = "You put down a "+item.getName();
    }
}
