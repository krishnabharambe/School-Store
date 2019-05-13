package ClassStd.BasketSales;

import CMN.BasketList;
import CMN.SalesTranscation;
import CMN.Student;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import static DbController.BasketController.getAllBasketName;
import static DbController.BasketController.getBasket;
import static DbController.BasketController.getBasketListObservableList;
import static DbController.BasketValueController.getBasketValue;
import DbController.SalesTranscationController;
import static DbController.SalesTranscationController.DeleteFromSalesTranscation;
import static DbController.SalesTranscationController.changeStudentNameinTranscationTable;
import static DbController.SalesTranscationController.getSalesTranscationObservableList;
import static DbController.SalesTranscationController.getSalesTranscationTotal;
import static DbController.StudentController.getAllStudentName;
import static DbController.StudentController.getStudentObservableList;
import static DbController.indiSalesTranscationController.DeleteFromindiSalesTranscation;
import DbController.salescontroller;
import static DbController.salescontroller.getcheckstudentwithSaleId;
import static DbController.salescontroller.getcountSaleId;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLBasketSaleController implements Initializable {

    @FXML
    private AnchorPane fpane;
    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtBasketName;
    @FXML
    private Button BtnAdd;
    @FXML
    private TableView<Student> tbstudent;
    @FXML
    private TableColumn<Student, String> tbStudentName;
    ObservableList<Student> data;
    @FXML
    private TableView<BasketList> tbBasket;
    @FXML
    private TableColumn<BasketList, String> tbBasketName;
    ObservableList<BasketList> data2;
    @FXML
    private TextField txtReceiprId;
    @FXML
    private TextField txtTotal;
    @FXML
    private TableView<SalesTranscation> tbsalesTranscation;
    @FXML
    private TableColumn<SalesTranscation, String> tbSalesTranscationId;
    @FXML
    private TableColumn<SalesTranscation, String> tbSalesTranscationStockName;
    @FXML
    private TableColumn<SalesTranscation, String> tbSalesTranscationStockQuantity;
    @FXML
    private TableColumn<SalesTranscation, String> tbSalesTranscationStockPrice;

    ObservableList<SalesTranscation> STdata;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODOTextFields.bindAutoCompletion(txtStockName, getAllStockName());
        txtReceiprId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                _LoadSalesTranscationTable();
            }

        });

    }

    void editLoader(String yearsession, String stdsection) {

        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        _LoadStudentTable();
        _LoadBasketList();
        TextFields.bindAutoCompletion(txtStudentName, getAllStudentName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        TextFields.bindAutoCompletion(txtBasketName, getAllBasketName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        txtReceiprId.setText(String.valueOf(salescontroller.getMaxSaleId() + 1));

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
            Logger.getLogger(FXMLBasketSaleController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLBasketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void _LoadStudentTable() {
        data = FXCollections.observableArrayList(getStudentObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("fullname"));
        tbstudent.setItems(data);
    }

    private void _LoadBasketList() {
        data2 = FXCollections.observableArrayList(getBasketListObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbBasketName.setCellValueFactory(new PropertyValueFactory<BasketList, String>("BasketName"));
        tbBasket.setItems(data2);//To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionAddBasketToSales(ActionEvent event) {
        if (txtStudentName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please check all Fields");

            alert.showAndWait();
        } else if (txtBasketName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please check all Fields");

            alert.showAndWait();
        } else {
            Alert alert9 = new Alert(AlertType.CONFIRMATION);
            alert9.setTitle("Confirmation Dialog");
            alert9.setHeaderText(null);
            alert9.setContentText("Are you ok with this?");

            Optional<ButtonType> result9 = alert9.showAndWait();
            if (result9.get() == ButtonType.OK) {
                // ... user chose OK

                try {
                    //        add
//        check if salesid exists in database or not
                    int saleidchecker = getcountSaleId(txtReceiprId.getText().trim());
                    if (saleidchecker == 0) {
                        //add
                        boolean setdata = salescontroller.addNewSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim());
                    } else {
                        //check for student name change
                        int result2 = getcheckstudentwithSaleId(txtReceiprId.getText().trim(), txtStudentName.getText().trim());
                        if (result2 != 1) {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Sure Want to Change Student Name??\nFor this receipt");
                            alert.setContentText(null);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                boolean setdata = salescontroller.addNewSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim());
                                changeStudentNameinTranscationTable(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim(), txtStudentName.getText().trim());
                            }
                        }

                    }

//          add Tranacation
//          get resultset for the selected basket
                    ResultSet data = getBasket(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtBasketName.getText().trim());
                    while (data.next()) {
                        //add to Transcation Table
                        SalesTranscationController.newSalesTranscation(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim(), txtStudentName.getText().trim(), data.getString("stockname"), data.getString("quantity"), data.getString("price"));
                    }
                    data.close();
                    _LoadSalesTranscationTable();

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Added Successfully");
                    alert.showAndWait();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLBasketSaleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void _LoadSalesTranscationTable() {
        STdata = FXCollections.observableArrayList(getSalesTranscationObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim()));
        tbSalesTranscationId.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("id"));
        tbSalesTranscationStockName.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("stockname"));
        tbSalesTranscationStockQuantity.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("quantity"));
        tbSalesTranscationStockPrice.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("price"));
        tbsalesTranscation.setItems(STdata);
        txtTotal.setText(String.valueOf(getBasketValue(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtBasketName.getText().trim())));

    }

    void editLoader(String fid, String yearsession, String stdsection, String id, String studentname) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        txtReceiprId.setText(id);
        txtStudentName.setText(studentname);
        _LoadStudentTable();
        _LoadBasketList();
        TextFields.bindAutoCompletion(txtStudentName, getAllStudentName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        TextFields.bindAutoCompletion(txtBasketName, getAllBasketName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));

    }

    @FXML
    private void actionGenPdf(ActionEvent event) {
        if (txtTotal.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            new PDFGeneration.PDFBasketSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim(), txtReceiprId.getText().trim(), txtTotal.getText().trim());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid Input\n\nPlease check all Fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void tbKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            SalesTranscation selecteditem = tbsalesTranscation.getSelectionModel().getSelectedItem();
            boolean setdata = DeleteFromSalesTranscation(selecteditem.getId(), selecteditem.getYearsessionName(), selecteditem.getStdsection(), selecteditem.getStockname(), selecteditem.getQuantity());
            if (setdata) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Transcation Deleted Successfully");
                alert.showAndWait();
                _LoadSalesTranscationTable();
            }
        }
    }

}
