package edu.isu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

public class Solve3CNF {

	private static SolveClique solve=new SolveClique();
	//arrayList of ints to hold number of variables in each phrase
	private ArrayList<Integer> variables = new ArrayList<Integer>();
	//arrayList to hold one CNF phrase
	private ArrayList<CNFNode> cnfPhrase; // = new ArrayList<CNFNode>();
	//arraylist of arraylist < cnf nodes> to hole CNF phrases
	private ArrayList<ArrayList<CNFNode>> cnfList = new ArrayList<ArrayList<CNFNode>>();
	ArrayList<Integer[][]> arr = new ArrayList<Integer[][]>();
	private int cnfCount = 0;
	private int graphCount=0;
	private int counter = 0;
	
	//reads 3CNF formatted file and populates cnfList with every 3CNF
	public void readCNF() {
		String filename;
		//num of variables in CNF
		int n;
		int currClause;
		
		
		//int rowCounter = -1;
		//int colCounter = 0;
		System.out.println("Please enter the name of the text file for the graph to be read: ");
		Scanner input = new Scanner(System.in);
		filename = input.nextLine();
		try {
			input = new Scanner(new File(filename));
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] lineArray = line.split(" ");
				//reset currClause every line
				currClause=1;
				CNFNode newNode = null;
				cnfPhrase=new ArrayList<CNFNode>();

				if(line.equals("0")) {

					break;
				}
				
				for(int i = 0; i < lineArray.length; i++) {
					int nextInt;
					try {
						nextInt = Integer.parseInt(lineArray[i]);
						
						//store first int in the line as n (number of variables in the CNF)
						if (i== 0) {
							n=nextInt;
							variables.add(n);
						}
						//create node
						else {
							newNode = new CNFNode(nextInt, currClause);
							//every 3 nodes update curr clause
							if(i%3==0) {
								currClause++;
							}
							//add each node to cnfPhrase
							
							cnfPhrase.add(newNode);
						}
						
					}catch(NumberFormatException e) {	
						
					}catch(StringIndexOutOfBoundsException e) {
						
					}

				}
				//add the CNF phrase to cnfList
				cnfList.add(cnfPhrase);
				//increment CNF Phrase count
				cnfCount++;

			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File was not found");
			e.printStackTrace();
		}
		//creates adjaceny matricies
		createAdjMatrix();
	}
	
	
	public static int[][] reduce(ArrayList<Integer> cnfTokens, int dimension) {
		int[][] graph = new int[dimension][dimension];
		for(int i = 0; i < dimension; i++) { //current index being compared to all other nodes
			int curr = cnfTokens.get(i); // value of current index
			for(int j = i; j < dimension; j++) { //index that current is being compared to
				int compare = cnfTokens.get(j); //value of that index
		
				if(i/3 != j/3) { //if they are not in the same group of 3
					if(curr != -compare) { //if it is not the negation, add an edge
						graph[i][j] = 1;
						graph[j][i] = 1;
					}
					else { //if they are negations, don't add an edge
						graph[i][j] = 0;
						graph[j][i] = 0;
					}
				} 
				// OKAY
				if(i == j) {
					graph[i][j] = 1;
				}
			}
		}
		return graph;
		// writeGraph(graph);
	}
	
	
	
	//creates an Adjacency Matrix for every 3CNF in cnfList
	private void createAdjMatrix() {
		int size;
		int temp;
		ArrayList<CNFNode> currPhrase = new ArrayList<CNFNode>();
		//loop through every 3CNF phrase
		for (int i=0;i<cnfCount;i++) {
			size = cnfList.get(i).size();
			currPhrase = cnfList.get(i);
			Graph graph = new Graph(size);
			
			//loop through every node in phrase
			for(int j = 0; j<size; j++) {
				//compare with every other node in phrase
				for(int k =j; k<size; k++) {
					//if looking at self add 1 to graph
					if(j==k) {
						graph.add(j, k, 1);
					}
					//if nodes have the same value and are from different clauses add 1 in graph
					else if(currPhrase.get(j).getValue()==currPhrase.get(k).getValue() && currPhrase.get(j).getClause()!=currPhrase.get(k).getClause()) {
						graph.add(j, k, 1);
					}
					//if nodes have different variables and are from different clauses and 1 in graph
					else if(Math.abs(currPhrase.get(j).getValue())!=Math.abs(currPhrase.get(k).getValue())&& currPhrase.get(j).getClause()!=currPhrase.get(k).getClause()) {
						graph.add(j++, k, 1);
					}
					//else add 0 in graph
					else {
						graph.add(j, k, 0);
					}
				}
				
			}
			//after graph has been created find maxClique
			ArrayList<Integer> tempClique = new ArrayList<Integer>();
			ArrayList<Integer> clique = new ArrayList<Integer>();
			for(int h =0; h <size; h++) {
				clique.add(h);
			}
			ArrayList<Integer> found = new ArrayList<Integer>();
			tempClique = solve.getMaxClique(graph);
			graph.toStr();
			break;
			//System.out.println(tempClique.toString());
		//	tempClique = solve.findClique(tempClique, 0, graph.getSize(), graph);
		//	print(tempClique, graph, i);
		}
	}
	
    private void print(ArrayList<Integer> clique, Graph g, int i) {
        int size = clique.size();
        char[] assignment = new char[variables.get(i)];

        System.out.print("3CNF No. " + (i+1) +": [n="+ variables.get(i) + " k="+ cnfList.get(i).size()/3 +"]");
        
        //check if clique =  num of clauses in phrase
        if (size == cnfList.get(i).size()/3) {
        	//find assignment
        	//for each node in the clique
        	for(int j = 0; j<clique.size();j++) {
        		//check value of each node in the clique, set the arrayList at that index to T if positive, F if negative
        		if(cnfList.get(i).get(clique.get(j)).getValue()>0) {
        			assignment[cnfList.get(i).get(clique.get(j)).getValue()-1] = 'T';
        		}
        		else {
        			assignment[Math.abs(cnfList.get(i).get(clique.get(j)).getValue())-1] = 'F';
        		}
        	}
        	//print array
        	for(int count = 0; count <variables.get(i); count++) {
        		System.out.print(assignment[count]+" ");
        	}
        	System.out.println();
        }
        else {
        	System.out.println(" No "+ cnfList.get(i).size()/3 +"-clique; no solution");
        }

        //System.out.println("G" + ++graphCount + " (" + g.getSize() + ", " + g.getTotalEdges() + ") " + clique.toString() + "(size=" + size + ")");
    }
}
