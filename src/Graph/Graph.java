package Graph;

import java.util.HashMap;

public class Graph {
	private final int MAX_VERTICES = 16;
	private final char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	private int numVertices;
	private int numEdges;
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); 
	private String[][] verticesMatrix;
	private String[] verticesName;
	
	//Edge sub Class.
	private class Edge{
		private String edgeName;
		private int weight;
		private String fromVertice;
		private String toVertice;
		private Edge(String name, int weight){
			edgeName = name;
			this.weight = weight;
		}
		
		//Return name of the edge
		public String getName(){
			return edgeName;
		}
	}
	
	/*CONSTRUCTOR*/
	
	//Construct a new graph with input number of vertices. MAX = 16
	public Graph(int inputNumVertices, int inputNumEdges){
		//Initialization		
		numVertices = Math.min(inputNumVertices, MAX_VERTICES);
		verticesMatrix = new String[numVertices][numVertices];
		verticesName = new String[numVertices];
		int maxNumEdges = numVertices*(numVertices - 1)/2;
		numEdges = Math.min(maxNumEdges, inputNumEdges);
			
		//Generate vertices
		generateVertices();
		generateEdges();
	}
	
	//Generate vertice's name using alphabet order.
	private void generateVertices(){
		for(int i = 0; i < verticesName.length; i++){
			verticesName[i] = CHARSET_AZ_09[i] +"";
		}
	}	
	
	//Generate random edge
	private void generateEdges(){
		for(int i = 1; i <= numEdges; i++){
			
			//Pick an random empty slot in verticesMatrix
			boolean found = false;
			int firstVertexIndex = -1;
			int secondVertexIndex = -1;
			do{
				firstVertexIndex = (int)(Math.random() * numVertices);
				secondVertexIndex = (int)(Math.random() * numVertices);
				String valueAtIndex = verticesMatrix[firstVertexIndex][secondVertexIndex];
				if(valueAtIndex == null && firstVertexIndex != secondVertexIndex)
					found = true;
			}
			while(!found);
			
			//Initialize edge
			String edgeKey = verticesName[firstVertexIndex] + "-" + verticesName[secondVertexIndex]; //Key for edge in HashMap will have format 'incoming edge'-'outgoing edge'
			String edgeName = verticesName[firstVertexIndex] + verticesName[secondVertexIndex]; 
			int weight = (int)(Math.random() * 100 + 1); //Weight for edge will be random number from 1 - 100
			Edge newEdge = new Edge(edgeName, weight);
			
			//Store key to HashMap
			edgeList.put(edgeKey, newEdge);
			//Store key-reference into verticeMatrix
			verticesMatrix[firstVertexIndex][secondVertexIndex] = edgeKey;
			verticesMatrix[secondVertexIndex][firstVertexIndex] = edgeKey; //Since this is undirected graph A->B is the same as B->A.
		}
	}
	
	/*SOME ULTILITY FUNCTIONS*/
	//Get index of the vertex
	private int getVertexIndex(String vertex){
		int vertexIndex = -1;
		for(int i = 0; i < verticesName.length; i++ ){
			if(verticesName[i].equals(vertex)){
				vertexIndex = i;
				break;
			}
		}
		return vertexIndex;
	}
	
	/*SOME COMMON GRAPH FUNCTION*/
	//Return the number of vertices in G
	public int numVertices(){
		return numVertices;
	}
	
	//Return the number of edges in G
	public int numEdges(){
		return numEdges;
	}
	
	//Return all vertices in G. All vertices will be concatenated into a single string.
	public String vertices(){
		String result = "";
		for(int i = 0; i < verticesName.length; i++){
			result += verticesName[i] + " ";
		}
		return result;
	}
	
	//Return all edges in the graph G. All edges will be concatenated in
	public String edges(){
		String result = "";
		for(String key : edgeList.keySet()){
			result += edgeList.get(key).getName() + " ";
		}
		return result;
	}
	
	//Return degree of the input vertex
	public int degree(String vertex){
		int result = 0;
		int vertexIndex = this.getVertexIndex(vertex);		
		for(int i = 0; i < verticesMatrix[vertexIndex].length; i++){
			if(verticesMatrix[vertexIndex][i] != null ){
				result++;
			}
		}
		return result;
	}
	
	//Return vertices that adjacent to input vertex.
	public String adjacentVertices(String vertex){
		String result = "";
		int vertexIndex = this.getVertexIndex(vertex);
		for(int i = 0; i < verticesMatrix[vertexIndex].length; i++){
			String value = verticesMatrix[vertexIndex][i];
			if(value != null ){
				String vertexName = verticesName[i];
				result += vertexName + " ";
			}
		}	
		return result;
	}
	
	//Return all edges incident upon vertex.
	public String incidentEdges(String vertex){
		String result = "";
		int vertexIndex = this.getVertexIndex(vertex);
		for(int i = 0; i < verticesMatrix[vertexIndex].length; i++){
			String value = verticesMatrix[vertexIndex][i];
			if(value != null ){
				String edgeName = edgeList.get(value).getName();
				result += edgeName + " ";
			}
		}	
		return result;
	}
	
	//TESTING
	public static void main(String[] args){
		Graph newGraph = new Graph(7,7);
		System.out.println("Number of Vertices: " + newGraph.numVertices());
		System.out.println("Number of Edges: " + newGraph.numEdges());
		System.out.println("Vertices: " + newGraph.vertices());
		System.out.println("Edges: " + newGraph.edges());
		System.out.println("Degree of vertex 'A': " + newGraph.degree("A"));
		System.out.println("Adjacent vertices of vertex 'A': " + newGraph.adjacentVertices("A"));
		System.out.println("Incident edges upon vertex 'A': " + newGraph.incidentEdges("A"));
	}
}
