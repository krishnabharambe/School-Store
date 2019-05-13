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
public class Student {
    String id,yearsessionName,stdsection,Rollno,fullname,contact,address,updated_on,dated_on,timestamp;

    public Student(String id, String yearsessionName, String stdsection, String Rollno, String fullname, String contact, String address, String updated_on, String dated_on, String timestamp) {
        this.id = id;
        this.yearsessionName = yearsessionName;
        this.stdsection = stdsection;
        this.Rollno = Rollno;
        this.fullname = fullname;
        this.contact = contact;
        this.address = address;
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

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String Rollno) {
        this.Rollno = Rollno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
