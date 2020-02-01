//
// Ezero
//
// This class provides a "main" method that acts as a driver program
// for evaluating bounding box implementations. A file of locations,
// consisting of names and coordinate pairs, is read. If a location
// name is duplicated in the list, the first location is recorded
// and the duplicate discarded. The name of the duplicate location
// is reported. The bounding box that surrounds all read locations
// is then output, and this is followed by the output of all of
// the locations, with no duplicates listed. Program output is
// written to the standard output stream.
//
// David Noelle -- Mon Sep  3 16:35:51 PDT 2018
//


import java.io.*;


public class Ezero {

	public static void main(String[] args) {
		MapBox map = new MapBox();
		String locationFilename = "locations.dat";

		System.out.println("BOUNDING BOX TEST");
		System.out.flush();
		// Read the locations into the allocated MapBox object ...
		try {
			// Open file of locations ...
			File locFile = new File(locationFilename);
			if (locFile.exists() && locFile.canRead()) {
				// Convert File to a BufferedReader ...
				FileInputStream locFileIn = new FileInputStream(locFile);
				InputStreamReader locISReader = new InputStreamReader(locFileIn);
				BufferedReader locBufferedReader = new BufferedReader(locISReader);
				// Read all of the locations in the file into the
				// allocted MapBox object. First, allocate storage
				// for the first location to be read ...
				Location loc = new Location();
				// Read one location at a time ...
				while (loc.read(locBufferedReader)) {
					// Record location in the MapBox object ...
					if (!(map.recordLocation(loc))) {
						// A false return value from "recordLocation"
						// means that the location is a duplicate.
						System.out.printf("Duplicate Location:  %s\n", loc.name);
					} else {
						// Allocate storage for the next location ...
						loc = new Location();
					}
				}
				// Output results ...
				// Output size of bounding box ...
				System.out.println("The Bounding Box:");
				System.out.printf("  West Edge = %f\n", map.Westmost());
				System.out.printf("  East Edge = %f\n", map.Eastmost());
				System.out.printf("  North Edge = %f\n", map.Northmost());
				System.out.printf("  South Edge = %f\n", map.Southmost());
				System.out.println("The Locations:");
				for (Location outloc : map.locations) {
					outloc.write(System.out, true);
					System.out.println("");
				}
			} else {
				System.err.println("ERROR: Could not open file for reading.");
			}
		} catch (IOException e) {
			// Something went wrong ...
			System.err.println("ERROR: IO exception thrown.");
		}
		// Done ...
		System.out.println("BOUNDING BOX TEST COMPLETE");
	}
}
