package edu.isu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolveISet {
	
	private static SolveClique solve=new SolveClique();
	private Graph[] arrayGraph = new Graph[1000];
	private Graph graph;
	private int arrayCount = 0;
	private int graphCount=0;
	
	//read graph from input fileName
	public void readGraph() {
		String filename;
		int rowCounter = -1;
		int colCounter = 0;
		System.out.println("Please enter the name of the text file for the graph to be read: ");
		Scanner input = new Scanner(System.in);
		filename = input.nextLine();
		System.out.println("* Max Independent Sets in graphs in" + filename +": (reduced to K-Clique) *");
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

			System.out.println("File was not found");
			e.printStackTrace();
		}
		//changes all graphs to be the compliment
		findCompliment();
		
		
		//Calls the findClique function on every graph in the arrayGraph array
		for(int g = 1; g<arrayCount; g++) {
			graph = arrayGraph[g];
			ArrayList<Integer> tempClique = new ArrayList<Integer>();
			tempClique = solve.findClique(tempClique, 0, graph.getSize(), graph);
			print(tempClique, graph);
		}
		
	}
	
	private void findCompliment() {
		
		//for every graph in arrayGraph
		for(int i=1; i<arrayCount; i++) {
			graph = arrayGraph[i];
			//for every row in current graph
			for(int j = 0; j < graph.getSize(); j++) {
				//for every column
				for(int k = 0; k<graph.getSize();k++) {
					//switch value at every cord in adj matrix except diagonal
					if(j != k) {
						if(graph.get(j, k) == 1) {
							arrayGraph[i].add(j, k, 0);
						}
						else {
							arrayGraph[i].add(j, k, 1);
						}
					}
				}
			}
		}
	}
	
	//prints the independent sets
    private void print(ArrayList<Integer> clique, Graph g) {
        int size = clique.size();

        System.out.println("G" + ++graphCount + " (" + g.getSize() + ", " + g.getTotalEdges() + ") " + clique.toString() + "(size=" + size + ")");
    }

}
