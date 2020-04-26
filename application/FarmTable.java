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
public class FarmTable implements HashTableADT<String, Integer>{
	
	private double loadFactorThreshold;
	private Farm[] table;
	private int size;
	
	public FarmTable() {
		loadFactorThreshold = 0.75;
		table =  new Farm[41];
		size = 0;
	}
	@Override
	public void insert(String key, Integer value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numKeys() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLoadFactorThreshold() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLoadFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}


}
