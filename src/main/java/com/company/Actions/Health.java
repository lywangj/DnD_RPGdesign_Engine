package com.company.Actions;

import com.company.EntPlayer;

public class Health extends Action {


    public Health(EntPlayer inputPlayer) {
        player = inputPlayer;
        implement();
    }

    @Override
    public void implement() {
        output = "You have " +
                player.getHealthLevel().toString() + " health";
    }

}
