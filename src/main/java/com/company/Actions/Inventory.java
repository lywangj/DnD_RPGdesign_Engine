package com.company.Actions;

import com.company.EntPlayer;

public class Inventory extends Action {

    public Inventory(EntPlayer inputPlayer) {
        player = inputPlayer;
        implement();
    }

    @Override
    public void implement() {
        String invList = generateList(player.getInvDescribeList());
        if(invList.length()==0){
            invList = "\nNothing";
        }
        output = "You are carrying:" + invList;
    }

}
