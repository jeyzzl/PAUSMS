package aca.kardex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class KrdxAltaUtil {
	
	public ArrayList<KrdxAlta> getLista(Connection conn, String year, String orden ) throws SQLException{
		
		ArrayList<KrdxAlta> lista		= new ArrayList<KrdxAlta>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		try{
			String comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CREDITOS, CURSO_ID, CARRERA_ID, MODALIDAD_ID, YEAR, TIPO"
					+ " FROM ENOC.KRDX_ALTA"
					+ " WHERE YEAR = "+year 
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE ESTADO = 'I') "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				KrdxAlta alta = new KrdxAlta();
				alta.mapeaReg(rs);
				lista.add(alta);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAltaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return lista;
	}

}