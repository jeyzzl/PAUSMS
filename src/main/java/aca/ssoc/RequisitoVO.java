/*
 * Created on 13/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequisitoVO {
    String nombre,obligatorio;
    int orden,id;
    public String getObligatorio() {
        return obligatorio;
    }
    public void setObligatorio(String obligatorio) {
        this.obligatorio = obligatorio;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getOrden() {
        return orden;
    }
    public void setOrden(int orden) {
        this.orden = orden;
    }
}