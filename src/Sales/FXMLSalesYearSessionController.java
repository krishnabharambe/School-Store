/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import CMN.yearsession;
import ClassStd.FXMLStdSectionController;
import static DbController.YearSessionController.AddYearSession;
import static DbController.YearSessionController.checkIfAlreadyExists;
import static DbController.YearSessionController.getYearSessionObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
public class FXMLSalesYearSessionController implements Initializable {

    @FXML
    private TableView<yearsession> tbyearsession;
    @FXML
    private TableColumn<yearsession, String> tbyearsessioncolid;
    @FXML
    private TableColumn<yearsession, String> tbcolyearsessionsession;
    @FXML
    private TextField txtSearch;

    ObservableList<yearsession> data = FXCollections.observableArrayList(getYearSessionObservableList());
    ObservableList<yearsession> filteredData = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane fsalesyearsession;

    /**
     * Initializes the controller class.
     */
    public FXMLSalesYearSessionController() {

        data = FXCollections.observableArrayList(getYearSessionObservableList());
       
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<yearsession>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends yearsession> change) {
                updateFilteredData();
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadYearSessionTable();        
        
        fsalesyearsession.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
                    fsalesyearsession.getChildren().setAll(load3);
                    AnchorPane.setRightAnchor(load3, 0.0);
                    AnchorPane.setLeftAnchor(load3, 0.0);
                    AnchorPane.setTopAnchor(load3, 0.0);
                    AnchorPane.setBottomAnchor(load3, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLSalesYearSessionController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }
    
    
    
    

    @FXML
    private void ActionNewSession(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Year Session");
        dialog.setHeaderText("Enter New Session Year");
        dialog.setContentText(null);

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().trim().equals("")) {

            } else {

                int ch = checkIfAlreadyExists(result.get());
                if (ch == 0) {
                    boolean s = AddYearSession(result.get());
                    if (s) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("INFORMATION");
                        alert.setHeaderText("New Session Added.");
                        alert.setContentText(null);
                        alert.showAndWait();
                        loadYearSessionTable();
                    }
                } else {
//            exists
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("YEAR SESSION ALREDY EXISTS.");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            }
        } else {

        }

    }

    private void loadYearSessionTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getYearSessionObservableList());
        filteredData.addAll(data);
        tbyearsessioncolid.setCellValueFactory(new PropertyValueFactory<yearsession, String>("id"));
        tbcolyearsessionsession.setCellValueFactory(new PropertyValueFactory<yearsession, String>("yearsessionName"));
        tbyearsession.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (yearsession p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(yearsession p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getYearsessionName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<yearsession, ?>> sortOrder = new ArrayList<>(tbyearsession.getSortOrder());
        tbyearsession.getSortOrder().clear();
        tbyearsession.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionLoadSTD(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                yearsession selectedItem = tbyearsession.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/FXMLStdSection.fxml"));
                Parent load = loader.load();
                FXMLStdSectionController controller = loader.getController();
                controller.editLoader(selectedItem.getId(), selectedItem.getYearsessionName());
                fsalesyearsession.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLSalesYearSessionController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void ActionLoadDashboard(MouseEvent event) {
        try {
            javafx.scene.Parent load3 = FXMLLoader.load(getClass().getResource("/Dashboard/FXMLDashboard.fxml"));
            fsalesyearsession.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLSalesYearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
