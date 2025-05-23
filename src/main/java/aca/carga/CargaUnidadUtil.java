package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CargaUnidadUtil {
	
	public boolean insertReg(Connection conn, CargaUnidad unidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_UNIDAD(CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE,ORDEN ) "+
				" VALUES( ?, ?, ?, TO_NUMBER(?,'99') ) ");
			
			ps.setString(1, unidad.getCursoCargaId());
			ps.setString(2, unidad.getUnidadId());
			ps.setString(3, unidad.getUnidadNombre());
			ps.setString(4, unidad.getOrden());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaUnidad unidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_UNIDAD "+ 
				" SET UNIDAD_NOMBRE = ?, " +
				" ORDEN = TO_NUMBER(?,'99')" +
				" WHERE UNIDAD_ID = ? " +
				" AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, unidad.getUnidadNombre());
			ps.setString(2, unidad.getOrden());
			ps.setString(3, unidad.getUnidadId());
			ps.setString(4, unidad.getCursoCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String unidadId, String cursoCargaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_UNIDAD"+ 
				" WHERE UNIDAD_ID = ?" +
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, unidadId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaUnidad mapeaRegId( Connection conn, String cursoCargaId, String unidadId) throws SQLException{
		
		CargaUnidad unidad = new CargaUnidad();
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN "+
			"FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = ? AND UNIDAD_ID = ?"); 
		ps.setString(1,cursoCargaId);
		ps.setString(2,unidadId);
		
		rs = ps.executeQuery();
		if (rs.next()){
			unidad.mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }
		
		return unidad;
	}
	
	public boolean existeReg(Connection conn, String unidadId, String cursoCargaId) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		boolean 		ok 	= false;
		
		try{			
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD WHERE UNIDAD_ID = ?  AND CURSO_CARGA_ID = ? "); 
			ps.setString(1,unidadId);
			ps.setString(2,cursoCargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		String maximo 			= "01";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT" +
					" COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(UNIDAD_ID,'99'))+1,'00')), '01') AS MAXIMO " +
					" FROM ENOC.CARGA_UNIDAD " + 
					" WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
		
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static int numUnidades(Connection conn, String cursoCargaId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int numUnidades		= 0;
		
		try{
			comando = " SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMUNIDADES FROM ENOC.CARGA_UNIDAD WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				numUnidades = rs.getInt("NUMUNIDADES");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|numUnidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numUnidades;
	}
	
	public static boolean tieneTemas(Connection conn, String cursoCargaId, String unidadId ) throws SQLException{
		
		boolean ok           = false;
		Statement st 		 = conn.createStatement();
		ResultSet rs 		 = null;
		String comando	     = "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_UNIDAD_TEMA WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + 
					  " AND SUBSTR(TEMA_ID,1,2) = '"+unidadId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|getNombreReligion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public static boolean tieneCompetencias(Connection conn, String cursoCargaId, String unidadId ) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_UNIDAD_COMP" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND UNIDAD_ID = ?" );
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, unidadId);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|tieneCompetencias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getUnidadNombre(Connection conn, String cursoCargaId, String unidadId ) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String nombre 			= "";
		
		try{
			ps = conn.prepareStatement("SELECT UNIDAD_NOMBRE FROM ENOC.CARGA_UNIDAD" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND UNIDAD_ID = ?" );
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, unidadId);
		
			rs= ps.executeQuery();		
			if(rs.next()){
				nombre = rs.getString("UNIDAD_NOMBRE");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidad|getUnidadNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public ArrayList<CargaUnidad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaUnidad> lisUnidad = new ArrayList<CargaUnidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN FROM ENOC.CARGA_UNIDAD "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidad uni = new CargaUnidad();
				uni.mapeaReg(rs);
				lisUnidad.add(uni);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargUnidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUnidad;
	}
	
	public ArrayList<CargaUnidad> getListUnidad(Connection conn,String cursoCargaId, String orden ) throws SQLException{
			
		ArrayList<CargaUnidad> lisUnidad = new ArrayList<CargaUnidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, UNIDAD_ID, UNIDAD_NOMBRE, ORDEN FROM ENOC.CARGA_UNIDAD " + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' " + orden;
			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaUnidad uni = new CargaUnidad();
				uni.mapeaReg(rs);
				lisUnidad.add(uni);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUnidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisUnidad;
	}
}