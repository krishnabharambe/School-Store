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
public class CommonController {

    public static int getinditotal(String saleid) {
        try {
            int ret = 0;
            ResultSet data = db.getdata("Select total FROM IndiSalesPrices WHERE saleid ='" + saleid + "'");
            ret = data.getInt("total");
            data.close();
            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CommonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
