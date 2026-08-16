import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;  

public class Main extends Application { 
   @Override     
   public void start(Stage primaryStage) throws Exception 
   {            
      try
      {
         Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
         Image appIcon = new Image(getClass().getResourceAsStream("/icons/calculator.png"));
         
         //Creating a Scene by passing the group object, width and height   
         Scene scene = new Scene(root, 400, 500); 
         primaryStage.setResizable(false);

         //Setting the title to Stage. 
         primaryStage.setTitle("Calculator"); 

         //Adding the scene to Stage 
         primaryStage.setScene(scene);
         
         primaryStage.getIcons().add(appIcon);
         
         //Displaying the contents of the stage 
         primaryStage.show(); 
         
      }
      catch(Exception e)
      {

      }
   }    
   public static void main(String args[])
   {          
      launch(args);     
   }         
} 