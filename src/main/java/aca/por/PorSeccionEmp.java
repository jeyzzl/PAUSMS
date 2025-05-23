//Bean del Catálogo de Documentos

package aca.por;

import java.sql.*;

public class PorSeccionEmp {

	private String porId;
	private String seccionId;
	private String codigoPersonal;
	private String textoCorto;
	private String textoLargo;
	private String comentario;
	private String folio;
	
	public PorSeccionEmp(){
		porId 			= "";
		seccionId		= "";
		codigoPersonal	= "";		
		textoCorto		= "";
		textoLargo   	= "";
		comentario 		= "-";
		folio			= "1";
		
	}	
	
	public String getPorId() {
		return porId;
	}

	public void setPorId(String porId) {
		this.porId = porId;
	}

	public String getSeccionId() {
		return seccionId;
	}

	public void setSeccionId(String seccionId) {
		this.seccionId = seccionId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getTextoCorto() {
		return textoCorto;
	}

	public void setTextoCorto(String textoCorto) {
		this.textoCorto = textoCorto;
	}

	public String getTextoLargo() {
		return textoLargo;
	}

	public void setTextoLargo(String textoLargo) {
		this.textoLargo = textoLargo;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO"
				+ " ENOC.POR_SECCION_EMP( POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO,  TEXTO_LARGO, COMENTARIO, FOLIO)"
				+ " VALUES( TO_NUMBER(?, '999'), ?, ?, ?, ?, ?,TO_NUMBER(?, '99'))");
			
			ps.setString(1, porId);
			ps.setString(2, seccionId);
			ps.setString(3, codigoPersonal);
			ps.setString(4, textoCorto);
			ps.setString(5, textoLargo);
			ps.setString(6, comentario);
			ps.setString(7, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_SECCION_EMP"
				+ " SET TEXTO_CORTO = ?, TEXTO_LARGO = ?, COMENTARIO = ?"
				+ " WHERE POR_ID = TO_NUMBER(?, '999')"
				+ " AND SECCION_ID = ?"
				+ " AND CODIGO_PERSONAL = ?"
				+ " AND FOLIO=TO_NUMBER(?, '99')");
			
			ps.setString(1, textoCorto);
			ps.setString(2, textoLargo);
			ps.setString(3, comentario);
			ps.setString(4, porId);
			ps.setString(5, seccionId);
			ps.setString(6, codigoPersonal);
			ps.setString(7, folio);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_SECCION_EMP"
				+ " WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')");
			ps.setString(1,porId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			ps.setString(4,folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		porId 				= rs.getString("POR_ID");
		seccionId 			= rs.getString("SECCION_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		textoCorto 			= rs.getString("TEXTO_CORTO");
		textoLargo          = rs.getString("TEXTO_LARGO");
		comentario			= rs.getString("COMENTARIO");
		folio		      	= rs.getString("FOLIO");
	}
	
	public void mapeaRegId( Connection conn, String porId, String seccionId, String codigoPersonal) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO"
					+ " FROM ENOC.POR_SECCION_EMP"
					+ " WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ? "); 
			ps.setString(1,porId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId( Connection conn, String porId, String seccionId, String codigoPersonal, String folio) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO"
					+ " FROM ENOC.POR_SECCION_EMP"
					+ " WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ? AND FOLIO=?"); 
			ps.setString(1,porId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			ps.setString(4,folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_SECCION_EMP WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ? AND FOLIO=TO_NUMBER(?, '99')"); 
			ps.setString(1,porId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			ps.setString(4,folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getTextoCorto(Connection conn, String portafolioId, String seccionId, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		
		String texto 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT TEXTO_CORTO FROM ENOC.POR_SECCION_EMP WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ?"); 
			ps.setString(1,portafolioId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				texto = rs.getString("TEXTO_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|getTextoCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return texto;
	}
	
	public static String getTextoLargo(Connection conn, String portafolioId, String seccionId, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		
		String texto 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT TEXTO_LARGO FROM ENOC.POR_SECCION_EMP WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ?"); 
			ps.setString(1,portafolioId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				texto = rs.getString("TEXTO_LARGO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|getTextoLargo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return texto;
	}
	
	public static String getNombreArchivo(Connection conn, String portafolioId, String seccionId, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		
		String texto 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT TEXTO_CORTO FROM ENOC.POR_SECCION_EMP WHERE POR_ID = TO_NUMBER(?, '999') AND SECCION_ID = ? AND CODIGO_PERSONAL = ?"); 
			ps.setString(1,portafolioId);
			ps.setString(2,seccionId);
			ps.setString(3,codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				texto = rs.getString("TEXTO_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|getNombreArchivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return texto;
	}
	
	public String maximoReg(Connection conn, String porId, String seccionId, String codigoPersonal) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE( MAX(FOLIO)+1 , 1 ) AS MAXIMO FROM ENOC.POR_SECCION_EMP WHERE POR_ID ="+porId+""
					+ " AND SECCION_ID = "+seccionId+""
					+ " AND CODIGO_PERSONAL = "+codigoPersonal+""); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmp|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
}