/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.Report;

import CMN.BasketList;
import CMN.BasketValue;
import CMN.commonFile;
import ClassStd.BasketSales.FXMLBasketSaleController;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import Database.db;
import static DbController.BasketController.getBasketListObservableList;
import static DbController.BasketValueController.getBasketValueObservableList;
import PDFGeneration.PDFClassReport;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLClassReportController implements Initializable {

    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private AnchorPane fpane;
    private DatePicker fromdate;
    private DatePicker todate;
    @FXML
    private TableView<BasketList> tbBasketNames;
    @FXML
    private TableColumn<BasketList, String> tbcolbasketname;

    ObservableList<BasketList> data;
    @FXML
    private TextField txtBasketName;
    @FXML
    private TextField txtBasketPrice;
    @FXML
    private Button btnsubmit;
    @FXML
    private TableView<BasketValue> tbbasketvalue;
    @FXML
    private TableColumn<BasketValue, String> tbcolbname;
    @FXML
    private TableColumn<BasketValue, String> tbcolbprice;

    ObservableList<BasketValue> data2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupfromdate();
        setuptodate();

    }

    public void editLoader(String yearsession, String StdSection) {
        txtyearsession.setText(yearsession);
        txtstdsection.setText(StdSection);
        _LoadBasketName();
        _Reset();
    }

    private void ActionGenerateReport(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Sure Want to Generate Report??");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
//            new PDFClassReport(txtyearsession.getText().trim(), txtstdsection.getText().trim());
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
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupfromdate() {
        String pattern = "dd-MM-yyyy";

        fromdate.setPromptText(pattern.toLowerCase());

        fromdate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private void setuptodate() {
        String pattern = "dd-MM-yyyy";

        todate.setPromptText(pattern.toLowerCase());

        todate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private void ActionGenerateBasketReport(ActionEvent event) {
        try {
            String year = txtyearsession.getText().trim();
            String stdsection = txtstdsection.getText().trim();
            String fromDate = fromdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
            String toDate = todate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();

            if (fromDate.equals("") || toDate.equals("")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please Select Fromdate And To date");

                alert.showAndWait();
            } else {
                //move the db to another table
//        for that get all data from SalesTranscation
                db.setdata("DELETE FROM sales2");
                db.setdata("DELETE FROM indisales2");
                ResultSet data = db.getdata("Select * From sales Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "'");

//        boolean setdata = db.setdata("INSERT INTO ReportsalesTranscation Select * From Salestranscation Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "'");
//        if (setdata) {
//            System.out.println("Done");
//        }else{
//            System.out.println("Try Again");
//        }
                while (data.next()) {
                    String dated_on = data.getString("dated_on");
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dated_on);
                    String date2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);

                    db.setdata("INSERT INTO sales2(yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                            + "VALUES( '" + data.getString("yearsessionName") + "', '" + data.getString("stdsection") + "', '" + data.getString("studentname") + "', '" + data.getString("updated_on") + "', '" + date2 + "', '" + data.getString("timestamp") + "')"
                    );

                }

                data.close();
                
                                ResultSet data2 = db.getdata("Select * From indisales Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "'");

//        boolean setdata = db.setdata("INSERT INTO ReportsalesTranscation Select * From Salestranscation Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "'");
//        if (setdata) {
//            System.out.println("Done");
//        }else{
//            System.out.println("Try Again");
//        }
                while (data.next()) {
                    String dated_on = data.getString("dated_on");
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dated_on);
                    String date2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);

                    db.setdata("INSERT INTO indisales2(saleid,yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                            + "VALUES( '"+data.getString("id")+"','" + data.getString("yearsessionName") + "', '" + data.getString("stdsection") + "', '" + data.getString("studentname") + "', '" + data.getString("updated_on") + "', '" + date2 + "', '" + data.getString("timestamp") + "')"
                    );

                }

                data.close();

                /// Procide Towards report
                new PDFClassReport(year, stdsection, fromDate, toDate);

            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void _LoadBasketName() {
        data = FXCollections.observableArrayList(getBasketListObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbcolbasketname.setCellValueFactory(new PropertyValueFactory<BasketList, String>("BasketName"));
        tbBasketNames.setItems(data);

    }

    @FXML
    private void actionTbkeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            BasketList selecteditem = tbBasketNames.getSelectionModel().getSelectedItem();
            txtBasketName.setText(selecteditem.getBasketName());
        }
    }

    @FXML
    private void actionsubmit(ActionEvent event) {
        if (txtBasketName.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText(null);
            alert.setContentText("Please, Check the Basket Name");
            alert.showAndWait();
        } else {
            if (txtBasketPrice.getText().trim().matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                try {
                    String year = txtyearsession.getText().trim();
                    String stdsection = txtstdsection.getText().trim();

                    //checdk if alreaddy added or not
                    int checker = 0;
                    ResultSet data = db.getdata("Select count(*) as checker from basketValue Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "' ");
                    checker = data.getInt("checker");
                    data.close();

                    if (checker == 0) {
                        //add    
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("Sure Want to Add?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            // ... user chose OK
                            boolean setdata = db.setdata("INSERT INTO basketValue(yearsessionName,stdsection,BasketName,basketvalue) VALUES('" + year + "','" + stdsection + "','" + txtBasketName.getText().trim() + "','" + txtBasketPrice.getText().trim() + "')");
                            if (setdata) {
                                Alert alert2 = new Alert(AlertType.INFORMATION);
                                alert2.setTitle("Information");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Added Successfully");

                                alert2.showAndWait();
                                _Reset();
                            }
                        }
                    } else {
                        //update
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("Sure Want to Update?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            // ... user chose OK
                            boolean setdata = db.setdata("update basketValue SET basketvalue='" + txtBasketPrice.getText().trim() + "' Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "' AND BasketName='" + txtBasketName.getText().trim() + "' ");
                            if (setdata) {
                                Alert alert2 = new Alert(AlertType.INFORMATION);
                                alert2.setTitle("Information");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Updated Successfully");

                                alert2.showAndWait();
                                _Reset();
                            }
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error ");
                alert.setHeaderText(null);
                alert.setContentText("Please, Check the Price");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void actionReset(ActionEvent event) {
        _Reset();
    }

    private void _Reset() {
        txtBasketName.setText("");
        txtBasketPrice.setText("");
        _LoadTable();
    }

    private void _LoadTable() {
        data2 = FXCollections.observableArrayList(getBasketValueObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        tbcolbname.setCellValueFactory(new PropertyValueFactory<BasketValue, String>("BasketName"));
        tbcolbprice.setCellValueFactory(new PropertyValueFactory<BasketValue, String>("basketValue"));
        tbbasketvalue.setItems(data2);
    }

}
