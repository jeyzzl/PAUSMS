package aca.attache.spring;

import java.sql.Date;

public class AttacheCustomerInvoiceTransaction {
    private String code;
    private String invnum;
    private String docnum;
    private String refer;
    private String cat;
    private Integer trantype;
    private Date trandate;
    private Double invamt;
    private Integer agecode;
    private Double invbal;
    private Date invdate;
    private String comment;

    public AttacheCustomerInvoiceTransaction(){
        code        = null;
        invnum      = null;
        docnum      = null;
        refer       = null;
        cat         = null;
        trantype    = null;
        trandate    = null;
        invamt      = null;
        agecode     = null;
        invbal      = null;
        invdate     = null;
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

    public String getCat() {
        return cat;
    }
    public void setCat(String cat) {
        this.cat = cat;
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

    public Integer getAgecode() {
        return agecode;
    }
    public void setAgecode(Integer agecode) {
        this.agecode = agecode;
    }

    public Double getInvbal() {
        return invbal;
    }
    public void setInvbal(Double invbal) {
        this.invbal = invbal;
    }

    public Date getInvdate() {
        return invdate;
    }
    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
