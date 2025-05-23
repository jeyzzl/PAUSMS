//Bean de la tablas KRDX_CURSO_ACT 
package  aca.kardex;

import java.sql.*;

public class KrdxAlumnoTitulo{
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String carreraId;
	private String presidente;
	private String secretario;
	private String miembro;
	private String comentario;
	private String nota;
	private String usuario;
	private String estado;
	
	public KrdxAlumnoTitulo(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		cursoId			= "";
		carreraId		= "";
		presidente		= "";
		secretario		= "";
		miembro 		= "";
		comentario		= "";
		nota			= "";
		usuario			= "";
		estado			= "";
		
	}
	

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @param carreraId the carreraId to set
	 */
	public void setCarreraId(String carreraId) {
		this.carreraId = carreraId;
	}
	
	/**
	 * @return the presidente
	 */
	public String getPresidente() {
		return presidente;
	}
	
	/**
	 * @param presidente the presidente to set
	 */
	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	/**
	 * @return the secretario
	 */
	public String getSecretario() {
		return secretario;
	}

	/**
	 * @param secretario the secretario to set
	 */
	public void setSecretario(String secretario) {
		this.secretario = secretario;
	}

	/**
	 * @return the miembro
	 */
	public String getMiembro() {
		return miembro;
	}

	/**
	 * @param miembro the miembro to set
	 */
	public void setMiembro(String miembro) {
		this.miembro = miembro;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}

	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @param conn the connection.
	 */
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_ALUMNO_TITULO"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, "+
				"SECRETARIO, MIEMBRO, COMENTARIO, NOTA, USUARIO, ESTADO) "+
				"VALUES( ?, ?, ?, ?, ?, "+
				"?,?,?, "+	
				"TO_NUMBER(?,'999'), "+
				"?,?) ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			ps.setString(4, carreraId);
			ps.setString(5, presidente);
			ps.setString(6, secretario);
			ps.setString(7, miembro);
			ps.setString(8, comentario);
			ps.setString(9, nota);
			ps.setString(10, usuario);
			ps.setString(11, estado);

						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoTitulo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	/**
	 * @param conn the connection.
	 */
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO_TITULO"+ 
				" SET "+
				" CURSO_ID = ?,"+
				" CARRERA_ID = ?,"+
				" PRESIDENTE  = ?,"+
				" SECRETARIO = ?,"+
				" MIEMBRO = ?,"+
				" COMENTARIO = ?,"+
				" NOTA = TO_NUMBER(?,'999'),"+
				" USUARIO= ?,"+
				" ESTADO = ? "+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_CARGA_ID = ?");			
			ps.setString(1, cursoId);
			ps.setString(2, carreraId);
			ps.setString(3, presidente);
			ps.setString(4, secretario);
			ps.setString(5, miembro);
			ps.setString(6, comentario);
			ps.setString(7, nota);
			ps.setString(8, usuario);
			ps.setString(9, estado);
			ps.setString(10,codigoPersonal);			
			ps.setString(11,cursoCargaId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoTitulo|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	/**
	 * @param conn the connection.
	 */
	
	/**
	 * @param conn The connection to set.
	 */		
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_TITULO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumnoTitulo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	/**
	 * @param "conn" the connection of DB and "cargaId" the period
	 */		
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		cursoId	 		= rs.getString("CURSO_ID");
		carreraId		= rs.getString("CARRERA_ID");
		presidente		= rs.getString("PRESIDENTE");
		secretario 		= rs.getString("SECRETARIO");
		miembro 		= rs.getString("MIEMBRO");
		comentario 		= rs.getString("COMENTARIO");
		nota 			= rs.getString("NOTA");
		usuario			= rs.getString("USUARIO");
		estado			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoCargaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CARRERA_ID, PRESIDENTE, "+
				"SECRETARIO, MIEMBRO, " +
				"COMENTARIO, " +
				"NOTA, " +
				"USUARIO, " +
				"ESTADO " +
				"FROM ENOC.KRDX_ALUMNO_TITULO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaCursoAprobado( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				"NOTA, COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, " +
				"TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"TIPO, TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"COMENTARIO, CORRECCION "+
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?" +
				" AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|mapeaCursoAprobado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO_TITULO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	
}