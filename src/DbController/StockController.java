/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.Stock;
import CMN.commonFile;
import CMN.yearsession;
import Database.db;
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
public class StockController {

    String id, yearsessionName, stdsection, Stockname, price, remainQuantity, soldquantity, updated_on, dated_on, timestamp;

    /**
     * *
     *
     * @param yearsessionName
     * @param stdsection
     * @param Stockname
     * @param price
     * @param remainQuantity
     * @param soldquantity
     * @return
     */
    public static boolean addStock(String yearsessionName, String stdsection, String Stockname, String price, String remainQuantity, String soldquantity) {
        return db.setdata("INSERT INTO Stock(yearsessionName, stdsection, Stockname, price, remainQuantity, soldquantity, updated_on, dated_on, timestamp)"
                + "VALUES('" + yearsessionName + "', '" + stdsection + "', '" + Stockname + "', '" + price + "', '" + remainQuantity + "', '" + soldquantity + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Date() + "', '" + commonFile.Fun_Timestamp() + "')");
    }

    /**
     * *
     *
     * @param id
     * @param yearsessionName
     * @param stdsection
     * @param Stockname
     * @param price
     * @return
     */
    public static boolean updateStock(String id, String yearsessionName, String stdsection, String Stockname, String price) {
        return db.setdata("UPDATE Stock SEt id='" + id + "', yearsessionName='" + yearsessionName + "', stdsection='" + stdsection + "', Stockname='" + Stockname + "', price='" + price + "' WHERE id ='" + id + "'");
    }

    /**
     * *
     *
     * @param id
     * @param Quantity
     * @return
     */
    public static boolean AddAdditionalStock(String id, String Quantity) {
        return db.setdata("Update Stock Set remainQuantity=remainQuantity+'" + Quantity + "' WHERE id ='" + id + "'");
    }

    /**
     * *
     *
     * @param id
     * @param Quantity
     * @return
     */
    public static boolean UpdateRemainStock(String id, String Quantity) {
        return db.setdata("Update Stock Set remainQuantity='" + Quantity + "' WHERE id ='" + id + "'");
    }

    /**
     * *
     *
     * @param id
     * @param Quantity
     * @return
     */
    public static boolean UpdateSoldStock(String id, String Quantity) {
        return db.setdata("Update Stock Set soldquantity='" + Quantity + "' WHERE id ='" + id + "'");
    }
    
    public static boolean DeleteStock(String id){
    return db.setdata("Delete from stock where id = '"+id+"'");
    }

    public static ResultSet getStock() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Stock order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ResultSet getStock(String yearsession, String stdSection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM Stock WHERE yearsessionName ='" + yearsession + "' AND stdsection='" + stdSection + "'");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<Stock> getStockObservableList() {
        ObservableList<Stock> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getStock();
            while (data.next()) {
                people.add(new Stock(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Stockname"), data.getString("price"), data.getString("remainQuantity"), data.getString("soldquantity"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static ObservableList<Stock> getStockObservableList(String yearsession, String stdSection) {
        ObservableList<Stock> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getStock(yearsession, stdSection);
            while (data.next()) {
                people.add(new Stock(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("Stockname"), data.getString("price"), data.getString("remainQuantity"), data.getString("soldquantity"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

//    public static String[] getAllStockName() {
//        try {
//
//            ResultSet data = getStock();
//            final List<String> timeStr = new ArrayList<>();
//            while (data.next()) {
//                // Just get the value of the column, and add it to the list
//                timeStr.add(data.getString("Stockname"));
//            }
//
//            // I would return the list here, but let's convert it to an array
//            return timeStr.toArray(new String[timeStr.size()]);
//        } catch (SQLException ex) {
//            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    
    public static String[] getAllStockName(String yearsession,String stdsection) {
        try {

            ResultSet data = getStock(yearsession,stdsection);
            final List<String> timeStr = new ArrayList<>();
            while (data.next()) {
                // Just get the value of the column, and add it to the list
                timeStr.add(data.getString("Stockname"));
            }

            // I would return the list here, but let's convert it to an array
            return timeStr.toArray(new String[timeStr.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static double getStockPriceFromName(String yearsession, String stdsection, String stockname) {
        double price = 0;
        try {
            ResultSet data = db.getdata("Select price from Stock WHERE yearsessionName='" + yearsession + "' AND stdsection='" + stdsection + "' AND Stockname='" + stockname + "'");
            price = data.getDouble("price");
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return price;
    }

    public static boolean newStockSale(String yearsessionName, String stdsection, String stockname, String quantity) {
        return db.setdata("UPDATE stock SET remainQuantity = remainQuantity - '" + quantity + "',soldquantity = soldquantity+'" + quantity + "' WHERE yearsessionName='" + yearsessionName + "' AND stdsection='" + stdsection + "' AND Stockname='" + stockname + "'");
    }

    public static boolean deleteStockSale(String yearsessionName, String stdsection, String stockname, String quantity) {
        return db.setdata("UPDATE stock SET remainQuantity = remainQuantity + '" + quantity + "',soldquantity = soldquantity-'" + quantity + "' WHERE yearsessionName='" + yearsessionName + "' AND stdsection='" + stdsection + "' AND Stockname='" + stockname + "'");
    }

}
