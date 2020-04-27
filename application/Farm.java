package application;
/**
 * Farm.java created by Abhilash Dharmavarapu on Lenovo Thinkpad X1 Extreme in MilkWeightProject
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

import java.util.ArrayList;
import java.util.List;

/**
 * Farm data object to have certain features of the farms
 * 
 * @author Abhilash Dharmavarapu,
 *
 */
public class Farm {
	/**
	 * Inner class containing update data objects
	 * 
	 * @author Abhilash Dharmavarapu
	 *
	 */
	private class FarmUpdate {
		private String prevDate; // date previous to update
		private String currDate; // date of update
		private int prevWeight; // weight previous to update
		private int currWeight; // weight of update

		/**
		 * Constructor for a farm update
		 * 
		 * @param pMonth  month previous to update
		 * @param cMonth  month of update
		 * @param pWeight weight previous to update
		 * @param cWeight weight of update
		 */
		public FarmUpdate(String pDate, String cDate, int pWeight, int cWeight) {
			prevDate = pDate;
			currDate = cDate;
			prevWeight = pWeight;
			currWeight = cWeight;
		}

		/**
		 * Gets the difference of weight
		 * 
		 * @return the difference of weight, can be either negative(decrease) or
		 *         positive(increase)
		 */
		public int getWeightDifference() {
			return currWeight - prevWeight;
		}
	}

	private String farmID; // Unique ID to farm
	private int milkWeight; // Milkweight of farm
	private List<FarmUpdate> updateList; // List of changes to farm

	/**
	 * Constructor for farm
	 * 
	 * @param id     unique id of farm
	 * @param weight milk weight of farm
	 * @param date   date of farm creation
	 */
	public Farm(String id, int weight, String date) {
		farmID = id;
		milkWeight = weight;
		updateList = new ArrayList<FarmUpdate>();
		updateList.add(new FarmUpdate("Start", date, 0, milkWeight));

	}

	/**
	 * update farm and store changes
	 * 
	 * @param date          date of update
	 * @param newMilkWeight new value of weight
	 */
	public void updateFarm(String date, int newMilkWeight) {
		FarmUpdate prevUpdate = updateList.get(updateList.size() - 1);
		updateList.add(new FarmUpdate(prevUpdate.currDate, date, milkWeight, newMilkWeight));
		milkWeight = newMilkWeight;
	}

	/**
	 * Get the min, max, and avg milk weight of farm within a certain month and year
	 * 
	 * @param month month to filter for
	 * @param year  year to filter for
	 * @return double array that contains the min(0), max(1), avg(1)
	 */
	public double[] getMinMaxAvg(String month, String year) {
		int sum = 0;
		int count = 0;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (FarmUpdate u : updateList) {
			String[] currDate = u.currDate.split("-");
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(year)
					&& Integer.parseInt(currDate[1]) == Integer.parseInt(month)) {
				sum += u.currWeight;
				count++;
				if (u.currWeight > max) {
					max = u.currWeight;
				}
				if (u.currWeight < min) {
					min = u.currWeight;
				}
			}
		}
		return new double[] { min, max, (1.0 * sum / count) };
	}

	/**
	 * W
	 * @param oldDate
	 * @param newDate
	 * @param oldWeight
	 * @param newWeight
	 */
	public void update(String oldDate, String newDate, int oldWeight, int newWeight) {
		for (FarmUpdate u : updateList) {
			if(oldDate.equals(u.prevDate) && newDate.equals(u.currDate)) {
				u.prevWeight = oldWeight;
				u.currWeight = newWeight;
			}
		}
	}
	
	/**
	 * get id of the farm
	 * 
	 * @return String representation of id
	 */
	public String getID() {
		return farmID;
	}

	/**
	 * Get the recent date of the last update
	 * 
	 * @return String representation of date
	 */
	public String getDate() {
		return updateList.get(updateList.size() - 1).currDate;
	}

	/**
	 * Get the recent decrease/increase of the last update
	 * 
	 * @return integer, negative -> decay, positive -> growth
	 */
	public int getDifference() {
		return updateList.get(updateList.size() - 1).getWeightDifference();
	}
	/**
	 * Get the current milk weight of the farm
	 * 
	 * @return milk weight in integer form
	 */
	public int getWeight() {
		return milkWeight;
	}

	/**
	 * Get the updates of the farm
	 * 
	 * @return list of updates of the farm
	 */
	public List<FarmUpdate> getUpdates() {
		return updateList;
	}
}
