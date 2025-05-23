package aca.attache.spring;

import java.sql.Date;

public class AttacheInvoiceHeader {
    private Integer doctype;
    private Integer internaldocnum;
    private Date docdate;
    private String code;
    private String docnum;
    private String cat;
    private String glset;

    public AttacheInvoiceHeader(){
        doctype         = null;
        internaldocnum  = null;
        docdate         = null;
        code            = null;
        docnum          = null;
        cat             = null;
        glset           = null;
    }

    public Integer getDoctype() {
        return doctype;
    }
    public void setDoctype(Integer doctype) {
        this.doctype = doctype;
    }

    public Integer getInternaldocnum() {
        return internaldocnum;
    }
    public void setInternaldocnum(Integer internaldocnum) {
        this.internaldocnum = internaldocnum;
    }

    public Date getDocdate() {
        return docdate;
    }
    public void setDocdate(Date docdate) {
        this.docdate = docdate;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDocnum() {
        return docnum;
    }
    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getCat() {
        return cat;
    }
    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getGlset() {
        return glset;
    }
    public void setGlset(String glset) {
        this.glset = glset;
    }  

}
