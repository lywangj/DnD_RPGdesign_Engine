package com.company.Actions;

import com.company.EntLocation;
import com.company.EntPlayer;
import com.company.Exceptions.ActionException;
import com.company.GameAction;
import com.company.GameEntity;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Action {

    HashMap<String,EntLocation> entityList;
    EntLocation currLocation;
    String playerName;
    EntPlayer player;
    GameAction action;
    String output;

    public void implement() throws ActionException {}

    public String generateList(ArrayList<String> list) {
        if(list.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for(String item : list) {
            result.append("\n");
            result.append(item);
        }
        return result.toString();
    }

    public void printEnvironment() {
        StringBuilder str = new StringBuilder();
        String opening = "You are in "+currLocation.getDescription()+". ";
        String charList = generateList(currLocation.getCharDescribingList());
        String artList = generateList(currLocation.getArtDescribingList());
        String furList = generateList(currLocation.getFurDescribingList());
        String playerList = generateList(currLocation.getPlayerDescribingList(playerName));
        String nextList = generateList(new ArrayList<>(currLocation.getNextLocations()));

        str.append(opening);
        str.append("You can see:").append(charList).append(artList).append(furList).append(playerList);
        str.append("\nYou can access from here:").append(nextList);
        output = str.toString();
    }


    public GameEntity findItemByName(String itemName) throws ActionException {
        for (EntLocation tgtLocation : entityList.values()) {
            GameEntity sub = tgtLocation.findAnItemByName(itemName);
            if(sub!=null){
                tgtLocation.removeAnItem(sub);
                return sub;
            }
        }
        throw new ActionException.notFoundToConsume();
    }

    public String getOutput() {
        return output;
    }


}
