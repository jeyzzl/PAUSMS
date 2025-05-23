/*
 * Created on 8/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ucas;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValuesUca {
    public String codigoPersonal,cargaId,descripcion,nombre;
    public double ucas,valor,hSemanal,hPeriodo;
    public int bloqueId,id;
    public int getBloqueId() {
        return bloqueId;
    }
    public void setBloqueId(int bloqueId) {
        this.bloqueId = bloqueId;
    }
    public String getCargaId() {
        return cargaId;
    }
    public void setCargaId(String cargaId) {
        this.cargaId = cargaId;
    }
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getHPeriodo() {
        return hPeriodo;
    }
    public void setHPeriodo(double periodo) {
        hPeriodo = periodo;
    }
    public double getHSemanal() {
        return hSemanal;
    }
    public void setHSemanal(double semanal) {
        hSemanal = semanal;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getUcas() {
        return ucas;
    }
    public void setUcas(double ucas) {
        this.ucas = ucas;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public double getValor() {
        return valor;
    }
}