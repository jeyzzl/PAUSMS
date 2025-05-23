package aca.tit.spring;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "TituloElectronico")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType( propOrder = {
    "firmaResponsables",
    "institucion",
    "carrera",
    "profesionista",
    "expedicion",
    "antecedente",
    "autenticacion"
})
public class TituloElectronico {	
	@XmlElement(name = "FirmaResponsables", required = true)
	protected FirmaResponsables firmaResponsables;
	@XmlElement(name = "Institucion", required = true)
    protected TitInstitucion institucion;
    @XmlElement(name = "Carrera", required = true)
    protected TitCarrera carrera;
    @XmlElement(name = "Profesionista", required = true)
    protected TitProfesional profesionista;
    @XmlElement(name = "Expedicion", required = true)
    protected TitExpedicion  expedicion;
    @XmlElement(name = "Antecedente", required = true)
    protected TitAntecedente antecedente;
    @XmlElement(name = "Autenticacion")
    protected Autenticacion autenticacion;    
    @XmlAttribute(name = "version", required = true)    
    protected String version;
    @XmlAttribute(name = "folioControl", required = true)
    protected String folioControl;
    
    public TituloElectronico() {    	
    	version 			= "1.0";
    	folioControl 		= "0";    	
    }
    
	public FirmaResponsables getFirmaResponsables() {
        return firmaResponsables;
    }

    public void setFirmaResponsables(FirmaResponsables value) {
        this.firmaResponsables = value;
    }    
     
    public TitInstitucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(TitInstitucion value) {
        this.institucion = value;
    }

    public TitCarrera getCarrera() {
        return carrera;
    }

    public void setCarrera(TitCarrera value) {
        this.carrera = value;
    }

    public TitProfesional getProfesionista() {
        return profesionista;
    }
    
    public void setProfesionista(TitProfesional value) {
        this.profesionista = value;
    }

    public TitExpedicion getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(TitExpedicion value) {
        this.expedicion = value;
    }

    public TitAntecedente getAntecedente() {
        return antecedente;
    }

    public void setAntecedente(TitAntecedente value) {
        this.antecedente = value;
    }

    public Autenticacion getAutenticacion() {
        return autenticacion;
    }

    public void setAutenticacion(Autenticacion value) {
        this.autenticacion = value;
    }
          
    public String getVersion() {       
            return version;       
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public String getFolioControl() {
        return folioControl;
    }

    public void setFolioControl(String value) {
        this.folioControl = value;
    }
}