package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GameEntity
{
    String name;
    String description;
    Type type;

    enum Type {
        artefacts,
        furniture,
        characters,
        players
    }

    public GameEntity(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public Boolean compareNames(String name1,String name2) {
        String test = name1+"[s]?$";
        Pattern pattern = Pattern.compile(test);
        Matcher matcher = pattern.matcher(name2);
        return matcher.find();
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getTypeName() {
        return type.toString();
    }

}
