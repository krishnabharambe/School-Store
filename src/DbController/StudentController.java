/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.Student;
import CMN.commonFile;
import CMN.yearsession;
import Database.db;
import static DbController.StockController.getStock;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class StudentController {

    public static boolean NewStudent(String yearsession, String StdSection, String RollNo, String Name, String Contact, String Address) {
        return db.setdata("INSERT iNTO Student (yearsessionName,stdsection,Rollno,fullname,contact,address,updated_on,dated_on,timestamp)"
                + "VALUES('" + yearsession + "','" + StdSection + "','" + RollNo + "','" + Name + "','" + Contact + "','" + Address + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static boolean UpdateStudent(String id, String yearsession, String StdSection, String RollNo, String Name, String Contact, String Address) {
        return db.setdata("UPDATE Student SET yearsessionName='" + yearsession + "',stdsection='" + StdSection + "',Rollno='" + RollNo + "',fullname='" + Name + "',contact='" + Contact + "',address='" + Address + "',updated_on='" + commonFile.Fun_Date() + "' WHERE id ='" + id + "'");
    }

    public static boolean DeleteStudent(String Id) {
        return db.setdata("DELETE FROm Student Where id = '" + Id + "'");
    }

    public static ResultSet getStudent() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM student order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student> getStudentObservableList() {
        ObservableList<Student> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getStudent();
            while (data.next()) {
                people.add(new Student(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Rollno"), data.getString("fullname"), data.getString("contact"), data.getString("address"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));

            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getStudent(String YearSession, String StdSection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM student WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Student> getStudentObservableList(String YearSession, String StdSection) {
        ObservableList<Student> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getStudent(YearSession, StdSection);
            while (data.next()) {
                people.add(new Student(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Rollno"), data.getString("fullname"), data.getString("contact"), data.getString("address"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
    
    public static String[] getAllStudentName(String YearSession, String StdSection) {
        try {

            ResultSet data = getStudent(YearSession,StdSection);
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("fullname"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
