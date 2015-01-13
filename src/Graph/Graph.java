package Graph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

/**Graph implementation using Adjacency Matrix Structure.
 * This demo has some basic function you can perform on a undirected graph as well as some algorithms to find shortest path.
 * 
 * @author Ryan Huynh
 * @version Jan 2015
 *
 */
public class Graph {
	private final int MAX_VERTICES = 16;
	private final char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	private int numVertices;
	private int numEdges;
	private boolean directed;
	private HashMap<String, Edge> edgeList = new HashMap<String, Edge>(); 
	private String[][] verticesMatrix;
	private String[] verticesName;
	//Those arrays are used to store discovered edge and back edge in graph traversal.
	private ArrayList<String> discoveredEdges;
	private ArrayList<String> backEdges;
	private ArrayList<String> discoveredVertices;
	//This hashMap is used to store distance from a vertex to all other vertices in G. Key: name of vertex, Value: distance.
	private HashMap<String,Integer> distance;
	
	
	/** 
	 * Edge class which holds information about the edge
	 */
	private class Edge{
		private int weight;
		private String startVertex;
		private String endVertex;
		private Edge(String fromVertex, String toVertex, int weight){
			this.startVertex = fromVertex;
			this.endVertex = toVertex;
			this.weight = weight;
		}
		
		/**
		 * Gets the weight of the edge.
		 *
		 * @return the weight
		 */
		private int getWeight(){
			return weight;
		}
		
		/**
		 * Gets the vertex where the edge started.
		 *
		 * @return the vertex
		 */
		private String getStartVertex(){
			return startVertex;
		}
		
		/**
		 * Gets the vertex where the edge goes to.
		 *
		 * @return the to vertex
		 */
		private String getEndVertex(){
			return endVertex;
		}
	}
	
	/**
	 * This Distance sub-class is used in Dijkstra's algorithm where the variable 'distance' is the distance
	 * from starting vertex to the vertex v (which will be stored in variable 'vertex').
	 * The 'distance' variable also will be used as key to be compared in our priorityQueue for the algorithm
	 */
	private class Distance{
		private int distance;
		private String vertex;
		private Distance(int d, String v){
			distance = d;
			vertex = v;
		}
		
		//Return distance to this vertex.
		private int getDistance(){
			return distance;
		}
		
		private String getVertexName(){
			return vertex;
		}
	}
	
	/**
	 * This comparator interface inplementation is used for PriorityQueue constructor for our Dijkstra's algorithm.
	 * The queue will be sorted with the smallest distance in the top of the queue.
	 */
	private static Comparator<Distance> distanceComparator = new Comparator<Distance>(){
		
		public int compare(Distance vertex1, Distance vertex2) {
			return (int)(vertex1.getDistance() - vertex2.getDistance());
		}
	};
	
	/**
	 * Construct a new graph with input number of vertices. MAX = 16
	 * Edges are randomly generate (with weight).
	 *
	 * @param inputNumVertices the input num vertices
	 * @param inputNumEdges the input num edges
	 * @param directed indicate whether or not this graph is directed.
	 */
	public Graph(int inputNumVertices, int inputNumEdges, boolean flag){
		
		//Initialization		
		numVertices = Math.min(inputNumVertices, MAX_VERTICES);
		verticesMatrix = new String[numVertices][numVertices];
		directed = flag;
		verticesName = new String[numVertices];
		int maxNumEdges = numVertices*(numVertices - 1)/2;
		numEdges = Math.min(maxNumEdges, inputNumEdges);
			
		//Generate vertices
		generateVertices();
		if(directed)
			generateDirectedEdges();
		else
			generateEdges();
	}
	
	/**SOME ULTILITY FUNCTIONS*/
	
	/**
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
			String edgeKey = verticesName[firstVertexIndex] + verticesName[secondVertexIndex]; //Key for edge in HashMap will have format combine of both vertices. 
			int weight = (int)(Math.random() * 100 + 1); //Weight for edge will be random number from 1 - 100
			Edge newEdge = new Edge(verticesName[firstVertexIndex], verticesName[secondVertexIndex],weight);
			
			//Store key to HashMap
			edgeList.put(edgeKey, newEdge);
			//Store key-reference into verticeMatrix
			verticesMatrix[firstVertexIndex][secondVertexIndex] = edgeKey;
			verticesMatrix[secondVertexIndex][firstVertexIndex] = edgeKey; //Since this is undirected graph A->B is the same as B->A.
		}
	}
	
	/**
	 * Generate random directed edges (with random weight from -100 -> 100), then store this edge in edgeList.
	 */
	private void generateDirectedEdges(){
		for(int i = 1; i <= numEdges; i++){
			
			//Pick an random empty slot in verticesMatrix
			boolean found = false;
			int startVertexIndex = -1;
			int endVertexIndex = -1;
			do{
				startVertexIndex = (int)(Math.random() * numVertices);
				endVertexIndex = (int)(Math.random() * numVertices);
				String valueAtIndex = verticesMatrix[startVertexIndex][endVertexIndex];
				if(valueAtIndex == null && startVertexIndex != endVertexIndex)
					found = true;
			}
			while(!found);
			
			//Initialize edge
			String edgeKey = verticesName[startVertexIndex] + verticesName[endVertexIndex]; //Key for edge in HashMap will have format combine of both vertices. 
			Random rng = new Random();
			int weight = rng.nextInt(201) - 100; //Weight for edge will be random number from -100 to 100.
			Edge newEdge = new Edge(verticesName[startVertexIndex], verticesName[endVertexIndex],weight);
			
			//Store key to HashMap
			edgeList.put(edgeKey, newEdge);
			//Store key-reference into verticeMatrix
			verticesMatrix[startVertexIndex][endVertexIndex] = edgeKey;
			verticesMatrix[endVertexIndex][startVertexIndex] = edgeKey; //Only allow 1 direction from each vertex for now.
		}
	}
	
	
	/**
	 * Return the edge object with key as input.
	 *
	 * @param inputEdge the input edge name.
	 * @return the edge object
	 */
	private Edge getEdge(String inputEdge){
		return edgeList.get(inputEdge);
	}
	/**
	 * Return the vertex's index
	 * @param vertex's name
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
	
	/**
	 * Prints the discovered edges in graph traversal.
	 */
	private void printDiscoveredEdge(){
		System.out.print("Discovered edges:");
		if(discoveredEdges != null){
			for(int i = 0; i < discoveredEdges.size(); i++){
				System.out.print(discoveredEdges.get(i) + " ");
			}
		}	
		System.out.println();
	}
	
	/**
	 * Prints the back edges in graph traversal.
	 */
	private void printBackEdge(){
		System.out.print("Back edges:");
		if(backEdges != null){
			for(int i = 0; i < backEdges.size(); i++){
				System.out.print(backEdges.get(i) + " ");
			}
		}	
		System.out.println();
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
	 * Return all edges in the graph G. All edges will be concatenated in a string.
	 *
	 * @return edges in the graph.
	 */
	public String edges(){
		String result = "";
		for(String key : edgeList.keySet()){
			result += key + " ";
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
	public ArrayList<String> incidentEdges(String vertex){
		ArrayList<String> result = new ArrayList<String>();
		int vertexIndex = this.getVertexIndex(vertex);
		for(int i = 0; i < verticesMatrix[vertexIndex].length; i++){
			String value = verticesMatrix[vertexIndex][i];
			if(value != null ){
				String edgeName = value;
				result.add(edgeName);;
			}
		}	
		return result;
	}
	
	/**
	 * Get the opposite vertex of the input vertex  v on edge e
	 *
	 * @param v the vertex
	 * @param e the edge
	 * @return vertex 
	 */
	public String opposite(String v, String e){
		Edge inputEdge = this.getEdge(e);
		String startVertex = inputEdge.getStartVertex();
		String endVertex = inputEdge.getEndVertex();
		return v.equals(startVertex) ? endVertex : startVertex; 
	}
	
	/**
	 * Gets the edges and the weight associated with it.
	 *
	 * @return the edges and weight
	 */
	public String getEdgesAndWeight(){
		String result = "";
		for(String key : edgeList.keySet() ){
			result+= key + ": " + edgeList.get(key).getWeight() + ", ";
		}
		return result;
	}
	/**GRAPH TRAVERSAL*/
	/**
	 * Depth-First Search.
	 * Recursively travel the G, if an edge is unexplored:
	 * 		- It points to unexplored vertex: mark this edge as discovered Edge.
	 * 		- It points to explored vertex: mark this edge as back edge.
	 * @param startVertex the start vertex's name.
	 */
	public void DFS(String startVertex){
		//Reset containers
		discoveredEdges = new ArrayList<String>();
		discoveredVertices = new ArrayList<String>();
		backEdges = new ArrayList<String>();
		
		//Start DFS
		this.doDFS(startVertex);
		
		//Print result
		this.printDiscoveredEdge();
		this.printBackEdge();
	}
	
	/**
	 * Depth-First Search implementation.
	 * Save all discovered edges in discoveredEdges array and all backEdges in backEdges array
	 * DFS start at vertex v and end when all edges are visited.
	 * 
	 * @param startVertex the start vertex
	 */
	private void doDFS(String startVertex){
		//Mark input vertex as explored
		discoveredVertices.add(startVertex);
		
		//Get incident edges on input vertex
		ArrayList<String> incidentEdges = this.incidentEdges(startVertex);
		for(int i = 0; i < incidentEdges.size(); i++){
			
			String e = incidentEdges.get(i);

			//Check for valid edge in directed graph. Valid edge is edge coming out from this current vertex.
			boolean valid = true;
			if(directed){
				Edge checkEdge = this.getEdge(e);
				String startVertex2 = checkEdge.getStartVertex();
				if(!startVertex2.equals(startVertex))
					valid = false;
			}
			if(valid){
				//Check to see if edge e is explored?
				if(!discoveredEdges.contains(e) && !backEdges.contains(e)){
					String oppositeVertex = this.opposite(startVertex, e);
					
					//If oppositeVertex is unexplored then add edge e to discovered edge list.
					if(!discoveredVertices.contains(oppositeVertex)){
						discoveredEdges.add(e);
						this.doDFS(oppositeVertex);
					}
					
					//Add e to back edge list
					else{
						backEdges.add(e);
					}
				}
			}
		}
	}
	
	/**
	 * Breadth-First Search.
	 * Add the start vertex s in container, explore all edges incident on this vertex. Add all adjacent vertices with s to 
	 * another container and mark it as next level. All the edges explore:
	 * 		- If it points to unexplored vertex then mark it as discovered edges.
	 * 		- If it points to explored vertex then mark it as next cross edges.
	 * Continue the same procedure at the next level until all vertices are visited.
	 * @param startVertex
	 */
	public void BFS(String startVertex){
		//Reset containers
		discoveredEdges = new ArrayList<String>();
		discoveredVertices = new ArrayList<String>();
		backEdges = new ArrayList<String>();
		
		//Start BFS
		this.doBFS(startVertex);
		
		//Print result
		this.printDiscoveredEdge();
		this.printBackEdge();
	}
	
	/**
	 * Breadth-First Search implementation.
	 * Save all discovered edges in discoveredEdges array and all backEdges in backEdges array
	 * BFS start at vertex v.
	 * 
	 * @param startVertex the start vertex
	 */
	private void doBFS(String startVertex){
		//Create a temporary container to hold vertices at current level. Add Start edge to this level.
		ArrayList<String> currentLevel = new ArrayList<String>();
		currentLevel.add(startVertex);
		//Add start vertex to discovered vertex as well
		discoveredVertices.add(startVertex);
		while(currentLevel.size() > 0){
			
			//Create another container to hold vertices at next level.
			ArrayList<String> nextLevel = new ArrayList<String>();
			for(int i = 0; i < currentLevel.size(); i++){
				String currentVertex = currentLevel.get(i);
				
				//Get incident edge on current vertex.
				ArrayList<String> incidentEdges = this.incidentEdges(currentVertex);
				for(int j = 0; j < incidentEdges.size(); j++){
					String e = incidentEdges.get(j);
					//Check for valid edge in directed graph. Valid edge is edge coming out from this current vertex.
					boolean valid = true;
					if(directed){
						Edge checkEdge = this.getEdge(e);
						String startVertex2 = checkEdge.getStartVertex();
						if(!startVertex2.equals(currentVertex))
							valid = false;
					}
					if(valid){
						//Check to see if edge e is explored?
						if(!discoveredEdges.contains(e) && !backEdges.contains(e)){
							String oppositeVertex = this.opposite(currentVertex, e);
							
							//If this vertex is not discovered
							if(!discoveredVertices.contains(oppositeVertex)){
								//Mark this as discovered vertex.
								discoveredVertices.add(oppositeVertex);
								discoveredEdges.add(e);
								nextLevel.add(oppositeVertex);
							}
							else{
								backEdges.add(e);
							}
						}
					}
				}
			}
			
			//Go to next level
			currentLevel = nextLevel;
		}
	}
	/**
	 * SHORTEST PATH PROBLEM
	 */
	/**
	 * Dijkstra algorithm implementation.
	 * Find the shortest path from starting vertex to other vertex: 
	 * The algorithm will run through the graph and print out the distance from starting vertex to other vertices.
	 *
	 * @param vertex the starting vertex.
	 */
	public void Dijkstra(String vertex){
		//Reset containers
		discoveredVertices = new ArrayList<String>();
		//Initialize our return hashMap.
		distance = new HashMap<String,Integer>();
		
		//Priority queue used in our algorithm. We use comparator 'distanceComparator', which is implemented above.
		PriorityQueue<Distance> queue = new PriorityQueue<Distance>(this.numVertices(),distanceComparator);
		
		//Set distance to starting vertex to 0 and added to our PriorityQueue and the return distance Hashmap.
		distance.put(vertex, 0);
		Distance distanceObj = new Distance(0, vertex);
		queue.add(distanceObj);
		
		//Add the remaining vertices to the queue. Each of the vertex will have default infinite value.
		for(int i = 0; i < verticesName.length; i++){
			String v = verticesName[i];
			if(!vertex.equals(v)){
				//Note here i subtract infinite value by i to max each value unique because we need to use this as key to compare.
				int infiniteVal = (Integer.MAX_VALUE - i) / 2;
				Distance d = new Distance(infiniteVal, v);
				queue.add(d);
				distance.put(v, infiniteVal);
			}
		}
		
		//We start by remove vertex from PriorityQueue.
		while(!queue.isEmpty()){
			//Get the vertex with the smallest distance from queue and added to our cloud. Note: discoveredVertices structure is
			//acting as our cloud in this implementation
			Distance smallestDistanceVertex = queue.poll();
			String currentVertex = smallestDistanceVertex.getVertexName();
			discoveredVertices.add(currentVertex);
			
			//Find the adjacent vertices to currentVertex that is not in the cloud.
			ArrayList<String> incidentEdges = this.incidentEdges(currentVertex);
			for(int i = 0; i < incidentEdges.size(); i++){
				String e = incidentEdges.get(i);
				//Check for valid edge in directed graph. Valid edge is edge coming out from this current vertex.
				boolean valid = true;
				if(directed){
					Edge checkEdge = this.getEdge(e);
					String startVertex2 = checkEdge.getStartVertex();
					if(!startVertex2.equals(currentVertex))
						valid = false;
				}
				if(valid){
					//Check to see if the opposite vertex is in cloud.
					String oppositeVertex  = this.opposite(currentVertex, e);
					if(!discoveredVertices.contains(oppositeVertex)){
						
						//Begin to do relaxation procedure on this currentVertex and edge e.
						//If the current distance from starting vertex to this opposite vertex is larger than the distance from
						//current vertex v + weight of edge (v, oppositeVertex), then update the distance to opposite vertex (in our result HashMap).
						int currentDistanceAtOppsiteVertex  = distance.get(oppositeVertex);
						int distanceToOppisiteVertex = distance.get(currentVertex) + this.getEdge(e).getWeight();
						if(currentDistanceAtOppsiteVertex > distanceToOppisiteVertex){
							//Update its value in result HashMap
							distance.put(oppositeVertex, distanceToOppisiteVertex);
							//Update our PriorityQueue. O(n) !!!!
							for(Distance d : queue){
								if(d != null){			
									if(d.getDistance() == currentDistanceAtOppsiteVertex){
										queue.remove(d);
										Distance newDistance = new Distance(distanceToOppisiteVertex, oppositeVertex);
										queue.add(newDistance);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		//Print out the result
		for(String k : distance.keySet()){
			System.out.print(k + ": " + distance.get(k) + ", ");
		}
	}
	
	public void bellmanFord(String startingVertex){
		//Reset containers
		discoveredVertices = new ArrayList<String>();
		//Initialize our return hashMap.
		distance = new HashMap<String,Integer>();
		
		//Set distance to starting vertex to 0.
		distance.put(startingVertex, 0);
		
		//Add the remaining vertices to the result container. Each of the vertex will have default infinite value.
		for(int i = 0; i < verticesName.length; i++){
			String v = verticesName[i];
			if(!startingVertex.equals(v)){
				int infiniteVal = Integer.MAX_VALUE / 2; //Avoid the case where value > infinity will go negative
				distance.put(v, infiniteVal);
			}
		}
		
		//Begin relaxation process from 1 to n - 1 times on G
		for(int i = 1; i < numVertices; i++){
			for(String k : edgeList.keySet()){
				Edge currentEdge = edgeList.get(k);
				String startVertex = currentEdge.getStartVertex();
				String endVertex = currentEdge.getEndVertex();
				int distanceAtStartVertex = distance.get(startVertex);
				int distanceAtEndVertex = distance.get(endVertex);
				int newDistance = distanceAtStartVertex + currentEdge.getWeight();
				if(newDistance < distanceAtEndVertex){
					distance.put(endVertex, newDistance);
				}
			}
		}
		
		//Print out the result 
		//Check for negative cycle.
		boolean negativeCycle = false;
		for(String k : edgeList.keySet()){
			Edge currentEdge = edgeList.get(k);
			String startVertex = currentEdge.getStartVertex();
			String endVertex = currentEdge.getEndVertex();
			int distanceAtStartVertex = distance.get(startVertex);
			int distanceAtEndVertex = distance.get(endVertex);
			int newDistance = distanceAtStartVertex + currentEdge.getWeight();
			if(newDistance < distanceAtEndVertex){
				negativeCycle = true;
				System.out.print("The graph contains negative cycle");
				break;
			}
		}
		
		if(!negativeCycle)
		for(String k : distance.keySet()){
			System.out.print(k + ": " + distance.get(k) + ", ");
		}
	}
	/**TESTING*/
	public static void main(String[] args){
		
		//Undirected graph
		/*Graph newGraph = new Graph(7,7,false);
		System.out.println("Number of Vertices: " + newGraph.numVertices());
		System.out.println("Number of Edges: " + newGraph.numEdges());
		System.out.println("Vertices: " + newGraph.vertices());
		System.out.println("Edges: " + newGraph.edges());
		System.out.println("Degree of vertex 'A': " + newGraph.degree("A"));
		System.out.println("Adjacent vertices of vertex 'A': " + newGraph.adjacentVertices("A"));
		System.out.print("Incident edges upon vertex 'A': ");
		ArrayList<String>str = newGraph.incidentEdges("A");
		for(int i = 0; i < str.size(); i++){
			System.out.print(str.get(i) + " ");
		}
		System.out.println();
		System.out.println("Weight for each edges: " + newGraph.getEdgesAndWeight());
		System.out.println("Depth-First Search: ");
		newGraph.DFS("A");
		
		System.out.println("Breadth-First Search: ");
		newGraph.BFS("A");
		
		System.out.print("Dijkstra: ");
		newGraph.Dijkstra("B");*/
		
		//Directed graph
		Graph newGraph = new Graph(7,10,true);
		System.out.println("Number of Vertices: " + newGraph.numVertices());
		System.out.println("Number of Edges: " + newGraph.numEdges());
		System.out.println("Vertices: " + newGraph.vertices());
		System.out.println("Edges: " + newGraph.edges());
		System.out.println("Degree of vertex 'A': " + newGraph.degree("A"));
		System.out.println("Adjacent vertices of vertex 'A': " + newGraph.adjacentVertices("A"));
		System.out.print("Incident edges upon vertex 'A': ");
		ArrayList<String>str = newGraph.incidentEdges("A");
		for(int i = 0; i < str.size(); i++){
			System.out.print(str.get(i) + " ");
		}
		System.out.println();
		System.out.println("Weight for each edges: " + newGraph.getEdgesAndWeight());
		System.out.println("Depth-First Search: ");
		newGraph.DFS("A");
		
		System.out.println("Breadth-First Search: ");
		newGraph.BFS("A");
	
		System.out.print("Dijkstra: ");
		newGraph.Dijkstra("A");
		
		System.out.println();
		System.out.print("Bellman Ford: ");
		newGraph.bellmanFord("A");
		
	}
}
