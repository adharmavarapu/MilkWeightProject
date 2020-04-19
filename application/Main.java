/**
 * 
 */
package application;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author adharmavarapu
 *
 */
public class Main extends Application{
	private List<String> args;
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		args = this.getParameters().getRaw();
		
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
