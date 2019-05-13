/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.IndependendSale;

import CMN.BasketList;
import CMN.SalesTranscation;
import CMN.Stock;
import CMN.Student;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import static DbController.BasketController.getAllBasketName;
import static DbController.BasketController.getBasket;
import static DbController.BasketController.getBasketListObservableList;
import static DbController.IndiSalesConstroller.addNewindiSales;
import static DbController.IndiSalesConstroller.getMaxindiSaleId;
import static DbController.IndiSalesConstroller.getcheckstudentwithindiSaleId;
import static DbController.IndiSalesConstroller.getcountindiSaleId;
import DbController.SalesTranscationController;
import static DbController.SalesTranscationController.changeStudentNameinTranscationTable;
import static DbController.SalesTranscationController.getSalesTranscationObservableList;
import static DbController.SalesTranscationController.getSalesTranscationTotal;
import static DbController.StockController.getAllStockName;
import static DbController.StockController.getStockObservableList;
import static DbController.StockController.getStockPriceFromName;
import static DbController.StudentController.getAllStudentName;
import static DbController.StudentController.getStudentObservableList;
import static DbController.indiSalesTranscationController.DeleteFromindiSalesTranscation;
import static DbController.indiSalesTranscationController.changeStudentNameinindiTranscationTable;
import static DbController.indiSalesTranscationController.getindiSalesTranscationObservableList;
import static DbController.indiSalesTranscationController.getindiSalesTranscationTotal;
import static DbController.indiSalesTranscationController.newindiSalesTranscation;
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
public class FXMLBasketIndiSaleController implements Initializable {

    @FXML
    private AnchorPane fpane;
    @FXML
    private TextField txtStudentName;
    @FXML
    private TextField txtReceiprId;
    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TextField txtBasketName;
    @FXML
    private Button BtnAdd;
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
    @FXML
    private TableView<Student> tbstudent;
    @FXML
    private TableColumn<Student, String> tbStudentName;
    ObservableList<Student> data;
    @FXML
    private TableView<Stock> tbStock;
    @FXML
    private TableColumn<Stock, String> tbStockName;
    ObservableList<Stock> data2;
    @FXML
    private TextField txtprice;
    @FXML
    private TextField txtquantity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        TextFields.bindAutoCompletion(txtBasketName, getAllStockName(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        txtReceiprId.setText(String.valueOf(getMaxindiSaleId() + 1));

        _Load();

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
            Logger.getLogger(ClassStd.BasketSales.FXMLBasketSaleController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ClassStd.BasketSales.FXMLBasketSaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void _LoadStudentTable() {
        data = FXCollections.observableArrayList(getStudentObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("fullname"));
        tbstudent.setItems(data);
    }

    private void _LoadBasketList() {
        data2 = FXCollections.observableArrayList(getStockObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbStockName.setCellValueFactory(new PropertyValueFactory<Stock, String>("Stockname"));
        tbStock.setItems(data2);//To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void actionAddBasketToSales(ActionEvent event) {
        if (txtStudentName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please check all Fields");

            alert.showAndWait();
        } else if (txtBasketName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please check all Fields");

            alert.showAndWait();
        } else {

            //        add
//        check if salesid exists in database or not
            int saleidchecker = getcountindiSaleId(txtReceiprId.getText().trim());
            if (saleidchecker == 0) {
                //add
                boolean setdata = addNewindiSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim());
            } else {
                //check for student name change
                int result2 = getcheckstudentwithindiSaleId(txtReceiprId.getText().trim(), txtStudentName.getText().trim());
                if (result2 != 1) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Sure Want to Change Student Name??\nFor this receipt");
                    alert.setContentText(null);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        boolean setdata = addNewindiSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim());
                        changeStudentNameinindiTranscationTable(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim(), txtStudentName.getText().trim());
                    }
                }

            }

//          add Tranacation
//          get resultset for the selected basket
            newindiSalesTranscation(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim(), txtStudentName.getText().trim(), txtBasketName.getText().trim(), txtquantity.getText().trim(), txtprice.getText().trim());

            _LoadSalesTranscationTable();

        }
    }

    private void _LoadSalesTranscationTable() {
        STdata = FXCollections.observableArrayList(getindiSalesTranscationObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim()));
        tbSalesTranscationId.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("id"));
        tbSalesTranscationStockName.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("stockname"));
        tbSalesTranscationStockQuantity.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("quantity"));
        tbSalesTranscationStockPrice.setCellValueFactory(new PropertyValueFactory<SalesTranscation, String>("price"));
        tbsalesTranscation.setItems(STdata);
        txtTotal.setText(String.valueOf(getindiSalesTranscationTotal(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtReceiprId.getText().trim())));

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
        _Load();
    }

    private void _Load() {
        txtBasketName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                txtprice.setText(String.valueOf(getStockPriceFromName(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtBasketName.getText().trim())));
                _LoadStockData();
            }

        });

        txtquantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                txtprice.setText(String.valueOf(getStockPriceFromName(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtBasketName.getText().trim())));

                _LoadStockData();
            }

        });
    }

    private void _LoadStockData() {
        if (txtquantity.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            double price = Double.valueOf(txtprice.getText().trim());
            double quantity = Double.valueOf(txtquantity.getText().trim());
            txtTotal.setText(String.format("%.2f", price * quantity + 0.000));
        }
    }

    @FXML
    private void tbkeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DELETE)) {
            SalesTranscation selecteditem = tbsalesTranscation.getSelectionModel().getSelectedItem();
            boolean setdata = DeleteFromindiSalesTranscation(selecteditem.getId(), selecteditem.getYearsessionName(), selecteditem.getStdsection(), selecteditem.getStockname(), selecteditem.getQuantity());
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

    @FXML
    private void actinGenPDf(ActionEvent event) {
        if (txtTotal.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
            new PDFGeneration.PDFIndiSales(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtStudentName.getText().trim(), txtReceiprId.getText().trim(), txtTotal.getText().trim());
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("\nInvalid Input\n\nPlease check all Fields.");
            alert.showAndWait();
        }
    }

}
