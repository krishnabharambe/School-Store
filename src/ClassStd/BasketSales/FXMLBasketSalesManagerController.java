package ClassStd.BasketSales;

import CMN.Student;
import CMN.sales;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import ClassStd.Student.FXMLStudentManagerController;
import static DbController.SalesTranscationController.DeleteFromSalesTranscation;
import static DbController.StudentController.getStudentObservableList;
import static DbController.salescontroller.deleteSales;
import static DbController.salescontroller.getSalesObservableList;
import PDFGeneration.ClassBasketReport;
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
public class FXMLBasketSalesManagerController implements Initializable {

    @FXML
    private AnchorPane fpane;
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
    @FXML
    private TextField txtSearch;

    ObservableList<sales> data = FXCollections.observableArrayList(getSalesObservableList());
    ObservableList<sales> filteredData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    public FXMLBasketSalesManagerController() {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/BasketSales/FXMLBasketSale.fxml"));
            Parent load = loader.load();
            FXMLBasketSaleController controller = loader.getController();
            controller.editLoader(txtyearsession.getText().trim(), txtstdsection.getText().trim());
            fpane.getChildren().setAll(load);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLBasketSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLBasketSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLBasketSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
        data = FXCollections.observableArrayList(getSalesObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/BasketSales/FXMLBasketSale.fxml"));
                Parent load = loader.load();
                FXMLBasketSaleController controller = loader.getController();
                controller.editLoader("1", txtyearsession.getText().trim(), txtstdsection.getText().trim(), selecteditem.getId(), selecteditem.getStudentname());

                fpane.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLBasketSalesManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Sure Want to Delete This?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK

//                Delete From Sales table
                sales selecteditem = tbSales.getSelectionModel().getSelectedItem();
                DeleteFromSalesTranscation(selecteditem.getId());
                deleteSales(selecteditem.getId());

                Alert alert2 = new Alert(AlertType.INFORMATION);
                alert2.setTitle("Information ");
                alert2.setHeaderText(null);
                alert2.setContentText("Deleted Successfully");
                _Table();
                alert2.showAndWait();

//                        
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
    private void ActionReport(MouseEvent event) {
        new ClassBasketReport(txtyearsession.getText().trim(), txtstdsection.getText().trim());
    }

}
