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
public class BasketValue {
  String id,yearsessionName,stdsection,BasketName,basketValue;  

    public BasketValue(String id, String yearsessionName, String stdsection, String BasketName, String basketValue) {
        this.id = id;
        this.yearsessionName = yearsessionName;
        this.stdsection = stdsection;
        this.BasketName = BasketName;
        this.basketValue = basketValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYearsessionName() {
        return yearsessionName;
    }

    public void setYearsessionName(String yearsessionName) {
        this.yearsessionName = yearsessionName;
    }

    public String getStdsection() {
        return stdsection;
    }

    public void setStdsection(String stdsection) {
        this.stdsection = stdsection;
    }

    public String getBasketName() {
        return BasketName;
    }

    public void setBasketName(String BasketName) {
        this.BasketName = BasketName;
    }

    public String getBasketValue() {
        return basketValue;
    }

    public void setBasketValue(String basketValue) {
        this.basketValue = basketValue;
    }
  
  
}
