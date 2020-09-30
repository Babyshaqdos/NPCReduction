package edu.isu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolveClique {
	private Graph[] arrayGraph = new Graph[1000];
	private Graph graph;
	private int arrayCount = 0;
	private int graphCount = 0;


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
			tempClique = findClique(tempClique, 0, graph.getSize(), graph);
			print(tempClique, graph);
		}
		
	}
	
	

	//Function that takes an arraylist as the clique, the starting row position, the length of the clique and a graph to find the maximum clique
	public ArrayList<Integer> findClique(ArrayList<Integer> clique, int row, int length, Graph g){
		//2 new ArrayLists, one to hold the max clique and another to handle recursive calls
		ArrayList<Integer> newClique = new ArrayList<Integer>();
		ArrayList<Integer> maxClique = new ArrayList<Integer>();
		maxClique = clique;
		for(int i = row; i < g.getSize(); i++) {
			boolean flag = true;
			for(int j = 0; j< clique.size(); j++) {
				if(g.get(j, i) != 1) {
					flag = false;
				}
			}
			if(flag) {
				//Create another new arraylist to hold the passed in clique list to in turn be passed back recursively after adding a node
				ArrayList<Integer> tempClique = new ArrayList<Integer>();
				tempClique = clique;
				if(!tempClique.contains(i)) {
					tempClique.add(i);
				}
				newClique = findClique(tempClique, i + 1, length, g);
				if(newClique.size() > maxClique.size()) {
					maxClique = newClique;
				}
			}
		}
		return maxClique;
	}



	//Function that takes a copy of the graph and the clique and outputs the solution
    private void print(ArrayList<Integer> clique, Graph g) {
        int size = clique.size();

        System.out.println("G" + graphCount++ + " (" + g.getSize() + ", " + g.getTotalEdges() + ") " + clique.toString() + "(size=" + size + ")");
    }


}
