/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.commonFile;
import CMN.sales;
import Database.db;
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
public class IndiSalesConstroller {

    public static boolean addNewindiSales(String yearsessionName, String stdsection, String studentname) {
        return db.setdata("INSERT INTO indiSales (yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                + "Values('" + yearsessionName + "','" + stdsection + "', '" + studentname + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static int getMaxindiSaleId() {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select max(id) as mid from indisales");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static int getcountindiSaleId(String salesid) {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as mid from indisales Where id='" + salesid + "'");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static int getcheckstudentwithindiSaleId(String salesid, String studentname) {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as mid from indisales Where id='" + salesid + "' AND studentname ='" + studentname + "'");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static ResultSet getindiSales() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM indisales order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<sales> getindiSalesObservableList() {
        ObservableList<sales> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getindiSales();
            while (data.next()) {
                people.add(new sales(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("studentname"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getindiSales(String YearSession, String StdSection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM indisales WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<sales> getindiSalesObservableList(String YearSession, String StdSection) {
        ObservableList<sales> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getindiSales(YearSession, StdSection);
            while (data.next()) {
                people.add(new sales(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("studentname"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static Float getValueFromindiSalesId(String Saleid) {

        try {
            ResultSet data = db.getdata("Select sum(price) as p from indisalesTranscation Where saleid = '" + Saleid + "'");
            return data.getFloat("p");
        } catch (SQLException ex) {
            Logger.getLogger(IndiSalesConstroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
