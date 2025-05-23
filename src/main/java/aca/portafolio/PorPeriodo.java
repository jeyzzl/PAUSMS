//Bean del Catalogo de Peridos

package aca.portafolio;

import java.sql.*;

public class PorPeriodo {

	private String periodoId;
	private String periodoNombre;
	private String fechaIni;
	private String fechaFin;
	private String estado;
	
	public PorPeriodo(){
		periodoId 	= "";
		periodoNombre		= "";
		fechaIni	= "";
		fechaFin	= "";
		estado		= "";
	}
	
	
	/**
	 * @return the periodoId
	 */
	public String getPeriodoId() {
		return periodoId;
	}

	/**
	 * @param periodoId the periodoId to set
	 */
	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	
	/**
	 * @return the periodoNombre
	 */
	public String getPeriodoNombre() {
		return periodoNombre;
	}


	/**
	 * @param periodoNombre the periodoNombre to set
	 */
	public void setPeriodoNombre(String periodoNombre) {
		this.periodoNombre = periodoNombre;
	}


	/**
	 * @return the fechaIni
	 */
	public String getFechaIni() {
		return fechaIni;
	}


	/**
	 * @param fechaIni the fechaIni to set
	 */
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}


	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}


	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_PERIODO( PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, FECHA_FIN, ESTADO) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),?)");
			ps.setString(1, periodoId);
			ps.setString(2, periodoNombre);
			ps.setString(3, fechaIni);
			ps.setString(4, fechaFin);
			ps.setString(5, estado);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_PERIODO "+ 
				"SET PERIODO_NOMBRE = ?, FECHA_INI = ?, FECHA_FIN = ?, ESTADO = ? WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoNombre);
			ps.setString(2, fechaIni);
			ps.setString(3, fechaFin);
			ps.setString(4, estado);
			ps.setString(5, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM  DANIEL.POR_PERIODO "+ 
				"WHERE PERIODO_ID = ?");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId 		= rs.getString("PERIODO_ID");
		periodoNombre 	= rs.getString("PERIODO_NOMBRE");
		fechaIni 		= rs.getString("FECHA_INI");
		fechaFin 		= rs.getString("FECHA_FIN");
		estado 			= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String periodoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, FECHA_INI, "+
				"FECHA_FIN, ESTADO FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = ?"); 
			ps.setString(1,periodoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = ?"); 
			ps.setString(1,periodoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getNombre(Connection conn, String periodo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT PERIODO_NOMBRE FROM DANIEL.POR_PERIODO WHERE PERIODO_ID = '"+periodo+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("PERIODO_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorPeriodo|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

	
}