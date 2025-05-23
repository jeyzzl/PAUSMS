/*
 * Created on 14/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit;


/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DocAlumVO {
    String tituloId,comentario,entregado,codigoPersonal;
    int documentoId;
    double cantidad;
    String fecha;
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public String getTituloId() {
        return tituloId;
    }
    public void setTituloId(String tituloId) {
        this.tituloId = tituloId;
    }
    public String getEntregado() {
        return entregado;
    }
    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }
    public double getCantidad() {
        return cantidad;
    }
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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
}