package Assignment_1;
//
// GoodHeuristic
//
// This class extends the Heuristic class, providing a reasonable
// implementation of the heuristic function method. The provided "good"
// heuristic function is admissible.
//
// Albert Nguyen -- 10/8/2019
//

// IMPORT ANY PACKAGES THAT YOU NEED.
// import java.util.*;


public class GoodHeuristic extends Heuristic {
	double smallestTimeDistanceRatio;
	int roadsExplored = 0;
	
	// Default constructor
	public GoodHeuristic() {
		this.destination = null;
		smallestTimeDistanceRatio = Integer.MAX_VALUE;
	}
    
	// Constructor with destination Location
	public GoodHeuristic(Location destination) {
		this.destination = destination;
		smallestTimeDistanceRatio = Integer.MAX_VALUE;
	}
	
	// heuristicValue -- Return the appropriate heuristic values for the
	// given search tree node. Note that the given Node should not be
	// modified within the body of this function.
	// Returns heuristic which is distance to destination multiplied by time cost from parent
	public double heuristicValue(Node thisNode) {
		// Get straight line distance from node to destination
		double x = Math.abs(thisNode.loc.longitude - destination.longitude);
		double y = Math.abs(thisNode.loc.latitude - destination.latitude);
		// get distance using Pythagorean theorem
		double distanceFromNodeToDestination = Math.sqrt((x * x) + (y * y));

		// Get straight line distance from parent to node
		x = Math.abs(thisNode.loc.longitude - thisNode.parent.loc.longitude);
		y = Math.abs(thisNode.loc.latitude - thisNode.parent.loc.latitude);
		double distanceFromParentToNode = Math.sqrt((x * x) + (y * y));;
		
		// travel/time cost from parent to current node
		double travelCostToNode = thisNode.partialPathCost - thisNode.parent.partialPathCost;
		
		// Update smallest time to distance ratio seen / think of this as the most ideal/fastest road seen
		smallestTimeDistanceRatio = Math.min(smallestTimeDistanceRatio, (travelCostToNode / distanceFromParentToNode));

		// This assumes the ratio of time/travel cost to distance for this node to destination is the same as the ratio from parent to this node
		// time1 / distance1 = time2 / distance2 THIS EQUATION WAS USED TO DERIVE ESTIMATED TRAVEL COST
		// estimatedCostToDest = distanceToDest * (time2 / distance2) where (time2 / distance2) is the smallestTimeDistanceRatio
		// Underestimate time:distance ratio starting by dividing by 10; it divides by less as more roads are explored then it just divides by 1.
		double estimatedTravelCostToDest = distanceFromNodeToDestination * (smallestTimeDistanceRatio / (10 - roadsExplored));
		
		// Increment roadsExplored to 9
		if (roadsExplored < 9) {
			roadsExplored++;
		}
	
		return estimatedTravelCostToDest;
	}

}
