import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AuthController {
    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void login(ActionEvent event) {
        String username = this.username.getText();
        String password = this.password.getText();

        Boolean isValidCredential = (username.equals("andri102022480017") && password.equals("andri"));

        if(isValidCredential) {
            try {
                Parent inventoryController = FXMLLoader.load(getClass().getResource("inventory.fxml"));
                Scene scene = new Scene(inventoryController);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                e.printStackTrace();
                alert.setTitle("Error");
                alert.setContentText("Terjadi kesalahan saat membuka halaman utama.");
                alert.showAndWait();
            } finally {
                // Optionally, you can clear the fields after login
                this.username.clear();
                this.password.clear();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Username atau Password Salah");
            alert.setContentText("Username atau password yang Anda masukkan salah. Silakan coba lagi.");
            alert.showAndWait();
        }
    }
}
