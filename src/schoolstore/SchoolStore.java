/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolstore;

import Database.db;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class SchoolStore extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));

        Scene scene = new Scene(root);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public SchoolStore() {
        db.setdata("CREATE TABLE IF NOT EXISTS UserManager(id integer primary key autoincrement,username text,password text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS YEARSESSION(id integer primary key autoincrement,yearsessionName text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS StandardSection(id integer primary key autoincrement,yearsessionName text,stdsection text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS Student(id integer primary key autoincrement,yearsessionName text,stdsection text,Rollno integer,fullname text,contact text,address text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS Stock(id integer primary key autoincrement,yearsessionName text,stdsection text,Stockname text,price text,remainQuantity text,soldquantity text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS basket(id integer primary key autoincrement,yearsessionName text,stdsection text,Stockname text,price text,Quantity text,BasketName text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS sales(id integer primary key autoincrement,yearsessionName text,stdsection text,studentname text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS indisales(id integer primary key autoincrement,yearsessionName text,stdsection text,studentname text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS salesTranscation(id integer primary key autoincrement,yearsessionName text,stdsection text,saleid text,studentname text,stockname text,quantity text,price text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS indisalesTranscation(id integer primary key autoincrement,yearsessionName text,stdsection text,saleid text,studentname text,stockname text,quantity text,price text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS ReportsalesTranscation(id integer primary key autoincrement,yearsessionName text,stdsection text,saleid text,studentname text,stockname text,quantity text,price text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS basketValue(id integer primary key autoincrement,yearsessionName text,stdsection text,BasketName text,basketvalue text)");
        db.setdata("CREATE TABLE IF NOT EXISTS sales2(id integer primary key autoincrement,yearsessionName text,stdsection text,studentname text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS indisales2(id integer primary key autoincrement,saleid text,yearsessionName text,stdsection text,studentname text,updated_on text,dated_on text,timestamp text)");
        db.setdata("CREATE TABLE IF NOT EXISTS IndiSalesPrices(id integer primary key autoincrement,yearsessionName text,stdsection text,indisaleid text,total text,updated_on text,dated_on text,timestamp text)");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
