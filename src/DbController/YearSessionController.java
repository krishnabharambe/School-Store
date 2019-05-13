/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.commonFile;
import CMN.yearsession;
import Database.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author admin
 */
public class YearSessionController {

    public static int checkIfAlreadyExists(String SessionName) {
        try {
            int checkercount = 0;
            ResultSet data = db.getdata("SELECT COUNT(*) as checker FROM YEARSESSION WHERE yearsessionName ='" + SessionName + "'");
            checkercount = data.getInt("checker");
            data.close();
            return checkercount;
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static boolean AddYearSession(String SessionName) {
        return db.setdata("INSERT INTO YEARSESSION (yearsessionName,updated_on,dated_on,timestamp)"
                + "VALUES('" + SessionName + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static ResultSet getYearSession() {
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM YEARSESSION order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<yearsession> getYearSessionObservableList() {
        ObservableList<yearsession> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getYearSession();
            while (data.next()) {
                people.add(new yearsession(data.getString("id"), data.getString("yearsessionName"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
}
