package application;
/**
 * FarmTable.java created by Abhilash Dharmavarapu on Lenovo Thinkpad X1 Extreme in MilkWeightProject
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
 * Hash Table to store farm data
 * @author Abhilash Dharmavarapu, 
 *
 */
public class FarmTable implements HashTableADT<String, Farm>{
	
	private double loadFactorThreshold;
	private Farm[] table;
	private int size;
	
	public FarmTable() {
		loadFactorThreshold = 0.75;
		table =  new Farm[41];
		size = 0;
	}
	public Farm[] getTable() {
		return table;
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
		
	}
	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Farm get(String key) {
		return null;
	}
	
	@Override
	public int numKeys() {
		// TODO Auto-generated method stub
		return size;
	}
	@Override
	public double getLoadFactorThreshold() {
		return loadFactorThreshold;
	}
	@Override
	public double getLoadFactor() {
		return 1.0*size/table.length;
	}
	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}



}
