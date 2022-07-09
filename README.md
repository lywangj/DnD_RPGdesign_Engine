<h2 id="top"> DnD_RPGdesign </h2>

Coursework files for Object Oriented Programming Unit undertaken at University of Bristol. Grage Achieved - 80% (2:1)

The aim was to build up a game engine for a text adventure game, which also allows game designer to construct their own game.

<p></p>

<a class="outlines" href="#program-design">______ Program Design</a>

<p></p>


#
<h3 id="program-design"> Program Design </h3>

- object oriented programming

<img src="images/2022-06-26_002032.png" height="130">

<a class="return" href="#top"> 《TOP》 </a>

### Player interface

Players are able to interact with some entities.
- look: describes all the items and characters in their current place
- get/drop {item}: pick up / put down an item 
- goto: moves the player to the next location
- inventory: lists all the items currently carried by the player

```
Mike:> look
You are in A log cabin in the woods. You can see:
A bottle of magic potion
A razor sharp axe
A silver coin
A locked wooden trapdoor in the floor
You can access from here:
forest

Mike:> get axe
You picked up a axe

Mike:> goto forest
You are in A deep dark forest. You can see:
A rusty old key
A tall pine tree
You can access from here:
cabin
riverbank

```

### Game Design


#### Entities

```
        subgraph cluster002 {
            node [shape = "none"];
            forest [description = "A dark forest"];
            subgraph artefacts {
                node [shape = "diamond"];
                key [description = "Brass key"];
            }
        }
```


#### Ations

```
-<action>
    -<triggers>
        <keyword>chop</keyword>
        <keyword>cut</keyword>
        <keyword>cutdown</keyword>
    </triggers>
    -<subjects>
        <entity>tree</entity>
        <entity>axe</entity>
    </subjects>
    -<consumed>
        <entity>tree</entity>
    </consumed>
    -<produced>
        <entity>log</entity>
    </produced>
    <narration>You cut down the tree with the axe</narration>
</action>
```
