package com.company.Actions;

import com.company.EntLocation;

public class Look extends Action {

    public Look(EntLocation inputLocation,String name) {
        currLocation = inputLocation;
        playerName = name;
        implement();
    }

    @Override
    public void implement()  {
        printEnvironment();
    }

}
