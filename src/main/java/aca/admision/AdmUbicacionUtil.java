package aca.admision;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmUbicacionUtil{
	
	
	public boolean insertReg(Connection conn, AdmUbicacion admUbica) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_UBICACION"+ 
				"(FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE, NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA) "+
				"VALUES( TO_NUMBER(?,'99999999'), TO_NUMBER(?,'999'),TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), " +
				" ?,?,?,?, ?)");
			ps.setString(1, admUbica.getFolio());
			ps.setString(2, admUbica.getPaisId());
			ps.setString(3, admUbica.getEstadoId());
			ps.setString(4, admUbica.getCiudadId());
			ps.setString(5, admUbica.getCalle());
			ps.setString(6, admUbica.getNumero());
			ps.setString(7, admUbica.getCodigoPostal());
			ps.setString(8, admUbica.getTelefono());
			ps.setString(9, admUbica.getColonia());

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, AdmUbicacion admUbica ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_UBICACION " + 
					" SET PAIS_ID = TO_NUMBER(?,'999'), " +
					" ESTADO_ID = TO_NUMBER(?,'999'), " +
					" CIUDAD_ID = TO_NUMBER(?,'999'), " +
					" CALLE = ?, " +
					" NUMERO = ?, " +
					" CODIGO_POSTAL = ?," +					
					" TELEFONO = ?," +
					" COLONIA = ? " +				
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			
			ps.setString(1, admUbica.getPaisId());
			ps.setString(2, admUbica.getEstadoId());
			ps.setString(3, admUbica.getCiudadId());
			ps.setString(4, admUbica.getCalle());
			ps.setString(5, admUbica.getNumero());
			ps.setString(6, admUbica.getCodigoPostal());
			ps.setString(7, admUbica.getTelefono());
			ps.setString(8, admUbica.getColonia());
			ps.setString(9, admUbica.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'99999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmUbicacionUtil|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public AdmUbicacion mapeaRegId( Connection conn, String folio ) throws SQLException{
		AdmUbicacion admUbica = new AdmUbicacion();
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, PAIS_ID, ESTADO_ID, CIUDAD_ID, CALLE," +
				" NUMERO, CODIGO_POSTAL, TELEFONO, COLONIA"+
				" FROM SALOMON.ADM_UBICACION "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			admUbica.mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }	
		return admUbica;
	}
	
	public boolean existeReg(Connection conn, String folio ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_UBICACION "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
						
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}