package ClassStd;

import static CMN.commonFile.setDatePicker;
import CMN.standardsection;
import ClassStd.Report.FXMLClassReportController;
import Database.db;
import DbController.StandardSectionController;
import static DbController.StandardSectionController.getSTDSectionObservableList;
import PDFGeneration.PDFAllCLass;
import PDFGeneration.PDFAllCLassindi;
import Sales.FXMLSalesYearSessionController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class FXMLStdSectionController implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<standardsection> tbstdsection;
    @FXML
    private TableColumn<standardsection, String> tbstdsectioncolid;
    @FXML
    private TableColumn<standardsection, String> tbstdsectioncolSessionYear;
    @FXML
    private TableColumn<standardsection, String> tbstdsectionColSTDSection;

    @FXML
    private Label txtyearsession;

    ObservableList<standardsection> data = FXCollections.observableArrayList(getSTDSectionObservableList());
    ObservableList<standardsection> filteredData = FXCollections.observableArrayList();
    @FXML
    private AnchorPane fstdsection;
    @FXML
    private DatePicker fromdate;
    @FXML
    private DatePicker todate;
    @FXML
    private DatePicker indifromdate;
    @FXML
    private DatePicker inditodate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        loadStdSectionTable();

    }

    public FXMLStdSectionController() {
        data = FXCollections.observableArrayList(getSTDSectionObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<standardsection>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends standardsection> change) {
                updateFilteredData();
            }
        });

    }

    @FXML
    private void ActionNewSession(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Standard & Section");
        dialog.setHeaderText("Enter New Standard & Section\n\nFor Session Year " + txtyearsession.getText().trim());
        dialog.setContentText(null);

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().trim().equals("")) {

            } else {

                int ch = StandardSectionController.checkIfAlreadyExistsSTDSection(result.get());
                if (ch == 0) {
                    boolean s = StandardSectionController.AddSTDSection(txtyearsession.getText().trim(), result.get());
                    if (s) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("INFORMATION");
                        alert.setHeaderText("Standard & Section Added.");
                        alert.setContentText(null);
                        alert.showAndWait();
                        loadStdSectionTable(txtyearsession.getText().trim());
                    }
                } else {
//            exists
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Standard & Section ALREDY EXISTS.");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            }
        } else {

        }
    }

    public void editLoader(String id, String yearsessionName) {
        txtyearsession.setText(yearsessionName);
        loadStdSectionTable(yearsessionName);
        setDatePicker(fromdate);
        setDatePicker(todate);
        setDatePicker(indifromdate);
        setDatePicker(inditodate);

        fstdsection.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.HOME == event.getCode() || KeyCode.END == event.getCode() || KeyCode.ESCAPE == event.getCode()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sales/FXMLSalesYearSession.fxml"));
                    Parent load = loader.load();
                    FXMLSalesYearSessionController controller = loader.getController();
                    fstdsection.getChildren().setAll(load);
                    AnchorPane.setRightAnchor(load, 0.0);
                    AnchorPane.setLeftAnchor(load, 0.0);
                    AnchorPane.setTopAnchor(load, 0.0);
                    AnchorPane.setBottomAnchor(load, 0.0);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLStdSectionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (standardsection p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(standardsection p) {
        String filterString = txtSearch.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (p.getId().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getYearsessionName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getStdsection().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<standardsection, ?>> sortOrder = new ArrayList<>(tbstdsection.getSortOrder());
        tbstdsection.getSortOrder().clear();
        tbstdsection.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadStdSectionTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getSTDSectionObservableList());
        filteredData.addAll(data);
        tbstdsectioncolid.setCellValueFactory(new PropertyValueFactory<standardsection, String>("id"));
        tbstdsectioncolSessionYear.setCellValueFactory(new PropertyValueFactory<standardsection, String>("yearsessionName"));
        tbstdsectionColSTDSection.setCellValueFactory(new PropertyValueFactory<standardsection, String>("stdsection"));
        tbstdsection.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        }); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadStdSectionTable(String stdsection) {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getSTDSectionObservableList(stdsection));
        filteredData.addAll(data);
        tbstdsectioncolid.setCellValueFactory(new PropertyValueFactory<standardsection, String>("id"));
        tbstdsectioncolSessionYear.setCellValueFactory(new PropertyValueFactory<standardsection, String>("yearsessionName"));
        tbstdsectionColSTDSection.setCellValueFactory(new PropertyValueFactory<standardsection, String>("stdsection"));
        tbstdsection.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        }); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void ActionLoadSTDDashboard(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                standardsection selectedItem = tbstdsection.getSelectionModel().getSelectedItem();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClassStd/FXMLClassDashboard.fxml"));
                Parent load = loader.load();
                FXMLClassDashboardController controller = loader.getController();
                controller.editLoader(selectedItem.getId(), selectedItem.getYearsessionName(), selectedItem.getStdsection());
                fstdsection.getChildren().setAll(load);
                AnchorPane.setRightAnchor(load, 0.0);
                AnchorPane.setLeftAnchor(load, 0.0);
                AnchorPane.setTopAnchor(load, 0.0);
                AnchorPane.setBottomAnchor(load, 0.0);
            } catch (IOException ex) {
                Logger.getLogger(FXMLStdSectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actionloadreport(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Sure Want to Generate ");
        alert.setContentText(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
//            new PDFAllCLass(txtyearsession.getText().trim());
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
            fstdsection.getChildren().setAll(load3);
            AnchorPane.setRightAnchor(load3, 0.0);
            AnchorPane.setLeftAnchor(load3, 0.0);
            AnchorPane.setTopAnchor(load3, 0.0);
            AnchorPane.setBottomAnchor(load3, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXMLStdSectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionAllReport(ActionEvent event) {

        try {
            String year = txtyearsession.getText().trim();

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

                ResultSet data = db.getdata("Select * From sales Where yearsessionName='" + year + "'");

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

//        boolean setdata = db.setdata("INSERT INTO ReportsalesTranscation Select * From Salestranscation Where yearsessionName='" + year + "' AND stdsection='" + stdsection + "'");
//        if (setdata) {
//            System.out.println("Done");
//        }else{
//            System.out.println("Try Again");
//        }
                db.setdata("DELETE FROM indisales2");
                ResultSet data2 = db.getdata("Select * From indisales Where yearsessionName='" + year + "'");
                while (data2.next()) {
                    String dated_on = data2.getString("dated_on");
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dated_on);
                    String date2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);

                    db.setdata("INSERT INTO indisales2(yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                            + "VALUES( '" + data2.getString("yearsessionName") + "', '" + data2.getString("stdsection") + "', '" + data2.getString("studentname") + "', '" + data2.getString("updated_on") + "', '" + date2 + "', '" + data2.getString("timestamp") + "')"
                    );

                }

//                data.close();
                /// Procide Towards report
                new PDFAllCLass(year, fromDate, toDate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXMLClassReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ActionIndiReport(ActionEvent event) {
        String year = txtyearsession.getText().trim();

        String indifromDate = indifromdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        String inditoDate = inditodate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();

        if (indifromDate.equals("") || inditoDate.equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please Select Fromdate And To date");
            alert.showAndWait();
        } else {
            try {
                db.setdata("DELETE FROM indisales2");
                ResultSet data2 = db.getdata("Select * From indisales Where yearsessionName='" + year + "'");
                while (data2.next()) {
                    String dated_on = data2.getString("dated_on");
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(dated_on);
                    String date2 = new SimpleDateFormat("yyyy-MM-dd").format(date1);

                    db.setdata("INSERT INTO indisales2(saleid,yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                            + "VALUES( '" + data2.getString("id") + "','" + data2.getString("yearsessionName") + "', '" + data2.getString("stdsection") + "', '" + data2.getString("studentname") + "', '" + data2.getString("updated_on") + "', '" + date2 + "', '" + data2.getString("timestamp") + "')"
                    );

                }

                new PDFAllCLassindi(year, indifromDate, inditoDate);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLStdSectionController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FXMLStdSectionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
