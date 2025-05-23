package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatPeriodoUtil {
	
	public boolean insertReg(Connection conn, CatPeriodo periodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_PERIODO( PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO) "+
				"VALUES( ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),?)");
			ps.setString(1, periodo.getPeriodoId());
			ps.setString(2, periodo.getNombre());
			ps.setString(3, periodo.getFechaIni());
			ps.setString(4, periodo.getFechaFin());
			ps.setString(5, periodo.getEstado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	public boolean updateReg(Connection conn, CatPeriodo periodo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_PERIODO "+ 
				"SET NOMBRE_PERIODO = ?, F_INICIO = ?, F_FINAL = ?, ESTADO = ? WHERE PERIODO_ID = ? ");
			ps.setString(1, periodo.getNombre());
			ps.setString(2, periodo.getFechaIni());
			ps.setString(3, periodo.getFechaFin());
			ps.setString(4, periodo.getEstado());
			ps.setString(5, periodo.getPeriodoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String periodoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_PERIODO "+ 
				"WHERE PERIODO_ID = ?");
			ps.setString(1, periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatPeriodo mapeaRegId( Connection conn, String periodoId) throws SQLException{
		PreparedStatement ps 		= null;
		ResultSet rs 				= null;
		CatPeriodo periodo 			= new CatPeriodo();
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO_ID, NOMBRE_PERIODO, F_INICIO, "+
				"F_FINAL, ESTADO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?"); 
			ps.setString(1,periodoId);
			rs = ps.executeQuery();
			if (rs.next()){
				periodo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public boolean existeReg(Connection conn, String periodoId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = ?"); 
			ps.setString(1,periodoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getPeriodo(Connection conn) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String periodo		= "0000";
		
		try{
			comando = "SELECT PERIODO_ID AS PERIODO FROM ENOC.CAT_PERIODO " + 
					"WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				periodo = rs.getString("PERIODO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static String getPeriodo(Connection conn, String fecha) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String periodo		= "x";
		
		try{
			comando = "SELECT PERIODO_ID AS PERIODO FROM ENOC.CAT_PERIODO " + 
					"WHERE TO_DATE('"+fecha+"','DD-MM-YY') BETWEEN F_INICIO AND F_FINAL";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				periodo = rs.getString("PERIODO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static String getNombre(Connection conn, String periodo) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT NOMBRE_PERIODO FROM ENOC.CAT_PERIODO WHERE PERIODO_ID = '"+periodo+"' "; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("NOMBRE_PERIODO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}

	public static String getSigPeriodo(String periodo) throws SQLException{
		
		String sigPeriodo	= periodo;
		String temp			= "0";
		
		try{
			temp 		=  String.valueOf(Integer.parseInt(periodo)+101);
			sigPeriodo 	=  temp.length()==3?"0"+temp:temp;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getNombre|:"+ex);
		}		
		return sigPeriodo;
	}
	
	public static boolean existeFechaBetween(Connection conn, String periodoId, String fecha) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM ENOC.CAT_PERIODO " +
										" WHERE PERIODO_ID = '"+periodoId+"' " +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') >= F_INICIO" +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') <= F_FINAL" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumno|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
/*	
	public static void main(String[] args) {
		try{
			getSigPeriodo("0506");
			getSigPeriodo("0607");
			getSigPeriodo("0708");
			getSigPeriodo("0809");
		}catch(Exception e){
			System.out.println(e);
		}
	}
*/
	
	public ArrayList<CatPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<CatPeriodo> lisPeriodo	= new ArrayList<CatPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO "+
					"FROM ENOC.CAT_PERIODO "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				CatPeriodo periodo = new CatPeriodo();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisPeriodo;
	}
	
	public HashMap<String,CatPeriodo> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatPeriodo> map = new HashMap<String,CatPeriodo>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO "+
					"FROM ENOC.CAT_PERIODO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatPeriodo obj = new CatPeriodo();
				obj.mapeaReg(rs);
				llave = obj.getPeriodoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<CatPeriodo> getLista(Connection conn, String sPeriodo, String orden) throws SQLException{
		
		ArrayList<CatPeriodo> lisPeriodo	= new ArrayList<CatPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO FROM ENOC.CAT_PERIODO "+ 
					"WHERE PERDIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_BLOQUE)) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatPeriodo periodo = new CatPeriodo();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPeriodo;
	}
	
	public ArrayList<CatPeriodo> getListPeriodosMaestro(Connection conn, String codigoMaestro, String orden) throws SQLException{
		
		ArrayList<CatPeriodo> lisPeriodo	= new ArrayList<CatPeriodo>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, NOMBRE_PERIODO, F_INICIO, F_FINAL, ESTADO FROM ENOC.CAT_PERIODO" +
					" WHERE PERIODO_ID IN (SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoMaestro+"')) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatPeriodo periodo = new CatPeriodo();
				periodo.mapeaReg(rs);
				lisPeriodo.add(periodo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPeriodoUtil|getListPeriodosMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPeriodo;
	}
}