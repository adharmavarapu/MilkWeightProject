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
import java.util.Arrays;
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
	 * @return double array that contains the min(index = 0), max(index = 1),
	 *         avg(index = 2)
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
	 * Get total weight for a given month and year
	 * 
	 * @param month specific month to find total weight of
	 * @param year  specific year to find total weight of
	 * @return total weight by month and year
	 */
	public int getWeight(String month, String year) {
		int weightTotal = 0;
		for (int i = updateList.size() - 1; i >= 0; i--) {
			FarmUpdate u = updateList.get(i);
			String[] currDate = u.currDate.split("-");
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(year)
					&& Integer.parseInt(currDate[1]) == Integer.parseInt(month)) {
				weightTotal += u.currWeight;
			}
		}
		return weightTotal;
	}

	/**
	 * Get total weight for a given year
	 * 
	 * @param year specific year to find total weight of
	 * @return total weight by year
	 */
	public int getWeight(String year) {
		int weightTotal = 0;
		for (int i = updateList.size() - 1; i >= 0; i--) {
			FarmUpdate u = updateList.get(i);
			String[] currDate = u.currDate.split("-");
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(year)) {
				weightTotal += u.currWeight;
			}
		}
		return weightTotal;
	}

	/**
	 * Compute date for a given weight range
	 * 
	 * @param start start date for range
	 * @param end   end date for range
	 * @return total weight for that range
	 */
	public int getWeightRange(String start, String end) {
		int weightOverRange = 0;
		String[] startDate = start.split("-");
		String[] endDate = end.split("-");
		int startIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < updateList.size(); i++) {
			FarmUpdate u = updateList.get(i);
			String[] currDate = u.currDate.split("-");
			String[] prevDate = u.prevDate.split("-");
			System.out.println(" " + Integer.parseInt(endDate[0]) + " " + Integer.parseInt(endDate[1]) + " "
					+ Integer.parseInt(endDate[2]));
			System.out.println(" " + Integer.parseInt(currDate[0]) + " " + Integer.parseInt(currDate[1]) + " "
					+ Integer.parseInt(currDate[2]));
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(startDate[0])
					&& Integer.parseInt(currDate[1]) == Integer.parseInt(startDate[1])
					&& Integer.parseInt(currDate[2]) == Integer.parseInt(startDate[2])) {
				startIndex = i;
			}
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(endDate[0])
					&& Integer.parseInt(currDate[1]) == Integer.parseInt(endDate[1])
					&& Integer.parseInt(currDate[2]) == Integer.parseInt(endDate[2])) {
				System.out.println("try");
				endIndex = i;
			}
		}
		System.out.print("Start: " + startIndex + "End: " + endIndex);
		for (int i = startIndex; i <= endIndex; i++) {
			weightOverRange += updateList.get(i).currWeight;
		}
		return weightOverRange;

	}

	/**
	 * Remove an update for a given date
	 * 
	 * @param date date of update to remove
	 */
	public void remove(String date) {
		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];

		for (int i = 0; i < updateList.size(); i++) {
			FarmUpdate u = updateList.get(i);
			String[] currDate = u.currDate.split("-");
			String[] prevDate = u.prevDate.split("-");
			if (Integer.parseInt(currDate[0]) == Integer.parseInt(year)
					&& Integer.parseInt(currDate[1]) == Integer.parseInt(month)
					&& Integer.parseInt(currDate[2]) == Integer.parseInt(day)) {
				updateList.remove(u);
			}
			if (!prevDate[0].equals("Start") && Integer.parseInt(prevDate[0]) == Integer.parseInt(year)
					&& Integer.parseInt(prevDate[1]) == Integer.parseInt(month)
					&& Integer.parseInt(prevDate[2]) == Integer.parseInt(day)) {
				if (i == 0) {
					u.prevDate = "START";
					u.prevWeight = 0;
				} else {
					u.prevDate = updateList.get(i - 1).currDate;
					u.prevWeight = updateList.get(i - 1).currWeight;
				}
			}
		}
	}

	/**
	 * Update an existing data point
	 * 
	 * @param oldDate   oldDate of the existing data point
	 * @param newDate   newDate of the existing data point
	 * @param oldWeight updated oldWeight for the data point
	 * @param newWeight updated newWeight for the data point
	 */
	public void update(String oldDate, String newDate, int oldWeight, int newWeight) {

		for (FarmUpdate u : updateList) {
			String[] prevDate = u.prevDate.split("-");
			String[] currDate = u.currDate.split("-");

			String prevYear = prevDate[0];
			String prevMonth = prevDate[1];
			String prevDay = prevDate[2];

			String currYear = currDate[0];
			String currMonth = currDate[1];
			String currDay = currDate[2];

			if (Integer.parseInt(prevDate[0]) == Integer.parseInt(prevYear)
					&& Integer.parseInt(prevDate[1]) == Integer.parseInt(prevMonth)
					&& Integer.parseInt(prevDate[2]) == Integer.parseInt(prevDay)) {
				if (Integer.parseInt(currDate[0]) == Integer.parseInt(currYear)
						&& Integer.parseInt(currDate[1]) == Integer.parseInt(currMonth)
						&& Integer.parseInt(currDate[2]) == Integer.parseInt(currDay)) {

				}
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
