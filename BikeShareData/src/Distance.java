import java.util.*;
import java.lang.*;
import java.io.*;

// https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
public class Distance {
	private double lat1, long1, lat2, long2;
	private int duration; // trip duration in seconds
	private final double RADIUS = 6371; // Earth's radius (km)
	private final double AVG_SPEED = 15.5; // km/hr
//	private static int count = 0;

	public Distance(double lat1, double long1, double lat2, double long2, int duration) {
		this.lat1 = lat1;
		this.long1 = long1;
		this.lat2 = lat2;
		this.long2 = long2;
		this.duration = duration;
	}

	// returns distance in kilometers
	public double distance() {
		if (lat1 == lat2 && long1 == long2) {
			// System.out.println("EQUAL");
			// count++;
			return (duration / 3600) * AVG_SPEED;
		}
	    double dLat = Math.toRadians(lat1-lat2);
	    double dLon = Math.toRadians(long1-long2);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	            Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lat1)) * 
	            Math.sin(dLon/2) * Math.sin(dLon/2); 
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
	    return RADIUS * c; 
//		double degreesLat = degreesToRadians(lat2 - lat1);
//		double degreesLon = degreesToRadians(long2 - long1);
//		double a = Math.sin(degreesLat / 2) * Math.sin(degreesLat / 2) + Math.cos(degreesToRadians(lat1))
//				* Math.cos(degreesToRadians(lat2)) * Math.sin(degreesLon / 2) * Math.sin(degreesLon / 2);
//		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//		double distance = RADIUS * c; // Distance in km
//		return distance;
	}

//	public static int getCount() {
//		return count;
//	}

	public double degreesToRadians(double degrees) {
		return degrees * (Math.PI / 180);
	}
}
