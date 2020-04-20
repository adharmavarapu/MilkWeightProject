package application;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

	/**
	 * Method that runs show primary Stage
	 * 
	 * @param primaryStage Stage passed down in launch of application
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		args = this.getParameters().getRaw();
		BorderPane root = new BorderPane();

		// Set left side of the main screen
		VBox leftButtons = new VBox();
		Button newData = new Button("", new Label("Add New Data"));
		newData.setOnAction(e -> newDataWindow(primaryStage));
		Button updateData = new Button("", new Label("Update Data"));
		updateData.setOnAction(e -> updateDataWindow(primaryStage));
		leftButtons.getChildren().addAll(newData, updateData);
		leftButtons.setSpacing(15);
		root.setLeft(leftButtons);

		// Set Right side of the main screen
		VBox rightButtons = new VBox();
		Button weightDifference = new Button("", new Label("Weight Difference"));
		weightDifference.setOnAction(e -> weightDifferenceWindow(primaryStage));
		Button uploadData = new Button("", new Label("Upload Data"));
		uploadData.setOnAction(e -> uploadFileWindow(primaryStage));
		rightButtons.getChildren().addAll(weightDifference, uploadData);
		rightButtons.setSpacing(15);
		root.setRight(rightButtons);

		// Finalize and set stage to main scene
		Scene mainScene = new Scene(root);
		primaryStage.setWidth(400);
		primaryStage.setHeight(150);
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	private void weightDifferenceWindow(Stage primaryStage) {
		// CREATE WINDOW FOR WEIGHT DIFFERENCE
		Stage wD = new Stage();
		wD.setTitle("Weight Difference");
		wD.initModality(Modality.APPLICATION_MODAL);
		wD.initOwner(primaryStage);

		BorderPane root = new BorderPane();
		Scene main = new Scene(root);
		// For prompt and text input
		HBox hb = new HBox();
		TextField idPrompt = new TextField();
		idPrompt.setPromptText("[Integer Value]");
		idPrompt.setFocusTraversable(false);
		hb.getChildren().addAll(new Label("Enter Farm ID: "), idPrompt);

		Button bt = new Button("DONE"); // Done Button
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
					String ID = idPrompt.getText();
					// GET MOST RECENT WEIGHT DIFFERNECE AND GOT TO
					// NEW WINDOW DISPLAYING DIFFERENCE
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
				} catch (IllegalArgumentException i) {
					Alert a = new Alert(AlertType.ERROR, "Did not valid date format.", ButtonType.CLOSE);
					a.show();
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

	public void newDataWindow(Stage primaryStage) {
		// CREATE WINDOW FOR NEW DATA
		Stage nD = new Stage();
		nD.setTitle("Add New Data");
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
		
		Button bt = new Button("DONE"); // Done Button
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					String ID = farmID.getText();
					int weightVal = Integer.parseInt(weight.getText());
					String[] dateArray = date.getText().split("-");
					if (dateArray.length != 3) {
						throw new IllegalArgumentException();
					}
					// ADD NEW DATA IN HASHTABLE
				}  catch (IllegalArgumentException i) {
					Alert a = new Alert(AlertType.ERROR, "Did not valid date format.", ButtonType.CLOSE);
					a.show();
				} finally {
					nD.close();
				}
			}
		});

		VBox vb = new VBox(); // VBox to put all 4 elements together
		vb.getChildren().addAll(hb1, hb2, hb3, bt);
		vb.setSpacing(25);

		root.setCenter(vb);

		Scene scene = new Scene(root);
		nD.setScene(scene);
		nD.setWidth(500);
		nD.setHeight(350);
		nD.show();
	}
	public void uploadFileWindow(Stage primaryStage) {
		Stage uF = new Stage();
		uF.setTitle("Upload Data");
		uF.initModality(Modality.APPLICATION_MODAL);
		uF.initOwner(primaryStage);
		
		BorderPane root = new BorderPane();
		
		HBox upload = new HBox(); // HBox for file input
		TextField fileInput = new TextField();
		fileInput.setPromptText("File path");
		upload.getChildren().addAll(new Label("Enter Path of File: "), fileInput);
		Button bt = new Button("DONE"); // Done Button
		
		bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					File f = new File(fileInput.getText());
					// ADD NEW DATA IN HASHTABLE
				} catch (Exception e) {
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
	/**
	 * Method that will run upon program execution
	 * 
	 * @param args System arguments to pass down before running
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
