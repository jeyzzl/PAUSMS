//Clase  para la tabla Materias_Insc
package adm.catalogo;

import java.sql.*;
import java.util.ArrayList;

public class CatCarreraUtil{
	
	public ArrayList<CatCarrera> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					"NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					"FROM ENOC.CAT_CARRERA "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					"NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					"FROM ENOC.CAT_CARRERA" +
					" WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO" +
										 " WHERE CARGA_ID = '"+cargaId+"') "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListCarrera(Connection conn, String facultad_Id, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					"NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultad_Id+"'"+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListAutorizadas(Connection conn, String codigoPersonal, String orden) throws SQLException{
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					"NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					"FROM ENOC.CAT_CARRERA" +
					" WHERE (SELECT ACCESOS FROM ENOC.ACCESO" +
							" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') LIKE '%'||CARRERA_ID||'%' "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListConReprobados(Connection conn, String cargaId, String orden) throws SQLException{
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					"NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					"FROM ENOC.CAT_CARRERA" +
					" WHERE CARRERA_ID IN (SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID))" +
										" FROM ENOC.KRDX_CURSO_ACT" +
										" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
										" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')) "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListEnLinea(Connection conn, String modalidadId, String orden) throws SQLException{
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA," +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL" +
					" FROM ENOC.CAT_CARRERA" +
					" WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE ENLINEA = 'S' AND ESTADO = 'A')" +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM SALOMON.ADM_MODCARRERA WHERE MODALIDAD_ID ='"+modalidadId+"') "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListEnLinea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getListPlanes(Connection conn, String orden) throws SQLException{
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA," +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL" +
					" FROM ENOC.CAT_CARRERA" +
					" WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE ESTADO = 'A') "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - adm.catalogo.CatCarreraUtil|getListPlanes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
}