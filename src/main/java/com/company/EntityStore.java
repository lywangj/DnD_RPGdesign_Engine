package com.company;

import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.Locale;

public class EntityStore {

    private final HashMap<String,EntLocation> locations;
    private  HashMap<String,ArrayList<String>> pathList;
    private EntLocation spot;

    public EntityStore (File file) {
        locations = new HashMap<>();
        storeEntities(file);
    }

    private void storeEntities(File file) {
        try{
            Parser parser = new Parser();
            FileReader reader = new FileReader(file);
            parser.parse(reader);
            Graph wholeDocument = parser.getGraphs().get(0);
            ArrayList<Graph> sections = wholeDocument.getSubgraphs();

            // The paths will always be in the second subgraph
            ArrayList<Edge> paths = sections.get(1).getEdges();
            pathList = new HashMap<>();
            storeEachPath(paths);

            // The locations will always be in the first subgraph
            ArrayList<Graph> areas = sections.get(0).getSubgraphs();
            storeEachArea(areas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeEachPath(ArrayList<Edge> paths) {
        for (Edge firstPath : paths) {
            Node fromLocation = firstPath.getSource().getNode();
            String fromName = fromLocation.getId().getId().toLowerCase(Locale.ROOT);
            Node toLocation = firstPath.getTarget().getNode();
            String toName = toLocation.getId().getId().toLowerCase(Locale.ROOT);
            if(pathList.containsKey(fromName)){
                pathList.get(fromName).add(toName);
            }else {
                ArrayList<String> toNames = new ArrayList<>();
                toNames.add(toName);
                pathList.put(fromName,toNames);
            }
        }
    }

    private void storeEachArea(ArrayList<Graph> areas) {
        boolean isStartingPt = true;
        // check number of area must be >= 1.
        for (Graph area : areas) {
            Node locationDetails = area.getNodes(false).get(0);
            String locationName = locationDetails.getId().getId().toLowerCase(Locale.ROOT);
            String locationDescription = locationDetails.getAttribute("description");

            // store locations
            spot = new EntLocation(locationName, locationDescription);
            // check pathList
            if(pathList.containsKey(locationName)){
                spot.getNextLocations().addAll(pathList.get(locationName));
            }

            // store subjects
            ArrayList<Graph> subjects = area.getSubgraphs();
            storeEachSubject(subjects);

            if(isStartingPt) {
                spot.setStartingPt();
            }
            locations.put(locationName, spot);
            isStartingPt = false;
        }
    }

    private void storeEachSubject(ArrayList<Graph> subjects) {
        for(Graph subject : subjects) {
            String dataType = subject.getId().getId().toLowerCase(Locale.ROOT);
            if(!subject.getNodes(false).isEmpty()){
                for (Node item : subject.getNodes(false)) {
                    storeItems(dataType, item, spot);
                }
            }
        }
    }


    private void storeItems(String dataType, Node item, EntLocation location) {
        String subjectName = item.getId().getId().toLowerCase(Locale.ROOT);
        String subjectDescription = item.getAttribute("description");
        GameEntity g ;
        switch(dataType){
            case "artefacts" -> g = new EntArtefacts(subjectName,subjectDescription);
            case "furniture" -> g = new EntFurniture(subjectName,subjectDescription);
            case "characters" -> g = new EntCharacters(subjectName,subjectDescription);
            default -> throw new IllegalStateException("Unexpected value: " + dataType);
        }
        location.addAnItem(g);
    }

    public HashMap<String,EntLocation> getLocations() {
        return locations;
    }

}
