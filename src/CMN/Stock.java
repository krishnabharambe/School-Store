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
public class Stock {
        String id,yearsessionName, stdsection, Stockname, price, remainQuantity, soldquantity, updated_on, dated_on, timestamp;

    public Stock(String id, String yearsessionName, String stdsection, String Stockname, String price, String remainQuantity, String soldquantity, String updated_on, String dated_on, String timestamp) {
        this.id = id;
        this.yearsessionName = yearsessionName;
        this.stdsection = stdsection;
        this.Stockname = Stockname;
        this.price = price;
        this.remainQuantity = remainQuantity;
        this.soldquantity = soldquantity;
        this.updated_on = updated_on;
        this.dated_on = dated_on;
        this.timestamp = timestamp;
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

    public String getStockname() {
        return Stockname;
    }

    public void setStockname(String Stockname) {
        this.Stockname = Stockname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(String remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getSoldquantity() {
        return soldquantity;
    }

    public void setSoldquantity(String soldquantity) {
        this.soldquantity = soldquantity;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getDated_on() {
        return dated_on;
    }

    public void setDated_on(String dated_on) {
        this.dated_on = dated_on;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    

}
