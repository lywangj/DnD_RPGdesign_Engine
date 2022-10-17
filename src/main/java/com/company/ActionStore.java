package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.TreeMap;

public class ActionStore {

    private final TreeMap<String, HashSet<GameAction>> actionList;
    private GameAction concept;

    public ActionStore(File file){
        actionList = new TreeMap<>();
        storeActions(file);
    }

    private void storeActions(File file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            NodeList actions = root.getChildNodes();
            // Get the first action (only the odd items are actually actions - 1, 3, 5 etc.)
            // go through all actions
            storeEachAction(actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeEachAction(NodeList actions) {
        for(int i=1; i<actions.getLength(); i=i+2) {
            Element currAction = (Element)actions.item(i);
            concept = new GameAction();
            ArrayList<String> keywords = storeKeywords(currAction);
            storeItems(currAction,"subjects");
            storeItems(currAction,"consumed");
            storeItems(currAction,"produced");
            storeNarration(currAction);

            // store in TreeMap
            for(String kword : keywords) {
                if(actionList.containsKey(kword)) {
                    actionList.get(kword).add(concept);
                } else {
                    HashSet<GameAction> sets = new HashSet<>();
                    sets.add(concept);
                    actionList.put(kword,sets);
                }
            }
        }
    }

    private ArrayList<String> storeKeywords(Element currAction) {
        ArrayList<String> keywords = new ArrayList<>();
        NodeList a1 = currAction.getElementsByTagName("triggers");
        Element triggers = (Element) a1.item(0);
        NodeList b1 = triggers.getElementsByTagName("keyword");
        for(int j=0; j<b1.getLength(); ++j) {
            String keyword = b1.item(j).getTextContent().toLowerCase(Locale.ROOT);
            keywords.add(keyword);
            concept.addAKeyword(keyword);
        }
        return keywords;
    }

    private void storeItems(Element currAction, String category) {
        NodeList a4 = currAction.getElementsByTagName(category);
        Element produced = (Element) a4.item(0);
        NodeList b4 = produced.getElementsByTagName("entity");
        for(int j=0; j<b4.getLength(); ++j) {
            String sub = b4.item(j).getTextContent().toLowerCase(Locale.ROOT);
            switch (category) {
                case "subjects" -> concept.addASubject(sub);
                case "produced" -> concept.addAProduction(sub);
                case "consumed" -> concept.addAConsumption(sub);
            }
        }
    }

    private void storeNarration(Element currAction) {
        Element narration = (Element)currAction.getElementsByTagName("narration").item(0);
        String line = narration.getTextContent();
        concept.addNarration(line);
    }

    public TreeMap<String, HashSet<GameAction>> getActions() {
        return actionList;
    }

    public GameAction trigger(String keyword, ArrayList<String> require) {
        GameAction unitAction = null;
        HashSet<GameAction> sets = actionList.get(keyword.toLowerCase(Locale.ROOT));
        for (GameAction set : sets) {
            if(isContained(set.getSubjects(), require)) {
                unitAction = set;
            }
        }
        return unitAction;
    }

    public Boolean isContained(ArrayList<String> largerList,
                               ArrayList<String> smallerList) {
        for (String subject : largerList) {
            if (!smallerList.contains(subject)) {
                return false;
            }
        }
        return true;
    }


}
