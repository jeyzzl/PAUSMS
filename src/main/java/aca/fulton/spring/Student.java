package aca.fulton.spring;

import java.util.List;
import java.math.BigDecimal;

public class Student {
    private String id;
    private String studentCode;
    private String name;
    private String email;
    private String sponsor;
    private boolean inactive;
    private BigDecimal balance;
    private String dc;
    private boolean sync;
    private List<StudentTransactions> transactions;

    public Student(){
        id          = "";
        studentCode = "";
        name        = "";
        email       = "";
        sponsor     = null;
        inactive    = false;
        balance     = null;
        dc          = "";
        sync        = false;
        transactions = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }    
    
    public String getSponsor() {
        return sponsor;
    }
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDc() {
        return dc;
    }
    public void setDc(String dc) {
        this.dc = dc;
    }

    public boolean isSync() {
        return sync;
    }
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public List<StudentTransactions> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<StudentTransactions> transactions) {
        this.transactions = transactions;
    }
}