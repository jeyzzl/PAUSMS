/*
 * Created on 14/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

/**
 * @author 
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SsocDocAlum {
    String codigoPersonal 	= "0";
    String comentario 		= "-";
    String entregado 		= "N";
    String planId			= "0";
    int folio				= 0;
    int documentoId 		= 0;
    int asignacionId		= 0;
    int numHoras			= 0;
    String fecha 			= aca.util.Fecha.getHoy();
    String mes		 		= "-";
    String usuario			= "0";
    
    public String getEntregado() {
        return entregado;
    }
    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }
    public int getAsignacionId() {
        return asignacionId;
    }
    public void setAsignacionId(int asignacionId) {
        this.asignacionId = asignacionId;
    }
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public int getDocumentoId() {
        return documentoId;
    }
    public void setDocumentoId(int documentoId) {
        this.documentoId = documentoId;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getFolio() {
        return folio;
    }
    public void setFolio(int folio) {
        this.folio = folio;
    }
    public int getNumHoras() {
        return numHoras;
    }
    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }
	public String getPlanId() {
        return planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}