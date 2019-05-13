/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.SalesTranscation;
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
public class indiSalesTranscationController {

    public static boolean newindiSalesTranscation(String yearsessionName, String stdsection, String saleid, String studentname, String stockname, String quantity, String price) {
        boolean setdata = db.setdata("INSERT INTO indisalesTranscation( yearsessionName, stdsection, saleid, studentname, stockname, quantity, price, updated_on, dated_on, timestamp)"
                + "VALUES( '" + yearsessionName + "', '" + stdsection + "', '" + saleid + "', '" + studentname + "', '" + stockname + "', '" + quantity + "', '" + price + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Timestamp() + "')");
        newStockSale(yearsessionName, stdsection, stockname, quantity);
        return setdata;
    }

    public static boolean changeStudentNameinindiTranscationTable(String yearsessionName, String stdsection, String saleid, String studentname) {
        return db.setdata("Update indisalesTranscation set studentname = '" + studentname + "' WHERE saleid = '" + saleid + "'");
    }

    public static ObservableList<SalesTranscation> getindiSalesTranscationObservableList(String YearSession, String StdSection, String Saleid) {
        ObservableList<SalesTranscation> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getindiSalesTranscation(YearSession, StdSection, Saleid);
            while (data.next()) {
                people.add(new SalesTranscation(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("saleid"), data.getString("studentname"), data.getString("stockname"), data.getString("quantity"), data.getString("price"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(indiSalesTranscationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getindiSalesTranscation(String YearSession, String StdSection, String Saleid) {
        return db.getdata("Select * from indisalesTranscation WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' AND saleid = '" + Saleid + "'");
    }

    public static double getindiSalesTranscationTotal(String YearSession, String StdSection, String Saleid) {
        try {
            return db.getdata("Select sum(price) as sp from indisalesTranscation WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' AND saleid = '" + Saleid + "'").getDouble("sp");
        } catch (SQLException ex) {
            Logger.getLogger(indiSalesTranscationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static boolean DeleteFromindiSalesTranscation(String transid) {
        return db.setdata("Delete FROm indisalesTranscation WHERE id = '" + transid + "'");
    }

    public static boolean DeleteFromindiSalesTranscation(String transid, String yearsessionName, String stdsection, String stockname, String quantity) {
        boolean setdata = db.setdata("Delete FROm indisalesTranscation WHERE id = '" + transid + "'");
        boolean setdata2 = deleteStockSale(yearsessionName, stdsection, stockname, quantity);

        return setdata2 && setdata;

    }
}
