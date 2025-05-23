package aca.fulton.spring;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StudentTransactionsTemp {

    private String studentId;
    private String period;
    private String date;
    private String type;
    private String reference;
    private BigDecimal amount;
    private String dc;
    private String description;

    public StudentTransactionsTemp(){
        studentId   = "";
        period      = "";
        date        = null;
        type        = "";
        reference   = "";
        amount      = null;
        dc          = "";
        description = "";
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDc() {
        return dc;
    }
    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

}
  