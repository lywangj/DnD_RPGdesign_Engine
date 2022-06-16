package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class BasicCommandTests {

  private GameServer server;

  // Make a new server for every @Test (i.e. this method runs before every @Test test case)
  @BeforeEach
  void setup() {
    File entitiesFile = Paths.get("config/basic-entities.dot").toAbsolutePath().toFile();
    File actionsFile = Paths.get("config/basic-actions.xml").toAbsolutePath().toFile();
    server = new GameServer(entitiesFile, actionsFile);
  }

  // Test to spawn a new server and send a simple "look" command
  @Test
  void testLookingAroundStartLocation() {
    String response = server.handleCommand("player 1: look").toLowerCase();
    assertTrue(response.contains("empty room"), "Did not see description of room in response to look");
    assertTrue(response.contains("magic potion"), "Did not see description of artifacts in response to look");
    assertTrue(response.contains("wooden trapdoor"), "Did not see description of furniture in response to look");
    assertTrue(response.contains("forest"), "Did not see description of next location in response to look");
  }

  @Test
  void testGotoNextLocation() {
    String response = server.handleCommand("player 1: goto forest").toLowerCase();
    assertTrue(response.contains("dark forest"), "Did not see description of room in response to goto");
    assertTrue(response.contains("cabin"), "Did not see description of next location in response to goto");
    assertTrue(response.contains("brass key"), "Did not see description of artifacts in response to goto");
    response = server.handleCommand("player 1: goto cabin").toLowerCase();
    assertTrue(response.contains("empty room"), "Did not see description of room in response to goto");
  }

  @Test
  void testGetOrDropItem() {
    String response = server.handleCommand("player 1: get potion").toLowerCase();
    assertTrue(response.contains("potion"), "Did not see description of item that is taken");
    response = server.handleCommand("player 1: drop potion").toLowerCase();
    assertTrue(response.contains("potion"), "Did not see description of item that is dropped");
  }

  @Test
  void testGetOrDropItemAndLookAround() {
    server.handleCommand("player 1: get potion");
    String response = server.handleCommand("player 1: look").toLowerCase();
    assertFalse(response.contains("potion"), "Did not see description of artifacts in response to look");
    server.handleCommand("player 1: drop potion");
    response = server.handleCommand("player 1: look").toLowerCase();
    assertTrue(response.contains("potion"), "Did not see description of artifacts in response to look");
  }

  @Test
  void testGetOrDropAndCheckInventory() {
    String response = server.handleCommand("player 1: inv").toLowerCase();
    assertTrue(response.contains("nothing"), "Did not see description of player's inventory");
    server.handleCommand("player 1: get potion");
    response = server.handleCommand("player 1: inventory").toLowerCase();
    assertTrue(response.contains("potion"), "Did not see description of player's inventory");
    server.handleCommand("player 1: drop potion");
    response = server.handleCommand("player 1: inVenTory").toLowerCase();
    assertFalse(response.contains("potion"), "Did not see description of player's inventory");
  }

  @Test
  void testGetAndGotoAndDropItem() {
    server.handleCommand("player 1: get potion");
    server.handleCommand("player 1: goto forest");
    String response = server.handleCommand("player 1: look").toLowerCase();
    assertFalse(response.contains("potion"), "Did not see description of item that is dropped");
    server.handleCommand("player 1: drop potion");
    response = server.handleCommand("player 1: look").toLowerCase();
    assertTrue(response.contains("potion"), "Did not see description of artifacts in response to look");
  }


  @Test
  void testGetOrDropItems() {
    String response = server.handleCommand("player 1: get potions").toLowerCase();
    assertTrue(response.contains("potion"), "Did not find the singular form");
    response = server.handleCommand("player 1: drop potions").toLowerCase();
    assertTrue(response.contains("potion"), "Did not find the singular form");
  }

  @Test
  void testGetOrDropItems2() {
    server.handleCommand("player 1: goto forest");
    String response = server.handleCommand("player 1: get keys").toLowerCase();
    assertTrue(response.contains("key"), "Did not find the singular form");
    response = server.handleCommand("player 1: drop keys").toLowerCase();
    assertTrue(response.contains("key"), "Did not find the singular form");
  }

}