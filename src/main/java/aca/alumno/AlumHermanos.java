package aca.alumno;
import java.sql.*;

public class AlumHermanos{	
	private String familiaId;
	private String codigoPersonal;
	private String fecha;
	private String estado;
	
	public AlumHermanos(){
		familiaId		= "";
		codigoPersonal	= "";
		fecha			= "";
		estado			= "";
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
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
		familiaId 			= rs.getString("FAMILIA_ID");
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		fecha 				= rs.getString("FECHA");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String familiaId, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_HERMANOS WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setInt(1, Integer.parseInt(familiaId));
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
	public void mapeaRegIdPorMatricula( Connection conn, String matricula) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"
					+ " FROM ENOC.ALUM_HERMANOS"
					+ " WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|mapeaRegIdPorMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}