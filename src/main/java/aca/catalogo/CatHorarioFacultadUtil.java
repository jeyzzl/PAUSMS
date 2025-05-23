package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatHorarioFacultadUtil {
	public ArrayList<CatHorarioFacultad> getLista(Connection conn, String horarioId, String orden ) throws SQLException{
		
		ArrayList<CatHorarioFacultad> lisHorario		= new ArrayList<CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE HORARIO_ID = '"+horarioId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorarioFacultad> getListaTurno(Connection conn, String horarioId, String turno, String orden) throws SQLException{
		
		ArrayList<CatHorarioFacultad> lisHorario		= new ArrayList<CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE HORARIO_ID = '"+horarioId+"' AND TURNO = '"+turno+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getListaTurno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<String> getTurno(Connection conn, String horarioId, String orden) throws SQLException{		
		ArrayList<String> lisHorario	= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";		
		try{
			comando = "SELECT DISTINCT(TURNO) AS TURNO FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = '"+horarioId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisHorario.add(rs.getString("TURNO"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getTurno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisHorario;
	}
	
	public HashMap<Integer, CatHorarioFacultad> getListaTurnoHashMap(Connection conn, String horarioId, String turno, String orden) throws SQLException{
		
		HashMap<Integer, CatHorarioFacultad> lisHorario		= new HashMap<Integer,CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE HORARIO_ID = '"+horarioId+"' AND TURNO = '"+turno+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getListaTurnoHashMap|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<Integer, CatHorarioFacultad> getListaAllHashMap(Connection conn, String horarioId, String orden) throws SQLException{
		
		HashMap<Integer, CatHorarioFacultad> lisHorario		= new HashMap<Integer, CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE HORARIO_ID = '"+horarioId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getListaAllHashMap|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorarioFacultad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorarioFacultad> lisHorario		= new ArrayList<CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, HORARIO_ID, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<String,CatHorarioFacultad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatHorarioFacultad> map = new HashMap<String,CatHorarioFacultad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO FROM ENOC.CAT_HORARIOFACULTAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFacultad obj = new CatHorarioFacultad();
				obj.mapeaReg(rs);
				llave = obj.getHorarioId()+obj.getPeriodo();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,CatHorarioFacultad> getMapHoras(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatHorarioFacultad> map = new HashMap<String,CatHorarioFacultad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO" +
					" FROM ENOC.CAT_HORARIOFACULTAD " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFacultad obj = new CatHorarioFacultad();
				obj.mapeaReg(rs);
				llave = obj.getHorarioId()+obj.getPeriodo();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getMapHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<CatHorarioFacultad> getPeriodos(Connection conn, String horarioId, String orden ) throws SQLException{
		
		ArrayList<CatHorarioFacultad> lisHorario		= new ArrayList<CatHorarioFacultad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = '"+horarioId+"'"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorarioFacultad horario = new CatHorarioFacultad();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultadUtil|getPeriodos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
}
