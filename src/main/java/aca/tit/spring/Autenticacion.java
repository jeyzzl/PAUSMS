package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


@XmlRootElement(name = "Autenticacion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "any"
})
public class Autenticacion {

    @XmlAnyElement(lax = true)
    protected Object any;

    public Object getAny() {
        return any;
    }

    public void setAny(Object value) {
        this.any = value;
    }

}
