package edu.isu;

public class Graph {
	private int[][] g;
	private int size;
	private int[] edges;
	private int totalEdges;
	
	Graph(int n) {
		size = n;
		totalEdges = 0;
		g = new int[n][n];
		edges = new int[size];
		for(int i =0; i <size; i++) {
			edges[i] = 0;
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public int getTotalEdges() {
		return totalEdges;
	}
	public void updateEdge(int x, int y, int n) {
		if(n == -1) {
			edges[x] -= 1;
			return;
		}
		if(g[y][x] == n) {
			edges[x] += 1;
		}
		else {
			edges[x] += 1;
			totalEdges++;
		}
		
	}
	
	public void add(int x, int y, int n) {
		g[x][y] = n;
	}
	
	public int get(int x, int y) {
		return g[x][y];
	}
	
	public void toStr() {
		for(int i =0; i <size; i++) {
			System.out.println();
			for(int y=0; y < size; y++) {
				System.out.print(g[i][y]);
			}
		}
	}
	
}