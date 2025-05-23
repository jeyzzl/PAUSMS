/*
 * Created on 1/09/2005
 *
 */
package aca.ucas;

/**
 * @author Pedro
 *
 */
public class ProfesorVO {
    
    private String codigoPersonal,nombre,facultad,carrera;
    private double servicio,docencia,aporte,aregulares,aespeciales,total;
    
    public double getAespeciales() {
        return aespeciales;
    }
    public void setAespeciales(double aespeciales) {
        this.aespeciales = aespeciales;
    }
    public double getAporte() {
        return aporte;
    }
    public void setAporte(double aporte) {
        this.aporte = aporte;
    }
    public double getAregulares() {
        return aregulares;
    }
    public void setAregulares(double aregulares) {
        this.aregulares = aregulares;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }
    public double getDocencia() {
        return docencia;
    }
    public void setDocencia(double docencia) {
        this.docencia = docencia;
    }
    public String getFacultad() {
        return facultad;
    }
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getServicio() {
        return servicio;
    }
    public void setServicio(double servicio) {
        this.servicio = servicio;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}