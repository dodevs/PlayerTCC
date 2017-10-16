package main2;

import com.jfoenix.controls.JFXDecorator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Thiago
 */
public class sad extends Application {
	private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {    	
    	Parent root = FXMLLoader.load(getClass().getResource("a.fxml"));       
        
        JFXDecorator decorator = new JFXDecorator(stage, root);
    	decorator.setCustomMaximize(true);
    	Scene scene = new Scene(decorator);         
        stage.setScene(scene);
        scene.getStylesheets().add
        (sad.class.getResource("application.css").toExternalForm());
        
        stage.setTitle("LoginFX");
        stage.show();        
        sad.setStage(stage);
    }

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    	
        launch(args);
    }
	public static Stage getStage() {
		return stage;
	}
	public static void setStage(Stage stage) {
		sad.stage = stage;
	}
}
