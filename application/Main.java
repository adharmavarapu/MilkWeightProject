package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The graphical user interface of the Milk Weight program
 * 
 * @author Abhilash Dharmavarapu, Atharva Kudkilwar
 *
 */
public class Main extends Application {
	private List<String> args;
	private static final String APP_TITLE = "Milk Weights";
	private FarmTable farmTable;

	/**
	 * Method that runs show primary Stage
	 * 
	 * @param primaryStage Stage passed down in launch of application
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		farmTable = new FarmTable();
		args = this.getParameters().getRaw();
		BorderPane root = new BorderPane();

		// Set left side of the main screen
		VBox leftButtons = new VBox();
		Button newData = new Button("", new Label("Add/Remove Data"));
		newData.setOnAction(e -> addRemoveDataWindow(primaryStage));
		Button updateData = new Button("", new Label("Update Data"));
		updateData.setOnAction(e -> updateDataWindow(primaryStage));
		leftButtons.getChildren().addAll(newData, updateData);
		leftButtons.setSpacing(15);
		root.setLeft(leftButtons);

		// Set Right side of the main screen
		VBox rightButtons = new VBox();
		Button weightDifference = new Button("", new Label("Recent Growth"));
		weightDifference.setOnAction(e -> weightDifferenceWindow(primaryStage));
		Button uploadData = new Button("", new Label("Upload Data"));
		uploadData.setOnAction(e -> uploadFileWindow(primaryStage));
		rightButtons.getChildren().addAll(weightDifference, uploadData);
		rightButtons.setSpacing(15);
		root.setRight(rightButtons);

		// Set bottom of the main screen
		Button viewData = new Button("", new Label("View Results"));
		viewData.setOnAction(e -> viewDataWindow(primaryStage));
		root.setBottom(viewData);
		BorderPane.setAlignment(viewData, Pos.BASELINE_CENTER);

		// Output Results button
		Button outputResults = new Button("", new Label("Output Results "));
		outputResults.setOnAction(e -> outputResults(primaryStage));
		BorderPane.setAlignment(outputResults, Pos.CENTER);

		root.setCenter(outputResults);
		// Finalize and set stage to main scene
		Scene mainScene = new Scene(root);
		primaryStage.setWidth(400);
		primaryStage.setHeight(150);
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private void outputResults(Stage primaryStage) {
		Stage oR = new Stage();
		oR.setTitle("Output Results");
		oR.initModality(Modality.APPLICATION_MODAL);
		oR.initOwner(primaryStage);
		BorderPane root = new BorderPane();

		ToggleGroup group = new ToggleGroup();

		RadioButton rb1 = new RadioButton("Farm Report");
		rb1.setToggleGroup(group);

		RadioButton rb2 = new RadioButton("Annual Report");
		rb2.setToggleGroup(group);

		RadioButton rb3 = new RadioButton("Monthly Report");
		rb3.setToggleGroup(group);

		RadioButton rb4 = new RadioButton("Date Range Report");
		rb4.setToggleGroup(group);

		HBox options = new HBox();
		options.getChildren().addAll(rb1, rb2, rb3, rb4);

		HBox hb0 = new HBox(); // HBox for farm ID line
		TextField farmID = new TextField();
		farmID.setPromptText("Farm ID");
		hb0.getChildren().addAll(new Label("Enter Farm ID: "), farmID);
		hb0.setVisible(false);

		HBox hb1 = new HBox(); // HBox for Previous Date line
		TextField year = new TextField();
		year.setPromptText("yyyy");
		hb1.getChildren().addAll(new Label("Year: "), year);
		hb1.setVisible(false);

		HBox hb2 = new HBox(); // HBox for New Date line
		TextField month = new TextField();
		month.setPromptText("mm");
		hb2.getChildren().addAll(new Label("Month:  "), month);
		hb2.setVisible(false);

		HBox hb3 = new HBox(); // HBox for Old Weight
		TextField startDate = new TextField();
		startDate.setPromptText("yyyy-mm-dd");
		hb3.getChildren().addAll(new Label("Start Date: "), startDate);
		hb3.setVisible(false);

		HBox hb4 = new HBox();
		TextField endDate = new TextField();
		endDate.setPromptText("yyyy-mm-dd");
		hb4.getChildren().addAll(new Label("End Date: "), endDate);
		hb4.setVisible(false);

		HBox hb5 = new HBox();
		TextField filePath = new TextField(); // HBox for new Weight
		filePath.setPromptText("file path");
		hb5.getChildren().addAll(new Label("File Path: "), filePath);

		rb1.setOnAction(e -> {
			hb0.setVisible(true);
			hb1.setVisible(true);
			hb2.setVisible(false);
			hb3.setVisible(false);
			hb4.setVisible(false);
		});
		rb2.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(true);
			hb2.setVisible(false);
			hb3.setVisible(false);
			hb4.setVisible(false);
		});
		rb3.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(true);
			hb2.setVisible(true);
			hb3.setVisible(false);
			hb4.setVisible(false);
		});
		rb4.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(false);
			hb2.setVisible(false);
			hb3.setVisible(true);
			hb4.setVisible(true);
		});

		Button bt = new Button("DONE"); // Done Button
		bt.setVisible(false);
		rb1.setOnAction(e -> {
			hb0.setVisible(true);
			hb1.setVisible(true);
			hb2.setVisible(false);
			hb3.setVisible(false);
			hb4.setVisible(false);
			bt.setVisible(true);
		});
		rb2.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(true);
			hb2.setVisible(false);
			hb3.setVisible(false);
			hb4.setVisible(false);
			bt.setVisible(true);
		});
		rb3.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(true);
			hb2.setVisible(true);
			hb3.setVisible(false);
			hb4.setVisible(false);
			bt.setVisible(true);
		});
		rb4.setOnAction(e -> {
			hb0.setVisible(false);
			hb1.setVisible(false);
			hb2.setVisible(false);
			hb3.setVisible(true);
			hb4.setVisible(true);
			bt.setVisible(true);
		});
		bt.setOnAction(e -> {
			File f = new File(filePath.getText());
			if (rb1.isSelected()) {
				try {
					farmReport(farmID.getText(), year.getText(), f);
					System.out.println("Done");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (rb2.isSelected()) {
				try {
					annualReport(year.getText(), f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (rb3.isSelected()) {
				try {
					monthlyReport(year.getText(), month.getText(), f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (rb4.isSelected()) {
				try {
					dateRangeReport(startDate.getText(), endDate.getText(), f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		VBox vb = new VBox();
		vb.getChildren().addAll(options, hb0, hb1, hb2, hb3, hb4, hb5, bt);

		vb.setSpacing(25);

		root.setCenter(vb);

		Scene sc = new Scene(root);
		oR.setScene(sc);
		oR.setHeight(500);
		oR.setWidth(450);
		oR.show();

	}

	private void dateRangeReport(String start, String end, File file) throws IOException {
		// TODO Auto-generated method stub
		Farm[] table = farmTable.getTable();
		FileWriter myWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(myWriter);
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeightRange(start, end)) / farmTable.computeSum();
				writer.write(f.getID() + " " + i + ": " + "Weight = " + f.getWeight(start, end) + " Share = "
						+ percent);
				writer.newLine();
			}
		}
		writer.close();
	}

	private void monthlyReport(String year, String month, File file) throws IOException {
		Farm[] table = farmTable.getTable();
		FileWriter myWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(myWriter);
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeight(month, year)) / farmTable.computeSum();
				writer.write(f.getID() + " " + i + ": " + "Weight = " + f.getWeight(month, year) + " Share = "
						+ percent);
				writer.newLine();
			}
		}
		writer.close();
	}

	private void annualReport(String year, File file) throws IOException {
		Farm[] table = farmTable.getTable();
		FileWriter myWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(myWriter);
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				double percent = (100.0 * f.getWeight(year)) / farmTable.computeSum();
				writer.write(f.getID() + " " + i + ": " + "Weight = " + f.getWeight(year) + " Share = "
						+ percent);
				writer.newLine();
			}
		}
		writer.close();
	}

	private void farmReport(String id, String year, File file) throws IOException {
		// TODO Auto-generated method stub
		FileWriter myWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(myWriter);
		for (int i = 1; i < 13; i++) {
			Farm f = farmTable.get(id);
			if (f != null) {
				double percent = (100.0 * f.getWeight(Integer.toString(i), year)) / farmTable.computeSum();
				writer.write("Month " + i + ": " + "Weight = " + f.getWeight(Integer.toString(i), year) + " Share = "
						+ percent);
				writer.newLine();
			}
		}
		writer.close();
	}

	/**
	 * Method to create a new window for viewing data
	 * 
	 * @param primaryStage parent stage to set modality
	 */
	private void viewDataWindow(Stage primaryStage) {
		Stage vD = new Stage();
		vD.setTitle("Data Viewer");
		vD.initModality(Modality.APPLICATION_MODAL);
		vD.initOwner(primaryStage);
		BorderPane root = new BorderPane();

		// Set the titles of headings of the window
		Label title1 = new Label("By Farm:");
		Label title2 = new Label("By Month-Year:");
		Label title3 = new Label("All Farm Data: ");
		title1.setStyle("-fx-font-weight: bold");
		title2.setStyle("-fx-font-weight: bold");
		title3.setStyle("-fx-font-weight: bold");
		HBox titleBox = new HBox();
		titleBox.getChildren().addAll(title1, title3, title2);
		titleBox.setAlignment(Pos.TOP_CENTER);
		titleBox.setSpacing(350);

		// Set the search by farm results section
		VBox byFarm = new VBox();
		HBox userInput1 = new HBox();
		TextField farmId = new TextField();
		farmId.setPromptText("Farm ID");
		farmId.setFocusTraversable(false);
		TextField year1 = new TextField();
		year1.setPromptText("Year: yyyy");
		year1.setFocusTraversable(false);
		Button go1 = new Button("Go");
		userInput1.getChildren().addAll(farmId, year1, go1);
		ListView results1 = new ListView();
		go1.setOnAction(e -> onFarmFilter(farmId.getText(), year1.getText(), results1));
		byFarm.getChildren().addAll(userInput1, results1);

		// Set the search by date results section
		VBox byMonth = new VBox();
		HBox userInput2 = new HBox();
		TextField month = new TextField();
		month.setPromptText("Month: mm");
		month.setFocusTraversable(false);
		TextField year2 = new TextField();
		year2.setPromptText("Year: yyyy");
		year2.setFocusTraversable(false);
		Button go2 = new Button("Go");
		userInput2.getChildren().addAll(month, year2, go2);
		ListView results2 = new ListView();
		go2.setOnAction(e -> onMonthFilter(month.getText(), year2.getText(), results2));
		byMonth.getChildren().addAll(userInput2, results2);

		// Set the filter of all farms section
		VBox allFarms = new VBox();
		HBox userInput3 = new HBox();
		TextField month2 = new TextField();
		month2.setPromptText("Month: mm");
		month2.setFocusTraversable(false);
		TextField year3 = new TextField();
		year3.setPromptText("Year: yyyy");
		year3.setFocusTraversable(false);
		Button filter = new Button("Filter");
		ListView allFarmsList = getFarms();
		filter.setOnAction(e -> getFarms(month2.getText(), year3.getText(), allFarmsList));
		userInput3.getChildren().addAll(month2, year3, filter);
		allFarms.getChildren().addAll(userInput3, allFarmsList);

		// Fix alignment and set each search to their section
		BorderPane.setAlignment(titleBox, Pos.CENTER);
		root.setTop(titleBox);
		root.setLeft(byFarm);
		root.setCenter(allFarms);
		root.setRight(byMonth);

		// Finalize properties of window and scene
		Scene sc = new Scene(root);
		vD.setScene(sc);
		vD.setHeight(500);
		vD.setWidth(1200);
		vD.show();
	}

	/**
	 * Helper method to get all the farms in the list view GUI object
	 * 
	 * @return listView that has all farms and shares
	 */
	private ListView getFarms() {
		ListView lv = new ListView();
		Farm[] table = farmTable.getTable();
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				System.out.println(f.getUpdates().toString());
				double percent = (100.0 * f.getWeight()) / farmTable.computeSum();
				Label farm = new Label(f.getID() + " => " + "Weight: " + f.getWeight() + " ,Share: " + percent + "%"
						+ ", Last Modfified: " + f.getDate());
				lv.getItems().add(farm);
			}
		}
		return lv;
	}

	/**
	 * 
	 * @return
	 */
	private void getFarms(String month, String year, ListView lv) {
		lv.getItems().clear();
		Farm[] table = farmTable.getTable();
		for (int i = 0; i < table.length; i++) {
			Farm f = table[i];
			if (f != null) {
				if (f.getWeight(month, year) == 0) {
					lv.getItems().add(new Label("Farm was not updated in this period of time"));
				}
				double percent = (100.0 * f.getWeight(month, year)) / farmTable.computeSum();
				Label farm = new Label(f.getID() + " => " + "Weight: " + f.getWeight(month, year) + " ,Share: "
						+ percent + "%" + ", Last Modfified: " + f.getDate());
				lv.getItems().add(farm);
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
	private void onMonthFilter(String month, String year, ListView results) {
		results.getItems().clear();
		Farm[] table = farmTable.getTable();
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
				System.out.println(Arrays.toString(a));
				int min = (int) a[0];
				int max = (int) a[1];
				double avg = a[2];
				if (min != Integer.MAX_VALUE && max != Integer.MIN_VALUE && avg != Double.NaN) {
					Label farm = new Label(f.getID() + ": ");
					Label analysis = new Label("Min: " + min + ", Max: " + max + ", Avg: " + avg);
					hb.getChildren().addAll(farm, analysis);
					results.getItems().add(hb);
				}
			}
		}
	}

	/**
	 * Helper method to get all data of a farm by month within a specific year
	 * 
	 * @param id      Farm that you are looking for
	 * @param year    what year you want to observe monthly results in
	 * @param results results listView object to update with the results
	 */
	private void onFarmFilter(String id, String year, ListView results) {
		results.getItems().clear();
		Farm f = farmTable.get(id);
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
	 * Method to create a new window for seeing weight difference(most recent
	 * increase or decrease)
	 * 
	 * @param primaryStage parent stage to set modality
	 */
	private void weightDifferenceWindow(Stage primaryStage) {
		// CREATE WINDOW FOR WEIGHT DIFFERENCE
		Stage wD = new Stage();
		wD.setTitle("Recent Growth");
		wD.initModality(Modality.APPLICATION_MODAL);
		wD.initOwner(primaryStage);

		BorderPane root = new BorderPane();
		Scene main = new Scene(root);
		// For prompt and text input
		HBox hb = new HBox();
		TextField idPrompt = new TextField();
		idPrompt.setPromptText("[Unique String ID]");
		idPrompt.setFocusTraversable(false);
		hb.getChildren().addAll(new Label("Enter Farm ID: "), idPrompt);

		Button bt = new Button("DONE"); // Done Button
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String ID = idPrompt.getText();
				// GET MOST RECENT WEIGHT DIFFERNECE AND GO TO
				Farm f = farmTable.get(ID);
				// NEW WINDOW DISPLAYING DIFFERENCE
				Alert success = new Alert(AlertType.CONFIRMATION, "Most recent growth/decay is: " + f.getDifference(),
						ButtonType.OK);
				success.show();
				wD.close();
			}
		});
		root.setTop(hb);
		root.setBottom(bt);
		hb.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(bt, Pos.BASELINE_CENTER);
		wD.setHeight(125);
		wD.setWidth(300);
		wD.setScene(main);
		wD.show();
	}

	/**
	 * Method to create a new window for updating existing data
	 * 
	 * @param primaryStage parent stage to set modality
	 */
	private void updateDataWindow(Stage primaryStage) {
		// CREATE WINDOW FOR UPDATE DATA
		Stage uD = new Stage();
		uD.setTitle("Update Existing Data");
		uD.initModality(Modality.APPLICATION_MODAL);
		uD.initOwner(primaryStage);

		BorderPane root = new BorderPane();

		HBox hb0 = new HBox(); // HBox for farm ID line
		TextField farmID = new TextField();
		farmID.setPromptText("Farm ID");
		hb0.getChildren().addAll(new Label("Enter Farm ID: "), farmID);

		HBox hb1 = new HBox(); // HBox for Previous Date line
		TextField oldDate = new TextField();
		oldDate.setPromptText("yyyy-mm-dd");
		hb1.getChildren().addAll(new Label("Enter Previous Date: "), oldDate);

		HBox hb2 = new HBox(); // HBox for New Date line
		TextField newDate = new TextField();
		newDate.setPromptText("yyyy-mm-dd");
		hb2.getChildren().addAll(new Label("Enter New Date: "), newDate);

		HBox hb3 = new HBox(); // HBox for Old Weight
		TextField oldWeight = new TextField();
		oldWeight.setPromptText("Enter Weight");
		hb3.getChildren().addAll(new Label("Enter Previous Weight: "), oldWeight);

		HBox hb4 = new HBox();
		TextField newWeight = new TextField(); // HBox for new Weight
		newWeight.setPromptText("Enter Weight: ");
		hb4.getChildren().addAll(new Label("Enter New Weight: "), newWeight);

		Button bt = new Button("DONE"); // Done Button
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String ID = farmID.getText();
					int oldWeightVal = Integer.parseInt(oldWeight.getText());
					int newWeightVal = Integer.parseInt(newWeight.getText());
					String[] oldDateArray = oldDate.getText().split("-");
					String[] newDateArray = newDate.getText().split("-");
					if (oldDateArray.length != 3 || newDateArray.length != 3) {
						throw new IllegalArgumentException();
					}
					// UPDATE FARM IN DATA STRUCTURE HERE
					Farm f = farmTable.get(ID);
					f.update(oldDate.getText(), newDate.getText(), oldWeightVal, newWeightVal);
					Alert success = new Alert(AlertType.CONFIRMATION, "Data has been successfully updated",
							ButtonType.OK);
					success.show();

				} catch (IllegalArgumentException i) {
					Alert error = new Alert(AlertType.ERROR, "Did not input valid date format.", ButtonType.CLOSE);
					error.show();
				} finally {
					uD.close();
				}
			}
		});
		VBox vb = new VBox();
		vb.getChildren().addAll(hb0, hb1, hb2, hb3, hb4, bt);
		vb.setSpacing(25);

		root.setCenter(vb);

		Scene sc = new Scene(root);
		uD.setScene(sc);
		uD.setHeight(500);
		uD.setWidth(350);
		uD.show();
	}

	/**
	 * Method to create a new window for adding or removing data increase or
	 * decrease)
	 * 
	 * @param primaryStage parent stage to set modality
	 */
	public void addRemoveDataWindow(Stage primaryStage) {
		// CREATE WINDOW FOR NEW DATA
		Stage nD = new Stage();
		nD.setTitle("Add/Remove Data");
		nD.initModality(Modality.APPLICATION_MODAL);
		nD.initOwner(primaryStage);
		BorderPane root = new BorderPane();

		HBox hb1 = new HBox(); // HBox for farm ID line
		TextField farmID = new TextField();
		farmID.setPromptText("Farm ID");
		hb1.getChildren().addAll(new Label("Enter Farm ID: "), farmID);

		HBox hb2 = new HBox(); // HBox for Date line
		TextField date = new TextField();
		date.setPromptText("yyyy-mm-dd");
		hb2.getChildren().addAll(new Label("Enter Date: "), date);

		HBox hb3 = new HBox(); // HBox for Weight line
		TextField weight = new TextField();
		weight.setPromptText("Enter Weight");
		hb3.getChildren().addAll(new Label("Enter Milk Weight: "), weight);

		HBox hb4 = new HBox();
		Button add = new Button("Add");
		Button remove = new Button("Remove");
		hb4.getChildren().addAll(add, remove);
		hb4.setSpacing(5);
		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String ID = farmID.getText();
					String[] dateArray = date.getText().split("-");
					if (dateArray.length != 3) {
						throw new IllegalArgumentException();
					} else {
						if (dateArray[0].length() != 4 && dateArray[1].length() != 2 && dateArray[2].length() != 2) {
							throw new IllegalArgumentException();
						}
						if (Integer.parseInt(dateArray[1]) > 12 || Integer.parseInt(dateArray[2]) > 31) {
							throw new IllegalArgumentException();
						}
					}
					if (farmTable.get(ID) == null) {
						throw new NullPointerException();
					}
					farmTable.get(ID).remove(date.getText());
					Alert success = new Alert(AlertType.CONFIRMATION, "Data has been successfully removed",
							ButtonType.OK);
					success.show();
				} catch (IllegalArgumentException i) {
					i.printStackTrace();
					Alert error = new Alert(AlertType.ERROR, "Please follow format shown in text field.",
							ButtonType.CLOSE);
					error.show();
				} catch (NullPointerException n) {
					Alert error = new Alert(AlertType.ERROR, "The given ID does not exist in the data.",
							ButtonType.CLOSE);
					error.show();
				} finally {
					nD.close();
				}
			}
		});
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String ID = farmID.getText();
					int weightVal = Integer.parseInt(weight.getText());
					String[] dateArray = date.getText().split("-");
					if (dateArray.length != 3) {
						throw new IllegalArgumentException();
					} else {
						if (dateArray[0].length() != 4 && dateArray[1].length() != 2 && dateArray[2].length() != 2) {
							throw new IllegalArgumentException();
						}
					}
					if (farmTable.get(ID) == null) {
						throw new NullPointerException();
					}
					farmTable.get(ID).updateFarm(date.getText(), weightVal);
					Alert success = new Alert(AlertType.CONFIRMATION, "Data has been successfully added",
							ButtonType.OK);
					success.show();
				} catch (IllegalArgumentException i) {
					Alert error = new Alert(AlertType.ERROR, "Please follow format shown in text field.",
							ButtonType.CLOSE);
					error.show();
				} catch (NullPointerException n) {
					Alert error = new Alert(AlertType.ERROR, "The given ID does not exist in the data.",
							ButtonType.CLOSE);
					error.show();
				} finally {

					nD.close();
				}
			}
		});

		VBox vb = new VBox(); // VBox to put all 4 elements together
		vb.getChildren().addAll(hb1, hb2, hb3, hb4);
		vb.setSpacing(25);

		root.setCenter(vb);

		Scene scene = new Scene(root);
		nD.setScene(scene);
		nD.setWidth(500);
		nD.setHeight(350);
		nD.show();
	}

	/**
	 * Method to create a new window for initializing data with file(CSV) increase
	 * or decrease)
	 * 
	 * @param primaryStage parent stage to set modality
	 */
	public void uploadFileWindow(Stage primaryStage) {
		Stage uF = new Stage();
		uF.setTitle("Upload Data");
		uF.initModality(Modality.APPLICATION_MODAL);
		uF.initOwner(primaryStage);

		BorderPane root = new BorderPane();

		HBox upload = new HBox(); // HBox for file input
		TextField fileInput = new TextField();
		fileInput.setPromptText("File path");
		fileInput.setFocusTraversable(false);
		upload.getChildren().addAll(new Label("Enter Path of File: "), fileInput);
		Button bt = new Button("DONE"); // Done Button

		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					List<String[]> data = fileRead(fileInput.getText());
					data.remove(0);
					for (String[] d : data) {
						farmTable.insert(d[1], new Farm(d[1], Integer.parseInt(d[2]), d[0]));
					}
					Alert success = new Alert(AlertType.CONFIRMATION, "Data has been successfully uploaded",
							ButtonType.OK);
					success.show();
				} catch (Exception e) {
					e.printStackTrace();
					Alert a = new Alert(AlertType.ERROR, "Did not input valid file path.", ButtonType.CLOSE);
					a.show();
				} finally {
					uF.close();
				}
			}
		});

		VBox vb = new VBox(); // VBox to put all 4 elements together
		vb.getChildren().addAll(upload, bt);
		vb.setSpacing(25);

		root.setCenter(vb);

		Scene scene = new Scene(root);
		uF.setScene(scene);
		uF.setWidth(500);
		uF.setHeight(175);
		uF.show();
	}

	private static List<String[]> fileRead(String f) {
		try (Stream<String> stream = Files.lines(Paths.get(f))) {
			List<String[]> data = stream.filter(u -> u.split(",").length == 3).map(m -> m.split(","))
					.collect(Collectors.toList());
			return data;
		} catch (Exception e) {

		} finally {
		}
		return null;
	}

	/**
	 * Method that will run upon program execution
	 * 
	 * @param args System arguments to pass down before running
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
