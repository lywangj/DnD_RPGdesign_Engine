package com.company;

import java.util.ArrayList;

public class GameAction
{
    private final ArrayList<String> keywords;
    private final ArrayList<String> subjects;
    private final ArrayList<String> consumptions;
    private final ArrayList<String> productions;
    private String narration;

    public GameAction() {
        keywords = new ArrayList<>();
        subjects = new ArrayList<>();
        consumptions = new ArrayList<>();
        productions = new ArrayList<>();
    }

    public void addAKeyword(String keyword) {
        keywords.add(keyword);
    }

    public void addASubject(String subject) {
        subjects.add(subject);
    }

    public void addAConsumption(String consumption) {
        consumptions.add(consumption);
    }

    public void addAProduction(String production) {
        productions.add(production);
    }

    public void addNarration(String string) {
        narration = string;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public ArrayList<String> getConsumptions() {
        return consumptions;
    }

    public ArrayList<String> getProductions() {
        return productions;
    }

    public String getNarration() {
        return narration;
    }

}
