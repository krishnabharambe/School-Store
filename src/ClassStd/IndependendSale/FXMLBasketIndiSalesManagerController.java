/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.IndependendSale;

import CMN.sales;
import ClassStd.BasketSales.FXMLBasketSaleController;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import Database.db;
import static DbController.IndiSalesConstroller.getindiSalesObservableList;
import static DbController.salescontroller.getSalesObservableList;
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
public class FXMLBasketIndiSalesManagerController implements Initializable {

    @FXML
    private AnchorPane fpane;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TableView<sales> tbSales;
    @FXML
    private TableColumn<sales, String> tbcolsaleid;
    @FXML
    private TableColumn<sales, String> tbcolstudentname;
    @FXML
    private TableColumn<sales, String> tbcolDate;

    ObservableList<sales> data = FXCollections.observableArrayList(getindiSalesObservableList());
    ObservableList<sales> filteredData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    public FXMLBasketIndiSalesManagerController() {
        data = FXCollections.observableArrayList(getSalesObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<sales>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends sales> change) {
                updateFilteredData();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actionNewBasketSale(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/IndependendSale/FXMLBasketIndiSale.fxml"));
            Parent load = loader.load();
            FXMLBasketIndiSaleController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLBasketIndiSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editLoader(String yearsession, String stdsection) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        _Table();
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
            Logger.getLogger(FXMLBasketIndiSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLBasketIndiSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (sales p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(sales p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getDated_on().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStudentname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<sales, ?>> sortOrder = new ArrayList<>(tbSales.getSortOrder());
        tbSales.getSortOrder().clear();
        tbSales.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    private void _Table() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getindiSalesObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        filteredData.addAll(data);
        tbcolsaleid.setCellValueFactory(new PropertyValueFactory<sales, String>("id"));
        tbcolstudentname.setCellValueFactory(new PropertyValueFactory<sales, String>("studentname"));
        tbcolDate.setCellValueFactory(new PropertyValueFactory<sales, String>("dated_on"));
        tbSales.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }

    @FXML
    private void tbsaleskeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                sales selecteditem = tbSales.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/IndependendSale/FXMLBasketIndiSale.fxml"));
                Parent load = loader.load();
                FXMLBasketIndiSaleController controller = loader.getController();
                controller.editLoader("1", txtyearsession.getText().trim(), txtstdsection.getText().trim(), selecteditem.getId(), selecteditem.getStudentname());

                fpane.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLBasketIndiSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Sure, Want to Delete??");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                sales selectedItem = tbSales.getSelectionModel().getSelectedItem();
                if (db.setdata("Delete from indisales where id ='" + selectedItem.getId() + "'")) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Deleted Successfully");
                    _Table();
                    alert2.showAndWait();

                    db.setdata("delete from indisalesTranscation where saleid ='" + selectedItem.getId() + "'");
                }
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
