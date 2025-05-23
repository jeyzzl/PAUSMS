package aca.carga;

import java.sql.*;
import java.util.ArrayList;

public class CargaUnidadTemaUtil {
	
	
	public boolean insertReg(Connection conn, CargaUnidadTema cargaUnidadTema ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD_TEMA(CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, FECHA, ORDEN )"
					+ " VALUES( ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99') )");
			ps.setString(1, cargaUnidadTema.getCursoCargaId());
			ps.setString(2, cargaUnidadTema.getTemaId());
			ps.setString(3, cargaUnidadTema.getTemaNombre());
			ps.setString(4, cargaUnidadTema.getFecha());
			ps.setString(5, cargaUnidadTema.getOrden());

			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn,CargaUnidadTema cargaUnidadTema ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD_TEMA "+ 
				" SET TEMA_NOMBRE = ?, " +
				" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
				" ORDEN = TO_NUMBER(?,'99')" +
				" WHERE TEMA_ID = ? " +
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, cargaUnidadTema.getTemaNombre());
			ps.setString(2, cargaUnidadTema.getFecha());
			ps.setString(3, cargaUnidadTema.getOrden());
			ps.setString(4, cargaUnidadTema.getTemaId());
			ps.setString(5, cargaUnidadTema.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String temaId, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD_TEMA "+ 
				" WHERE TEMA_ID = ?  " +
				" AND CURSO_CARGA_ID = ? ");
			ps.setString(1, temaId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaUnidadTema mapeaRegId( Connection conn, String cursoCargaId, String temaId) throws SQLException{
		CargaUnidadTema cargaUnidadTema = new CargaUnidadTema();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, " +
					" ORDEN FROM ENOC.CARGA_UNIDAD_TEMA WHERE CURSO_CARGA_ID = ? AND TEMA_ID = ?");		 
			ps.setString(1,cursoCargaId);
			ps.setString(2,temaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				cargaUnidadTema.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cargaUnidadTema;
	}
	
	public boolean existeReg(Connection conn, String temaId, String cursoCargaId) throws SQLException{
		boolean 		  ok 	= false;
		ResultSet 		  rs	= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_TEMA WHERE TEMA_ID = ?  AND CURSO_CARGA_ID = ? "); 
			ps.setString(1,temaId);
			ps.setString(2,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreTema(Connection conn, String cursoCargaId, String temaId) throws SQLException{
	
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String nombre			= "";				
		
		try{
			ps = conn.prepareStatement(" SELECT TEMA_NOMBRE FROM ENOC.CARGA_UNIDAD_TEMA " + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND TEMA_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, temaId);
		
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("TEMA_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|getNombreTema|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
   public String getMaximo(Connection conn, String cursoCargaId, String unidadId ) throws SQLException{
		
	    String maximo 			= "01";
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	        = "";
		
		try{
			comando = " SELECT COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(TEMA_ID,3,2),'99'))+1,'00')),'01') AS MAXIMO" +
			" FROM ENOC.CARGA_UNIDAD_TEMA " + 
			" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
		    " AND SUBSTR(TEMA_ID,1,2)= '"+unidadId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				maximo = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
   }
   
   public static boolean tieneActividades(Connection conn, String cursoCargaId, String temaId ) throws SQLException{
		
		boolean ok           = false;
		Statement st 		 = conn.createStatement();
		ResultSet rs 		 = null;
		String comando	     = "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_UNIDAD_ACTIVIDAD WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + 
					  " AND SUBSTR(ACTIVIDAD_ID,1,4) = '"+temaId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|tieneActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
   
   	public ArrayList<CargaUnidadTema> getListAll(Connection conn, String orden ) throws SQLException{
   
   		ArrayList<CargaUnidadTema> lisTema = new ArrayList<CargaUnidadTema>();
   		Statement st 			= conn.createStatement();
   		ResultSet rs 			= null;
   		String comando	        = "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, FECHA, ORDEN FROM ENOC.CARGA_UNIDAD_TEMA "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadTema tema = new CargaUnidadTema();
				tema.mapeaReg(rs);
				lisTema.add(tema);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadTemaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTema;
	}


	public ArrayList<CargaUnidadTema> getListTema(Connection conn,String cursoCargaId, String orden ) throws SQLException{
	
		ArrayList<CargaUnidadTema> lisTema = new ArrayList<CargaUnidadTema>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, TEMA_ID, TEMA_NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, ORDEN FROM ENOC.CARGA_UNIDAD_TEMA " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidadTema tema = new CargaUnidadTema();
				tema.mapeaReg(rs);
				lisTema.add(tema);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadCompUtil|getListCompetencias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisTema;
	}
	
}