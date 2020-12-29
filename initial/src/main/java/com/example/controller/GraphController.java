package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.LinkedList;

@RestController
public class GraphController {
    class Graph {
        private int V; // No. of vertices

        // Array  of lists for
        // Adjacency List Representation
        private LinkedList<Integer> adj[];

        // Constructor
        @SuppressWarnings("unchecked")
        Graph(int v) {
            V = v;
            adj = new LinkedList[v];
            for (int i = 0; i < v; ++i)
                adj[i] = new LinkedList<Integer>();
        }

        // Function to add an edge into the graph
        void addEdge(int v, int w) {
            adj[v].add(w); // Add w to v's list.
        }

        // A function used by DFS
        void DFSUtil(int v, boolean visited[]) {
            // Mark the current node as visited and print it
            visited[v] = true;
            System.out.print(v + " ");

            // Recur for all the vertices adjacent to this
            // vertex
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n])
                    DFSUtil(n, visited);
            }
        }

        // The function to do DFS traversal.
        // It uses recursive
        // DFSUtil()
        void DFS(int v) {
            // Mark all the vertices as
            // not visited(set as
            // false by default in java)
            boolean visited[] = new boolean[V];

            // Call the recursive helper
            // function to print DFS
            // traversal
            DFSUtil(v, visited);
        }
    }

    @RequestMapping("/graph-connected")
    public void getConnection(@RequestParam String origin, @RequestParam String destination) {
        Graph g = new Graph(8);
        g.addEdge(4, 1);
        g.addEdge(6, 2);
        g.addEdge(5, 7);
        g.addEdge(3, 5);
        g.addEdge(7, 4);
        g.addEdge(1, 3);

        System.out.println(
                "Following is Depth First Traversal "
                        + "(starting from vertex 2)");

        g.DFS(1);
    }

}
