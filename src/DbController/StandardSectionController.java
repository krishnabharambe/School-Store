/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import CMN.commonFile;
import CMN.standardsection;
import CMN.yearsession;
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
public class StandardSectionController {

    public static int checkIfAlreadyExistsSTDSection(String name) {
        try {
            int checkercount = 0;
            ResultSet data = db.getdata("SELECT COUNT(*) as checker FROM StandardSection WHERE stdsection  ='" + name + "'");
            checkercount = data.getInt("checker");
            data.close();
            return checkercount;
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static boolean AddSTDSection(String SessionName, String stdname) {
        return db.setdata("INSERT INTO StandardSection (yearsessionName,stdsection,updated_on,dated_on,timestamp)"
                + "VALUES('" + SessionName + "','" + stdname + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Date() + "','" + commonFile.Fun_Timestamp() + "')");
    }

    public static ResultSet getSTDSection(){
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM StandardSection order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     public static ResultSet getSTDSection(String yearsessionName){
        try {
            return db.getconnection().createStatement().executeQuery("SELECT * FROM StandardSection WHERE yearsessionName ='"+yearsessionName+"' order by id desc");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ObservableList<standardsection> getSTDSectionObservableList() {
        ObservableList<standardsection> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSTDSection();
            while (data.next()) {
                people.add(new standardsection(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
    
      public static ObservableList<standardsection> getSTDSectionObservableList(String yearsessionName) {
        ObservableList<standardsection> people = FXCollections.observableArrayList();
        try {
            ResultSet data = getSTDSection(yearsessionName);
            while (data.next()) {
                people.add(new standardsection(data.getString("id"), data.getString("yearsessionName"), data.getString("stdsection"), data.getString("updated_on"), data.getString("dated_on"), data.getString("timestamp")));
            }
            data.close();
        } catch (SQLException ex) {
            Logger.getLogger(YearSessionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return people;
    }
}
