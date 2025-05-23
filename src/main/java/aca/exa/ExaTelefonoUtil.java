package aca.exa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ExaTelefonoUtil {
	
	public boolean insertReg(Connection conn, ExaTelefono exaTelefono ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.EXA_TELEFONO(TELEFONO_ID, MATRICULA, TIPO, TELEFONO, FECHAACTUALIZACION, " +
				"ELIMINADO, IDTELEFONO) "+
				"VALUES( TO_NUMBER(?,'99999999'), ?, ? ,?,TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'), " +
				"TO_NUMBER(?,'9'), ? )");
			
			ps.setString(1, exaTelefono.getTelefonoId());
			ps.setString(2, exaTelefono.getMatricula());
			ps.setString(3, exaTelefono.getTipo());
			ps.setString(4, exaTelefono.getTelefono());
			ps.setString(5, exaTelefono.getFechaAct());
			ps.setString(6, exaTelefono.getEliminado());
			ps.setString(7, exaTelefono.getIdTelefono());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean eliminar(Connection conn, String telefonoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.EXA_TELEFONO"+ 
				" SET ELIMINADO = '1' " +
				" WHERE TELEFONO_ID = TO_NUMBER(?,'99999999')");
			ps.setString(1, telefonoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|eliminar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ExaTelefono mapeaRegIdEstudio( Connection conn, String telefonoId) throws SQLException{
		ExaTelefono exaTelefono = new ExaTelefono();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO "+
				"FROM ENOC.EXA_TELEFONO WHERE TELEFONO_ID = TO_NUMBER(?,'99999999')"); 
			ps.setString(1,telefonoId);
			rs = ps.executeQuery();
			if (rs.next()){
				exaTelefono.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaTelefono;
	}
	
	public ExaTelefono mapeaRegId( Connection conn, String matricula) throws SQLException{
		ExaTelefono exaTelefono = new ExaTelefono();
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO,  " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO "+
				"FROM ENOC.EXA_TELEFONO WHERE MATRICULA = ?"); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next()){
				exaTelefono.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return exaTelefono;
	}
	
	public boolean existeReg(Connection conn, String telefonoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_TELEFONO WHERE TELEFONO_ID = TO_NUMBER(?,'99999999') "); 
			ps.setString(1, telefonoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.EXA_TELEFONO WHERE MATRICULA = ? AND ELIMINADO!=1 "); 
			ps.setString(1,matricula);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|existeAlumno|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(TELEFONO_ID)+1 AS MAXIMO FROM ENOC.EXA_TELEFONO WHERE ELIMINADO !=1 AND MATRICULA = ?"); 
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|maximoReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(TELEFONO_ID)+1 AS MAXIMO FROM ENOC.EXA_TELEFONO");
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public ArrayList<ExaTelefono> getTelefono(Connection conn, String matricula, String orden) throws SQLException{
		
		ArrayList<ExaTelefono> list		= new ArrayList<ExaTelefono>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT TELEFONO_ID, MATRICULA, TIPO, TELEFONO, " +
					" FECHAACTUALIZACION, ELIMINADO, IDTELEFONO FROM ENOC.EXA_TELEFONO" +
					" WHERE MATRICULA = '"+matricula+"' AND ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ExaTelefono obj = new ExaTelefono();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|getTelefono|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public HashMap<String, ExaTelefono> getMapTelefono(Connection conn, String orden) throws SQLException{
		
		HashMap<String, ExaTelefono> mapa		= new HashMap<String, ExaTelefono>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.EXA_TELEFONO WHERE ELIMINADO != 1 "+orden;
			
			rs = st.executeQuery(comando);
			String cod = "";
			while (rs.next()){
				if(!cod.equals(rs.getString("MATRICULA"))){
					cod = rs.getString("MATRICULA");
					
					ExaTelefono obj = new ExaTelefono();
					obj.mapeaReg(rs);
					mapa.put(obj.getMatricula(), obj);					
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.exa.ExaTelefonoUtil|getMapTelefono|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
}
