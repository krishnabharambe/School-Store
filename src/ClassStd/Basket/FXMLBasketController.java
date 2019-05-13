/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.Basket;

import CMN.SalesTranscation;
import CMN.Stock;
import CMN.basket;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import DbController.BasketController;
import static DbController.BasketController.DeleteFromBasketTranscation;
import static DbController.BasketController.getAllBasketName;
import static DbController.BasketController.getbasketvalue;
import static DbController.SalesTranscationController.DeleteFromSalesTranscation;
import static DbController.StockController.getAllStockName;
import static DbController.StockController.getStockObservableList;
import static DbController.StockController.getStockPriceFromName;
import static DbController.StudentController.getAllStudentName;
import static DbController.indiSalesTranscationController.DeleteFromindiSalesTranscation;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLBasketController implements Initializable {

    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TableView<Stock> tbStock;
    @FXML
    private TableColumn<Stock, String> tbStockName;
    ObservableList<Stock> data;
    @FXML
    private TextField txtStockName;
    @FXML
    private TextField txtquantity;
    @FXML
    private TextField txttotal;
    @FXML
    private TextField txtBasketName;
    @FXML
    private AnchorPane fpane;
    @FXML
    private TableView<basket> tbBasketTranscation;
    @FXML
    private TableColumn<basket, String> tbBasketId;
    @FXML
    private TableColumn<basket, String> tbbasketStockName;
    @FXML
    private TableColumn<basket, String> tbbasketQuantity;
    @FXML
    private TableColumn<basket, String> tbbasketTotal;

    ObservableList<basket> data2;
    @FXML
    private TextField txtbasketValue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtStockName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadStockData();
            }

        });

        txtquantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadStockData();
            }

        });

        txtBasketName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadTranscationTable();
            }

        });

    }

    void editLoader(String yearsession, String StdSection) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(StdSection);
        TextFields.bindAutoCompletion(txtStockName, getAllStockName(yearsession, StdSection));
//        TextFields.bindAutoCompletion(txtBasketName, getAllBasketName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        _LoadStockTable();

    }

    private void _LoadStockTable() {
        data = FXCollections.observableArrayList(getStockObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbStockName.setCellValueFactory(new PropertyValueFactory<Stock, String>("Stockname"));
        tbStock.setItems(data); //To change body of generated methods, choose Tools | Templates.
    }

    private void _LoadStockData() {

        if (txtquantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            double price = getStockPriceFromName(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStockName.getText().trim());
            double quantity = Double.valueOf(txtquantity.getText().trim());
            txttotal.setText(String.format("%.2f", price * quantity + 0.000));
        }
    }

    @FXML
    private void actionToBasket(ActionEvent event) {
        if (txtBasketName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
        } else if (!txtquantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();

        } else if (Double.valueOf(txtquantity.getText().trim()) < 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();

        } else {       //add

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Sure Want to Add??");
            alert.setContentText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                boolean setdata = BasketController.addToBasket(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStockName.getText().trim(), txttotal.getText().trim(), txtquantity.getText().trim(), txtBasketName.getText().trim());
                if (setdata) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("INFORMATION");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Added Successfully");
                    alert2.showAndWait();
                    _LoadTranscationTable();
                    txtStockName.setText("");
                    
                }

            }

        }
    }

    @FXML
    private void ActionLoadYearSession(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/FXMLStdSection.fxml"));
            Parent load = loader.load();
            FXMLStdSectionController controller = loader.getController();
            controller.editLoader("1", txtyearsession.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLBasketController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void actionLoadClassDashboard(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/FXMLClassDashboard.fxml"));
            Parent load = loader.load();
            FXMLClassDashboardController controller = loader.getController();
            controller.editLoader("1", txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLBasketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void _LoadTranscationTable() {

        data2 = FXCollections.observableArrayList(BasketController.getBasketObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtBasketName.getText().trim()));

        tbBasketId.setCellValueFactory(new PropertyValueFactory<basket, String>("id"));
        tbbasketStockName.setCellValueFactory(new PropertyValueFactory<basket, String>("Stockname"));
        tbbasketQuantity.setCellValueFactory(new PropertyValueFactory<basket, String>("Quantity"));
        tbbasketTotal.setCellValueFactory(new PropertyValueFactory<basket, String>("price"));
        tbBasketTranscation.setItems(data2);

        _loadbasketValue();

    }

    private void _loadbasketValue() {
        txtbasketValue.setText(String.format("%.2f", getbasketvalue(txtyearsession.getText().trim(), txtstdsection.getText(), txtBasketName.getText().trim()) + 0.000));
    }

    void editLoader(String yearsession, String StdSection, String basketName) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(StdSection);
        txtBasketName.setText(basketName);
        TextFields.bindAutoCompletion(txtStockName, getAllStockName(yearsession, StdSection));

        _LoadStockTable();
    }

    @FXML
    private void tbKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            basket selecteditem = tbBasketTranscation.getSelectionModel().getSelectedItem();
            boolean setdata = DeleteFromBasketTranscation(selecteditem.getId());
            if (setdata) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Transcation Deleted Successfully");
                alert.showAndWait();
                _LoadTranscationTable();
            }
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
