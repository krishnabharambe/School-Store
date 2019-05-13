/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.Basket;

import CMN.BasketList;
import CMN.Student;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import ClassStd.Stock.FXMLStockController;
import static DbController.BasketController.getBasketListObservableList;
import static DbController.StudentController.getStudentObservableList;
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
public class FXMLBasketManagerController implements Initializable {

    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private AnchorPane fpane;
    @FXML
    private TableView<BasketList> tbbasket;
    @FXML
    private TableColumn<BasketList, String> tbbasketcolid;
    @FXML
    private TableColumn<BasketList, String> tbbasketBasketName;

    ObservableList<BasketList> data;
    ObservableList<BasketList> filteredData = FXCollections.observableArrayList();
    @FXML
    private TextField txtSearch;

    public FXMLBasketManagerController() {

        data = FXCollections.observableArrayList(getBasketListObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<BasketList>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends BasketList> change) {
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

    }

    public void editLoader(String yearsession, String stdsection) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        _LoadBasketList();
    }

    @FXML
    private void ActionNewBasket(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Basket/FXMLBasket.fxml"));
            Parent load = loader.load();
            FXMLBasketController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLBasketManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ActionLoadStock(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Stock/FXMLStock.fxml"));
            Parent load = loader.load();
            FXMLStockController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLClassDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ActionLoadBasketManager(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Basket/FXMLBasketManager.fxml"));
            Parent load = loader.load();
            FXMLBasketManagerController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
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

    private void updateFilteredData() {
        filteredData.clear();

        for (BasketList p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(BasketList p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getBasketName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<BasketList, ?>> sortOrder = new ArrayList<>(tbbasket.getSortOrder());
        tbbasket.getSortOrder().clear();
        tbbasket.getSortOrder().addAll(sortOrder); 
    }

    private void _LoadBasketList() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getBasketListObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        filteredData.addAll(data);
        tbbasketcolid.setCellValueFactory(new PropertyValueFactory<BasketList, String>("id"));
        tbbasketcolid.setVisible(false);
        tbbasketBasketName.setCellValueFactory(new PropertyValueFactory<BasketList, String>("BasketName"));
        tbbasket.setItems(data);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }

    @FXML
    private void tbBasketManagerKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                BasketList selectedItem = tbbasket.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/Basket/FXMLBasket.fxml"));
                Parent load = loader.load();
                FXMLBasketController controller = loader.getController();
                controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim(), selectedItem.getBasketName());
                fpane.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLBasketManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
