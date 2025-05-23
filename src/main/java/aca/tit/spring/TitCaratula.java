package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "documentos",
    "activacion"
})
@XmlRootElement(name = "TitCaratulaCct")
public class TitCaratula {
	@XmlElement(name = "Documentos", required = true)
    protected TitDocumentos documentos;
    @XmlElement(name = "ActivacionCct", required = true)
    protected TitActivacion activacion;
    @XmlAttribute(name = "version", required = true)
    protected String version;
    @XmlAttribute(name = "fecha", required = true)
    protected String fecha;
    @XmlAttribute(name = "institucion", required = true)
    protected String institucion;
    @XmlAttribute(name = "noCertificado", required = true)
    protected String noCertificado;
    @XmlAttribute(name = "sello", required = true)
    protected String sello;
    @XmlAttribute(name = "noDocumentos", required = true)
    protected String noDocumentos;
   
    public TitDocumentos getDocumentos() {
    	return documentos;
    }
  
    public void setDocumentos(TitDocumentos value) {
    	this.documentos = value;
    }
    
    public TitActivacion getActivacionCct() {
    	return activacion;
    }
    
    public void setActivacionCct(TitActivacion value) {
    	this.activacion = value;
    }
    
    public String getVersion() {
    	if (version == null) {
    		return "1.0";
    	}else {
    		return version;
    	}
   }

   public void setVersion(String value) {
       this.version = value;
   }

   public String getFecha() {
       return fecha;
   }

   public void setFecha(String value) {
       this.fecha = value;
   }

   public String getInstitucion() {
       return institucion;
   }

   public void setInstitucion(String value) {
       this.institucion = value;
   }

   public String getNoCertificado() {
       return noCertificado;
   }

   public void setNoCertificado(String value) {
       this.noCertificado = value;
   }

   public String getSello() {
       return sello;
   }

   public void setSello(String value) {
       this.sello = value;
   }

   public String getNoDocumentos() {
       return noDocumentos;
   }

   public void setNoDocumentos(String value) {
       this.noDocumentos = value;
   }

}