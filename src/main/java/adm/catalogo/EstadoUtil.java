// Clase Util para la tabla de Cat_Carrera
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class EstadoUtil{
		
	public ArrayList<CatEstado> getLista(Connection conn, String paisId, String orden ) throws SQLException{
		
		ArrayList<CatEstado> lisEstado	= new ArrayList<CatEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO "+
				"FROM ENOC.CAT_ESTADO WHERE PAIS_ID = TO_NUMBER('"+paisId+"','999') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEstado estado = new CatEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	
	public ArrayList<CatEstado> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<CatEstado> lisEstado	= new ArrayList<CatEstado>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT PAIS_ID, ESTADO_ID, NOMBRE_ESTADO "+
				"FROM ENOC.CAT_ESTADO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatEstado estado = new CatEstado();
				estado.mapeaReg(rs);
				lisEstado.add(estado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEstado;
	}
	public String getEstado(Connection Conn,String paisId, String estadoId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String nombre		= "No encontro";
		
		try{
			comando = "SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO where estado_id = "+estadoId+" and pais_id = "+paisId;
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.EstadoUtil|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}		
}