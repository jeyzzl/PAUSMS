// Bean de Alum_Foto
package  aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlumFotografia{
	private String codigoPersonal;
	private String resolucion;
		
	public AlumFotografia(){
		codigoPersonal	= "";
		resolucion		= "";	
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_FOTOGRAFIA"
					+ " (CODIGO_PERSONAL, RESOLUCION)"
					+ " VALUES( ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, resolucion);			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFotografia|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_FOTOGRAFIA "+ 
				"SET RESOLUCION = ? "+				
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, resolucion);			
			ps.setString(2, codigoPersonal);			
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFotografia|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_FOTOGRAFIA "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);	
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFotografia|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		resolucion 			= rs.getString("RESOLUCION");		
	}
	
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT"+
			" CODIGO_PERSONAL, RESOLUCION FROM ENOC.ALUM_FOTOGRAFIA"+ 
			" WHERE CODIGO_PERSONAL = ?");
		ps.setString(1, codigoPersonal);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFotografia|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_FOTOGRAFIA "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFotografia|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}