package com.company;

import com.alexmerz.graphviz.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

final class EntityStoreTest {

    File path;
    EntityStore entityStore;
    HashMap<String,EntLocation> locations;

    @Test
    void testStoreBasicEntities() {
        path = new File("config" + File.separator + "basic-entities.dot");
//        path = new File("config" + File.separator + "extended-entities.dot");
        entityStore = new EntityStore(path);
        locations = entityStore.getLocations();
        assertEquals(locations.get("cabin").getDescription(),"An empty room");
        assertTrue(locations.get("cabin").getStartingPt());
        assertTrue(locations.get("forest").getNextLocations().contains("cabin"));
        assertFalse(locations.get("cellar").getStartingPt());
        assertEquals(locations.get("storeroom").getDescription(),"Storage for any entities not placed in the game");
        assertTrue(locations.get("storeroom").getCharacters().isEmpty());
        assertEquals(locations.get("storeroom").getArtefacts().get(0).getName(),"log");
        assertEquals(locations.get("storeroom").getArtefacts().get(0).getDescription(),"A heavy wooden log");
        assertTrue(locations.get("storeroom").getFurniture().isEmpty());
        assertTrue(locations.get("storeroom").getNextLocations().isEmpty());
    }

    @Test
    void testStoreExtendedEntities() {
        path = new File("config" + File.separator + "extended-entities.dot");
        entityStore = new EntityStore(path);
        locations = entityStore.getLocations();
        assertEquals(locations.get("cabin").getArtefacts().get(0).getName(),"potion");
        assertEquals(locations.get("cabin").getArtefacts().get(1).getName(),"axe");
        assertEquals(locations.get("cabin").getArtefacts().get(2).getName(),"coin");
        assertEquals(locations.get("cabin").getArtefacts().get(2).getDescription(),"A silver coin");
        assertTrue(locations.get("cabin").getStartingPt());
        assertEquals(locations.get("storeroom").getFurniture().get(0).getName(),"hole");
        assertEquals(locations.get("storeroom").getFurniture().get(0).getDescription(),"A deep hole in the ground");
        assertEquals(locations.get("cellar").getCharacters().get(0).getName(),"elf");
        assertEquals(locations.get("cellar").getCharacters().get(0).getDescription(),"An angry looking Elf");
        assertTrue(locations.get("riverbank").getNextLocations().contains("forest"));
        assertFalse(locations.get("clearing").getStartingPt());
    }

}