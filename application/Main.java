package MilkWeightProject.application;

import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The graphical user interface of the Milk Weight program
 * @author Abhilash Dharmavarapu, Atharva Kudkilwar
 *
 */
public class Main extends Application{
	private List<String> args;
	private static final String APP_TITLE = "Milk Weights";

	/**
	 * Method that runs show primary Stage
	 * @param primaryStage Stage passed down in launch of application 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		args = this.getParameters().getRaw();
		BorderPane root = new BorderPane();
		
		//Set left side of the main screen
		VBox leftButtons = new VBox();
		Button newData = new Button("", new Label("Add New Data"));
		newData.setOnAction(e -> newDataWindow(primaryStage));
		Button updateData = new Button("", new Label("Update Data"));
		updateData.setOnAction(e -> updateDataWindow(primaryStage));
		leftButtons.getChildren().addAll(newData, updateData);
		leftButtons.setSpacing(15);
		root.setLeft(leftButtons);
		
		//Set Right side of the main screen
		Button weightDifference = new Button("", new Label("Update Data"));
		weightDifference.setOnAction(e -> weightDifferenceWindow(primaryStage));
		root.setRight(weightDifference);		
		
		//Finalize and set stage to main scene
		Scene mainScene = new Scene(root);
		primaryStage.setWidth(400);
		primaryStage.setHeight(150);
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	private void weightDifferenceWindow(Stage primaryStage) {
		//CREATE WINDOW FOR WEIGHT DIFFERENCE
		Stage wD = new Stage();
		wD.setTitle("Weight Difference");
		wD.initModality(Modality.APPLICATION_MODAL);
		wD.initOwner(primaryStage);
		wD.show();
	}
	private void updateDataWindow(Stage primaryStage) {
		//CREATE WINDOW FOR UPDATE DATA
		Stage uD = new Stage();
		uD.setTitle("Update Existing Data");
		uD.initModality(Modality.APPLICATION_MODAL);
		uD.initOwner(primaryStage);
		uD.show();
	}
	public void newDataWindow(Stage primaryStage) {
		//CREATE WINDOW FOR NEW DATA
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
	/**
	 * Method that will run upon program execution
	 * @param args System arguments to pass down before running
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}


}
