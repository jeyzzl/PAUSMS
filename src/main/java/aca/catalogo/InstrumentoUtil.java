package aca.catalogo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class InstrumentoUtil {
public ArrayList<CatInstrumento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatInstrumento> lisInstrumentos = new ArrayList<CatInstrumento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT INSTRUMENTO_ID, DESCRIPCION FROM ENOC.CAT_INSTRUMENTO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatInstrumento instrumento = new CatInstrumento();
				instrumento.mapeaReg(rs);
				lisInstrumentos.add(instrumento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInstrumentos;
	}

	public HashMap<String,CatInstrumento> getMapAll(Connection conn, String orden ) throws SQLException{
	
		HashMap<String,CatInstrumento> map = new HashMap<String,CatInstrumento>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT INSTRUMENTO_ID, DESCRIPCION FROM ENOC.CAT_INSTRUMENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatInstrumento obj = new CatInstrumento();
				obj.mapeaReg(rs);
				llave = obj.getInstrumentoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CriterioUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}