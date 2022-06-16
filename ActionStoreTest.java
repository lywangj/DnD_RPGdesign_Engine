package com.company;

import com.alexmerz.graphviz.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ActionStoreTest {

    File path;
    ActionStore actionStore;
    TreeMap<String, HashSet<GameAction>> actions;

    @Test
    void test() {
        path = new File("config" + File.separator + "basic-actions.xml");
//        path = new File("config" + File.separator + "extended-actions.xml");
        actionStore = new ActionStore(path);
        actions = actionStore.getActions();
        ArrayList<String> requests = new ArrayList<>(List.of("trapdoor","key"));
        assertEquals(actionStore.trigger("open",requests).getNarration(),"You unlock the trapdoor and see steps leading down into a cellar");
        requests = new ArrayList<>(List.of("elf"));
        assertEquals(actionStore.trigger("fight",requests).getNarration(),"You attack the elf, but he fights back and you lose some health");
        assertEquals(actionStore.trigger("attack",requests).getNarration(),"You attack the elf, but he fights back and you lose some health");
        requests = new ArrayList<>(List.of("potion"));
        assertEquals(actionStore.trigger("drink",requests).getSubjects().get(0),"potion");
        assertEquals(actionStore.trigger("drink",requests).getConsumptions().get(0),"potion");
        assertEquals(actionStore.trigger("drink",requests).getProductions().get(0),"health");

    }

}