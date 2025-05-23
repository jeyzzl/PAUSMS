package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiHorarioUtil {
	public ArrayList<MusiHorario> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiHorario> lisMusiHorario	= new ArrayList<MusiHorario>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT HORARIO_ID, MAESTRO_ID, HORA_INICIO, HORA_FINAL, CUPO, SALON_ID, DIA, ESTADO, MIN_INICIO, MIN_FINAL "+
			"FROM ENOC.MUSI_HORARIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiHorario mc = new MusiHorario();
				mc.mapeaReg(rs);
				lisMusiHorario.add(mc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiHorarioUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiHorario;
	}
	public ArrayList<MusiHorario> getListMaestro(Connection conn, String maestroId, String orden) throws SQLException{
		
		ArrayList<MusiHorario> lisMusiMaestro	= new ArrayList<MusiHorario>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT HORARIO_ID, MAESTRO_ID, HORA_INICIO, HORA_FINAL, CUPO, SALON_ID, DIA, ESTADO, MIN_INICIO, MIN_FINAL "+
			"FROM ENOC.MUSI_HORARIO "+ 
			"WHERE MAESTRO_ID = '"+maestroId+"'" +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				MusiHorario mc = new MusiHorario();
				mc.mapeaReg(rs);
				lisMusiMaestro.add(mc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiHorarioUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMusiMaestro;
	}

}