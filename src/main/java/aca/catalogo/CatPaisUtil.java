// Bean del Catalogo de Paises
package  aca.catalogo;

import java.sql.*;
import java.util.HashMap;

public class CatPaisUtil{
	
	public boolean insertReg(Connection conn, CatPais pais) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			 
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_PAIS"
					+ " (PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID)"
					+ " VALUES( ?, ?, ?, ?, ?)");		
			ps.setString(1, pais.getPaisId());
			ps.setString(2, pais.getNombrePais());
			ps.setString(3, pais.getNacionalidad());
			ps.setString(4, pais.getInteramerica());
			ps.setString(5, pais.getDivisionId());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPaisUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatPais pais ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_PAIS "+ 
				"SET PAIS_ID = ?, NOMBRE_PAIS = ?, NACIONALIDAD = ?, "+
				"INTERAMERICA = ?, DIVISION_ID = ?");
			ps.setString(1, pais.getPaisId());
			ps.setString(2, pais.getNombrePais());
			ps.setString(3, pais.getNacionalidad());
			ps.setString(4, pais.getInteramerica());
			ps.setString(5, pais.getDivisionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatPaisUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
public HashMap<String,CatPais> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatPais> map = new HashMap<String,CatPais>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, NOMBRE_PAIS, NACIONALIDAD, INTERAMERICA, DIVISION_ID "+
				"FROM ENOC.CAT_PAIS "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatPais obj = new CatPais();
				obj.mapeaReg(rs);
				llave = obj.getPaisId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.PaisUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}