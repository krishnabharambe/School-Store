/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbController;

import Database.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class LoginController {

    public static boolean authLogin(String username, String password) {
        try {
            int checker = 0;
            ResultSet data = db.getdata("SELECT count(*) as checker FROM UserManager WHERE username ='" + username + "' AND password = '" + password + "'");
            checker = data.getInt("checker");
            data.close();
            if (checker == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
