// Bean de Carga_Grupo_Imp
package  aca.carga;

import java.sql.*;

public class CargaGrupoImp{
	private String grupoId;
	private String cursoId;
	private String maestro;
	private String alumnos;
	private String cursoCargaId;
	private String fecha;
	private String grupo;
	
	public CargaGrupoImp(){
		grupoId			= "";
		cursoId			= "";
		maestro			= "";
		alumnos			= "";
		cursoCargaId	= "";
		fecha			= "";
		grupo			= "";		
	}	
	
	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getMaestro() {
		return maestro;
	}

	public void setMaestro(String maestro) {
		this.maestro = maestro;
	}

	public String getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(String alumnos) {
		this.alumnos = alumnos;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		grupoId			= rs.getString("GRUPO_ID");
		cursoId 		= rs.getString("CURSO_ID");
		maestro			= rs.getString("MAESTRO");
		alumnos		 	= rs.getString("ALUMNOS");
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");
		fecha 			= rs.getString("FECHA");
		grupo			= rs.getString("GRUPO");		
	}			
	
	public void mapeaRegId( Connection conn, String grupoId ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"
					+ " GRUPO_ID,"
					+ " CURSO_ID,"
					+ " MAESTRO,"
					+ " ALUMNOS,"
					+ " CURSO_CARGA_ID,"
					+ " TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " GRUPO "
					+ " FROM ENOC.CARGA_GRUPO_IMP"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'999')"); 
			ps.setString(1, grupoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoImp|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
}