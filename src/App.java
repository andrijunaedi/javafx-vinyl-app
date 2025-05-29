import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;   
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;       
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Call login screen first
        showLoginScreen(primaryStage);
    }
    
    private void showLoginScreen(Stage primaryStage) {
        try {
            // Load the FXML file for the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 300, 275);
            primaryStage.setTitle("Arkana's Vinyl Lounger");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();

            // Display an error message if the FXML file cannot be loaded
            Text errorMsg = new Text("Gagal memuat halaman login: " + e.getMessage());
            StackPane errorPane = new StackPane(errorMsg);
            Scene errorScene = new Scene(errorPane, 400, 100);
            primaryStage.setTitle("Error");
            primaryStage.setScene(errorScene);
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}