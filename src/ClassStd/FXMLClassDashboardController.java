/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd;

import CMN.yearsession;
import ClassStd.Basket.FXMLBasketManagerController;
import ClassStd.BasketSales.FXMLBasketSalesManagerController;
import ClassStd.IndependendSale.FXMLBasketIndiSalesManagerController;
import ClassStd.Report.FXMLClassReportController;
import ClassStd.Stock.FXMLStockController;
import ClassStd.Student.FXMLStudentManagerController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLClassDashboardController implements Initializable {

    @FXML
    private AnchorPane fClasssDashpane;
    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public FXMLClassDashboardController() {

    }

    public void editLoader(String id, String yearsessionName, String stdsection) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        txtyearsession.setText(yearsessionName);
        txtstdsection.setText(stdsection);

        fClasssDashpane.setFocusTraversable(true);

    }

    @FXML
    private void ActionLoadStudent(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Student/FXMLStudentManager.fxml"));
            Parent load = loader.load();
            FXMLStudentManagerController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLoadStock(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Stock/FXMLStock.fxml"));
            Parent load = loader.load();
            FXMLStockController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLoadBasketManager(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Basket/FXMLBasketManager.fxml"));
            Parent load = loader.load();
            FXMLBasketManagerController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLoadYearSession(MouseEvent event) {
        loadBack();
    }

    @FXML
    private void actionLoadBasketSalesManager(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/BasketSales/FXMLBasketSalesManager.fxml"));
            Parent load = loader.load();
            FXMLBasketSalesManagerController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLoadindiSalesManager(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/IndependendSale/FXMLBasketIndiSalesManager.fxml"));
            Parent load = loader.load();
            FXMLBasketIndiSalesManagerController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionLoadGenerateReport(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Report/FXMLClassReport.fxml"));
            Parent load = loader.load();
            FXMLClassReportController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void ActionKeyReleased(KeyEvent event) {
        if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
            loadBack();
            
        }
    }

    private void loadBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/FXMLStdSection.fxml"));
            Parent load = loader.load();
            FXMLStdSectionController controller = loader.getController();
            controller.editLoader("1", txtyearsession.getText().trim());
            fClasssDashpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        } //To change body of generated methods, choose Tools | Templates.
    }

}
