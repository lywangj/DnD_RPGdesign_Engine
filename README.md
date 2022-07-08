## DnD_RPGdesign

Coursework files for Object Oriented Programming Unit undertaken at University of Bristol. Grage Achieved - 80% (2:1)

The aim was to build up a game engine for a text adventure game, which also allows game designer to construct their own game.

### Program Design

- object oriented programming


### User interface

- 


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
