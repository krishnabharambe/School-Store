/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.Stock;

import CMN.Stock;
import ClassStd.Basket.FXMLBasketController;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import DbController.StockController;
import static DbController.StockController.DeleteStock;
import static DbController.StockController.getStockObservableList;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLStockController implements Initializable {

    String Updating = "0";

    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TextField txtStockName;
    @FXML
    private TextField txtStockPrice;
    @FXML
    private Button btnnReset;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtAdditionalQuantity;
    @FXML
    private Button btnaddAdditionalQuantity;
    @FXML
    private TextField txtRemainQuantity;
    @FXML
    private Button btnUpdateRemainQty;
    @FXML
    private TextField txtSoldQuantity;
    @FXML
    private Button btnUpdateSoldQty;

    ObservableList<Stock> data = FXCollections.observableArrayList(getStockObservableList());
    ObservableList<Stock> filteredData = FXCollections.observableArrayList();
    @FXML
    private TableView<Stock> tbStock;
    @FXML
    private TableColumn<Stock, String> tbStockId;
    @FXML
    private TableColumn<Stock, String> tbStockName;
    @FXML
    private TableColumn<Stock, String> tbStockPrice;
    @FXML
    private TableColumn<Stock, String> tbStockSold;
    @FXML
    private TableColumn<Stock, String> tbStockRemain;
    @FXML
    private TextField txtSearch;
    @FXML
    private AnchorPane fpane;

    public FXMLStockController() {

        data = FXCollections.observableArrayList(getStockObservableList());

        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Stock>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Stock> change) {
                updateFilteredData();
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        _LoadStockTable();
    }

    public void editLoader(String yearsession, String stdsection) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        _LoadStockTable();
    }

    @FXML
    private void actionAddStock(ActionEvent event) {
        if (txtStockName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please Check Stock Name.");

            alert.showAndWait();
        } else if (!txtStockPrice.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Price");
            alert.showAndWait();
        } else {
            if (btnAdd.getText().trim().equals("Add")) {
                //add
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Sure Want to Add??");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK 
                    boolean setdata = StockController.addStock(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStockName.getText().trim(), txtStockPrice.getText().trim(), "0", "0");
                    if (setdata) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("Information");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Added Successfully");
                        alert2.showAndWait();
                        _LoadStockTable();
                        _Reset();
                    }
                }

            } else {
                //update
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Sure Want to Update??");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK 
                    boolean setdata = StockController.updateStock(Updating, txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStockName.getText().trim(), txtStockPrice.getText().trim());
                    if (setdata) {
                        Alert alert3 = new Alert(AlertType.INFORMATION);
                        alert3.setTitle("Information");
                        alert3.setHeaderText(null);
                        alert3.setContentText("Updated Successfully");
                        alert3.showAndWait();
                        _LoadStockTable();
                        _Reset();
                    }
                }

            }

        }
    }

    @FXML
    private void actionAddAdditionalStockQuantity(ActionEvent event) {
        if (Updating.trim().equals("") || txtAdditionalQuantity.getText().trim().equals("") || !txtAdditionalQuantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText(null);
            alert3.setContentText("Please Check Additional Quantity");
            alert3.showAndWait();
        } else {
            boolean setdata = StockController.AddAdditionalStock(Updating, txtAdditionalQuantity.getText().trim());
            if (setdata) {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Information");
                alert3.setHeaderText(null);
                alert3.setContentText("Updated Successfully");
                alert3.showAndWait();
                _LoadStockTable();
                _Reset();
            }
        }
    }

    @FXML
    private void ActionUpdateRemainQty(ActionEvent event) {
        if (Updating.trim().equals("") || txtRemainQuantity.getText().trim().equals("") || !txtRemainQuantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText(null);
            alert3.setContentText("Please Check Additional Quantity");
            alert3.showAndWait();
        } else {
            boolean setdata = StockController.UpdateRemainStock(Updating, txtRemainQuantity.getText().trim());
            if (setdata) {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Information");
                alert3.setHeaderText(null);
                alert3.setContentText("Updated Successfully");
                alert3.showAndWait();
                _LoadStockTable();
                _Reset();
            }
        }
    }

    @FXML
    private void ActionUpdateSoldQty(ActionEvent event) {
        if (Updating.trim().equals("") || txtSoldQuantity.getText().trim().equals("") || !txtSoldQuantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            Alert alert3 = new Alert(AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText(null);
            alert3.setContentText("Please Check Additional Quantity");
            alert3.showAndWait();
        } else {
            boolean setdata = StockController.UpdateSoldStock(Updating, txtSoldQuantity.getText().trim());
            if (setdata) {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Information");
                alert3.setHeaderText(null);
                alert3.setContentText("Updated Successfully");
                alert3.showAndWait();
                _LoadStockTable();
                _Reset();
            }
        }
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Stock p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Stock p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStockname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Stock, ?>> sortOrder = new ArrayList<>(tbStock.getSortOrder());
        tbStock.getSortOrder().clear();
        tbStock.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    private void _LoadStockTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getStockObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        filteredData.addAll(data);
        tbStockId.setCellValueFactory(new PropertyValueFactory<Stock, String>("id"));
        tbStockName.setCellValueFactory(new PropertyValueFactory<Stock, String>("Stockname"));
        tbStockPrice.setCellValueFactory(new PropertyValueFactory<Stock, String>("price"));
        tbStockRemain.setCellValueFactory(new PropertyValueFactory<Stock, String>("remainQuantity"));
        tbStockSold.setCellValueFactory(new PropertyValueFactory<Stock, String>("soldquantity"));
        tbStock.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });  //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionTbKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            Stock selecteditem = tbStock.getSelectionModel().getSelectedItem();
            Updating = selecteditem.getId();
            txtStockName.setText(selecteditem.getStockname());
            txtStockPrice.setText(selecteditem.getPrice());
            txtRemainQuantity.setText(selecteditem.getRemainQuantity());
            txtSoldQuantity.setText(selecteditem.getSoldquantity());
            btnAdd.setText("Update " + Updating);
            btnaddAdditionalQuantity.setText("Update " + Updating);
            btnUpdateRemainQty.setText("Update " + Updating);
            btnUpdateSoldQty.setText("Update " + Updating);
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Stock selecteditem = tbStock.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Stock Title :" + selecteditem.getStockname()+ "\n"
                    + "Stock Store Id : " + selecteditem.getId() + "\n"
                    + "Sure Want to Delete this Stock???");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdata = DeleteStock(selecteditem.getId());
                if (setdata) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Stock Deleted Successfully");
                    alert2.showAndWait();
                    _LoadStockTable();
                    _Reset();
                }
            }
        }
    }

    private void _Reset() {
        Updating = "0";
        txtStockName.setText("");
        txtStockPrice.setText("");
        txtRemainQuantity.setText("");
        txtSoldQuantity.setText("");
        txtAdditionalQuantity.setText("");
        btnAdd.setText("Update");
        btnaddAdditionalQuantity.setText("Update");
        btnUpdateRemainQty.setText("Update");
        btnUpdateSoldQty.setText("Update");
        btnAdd.setText("Add");
    }

    @FXML
    private void ActionReset(ActionEvent event) {
        _Reset();
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
