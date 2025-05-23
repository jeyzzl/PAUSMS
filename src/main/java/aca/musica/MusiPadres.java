package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusiPadres {
 	private String codigoId;
 	private String padNombre;
 	private String padPaterno;
 	private String padMaterno;
 	private String padDireccion;
 	private String padCorreo;
 	private String padOcupacion;
 	private String padTelcasa;
 	private String padTeltrabajo;
 	private String padTelcelular;
 	private String madNombre;
 	private String madPaterno;
 	private String madMaterno;
 	private String madOcupacion;
 	private String madDireccion;
 	private String madCorreo;
 	private String madTelcasa;
 	private String madTeltrabajo;
 	private String madTelcelular;
 	private String padVive;
 	private String madVive;
 	private String codigoUsuario;
 	private String padReligionId;
 	private String madReligionId;
 	
 
 	
 	public MusiPadres(){
 		codigoId		= "";
 		padNombre		= "";
 		padPaterno		= "";
 		padMaterno		= "";
 		padDireccion	= "";
 		padCorreo		= "";
 		padOcupacion	= "";
 		padTelcasa		= "";
 		padTeltrabajo	= "";
 		padTelcelular	= "";
 		madNombre		= "";
 		madPaterno		= "";
 		madMaterno		= "";
 		madOcupacion    = "";
 		madDireccion	= "";
 		madCorreo		= "";
 		madTelcasa		= "";
 		madTeltrabajo	= "";
 		madTelcelular   = "";
 		padVive			= "";
 		madVive			= "";
 		codigoUsuario	= "";
 		padReligionId	= "";
 		madReligionId	= "";
 	}
 	
	/**
	 * @return the codigoId
	 */
	public String getCodigoId() {
		return codigoId;
	}

	/**
	 * @param codigoId the codigoId to set
	 */
	public void setCodigoId(String codigoId) {
		this.codigoId = codigoId;
	}

	/**
	 * @return the padNombre
	 */
	public String getPadNombre() {
		return padNombre;
	}

	/**
	 * @param padNombre the padNombre to set
	 */
	public void setPadNombre(String padNombre) {
		this.padNombre = padNombre;
	}

	/**
	 * @return the padPaterno
	 */
	public String getPadPaterno() {
		return padPaterno;
	}

	/**
	 * @param padPaterno the padPaterno to set
	 */
	public void setPadPaterno(String padPaterno) {
		this.padPaterno = padPaterno;
	}

	/**
	 * @return the padMaterno
	 */
	public String getPadMaterno() {
		return padMaterno;
	}

	/**
	 * @param padMaterno the padMaterno to set
	 */
	public void setPadMaterno(String padMaterno) {
		this.padMaterno = padMaterno;
	}

	/**
	 * @return the padDireccion
	 */
	public String getPadDireccion() {
		return padDireccion;
	}
	
	/**
	 * @param padDireccion the padDireccion to set
	 */
	public void setPadDireccion(String padDireccion) {
		this.padDireccion = padDireccion;
	}

	/**
	 * @return the padCorreo
	 */
	public String getPadCorreo() {
		return padCorreo;
	}

	/**
	 * @param padCorreo the padCorreo to set
	 */
	public void setPadCorreo(String padCorreo) {
		this.padCorreo = padCorreo;
	}

	/**
	 * @return the padOcupacion
	 */
	public String getPadOcupacion() {
		return padOcupacion;
	}

	/**
	 * @param padOcupacion the padOcupacion to set
	 */
	public void setPadOcupacion(String padOcupacion) {
		this.padOcupacion = padOcupacion;
	}

	/**
	 * @return the padTelcasa
	 */
	public String getPadTelcasa() {
		return padTelcasa;
	}

	/**
	 * @param padTelcasa the padTelcasa to set
	 */
	public void setPadTelcasa(String padTelcasa) {
		this.padTelcasa = padTelcasa;
	}

	/**
	 * @return the padTeltrabajo
	 */
	public String getPadTeltrabajo() {
		return padTeltrabajo;
	}

	/**
	 * @param padTeltrabajo the padTeltrabajo to set
	 */
	public void setPadTeltrabajo(String padTeltrabajo) {
		this.padTeltrabajo = padTeltrabajo;
	}

	/**
	 * @return the padTelcelular
	 */
	public String getPadTelcelular() {
		return padTelcelular;
	}

	/**
	 * @param padTelcelular the padTelcelular to set
	 */
	public void setPadTelcelular(String padTelcelular) {
		this.padTelcelular = padTelcelular;
	}

	/**
	 * @return the madNombre
	 */
	public String getMadNombre() {
		return madNombre;
	}

	/**
	 * @param madNombre the madNombre to set
	 */
	public void setMadNombre(String madNombre) {
		this.madNombre = madNombre;
	}

	/**
	 * @return the madPaterno
	 */
	public String getMadPaterno() {
		return madPaterno;
	}

	/**
	 * @param madPaterno the madPaterno to set
	 */
	public void setMadPaterno(String madPaterno) {
		this.madPaterno = madPaterno;
	}

	/**
	 * @return the madMaterno
	 */
	public String getMadMaterno() {
		return madMaterno;
	}
	
	/**
	 * @param madMaterno the madMaterno to set
	 */
	public void setMadMaterno(String madMaterno) {
		this.madMaterno = madMaterno;
	}

	/**
	 * @return the madOcupacion
	 */
	public String getMadOcupacion() {
		return madOcupacion;
	}

	/**
	 * @param madOcupacion the madOcupacion to set
	 */
	public void setMadOcupacion(String madOcupacion) {
		this.madOcupacion = madOcupacion;
	}

	/**
	 * @return the madDireccion
	 */
	public String getMadDireccion() {
		return madDireccion;
	}

	/**
	 * @param madDireccion the madDireccion to set
	 */
	public void setMadDireccion(String madDireccion) {
		this.madDireccion = madDireccion;
	}

	/**
	 * @return the madCorreo
	 */
	public String getMadCorreo() {
		return madCorreo;
	}
	
	/**
	 * @param madCorreo the madCorreo to set
	 */
	public void setMadCorreo(String madCorreo) {
		this.madCorreo = madCorreo;
	}

	/**
	 * @return the madTelcasa
	 */
	public String getMadTelcasa() {
		return madTelcasa;
	}

	/**
	 * @param madTelcasa the madTelcasa to set
	 */
	public void setMadTelcasa(String madTelcasa) {
		this.madTelcasa = madTelcasa;
	}

	/**
	 * @return the madTeltrabajo
	 */
	public String getMadTeltrabajo() {
		return madTeltrabajo;
	}

	/**
	 * @param madTeltrabajo the madTeltrabajo to set
	 */
	public void setMadTeltrabajo(String madTeltrabajo) {
		this.madTeltrabajo = madTeltrabajo;
	}

	/**
	 * @return the madTelcelular
	 */
	public String getMadTelcelular() {
		return madTelcelular;
	}

	/**
	 * @param madTelcelular the madTelcelular to set
	 */
	public void setMadTelcelular(String madTelcelular) {
		this.madTelcelular = madTelcelular;
	}

	/**
	 * @return the padVive
	 */
	public String getPadVive() {
		return padVive;
	}

	/**
	 * @param padVive the padVive to set
	 */
	public void setPadVive(String padVive) {
		this.padVive = padVive;
	}

	/**
	 * @return the madVive
	 */
	public String getMadVive() {
		return madVive;
	}

	/**
	 * @param madVive the madVive to set
	 */
	public void setMadVive(String madVive) {
		this.madVive = madVive;
	}
	
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getPadReligionId() {
		return padReligionId;
	}

	public void setPadReligionId(String padReligionId) {
		this.padReligionId = padReligionId;
	}

	public String getMadReligionId() {
		return madReligionId;
	}

	public void setMadReligionId(String madReligionId) {
		this.madReligionId = madReligionId;
	}

	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_PADRES "
 					+ " (CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, PAD_DIRECCION, "
 					+ " PAD_CORREO, PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, PAD_TELCELULAR, "
 					+ " MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, MAD_OCUPACION, MAD_DIRECCION, "
 					+ " MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR, "
 					+ " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID) "
 					+ " VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
 					+ " ?, ?, ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))");
 			
 			ps.setString(1, codigoId);
 			ps.setString(2, padNombre);
 			ps.setString(3, padPaterno);
 			ps.setString(4, padMaterno);
 			ps.setString(5, padDireccion);
 			ps.setString(6, padCorreo);
 			ps.setString(7, padOcupacion);
 			ps.setString(8, padTelcasa);
 			ps.setString(9, padTeltrabajo);
 			ps.setString(10, padTelcelular);
 			ps.setString(11, madNombre);
 			ps.setString(12, madPaterno);
 			ps.setString(13, madMaterno);
 			ps.setString(14, madOcupacion);
 			ps.setString(15, madDireccion);
 			ps.setString(16, madCorreo);
 			ps.setString(17, madTelcasa);
 			ps.setString(18, madTeltrabajo);
 			ps.setString(19, madTelcelular);
 			ps.setString(20, padVive);
 			ps.setString(21, madVive);
 			ps.setString(22, codigoUsuario);
 			ps.setString(23, padReligionId);
 			ps.setString(24, madReligionId); 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.MusiPadres|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		Statement st 		= conn.createStatement(); 		
 		String comando = "";
 		boolean ok = false;
 		
 		try{
 			comando = "UPDATE ENOC.MUSI_PADRES"+			 
 				" SET PAD_NOMBRE 	= '"+padNombre+"',"+
 				" PAD_PATERNO 		= '"+padPaterno+"',"+
 				" PAD_MATERNO 		= '"+padMaterno+"',"+
 				" PAD_DIRECCION 	= '"+padDireccion+"',"+
 				" PAD_CORREO 		= '"+padCorreo+"',"+
 				" PAD_OCUPACION 	= '"+padOcupacion+"',"+
 				" PAD_TELCASA 		= '"+padTelcasa+"',"+
 				" PAD_TELTRABAJO 	= '"+padTeltrabajo+"',"+
 				" PAD_TELCELULAR	= '"+padTelcelular+"',"+
 				" MAD_NOMBRE	 	= '"+madNombre+"',"+
 				" MAD_PATERNO 		= '"+madPaterno+"',"+
 				" MAD_MATERNO 		= '"+madMaterno+"'," +
 				" MAD_OCUPACION		= '"+madOcupacion+"'," + 
 				" MAD_DIRECCION 	= '"+madDireccion+"'," + 
 				" MAD_CORREO 		= '"+madCorreo+"'," + 
 				" MAD_TELCASA 		= '"+madTelcasa+"'," + 
 				" MAD_TELTRABAJO 	= '"+madTeltrabajo+"'," + 
 				" MAD_TELCELULAR 	= '"+madTelcelular+"'," + 
 				" PAD_VIVE 			= '"+padVive+"'," + 
 				" MAD_VIVE 			= '"+madVive+"'," + 
 				" CODIGO_USUARIO	= '"+codigoUsuario+"'," + 
 				" PAD_RELIGION_ID	= '"+padReligionId+"'," + 
 				" MAD_RELIGION_ID	= '"+madReligionId+"'" + 
 				" WHERE CODIGO_ID 	= '"+codigoId+"'";
 			
			if (st.executeUpdate(comando)==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPadres|updateReg|:"+ex);		
 		}finally{
 			try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_PADRES WHERE CODIGO_ID = ? ");
 			ps.setString(1, codigoId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPadres|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoId 			= rs.getString("CODIGO_ID");
 		padNombre 			= rs.getString("PAD_NOMBRE");
 		padPaterno		 	= rs.getString("PAD_PATERNO");
 		padMaterno			= rs.getString("PAD_MATERNO");
 		padDireccion		= rs.getString("PAD_DIRECCION");
 		padCorreo			= rs.getString("PAD_CORREO");
 		padOcupacion 		= rs.getString("PAD_OCUPACION");
 		padTelcasa			= rs.getString("PAD_TELCASA");
 		padTeltrabajo		= rs.getString("PAD_TELTRABAJO");
 		padTelcelular		= rs.getString("PAD_TELCELULAR");
 		madNombre			= rs.getString("MAD_NOMBRE");
 		madPaterno			= rs.getString("MAD_PATERNO");
 		madMaterno			= rs.getString("MAD_MATERNO");
 		madOcupacion		= rs.getString("MAD_OCUPACION");
 		madDireccion 		= rs.getString("MAD_DIRECCION");
 		madCorreo			= rs.getString("MAD_CORREO");
 		madTelcasa			= rs.getString("MAD_TELCASA");
 		madTeltrabajo		= rs.getString("MAD_TELTRABAJO");
 		madTelcelular		= rs.getString("MAD_TELCELULAR");
 		padVive				= rs.getString("PAD_VIVE");
 		madVive				= rs.getString("MAD_VIVE");
 		codigoUsuario		= rs.getString("CODIGO_USUARIO");
 		padReligionId		= rs.getString("PAD_RELIGION_ID");
 		madReligionId		= rs.getString("MAD_RELIGION_ID");
 		
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
 		ps = conn.prepareStatement("SELECT CODIGO_ID, PAD_NOMBRE, PAD_PATERNO, PAD_MATERNO, "
 				+ " PAD_DIRECCION, PAD_CORREO, PAD_OCUPACION, PAD_TELCASA, PAD_TELTRABAJO, "
 				+ " PAD_TELCELULAR, MAD_NOMBRE, MAD_PATERNO, MAD_MATERNO, MAD_OCUPACION, "
 				+ " MAD_DIRECCION, MAD_CORREO, MAD_TELCASA, MAD_TELTRABAJO, MAD_TELCELULAR, "
 				+ " PAD_VIVE, MAD_VIVE, CODIGO_USUARIO, PAD_RELIGION_ID, MAD_RELIGION_ID "
 				+ " FROM ENOC.MUSI_PADRES WHERE CODIGO_ID = '"+codigoId+"' "); 
	 	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPadres|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	


 	public boolean existeReg(Connection conn, String codigoId) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_PADRES WHERE CODIGO_ID = '"+codigoId+"' ");
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiPadres|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 }