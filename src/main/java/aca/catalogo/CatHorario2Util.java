package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CatHorario2Util {
	public ArrayList<CatHorario2> getLista(Connection conn, String turno, String orden ) throws SQLException{
		
		ArrayList<CatHorario2> lisHorario		= new ArrayList<CatHorario2>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD FROM ENOC.CAT_HORARIO2 "+ 
				"WHERE TURNO = '"+turno+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario2 horario = new CatHorario2();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.HorarioUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorario2> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorario2> lisHorario		= new ArrayList<CatHorario2>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD FROM ENOC.CAT_HORARIO2 "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario2 horario = new CatHorario2();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.HorarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
}
