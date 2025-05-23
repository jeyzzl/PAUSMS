/*
 * Created on May 19, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package aca.kardex;

public class ConvalidarMaterias {

	public String cursoId;
	public String clave;
	public String nombre;
	public String fecha; 
	public int ciclo;
	public int nota;
	public String convalidacion;

	public ConvalidarMaterias( String cursoId, String clave, String nombre, String fecha, int ciclo, int nota, String convalidacion) {
		this.cursoId = cursoId;
		this.clave = clave;
		this.nombre = nombre;
		this.fecha = fecha;
		this.ciclo = ciclo;
		this.nota = nota;
		this.convalidacion = convalidacion;
	}
}