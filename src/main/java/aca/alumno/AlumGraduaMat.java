package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumGraduaMat {
	private String codigoPersonal;
	private String cursoId;
	private String programada;
	private String comentario;
	
	public AlumGraduaMat(){
		codigoPersonal	= "";
		cursoId     	= "";
		programada		= "";
		comentario		= "";
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
	 * @return the programada
	 */
	public String getProgramada() {
		return programada;
	}

	/**
	 * @param programada the programada to set
	 */
	public void setProgramada(String programada) {
		this.programada = programada;
	}

	/**
	 * @param "conn" the connection of DB and "cargaId" the period
	*/
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cursoId      	= rs.getString("CURSO_ID");
		programada	 	= rs.getString("PROGRAMADA");
		comentario  	= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, CURSO_ID,"+
				" PROGRAMADA, " +
				" COMENTARIO " +
				" FROM ENOC.ALUM_GRADUA_MAT "+ 
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumGraduaMatUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}

}