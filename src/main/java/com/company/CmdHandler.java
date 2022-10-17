package com.company;

import com.company.Actions.*;
import com.company.Exceptions.CmdException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CmdHandler {

    private final TreeMap<String, HashSet<GameAction>> actionList;
    private final HashMap<String,EntLocation> entityList;
    private final HashMap<String,String> playerList;

    private final String originalString;
    private String playerName;
    private EntPlayer player;
    private String command;
    private EntLocation currLocation;
    private String action;
    private ArrayList<String> cmdWords;
    private ArrayList<String> subjects;
    private ArrayList<EntArtefacts> cmdArtefacts;
    private HashSet<GameAction> actions;
    private String output;

    public CmdHandler(String incomingCmd,
                      HashMap<String,EntLocation> entityDB,
                      TreeMap<String, HashSet<GameAction>> actionDB,
                      HashMap<String,String> players) {
        actionList = actionDB;
        entityList = entityDB;
        playerList = players;
        originalString = incomingCmd;
        try{
            handleCmd();
            checkPlayerStatus();
            parseCmd();
            executeAction();
            confirmPlayerStatus();
            output+="\n";
        } catch (Exception e){
            output = e +"\n";
        }
    }

    public void confirmPlayerStatus() {
        if(player.getHealthLevel()==0){
            // reset player health
            player.setHealthLevel(3);

            // drop all carried item in current location
            ArrayList<EntArtefacts> items = new ArrayList<>(player.getInventory());
            currLocation.getArtefacts().addAll(items);
            player.getInventory().removeAll(items);

            // transport player to the start location
            String startLocation = getStartingLocation().getName();
            if(!startLocation.equals(currLocation.getName())){
                EntPlayer resetPlayer = player;
                currLocation.getPlayers().remove(resetPlayer);
                entityList.get(startLocation).addAnItem(resetPlayer);
                playerList.replace(playerName,startLocation);
            }
            // update output
            output+="\nyou died and lost all of your items, " +
                    "you must return to the start of the game";
        }
    }

    public void executeAction() {
        String locationName = currLocation.getName();
        switch(action){
            case "inventory","inv" -> {
                Inventory cmd = new Inventory(player);
                output = cmd.getOutput();
            }
            case "get","drop" -> {
                Action cmd = new GetOrDrop(action,currLocation,
                        cmdArtefacts.get(0),playerName);
                output = cmd.getOutput();
            }
            case "goto" -> {
                Goto cmd = new Goto(entityList,locationName,subjects.get(0),playerName);
                output = cmd.getOutput();
                playerList.replace(playerName,subjects.get(0));
            }
            case "look" -> {
                Look cmd = new Look(currLocation,playerName);
                output = cmd.getOutput();
            }
            case "health" -> {
                Health cmd = new Health(player);
                output = cmd.getOutput();
            }
            default -> executeGameAction(locationName);
        }
    }

    private void executeGameAction(String locationName) {
        ArrayList<GameAction> executedAct = new ArrayList<>(actions);
        String ck1,ck2;
        Action cmd1 = new Consume(entityList,locationName,playerName,executedAct.get(0));
        ck1 = cmd1.getOutput();
        Action cmd2 = new Produce(entityList,locationName,playerName,executedAct.get(0));
        ck2 = cmd2.getOutput();
        if(ck1!=null) {
            output = ck1;
        }else if(ck2!=null) {
            output =ck2;
            Action reverse = new Reverse(entityList,locationName,playerName,executedAct.get(0));
        }else {
            output = executedAct.get(0).getNarration();
        }
    }

    public void handleCmd() {
        ArrayList<String> cmd =
                new ArrayList<>(Arrays.asList(originalString.split(":")));
        playerName = cmd.get(0);
        command = cmd.get(1).toLowerCase(Locale.ROOT);
    }

    public void checkPlayerStatus() {
        // check the player is existed in entityDB
        // check the player's current location
        if(playerList.containsKey(playerName)) {
            currLocation = entityList.get(playerList.get(playerName));
            player = currLocation.getPlayerByName(playerName);
        } else {
            // put the new player in start location
            currLocation = getStartingLocation();
            playerList.put(playerName, currLocation.getName());
            player = new EntPlayer(playerName, "A player named "+playerName);
            currLocation.addAnItem(player);
        }
    }

    public EntLocation getStartingLocation() {
        for (EntLocation location : entityList.values()) {
            if(location.getStartingPt()) {
                return location;
            }
        }
        return null;
    }

    private void transferToCmdList() {
        ArrayList<String> words = new ArrayList<>(Arrays.asList(command.split(" ")));
        cmdWords = new ArrayList<>();
        for(String word : words) {
            if(!word.equals("")){
                cmdWords.add(word);
            }
        }
    }

    public void parseCmd() throws CmdException {
        transferToCmdList();
        // find built-in actions
        findBuiltinActions();
        findGameActions();
        if(action==null){
            if(actions.size()>0) {
                checkForGameActs();
                action = "other";
            }else {
                throw new CmdException.invalidImplement();
            }
        }else {
            if(actions.size()>0){
                throw new CmdException.tooManyActions();
            }
        }
    }

    public void findGameActions() {
        actions = new HashSet<>();
        String checkCmd = " "+command+" ";
        for (String kword : actionList.keySet()) {
            String checkWord = " "+kword+" ";
            if(checkCmd.contains(checkWord)) {
                for(GameAction act : actionList.get(kword)) {
                    checkEachAction(act, cmdWords);
                }
            }
        }
    }

    public void checkEachAction(GameAction act,
                                ArrayList<String> otherWords) {
        ArrayList<String> tmpSubs =
                getContainedSubs(act.getSubjects(),otherWords);
        int matchingCnt = tmpSubs.size();
        if(matchingCnt>=act.getSubjects().size()) {
            checkExcusable(act);
        } else if(matchingCnt>=1) {
            ArrayList<String> tmpList = getAllSubjectsList();
            tmpSubs = getContainedSubs(act.getSubjects(),tmpList);
            matchingCnt = tmpSubs.size();
            if(matchingCnt>=act.getSubjects().size()) {
                checkExcusable(act);
            }
        }
    }

    private void checkExcusable(GameAction act) {
        if(enableToExecuteAction(act.getSubjects())){
            actions.add(act);
        }
    }

    public Boolean enableToExecuteAction(ArrayList<String> subjects) {
        for (String itemName : subjects) {
            if(currLocation.findAnItemByName(itemName)==null) {
                if (player.getInvByName(itemName) == null) {
                    if(!currLocation.getName().equals(itemName)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void checkForGameActs() throws CmdException {
        if(actions.size()==0){
            throw new CmdException.invalidImplement();
        } else if(actions.size()>1){
            throw new CmdException.tooManyActions();
        }
    }

    public void findBuiltinActions() throws CmdException {
        ArrayList<String> builtinActions = new ArrayList<>(
                Arrays.asList("inventory","inv","get","drop","goto","look","health"));
        ArrayList<String> words = cmdWords;
        for(String word : words) {
            if(builtinActions.contains(word)){
                if(action==null) {
                    action = word;
                }else {
                    throw new CmdException.tooManyItemsToGet();
                }
            }
        }
        if(action!=null) {
            checkForBuiltinActs();
        }
    }

    public void checkForBuiltinActs() throws CmdException {
        switch (action){
            case "inventory", "inv" -> checkOnlyInventory();
            case "look" -> checkOnlyLook();
            case "get" -> checkArtInEnvironment();
            case "drop" -> checkArtInInv();
            case "goto" -> checkNextLocation();
        }
    }

    public void checkOnlyInventory() throws CmdException {
        if(cmdWords.size()!=1) {
            throw new CmdException.notOnlyInventory();
        }
    }

    public void checkOnlyLook() throws CmdException {
        if(cmdWords.size()!=1) {
            throw new CmdException.notOnlyLook();
        }
    }

    public void checkNextLocation() throws CmdException {
        subjects = new ArrayList<>();
        int locationCount = 0;
        for(String word : cmdWords) {
            if(entityList.containsKey(word)) {
                locationCount++;
                if(currLocation.getNextLocations().contains(word)) {
                    subjects.add(word);
                }
            }
        }

        if(locationCount>1){
            throw new CmdException.tooManyDestination();
        }
        if(subjects.isEmpty()) {
            throw new CmdException.invalidDestination();
        }
    }

    public void checkArtInEnvironment() throws CmdException {
        cmdArtefacts = new ArrayList<>();
        for(String word : cmdWords) {
            EntArtefacts item = currLocation.getArtByName(word);
            if(item!=null) {
                cmdArtefacts.add(item);
            }
        }
        if(cmdArtefacts.isEmpty()) {
            throw new CmdException.absentItemInEnvironment();
        } else if(cmdArtefacts.size()!=1) {
            throw new CmdException.tooManyItemsToGet();
        }
    }

    public void checkArtInInv() throws CmdException {
        cmdArtefacts = new ArrayList<>();
        for(String word : cmdWords) {
            if(player.checkInInvByName(word)){
                cmdArtefacts.add(player.getInvByName(word));
                break;
            }
        }
        if(cmdArtefacts.isEmpty()) {
            throw new CmdException.absentItemInInv();
        }
    }

    public ArrayList<String> getAllSubjectsList() {
        HashSet<String> tmpSet = new HashSet<>();
        tmpSet.add(currLocation.getName());
        tmpSet.addAll(currLocation.getCharNameList());
        tmpSet.addAll(currLocation.getArtNameList());
        tmpSet.addAll(currLocation.getFurNameList());
        tmpSet.addAll(player.getInvNameList());
        return new ArrayList<>(tmpSet);
    }


    public ArrayList<String> getContainedSubs(ArrayList<String> smallerList,
                                              ArrayList<String> largerList) {
        ArrayList<String> result = new ArrayList<>();
        if(largerList.size()==0) {
            return result;
        }
        for (String tgtSub : smallerList) {
            for (String inputSub : largerList) {
                if(compareNames(tgtSub,inputSub)) {
                    result.add(tgtSub);
                }
            }
        }
        return result;
    }

    public Boolean compareNames(String name1,String name2) {
        String test = name1+"[s]?$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name2);
        return matcher.find();
    }

    public String getOutput() {
        return output;
    }

}
