// Bean del Catalogo de Bloques
package  aca.carga;

import java.sql.*;

public class CargaBloque{
	private String cargaId;
	private String bloqueId;
	private String nombreBloque;
	private String fInicio;
	private String fFinal;	
	private String fCierre;
	
	public CargaBloque(){
		cargaId			= "";
		bloqueId		= "0";
		nombreBloque	= "";
		fInicio			= "";
		fFinal			= "";
		fCierre			= "";		
	}
	
	public String getCargaId(){
		return cargaId;
	}
	
	public void setCargaId( String cargaId){
		this.cargaId = cargaId;
	}	
	
	public String getBloqueId(){
		return bloqueId;
	}
	
	public void setBloqueId( String bloqueId){
		this.bloqueId = bloqueId;
	}	
	
	public String getNombreBloque(){
		return nombreBloque;
	}
	
	public void setNombreBloque( String nombreBloque){
		this.nombreBloque = nombreBloque;
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
	
	public String getFCierre(){
		return fCierre;
	}
	
	public void setFCierre( String fCierre){
		this.fCierre = fCierre;
	}
	
	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId 			= rs.getString("CARGA_ID");
		bloqueId 			= rs.getString("BLOQUE_ID");
		nombreBloque	 	= rs.getString("NOMBRE_BLOQUE");
		fInicio	 			= rs.getString("F_INICIO");
		fFinal 				= rs.getString("F_FINAL");
		fCierre 			= rs.getString("F_CIERRE");
	}
	
	public void mapeaRegId( Connection conn, String cargaId, String bloqueId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CARGA_ID, BLOQUE_ID, NOMBRE_BLOQUE, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE "+
				"FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ? "+
				"AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cargaId);
			ps.setString(2, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean CargaActiva(Connection conn, String cargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;	
	
		try{
			ps = conn.prepareStatement("SELECT"+
				" CARGA_ID, COALESCE(BLOQUE_ID,0) AS BLOQUE_ID, NOMBRE_BLOQUE,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
				" TO_CHAR(F_CIERRE,'DD/MM/YYYY') F_CIERRE"+
				" FROM ENOC.CARGA_BLOQUE"+ 
				" WHERE CARGA_ID = ?"+
				" AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE");
			ps.setString(1, cargaId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
				ok = true;
			}else{ 
				ok = false;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|cargaActiva|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getBloqueActivo(Connection conn, String cargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String bloque			= "0";
	
		try{
			ps = conn.prepareStatement("SELECT BLOQUE_ID FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = ? AND TO_DATE(now(),'dd-mm-yy') BETWEEN F_INICIO AND F_CIERRE");
			ps.setString(1, cargaId);		
			
			rs = ps.executeQuery();
			if (rs.next()){				
				bloque = rs.getString("BLOQUE_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|getBloqueActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bloque;
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_BLOQUE "+ 
				"WHERE CARGA_ID = ? AND BLOQUE_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cargaId);
			ps.setString(2, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cargaId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(BLOQUE_ID)+1 MAXIMO FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = ?"); 
			ps.setString(1, cargaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getBloqueActual(Connection conn, String cargaId) throws SQLException{
		String bloque 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT BLOQUE_ID FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ?"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL");
			
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				bloque = rs.getString("BLOQUE_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|getBloqueActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bloque;
	}
	
	public static String esBloqueActivo(Connection conn, String cargaId, String bloqueId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String esActivo 		= "N";
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_BLOQUE"
					+ " WHERE CARGA_ID = ?"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL");
			
			ps.setString(1, cargaId);
			ps.setString(2, bloqueId);
			
			rs = ps.executeQuery();
			if (rs.next())
				esActivo = "S";
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaBloque|esBloqueActivo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return esActivo;
	}
	
	public static boolean getFechaAntesBloque(Connection conn, String cargaId, String bloque ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		boolean docencia 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_BLOQUE WHERE CARGA_ID = '"+cargaId+"' AND now() <= F_CIERRE AND BLOQUE_ID = '"+bloque+"'");
			
			rs = ps.executeQuery();
			if (rs.next())
				docencia = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getFechaAntesBloque|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return docencia;
	}
}