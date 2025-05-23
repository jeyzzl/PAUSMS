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
 *         &lt;element name="Recibo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClaveDGP" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Archivo_Nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "recibo",
    "claveDGP",
    "archivoNombre"
})
@XmlRootElement(name = "Descarga")
public class Descarga {

    @XmlElement(name = "Recibo")
    protected String recibo;
    @XmlElement(name = "ClaveDGP")
    protected int claveDGP;
    @XmlElement(name = "Archivo_Nombre")
    protected String archivoNombre;

    /**
     * Obtiene el valor de la propiedad recibo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecibo() {
        return recibo;
    }

    /**
     * Define el valor de la propiedad recibo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecibo(String value) {
        this.recibo = value;
    }

    /**
     * Obtiene el valor de la propiedad claveDGP.
     * 
     */
    public int getClaveDGP() {
        return claveDGP;
    }

    /**
     * Define el valor de la propiedad claveDGP.
     * 
     */
    public void setClaveDGP(int value) {
        this.claveDGP = value;
    }

    /**
     * Obtiene el valor de la propiedad archivoNombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArchivoNombre() {
        return archivoNombre;
    }

    /**
     * Define el valor de la propiedad archivoNombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArchivoNombre(String value) {
        this.archivoNombre = value;
    }

}
