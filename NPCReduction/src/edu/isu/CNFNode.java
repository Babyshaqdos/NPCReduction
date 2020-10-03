package edu.isu;

public class CNFNode {

	private int value;
	private int clause;
	
	CNFNode(int val, int cl){
		value = val;
		clause = cl;
	}

	public int getValue() {
		return value;
	}

	public int getClause() {
		return clause;
	}

}
