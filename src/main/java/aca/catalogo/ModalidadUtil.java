// Clase Util para la tabla de Cat_Area
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModalidadUtil{
	
	public boolean insertReg(Connection conn, CatModalidad modalidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.CAT_MODALIDAD(MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE ) "+
				"VALUES( TO_NUMBER(?,'99'), ?, ?, ? ) ");
			ps.setString(1, modalidad.getModalidadId());
			ps.setString(2, modalidad.getNombreModalidad());
			ps.setString(3, modalidad.getEnLinea());
			ps.setString(4, modalidad.getAdmisible());	
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatModalidad modalidad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_MODALIDAD "+ 
				"SET NOMBRE_MODALIDAD = ?," +
				" ENLINEA = ?," +
				" ADMISIBLE = ? " +
				"WHERE MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, modalidad.getNombreModalidad());
			ps.setString(2, modalidad.getEnLinea());
			ps.setString(3, modalidad.getAdmisible());
			ps.setString(4, modalidad.getModalidadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String modalidadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_MODALIDAD "+ 
				"WHERE MODALIDAD_ID = TO_NUMBER(?,'99')");
			ps.setString(1, modalidadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }	
		}
		return ok;
	}
	
	public CatModalidad mapeaRegId( Connection conn, String modalidadId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		CatModalidad modalidad = new CatModalidad();
		
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE "+
				"FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				modalidad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return modalidad;
	}
	
	public boolean existeReg(Connection conn, String modalidadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,modalidadId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
				
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(MODALIDAD_ID)+1 MAXIMO FROM ENOC.CAT_MODALIDAD"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean esLinea(Connection conn, String modalidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok				= true;
		
		try{
			ps = conn.prepareStatement("SELECT ENLINEA FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = rs.getString("ENLINEA").equals("S");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|esLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esExtension(Connection conn, String modalidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok				= true;
		
		try{
			ps = conn.prepareStatement("SELECT ENLINEA FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){
				ok = rs.getString("ENLINEA").equals("E");
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|esExtension|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String modalidadesEnLinea(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String modalidades 		= "";
		int row = 0;
		
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM ENOC.CAT_MODALIDAD WHERE ENLINEA = 'S' ORDER BY MODALIDAD_ID");			
			rs = ps.executeQuery();
			while (rs.next()){ row++;
				if (row>1){
					modalidades+= ","+rs.getString("MODALIDAD_ID");
				}else{
					modalidades+= rs.getString("MODALIDAD_ID");
				}
				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|modalidadesEnLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return modalidades;
	}
	
	public static String getEnLinea(Connection conn, String modalidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String enLinea 			= "N";		
		
		try{
			ps = conn.prepareStatement("SELECT ENLINEA FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = ?");
			ps.setString(1, modalidadId);
			rs = ps.executeQuery();
			if (rs.next()){			
				enLinea= rs.getString("ENLINEA");		
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getEnLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return enLinea;
	}
		
	public ArrayList<CatModalidad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 								= conn.createStatement();
		ResultSet rs 								= null;
		String comando								= "";
		
		try{
			comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}
	
	public ArrayList<CatModalidad> getListInscritos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = "SELECT A.MODALIDAD_ID AS MODALIDAD_ID," +
					" (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) AS NOMBRE_MODALIDAD," +
					" ENLINEA, ADMISIBLE" +
					" FROM ENOC.CAT_MODALIDAD A" + 
					" WHERE (SELECT COUNT(DISTINCT(B.CODIGO_PERSONAL)) FROM ENOC.INSCRITOS B WHERE B.MODALIDAD_ID = A.MODALIDAD_ID) > 0 "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}
	
	public static HashMap<String,CatModalidad> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatModalidad> mapModalidad = new HashMap<String,CatModalidad>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT MODALIDAD_ID, NOMBRE_MODALIDAD, ENLINEA, ADMISIBLE FROM ENOC.CAT_MODALIDAD "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				llave = modalidad.getModalidadId();
				mapModalidad.put(llave, modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapModalidad;
	}
	
	public ArrayList<CatModalidad> getListInscCargas(Connection conn, String cargas, String modalidades, String orden ) throws SQLException{
		
		ArrayList<CatModalidad> lisModalidad 		= new ArrayList<CatModalidad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		
		try{
			comando = " SELECT A.MODALIDAD_ID AS MODALIDAD_ID, A.NOMBRE_MODALIDAD,"
					+ " (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) AS ENLINEA,"
					+ " ADMISIBLE"
					+ " FROM ENOC.CAT_MODALIDAD A"
					+ " WHERE (SELECT COALESCE(COUNT(DISTINCT(B.CODIGO_PERSONAL)),0) FROM ENOC.INSCRITOS B WHERE B.CARGA_ID IN ("+cargas+") AND B.MODALIDAD_ID = A.MODALIDAD_ID) > 0"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatModalidad modalidad = new CatModalidad();
				modalidad.mapeaReg(rs);
				lisModalidad.add(modalidad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getListInscCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisModalidad;
	}	
	
	public static String getNombreModalidad(Connection conn, String modalidadId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String nombre			= "x";
		
		try{
			comando = "SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = '"+modalidadId+"'"; 
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE_MODALIDAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.ModalidadUtil|getNombreModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
}