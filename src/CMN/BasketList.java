/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CMN;

/**
 *
 * @author admin
 */
public class BasketList {

    String id,BasketName;

    public BasketList(String id, String BasketName) {
        this.id = id;
        this.BasketName = BasketName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBasketName() {
        return BasketName;
    }

    public void setBasketName(String BasketName) {
        this.BasketName = BasketName;
    }

   

}
