/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.BasketList;
import CMN.basket;
import CMN.commonFile;
import Database.db;
import static DbController.StudentController.getStudent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author admin
 */
public class BasketController {

//    String id, yearsessionName, stdsection, Stockname, price, Quantity, BasketName, updated_on, dated_on, timestamp;
    public static boolean addToBasket(String yearsessionName, String stdsection, String Stockname, String price, String Quantity, String BasketName) {
        return db.setdata("INSERT INTO BASKET (yearsessionName, stdsection, Stockname, price, Quantity, BasketName, updated_on, dated_on, timestamp)"
                + "Values('" + yearsessionName + "','" + stdsection + "', '" + Stockname + "', '" + price + "', '" + Quantity + "', '" + BasketName + "', '" + commonFile.Fun_Date() + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static boolean DeleteFromBasketTranscation(String Transid) {
        return db.setdata("DELETE FROM Basket WHERE id = '" + Transid + "'");
    }

    public static ResultSet getBasket() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM basket order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<basket> getBasketObservableList() {
        ObservableList<basket> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getBasket();
            while (data.next()) {
                people.add(new basket(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Stockname"), data.getString("price"), data.getString("Quantity"), data.getString("BasketName"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getBasket(String yearsession, String Stdsection, String basketname) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM basket WHERE yearsessionName='" + yearsession + "' AND stdsection='" + Stdsection + "' AND BasketName = '" + basketname + "' order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<basket> getBasketObservableList(String yearsession, String Stdsection, String basketname) {
        ObservableList<basket> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getBasket(yearsession, Stdsection, basketname);
            while (data.next()) {
                people.add(new basket(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Stockname"), data.getString("price"), data.getString("Quantity"), data.getString("BasketName"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static double getbasketvalue(String yearsession, String stdsection, String BasketName) {
        try {
            double value = 0;
            ResultSet data = db.getdata("Select sum(price) as value from basket where yearsessionName='" + yearsession + "' AND stdsection='" + stdsection + "' AND BasketName = '" + BasketName + "' ");
            value = data.getDouble("value");
            data.close();
            return value;
        } catch (SQLException ex) {
            Logger.getLogger(BasketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static ResultSet getBasketList() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT *,sum(price) as BasketValue FROM basket order by id");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<BasketList> getBasketListObservableList() {
        ObservableList<BasketList> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getBasketList();
            while (data.next()) {
                people.add(new BasketList("id", data.getString("BasketName")));

            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(BasketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ResultSet getBasketList(String yearsession, String stdsection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM basket WHERE yearsessionName='" + yearsession + "' AND stdsection='" + stdsection + "' GROUP BY BasketName");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<BasketList> getBasketListObservableList(String yearsession, String stdsection) {
        ObservableList<BasketList> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getBasketList(yearsession, stdsection);
            while (data.next()) {
                people.add(new BasketList("id", data.getString("BasketName")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(BasketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static String[] getAllBasketName(String YearSession, String StdSection) {
        try {

            ResultSet data = getBasketList(YearSession, StdSection);
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("BasketName"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
