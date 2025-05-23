package aca.attache.spring;

import java.sql.Date;

public class AttacheCustomerAdjustmentTransaction {
    private String code;
    private String invnum;
    private String docnum;
    private String refer;
    private Integer trantype;
    private Date trandate;
    private Double invamt;
    private Double totaladjustamt;
    private Date invdate;
    private Integer agecode;
    private String comment;

    public AttacheCustomerAdjustmentTransaction(){
        code        = null;
        invnum      = null;
        docnum      = null;
        refer       = null;
        trantype    = null;
        trandate    = null;
        invamt      = null;
        totaladjustamt = null;
        invdate     = null;
        agecode     = null;
        comment     = null;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getInvnum() {
        return invnum;
    }
    public void setInvnum(String invnum) {
        this.invnum = invnum;
    }

    public String getDocnum() {
        return docnum;
    }
    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getRefer() {
        return refer;
    }
    public void setRefer(String refer) {
        this.refer = refer;
    }

    public Integer getTrantype() {
        return trantype;
    }
    public void setTrantype(Integer trantype) {
        this.trantype = trantype;
    }

    public Date getTrandate() {
        return trandate;
    }
    public void setTrandate(Date trandate) {
        this.trandate = trandate;
    }

    public Double getInvamt() {
        return invamt;
    }
    public void setInvamt(Double invamt) {
        this.invamt = invamt;
    }

    public Double getTotaladjustamt() {
        return totaladjustamt;
    }
    public void setTotaladjustamt(Double totaladjustamt) {
        this.totaladjustamt = totaladjustamt;
    }

    public Date getInvdate() {
        return invdate;
    }
    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public Integer getAgecode() {
        return agecode;
    }
    public void setAgecode(Integer agecode) {
        this.agecode = agecode;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
