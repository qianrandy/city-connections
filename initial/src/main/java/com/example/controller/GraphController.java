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

    private Graph g;

    {
        g = new Graph();
    }

    @RequestMapping("/graph-connected")
    public String getConnection(@RequestParam String origin, @RequestParam String destination) {

        String root = origin;
        Integer start = 0;

        try {
            Resource resource = new ClassPathResource("city.txt");
            File file = resource.getFile();
            Scanner scanner = new Scanner(new FileReader(file));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cities = line.split(",");
                String city0 = cities[0].trim();
                String city1 = cities[1].trim();
                g.addVertex(city0);
                g.addVertex(city1);
                g.addEdge(city0, city1);
                g.addEdge(city1, city0);
            }
            scanner.close();
        } catch (IOException e) { }


        Set<String> connected = g.breadthFirstTraversal(g, root);
        if(connected.contains(destination))
            return "yes";

        return "no";
    }

}
