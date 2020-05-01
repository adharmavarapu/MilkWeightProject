package application;
/**
 * HashTableADT.java created by Abhilash Dharmavarapu on Lenovo Thinkpad X1 Extreme in MilkWeightProject
 *
 * Author: Abhilash Dharmavarapu (dharmavarapu@wisc.edu)
 * Date: 04/26/2020
 *
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 001
 *
 * IDE: Eclipse IDE for Java Developers
 * Version: 2019-12 (4.14.0)
 * Build id: 20191212-1212
 *
 * Device: DHARMAVARAPU_X1EXTREME
 * OS: Windows 10 Pro
 * Version: 1903
 * OS Build: 18362.535
 *
 * List of Collaborators: Name, email.wisc.edu, lecture number
 *
 * Other Credits: describe other source (web sites or people)
 *
 * Known Bugs: describe known unresolved bugs here
 */

/**
 * Interface for creating a hashtable, ensures better group collaboration
 * 
 * @author Abhilash Dharmavarapu
 *
 * @param <K> Key type
 * @param <V> Value type
 */

public interface HashTableADT<K extends Comparable<K>, V> {
	// Add the key,value pair to the data structure and increase the number of keys.
	// If key is already in data structure, replace value with new value
	void insert(K key, V value);

	// If key is found,
	// remove the key,value pair from the data structure
	// decrease number of keys.
	// return true
	// If key is not found, return false
	boolean remove(K key);

	// Returns the value associated with the specified key
	// Does not remove key or decrease number of keys
	//
	V get(K key);

	// Returns the number of key,value pairs in the data structure
	int numKeys();

	// Returns the load factor threshold that was
	// passed into the constructor when creating
	// the instance of the HashTable.
	// When the current load factor is greater than or
	// equal to the specified load factor threshold,
	// the table is resized and elements are rehashed.
	public double getLoadFactorThreshold();

	// Returns the current load factor for this hash table
	// load factor = number of items / current table size
	public double getLoadFactor();

	// Return the current Capacity (table size)
	// of the hash table array.
	//
	// The initial capacity must be a positive integer, 1 or greater
	// and is specified in the constructor.
	//
	// REQUIRED: When the load factor threshold is reached,
	// the capacity must increase to: 2 * capacity + 1
	//
	// Once increased, the capacity never decreases
	public int getCapacity();

	// Sets all elements in table to null
	public void clear();

}
