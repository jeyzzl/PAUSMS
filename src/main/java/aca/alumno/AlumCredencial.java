// Bean de Alum_Foto
package  aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumCredencial{
	private String codigoPersonal;
	private String nombres;	
	private String apellidos;
	private String carrera;
	private String cotejado;
	private String fecha;
	private byte[] foto;
	private String periodo1;
	private String periodo2;
	private String periodo3;
		
	public AlumCredencial(){
		codigoPersonal	= "";
		nombres			= "";		
		apellidos		= "";
		carrera			= "";
		cotejado 		= ""; 
		fecha			= "";
		foto			= null;
		periodo1 		= "";
		periodo2 		= "";
		periodo3 		= "";
		
	}		
		
	/**
	 * @return the periodo1
	 */
	public String getPeriodo1() {
		return periodo1;
	}

	/**
	 * @param periodo1 the periodo1 to set
	 */
	public void setPeriodo1(String periodo1) {
		this.periodo1 = periodo1;
	}

	/**
	 * @return the periodo2
	 */
	public String getPeriodo2() {
		return periodo2;
	}

	/**
	 * @param periodo2 the periodo2 to set
	 */
	public void setPeriodo2(String periodo2) {
		this.periodo2 = periodo2;
	}

	/**
	 * @return the periodo3
	 */
	public String getPeriodo3() {
		return periodo3;
	}

	/**
	 * @param periodo3 the periodo3 to set
	 */
	public void setPeriodo3(String periodo3) {
		this.periodo3 = periodo3;
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
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}
	
	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	
	/**
	 * @return the cotejado
	 */
	public String getCotejado() {
		return cotejado;
	}

	/**
	 * @param cotejado the cotejado to set
	 */
	public void setCotejado(String cotejado) {
		this.cotejado = cotejado;
	}
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the foto
	 */
	public byte[] getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		nombres 			= rs.getString("NOMBRES");
		apellidos	 		= rs.getString("APELLIDOS");
		carrera				= rs.getString("CARRERA");
		cotejado			= rs.getString("COTEJADO");
		periodo1			= rs.getString("PERIODO1");
		periodo2			= rs.getString("PERIODO2");
		periodo3			= rs.getString("PERIODO3");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String folio ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, NOMBRES, CARRERA, COTEJADO, PERIODO1, PERIODO2, PERIODO3"+
				" FROM ENOC.ALUM_CREDENCIAL"+ 
				" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

}