Default:
	make Compile
	make Program
	make clean
Compile:
	javac --module-path "/home/adharmavarapu/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml -classpath ".:application" -d . application/*.java 
Program:
	java --module-path "/home/adharmavarapu/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml -jar executable.jar
clean:
	rm -f application/*.class
