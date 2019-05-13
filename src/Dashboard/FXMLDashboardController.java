/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLDashboardController implements Initializable {


    @FXML
    private AnchorPane fdashboard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ActionLoadSales(MouseEvent event) {
        _LoadSales();
    }

    private void _LoadSales() {

        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Sales/FXMLSalesYearSession.fxml"));
            fdashboard.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionLoadPRofile(MouseEvent event) {
         try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLPasswordChange.fxml"));
            fdashboard.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLogout(MouseEvent event) {
        _Logout();
    }
    private void _Logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Sure Want To Logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
           System.exit(0);
        }
    }

}
