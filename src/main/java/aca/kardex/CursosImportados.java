
package aca.kardex;
//import java.sql.*;
//import java.text.*;

public class CursosImportados {
	public String codigoPersonal;
	public String cursoId;
	public String cursoId2;
	public int ciclo;
	public int folio;
	public String fCreada;
	public String convalidacion;
	public String titulo;
	public String optativa;
	public String tipocalId;
	public int nota;
	public int notaExtra;
	public String fExtra;
	public String observaciones;
	public String notaConva;
	public String usuario;
	public String fecha;
	
	public CursosImportados(String codigoPersonal, String cursoId, String cursoId2, int ciclo, int folio, String fCreada, String convalidacion, String titulo, String optativa, String tipocalId, int nota, int notaExtra, String fExtra, String observaciones, String notaConva, String usuario, String fecha){
		
		this.codigoPersonal	= codigoPersonal;
		this.cursoId		= cursoId;
		this.cursoId2		= cursoId2;
		this.ciclo			= ciclo;
		this.folio			= folio;
		this.fCreada		= fCreada;
		this.convalidacion	= convalidacion;
		this.titulo			= titulo;
		this.optativa		= optativa;
		this.tipocalId		= tipocalId;			
		this.nota			= nota;
		this.notaExtra		= notaExtra;
		this.fExtra			= fExtra;
		this.observaciones	= observaciones;
		this.notaConva		= notaConva;
		this.usuario		= usuario;
		this.fecha			= fecha;
	}	

}