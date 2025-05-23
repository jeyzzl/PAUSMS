package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatHorarioFacUtil {
	public ArrayList<CatHorarioFac> getLista(Connection conn, String facultad, String orden ) throws SQLException{
		
		ArrayList<CatHorarioFac> lisHorario		= new ArrayList<CatHorarioFac>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD_ID FROM ENOC.CAT_HORARIOFAC "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorarioFac horario = new CatHorarioFac();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorarioFac> getListaTurno(Connection conn, String facultad, String turno, String orden) throws SQLException{
		
		ArrayList<CatHorarioFac> lisHorario		= new ArrayList<CatHorarioFac>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD_ID FROM ENOC.CAT_HORARIOFAC "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' AND TURNO = '"+turno+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFac horario = new CatHorarioFac();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getListaTurno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<Integer, CatHorarioFac> getListaTurnoHashMap(Connection conn, String facultad, String turno, String orden) throws SQLException{
		
		HashMap<Integer, CatHorarioFac> lisHorario		= new HashMap<Integer, CatHorarioFac>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD_ID FROM ENOC.CAT_HORARIOFAC "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' AND TURNO = '"+turno+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFac horario = new CatHorarioFac();
				horario.mapeaReg(rs);
				lisHorario.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getListaTurnoHashMap|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<Integer, CatHorarioFac> getListaAllHashMap(Connection conn, String facultad, String orden) throws SQLException{
		
		HashMap<Integer, CatHorarioFac> lisHorario		= new HashMap<Integer, CatHorarioFac>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_HORARIOFAC "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFac horario = new CatHorarioFac();
				horario.mapeaReg(rs);
				lisHorario.put(Integer.parseInt(horario.getPeriodo()), horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getListaAllHashMap|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorarioFac> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorarioFac> lisHorario		= new ArrayList<CatHorarioFac>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD_ID FROM ENOC.CAT_HORARIOFAC "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorarioFac horario = new CatHorarioFac();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<String,CatHorarioFac> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatHorarioFac> map = new HashMap<String,CatHorarioFac>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT FACULTAD_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL FROM ENOC.CAT_HORARIOFAC "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFac obj = new CatHorarioFac();
				obj.mapeaReg(rs);
				llave = obj.getFacultad()+obj.getPeriodo();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,CatHorarioFac> getMapHoras(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatHorarioFac> map = new HashMap<String,CatHorarioFac>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT FACULTAD_ID, PERIODO, TURNO, TO_CHAR(HORA_INICIO,'HH24MI') AS HORA_INICIO, TO_CHAR(HORA_FINAL,'HH24MI') AS HORA_FINAL" +
					" FROM ENOC.CAT_HORARIOFAC " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatHorarioFac obj = new CatHorarioFac();
				obj.mapeaReg(rs);
				llave = obj.getFacultad()+obj.getPeriodo();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacUtil|getMapHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}
