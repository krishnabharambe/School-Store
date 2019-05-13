/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.BasketValue;
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
public class BasketValueController {

    public static ResultSet getBasketValue(String yearsession, String stdsection) {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM basketvalue WHERE yearsessionName='" + yearsession + "' AND stdsection='" + stdsection + "' GROUP BY BasketName");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BasketValueController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BasketValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<BasketValue> getBasketValueObservableList(String yearsession, String stdsection) {
        ObservableList<BasketValue> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getBasketValue(yearsession, stdsection);
            while (data.next()) {
                people.add(new BasketValue("id", data.getString("yearsessionName"), data.getString("stdsection"), data.getString("BasketName"), data.getString("basketvalue")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(BasketValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }

    public static String getBasketValue(String yearsession, String stdsection, String BasketName) {
        try {
            String tr = "00.00";
            ResultSet data = db.getdata("Select * from basketvalue WHERE yearsessionName='" + yearsession + "' AND stdsection='" + stdsection + "' AND basketname='" + BasketName + "'");
            tr = data.getString("basketvalue");
            data.close();

            return tr;
        } catch (SQLException ex) {
            Logger.getLogger(BasketValueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
