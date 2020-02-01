
import java.util.ArrayList;

public class MapBox {
	public ArrayList<Location> locations;
	
	// Constructor instantiates ArrayList object 
	public MapBox() {
		locations = new ArrayList<Location>();
	}
	
	public double Westmost() {
		// Start with impossible max longitude
		double minimumX = 181;
		// Goes through each location in list and return smallest longitude value/minimumX
		for(Location position : locations) {
			if(position.longitude < minimumX) {
				minimumX = position.longitude;
			}
		}
		return minimumX;
	}
	
	public double Eastmost() {
		// Start with impossible min longitude
		double maximumX = -181;
		// Goes through each location in list and return greatest longitude value/maximum X
		for (Location position : locations) {
			if (position.longitude > maximumX) {
				maximumX = position.longitude;
			}
		}
		return maximumX;
	}
	
	public double Southmost() {
		// Start with impossible max latitude
		double minimumY = 91;
		// Goes through each location in list and return smallest latitude value/minimumY
		for(Location position : locations) {
			if(position.latitude < minimumY) {
				minimumY = position.latitude;
			}
		}
		return minimumY;
	}
	
	public double Northmost() {
		// Start with impossible min latitude
		double maximumY = -91;
		// Goes through each location in list and return greatest latitude value/maximumY
		for(Location position : locations) {
			if(position.latitude > maximumY) {
				maximumY = position.latitude;
			}
		}
		return maximumY;
	}
	
	// Goes through each location in list and compares it to location that wants to be added. Duplicated will not be added
	public boolean recordLocation(Location location) {
		boolean newLocation = true;
		
		for(Location position : locations) {
			if(location.equals(position)) {
				newLocation = false;
				break;
			}
		}
		if(newLocation) {
			locations.add(location);
		}
		
		return newLocation;
	}
	
}
