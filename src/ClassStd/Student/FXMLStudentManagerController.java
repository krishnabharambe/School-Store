/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassStd.Student;

import CMN.Student;
import CMN.commonFile;
import CMN.yearsession;
import ClassStd.Basket.FXMLBasketController;
import ClassStd.FXMLClassDashboardController;
import ClassStd.FXMLStdSectionController;
import Database.db;
import DbController.StudentController;
import static DbController.StudentController.DeleteStudent;
import static DbController.StudentController.getStudentObservableList;
import static DbController.YearSessionController.getYearSessionObservableList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLStudentManagerController implements Initializable {

    String Updating = "0";

    @FXML
    private AnchorPane fpane;
    @FXML
    private Label txtyearsession;
    @FXML
    private Label txtstdsection;
    @FXML
    private TableView<Student> tbStudent;
    @FXML
    private TableColumn<Student, String> tbStudentId;
    @FXML
    private TableColumn<Student, String> tbStudentRollNo;
    @FXML
    private TableColumn<Student, String> tbStudentName;
    @FXML
    private TableColumn<Student, String> tbStudentAddress;
    @FXML
    private TextField txtSearch;

    ObservableList<Student> data = FXCollections.observableArrayList(getStudentObservableList());
    ObservableList<Student> filteredData = FXCollections.observableArrayList();
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtAddress;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtRollNo;

    /**
     * Initializes the controller class.
     */
    public FXMLStudentManagerController() {
        data = FXCollections.observableArrayList(getStudentObservableList());
        filteredData.addAll(data);

        data.addListener(new ListChangeListener<Student>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Student> change) {
                updateFilteredData();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        _LoadStudentTable();
        _Reset();
    }

    @FXML
    private void ActionImportStudentData(ActionEvent event) {

        FileInputStream input = null;
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.showOpenDialog(null);
            File selectedPfile = chooser.getSelectedFile();
            System.out.println("Path :" + selectedPfile.getPath());
            input = new FileInputStream(selectedPfile.getPath());
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                int id = (int) row.getCell(0).getNumericCellValue();
                String name = row.getCell(1).getStringCellValue().toString();
                String address = row.getCell(2).getStringCellValue().toString();
                db.setdata("INSERT INTO STUDENT(yearsessionName,stdsection,Rollno,fullname,contact,address,updated_on,dated_on,timestamp)"
                        + "VALUES('" + txtyearsession.getText().trim() + "','" + txtstdsection.getText().trim() + "','" + id + "','" + name + "','" + "Contact" + "','" + address + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
            }

            input.close();
            _LoadStudentTable();
            _Reset();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void editLoader(String yearsession, String stdsection) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        txtyearsession.setText(yearsession);
        txtstdsection.setText(stdsection);
        _LoadStudentTable();
        _Reset();
    }

    private void _LoadStudentTable() {
        filteredData.removeAll(data);
        data = FXCollections.observableArrayList(getStudentObservableList(txtyearsession.getText().trim(), txtstdsection.getText().trim()));
        filteredData.addAll(data);
        tbStudentId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        tbStudentRollNo.setCellValueFactory(new PropertyValueFactory<Student, String>("Rollno"));
        tbStudentName.setCellValueFactory(new PropertyValueFactory<Student, String>("fullname"));
        tbStudentAddress.setCellValueFactory(new PropertyValueFactory<Student, String>("address"));
        tbStudent.setItems(filteredData);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });//To change body of generated methods, choose Tools | Templates.
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Student p : data) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Student p) {
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
        } else if (p.getRollno().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getFullname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getContact().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (p.getAddress().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Student, ?>> sortOrder = new ArrayList<>(tbStudent.getSortOrder());
        tbStudent.getSortOrder().clear();
        tbStudent.getSortOrder().addAll(sortOrder); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void ActionTbStudentKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.F2)) {
            Student S = tbStudent.getSelectionModel().getSelectedItem();
            Updating = S.getId();
            btnAdd.setText("Update : " + Updating);
            txtRollNo.setText(S.getRollno());
            txtName.setText(S.getFullname());
            txtAddress.setText(S.getAddress());
        } else if (event.getCode().equals(KeyCode.DELETE)) {
            Student s = tbStudent.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Student Roll No :" + s.getRollno() + "\n"
                    + "Student Name Full Name :" + s.getFullname() + "\n"
                    + "Student Store Id : " + s.getId() + "\n"
                    + "Sure Want to Delete this Student???");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean setdata = DeleteStudent(s.getId());
                if (setdata) {
                    Alert alert2 = new Alert(AlertType.INFORMATION);
                    alert2.setTitle("Information");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Student Deleted Successfully");
                    alert2.showAndWait();
                    _LoadStudentTable();
                    _Reset();
                }
            }
        }

    }

    @FXML
    private void ActionAddNewStudent(ActionEvent event) {
        if (txtName.getText().trim().equals("") || txtAddress.getText().trim().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please Check the Name & Address");
            alert.showAndWait();
        } else {
            if (btnAdd.getText().trim().equals("Add")) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Sure Want to Add??");
                alert.setContentText(null);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    boolean setdata = StudentController.NewStudent(txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtRollNo.getText().trim(), txtName.getText().trim(), "Contact", txtAddress.getText().trim());
                    if (setdata) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("INFORMATION");
                        alert2.setHeaderText(null);
                        alert2.setContentText("New Student Added Successfully");
                        alert2.showAndWait();
                        _LoadStudentTable();
                        _Reset();
                    }
                }
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Sure Want to Update??");
                alert.setContentText(null);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    boolean setdata = StudentController.UpdateStudent(Updating, txtyearsession.getText().trim(), txtstdsection.getText().trim(), txtRollNo.getText().trim(), txtName.getText().trim(), "Contact", txtAddress.getText().trim());
                    if (setdata) {
                        Alert alert2 = new Alert(AlertType.INFORMATION);
                        alert2.setTitle("INFORMATION");
                        alert2.setHeaderText(null);
                        alert2.setContentText("New Student Updated Successfully");
                        alert2.showAndWait();
                        _LoadStudentTable();
                        _Reset();
                    }
                }

            }

        }
    }

    @FXML
    private void actionreset(ActionEvent event) {
        _Reset();
    }

    private void _Reset() {
        Updating = "0";
        txtRollNo.setText(String.valueOf(NewRollNo()));//To change body of generated methods, choose Tools | Templates.
        txtName.setText("");
        txtAddress.setText("");
        btnAdd.setText("Add");
    }

    private int NewRollNo() {
        int maxid = 0;
        try {

            ResultSet data = db.getdata("Select max(RollNo) as maxrollno FROM Student WHERE yearsessionName ='" + txtyearsession.getText().trim() + "' AND stdsection='" + txtstdsection.getText().trim() + "'");
            maxid = data.getInt("maxrollno");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid + 1;

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
            Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FXMLStudentManagerController.class.getName()).log(Level.SEVERE, null, ex);
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
