// Clase Util para la tabla de Cat_Carrera
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class HorarioUtil{
		
	public ArrayList<CatHorario> getLista(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorario> lisHorario		= new ArrayList<CatHorario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO  FROM ENOC.CAT_HORARIO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario horario = new CatHorario();
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
	
	public ArrayList<CatHorario> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorario> lisHorario		= new ArrayList<CatHorario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT HORARIO_ID, FACULTAD_ID, DESCRIPCION, ESTADO FROM ENOC.CAT_HORARIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario horario = new CatHorario();
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
	
	public static String getHorarioId(Connection conn, String facultadId ) throws SQLException{
			
			ResultSet 		rs		= null;			
			Statement st 			= conn.createStatement();
			String horarioId		= "";
			
			try{
				String comando = "SELECT HORARIO_ID FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID = '"+facultadId+"' AND ESTADO = 'A' ";
				rs = st.executeQuery(comando);				
				if (rs.next())
					horarioId = rs.getString("HORARIO_ID");
				
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.HorarioUtil|getHorarioId|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return horarioId;
		}
}