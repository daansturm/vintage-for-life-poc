package VintageForLife;

import VintageForLife.DB.DBConnection;
import VintageForLife.Routes.APPRoutes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen {
    private final String SALT = "$2a$10$abcdefghijklmnopqrstuv";
    public TextField emailTextField;
    public PasswordField passwordField;
    public Label notificationLabel;

    private SceneController sceneController;
    private final Connection connection;

    public LoginScreen() throws SQLException {
        sceneController = new SceneController();
        connection = DBConnection.getConnection();

    }

    @FXML
    void Login(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (authenticateUser(email, password)) {
            try {
                sceneController.ShowOverzichtRoutes(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            notificationLabel.setText("Ongeldig e-mailadres of wachtwoord");
            notificationLabel.setVisible(true);
        }
    }

    private boolean authenticateUser(String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, this.SALT);

        try {
            String sql = "SELECT * FROM gebruikers WHERE email = ? AND wachtwoord = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return BCrypt.checkpw(password, hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
