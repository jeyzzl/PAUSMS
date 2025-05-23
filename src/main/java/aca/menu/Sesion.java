// Bean del Catalogo Planes
package  aca.menu;

import java.sql.*;

public class Sesion{
	private String sesionId;
	private String codigoPersonal; 
	private String fInicio;	
	private String fFinal;
	private String ip;
	private String finalizo;
	
	
	public Sesion(){
		sesionId		= "";
		codigoPersonal	= "";
		fInicio			= "";		
		fFinal			= "";
		ip				= "";
		finalizo		= "";
	}
	
	public String getSesionId(){
		return sesionId;
	}
	
	public void setSesionId( String sesionId){
		this.sesionId = sesionId;
	}	
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getFInicio(){
		return fInicio;
	}
	
	public void setFInicio( String fInicio){
		this.fInicio = fInicio;
	}
	
	public String getFFinal(){
		return fFinal;
	}
	
	public void setFFinal( String fFinal){
		this.fFinal = fFinal;
	}	
	
	public String getIp(){
		return ip;
	}
	
	public void setIp( String ip){
		this.ip = ip;
	}
	
	/**
	 * @return the finalizo
	 */
	public String getFinalizo() {
		return finalizo;
	}

	/**
	 * @param finalizo the finalizo to set
	 */
	public void setFinalizo(String finalizo) {
		this.finalizo = finalizo;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MODULO_SESION"+ 
				"(SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO ) "+
				"VALUES( ?, ?, "+
				"now(), "+
				"now(), "+
				" ?, 'N' )");			
			ps.setString(1, sesionId);
			ps.setString(2, codigoPersonal);			
			ps.setString(3, ip);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}		
	
	public boolean update(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_SESION "+ 
				"SET F_FINAL = now() " +
				"WHERE SESION_ID = ? ");				
			ps.setString(1, sesionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_SESION "+ 
				"SET F_FINAL = now()" +
				" WHERE SESION_ID = ? ");				
			ps.setString(1, sesionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateRegFinalizo(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_SESION "+				 
				"SET FINALIZO = 'S' "+
				"WHERE SESION_ID = ? ");								
			ps.setString(1, sesionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|updateRegFinalizo|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MODULO_SESION "+ 
				"WHERE SESION_ID = ? ");
			ps.setString(1, sesionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		sesionId 		= rs.getString("SESION_ID");
		codigoPersonal = rs.getString("CODIGO_PERSONAL");
		fInicio 		= rs.getString("F_INICIO");
		fFinal	 		= rs.getString("F_FINAL");
		ip 				= rs.getString("IP");
		finalizo		= rs.getString("FINALIZO");
	}
	
	public void mapeaRegId( Connection conn, String sesionId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT SESION_ID, CODIGO_PERSONAL, F_INICIO, "+
				"F_FINAL, IP, FINALIZO "+
				"FROM ENOC.MODULO_SESION WHERE SESION_ID = ? "); 
			ps.setString(1, sesionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MODULO_SESION "+ 
				"WHERE SESION_ID = ?");
			ps.setString(1, sesionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String validaSesionUnica(Connection conn, String sesionId, String codigoPersonal, String ipCliente) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String respuesta = "milisegundosEspera = 0.5 * 60 * 1000; muestraMensajeSesion('";
		boolean entro = false;	
		//System.out.println("Sesion:"+sesionId+" Codigo:"+codigoPersonal+" IP:"+ipCliente);
		try{
			ps = conn.prepareStatement("SELECT SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO FROM ENOC.MODULO_SESION" + 
					" WHERE SESION_ID != ?" +
					" AND CODIGO_PERSONAL = ?" +
					" AND (TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')-F_FINAL)*24*60 <= 2" +
					" AND FINALIZO = 'N'" +
					" ORDER BY F_FINAL DESC");
			ps.setString(1, sesionId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			while(rs.next()){
				String ip = rs.getString("IP");
				if((ip.trim().substring(0, 3).equals("10.") || ip.trim().substring(0, 3).equals("172")) && !ip.trim().equals(ipCliente.trim())){
					respuesta += "Otra sesion con su usuario se encuentra en estos momentos abierta en "+ip+"<br/>";
					entro = true;
				}
			}
			respuesta += "<br><b>Por seguridad es recomendable que cambie su contrase&ntilde;a...</b><br/>"+
						 "O puede <a href=\"#\" onclick=\"expulsaOtrasSesiones();\">expulsar las otras sesiones</a>');";
			if(!entro)
				respuesta = "milisegundosEspera = 2 * 60 * 1000; ocultaMensajeSesion();";
			PreparedStatement ps2 = conn.prepareStatement("SELECT SESION_ID, CODIGO_PERSONAL, F_INICIO, F_FINAL, IP, FINALIZO FROM ENOC.MODULO_SESION" + 
					" WHERE SESION_ID = ?" +
					" AND CODIGO_PERSONAL = ?" +
					" ORDER BY F_FINAL DESC");
			ps2.setString(1, sesionId);
			ps2.setString(2, codigoPersonal);
			
			ResultSet rs2 = ps2.executeQuery();
			
			while(rs2.next()){
				if(rs2.getString("FINALIZO").equals("E")){
					respuesta += "alert('Ah sido expulsado de la sesion');";
					respuesta += "document.location = 'salir';";
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|validaSesionUnica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return respuesta;
	}
	
	public String expulsaOtrasSesiones(Connection conn, String sessionId, String codigoPersonal) throws SQLException{
		String respuesta		= "";
		PreparedStatement ps	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MODULO_SESION" + 
					" SET FINALIZO = ?" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND SESION_ID != ?" +
					" AND (TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')-F_FINAL)*24*60 <= 2" +
					" AND FINALIZO = 'N'");
			ps.setString(1, "E");
			ps.setString(2, codigoPersonal);
			ps.setString(3, sessionId);
			
			if (ps.executeUpdate() >= 1){
				respuesta += "alert('La orden de expulsion ha sido enviada. En 2 minutos o menos el mensaje de otras sesiones debe DESAPARECER');";
			}else{
				respuesta += "alert('Ocurrio un ERROR. Intentelo de nuevo.\nSi el error persiste, contacte a sistemas')";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.Sesion|expulsaOtrasSesiones|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return respuesta;
	}
}