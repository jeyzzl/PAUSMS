// Bean de Alum_Foto
package  aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumFoto{
	private String codigoPersonal;
	private String folio;	
	private String fecha;
	private String usuario;
	private byte[] foto;
		
	public AlumFoto(){
		codigoPersonal	= "";
		folio			= "";		
		fecha			= "";
		usuario			= "";
		foto			= null;
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	
	public boolean insertRegByte(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_FOTO"+ 
				"(CODIGO_PERSONAL, FOLIO,  FECHA, USUARIO, FOTO ) "+
				"VALUES( ?, " +
				"TO_NUMBER(?,'9'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				" ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);			
			ps.setString(3, fecha);
			ps.setString(4, usuario);
			ps.setBytes(5, foto);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFoto|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_FOTO "+ 
				"SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"USUARIO = ?, "+
				"FOTO = ? "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')");
			ps.setString(1, fecha);
			ps.setString(2, usuario);
			ps.setBytes(3, foto);
			ps.setString(4, codigoPersonal);
			ps.setString(5, folio);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFoto|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_FOTO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFoto|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		folio 				= rs.getString("FOLIO");		
		fecha	 			= rs.getString("FECHA");
		usuario				= rs.getString("USUARIO");
		foto				= rs.getBytes("FOTO");
	}
	
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String folio ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT"+
			" CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, FOTO"+
			" FROM ENOC.ALUM_FOTO"+ 
			" WHERE CODIGO_PERSONAL = ?"+
			" AND FOLIO = TO_NUMBER(?,'9')");
		ps.setString(1, codigoPersonal);
		ps.setString(2, folio);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFoto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}

	public boolean existeReg(Connection conn) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_FOTO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'9')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFoto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}