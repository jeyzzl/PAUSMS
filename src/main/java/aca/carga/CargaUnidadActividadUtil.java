package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaUnidadActividadUtil {
	
	
	public boolean insertReg(Connection conn, CargaUnidadActividad actividad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD_ACTIVIDAD(CURSO_CARGA_ID, ACTIVIDAD_ID, " + 
			    " ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN)" +
				" VALUES( ?, ?, ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'99'))");
			
			ps.setString(1, actividad.getCursoCargaId()); 
			ps.setString(2, actividad.getActividadId());
			ps.setString(3, actividad.getActividadNombre());
			ps.setString(4, actividad.getComentario());
			ps.setString(5, actividad.getValor());
			ps.setString(6, actividad.getOrden());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaUnidadActividad actividad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD_ACTIVIDAD "+ 
				" SET ACTIVIDAD_NOMBRE = ?, " +
				" COMENTARIO = ?," +
				" VALOR = TO_NUMBER(?,'999.99'), " +
				" ORDEN = TO_NUMBER(?,'99')" +				
				" WHERE CURSO_CARGA_ID = ? " +
				" AND ACTIVIDAD_ID = ? ");
			
			ps.setString(1, actividad.getActividadNombre());
			ps.setString(2, actividad.getComentario());
			ps.setString(3, actividad.getValor());
			ps.setString(4, actividad.getOrden());
			ps.setString(5, actividad.getCursoCargaId());
			ps.setString(6, actividad.getActividadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String actividadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD_ACTIVIDAD "+ 
				" WHERE CURSO_CARGA_ID = ?" +
				" AND ACTIVIDAD_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, actividadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaUnidadActividad mapeaRegId( Connection conn, String cursoCargaId, String actividadId) throws SQLException{
		
		CargaUnidadActividad actividad = new CargaUnidadActividad();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, " +
					" ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? AND ACTIVIDAD_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, actividadId);
			rs = ps.executeQuery();
			if (rs.next()){
				actividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return actividad;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String actividadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?  AND ACTIVIDAd_ID = ?");
			ps.setString(1,cursoCargaId);
			ps.setString(2,actividadId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId ) throws SQLException{
		String maximo 			= "01";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(" SELECT COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(ACTIVIDAD_ID,4,6),'99'))+1,'00')),'01') AS MAXIMO" +
					" FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
		
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean tieneCriterios(Connection conn, String cursoCargaId, String actividadId ) throws SQLException{
			
		Statement st 		 = conn.createStatement();
		boolean ok           = false;
			
		ResultSet rs 		 = null;
		String comando	     = "";
			
		try{
			comando = " SELECT * FROM ENOC.CARGA_UNIDAD_CRITERIO WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + 
					  " AND SUBSTR(CRITERIO_ID,1,6) = '"+actividadId+"' ";				
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadAtividades|tieneCriterios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
			
		return ok;
	}
	
	public static int getNumActividad(Connection conn, String cursoCargaId) throws SQLException{
		int numActividades		= 0;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(ACTIVIDAD_ID) AS ACTIVIDAD FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?");
			
			ps.setString(1, cursoCargaId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				numActividades = rs.getInt("ACTIVIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|getSumActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numActividades;
	}
	
	public static String getNombreActividad(Connection conn, String cursoCargaId, String actividadId) throws SQLException{
		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_NOMBRE FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ACTIVIDAD_ID = ?");
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, actividadId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				nombre = rs.getString("ACTIVIDAD_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividad|getNombreActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CargaUnidadActividad> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<CargaUnidadActividad> lisActividad = new ArrayList<CargaUnidadActividad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
			
		try{
			comando = "SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, COMENTARIO, VALOR,ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){					
				CargaUnidadActividad act = new CargaUnidadActividad();
				act.mapeaReg(rs);
				lisActividad.add(act);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.UnidadActividadesUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisActividad;
	}
	
  
	public ArrayList<CargaUnidadActividad> getListActividades(Connection conn,String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaUnidadActividad> lisAct = new ArrayList<CargaUnidadActividad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, ACTIVIDAD_ID, ACTIVIDAD_NOMBRE, COMENTARIO, VALOR, ORDEN FROM ENOC.CARGA_UNIDAD_ACTIVIDAD " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadActividad tema = new CargaUnidadActividad();
				tema.mapeaReg(rs);
				lisAct.add(tema);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividadUtil|getListActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAct;
	}
	
	public static ArrayList<String> getDistintasActividades(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<String> lisAct = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(SUBSTR(ACTIVIDAD_ID,5,2)) AS ACTIVIDAD FROM ENOC.CARGA_UNIDAD_ACTIVIDAD" + 
				" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				String actividad = rs.getString("ACTIVIDAD");
				lisAct.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividadUtil|getDistintasActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAct;
	}
	
	public static double sumaValorActividad(Connection conn, String cursoCargaId, String actividadId ) throws SQLException{
		
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		double suma		= 0;
		
		try{
			comando = "SELECT SUM(VALOR) AS SUMA FROM ENOC.CARGA_UNIDAD_ACTIVIDAD" + 
				" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " +
				" AND SUBSTR(ACTIVIDAD_ID,5,2) = '"+actividadId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				suma = rs.getDouble("SUMA");				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadActividadUtil|sumaValorActividad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return suma;
	}
	
}