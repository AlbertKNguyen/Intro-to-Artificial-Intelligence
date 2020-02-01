//
// Location
//
// This class implements a place on a map. Each location
// includes a textual name and a pair of Cartesian
// coordinates. Note that textual names are assumed to be
// unique; two locations are considered the same if they
// have the same name.
//
// David Noelle -- Mon Sep  3 16:23:17 PDT 2018
//


import java.io.*;
import java.util.*;


public class Location {
	public String name = "";
	public double longitude = 0.0;
	public double latitude = 0.0;

	// Default constructor ...
	public Location() {
		this.name = "";
		this.longitude = 0.0;
		this.latitude = 0.0;
	}

	// Constructor with location name specified ...
	public Location(String name) {
		this();
		this.name = name;
	}

	// Constructor with location name and coordinates specified ...
	public Location(String name, double longitude, double latitude) {
		this(name);
		this.longitude = longitude;
		this.latitude = latitude;
	}

	// equals -- Return true if and only if this location has the same name
	// as the argument location.
	public boolean equals(Location loc) {
		return (loc.name.equals(this.name));
	}

	// read -- Read a location description from the given stream into this
	// object. At minimum, a name must be read from the stream. Optionally,
	// coordinates may also be specified as a pair of double precision
	// floating point numbers. Return true if at least a name was read and
	// false otherwise.
	public boolean read(BufferedReader str) {
		try {
			String thisLine = str.readLine();
			if (thisLine == null)
				// No more input, at all ...
				return (false);
			Scanner inScanner = new Scanner(thisLine);
			inScanner.useDelimiter("\\s+");
			if (inScanner.hasNext()) {
				// There is something to read ...
				name = inScanner.next();
				if (inScanner.hasNextDouble()) {
					// There is a longitude to read ...
					longitude = inScanner.nextDouble();
					if (inScanner.hasNextDouble()) {
						// There is a latitude to read ...
						latitude = inScanner.nextDouble();
					}
				}
				inScanner.close();
				// At least a name was successfully read ...
				return (true);
			} else {
				inScanner.close();
				// Did not even read a name ...
				return (false);
			}
		} catch (IOException e) {
			// Something went wrong ...
			return (false);
		}
	}

	// write -- Write the name of this location to the given stream. If the
	// "showCoords" argument is true, then also output the Cartesian
	// coordinates of this location, separated by blanks, on the same line.
	public void write(OutputStream str, boolean showCoords) {
		PrintWriter out = new PrintWriter(str, true);
		out.printf("%s", name);
		if (showCoords) {
			out.printf(" %f %f", longitude, latitude);
		}
	}

}
