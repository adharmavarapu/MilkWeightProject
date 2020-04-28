package application;

import java.util.ArrayList;

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
     * @param key-   To store the key of the Key-Value pair.
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
    loadFactorThreshold = 0.75;
    size = 0;
    bucketArray = new ArrayList<>();
    size = 0;

  }

  /**
   * Creates a new ArrayList of HashNodes. Also initializes the size, loadFactor and current
   * capacity.
   *
   * @param initialCapacity-     The current capacity of the table.
   *
   * @param loadFactorThreshold- The value at which the table needs to rehash itself.
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

  public Farm[] getTable() {
    return (Farm[]) bucketArray.toArray();
  }

  /**
   * Hashes the key based off the hashCode from Java and the table size
   * 
   * @param key         K key to hash
   * @param tableLength int current table size to hash
   * @return
   */
  private int hash(String key, int tableLength) {
    return Math.abs(key.hashCode()) % tableLength;
  }

  @Override
  public void insert(String key, Farm value) {
    if (key != null) {
      // Gets the index, through the hash code for the key.
      int bucketIndex = Math.abs(key.hashCode() % numBuckets);
      HashNode<String, Farm> head = bucketArray.get(bucketIndex);// Head of the chain.
      while (head != null) {
        if (head.key.equals(key)) {// Update the value, since key already exists.
          head.value = value;
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

  @Override
  public boolean remove(String key) {
    if (key != null) {

      // Gets the index, through the hash code for the key.

      int bucketIndex = Math.abs(key.hashCode() % numBuckets);

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

  @Override
  public Farm get(String key) {
    if (key != null) {

      int bucketIndex = Math.abs(key.hashCode() % numBuckets);// Find head of chain for given key
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

  @Override
  public int numKeys() {
    return size;
  }

  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  @Override
  public double getLoadFactor() {
    return 1.0 * size / numBuckets;
  }

  @Override
  public int getCapacity() {
    return numBuckets;
  }



}
