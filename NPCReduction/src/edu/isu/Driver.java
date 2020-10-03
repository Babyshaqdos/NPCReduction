package edu.isu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
	private static SolveClique solve;
	public static void main(String[] args) {
		
		solve = new SolveClique();
		solve.readGraph();
		
		SolveISet solveIS = new SolveISet();
		solveIS.readGraph();
		
		Solve3CNF solve3CNF = new Solve3CNF();
		solve3CNF.readCNF();

	}
	
}