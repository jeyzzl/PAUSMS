package aca.admision;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AdmAcademicoUtil {
	public ArrayList<AdmAcademico> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AdmAcademico> list	= new ArrayList<AdmAcademico>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA "
					+ " FROM SALOMON.ADM_ACADEMICO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AdmAcademico academico = new AdmAcademico();
				academico.mapeaReg(rs);
				list.add(academico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmAcademicoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}	
	
	public HashMap<String, AdmAcademico> mapAlumnos(Connection conn, String condicion) throws SQLException{
		
		HashMap<String, AdmAcademico> map		= new HashMap<String,AdmAcademico>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT FOLIO, MODALIDAD_ID, NIVEL_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA"
					+ " FROM SALOMON.ADM_ACADEMICO "+condicion;
			rs = st.executeQuery(comando);
			while (rs.next()){	
				AdmAcademico academico = new AdmAcademico();
				academico.mapeaReg(rs);
				map.put(rs.getString("FOLIO"), academico);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.admision.AdmAcademicoUtil|mapAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}