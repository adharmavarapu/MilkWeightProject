package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * FarmTable.java created by Abhilash Dharmavarapu on Lenovo Thinkpad X1 Extreme in
 * MilkWeightProject
 *
 * Author: Abhilash Dharmavarapu (dharmavarapu@wisc.edu) Date: 04/26/2020
 *
 * Course: CS400 Semester: Spring 2020 Lecture: 001
 *
 * IDE: Eclipse IDE for Java Developers Farmersion: 2019-12 (4.14.0) Build id: 20191212-1212
 *
 * Device: DHARMAVARAPU_X1EXTREME OS: Windows 10 Pro Farmersion: 1903 OS Build: 18362.535
 *
 * List of Collaborators: Name, email.wisc.edu, lecture number
 * Shreyam Taneja, staneja3@wisc.edu, 001
 * Nishit Saraf, nsaraf@wisc.edu, 001
 *
 * Other Credits: describe other source (web sites or people)
 *
 * Known Bugs: none
 */

/**
 * Hash Table to store farm data
 * 
 * @author Abhilash Dharmavarapu,
 *
 */
public class FarmTable implements HashTableADT<String, Farm> {

	private class HashNode<String, Farm> {
		String key;// To store the key.
		Farm value;// To Store the Farmalue.

		HashNode<String, Farm> next;// Reference to the next node.

		/**
		 * Updates and initializes the HashNode object.
		 *
		 * @param key- To store the key of the Key-Value pair.
		 * @param value- To store the value of the Farmalue-Key pair.
		 */
		private HashNode(String key, Farm value) {
			this.key = key;
			this.value = value;
		}
	}

	private ArrayList<HashNode<String, Farm>> bucketArray;// bucketArray is used to store array of
															// chains
	private int numBuckets;// Current capacity of array list

	private double loadFactorThreshold;
	private int size;

	public FarmTable() {
		this(10, .75);
	}

	/**
	 * Creates a new ArrayList of HashNodes. Also initializes the size, loadFactor
	 * and current capacity.
	 *
	 * @param initialCapacity- The current capacity of the table.
	 *
	 * @param loadFactorThreshold- The value at which the table needs to rehash
	 *        itself.
	 */
	public FarmTable(int initialCapacity, double loadFactorThreshold) {
		bucketArray = new ArrayList<>();
		size = 0;
		numBuckets = initialCapacity;// Updates the instance variables.

		this.loadFactorThreshold = loadFactorThreshold;// Updates the threshold.

		for (int i = 0; i < numBuckets; i++) {// Initializes the values till the end of the ArrayList.
			bucketArray.add(null);
		}
	}

	/**
	 * Get the table of the hashtable
	 * 
	 * @return table of farm objects
	 */
	private Farm[] getTable() {
		Farm[] t = new Farm[size];
		int index = 0;
		for (int i = 0; i < bucketArray.size(); i++) {
			HashNode bucket = bucketArray.get(i);
			while (bucket != null) {
				t[index] = (Farm) bucket.value;
				bucket = bucket.next;
				index++;
			}
		}
		return t;
	}

	/**
	 * Add the key,value pair to the data structure and increase the number of keys.
	 * If key is already in data structure, replace value with new value
	 * 
	 * @param key   the id of the id-farm to be inserted
	 * @param value the farm of the id-farm pair to be inserted
	 **/
	@Override
	public void insert(String key, Farm value) {
		if (key != null) {
			// Gets the index, through the hash code for the key.
			int bucketIndex = Math.abs(key.hashCode() % bucketArray.size());
			HashNode<String, Farm> head = bucketArray.get(bucketIndex);// Head of the chain.
			while (head != null) {
				if (head.key.equals(key)) {// Update the value, since key already exists.
					head.value.updateFarm(value.getDate(), value.getWeight());
					// head.value = value;
					return;
				}
				head = head.next;// Updates the head pointer till it reaches null.
			}
			size++;
			// Gets the index, through the hash code for the key.

			head = bucketArray.get(bucketIndex);// Gets the head of the chain.

			HashNode<String, Farm> newNode = new HashNode<String, Farm>(key, value);// Makes a new node.

			newNode.next = head;// Makes a new node next point to the head.

			bucketArray.set(bucketIndex, newNode);// Adds the new node at the particular ArrayList index.

			if ((double) (size / numBuckets) >= loadFactorThreshold) {// Checks if Rehashing is required
																		// or not.

				ArrayList<HashNode<String, Farm>> temp = bucketArray;// Makes a temporary Arraylist.

				bucketArray = new ArrayList<>();// New ArrayList.

				numBuckets = 2 * numBuckets + 1;// Doubles the size and adds 1
				size = 0;
				for (int i = 0; i < numBuckets; i++) {// Initializes rest of the ArrayList values.
					bucketArray.add(null);
				}
				for (HashNode<String, Farm> headNode : temp) {// For Each Loop, updates the HeadNode.
					while (headNode != null) {
						insert(headNode.key, headNode.value);// Recursive case.

						headNode = headNode.next;// Updates HeadNode.
					}
				}
			}
		}
	}

	/**
	 * If key is found, remove the key,value pair from the data structure decrease
	 * number of keys.
	 * 
	 * @param key the id of the id-farm pair to remove
	 * @return If key is found, return true. If key is not found, return false
	 */
	@Override
	public boolean remove(String key) {
		if (key != null) {

			// Gets the index, through the hash code for the key.

			int bucketIndex = Math.abs(key.hashCode() % bucketArray.size());

			HashNode<String, Farm> head = bucketArray.get(bucketIndex); // Get head of chain

			HashNode<String, Farm> prev = null;// Search for key in its chain
			while (head != null) {
				if (head.key.equals(key)) { // If Key is found
					break;
				}

				prev = head;// Else keep moving in chain
				head = head.next;
			}

			if (head == null) {// If key was not there
				return false;
			}
			size--; // Updates size

			if (prev != null) {
				prev.next = head.next;// Updates the pointer.
			} else {
				bucketArray.set(bucketIndex, head.next);// Updates the ArrayList.
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the value associated with the specified key Does not remove key or
	 * decrease number of keys
	 * 
	 * @param key the id of the id-farm pair to get
	 * @return returns the farm of the id-farm pair, or returns null if it does not
	 *         exist
	 **/
	@Override
	public Farm get(String key) {
		if (key != null) {

			int bucketIndex = Math.abs(key.hashCode() % bucketArray.size());// Find head of chain for given key
			HashNode<String, Farm> head = bucketArray.get(bucketIndex);

			while (head != null) {// Search key in chain
				if (head.key.equals(key)) {
					return head.value;
				}
				head = head.next;// Updates the head pointer.
			}
		}
		return null;
	}

	/**
	 * Compute the date range analysis for each farm and output to existing listView
	 * 
	 * @param start   start date of range
	 * @param end     end date of range
	 * @param file    file to output to
	 * @param results listView to write to
	 */
	public void dateRangeReport(String start, String end, ListView results) {
		results.getItems().clear();
		Farm[] table = this.getTable();
		int total = 0;
		// Get Total Weight for calculating share
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				total += f.getWeightRange(start, end);
			}
		}
		// Compute Share and Weight and write to file
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeightRange(start, end)) / total;
				results.getItems().add(new Label(
						f.getID() + " : " + "Weight = " + f.getWeightRange(start, end) + " Share = " + percent + "%"));
			}
		}
	}

	/**
	 * Compute the monthly report for each farm and output to existing listView
	 * 
	 * @param year    year to analyze
	 * @param month   month to analyze
	 * @param results listView to write to
	 */
	public void monthlyReport(String year, String month, ListView results) {
		results.getItems().clear();
		Farm[] table = this.getTable();
		int total = 0;
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				total += f.getWeight(month, year);
			}
		}
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeight(month, year)) / total;
				results.getItems().add(new Label(
						f.getID() + " : " + "Weight = " + f.getWeight(month, year) + " Share = " + percent + "%"));
			}
		}
	}

	/**
	 * Compute the farm report by month for a specific farm and output to existing
	 * listView
	 * 
	 * @param id      identification string for specific farm
	 * @param year    year to analyze
	 * @param results ListView to write to
	 */
	public void farmReport(String id, String year, ListView results) {
		results.getItems().clear();
		Farm f = this.get(id);
		if (year.length() == 0) {
			results.getItems().add(new Label("Please input a year to filter by."));
			return;
		}
		if (f == null) {
			results.getItems().add(new Label("The farm id does not exist in the data."));
			return;
		}
		for (int i = 1; i < 13; i++) {
			if (f != null) {
				double percent = (100.0 * f.getWeight(Integer.toString(i), year))
						/ getTotalWeight(year, Integer.toString(i));
				results.getItems().add(new Label("Month " + i + ": " + "Weight = "
						+ f.getWeight(Integer.toString(i), year) + " Share = " + percent + "%"));
			}
		}
	}

	/**
	 * Compute the annual report for each farm and output to existing listView
	 * 
	 * @param year    year of analysis
	 * @param results ListView to write to
	 */
	public void annualReport(String year, ListView results) {
		results.getItems().clear();
		Farm[] table = this.getTable();
		int total = 0;
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				total += f.getWeight(year);
			}
		}
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeight(year)) / total;
				results.getItems().add(
						new Label(f.getID() + " : " + "Weight = " + f.getWeight(year) + " Share = " + percent + "%"));
			}
		}
	}

	/**
	 * Get total weight for all farms for a given month and year
	 * 
	 * @param year  year for a specific sum
	 * @param month month for a specific sum
	 * @return total weight of all farms for given month and year
	 */
	public int getTotalWeight(String year, String month) {
		Farm[] table = this.getTable();
		int total = 0;
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				total += f.getWeight(month, year);
			}
		}
		return total;
	}

	/**
	 * Helper method to get all data of a farm by month within a specific year
	 * 
	 * @param id      Farm that you are looking for
	 * @param year    what year you want to observe monthly results in
	 * @param results results listView object to update with the results
	 */
	public void onFarmFilter(String id, String year, ListView results) {
		results.getItems().clear();
		Farm f = this.get(id);
		if (year.length() == 0) {
			results.getItems().add(new Label("Please input a year to filter by."));
			return;
		}
		if (f == null) {
			results.getItems().add(new Label("The farm id does not exist in the data."));
			return;
		}
		for (int i = 1; i < 13; i++) {
			double[] a = f.getMinMaxAvg(Integer.toString(i), year);
			HBox hb = new HBox();
			int min = (int) a[0];
			int max = (int) a[1];
			double avg = a[2];
			if (min != Integer.MAX_VALUE && max != Integer.MIN_VALUE && avg != Double.NaN) {
				Label analysis = new Label("Min: " + min + ", Max: " + max + ", Avg: " + avg);
				hb.getChildren().addAll(new Label("Month " + i + ": "), analysis);
				results.getItems().add(hb);
			}
		}
	}

	/**
	 * Helper method to get all the farms and data associated with period of time
	 * 
	 * @param month   month you are looking for
	 * @param year    year you are looking for
	 * @param results listView object to update with the results
	 */
	public void onMonthFilter(String month, String year, ListView results) {
		results.getItems().clear();
		Farm[] table = this.getTable();
		if (month.length() == 0) {
			results.getItems().add(new Label("Please input a month to filter by."));
			return;
		}
		if (year.length() == 0) {
			results.getItems().add(new Label("Please input a year to filter by."));
			return;
		}
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				HBox hb = new HBox();
				double[] a = f.getMinMaxAvg(month, year);
				int min = (int) a[0];
				int max = (int) a[1];
				if(min == Integer.MAX_VALUE) {
					min = 0;
				}
				if(max == Integer.MIN_VALUE) {
					max = 0;
				}
				double avg = a[2];
				Label analysis = new Label(f.getID() + ": "  + "Min: " + min + ", Max: " + max + ", Avg: " + avg);
				results.getItems().add(analysis);
			}
		}
	}

	/**
	 * Get each farm's total weight and share for a given month and year
	 * 
	 * @return listView containing total weight and shares for each farm
	 */
	public void getFarms(String month, String year, ListView lv) {
		lv.getItems().clear();
		Farm[] table = this.getTable();
		int total = 0;
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				total += f.getWeight(month, year);
			}
		}
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeight(month, year)) / total;
				Label farm = new Label(f.getID() + " => " + "Weight: " + f.getWeight(month, year) + " ,Share: "
						+ percent + "%" + ", Last Modified: " + f.getDate());
				lv.getItems().add(farm);
			}
		}
	}

	/**
	 * Sets all elements in table to null
	 */
	@Override
	public void clear() {
		Farm[] table = this.getTable();
		for (Farm f : table) {
			f = null;
		}
	}

	/**
	 * Returns the number of keys in hashtable(size)
	 * 
	 * @return integer amount of keys
	 */
	@Override
	public int numKeys() {
		return size;
	}

	/**
	 * Get the load factor threshold of hashtable
	 * 
	 * @return loadFactorThreshold in form of double
	 */
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}

	/**
	 * Get the current load factor of hashtable
	 * 
	 * @return size/number of buckets in form of double
	 */
	@Override
	public double getLoadFactor() {
		return 1.0 * size / numBuckets;
	}

	/**
	 * Get the current capacity of hashtable
	 * 
	 * @return number of buckets in form of int
	 */
	@Override
	public int getCapacity() {
		return numBuckets;
	}

}
