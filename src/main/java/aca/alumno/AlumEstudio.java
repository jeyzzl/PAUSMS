package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumEstudio {
	
	private String codigoPersonal;
	private String id;
	private String titulo;
	private String institucion;
	private String completo;
	private String inicio;
	private String fin;
	private String dependencia;
	private String convalida;
	
	public AlumEstudio() {
		codigoPersonal 	= "";
		id 				= "";
		titulo			= "";
		institucion 	= "";
		completo 		= "";
		inicio 			= "";
		fin 			= "";
		dependencia 	= "";
		convalida 		= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getConvalida() {
		return convalida;
	}

	public void setConvalida(String convalida) {
		this.convalida = convalida;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		id 				= rs.getString("ID");
		titulo			= rs.getString("TITULO");
		institucion 	= rs.getString("INSTITUCION");
		completo 		= rs.getString("COMPLETO");
		inicio 			= rs.getString("INICIO");
		fin 			= rs.getString("FIN");
		dependencia 	= rs.getString("DEPENDENCIA");
		convalida 		= rs.getString("CONVALIDA");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String id) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA "
									 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = "+codigoPersonal+" AND ID = "+id); 
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEstudioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}
