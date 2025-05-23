package aca.tit.spring;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "FirmaResponsables")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "firmaResponsable"
})
public class FirmaResponsables {

    @XmlElement(name = "FirmaResponsable", required = true)
    protected List<TitFirma> firmaResponsable;

    public List<TitFirma> getFirmaResponsable() {
        if (firmaResponsable == null) {
            firmaResponsable = new ArrayList<TitFirma>();
        }
        return this.firmaResponsable;
    }

	public void setFirmaResponsable(List<TitFirma> firmaResponsable) {
		this.firmaResponsable = firmaResponsable;
	}
}
