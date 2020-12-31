package com.example.controller;

import com.example.entity.Graph;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class GraphController {

    @RequestMapping("/graph-connected")
    public void getConnection(@RequestParam String origin, @RequestParam String destination) {

        //Read cities from file and construct a mapping
        Map<Integer, String> searchMap = new HashMap();
        String root = origin;
        Integer start = 0;

        try {
            Resource resource = new ClassPathResource("city.txt");
            File file = resource.getFile();
            Scanner scanner = new Scanner(new FileReader(file));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] strs = line.split(",");
                if(!searchMap.containsValue(strs[0])){
                    searchMap.put(start, strs[0]);
                    start++;
                }
                if(!searchMap.containsValue(strs[1])){
                    searchMap.put(start, strs[1]);
                    start++;
                }

                /*map.putIfAbsent(strs[0], new HashSet<>());
                map.get(strs[0]).add(strs[1]);
                searchMap.putIfAbsent(strs[1], new HashSet<>());
                searchMap.get(strs[1]).add(strs[0]);*/
            }
            scanner.close();
        } catch (IOException e) { }



        Graph g;
        g = new Graph();
        g.addVertex("Boston");
        g.addVertex("New York");
        g.addVertex("Philadelphia");
        g.addVertex("Newark");
        g.addVertex("Trenton");
        g.addVertex("Albany");

        g.addEdge("Boston", "New York");
        g.addEdge("Philadelphia", "Newark");
        g.addEdge("Newark", "Boston");
        g.addEdge("Trenton", "Albany");
        g.addEdge("New York", "Boston");
        g.addEdge("Newark", "Philadelphia");
        g.addEdge("Boston", "Newark");
        g.addEdge("Albany", "Trenton");

        System.out.println(
                "Following is Depth First Traversal "
                        + "(starting from vertex 2)");

        System.out.println(g.breadthFirstTraversal(g, "Boston").toString());
    }

}
