package edu.isu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolveClique {
	private Graph[] arrayGraph = new Graph[51];
	private Graph graph;
	private int arrayCount = 0;
	private int graphCount = 0;
	private ArrayList<ArrayList<Integer>> cliques;
	private long time;


	//Function to read the text file and build the graph, will also call the findClique for each member of arrayGraph
	public void readGraph() {
		String filename;
		int rowCounter = -1;
		int colCounter = 0;
		System.out.println("Please enter the name of the text file for the graph to be read: ");
		Scanner input = new Scanner(System.in);
		filename = input.nextLine();
		try {
			input = new Scanner(new File(filename));
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] lineArray = line.split("[^\\d]+");
				colCounter = 0;
				rowCounter++;
				if(line.equals("0")) {
					arrayGraph[arrayCount++] = graph;
					break;
				}
				for(int i = 0; i < lineArray.length; i++) {
					int nextInt;
					try {
						nextInt = Integer.parseInt(lineArray[i]);
						if(nextInt == 1) {
							graph.add(rowCounter, colCounter, nextInt);
							graph.updateEdge(rowCounter, colCounter, nextInt);
						}
						else if (nextInt == 0) {
							graph.add(rowCounter, colCounter, nextInt);
						}
						else {
							arrayGraph[arrayCount++] = graph;
							graph = new Graph(nextInt);
							rowCounter = -1;
							colCounter = 0;
						}
						colCounter++;
					}catch(NumberFormatException e) {	
						
					}catch(StringIndexOutOfBoundsException e) {
						
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File was not found");
			e.printStackTrace();
		}
		
		//Calls the findClique function on every graph in the arrayGraph array
		for(int g = 1; g<arrayCount; g++) {
			graph = arrayGraph[g];
			ArrayList<Integer> tempClique = new ArrayList<Integer>();
			time = 0;
			tempClique = getMaxClique(graph);
			print(tempClique, graph);
		} 
		
		
	}
	

	
	
	//Helper function to find the max clique using the Bron Kerbosch algorithm
	public ArrayList<Integer> getMaxClique(Graph g){
		cliques = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> potentialCliques = new ArrayList<Integer>();
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		ArrayList<Integer> alreadyFound = new ArrayList<Integer>();
		for(int i =0; i < g.getSize(); i++) {
			candidates.add(i);
		}
		rkb(potentialCliques, candidates, alreadyFound, g);
		int max = 0;
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		for(ArrayList<Integer> clique : cliques) {
			if(max < clique.size()) {
				max = clique.size();
			} 
		}
		for(ArrayList<Integer> clique: cliques) {
			if(max == clique.size()) {
				maxClique.addAll(clique);
				break;
			}
		}
		return maxClique;
	}
	
	
	//Bron Kerbosch algorithm for finding maximum cliques 
	public void rkb(ArrayList<Integer> R, ArrayList<Integer> P, ArrayList<Integer> X, Graph g) {
		long start = System.currentTimeMillis();
		int vertex;
		if(P.isEmpty() && X.isEmpty()) {
			cliques.add(R);
			return;
		}
		while(!P.isEmpty()) {
			vertex = P.get(0);
			ArrayList<Integer> P2 = new ArrayList<Integer>();
			for(int i =1; i < P.size(); i++) {
				if(g.get(vertex, P.get(i))==1 && vertex != P.get(i)) {
					P2.add(P.get(i));
				}
			}
			ArrayList<Integer> R2 = new ArrayList<Integer>(R);
			ArrayList<Integer> X2 = new ArrayList<Integer>(X);
			rkb(union(R2, vertex), intersection(P, P2), intersection(X2, P2), g);
			union(X, vertex);
			P.remove(0);	
		}
		long end = System.currentTimeMillis();
		time = end-start;
		
	}
	

	
	
	//Function to find and return a union between a list and a new integer, ensures that no duplicates are added to the list
	public ArrayList<Integer> union(ArrayList<Integer> l1, int l2){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(l2);
		temp.addAll(l1);
		return new ArrayList<Integer>(temp);
		
	}
	
	
	//Function to find and return the intersection between two lists
	public ArrayList<Integer> intersection(ArrayList<Integer> l1, ArrayList<Integer> l2){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int vertex: l1) {
			if(l2.contains(vertex)) {
				temp.add(vertex);
			}
		}
		return temp;
	}



	//Function that takes a copy of the graph and the clique and outputs the solution
    private void print(ArrayList<Integer> clique, Graph g) {
        int size = clique.size();

        System.out.println("G" + graphCount++ + " (" + g.getSize() + ", " + g.getTotalEdges() + ") " + clique.toString() + "(size=" + size + 
        		" , " + "time= "+time + ")");
    }


}