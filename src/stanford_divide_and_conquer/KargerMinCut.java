/*
 * Karger's algorithm is a ranodmized contraction algorithm to compute a minimum cut of a undirected graph
 * 
 * Given undirected ungraph G = {V, E}, n = # of vertices, m = # of edges
 *
 * while no. of vertices > 2
 * pick a remaining edge(u, v) uniformly at random
 * merge or contract u and v into a single vertex
 * remove self-loops
 * return cut represented by final 2 vertices
 */

package stanford_divide_and_conquer;

import java.io.*;
import java.util.*; //  import package of required class 


public class KargerMinCut {
	// declare instance variables
	private static Edge edge;
	private static ArrayList<Integer> vertices;
	private static HashMap<Integer, ArrayList<Integer>> adjList;
	
	// create private inner class to hold the two ends of the chosen edge
	private class Edge {
		int startVertex;
		int endVertex;
		
		public Edge (int startVertex, int endVertex) {
			this.startVertex = startVertex;
			this.endVertex = endVertex;
		}
	}

	// constructor to create instance of KargerMincut object
	public KargerMinCut() {
		vertices = new ArrayList<Integer>();
		adjList = new HashMap<Integer, ArrayList<Integer>>();
	}

	// method to read the data from text file 
	public void readEdges(String filename) throws IOException {

		// use BufferedReader class to read text file line by line
		FileReader file = new FileReader(filename);
		BufferedReader inputFile = new BufferedReader(file);
		
		try {
			// reader the text file line by line
			String line = inputFile.readLine();
			
			while (line != null) {
				// split the line of text into an array of string
				String[] data = line.split("\t");
				
				// get the first element of data as vertex label
				int vertex = Integer.parseInt(data[0]);
				
				// create adjVertices object to store adjacent vertices
				ArrayList<Integer> adjVertices = new ArrayList<Integer>();
				
				// get the remaining elements of data as adjacent vertices
				for (int i = 1; i < data.length; i++) {
					int adjVertex = Integer.parseInt(data[i]);
					if (adjVertex != vertex) {
						adjVertices.add(adjVertex);
					}
				}
						
				// add labeled vertex into vertices which is the key of the HashMap
				vertices.add(vertex);
				// map the vertex to adjacent vertices
				adjList.put(vertex, adjVertices);
				
				// read new line of text
				line = inputFile.readLine();
			}
			
			
		} 
		catch (IOException e) {
			 e.printStackTrace();
		}

		finally {
			/*
			System.out.println(vertices);
			
			for (Integer vertex : vertices) {
				System.out.println(vertex + ": " + adjList.get(vertex));
			}
			*/		
			inputFile.close();
		}
		
	}
	
	
	// choose random edge uniformly at random
	public Edge chooseRandomEdge(ArrayList<Integer> vertices, HashMap<Integer, ArrayList<Integer>> adjList) {
		Random rand = new Random();
		int randKey = vertices.get(rand.nextInt(vertices.size()));
		ArrayList<Integer> AdjVertices = adjList.get(randKey);
		int randAdjVertex = AdjVertices.get(rand.nextInt(AdjVertices.size()));
		
		return new Edge(randKey, randAdjVertex);			
	}
	
	// merge subroutine of edge(startVertex, endVertex)
	public void merge(HashMap<Integer, ArrayList<Integer>> adjList, int startVertex, int endVertex) {
		// make a copy of array of vertices incident on startVertex
		ArrayList<Integer> startVertices = adjList.get(startVertex);
		// make a copy of array of vertices incident on endVertex
		ArrayList<Integer> endVertices = adjList.get(endVertex);
		
		//System.out.println(startVertex);
		//System.out.println(endVertex);
		
		// merge edges by combining two arrays 
		for (int i = 0; i < endVertices.size(); i++) {
			startVertices.add(endVertices.get(i));
		}
		
		// remove the key-values pair given the key is endVertex as the endVertex is fused into startVertex
		adjList.remove(endVertex);
		
		// update the current adjList given the key is startVertex
		adjList.put(startVertex, startVertices);
		
		
		// iterate every entry in HashMap to update references
		for (Integer vertex: adjList.keySet()) {
			for (int i = 0; i < adjList.get(vertex).size(); i++) {
				if (adjList.get(vertex).get(i) == endVertex) {
					adjList.get(vertex).set(i, startVertex);
				}
			}
					
		}
	
		// update vertices arraylist
		vertices.remove(vertices.indexOf(endVertex));			

	}
	
	// randomized contract algorithm to compute mincut
	public int findMinCut(ArrayList<Integer> vertices, HashMap<Integer, ArrayList<Integer>> adjList) {
		
		while (adjList.size() > 2) {
			// pick an edge(u, v) uniformly at random
			Edge randEdge = chooseRandomEdge(vertices, adjList);
			int startVertex = randEdge.startVertex;
			int endVertex = randEdge.endVertex;
			
			// merge startVetex and endVertex into a single vertex
			merge(adjList, startVertex, endVertex);
			
			// remove selfloop
			int position = 0;
			int length = adjList.get(startVertex).size();
			while (position < length)
			{
				if(adjList.get(startVertex).get(position) == startVertex)
				{
					adjList.get(startVertex).remove(position);
					// Need to decrease length, since we removed an edge
					length--;
				} 
				else
					position++;
			} 
			
		}

		/*
		for (Integer vertex: adjList.keySet()) {
			System.out.println(vertex + ": " + adjList.get(vertex));
		}
		*/
		return adjList.get(vertices.get(0)).size();
	}
	
	
	public static void main(String[] args) throws IOException {
		// measuring elapsed time using System.nanoTime
		long startTime = System.nanoTime();
		
		KargerMinCut minCut = new KargerMinCut();
		// read vertex and incident edges from text file
		minCut.readEdges("kargerMinCut.txt");
		
		// Set initial value of minCut
		int minimalMinCut = minCut.findMinCut(vertices, adjList);
		
		// n iterations to find the minimalMinCut of MinCuts
		for (int i = 0; i < 1000; i++) {
			// compute the current no. of MinCut 
			int currMinCut = minCut.findMinCut(vertices, adjList);
			
			System.out.println(i+1 + " trial: " +"No. of minCut: " + currMinCut);
			
			if (currMinCut < minimalMinCut) {
				minimalMinCut = currMinCut;
			}
			
			adjList.clear();
			vertices.clear();
			minCut = new KargerMinCut();
			minCut.readEdges("kargerMinCut.txt");
			
		}
		
		long elapsedTime = System.nanoTime() - startTime;
		
		System.out.println("Minimal number of minCut: " + minimalMinCut);
		System.out.println("Total execution time in millis: "
                + elapsedTime/1000000 + " ms");

	
	}

}
