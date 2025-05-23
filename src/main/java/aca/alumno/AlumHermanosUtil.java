//Clase para la tabla de Alum_Academico
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumHermanosUtil{
	
	public boolean insertReg(Connection conn, AlumHermanos alumHermanos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_HERMANOS"+ 
				"(FAMILIA_ID, CODIGO_PERSONAL, ESTADO)"+
				" VALUES( ?, ?, ?)");
					
			ps.setInt(1, Integer.parseInt(alumHermanos.getFamiliaId()));
			ps.setString(2, alumHermanos.getCodigoPersonal());
			ps.setString(3, alumHermanos.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumHermanos alumHermanos ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_HERMANOS "+ 
				"SET"+
				" FECHA = now(),"+
				" ESTADO = ?"+
				" WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, alumHermanos.getEstado());
			ps.setInt(2, Integer.parseInt(alumHermanos.getFamiliaId()));
			ps.setString(3, alumHermanos.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn,String familiaId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_HERMANOS"+ 
				" WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setInt(1, Integer.parseInt(familiaId));
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumHermanos mapeaRegId( Connection conn, String familiaId, String codigoPersonal) throws SQLException{
		AlumHermanos alumHermanos = new AlumHermanos();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_HERMANOS WHERE FAMILIA_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setInt(1, Integer.parseInt(familiaId));
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumHermanos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumHermanos;
	}
	
	public AlumHermanos mapeaRegIdPorMatricula( Connection conn, String matricula) throws SQLException{
		AlumHermanos alumHermanos = new AlumHermanos();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"
					+ " FROM ENOC.ALUM_HERMANOS"
					+ " WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumHermanos.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|mapeaRegIdPorMatricula|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumHermanos;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT FAMILIA_ID FROM ENOC.ALUM_HERMANOS "+
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public ArrayList<AlumHermanos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumHermanos> listaAlumnos= new ArrayList<AlumHermanos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
				" FROM ENOC.ALUM_HERMANOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumHermanos alumno = new AlumHermanos();
				alumno.mapeaReg(rs);
				listaAlumnos.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		return listaAlumnos;
	}
	
	public HashMap<String, AlumHermanos> getMapAll(Connection conn, String condiciones) throws SQLException{
		HashMap<String, AlumHermanos> map = new HashMap<String, AlumHermanos>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT FAMILIA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY HH:MI AM') AS FECHA, ESTADO"+
					" FROM ENOC.ALUM_HERMANOS "+condiciones; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumHermanos alumno = new AlumHermanos();
				alumno.mapeaReg(rs);
				map.put(rs.getString("FAMILIA_ID")+"-"+rs.getString("CODIGO_PERSONAL"), alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumHermanosUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
}