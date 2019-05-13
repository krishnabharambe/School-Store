/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard;

import Database.db;
import Sales.FXMLSalesYearSessionController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLPasswordChangeController implements Initializable {

    @FXML
    private TextField txtoldpass;
    @FXML
    private TextField txtnewpass;
    @FXML
    private TextField txtconfirmPass;
    
    @FXML
    private AnchorPane fpasswordChnaged;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fpasswordChnaged.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    fpasswordChnaged.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLPasswordChangeController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }

    @FXML
    private void ActionChangePass(ActionEvent event) {
        if (txtoldpass.getText().trim().equals("") || txtnewpass.getText().trim().equals("") || txtconfirmPass.getText().trim().equals("") || !txtnewpass.getText().trim().equals(txtconfirmPass.getText())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("PLease, Check All fields OR Invalid Input.");
            alert.setContentText(null);

            alert.showAndWait();
        } else {
            try {
                int checker = 0;
                ResultSet data = db.getdata("Select count(*) as checker from UserManager WHERE username='Admin' AND password = '" + txtoldpass.getText().trim() + "'");
                checker = data.getInt("checker");
                data.close();

                if (checker == 0) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error ");
                    alert.setHeaderText("Invalid Password");
                    alert.setContentText(null);
                    alert.showAndWait();
                } else {
                    boolean setdata = db.setdata("UPDATE UserManager SET password = '" + txtconfirmPass.getText().trim() + "' WHERE Username = 'Admin' ");
                    if (setdata) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("INFORMATION ");
                        alert.setHeaderText("Password Change Successfully\nPlease Close this Window Start the Application");
                        alert.setContentText(null);
                        alert.showAndWait();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPasswordChangeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
