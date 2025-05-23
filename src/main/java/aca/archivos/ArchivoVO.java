/*
 * Created on 14/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.archivos;

/**
 * @author pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ArchivoVO {
	private String id,codigoPersonal,nombre,comentario,autorizacion,nombreAlumno,estrategia,status,nombreProfesor;
	private String fecha;
	private int folio;
	private long tamano;
   
    public String getNombreProfesor() {
        return nombreProfesor;
    }
    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public long getTamano() {
        return tamano;
    }
    public void setTamano(long tamano) {
        this.tamano = tamano;
    }
    public String getEstrategia() {
        return estrategia;
    }
    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }
    public String getNombreAlumno() {
        return nombreAlumno;
    }
    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }
    public String getAutorizacion() {
        return autorizacion;
    }
    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
	/**
	 * @return Returns the codigoPersona.
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	/**
	 * @param codigoPersona The codigoPersona to set.
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return Returns the folio.
	 */
	public int getFolio() {
		return folio;
	}
	/**
	 * @param folio The folio to set.
	 */
	public void setFolio(int folio) {
		this.folio = folio;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}