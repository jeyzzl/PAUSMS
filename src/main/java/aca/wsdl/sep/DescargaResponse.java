//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.0 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2020.01.29 a las 08:32:34 PM CST 
//


package aca.wsdl.sep;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DescargaResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "descargaResult"
})
@XmlRootElement(name = "DescargaResponse")
public class DescargaResponse {

    @XmlElement(name = "DescargaResult")
    protected String descargaResult;

    /**
     * Obtiene el valor de la propiedad descargaResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescargaResult() {
        return descargaResult;
    }

    /**
     * Define el valor de la propiedad descargaResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescargaResult(String value) {
        this.descargaResult = value;
    }

}
