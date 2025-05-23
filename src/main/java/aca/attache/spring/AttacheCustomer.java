package aca.attache.spring;

public class AttacheCustomer {
    private String code;
    private String name;
    private String acctype;
    private String glset;
    private String cat;
    private Double openbal;
    private Double currentbal;
    private Double period1bal;
    private Double period2bal;
    private Double period3bal;
    private Double unallocbal;
    private Double postdatebal; 
    private String sort;
    private Double discperc;

    public AttacheCustomer(){
        code        = null;
        name        = null;
        acctype     = null;
        glset       = null;
        cat         = null;
        openbal     = null;
        currentbal  = null;
        period1bal  = null;
        period2bal  = null;
        period3bal  = null;
        unallocbal  = null;
        postdatebal = null;
        sort        = null;
        discperc    = null;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAcctype() {
        return acctype;
    }
    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public String getGlset() {
        return glset;
    }
    public void setGlset(String glset) {
        this.glset = glset;
    }

    public String getCat() {
        return cat;
    }
    public void setCat(String cat) {
        this.cat = cat;
    }

    public Double getOpenbal() {
        return openbal;
    }
    public void setOpenbal(Double openbal) {
        this.openbal = openbal;
    }

    public Double getCurrentbal() {
        return currentbal;
    }
    public void setCurrentbal(Double currentbal) {
        this.currentbal = currentbal;
    }

    public Double getPeriod1bal() {
        return period1bal;
    }

    public void setPeriod1bal(Double period1bal) {
        this.period1bal = period1bal;
    }

    public Double getPeriod2bal() {
        return period2bal;
    }

    public void setPeriod2bal(Double period2bal) {
        this.period2bal = period2bal;
    }

    public Double getPeriod3bal() {
        return period3bal;
    }

    public void setPeriod3bal(Double period3bal) {
        this.period3bal = period3bal;
    }

    public Double getUnallocbal() {
        return unallocbal;
    }

    public void setUnallocbal(Double unallocbal) {
        this.unallocbal = unallocbal;
    }

    public Double getPostdatebal() {
        return postdatebal;
    }

    public void setPostdatebal(Double postdatebal) {
        this.postdatebal = postdatebal;
    }

    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }

    public Double getDiscperc() {
        return discperc;
    }
    public void setDiscperc(Double discperc) {
        this.discperc = discperc;
    }
    
}
