//Beans de la tabla ARCH_PERMISOS
package aca.archivo;

import java.sql.*;

public class ArchPermisos {
    private String Matricula;
	private String UsuarioAlta;
	private String UsuarioBaja;
	private String FechaIni;
	private String FechaLim;
	private String Estado;
	private String Folio;
	private String Comentario;
	
	public ArchPermisos(){
		Matricula 	= "";
		UsuarioAlta = "";
		UsuarioBaja = "";
		FechaIni 	= "";
		FechaLim 	= "";
		Estado 		= "";
		Folio		= "";
		Comentario  = "";
	}
	
	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getFechaIni() {
		return FechaIni;
	}

	public void setFechaIni(String fechaIni) {
		FechaIni = fechaIni;
	}

	public String getFechaLim() {
		return FechaLim;
	}

	public void setFechaLim(String fechaLim) {
		FechaLim = fechaLim;
	}

	public String getFolio() {
		return Folio;
	}

	public void setFolio(String folio) {
		Folio = folio;
	}

	public String getMatricula() {
		return Matricula;
	}

	public void setMatricula(String matricula) {
		Matricula = matricula;
	}

	public String getUsuarioAlta() {
		return UsuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		UsuarioAlta = usuarioAlta;
	}

	public String getUsuarioBaja() {
		return UsuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		UsuarioBaja = usuarioBaja;
	}
	
	public String getComentario() {
		return Comentario;
	}

	public void setComentario(String comentario) {
		Comentario = comentario;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		Matricula			= rs.getString("Matricula");
		UsuarioAlta			= rs.getString("UsuarioAlta");
		UsuarioBaja			= rs.getString("UsuarioBaja");
		FechaIni			= rs.getString("FechaIni");
		FechaLim			= rs.getString("FechaLim");
		Estado				= rs.getString("Estado");
		Folio				= rs.getString("Folio");
		Comentario			= rs.getString("Comentario");
	}		
	
	public static String getFechasPermiso( Connection conn, String matricula, String fecha) throws SQLException, Exception {
		Statement stmt 		= conn.createStatement();
		ResultSet rset 		= null;		
		String COMANDO		= "";
		String fechas		= "";
		
		COMANDO = "SELECT TO_CHAR (FECHA_INI,'DD-Mon')||' al '|| TO_CHAR (FECHA_LIM,'DD-Mon') AS FECHAS "+
			"FROM ENOC.ARCH_PERMISOS "+ 
			"WHERE MATRICULA = '"+matricula+"' "+			
			"AND TO_DATE('"+fecha+"','dd-mm-yy') between fecha_ini and fecha_lim";
		rset = stmt.executeQuery(COMANDO);	
		if (rset.next()){
			fechas = rset.getString("FECHAS");
		}else{
			fechas = "Sin permiso";
		}
		
		return fechas;
	} 
	
	public void mapeaRegId( Connection conn, String Matricula, String Folio) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT MATRICULA, USUARIO_ALTA, USUARIO_BAJA, " +
					"FECHA_INI, FECHA_LIM, ESTADO, FOLIO, COMENTARIO FROM ENOC.ARCH_PERMISOS " + 
					"WHERE MATRICULA = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1,Matricula);
			ps.setString(2,Folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchPermisosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}