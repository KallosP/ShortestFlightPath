
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



class Graph {
 
    private Set<Node> nodes = new HashSet<>();
     
    public void addNode(Node node) {
        nodes.add(node);
    }
    
    public Set<Node> getNodes()
    {
    	return nodes;
    }
 
}


class Node {
    
    private String name;
     
    private List<Node> shortestPath = new LinkedList<>();
     
    private Integer distance = Integer.MAX_VALUE;
     
    Map<Node, Integer> adjacentNodes = new HashMap<>();
 
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
  
    public Node(String name) {
        this.name = name;
    }
    
    public void setDistance(int dist)
    {
    	distance = dist;
    }
    
    public int getDistance()
    {
    	return distance;
    }
    
    public Map<Node,Integer> getAdjacentNodes()
    {
    	return adjacentNodes;
    }
    
    public List<Node> getShortestPath()
    {
    	return shortestPath;
    }
    
    public void setShortestPath(List<Node> newshortestpath)
    {
    	shortestPath = newshortestpath;
    }
    
    @Override
    public String toString()
    {
    	return name;
    }
     
}



class Dijkstra
{
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
		//initializes the first node to be 0 (otherwise NullPointerException), this is the starting point
		source.setDistance(0); 
	 
		//create a set to hold list of unvisited nodes
	    Set<Node> unvisitedNodes = new HashSet<>(); 
	 
	    //stores all nodes from graph into unvisitedNodes Set
	    for(Node node : graph.getNodes()) {
	    	unvisitedNodes.add(node);
	    }
	    
	    //true as long as unvisited nodes are still present
	    while (unvisitedNodes.size() != 0) {
	    	//finds lowest distance node in graph
	        Node currentNode = getLowestDistanceNode(unvisitedNodes); 
	        
	        //removes lowest distance node from the graph
	        unvisitedNodes.remove(currentNode); 
	        
	        //visits each adjacent node to currentNode and uses CalculateMinimumDistance to determine whether or not adjacent nodes
	        //to currentNode should be updated to have a shorter path
	        for (Entry <Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet())
	        {
	        	//get the adjacent node of the current node
	        	Node adjacentNode = adjacencyPair.getKey();
	        	
	        	//get the edge weight of the current node
	            Integer edgeWeight = adjacencyPair.getValue();
	            
	            //finds shortest distance
	            CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode); 
	        }
	        
	    }
	    //return the resultant graph with the shortest paths
	    return graph;
	}

	private static Node getLowestDistanceNode(Set < Node > unvisitedNodes) {
		//initialize lowest distance node to null, only updated if a lower distance is found
	    Node lowestDistanceNode = null; 
	    
	    //initialized to the largest possible value (infinity)
	    int lowestDistance = Integer.MAX_VALUE;
	    
	    //logic for determining whether or not a node's value should be smaller (or stay the same)
	    for (Node node: unvisitedNodes) {
	    	//get the distance of current node
	        int nodeDistance = node.getDistance(); 
	        
	        //if the distance is smaller than what the node currently has then make that distance the new smallest distance
	        if (nodeDistance < lowestDistance) {
	        	//makes current distance the new smallest distance
	            lowestDistance = nodeDistance; 
	            
	            //changes the null value to become the current node being checked;
	            //reaching this point in the code means a node's value was updated to a shorter distance
	            lowestDistanceNode = node; 
	        }
	    }
	    //returns null if no node with a lower distance was found, if lower distance was found, return the updated node 
	    return lowestDistanceNode; 
	}

	private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode)
	{
		        //gets the distance of the current node
			    Integer sourceDistance = sourceNode.getDistance(); 
			    
			    //if the distance of the current node + its edge weight is less than the node's current distance, 
			    //return true 
			    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) { 
			    	//set the current distance of the node that's being evaluated be the sum of the sourceDistance and the edgeWeigh
			        evaluationNode.setDistance(sourceDistance + edgeWeigh);
			        
			        //make a LinkedList of Nodes to store the shortest path
			        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			        
			        //add the shortest path that was found to the shortestPath LinkedList
			        shortestPath.add(sourceNode);
			        
			        //finally updated the shortest path to the node that is being evaluated
			        evaluationNode.setShortestPath(shortestPath);
			    }
	}
}

public class GraphDijkstraExample
{
	
	public static void main(String args[])
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D"); 
		Node nodeE = new Node("E");
		Node nodeF = new Node("F");
		Node nodeG = new Node("G");
		Node nodeH = new Node("H");

		
		nodeA.addDestination(nodeG, 278);
		nodeA.addDestination(nodeH, 115);
		nodeA.addDestination(nodeB, 155);
		
		nodeB.addDestination(nodeA, 155);
		nodeB.addDestination(nodeC, 168);
		
		nodeC.addDestination(nodeB, 168);
		nodeC.addDestination(nodeD, 250);
		
		nodeD.addDestination(nodeH, 65);
		nodeD.addDestination(nodeC, 250);
		nodeD.addDestination(nodeE, 280);
		
		nodeE.addDestination(nodeD, 280);
		nodeE.addDestination(nodeF, 145);
		
		nodeF.addDestination(nodeG, 315);
		nodeF.addDestination(nodeE, 145);
		nodeF.addDestination(nodeH, 218);
		
		nodeG.addDestination(nodeF, 315);
		nodeG.addDestination(nodeA, 278);
		nodeG.addDestination(nodeH, 78);
		
		nodeH.addDestination(nodeD, 65);
		nodeH.addDestination(nodeF, 218);
		nodeH.addDestination(nodeG, 78);
		nodeH.addDestination(nodeA, 115);
		
		 
		Graph graph = new Graph();
		 
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);

		 
		graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);
		
		for (Node node: graph.getNodes())
		System.out.println(node.toString() + node.getDistance() + node.getShortestPath());
	
	}
}