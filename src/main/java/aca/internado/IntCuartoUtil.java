package aca.internado;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jose Torres
 *
 */
public class IntCuartoUtil {
	
	public ArrayList<Cuarto> lisCuartos(Connection conn, String dormitorioId, String orden) throws SQLException{
		ArrayList<Cuarto> listor = new ArrayList<Cuarto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = '"+dormitorioId+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Cuarto cuarto= new Cuarto();
				cuarto.mapeaReg(rs);
				listor.add(cuarto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|lisCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public static HashMap<String,Cuarto> mapCuartos(Connection conn, String dormitorioId) throws SQLException{
		
		HashMap<String,Cuarto> map		= new HashMap<String,Cuarto>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT DORMITORIO_ID, CUARTO_ID, PASILLO, CUPO, ESTADO FROM ENOC.INT_CUARTO"
					+ " WHERE DORMITORIO_ID = "+dormitorioId;
			rs = st.executeQuery(comando);			
			while (rs.next()){
				Cuarto cuarto = new Cuarto();
				cuarto.mapeaReg(rs);				
				map.put(cuarto.getCuartoId(), cuarto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.DormitorioUtil|mapCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}