// Clase para la tabla de Modulo
package aca.acceso;
import java.sql.*;

public class AccesoClave{
	
	private String codigoPersonal;
	private String fecha;
	private String clave;
	private String ip;
	private int folio;
	
	// Constructor
	public AccesoClave(){
		codigoPersonal	= "";
		fecha			= "";
		clave			= "";
		ip				= "";
		folio			= 0;
	}
	
	public String getClave() {
		return clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}	
		
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ACCESO_CLAVE(CODIGO_PERSONAL, FECHA, CLAVE, IP, FOLIO)" +
					" VALUES(?,TO_DATE(?,'DD/MM/YYYY'),?,?,?)");
			ps.setString(1,codigoPersonal);
			ps.setString(2,fecha);
			ps.setString(3,clave);
			ps.setString(4,ip);
			ps.setInt(5,folio);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.Acceso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?" + 
					" AND FOLIO = ?");
			ps.setString(1,codigoPersonal);	
			ps.setInt(2, folio);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.Acceso|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal  = rs.getString("CODIGO_PERSONAL");
		fecha			= rs.getString("FECHA");
		clave			= rs.getString("CLAVE");
		ip 				= rs.getString("IP");
		folio			= rs.getInt("FOLIO");
	}
	
	public void mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY'),'01/01/1900') FECHA,"+
					" CLAVE, IP, FOLIO"+
					" FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.AccesoClave|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
	public void mapeaRegIdFolio(Connection con, String sCodigoPersonal, int nFolio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY'),'01/01/1900') FECHA,"+
					" CLAVE, IP, FOLIO"+
					" FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?" + 
					" AND FOLIO = '");
			ps.setString(1, sCodigoPersonal);
			ps.setInt(2, nFolio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoClave|mapeaRegIdFolio|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?" + 
					" AND FOLIO = ?");
			ps.setString(1, codigoPersonal);
			ps.setInt(2, folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.Acceso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int ultimoFolio(Connection conn, String matricula) throws SQLException{
		int folio				= 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FOLIO) FOLIO FROM ENOC.ACCESO_CLAVE WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, matricula);
			rs= ps.executeQuery();		
			if(rs.next()){
				folio = rs.getInt("FOLIO");
			}else{
				folio = 1;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.Acceso|ultimoFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return folio;
	}
}