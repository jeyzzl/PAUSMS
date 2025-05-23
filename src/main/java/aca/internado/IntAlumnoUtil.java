package aca.internado;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class IntAlumnoUtil {
	
	public ArrayList<Alumno> getListDormitorioPorFechaOtros(Connection conn, String dormitorioId, String fechaIni, String fechaFin) throws SQLException{
		ArrayList<Alumno> lisInscritos	= new ArrayList<Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT DORMITORIO_ID, CUARTO_ID, CODIGO_PERSONAL, FECHA_FINAL, ESTADO, FOLIO FROM ENOC.INT_ALUMNO"
					+ " WHERE DORMITORIO_ID = '"+dormitorioId+"'"
					+ " AND ESTADO = 'A'"
					+ " AND CODIGO_PERSONAL NOT IN( "
					+ " 		SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS"
					+ " 		WHERE RESIDENCIA_ID = 'I' "
					+ "			AND DORMITORIO = '"+dormitorioId+"' "
					+ "			AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') "
					+ "			AND TO_DATE('"+fechaFin+"','DD/MM/YYYY'))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Alumno alumnos = new Alumno();
				alumnos.mapeaReg(rs);
				lisInscritos.add(alumnos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.IntAlumnoUtil|getListDormitorioPorFechaOtros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lisInscritos;
	}
	
	/*SELECT CUARTO_ID, COUNT(*) AS TOTAL FROM INT_ALUMNO WHERE DORMITORIO_ID = 1 GROUP BY CUARTO_ID;*/
	
	public HashMap<String,String> mapOcupados(Connection conn, String dormitorioId) throws SQLException{
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		Statement st 					= conn.createStatement();
		ResultSet rs		 			= null;
		String comando					= "";
				
		try{
			comando = "SELECT CUARTO_ID, COUNT(*) AS TOTAL FROM INT_ALUMNO WHERE DORMITORIO_ID = "+dormitorioId+" GROUP BY CUARTO_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				mapa.put(rs.getString("CUARTO_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.IntAlumnoUtil|mapOcupados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
}