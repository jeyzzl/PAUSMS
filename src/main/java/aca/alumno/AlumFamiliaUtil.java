//Clase para la tabla de Alum_Academico
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumFamiliaUtil{
	
	public boolean insertReg(Connection conn, AlumFamilia alumFamilia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_FAMILIA"+ 
				"(FAMILIA_ID, FECHA, ESTADO)"+
				" VALUES( ?, now(), ?)");
					
			ps.setInt(1, Integer.parseInt(alumFamilia.getFamiliaId()));
			ps.setString(2, alumFamilia.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumFamilia alumFamilia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_FAMILIA "+ 
				"SET"+
				" ESTADO = ?"+
				" WHERE FAMILIA_ID = ?");
			ps.setString(1, alumFamilia.getEstado());
			ps.setInt(2, Integer.parseInt(alumFamilia.getFamiliaId()));
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String familiaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_FAMILIA"+ 
				" WHERE FAMILIA_ID = ?");
			ps.setInt(1, Integer.parseInt(familiaId));
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumFamilia mapeaRegId( Connection conn, String familiaId) throws SQLException{
		AlumFamilia alumFamilia = new AlumFamilia();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MM AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_FAMILIA WHERE FAMILIA_ID = ?"); 
			ps.setInt(1, Integer.parseInt(familiaId));
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumFamilia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumFamilia;
	}
	
	public boolean existeReg(Connection conn, String familiaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID FROM ENOC.ALUM_FAMILIA "+ 
				"WHERE FAMILIA_ID = ?");
			ps.setInt(1, Integer.parseInt(familiaId));
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FAMILIA_ID)+1, 1) AS MAXIMO"+
				" FROM ENOC.ALUM_FAMILIA"); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = String.valueOf(rs.getInt("MAXIMO"));			
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
		
	public ArrayList<AlumFamilia> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumFamilia> listaAlumnos= new ArrayList<AlumFamilia>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM')AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_FAMILIA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumFamilia alumno = new AlumFamilia();
				alumno.mapeaReg(rs);
				listaAlumnos.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		return listaAlumnos;
	}
	
	public HashMap<String, AlumFamilia> getMapAll(Connection conn, String condiciones) throws SQLException{
		HashMap<String, AlumFamilia> map = new HashMap<String, AlumFamilia>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT FAMILIA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM')AS FECHA, ESTADO"+
					" FROM ENOC.ALUM_FAMILIA "+condiciones; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumFamilia alumno = new AlumFamilia();
				alumno.mapeaReg(rs);
				map.put(rs.getString("FAMILIA_ID"), alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumFamiliaUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
}