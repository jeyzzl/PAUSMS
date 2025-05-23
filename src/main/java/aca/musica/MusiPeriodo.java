// Bean del Catalogo MusiPeriodo
package  aca.musica;

import java.sql.*;

public class MusiPeriodo{
	private String periodoId;
	private String periodoNombre;
	private String fInicio;
	private String fFinal;
	private String cicloEscolar;
	private String estado;
	private String numPagare;	
	private String costoPagare;	
	
	
	public MusiPeriodo(){
		periodoId		= "";
		periodoNombre	= "";
		fInicio			= "";
		fFinal			= "";
		cicloEscolar	= "";
		fFinal			= "";
		estado			= "";
		numPagare		= "";
		costoPagare		= "";
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
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}


	/**
	 * @param inicio the fInicio to set
	 */
	public void setFInicio(String inicio) {
		fInicio = inicio;
	}


	/**
	 * @return the fFinal
	 */
	public String getFFinal() {
		return fFinal;
	}


	/**
	 * @param final1 the fFinal to set
	 */
	public void setFFinal(String final1) {
		fFinal = final1;
	}


	/**
	 * @return the cicloEscolar
	 */
	public String getCicloEscolar() {
		return cicloEscolar;
	}


	/**
	 * @param cicloEscolar the cicloEscolar to set
	 */
	public void setCicloEscolar(String cicloEscolar) {
		this.cicloEscolar = cicloEscolar;
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
	
	
	/**
	 * @return the numPagare
	 */
	public String getNumPagare() {
		return numPagare;
	}


	/**
	 * @param numPagare the numPagare to set
	 */
	public void setNumPagare(String numPagare) {
		this.numPagare = numPagare;
	}

	
	public String getCostoPagare() {
		return costoPagare;
	}


	public void setCostoPagare(String costoPagare) {
		this.costoPagare = costoPagare;
	}


	/**
	 * @param conn
	 * @return boolean
	 */
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_PERIODO"+ 
				"(PERIODO_ID, PERIODO_NOMBRE, F_INICIO, "+
				"F_FINAL, CICLO_ESCOLAR, ESTADO, NUMPAGARE, COSTOPAGARE) "+
				"VALUES( ?, ?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?,?, TO_NUMBER(?,'9'), TO_NUMBER(?,'99999.99'))");
					
			ps.setString(1,  periodoId);
			ps.setString(2,  periodoNombre);
			ps.setString(3,  fInicio);
			ps.setString(4,  fFinal);
			ps.setString(5,  cicloEscolar);
			ps.setString(6,  estado);
			ps.setString(7,  numPagare);
			ps.setString(8,  costoPagare);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MUSI_PERIODO "+ 
				"SET PERIODO_NOMBRE = ?, "+
				"F_INICIO = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_FINAL = TO_DATE(?,'DD/MM/YYYY'), "+			
				"CICLO_ESCOLAR = ?, "+
				"ESTADO = ?, " +
				"NUMPAGARE = TO_NUMBER(?,'9'), COSTOPAGARE = TO_NUMBER(?,'99999.99') " +
				"WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoNombre);
			ps.setString(2, fInicio);
			ps.setString(3, fFinal);
			ps.setString(4, cicloEscolar);
			ps.setString(5, estado);
			ps.setString(6, numPagare);
			ps.setString(7, costoPagare);
			ps.setString(8, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodo|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_PERIODO "+ 
				"WHERE PERIODO_ID = ? ");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.MusiPeriodo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		periodoId 		= rs.getString("PERIODO_ID");
		periodoNombre 	= rs.getString("PERIODO_NOMBRE");
		fInicio			= rs.getString("F_INICIO");
		fFinal 			= rs.getString("F_FINAL");
		cicloEscolar 	= rs.getString("CICLO_ESCOLAR");
		estado			= rs.getString("ESTADO");
		numPagare		= rs.getString("NUMPAGARE");
		costoPagare		= rs.getString("COSTOPAGARE");
		
	}
	
	public void mapeaRegId( Connection conn, String periodoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, PERIODO_NOMBRE, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"CICLO_ESCOLAR, ESTADO, NUMPAGARE, COSTOPAGARE "+
				"FROM ENOC.MUSI_PERIODO WHERE PERIODO_ID = ? "); 
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodo|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_PERIODO "+ 
				"WHERE PERIODO_ID = ?");
			ps.setString(1, periodoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getPeriodoActual(Connection conn ) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  periodo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID FROM ENOC.MUSI_PERIODO "+ 
				" WHERE now() BETWEEN F_INICIO AND F_FINAL");
			rs = ps.executeQuery();
			if (rs.next()){		
				periodo = rs.getString("PERIODO_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error -aca.musica.MusiPeriodo|getPeriodoActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static String getPeriodoNombre(Connection conn, String periodoId ) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  periodo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_NOMBRE FROM ENOC.MUSI_PERIODO "+ 
				" WHERE PERIODO_ID = '"+periodoId+"'");	
			rs = ps.executeQuery();
			if (rs.next()){		
				periodo = rs.getString("PERIODO_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error -aca.musica.MusiPeriodo|getPeriodoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static String getNumPagare(Connection conn, String periodoId ) throws SQLException{
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  numPagare 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT NUMPAGARE FROM ENOC.MUSI_PERIODO "+ 
				" WHERE PERIODO_ID = '"+periodoId+"'");	
			rs = ps.executeQuery();
			if (rs.next()){		
				numPagare = rs.getString("NUMPAGARE");
			}
			
		}catch(Exception ex){
			System.out.println("Error -aca.musica.MusiPeriodo|getNumPagare|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numPagare;
	}
	
	
/*
	public static void main(String args[]){
		try{
			Connection Conn = null;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "enoc", "caminacondios");
			
			Carga carga = new Carga();	
			System.out.println("Cargas="+carga.getCargasActivas(Conn, "09/08/2005"));
			Conn.commit();
			Conn.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	}	
*/
}