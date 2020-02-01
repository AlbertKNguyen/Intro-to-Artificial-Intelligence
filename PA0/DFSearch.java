package Assignment_0;

import java.util.*;

public class DFSearch {
	private Map graph;
	private String initialLoc;
	private String destinationLoc;
	private int limit;
	public int nodeExpansionCount;

	public DFSearch(Map graph, String initialLoc, String destinationLoc, int limit) {
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
		
		// initialize stack
		Frontier stack = new Frontier();
		stack.addToTop(source);
		
		// initialize currentNode to source/initial location
		Node currentNode = source;
		
		// initialize set of visited nodes
		Set<Location> visited = new HashSet<Location>();
		
		// expand out node graph, searching for destination
		while (!stack.isEmpty() && nodeExpansionCount < limit) {
			// get and remove top node
			currentNode = stack.removeTop();

			// mark node as visited
			visited.add(currentNode.loc);

			// return destination node
			if (currentNode.loc.equals(graph.findLocation(destinationLoc))) {
				return currentNode;
			}
			
			// expand node
			currentNode.expand();
			nodeExpansionCount++;
			
			// add children nodes to stack
			for (Node child : currentNode.children) {
				// only add if location has not been visited and not in stack
				if(repeatedStateChecking) {
					if (!visited.contains(child.loc) && !stack.contains(child.loc)) {
						stack.addToTop(child);
					}
				} else { // add no matter what with no repeatedStateChecking
					stack.addToTop(child);
				}
			}
			
		}
		return null;
	}
	
}