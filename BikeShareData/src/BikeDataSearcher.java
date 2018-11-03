
// Katherine Sullivan
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Map.Entry;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.SortedSet;
import java.io.FileReader;
import java.util.Comparator;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BikeDataSearcher {
	public static void main(String[] args) {
		Map<String, Integer> startCounter = new TreeMap<String, Integer>();
		Map<String, Integer> stopCounter = new TreeMap<String, Integer>();
		Map<Integer, Integer> durations = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> distances = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> ridersPerMonth = new TreeMap<Integer, Integer>();
		double distance = 0;
		double numDistances = 0;
		int count = 0;
		int regularRiders = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("BikeData.txt")));
			br.readLine();
			String str;
			while ((str = br.readLine()) != null) {
				count++;
				String[] splitStr = str.trim().split("\t");
				Integer duration = Integer.parseInt(splitStr[1]);
				String startID = splitStr[4];
				String stopID = splitStr[7];
				int month = Integer.parseInt(splitStr[2].substring(5, 7));
				if (!ridersPerMonth.containsKey(month)) {
					ridersPerMonth.put(month, 1);
				} else {
					ridersPerMonth.put(month, ridersPerMonth.get(month) + 1);
				}
				if (durations.containsKey(duration)) {
					durations.put(duration, durations.get(duration) + 1);
				} else {
					durations.put(duration, 1);
				}
				if (!startID.equals("") && startCounter.containsKey(startID)) {
					startCounter.put(startID, startCounter.get(startID) + 1);
				} else if (!startID.equals("")) {
					startCounter.put(startID, 1);
				}
				if (!stopID.equals("") && stopCounter.containsKey(stopID)) {
					stopCounter.put(stopID, stopCounter.get(stopID) + 1);
				} else if (!stopID.equals("")) {
					stopCounter.put(stopID, 1);
				}
				if (splitStr[13] != "Walk-up") {
					regularRiders++;
				}
				try {
//					System.out.println(splitStr[5] + " " + splitStr[6] + " " + splitStr[8] + " " + splitStr[9]);
					if (Double.valueOf(splitStr[5]) != 0 && Double.valueOf(splitStr[6]) != 0
							&& Double.valueOf(splitStr[8]) != 0 && Double.valueOf(splitStr[9]) != 0) {
						Distance dist = new Distance(Double.valueOf(splitStr[5]), Double.valueOf(splitStr[6]),
								Double.valueOf(splitStr[8]), Double.valueOf(splitStr[9]),
								Integer.parseInt(splitStr[1]));
						distance += dist.distance();
						numDistances++;
					}

				} catch (NumberFormatException e) {
				}

			}
//			for (String key : startCounter.keySet()) {
//				System.out.println(key + " " + startCounter.get(key));
//			}
//			System.out.println();
//			for (String key : stopCounter.keySet()) {
//				System.out.println(key + " " + stopCounter.get(key));
//			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
//		System.out.println("COUNT: " + Distance.getCount());
		for (Entry<Integer,Integer> e : ridersPerMonth.entrySet()) {
			System.out.println("Month: " + e.getKey() + " Riders: " + e.getValue());
		}
		System.out.println("REGULAR RIDERS: " + regularRiders);
		System.out.println("AVERAGE DISTANCE: " + (double) (distance / numDistances) + " Kilometers");

		Set<Entry<String, Integer>> sortedStartSet = sortByValue(startCounter);
		Set<Entry<String, Integer>> sortedStopSet = sortByValue(stopCounter);
		Set<Entry<Integer, Integer>> sortedDurations = sortIntsByValue(durations);

		System.out.println("Most Popular Starts:");
		for (Entry<String, Integer> mapping : sortedStartSet) {
			System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
		}

		System.out.println("\nMost Popular Stops:");
		for (Entry<String, Integer> mapping : sortedStopSet) {
			System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
		}

		System.out.println("Durations:");
		for (Entry<Integer, Integer> duration : sortedDurations) {
			System.out.println(duration.getKey() + " ==> " + duration.getValue());
		}

//		Read more: http://www.java67.com/2015/01/how-to-sort-hashmap-in-java-based-on.html#ixzz5Ugbd3uv5

	}

	public static Set<Entry<String, Integer>> sortByValue(Map<String, Integer> map) {
		Set<Entry<String, Integer>> sortedSet = map.entrySet();

		// define custom comparator to compare by value instead of by key
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				{
					Integer int1 = e1.getValue();
					Integer int2 = e2.getValue();
					return int2.compareTo(int1);
				}
			}
		};

		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(sortedSet);

		Collections.sort(listOfEntries, valueComparator);

		LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());

		for (Entry<String, Integer> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}

		Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

//		for(Entry<String, Integer> mapping : entrySetSortedByValue) { 
//			System.out.println(mapping.getKey() + " ==> " + mapping.getValue()); 
//			}

		return entrySetSortedByValue;
	}

	public static Set<Entry<Integer, Integer>> sortIntsByValue(Map<Integer, Integer> map) {
		Set<Entry<Integer, Integer>> sortedSet = map.entrySet();

		// define custom comparator to compare by value instead of by key
		Comparator<Entry<Integer, Integer>> valueComparator = new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> e1, Entry<Integer, Integer> e2) {
				{
					Integer int1 = e1.getValue();
					Integer int2 = e2.getValue();
					return int2.compareTo(int1);
				}
			}
		};

		List<Entry<Integer, Integer>> listOfEntries = new ArrayList<Entry<Integer, Integer>>(sortedSet);

		Collections.sort(listOfEntries, valueComparator);

		LinkedHashMap<Integer, Integer> sortedByValue = new LinkedHashMap<Integer, Integer>(listOfEntries.size());

		for (Entry<Integer, Integer> entry : listOfEntries) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}

		Set<Entry<Integer, Integer>> entrySetSortedByValue = sortedByValue.entrySet();

//		for(Entry<String, Integer> mapping : entrySetSortedByValue) { 
//			System.out.println(mapping.getKey() + " ==> " + mapping.getValue()); 
//			}

		return entrySetSortedByValue;
	}

//	static <K,V extends Comparable<? super V>>
//	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
//	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
//	        new Comparator<Map.Entry<K,V>>() {
//	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
//	                int res = e1.getValue().compareTo(e2.getValue());
//	                return res != 0 ? res : 1;
//	            }
//	        }
//	    );
//	    sortedEntries.addAll(map.entrySet());
//	    return sortedEntries;
//	}

	public static String toString(String[] arr) {
		String result = "[ ";
		for (int i = 0; i < arr.length; i++) {
			result += arr[i] + ", ";
		}
		result += "]";
		return result;
	}

}
