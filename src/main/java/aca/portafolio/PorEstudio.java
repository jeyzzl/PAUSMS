//Bean del Catálogo de PorEstudios

package aca.portafolio;

import java.sql.*;

public class PorEstudio {
	
	private String codigoPersonal;
	private String folio;
	private String fecha;
	private String nivelId;
	private String titulo;
	private String hojas;
	
	public PorEstudio(){		
		codigoPersonal 	= "";
		folio			= "";
		fecha 			= "";
		nivelId		= "";
		titulo			= "";
		hojas 			= ""; 
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
	 * @return the nivelId
	 */
	public String getNivelId() {
		return nivelId;
	}

	/**
	 * @param nivelId the nivelId to set
	 */
	public void setNivelId(String nivelId) {
		this.nivelId = nivelId;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the hojas
	 */
	public String getHojas() {
		return hojas;
	}

	/**
	 * @param hojas the hojas to set
	 */
	public void setHojas(String hojas) {
		this.hojas = hojas;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_ESTUDIO( CODIGO_PERSONAL, FOLIO, FECHA, NIVEL_ID, TITULO, HOJAS) "+
				"VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'), ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			ps.setString(3, fecha);
			ps.setString(4, nivelId);
			ps.setString(5, titulo);
			ps.setString(6, hojas);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudio|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_ESTUDIO"+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY'), NIVEL_ID = ?, TITULO = ?, HOJAS = ?" +
				" WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1, fecha);
			ps.setString(2, nivelId);
			ps.setString(3, titulo);			
			ps.setString(4, hojas);
			ps.setString(5, codigoPersonal);
			ps.setString(6, folio);		
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudio|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM DANIEL.POR_ESTUDIO "+ 
				"WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudio|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio			= rs.getString("FOLIO");
		fecha 			= rs.getString("FECHA");
		nivelId		 	= rs.getString("NIVEL_ID");
		titulo 			= rs.getString("TITULO");
		hojas 			= rs.getString("HOJAS");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NIVEL_ID, TITULO, HOJAS" +
					" FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudio|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorEstudio|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1, 1) AS MAXIMO"+
				" FROM DANIEL.POR_ESTUDIO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = String.valueOf(rs.getInt("MAXIMO"));			
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.porEstudio|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	
}