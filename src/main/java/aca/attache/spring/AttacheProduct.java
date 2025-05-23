package aca.attache.spring;

public class AttacheProduct {
    private String location;
    private String productgroup;
    private String code;
    private String description;
    private String glset;
    private String comment;
    private Double salesprice1;
    private Double unitcost;

    public AttacheProduct(){
        location        = null;
        productgroup    = null;
        code            = null;
        description     = null;
        glset           = null;
        comment         = null;
        salesprice1     = null;
        unitcost        = null;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductgroup() {
        return productgroup;
    }
    public void setProductgroup(String productgroup) {
        this.productgroup = productgroup;
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

    public String getGlset() {
        return glset;
    }
    public void setGlset(String glset) {
        this.glset = glset;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getSalesprice1() {
        return salesprice1;
    }
    public void setSalesprice1(Double salesprice1) {
        this.salesprice1 = salesprice1;
    }

    public Double getUnitcost() {
        return unitcost;
    }
    public void setUnitcost(Double unitcost) {
        this.unitcost = unitcost;
    }
    
}
