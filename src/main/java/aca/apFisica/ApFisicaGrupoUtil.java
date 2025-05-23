// Clase para la tabla de Acceso
package aca.apFisica;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ApFisicaGrupoUtil{
	
	public boolean insertReg(Connection conn, ApFisicaGrupo apFGrupo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
					" ENOC.APFISICA_GRUPO(GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, F_INICIO, F_FINAL, DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, F_CIERRE, ACCESO, LIGA)" +
					" VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, TO_NUMBER(?, '999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?)");
			ps.setString(1, apFGrupo.getGrupoID());
			ps.setString(2, apFGrupo.getNombreGrupo());
			ps.setString(3, apFGrupo.getLugar());
			ps.setString(4, apFGrupo.getInstructor());
			ps.setString(5, apFGrupo.getCupo());
			ps.setString(6, apFGrupo.getfInicio());
			ps.setString(7, apFGrupo.getfFinal());
			ps.setString(8, apFGrupo.getDia1());
			ps.setString(9, apFGrupo.getCargas());
			ps.setString(10, apFGrupo.getClave());
			ps.setString(11, apFGrupo.getDescripcion());
			ps.setString(12, apFGrupo.getHora());
			ps.setString(13, apFGrupo.getfCierre());
			ps.setString(14, apFGrupo.getAcceso());
			ps.setString(15, apFGrupo.getLiga());			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, ApFisicaGrupo apFGrupo ) throws SQLException{
		boolean ok = false;
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE ENOC.APFISICA_GRUPO"
					+ " SET NOMBRE_GRUPO = ?, LUGAR = ?, INSTRUCTOR = ?, CUPO = TO_NUMBER(?, '999'), F_INICIO = TO_DATE(?,'DD/MM/YYYY'), F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
					+ " DIA1 = ?, CARGAS = ?, CLAVE = ?, DESCRIPCION = ?, HORA = ?, F_CIERRE = TO_DATE(?,'DD/MM/YYYY'), ACCESO = ?, LIGA = ?"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'9999') ");
			ps.setString(1, apFGrupo.getNombreGrupo());
			ps.setString(2, apFGrupo.getLugar());
			ps.setString(3, apFGrupo.getInstructor());
			ps.setString(4, apFGrupo.getCupo());
			ps.setString(5, apFGrupo.getfInicio());
			ps.setString(6, apFGrupo.getfFinal());
			ps.setString(7, apFGrupo.getDia1());
			ps.setString(8, apFGrupo.getCargas());
			ps.setString(9, apFGrupo.getClave());
			ps.setString(10, apFGrupo.getDescripcion());
			ps.setString(11, apFGrupo.getHora());
			ps.setString(12, apFGrupo.getfCierre());
			ps.setString(13, apFGrupo.getAcceso());
			ps.setString(14, apFGrupo.getLiga());
			ps.setString(15, apFGrupo.getGrupoID());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
			try { ps.close(); } catch (Exception ignore) { }
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|updateReg|: "+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String grupoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
			ps.setString(1, grupoId);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ApFisicaGrupo mapeaRegId(Connection conn, String grupoID) throws SQLException{
		ApFisicaGrupo apFGrupo = new ApFisicaGrupo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA"
					+ " FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = '"+grupoID+"' "); 
			rs = ps.executeQuery();
			
			if(rs.next()){
				apFGrupo.mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
		return apFGrupo;
	}
	
	public boolean existeReg(Connection conn, String grupoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')");
			ps.setString(1, grupoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo			= "1";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(GRUPO_ID)+1,1) AS MAXIMO FROM ENOC.APFISICA_GRUPO"); 
			rs= ps.executeQuery();		
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|maximo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return maximo;
	}
	
	public static String getCargas(Connection conn, String grupoID) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String cargas			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(CARGAS,'-') AS CARGAS FROM ENOC.APFISICA_GRUPO"); 
			rs= ps.executeQuery();		
			if (rs.next())
				cargas = rs.getString("CARGAS");				
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return cargas;
	}
	
	public static String getClave(Connection conn, String grupoID) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String clave			= "-";
		
		try{
			ps	= conn.prepareStatement("SELECT CLAVE FROM ENOC.APFISICA_GRUPO"); 
			rs	= ps.executeQuery();		
			if (rs.next())
				clave = rs.getString("CLAVE");				
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getClave|:"+ex);
		}finally{
			if (rs !=null) rs.close();
			if (ps !=null) ps.close();
		}			
		return clave;
	}
	
	public static boolean getTieneGrupo(Connection conn, String cargaId, String clave) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.APFISICA_GRUPO WHERE CARGAS LIKE '%"+cargaId+"%' AND CLAVE = '"+clave+"'");
			rs= ps.executeQuery();		
			if (rs.next())
				ok = true;
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getTieneGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public static boolean hayGrupoActivos(Connection conn, String cargaId, String clave, String acceso) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.APFISICA_GRUPO "
					+ " WHERE INSTR(CARGAS,?) > 0"
					+ " AND CLAVE = ?"
					+ " AND ACCESO = ?"
					+ " AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE");
			ps.setString(1, cargaId);
			ps.setString(2, clave);
			ps.setString(3, acceso);
			
			rs= ps.executeQuery();		
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|hayGrupoActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public String nombreGrupo(Connection conn, String grupoId) throws SQLException{
		String maximo			= "1";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT  NOMBRE_GRUPO  FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = '"+grupoId+"' "); 
			rs= ps.executeQuery();		
			if (rs.next())
				maximo = rs.getString("NOMBRE_GRUPO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|nombreGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return maximo;
	}
	
	public ArrayList<ApFisicaGrupo> lisTieneGrupo(Connection conn, String fecha, String orden) throws SQLException{
		
		ArrayList<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = " SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA"
					+ " FROM ENOC.APFISICA_GRUPO"
					+ " WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaGrupo objeto = new ApFisicaGrupo();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getTieneGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
		
	public ArrayList<ApFisicaGrupo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = " SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA "
					+ " FROM ENOC.APFISICA_GRUPO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaGrupo horario = new ApFisicaGrupo();
				horario.mapeaReg(rs);
				lista.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}	
	
	public ArrayList<ApFisicaGrupo> getGruposAlumnos(Connection conn, String cargaId, String clave, String acceso, String orden) throws SQLException{
		
		ArrayList<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();
		Statement st 		= conn.createStatement();		
		ResultSet rs 		= null;		
		String comando	    = "";
		
		try{
			comando = " SELECT  GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA "
					+ " FROM ENOC.APFISICA_GRUPO"
					+ " WHERE CARGAS LIKE '%"+cargaId+"%'"
					+ " AND CLAVE = '"+clave+"'"					
					+ " AND ACCESO IN ("+acceso+") "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ApFisicaGrupo horario = new ApFisicaGrupo();
				horario.mapeaReg(rs);
				lista.add(horario);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getGruposAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ApFisicaGrupo> getGrupo(Connection conn, String grupoId) throws SQLException{
		
		ArrayList<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT * FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID= '"+grupoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaGrupo objeto = new ApFisicaGrupo();
				objeto.mapeaReg(rs);
				lista.add(objeto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getTieneGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	
	public String getNombreGrupo(Connection conn, String grupoId) throws SQLException{
		
		String nombre 	= "";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT * FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID= '"+grupoId+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ApFisicaGrupo objeto = new ApFisicaGrupo();
				objeto.mapeaReg(rs);
				nombre = objeto.getNombreGrupo();
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|getNombreGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return nombre;
	}
	
	public HashMap<String,String> mapaActivos(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;						
		try{
			String comando = " SELECT GRUPO_ID FROM ENOC.APFISICA_GRUPO"
					+ " WHERE CARGAS LIKE '%"+cargaId+"%'"					
					+ " AND TO_DATE(TO_CHAR(now(), 'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put( rs.getString("GRUPO_ID"), rs.getString("GRUPO_ID"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|mapaActivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
		
}