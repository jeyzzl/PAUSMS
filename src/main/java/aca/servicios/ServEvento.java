//Bean de la tabla Serv_Evento

package aca.servicios;

import java.sql.*;

public class ServEvento {

	private String eventoId;
	private String eventoNombre;
	private String fechaInicio;
	private String fechaFinal;
	private String usuario;
	
	public ServEvento(){
		eventoId 		= "";
		eventoNombre	= "";
		fechaInicio		= "";
		fechaFinal		= "";
		usuario			= "";
	}	

	
	/**
	 * @return the eventoId
	 */
	public String getEventoId() {
		return eventoId;
	}

	/**
	 * @param eventoId the eventoId to set
	 */
	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	/**
	 * @return the eventoNombre
	 */
	public String getEventoNombre() {
		return eventoNombre;
	}

	/**
	 * @param eventoNombre the eventoNombre to set
	 */
	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
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


	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SERV_EVENTO( EVENTO_ID, EVENTO_NOMBRE, FECHA_INICIO, FECHA_FINAL, USUARIO) "+
				"VALUES( TO_NUMBER(?,'99999'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),?)");
			ps.setString(1, eventoId);
			ps.setString(2, eventoNombre);
			ps.setString(3, fechaInicio);
			ps.setString(4, fechaFinal);
			ps.setString(5, usuario);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SERV_EVENTO "+ 
				"SET EVENTO_NOMBRE = ?, FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'), FECHA_FINAL = TO_DATE(?,'DD/MM/YYYY'), USUARIO = ? WHERE EVENTO_ID = TO_NUMBER(?,'99999') ");
			ps.setString(1, eventoNombre);
			ps.setString(2, fechaInicio);
			ps.setString(3, fechaFinal);
			ps.setString(4, usuario);
			ps.setString(5, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')");
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 		= rs.getString("EVENTO_ID");
		eventoNombre 	= rs.getString("EVENTO_NOMBRE");
		fechaInicio 	= rs.getString("FECHA_INICIO");
		fechaFinal 		= rs.getString("FECHA_FINAL");
		usuario 		= rs.getString("USUARIO");
	}
	
	public void mapeaRegId( Connection conn, String eventoId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID, EVENTO_NOMBRE, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, "+
				"TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL, USUARIO FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"); 
			ps.setString(1,eventoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = TO_NUMBER(?,'99999')"); 
			ps.setString(1,eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getEventoNombre(Connection conn, String eventoId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String eventoNombre		= "x";
		
		try{
			comando = "SELECT EVENTO_NOMBRE FROM ENOC.SERV_EVENTO WHERE EVENTO_ID = '"+eventoId+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				eventoNombre = rs.getString("EVENTO_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|getEventoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return eventoNombre;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(EVENTO_ID)+1 MAXIMO FROM ENOC.SERV_EVENTO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServEvento|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
}