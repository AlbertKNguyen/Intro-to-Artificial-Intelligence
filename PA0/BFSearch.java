package Assignment_0;

import java.util.*;

public class BFSearch {
	private Map graph;
	private String initialLoc;
	private String destinationLoc;
	private int limit;
	public int nodeExpansionCount;

	public BFSearch(Map graph, String initialLoc, String destinationLoc, int limit) {
		this.graph = graph;
		this.initialLoc = initialLoc;
		this.destinationLoc = destinationLoc;
		this.limit = limit;
	}
	
	public Node search(boolean repeatedStateChecking) {
		nodeExpansionCount = 0;
		Node source = null;
		
		// find source location to initialize source node
		for (Location loc : graph.locations) {
			if (loc.equals(graph.findLocation(initialLoc))) {
				source = new Node(graph.findLocation(initialLoc));
				break;
			}
		}
		
		// initialize queue
		Frontier queue = new Frontier();
		queue.addToBottom(source);
		
		// initialize currentNode to source/initial location
		Node currentNode = source;
		
		// initialize set of visited nodes
		Set<Location> visited = new HashSet<Location>();
		
		// expand out node graph, searching for destination
		while (!queue.isEmpty() && nodeExpansionCount < limit) {
			// get and remove top node
			currentNode = queue.removeTop();

			// mark node as visited
			visited.add(currentNode.loc);

			// return destination node
			if (currentNode.loc.equals(graph.findLocation(destinationLoc))) {
				return currentNode;
			}
			
			// expand node
			currentNode.expand();
			nodeExpansionCount++;
			
			// add children nodes to queue
			for (Node child : currentNode.children) {
				// only add if location has not been visited and not in queue
				if(repeatedStateChecking) {
					if (!visited.contains(child.loc) && !queue.contains(child.loc)) {
						queue.addToBottom(child);
					}
				} else { // add no matter what with no repeatedStateChecking
					queue.addToBottom(child);
				}
			}
			
		}
		return null;
	}
	
}