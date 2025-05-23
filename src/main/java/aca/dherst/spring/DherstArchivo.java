package aca.dherst.spring;

public class DherstArchivo {
    private String folio;
    private String nombre;
    private String fecha;
    private String comentario;
    private byte[] archivo;
    private String codigoUsuario;

    public DherstArchivo(){
        folio           = "0";
        nombre          = "";
        fecha           = "";
        comentario      = "";
        archivo         = null;
        codigoUsuario   = "";
    }

    public String getFolio() {
        return folio;
    }
    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getArchivo() {
        return archivo;
    }
    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }
    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
    
}