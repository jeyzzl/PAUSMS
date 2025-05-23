package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Carlos
 *
 */
public class DatosAlumno {
	private String codigoPersonal;
	private String computadora;
	private String tratamiento;
	private String motivo;
	private String tipoSangre;					
	private String instrumentos;
	private String celular;
	private String correo;
	private String telefono;
	
	public DatosAlumno(){
		codigoPersonal 	= "";
		computadora 	= "";
		tratamiento 	= "";
		motivo 			= "";
		tipoSangre 		= "";		
		instrumentos 	= "";
		celular 		= "";
		correo 			= "";
		telefono 		= "";		
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getComputadora() {
		return computadora;
	}

	public void setComputadora(String computadora) {
		this.computadora = computadora;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipoSangre() {
		return tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public String getInstrumentos() {
		return instrumentos;
	}

	public void setInstrumentos(String instrumentos) {
		this.instrumentos = instrumentos;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		computadora 	= rs.getString("COMPUTADORA");
		tratamiento		= rs.getString("TRATAMIENTO");
		motivo 			= rs.getString("MOTIVO");
		tipoSangre 		= rs.getString("TIPO_SANGRE");		
		instrumentos 	= rs.getString("INSTRUMENTOS");
		celular			= rs.getString("CELULAR");
		correo 			= rs.getString("CORREO");
		telefono 		= rs.getString("TELEFONO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_DATOS_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DatosAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}