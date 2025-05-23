package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExaBitacoraUtil {
	
	public boolean insertReg(Connection conn, ExaBitacora exaBitacora ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_BITACORA(BITACORA_ID, MATRICULA, TEXTO, FECHA, " +
				"ELIMINADO, IDBITACORA) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ?, TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			ps.setString(1, exaBitacora.getBitacoraId());
			ps.setString(2, exaBitacora.getMatricula());
			ps.setString(3, exaBitacora.getTexto());
			ps.setString(4, exaBitacora.getTexto());
			ps.setString(5, exaBitacora.getEliminado());
			ps.setString(6, exaBitacora.getIdBitacora());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, String bitacoraId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_BITACORA"+ 
				" SET ELIMINADO = '1' " +
				" WHERE REDSOCIAL_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, bitacoraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaBitacora mapeaRegIdEstudio( Connection conn, String bitacoraId) throws SQLException{
		ExaBitacora exaBitacora = new ExaBitacora();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT BITACORA_ID, MATRICULA, TEXTO, " +
					" FECHA, ELIMINADO, IDBITACORA "+
				"FROM ENOC.EXA_BITACORA WHERE BITACORA_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,bitacoraId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaBitacora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaBitacora;
	}
	
	public ExaBitacora mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaBitacora exaBitacora = new ExaBitacora();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT REDSOCIAL_ID, MATRICULA, RED,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDREDSOCIAL "+
				"FROM ENOC.EXA_BITACORA WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaBitacora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaBitacora;
	}
	
	public boolean existeReg(Connection conn, String bitacoraId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_BITACORA WHERE BITACORA_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,bitacoraId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String matricula) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(BITACORA_ID)+1 AS MAXIMO FROM ENOC.EXA_BITACORA WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(BITACORA_ID)+1 AS MAXIMO FROM ENOC.EXA_BITACORA");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaBitacora> getBitacora(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaBitacora> list		= new ArrayList<ExaBitacora>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT BITACORA_ID, MATRICULA, TEXTO, " +
					" FECHA, ELIMINADO, IDBITACORA FROM ENOC.EXA_BITACORA" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaBitacora obj = new ExaBitacora();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaBitacoraUtil|getBitacora|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
