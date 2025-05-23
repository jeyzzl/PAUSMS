package aca.attache.spring;

import java.sql.Date;

public class AttacheInvoiceDetailProduct {
    private Integer doctype;
    private Integer internaldocnum;
    private Integer status;
    private Double priceamt;
    private Double unitcost;
    private String code;
    private String description;
    private String cat;
    private String glset;
    private Integer detailnum;

    public AttacheInvoiceDetailProduct(){
        doctype         = null;
        internaldocnum  = null;
        status          = null;
        priceamt        = null;
        unitcost        = null;
        code            = null;
        description     = null;
        cat             = null;
        glset           = null;
        detailnum       = null;
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

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPriceamt() {
        return priceamt;
    }
    public void setPriceamt(Double priceamt) {
        this.priceamt = priceamt;
    }

    public Double getUnitcost() {
        return unitcost;
    }
    public void setUnitcost(Double unitcost) {
        this.unitcost = unitcost;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

    public Integer getDetailnum() {
        return detailnum;
    }
    public void setDetailnum(Integer detailnum) {
        this.detailnum = detailnum;
    }
    
}
