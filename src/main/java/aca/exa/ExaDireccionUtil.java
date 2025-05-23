package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ExaDireccionUtil {
	
	public boolean insertReg(Connection conn, ExaDireccion exaDireccion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_DIRECCION(DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, " +
				" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION) "+
				"VALUES(TO_NUMBER(?,'99999999'), ?, ? ,?, TO_NUMBER(?,'99999'), TO_NUMBER(?,'999'), ?, " +
				"TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'),TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaDireccion.getDirId());
			ps.setString(2, exaDireccion.getMatricula());
			ps.setString(3, exaDireccion.getCiudad());
			ps.setString(4, exaDireccion.getDireccion());
			ps.setString(5, exaDireccion.getEdoId());
			ps.setString(6, exaDireccion.getPaisId());
			ps.setString(7, exaDireccion.getCp());
			ps.setString(8, exaDireccion.getFechaAct());
			ps.setString(9, exaDireccion.getEliminado());
			ps.setString(10, exaDireccion.getIdDir());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String dirId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_DIRECCION"+ 
				" SET ELIMINADO = '1' " +
				" WHERE DIRECCION_ID = TO_NUMBER(?,'99999999')");
			
			ps.setString(1, dirId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public ExaDireccion mapeaRegIdEstudio( Connection conn, String direccionId) throws SQLException{
		ExaDireccion exaDireccion = new ExaDireccion();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP, " +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION "+
				"FROM ENOC.EXA_DIRECCION WHERE DIRECCION_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,direccionId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaDireccion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaEgreso|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaDireccion;
	}
	
	public ExaDireccion mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaDireccion exaDireccion = new ExaDireccion();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION "+
				"FROM ENOC.EXA_DIRECCION WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaDireccion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaDireccion;
	}
	
	public boolean existeReg(Connection conn, String dirId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_DIRECCION WHERE DIRECCION_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1,dirId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeAlumno(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_DIRECCION WHERE MATRICULA = ?  AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(DIRECCION_ID) AS MAXIMO FROM ENOC.EXA_DIRECCION WHERE ELIMINADO !=1 AND MATRICULA = ?");
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(DIRECCION_ID)+1 AS MAXIMO FROM ENOC.EXA_DIRECCION");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaDireccion> getListAll(Connection conn, String orden) throws SQLException{
		ArrayList<ExaDireccion> listExa		= new ArrayList<ExaDireccion>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP," +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION FROM ENOC.EXA_DIRECCION " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ExaDireccion doc = new ExaDireccion();
				doc.mapeaReg(rs);
				listExa.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listExa;
	}

	public ArrayList<ExaDireccion> getDireccion(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaDireccion> list		= new ArrayList<ExaDireccion>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP," +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION FROM ENOC.EXA_DIRECCION" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaDireccion obj = new ExaDireccion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|getDireccion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String,ExaDireccion> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,ExaDireccion> map = new HashMap<String,ExaDireccion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT DIRECCION_ID, MATRICULA, CIUDAD, DIRECCION, ESTADO_ID, PAIS_ID, CP," +
					" FECHAACTUALIZACION, ELIMINADO, IDDIRECCION FROM ENOC.EXA_DIRECCION" +
					" WHERE ELIMINADO != 1 "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ExaDireccion obj = new ExaDireccion();
				obj.mapeaReg(rs);
				llave = obj.getMatricula();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaDireccionUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}
