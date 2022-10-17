package com.company;

import java.util.ArrayList;

public class EntPlayer extends EntCharacters{

    private final ArrayList<EntArtefacts> inventory;
    private Integer healthLevel;

    public EntPlayer(String name, String description) {
        super(name, description);
        inventory = new ArrayList<>();
        healthLevel = 3;
        type = Type.players;
    }

    public ArrayList<String> getInvNameList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntArtefacts item : inventory) {
            list.add(item.getName());
        }
        return list;
    }

    public ArrayList<String> getInvDescribeList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntArtefacts item : inventory) {
            list.add(item.getDescription());
        }
        return list;
    }

    public void addAnArtefact(EntArtefacts item) {
        inventory.add(item);
    }

    public EntArtefacts getInvByName(String itemName) {
        for(EntArtefacts subject : inventory) {
            if(compareNames(subject.getName(),itemName)) {
//            if(subject.getName().equals(itemName)){
                return subject;
            }
        }
        return null;
    }

    public Boolean checkInInvByName(String itemName) {
        for(EntArtefacts subject : inventory) {
            if(compareNames(subject.getName(),itemName)) {
//            if(subject.getName().equals(itemName)){
                return true;
            }
        }
        return false;
    }

    public void increaseHealth() {
        healthLevel++;
    }

    public void decreaseHealth() {
        healthLevel--;
    }

    public ArrayList<EntArtefacts> getInventory() {
        return inventory;
    }

    public Integer getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(Integer setLevel) {
        healthLevel = setLevel;
    }
}
