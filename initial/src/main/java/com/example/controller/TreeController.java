package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TreeController {
	
	static final String[] CSV = new String[] {
			"4,1,FOUR", "6,2,SIX", "1,,ONE", "2,1,TWO", "5,2,FIVE", "7,4,SEVEN", "3,1,THREE" };

	static class Tree {
		String val;
		List<Node> next;
		
		public Tree(String val) {
			this.val = val;
			this.next = new ArrayList<>();
		}
		
		public Tree() {
			this.next = new ArrayList<>();
		}
	}

	static class Node {
		String val;
		List<Node> next;

		public Node(String val) {
			this.val = val;
			this.next = new ArrayList<>();
		}

		@Override
		public String toString() {
			return val + "";
		}	
	}
	
	@RequestMapping("/tree-connected")
	public String getConnection(@RequestParam String origin, @RequestParam String destination) {
		
		  Map<String, Set<String>> map = new HashMap<>(); 
		  Map<String, Set<String>> searchMap = new HashMap<>(); String root = "";
		  
		  try { 
			  Resource resource = new ClassPathResource("city.txt"); 
			  File file = resource.getFile(); 
			  Scanner scanner = new Scanner(new FileReader(file));
			  while (scanner.hasNextLine()) { 
				  String line = scanner.nextLine(); 
				  String[] strs = line.split(","); 
				  if(strs[0].equals(origin)) 
				  	root = strs[0];
				  map.putIfAbsent(strs[0], new HashSet<>()); 
				  map.get(strs[0]).add(strs[1]);
				  searchMap.putIfAbsent(strs[1], new HashSet<>());
				  searchMap.get(strs[1]).add(strs[0]); 
			  } 
			  scanner.close(); 
		  } catch (IOException e) { }
		 
		/*
		Map<Integer, Set<Integer>> map = new HashMap<>();
		Map<Integer, Set<Integer>> searchMap = new HashMap<>();
		int root = -1;
		for(String s : CSV) {
			String[] strs = s.split(",");
			int from = strs[1].equals("") ? -1 : Integer.parseInt(strs[1]), to = Integer.parseInt(strs[0]);
			if(from == -1) {
				root = to;
				continue;
			}
			map.putIfAbsent(from, new HashSet<>());
			map.get(from).add(to);
			searchMap.putIfAbsent(to, new HashSet<>());
			searchMap.get(to).add(from);
		}
		*/
		Tree tree = buildTree(map, "1", null);
		printTree(tree);
		System.out.println();
		printChildren(tree);
		System.out.println();
		List<String> path = new ArrayList<>();
		findPath(searchMap, path, 7+"");
		System.out.println(path);
		return origin + destination;
	}

	
	
	private static void findPath(Map<String, Set<String>> searchMap, List<String> path, String cur) {
		if(!searchMap.containsKey(cur)) {
			return;
		}
		for(String next : searchMap.get(cur)) {
			path.add(next);
			findPath(searchMap, path, next);
		}
	}

	private static void printChildren(Tree tree) {
		System.out.println(tree.val + " - " + tree.next);
		for(Node child : tree.next) {
			if(child.next.size() > 0)
				dfs(child, child.val);
		}
	}

	private static void dfs(Node child, String parent) {
		System.out.println(parent + " - " + child.next);
		for(Node nei : child.next) {
			if(nei.next.size() > 0) {
				dfs(nei, child.val);
			}
		}
	}

	private static void printTree(Tree node) {
		System.out.println(node.val);
		Queue<Node> q = new LinkedList<>();
		q.addAll(node.next);
		while(!q.isEmpty()) {
			int size = q.size();
			for(int i=0;i<size;i++) {
				Node cur = q.poll();
				System.out.print(cur.val + " ");
				if(cur.next.size() > 0) {
					q.addAll(cur.next);
				}
			}
			System.out.println();
		}
		
	}

	static Tree buildTree(Map<String, Set<String>> map, String cur, Node parent) {
		Tree res = new Tree(cur);
		for(String nei : map.getOrDefault(cur, new HashSet<>())) {
			Node curNode = new Node(nei);
			if(parent == null)
				res.next.add(curNode);
			else
				parent.next.add(curNode);
			buildTree(map, nei, curNode);
		}
		return res;
	}
	
}
