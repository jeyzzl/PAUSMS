package aca.attache.spring;

import java.sql.Date;

public class AttacheCustomerPaymentTransaction {
    private String code;
    private String invnum;
    private String docnum;
    private String refer;
    private Integer trantype;
    private Date trandate;
    private Double invamt;
    private Double totalpayamt;
    private Date invdate;
    private Integer agecode;
    private String comment;

    public AttacheCustomerPaymentTransaction(){
        code        = null;
        invnum      = null;
        docnum      = null;
        refer       = null;
        trantype    = null;
        trandate    = null;
        invamt      = null;
        totalpayamt = null;
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

    public Double getTotalpayamt() {
        return totalpayamt;
    }
    public void setTotalpayamt(Double totalpayamt) {
        this.totalpayamt = totalpayamt;
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
