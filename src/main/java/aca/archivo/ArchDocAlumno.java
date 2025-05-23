//Beans para la tabla ARCH_DOCALUM
package aca.archivo;

import java.sql.*;

public class ArchDocAlumno {
	private String matricula;
	private String idDocumento;
	private String idStatus;
	private String fecha;
	private String usuario;
	private String cantidad;
	private String ubicacion;
	
	public ArchDocAlumno(){
		matricula 	= "";
		idDocumento = "";
		idStatus	= "";
		fecha		= "";
		usuario		= "";
		cantidad	= "";
		ubicacion	= "";
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula			= rs.getString("MATRICULA");
		idDocumento  		= rs.getString("IDDOCUMENTO");
		idStatus			= rs.getString("IDSTATUS");
		fecha				= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		cantidad			= rs.getString("CANTIDAD");
		ubicacion			= rs.getString("UBICACION");
	}
	
	public void mapeaRegId(Connection con, String Matricula, String IdDocumento) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = con.prepareStatement("SELECT MATRICULA, IDDOCUMENTO, " +
					"IDSTATUS, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CANTIDAD, UBICACION " +
					"FROM ENOC.ARCH_DOCALUM WHERE MATRICULA = ? AND IDDOCUMENTO = TO_NUMBER(?,'999')");		 
			ps.setString(1,Matricula);		
			ps.setString(2,IdDocumento);
			rs = ps.executeQuery();
		
			if(rs.next()){		
				mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchDocAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
	
}		