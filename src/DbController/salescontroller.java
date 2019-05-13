/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.Student;
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
public class salescontroller {

    String id, yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp;

    public static boolean addNewSales(String yearsessionName, String stdsection, String studentname) {
        return db.setdata("INSERT INTO Sales (yearsessionName, stdsection, studentname, updated_on, dated_on, timestamp)"
                + "Values('" + yearsessionName + "','" + stdsection + "', '" + studentname + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static boolean deleteSales(String Saleid) {
        return db.setdata("Delete FROM Sales where id='" + Saleid + "'");
        
    }

    public static int getMaxSaleId() {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select max(id) as mid from sales");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static int getcountSaleId(String salesid) {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as mid from sales Where id='" + salesid + "'");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static int getcheckstudentwithSaleId(String salesid, String studentname) {
        int maxid = 0;
        try {
            ResultSet data = db.getdata("Select count(*) as mid from sales Where id='" + salesid + "' AND studentname ='" + studentname + "'");
            maxid = data.getInt("mid");
            data.close();
            return maxid;
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maxid;
    }

    public static ResultSet getSales() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM sales order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<sales> getSalesObservableList() {
        ObservableList<sales> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSales();
            while (data.next()) {
                people.add(new sales(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("studentname"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getSales(String YearSession, String StdSection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM sales WHERE yearsessionName='" + YearSession + "' AND stdsection='" + StdSection + "' order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    public static ObservableList<sales> getSalesObservableList(String YearSession, String StdSection) {
        ObservableList<sales> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSales(YearSession, StdSection);
            while (data.next()) {
                people.add(new sales(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("studentname"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(salescontroller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

}
