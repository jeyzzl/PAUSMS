package aca.dherst.spring;

public class DherstAlumno {
    private String folio;
    private String codigoPersonal;
    private String slfNo;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String telefono;
    private String celular;
    private String gpa;
    private String sexo;
    private String residencia;
    private String resEstadoId;
    private String estadoId;
    private String religionId;
    private String prefAeropuerto;
    private String estadoCivil;
    private String residenciaTipo;
    private String planId;
    private String status;

    public DherstAlumno(){
        folio           = "0";
        codigoPersonal  = "0";
        slfNo           = "0";
        nombre          = "";
        apellido        = "";
        direccion       = "";
        email           = "";
        telefono        = "";
        celular         = "";
        gpa             = "0";
        sexo            = "";
        residencia      = "";
        resEstadoId     = "0";
        estadoId        = "0";
        religionId      = "0";
        prefAeropuerto  = "";
        estadoCivil     = "";
        residenciaTipo  = "";
        planId          = "";
        status          = "I";
    }

    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCodigoPersonal() {
        return codigoPersonal;
    }
    public void setCodigoPersonal(String codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public String getSlfNo() {
        return slfNo;
    }
    public void setSlfNo(String slfNo) {
        this.slfNo = slfNo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getGpa() {
        return gpa;
    }
    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getResidencia() {
        return residencia;
    }
    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getResEstadoId() {
        return resEstadoId;
    }
    public void setResEstadoId(String resEstadoId) {
        this.resEstadoId = resEstadoId;
    }

    public String getEstadoId() {
        return estadoId;
    }
    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getReligionId() {
        return religionId;
    }
    public void setReligionId(String religionId) {
        this.religionId = religionId;
    }

    public String getPrefAeropuerto() {
        return prefAeropuerto;
    }
    public void setPrefAeropuerto(String prefAeropuerto) {
        this.prefAeropuerto = prefAeropuerto;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getResidenciaTipo() {
        return residenciaTipo;
    }
    public void setResidenciaTipo(String residenciaTipo) {
        this.residenciaTipo = residenciaTipo;
    }

    public String getPlanId() {
        return planId;
    }
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
