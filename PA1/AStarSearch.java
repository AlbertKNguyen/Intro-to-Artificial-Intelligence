package Assignment_1;

import java.util.*;

public class AStarSearch {
	private StreetMap graph;
	private String initialLoc;
	private String destinationLoc;
	private int limit;
	public int nodeExpansionCount;

	public AStarSearch(StreetMap graph, String initialLoc, String destinationLoc, int limit) {
		this.graph = graph;
		this.initialLoc = initialLoc;
		this.destinationLoc = destinationLoc;
		this.limit = limit;
	}
	
	public Node search(boolean repeatedStateChecking) {
		Heuristic goodHeuristic = new GoodHeuristic(graph.findLocation(destinationLoc));
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
		SortedFrontier queue = new SortedFrontier(SortBy.f);
		queue.addSorted(source);
		
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
			currentNode.expand(goodHeuristic);
			nodeExpansionCount++;
			
			// add children nodes to queue
			for (Node child : currentNode.children) {
				if(repeatedStateChecking) {
					// add if location has not been visited and not in queue
					if (!visited.contains(child.loc) && !queue.contains(child.loc)) {
						queue.addSorted(child);
					} 
					// if not visited but in queue, add if partial path cost + heuristic value is lower or equal
					else if (!visited.contains(child.loc) && child.partialPathCost + child.heuristicValue <= queue.find(child).partialPathCost + queue.find(child).heuristicValue) {
						queue.addSorted(child);
					}
				} else { // add no matter what with no repeatedStateChecking
					queue.addSorted(child);
				}
			}
		}
		return null;
	}
	
}