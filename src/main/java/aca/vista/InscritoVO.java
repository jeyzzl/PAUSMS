/*
 * Created on 1/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.vista;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InscritoVO {
    private String codigoPersonal,planId,nombre,fechaDeNac,modalidad,facultad,carrera;
    private String residencia,sexo,grado,semestre, tipoAlumnoId;
    
    
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
    public String getFacultad() {
        return facultad;
    }
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
    public String getFechaDeNac() {
        return fechaDeNac;
    }
    public void setFechaDeNac(String fechaDeNac) {
        this.fechaDeNac = fechaDeNac;
    }
    public String getGrado() {
        return grado;
    }
    public void setGrado(String grado) {
        this.grado = grado;
    }
    public String getModalidad() {
        return modalidad;
    }
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPlanId() {
        return planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }
    public String getResidencia() {
        return residencia;
    }
    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }
    public String getSemestre() {
        return semestre;
    }
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    public String getSexo() {
        return sexo;
    }
    public void setTipoAlumnoId(String tipoAlumnoId) {
        this.tipoAlumnoId = tipoAlumnoId;
    }
    public String getTipoAlumnoId() {
        return tipoAlumnoId;
    }
    
}