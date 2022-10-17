package com.company;

import java.util.ArrayList;
import java.util.HashSet;

public class EntLocation extends GameEntity {

    private final HashSet<String>  nextLocations;
    private final ArrayList<EntCharacters> characters;
    private final ArrayList<EntArtefacts> artefacts;
    private final ArrayList<EntFurniture> furniture;
    private final ArrayList<EntPlayer> players;
    private Boolean startingPt;

    public EntLocation(String name, String description) {
        super(name, description);
        characters = new ArrayList<>();
        artefacts = new ArrayList<>();
        furniture = new ArrayList<>();
        players = new ArrayList<>();
        nextLocations = new HashSet<>();
        startingPt = false;
    }

    public void setStartingPt() {
        startingPt = true;
    }

    public GameEntity findAnItemByName(String itemName) {
        GameEntity sub = getCharByName(itemName);
        if(sub!=null){
            return sub;
        }
        sub = getArtByName(itemName);
        if(sub!=null){
            return sub;
        }
        sub = getFurByName(itemName);
        return sub;
    }

    public void addAnItem(GameEntity item) {
        switch (item.getTypeName()) {
            case "characters" -> characters.add((EntCharacters) item);
            case "artefacts" -> artefacts.add((EntArtefacts) item);
            case "furniture" -> furniture.add((EntFurniture) item);
            case "players" -> players.add((EntPlayer) item);
        }
    }

    public void removeAnItem(GameEntity item) {
        switch (item.getTypeName()) {
            case "characters" -> characters.remove((EntCharacters) item);
            case "artefacts" -> artefacts.remove((EntArtefacts) item);
            case "furniture" -> furniture.remove((EntFurniture) item);
            case "players" -> players.remove((EntPlayer) item);
        }
    }

    public void addANextLocation(String NextLocation) {
        nextLocations.add(NextLocation);
    }

    public ArrayList<String> getCharNameList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntCharacters item : characters) {
            list.add(item.getName());
        }
        return list;
    }

    public ArrayList<String> getArtNameList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntArtefacts item : artefacts) {
            list.add(item.getName());
        }
        return list;
    }

    public ArrayList<String> getFurNameList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntFurniture item : furniture) {
            list.add(item.getName());
        }
        return list;
    }

    public ArrayList<String> getCharDescribingList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntCharacters item : characters) {
            list.add(item.getDescription());
        }
        return list;
    }

    public ArrayList<String> getArtDescribingList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntArtefacts item : artefacts) {
            list.add(item.getDescription());
        }
        return list;
    }

    public ArrayList<String> getFurDescribingList() {
        ArrayList<String> list = new ArrayList<>();
        for(EntFurniture item : furniture) {
            list.add(item.getDescription());
        }
        return list;
    }

    public ArrayList<String> getPlayerDescribingList(String himself) {
        ArrayList<String> list = new ArrayList<>();
        for(EntPlayer item : players) {
            if(!item.getName().equals(himself)) {
                list.add(item.getDescription());
            }
        }
        return list;
    }

    public EntPlayer getPlayerByName(String playerName) {
        for(EntPlayer player : players) {
            if(player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public EntArtefacts getArtByName(String name) {
        for(EntArtefacts artefact : artefacts) {
            if(compareNames(artefact.getName(),name)) {
//            if(artefact.getName().equals(name)){
                return artefact;
            }
        }
        return null;
    }

    public EntCharacters getCharByName(String name) {
        for(EntCharacters character : characters) {
            if(compareNames(character.getName(),name)) {
//            if(character.getName().equals(name)){
                return character;
            }
        }
        return null;
    }

    public EntFurniture getFurByName(String name) {
        for(EntFurniture fur : furniture) {
            if(compareNames(fur.getName(),name)) {
//                if(fur.getName().equals(name)){
                return fur;
            }
        }
        return null;
    }

    public ArrayList<EntCharacters> getCharacters() {
        return characters;
    }

    public ArrayList<EntArtefacts> getArtefacts() {
        return artefacts;
    }

    public ArrayList<EntFurniture> getFurniture() {
        return furniture;
    }

    public ArrayList<EntPlayer> getPlayers() {
        return players;
    }

    public HashSet<String> getNextLocations() {
        return nextLocations;
    }

    public Boolean getStartingPt() {
        return startingPt;
    }
}
