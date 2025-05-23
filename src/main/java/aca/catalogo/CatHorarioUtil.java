package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CatHorarioUtil {
	public ArrayList<CatHorario> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatHorario> lisHorario		= new ArrayList<CatHorario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_HORARIO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario horario = new CatHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CatHorario> filtroporAcceso(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CatHorario> lisHorario		= new ArrayList<CatHorario>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.CAT_HORARIO"+
					  " WHERE FACULTAD_ID IN "+
					  " (SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'),CARRERA_ID)>0) "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatHorario horario = new CatHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioUtil|filtroporAcceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public static HashMap<String, String> mapHorario(Connection conn) throws SQLException{
		
		HashMap<String, String> mapHorario	= new HashMap<String, String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, HORARIO_ID FROM ENOC.CAT_HORARIO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapHorario.put(rs.getString("FACULTAD_ID"), rs.getString("HORARIO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioUtil|mapHorario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapHorario;
	}
	
	public String getHorarioId(Connection conn, String cursoCargaId) throws SQLException{
		
		String  horarioId		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ( " SELECT HORARIO_ID FROM ENOC.CAT_HORARIO WHERE FACULTAD_ID =" +
										 " (SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = '"+cursoCargaId+"') AND ESTADO = 'A'");	 
			rs= ps.executeQuery();		
			if(rs.next()){
				horarioId = rs.getString("HORARIO_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioUtil|getHorarioId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horarioId;
	}
}
