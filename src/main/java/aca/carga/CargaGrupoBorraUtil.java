// Bean del Catalogo de Grupos
package  aca.carga;

import java.sql.*;

public class CargaGrupoBorraUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoBorra borra ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_BORRA"+ 
				" (CURSO_CARGA_ID, FOLIO, FECHA, USUARIO, IP, NUMEST,NUMACT)"+				 
				" VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'), ?, ?," +
				" TO_NUMBER(?,'99'), TO_NUMBER(?,'999'))");			
			ps.setString(1, borra.getCursoCargaId());
			ps.setString(2, borra.getFolio());
			ps.setString(3, borra.getFecha());
			ps.setString(4, borra.getUsuario());
			ps.setString(5, borra.getIp());
			ps.setString(6, borra.getNumEst());
			ps.setString(7, borra.getNumAct());			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoBorra borra ) throws Exception{
		
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_BORRA"+ 
				" SET FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" USUARIO = ?," +
				" IP = ?," +
				" NUMEST = TO_NUMBER(?,'99')," +
				" NUMACT = TO_NUMBER(?,'999')"+				
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");		
			
			ps.setString(3, borra.getFecha());
			ps.setString(4, borra.getUsuario());
			ps.setString(5, borra.getIp());
			ps.setString(6, borra.getNumEst());
			ps.setString(7, borra.getNumAct());
			ps.setString(1, borra.getCursoCargaId());
			ps.setString(2, borra.getFolio());
					
			if (ps.executeUpdate()== 1){
				ok = true;				
			}else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
		
	public boolean deleteReg(Connection conn, String cursoCargaId, String folio ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_BORRA"+ 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoBorra mapeaRegId( Connection conn, String cursoCargaId, String folio ) throws SQLException{
		
		CargaGrupoBorra borra = new CargaGrupoBorra();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CURSO_CARGA_ID,"+
				" FOLIO,"+
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"+
				" USUARIO,"+
				" IP,"+
				" NUMEST,"+
				" NUMACT"+			
				" FROM ENOC.CARGA_GRUPO_BORRA " + 
				" WHERE CURSO_CARGA_ID = ? " +
				" AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				borra.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return borra;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String folio) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND FOLIO = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo 			= "1";	
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.CARGA_GRUPO_BORRA" + 
					" WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoBorra|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
}