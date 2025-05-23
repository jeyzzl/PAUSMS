package aca.por;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PorSeccionEmpUtil {
	
	public ArrayList<PorSeccionEmp> getListAll(Connection conn, String orden ) throws SQLException{
	
		ArrayList<PorSeccionEmp> lisSeccion	= new ArrayList<PorSeccionEmp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO FROM ENOC.POR_SECCION_EMP "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccionEmp seccion = new PorSeccionEmp();
				seccion.mapeaReg(rs);
				lisSeccion.add(seccion);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisSeccion;
	}
	
	public ArrayList<String> getListEmp(Connection conn, String porId ) throws SQLException{
		
		ArrayList<String> listEmp = new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = "SELECT DISTINCT (CODIGO_PERSONAL) FROM ENOC.POR_SECCION_EMP WHERE POR_ID ="+porId;
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				listEmp.add(rs.getString("CODIGO_PERSONAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|getListEmp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return listEmp;
	}

	
	public ArrayList<PorSeccionEmp> getListPortafolioEmpleado(Connection conn, String porId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<PorSeccionEmp> lisSeccion	= new ArrayList<PorSeccionEmp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO"
					+ " FROM ENOC.POR_SECCION_EMP"
					+ " WHERE POR_ID = '"+porId+"'"					
					+ " AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccionEmp seccion = new PorSeccionEmp();
				seccion.mapeaReg(rs);
				lisSeccion.add(seccion);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|getListPortafolioEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisSeccion;
	}
	
	public HashMap<String, PorSeccionEmp> mapSeccionEmp(Connection conn, String portafolio, String empleadoId) throws SQLException{
		
		HashMap<String, PorSeccionEmp> mapa		= new HashMap<String, PorSeccionEmp>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		try{
			comando = " SELECT * FROM ENOC.POR_SECCION_EMP WHERE POR_ID = '"+portafolio+"' AND CODIGO_PERSONAL = '"+empleadoId+"'"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				PorSeccionEmp obj = new PorSeccionEmp();
				obj.mapeaReg(rs);
				llave = obj.getSeccionId();
				mapa.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|mapSeccionEmp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}	
	
	public ArrayList<PorSeccionEmp> getListImagen(Connection conn, String porId, String seccion, String orden ) throws SQLException{
		
		ArrayList<PorSeccionEmp> lisSeccion	= new ArrayList<PorSeccionEmp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO FROM ENOC.POR_SECCION_EMP "
					+ " WHERE POR_ID='"+porId+"' AND CODIGO_PERSONAL = '"+"'AND SECCION_ID='"+seccion+"' "
					+ "AND SECCION_ID IN(SELECT SECCION_ID FROM ENOC.POR_SECCION WHERE TIPO= 'I')";

			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccionEmp obj = new PorSeccionEmp();
				obj.mapeaReg(rs);
				lisSeccion.add(obj);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|getListImagen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisSeccion;
	}

	
	public ArrayList<PorSeccionEmp> getListImagen(Connection conn, String porId, String seccion, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<PorSeccionEmp> lisSeccion	= new ArrayList<PorSeccionEmp>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	 
		try{
			comando = " SELECT POR_ID, SECCION_ID, CODIGO_PERSONAL, TEXTO_CORTO, TEXTO_LARGO, COMENTARIO, FOLIO FROM ENOC.POR_SECCION_EMP "
					+ " WHERE POR_ID='"+porId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"'AND SECCION_ID='"+seccion+"' "
					+ "AND SECCION_ID IN(SELECT SECCION_ID FROM ENOC.POR_SECCION WHERE TIPO= 'I')";

			rs = st.executeQuery(comando);
			while (rs.next()){
			
				PorSeccionEmp obj = new PorSeccionEmp();
				obj.mapeaReg(rs);
				lisSeccion.add(obj);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|getListImagen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisSeccion;
	}
	
	public ArrayList<String> lisEmpleados(Connection conn, String porId, String orden ) throws SQLException{
		
		ArrayList<String> lista	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
	 
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.POR_SECCION_EMP WHERE POR_ID = "+porId+" "+orden;

			rs = st.executeQuery(comando);
			while (rs.next()){
				lista.add(rs.getString("CODIGO"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccionEmpUtil|lisEmpleados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lista;
	}
}