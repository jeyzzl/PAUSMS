// Clase para la tabla de Modulo
package aca.apFisica;
import java.sql.*;

public class ApFisicaAlumno{	
	private String grupoId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	
	// Constructor
	public ApFisicaAlumno(){
		grupoId			= "";
		codigoPersonal	= "";
		fecha			= "";
		estado			= "";
	}
	
	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		grupoId				= rs.getString("GRUPO_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fecha				= rs.getString("FECHA");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal, String grupoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT GRUPO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ESTADO"
					+ " FROM ENOC.APFISICA_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND GRUPO_ID = "+grupoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.ApFisicaAlumno|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
}