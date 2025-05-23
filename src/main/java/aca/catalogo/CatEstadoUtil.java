
package  aca.catalogo;

import java.sql.*;
import java.util.HashMap;

public class CatEstadoUtil{
	
	public boolean insertReg(Connection conn, CatEstado estado) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			 
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_ESTADO"
					+ " (PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO)"
					+ " VALUES( ?, ?, ?, ?)");		
			ps.setString(1, estado.getPaisId());
			ps.setString(2, estado.getEstadoId());
			ps.setString(3, estado.getNombreEstado());
			ps.setString(4, estado.getCorto());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstadoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatEstado estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_ESTADO "+ 
				"SET PAIS_ID = ?, ESTADO_ID = ?, NOMBRE_ESTADO = ?, "+
				"CORTO = ?");
			ps.setString(1, estado.getPaisId());
			ps.setString(2, estado.getEstadoId());
			ps.setString(3, estado.getNombreEstado());
			ps.setString(4, estado.getCorto());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatEstadoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
public HashMap<String,CatEstado> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatEstado> map = new HashMap<String,CatEstado>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO, CORTO "+
				"FROM ENOC.CAT_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatEstado obj = new CatEstado();
				obj.mapeaReg(rs);
				llave = obj.getEstadoId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}