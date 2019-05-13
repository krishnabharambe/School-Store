/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.SalesTranscation;
import CMN.Student;
import CMN.commonFile;
import Database.db;
import static DbController.StockController.deleteStockSale;
import static DbController.StockController.newStockSale;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class SalesTranscationController {

//    String id, yearsessionName, stdsection, saleid, studentname, stockname, quantity, price, updated_on, dated_on, timestamp;
////salesTranscation
    public static boolean newSalesTranscation(String yearsessionName, String stdsection, String saleid, String studentname, String stockname, String quantity, String price) {
        boolean setdata = db.setdata("INSERT INTO salesTranscation( yearsessionName, stdsection, saleid, studentname, stockname, quantity, price, updated_on, dated_on, timestamp)"
                + "VALUES( '" + yearsessionName + "', '" + stdsection + "', '" + saleid + "', '" + studentname + "', '" + stockname + "', '" + quantity + "', '" + price + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Timestamp() + "')");
        newStockSale(yearsessionName, stdsection, stockname, quantity);
        return setdata;
    }

    public static boolean changeStudentNameinTranscationTable(String yearsessionName, String stdsection, String saleid, String studentname) {
        return db.setdata("Update salesTranscation set studentname = '" + studentname + "' WHERE saleid = '" + saleid + "'");
    }

//    public static ResultSet getStudent(String YearSession, String StdSection) {
//        try {
//            return db.getconnection().createStatement().executeQuery("SELECT * FROM student WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' order by id");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    public static ObservableList<SalesTranscation> getSalesTranscationObservableList(String YearSession, String StdSection, String Saleid) {
        ObservableList<SalesTranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSalesTranscation(YearSession, StdSection, Saleid);
            while (data.next()) {
                people.add(new SalesTranscation(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("saleid"), data.getString("studentname"), data.getString("stockname"), data.getString("quantity"), data.getString("price"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(SalesTranscationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getSalesTranscation(String YearSession, String StdSection, String Saleid) {
        return db.getdata("Select * from salesTranscation WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' AND saleid = '" + Saleid + "'");
    }

    public static double getSalesTranscationTotal(String YearSession, String StdSection, String Saleid) {
        try {
            return db.getdata("Select sum(price) as sp from salesTranscation WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' AND saleid = '" + Saleid + "'").getDouble("sp");
        } catch (SQLException ex) {
            Logger.getLogger(SalesTranscationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static boolean DeleteFromSalesTranscation(String transid, String yearsessionName, String stdsection, String stockname, String quantity) {
        boolean setdata = db.setdata("Delete FROm salesTranscation WHERE id = '" + transid + "'");
        boolean setdata2 = deleteStockSale(yearsessionName, stdsection, stockname, quantity);

        return setdata2 && setdata;

    }

    public static boolean DeleteFromSalesTranscation(String Saleid) {

        return db.setdata("Delete FROM salesTranscation where saleid='" + Saleid + "'");

    }
}
