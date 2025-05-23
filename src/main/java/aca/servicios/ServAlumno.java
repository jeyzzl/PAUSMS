//Bean de la tabla Serv_Alumno

package aca.servicios;

import java.sql.*;

public class ServAlumno {

	private String eventoId;
	private String codigoPersonal;
	private String segMedico;
	private String segAccidente;
	private String dormitorio;
	private String comedor;
	private String usuario;
	
	public ServAlumno(){
		eventoId			= "";
		codigoPersonal 		= "";
		segMedico			= "";
		segAccidente		= "";
		dormitorio			= "";
		comedor				= "";
		usuario				= "";
				
	}	

	public String getEventoId() {
		return eventoId;
	}

	public void setEventoId(String eventoId) {
		this.eventoId = eventoId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getSegMedico() {
		return segMedico;
	}

	public void setSegMedico(String segMedico) {
		this.segMedico = segMedico;
	}

	public String getSegAccidente() {
		return segAccidente;
	}

	public void setSegAccidente(String segAccidente) {
		this.segAccidente = segAccidente;
	}

	public String getDormitorio() {
		return dormitorio;
	}

	public void setDormitorio(String dormitorio) {
		this.dormitorio = dormitorio;
	}

	public String getComedor() {
		return comedor;
	}

	public void setComedor(String comedor) {
		this.comedor = comedor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.SERV_ALUMNO( EVENTO_ID, CODIGO_PERSONAL, SEG_MEDICO, SEG_ACCIDENTE, DORMITORIO, COMEDOR, USUARIO) "+
				"VALUES( TO_NUMBER(?,'99999'), ?, ?, ?, ?, ?, ?)");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, segMedico);
			ps.setString(4, segAccidente);
			ps.setString(5, dormitorio);
			ps.setString(6, comedor);
			ps.setString(7, usuario);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.SERV_ALUMNO"+ 
				" SET SEG_MEDICO = ?, SEG_ACCIDENTE = ?, DORMITORIO = ? , COMEDOR = ?, USUARIO = ?" +
				" WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, segMedico);
			ps.setString(2, segAccidente);
			ps.setString(3, dormitorio);
			ps.setString(4, comedor);
			ps.setString(5, usuario);
			ps.setString(6, eventoId);
			ps.setString(7, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		eventoId 		= rs.getString("EVENTO_ID");
		codigoPersonal 	= rs.getString("CODIGO_PERSONAL");
		segMedico 		= rs.getString("SEG_MEDICO");
		segAccidente 	= rs.getString("SEG_ACCIDENTE");
		dormitorio 		= rs.getString("DORMITORIO");
		comedor			= rs.getString("COMEDOR");
		usuario 		= rs.getString("USUARIO");
	}
	
	public void mapeaRegId( Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT EVENTO_ID, CODIGO_PERSONAL, SEG_MEDICO, SEG_ACCIDENTE, DORMITORIO, COMEDOR, USUARIO" +
				" FROM ENOC.SERV_ALUMNO" +
				" WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = TO_NUMBER(?,'99999') AND CODIGO_PERSONAL = ?");
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getSeguroMedico(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String seguroMedico		= "x";
		
		try{
			comando = "SELECT SEG_MEDICO FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				seguroMedico = rs.getString("SEG_MEDICO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|getSeguroMedico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return seguroMedico;
	}

	public static String getSeguroAccidente(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String seguroAccidente		= "x";
		
		try{
			comando = "SELECT SEG_ACCIDENTE FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				seguroAccidente = rs.getString("SEG_ACCIDENTE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|getSeguroAccidente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return seguroAccidente;
	}
	
	public static String getComedor(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String comedor		= "x";
		
		try{
			comando = "SELECT COMEDOR FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				comedor = rs.getString("COMEDOR");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|getComedor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return comedor;
	}
	
	public static String getDormitorio(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String dormitorio		= "x";
		
		try{
			comando = "SELECT DORMITORIO FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				dormitorio = rs.getString("DORMITORIO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|getDormitorio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return dormitorio;
	}
	
	
	
	public static String getServicioExtendido(Connection conn, String matricula) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String servicio		= "N";
		
		try{			
			comando = "SELECT COALESCE('S','N') AS CODIGO FROM ENOC.SERV_ALUMNO WHERE CODIGO_PERSONAL = '"+matricula+"' AND EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.SERV_EVENTO WHERE now() BETWEEN FECHA_INICIO AND FECHA_FINAL)";					
			rs = st.executeQuery(comando);
			
			
			
			if (rs.next()){
				servicio = rs.getString("SERVICIO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.servicios.ServAlumno|getServicioExtendido|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return servicio;
		
	}
	
	
	
}