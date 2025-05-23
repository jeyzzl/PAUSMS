//Clase  para la tabla Materias_Insc
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CatCarreraUtil{
	
	public boolean insertReg(Connection conn, CatCarrera carrera) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{			 
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_CARRERA"
					+ " (FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL)"
					+ " VALUES( ?, ?, TO_NUMBER(?,'99') , ? , ?, ?, ?, ?)");		
			ps.setString(1, carrera.getFacultadId());
			ps.setString(2, carrera.getCarreraId());
			ps.setString(3, carrera.getNivelId());
			ps.setString(4, carrera.getTitulo());
			ps.setString(5, carrera.getNombreCarrera());
			ps.setString(6, carrera.getNombreCorto());
			ps.setString(7, carrera.getCcostoId());
			ps.setString(8, carrera.getCodigoPersonal());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatCarrera carrera ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_CARRERA "+ 
				"SET FACULTAD_ID = ?, NIVEL_ID = TO_NUMBER(?,'99'), TITULO = ?, "+
				"NOMBRE_CARRERA = ?, NOMBRE_CORTO = ?, CCOSTO_ID = ?, "+
				"CODIGO_PERSONAL = ? "+
				"WHERE CARRERA_ID = ?");
			ps.setString(1, carrera.getFacultadId());
			ps.setString(2, carrera.getNivelId());
			ps.setString(3, carrera.getTitulo());
			ps.setString(4, carrera.getNombreCarrera());
			ps.setString(5, carrera.getNombreCorto());
			ps.setString(6, carrera.getCcostoId());
			ps.setString(7, carrera.getCodigoPersonal());
			ps.setString(8, carrera.getCarreraId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String carreraId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_CARRERA "+ 
				"WHERE CARRERA_ID = ?");			
			ps.setString(1, carreraId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatCarrera mapeaRegId(Connection conn, String facultadId, String carreraId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatCarrera carrera		= new CatCarrera(); 
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID, "+
				"CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ? AND CARRERA_ID = ?");		 
			ps.setString(1, facultadId);
			ps.setString(2, carreraId);
			rs = ps.executeQuery();
			if (rs.next()){
				carrera.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public CatCarrera mapeaRegIdsinFac(Connection conn, String carreraId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatCarrera carrera		= new CatCarrera(); 
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA,"
					+ " NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL"
					+ " FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?");		 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next()){
				carrera.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|mapeaRegId|:"+ex);			
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public boolean existeReg(Connection conn, String carreraId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(CARRERA_ID)+1 MAXIMO FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreCarrera(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "null";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CARRERA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getNombreCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreCorto(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= " ";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CORTO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getFacultadId(Connection conn, String carreraId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String facultadId		= "";
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				facultadId = rs.getString("FACULTAD_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getFacultadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultadId;
	}
	
	public static boolean esCoordinador(Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO "+
					"FROM ENOC.CAT_CARRERA WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|esCoodinador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static String getCoordinador(Connection conn, String carreraId) throws SQLException{
		ResultSet rs				= null;
		PreparedStatement ps		= null;
		String coordinadorId		= "";
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				coordinadorId = rs.getString("CODIGO_PERSONAL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getFacultadId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return coordinadorId;
	}
	
	public static String getNivelId(Connection conn, String carreraId) throws SQLException{
		ResultSet rs				= null;
		PreparedStatement ps		= null;
		String nivel				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(NIVEL_ID,0) AS NIVEL FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = ?"); 
			ps.setString(1, carreraId);
			rs = ps.executeQuery();
			if (rs.next())
				nivel = rs.getString("NIVEL");
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nivel;
	}
	
	public static String getCarrerasCoordinador(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet rs				= null;
		PreparedStatement ps		= null;
		String carreras				= "";
		int row 					= 0;
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			while(rs.next()){
				row++;
				if (row==1){
					carreras += "'"+rs.getString("CARRERA_ID")+"'";
				}else{
					carreras += ",'"+rs.getString("CARRERA_ID")+"'";
				}				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getCarrerasCoordinador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carreras;
	}
	
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
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> getLista(Connection conn, String facultadId, String orden ) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera	= new ArrayList<CatCarrera>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CatCarrera carrera = new CatCarrera();
				carrera.mapeaReg(rs);
				lisCarrera.add(carrera);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}

	public ArrayList<aca.Mapa> getListaPlanesId(Connection conn, String facultadId, String orden ) throws SQLException{
		
		ArrayList<aca.Mapa> lisCarrera	= new ArrayList<aca.Mapa>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN WHERE CARRERA_ID IN(SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultadId+"')"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				aca.Mapa mapa = new aca.Mapa();
				mapa.setLlave(rs.getString("LLAVE"));
				mapa.setValor(rs.getString("VALOR"));
				
				lisCarrera.add(mapa);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getListaPlanesId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}
	
	public HashMap<String,CatCarrera> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,CatCarrera> map = new HashMap<String,CatCarrera>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, "+
				"NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL "+
				"FROM ENOC.CAT_CARRERA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CatCarrera obj = new CatCarrera();
				obj.mapeaReg(rs);
				llave = obj.getCarreraId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapEmpresa(Connection conn) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		try{
			comando = "SELECT CARRERA_ID, SUBSTR(COALESCE(TRIM(CCOSTO_ID),'1.01'),1,4) AS EMPRESA FROM ENOC.CAT_CARRERA WHERE CCOSTO_ID IS NOT NULL";
			
			rs = st.executeQuery(comando);
			while (rs.next()){			
				map.put(rs.getString("CARRERA_ID"), rs.getString("EMPRESA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|mapEmpresa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
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
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCarga|:"+ex);
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
					"FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+facultad_Id+"' "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> listCarreraConMentor(Connection conn, String periodoId, String fecha, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL"
					+ " FROM ENOC.CAT_CARRERA"
					+ " WHERE CARRERA_ID IN"
					+ " 	(SELECT CARRERA_ID FROM ENOC.MENT_CARRERA WHERE PERIODO_ID = '"+periodoId+"'"
					+ "		AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INICIO AND FECHA_FINAL)) "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
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
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
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
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	// Lista de carreras con permiso en una carga academica
	public ArrayList<CatCarrera> getListCargaCarrera(Connection conn,String facultad, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE FACULTAD_ID = '"+facultad+"' " +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"')" +
					"  " +orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCargaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}

	// Lista de carreras con materias en una carga
	public ArrayList<CatCarrera> getListCargaCarreraOficial(Connection conn,String facultad, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE FACULTAD_ID = '"+facultad+"' " +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"')" +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S') " +orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCargaCarreraOficial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	// Lista de carreras con materias en una carga
	public ArrayList<CatCarrera> getListCargaCarreraOficial(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT FACULTAD_ID, CARRERA_ID, NIVEL_ID, TITULO, NOMBRE_CARRERA, " +
					" NOMBRE_CORTO, CCOSTO_ID, CODIGO_PERSONAL " +
					" FROM ENOC.CAT_CARRERA " + 
					" WHERE  CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"')" +
					" AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S') " +orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|getListCargaCarreraOficial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<CatCarrera> carrerasPorCarga(Connection conn, String facultadId, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatCarrera> lisCarrera		= new ArrayList<CatCarrera>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_CARRERA WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"') AND FACULTAD_ID = '"+facultadId+"' " +orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatCarrera carreraU = new CatCarrera();
				carreraU.mapeaReg(rs);
				lisCarrera.add(carreraU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatCarreraUtil|carrerasPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarrera;
	}
	
	public ArrayList<String> getCarrerasAlumno(Connection conn, String matricula, String orden ) throws SQLException{
		
		ArrayList<String> lisCarrera	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE PLAN_ID = ALUM_PLAN.PLAN_ID) AS CARRERA " +
					"FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+matricula+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisCarrera.add(rs.getString("CARRERA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getCarrerasAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}
	
	public ArrayList<aca.Mapa> getPlanAlumno(Connection conn, String matricula ) throws SQLException{
		
		ArrayList<aca.Mapa> lisCarrera	= new ArrayList<aca.Mapa>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID AS LLAVE, CARRERA_SE AS VALOR FROM ENOC.MAPA_PLAN WHERE PLAN_ID IN (SELECT PLAN_ID FROM ENOC.ALUM_PLAN WHERE CODIGO_PERSONAL = '"+matricula+"')"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				aca.Mapa mapa = new aca.Mapa();
				mapa.setLlave(rs.getString("LLAVE"));
				mapa.setValor(rs.getString("VALOR"));
				lisCarrera.add(mapa);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CarreraUtil|getPlanAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCarrera;
	}
	
}