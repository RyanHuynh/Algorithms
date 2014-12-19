package Graph;
import java.util.HashMap;

/**Graph implementation using Adjacency Matrix Structure.
 * This demo has some basic function you can perform on a undirected graph as well as some algorithms to find shortest path.
 * 
 * @author Ryan Huynh
 * @version Dec 2014
 *
 */
public class Graph {
	private final int MAX_VERTICES = 16;
	private final char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	private int numVertices;
	private int numEdges;
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); 
	private String[][] verticesMatrix;
	private String[] verticesName;
	
	/** 
	 * Edge class which holds information about the edge
	 */
	private class Edge{
		private String edgeName;
		private int weight;
		private String fromVertice;
		private String toVertice;
		private Edge(String name, int weight){
			edgeName = name;
			this.weight = weight;
		}
		
		/**
		 * Return edge name.
		 *
		 * @return name of the edge.
		 */
		private String getName(){
			return edgeName;
		}
	}
	
	/**CONSTRUCTOR*/
	/**
	 * Construct a new graph with input number of vertices. MAX = 16
	 * Edges are randomly generate (with weight).
	 *
	 * @param inputNumVertices the input num vertices
	 * @param inputNumEdges the input num edges
	 */
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
	
	/**SOME ULTILITY FUNCTIONS*//**
	 * Generate vertices' names using alphabet order.
	 */
	private void generateVertices(){
		for(int i = 0; i < verticesName.length; i++){
			verticesName[i] = CHARSET_AZ_09[i] +"";
		}
	}	
	
	/**
	 * Generate random edges (with random weight from 1 - 100), then store this edge in edgeList.
	 */
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
	/**
	 * Return the vertex's index
	 * @param vertex
	 * @return index of input vertex.
	 */
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
	
	/**SOME COMMON GRAPH FUNCTION*/
	/**
	 * Return number of vertices in the graph.
	 * @return number of vertices in the graph
	 */
	public int numVertices(){
		return numVertices;
	}
	
	/**
	 * Return number of edges in the graph.
	 *
	 * @return number of edges.
	 */
	public int numEdges(){
		return numEdges;
	}
	
	/**
	 * Return all vertices in G. All vertices will be concatenated into a single string.
	 *
	 * @return vertices in the graph.
	 */
	public String vertices(){
		String result = "";
		for(int i = 0; i < verticesName.length; i++){
			result += verticesName[i] + " ";
		}
		return result;
	}
	
	/**
	 * Return all edges in the graph G. All edges will be concatenated in
	 *
	 * @return edges in the graph.
	 */
	public String edges(){
		String result = "";
		for(String key : edgeList.keySet()){
			result += edgeList.get(key).getName() + " ";
		}
		return result;
	}
	
	/**
	 * Degree of the vertex (Number of edges that connects to this vertex)
	 *
	 * @param vertex the vertex
	 * @return degree of this vertex.
	 */
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
	
	/**
	 * Return adjacent vertices that connect to this vertex.
	 *
	 * @param vertex the vertex
	 * @return String of vertices that connect to the vertex.
	 */
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
	
	/**
	 * Return all edges incident upon vertex.
	 *
	 * @param vertex the vertex
	 * @return String of all edge connect to this vertex.
	 */
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
	
	/**TESTING*/
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
