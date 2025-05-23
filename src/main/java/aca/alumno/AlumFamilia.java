package aca.alumno;
import java.sql.*;

public class AlumFamilia{	
	private String familiaId;
	private String fecha;
	private String estado;
	
	public AlumFamilia(){
		familiaId	= "";
		fecha		= "";
		estado		= "";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
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
		familiaId 			= rs.getString("FAMILIA_ID");
		fecha 				= rs.getString("FECHA");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String familiaId) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_FAMILIA WHERE FAMILIA_ID = ?"); 
			ps.setInt(1, Integer.parseInt(familiaId));
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}

}