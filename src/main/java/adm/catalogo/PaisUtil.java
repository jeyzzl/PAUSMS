// Clase Util para la tabla de Cat_Division
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class PaisUtil{
		
	public ArrayList<CatPais> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatPais> lisPais		= new ArrayList<CatPais>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD FROM ENOC.CAT_PAIS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatPais pais = new CatPais();
				pais.mapeaReg(rs);
				lisPais.add(pais);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.PaisUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPais;
	}
	public String getPais(Connection Conn, String paisId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS where pais_id = "+paisId;
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.PaisUtil|getPais|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}		
}