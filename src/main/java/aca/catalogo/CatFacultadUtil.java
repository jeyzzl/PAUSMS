//Clase  para la tabla Materias_Insc
package aca.catalogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import aca.catalogo.CatFacultad;

public class CatFacultadUtil{
	
	public boolean insertReg(Connection conn, CatFacultad facultad) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_FACULTAD"
					+ " (AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE)"
					+ " VALUES( ?, ?, ? , ? , ?, ?, ?)");			
			ps.setString(1, facultad.getAreaId());
			ps.setString(2, facultad.getFacultadId());
			ps.setString(3, facultad.getTitulo());
			ps.setString(4, facultad.getNombreFacultad());
			ps.setString(5, facultad.getCodigoPersonal());
			ps.setString(6, facultad.getNombreCorto());
			ps.setString(7, facultad.getInvReferente());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CatFacultad facultad ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_FACULTAD"
					+ " SET AREA_ID = ?,"
					+ " TITULO = ?,"
					+ " NOMBRE_FACULTAD = ?,"
					+ " CODIGO_PERSONAL = ?,"
					+ " NOMBRE_CORTO = ?,"
					+ " INV_REFERENTE = ? "
					+ " WHERE FACULTAD_ID = ?");
			ps.setString(1, facultad.getAreaId());
			ps.setString(2, facultad.getTitulo());
			ps.setString(3, facultad.getNombreFacultad());
			ps.setString(4, facultad.getCodigoPersonal());
			ps.setString(5, facultad.getNombreCorto());
			ps.setString(6, facultad.getInvReferente());
			ps.setString(7, facultad.getFacultadId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String facultadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?");
			ps.setString(1, facultadId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CatFacultad mapeaRegId( Connection conn, String facultadId, String areaId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatFacultad facultad 	= new CatFacultad();
		
		try{
			ps = conn.prepareStatement("SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
					+ " FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ? AND AREA_ID = ?");		 
			ps.setString(1, facultadId);
			ps.setString(2, areaId);
			rs = ps.executeQuery();
			if (rs.next()){
				facultad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return facultad;
	}
	
	public CatFacultad mapeaRegId( Connection conn, String facultadId) throws SQLException{
		
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		CatFacultad facultad 	= new CatFacultad();
		try{
			ps = conn.prepareStatement("SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE "+
				"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?");		 
			ps.setString(1, facultadId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				facultad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultad;		
	}
	
	public boolean existeReg(Connection conn, String facultadId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?");	 
			ps.setString(1, facultadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String areaId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FACULTAD_ID)+1 MAXIMO FROM ENOC.CAT_FACULTAD WHERE AREA_ID = ?"); 
			ps.setString(1, areaId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombreFacultad(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_FACULTAD AS NOMBRE "+
					"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getNombreFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getNombreCorto(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO "+
					"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_CORTO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getNombreCorto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static boolean esDirector(Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CORTO "+
					"FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|esDirector|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public static String Director(Connection conn, String FacultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String director 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL "+
					"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?"); 
			ps.setString(1, FacultadId);
			rs = ps.executeQuery();
			if (rs.next())
				director = rs.getString("CODIGO_PERSONAL");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|esDirector|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return director;
	}
	
	public static String getNombreHorarioId(Connection conn, String horarioId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_FACULTAD FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN (SELECT FACULTAD_ID FROM ENOC.CAT_HORARIO WHERE HORARIO_ID = '"+horarioId+"')"); 
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE_FACULTAD");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getNombreHorarioId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getFacultadInvReferente(Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String facultades		= "";
		int row = 0;
		try{ 
			ps = conn.prepareStatement("SELECT FACULTAD_ID FROM ENOC.CAT_FACULTAD WHERE INV_REFERENTE = ? OR CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, codigoPersonal);
			rs = ps.executeQuery();
			while (rs.next()){
				if (row>0) facultades = facultades + ",";
				facultades = facultades + "'"+rs.getString("FACULTAD_ID")+"'";
				row++;
			}
			if (row==0) facultades = "'X'";
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getFacultadInvReferente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultades;
	}
	
	public static String getFacultad(Connection conn, String codigoPersonal ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String codigo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID "+
					"FROM ENOC.CAT_FACULTAD WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				codigo = rs.getString("FACULTAD_ID");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|esDirector|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return codigo;
	}
	
	public static String getAreaId(Connection conn, String facultadId ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String codigo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT AREA_ID "+
					"FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID = ?"); 
			ps.setString(1, facultadId);
			rs = ps.executeQuery();
			if (rs.next())
				codigo = rs.getString("AREA_ID");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|esDirector|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return codigo;
	}


	
	public ArrayList<CatFacultad> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CatFacultad> lisFac	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD "+orden;		 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				CatFacultad facultadU = new CatFacultad();
				facultadU.mapeaReg(rs);
				lisFac.add(facultadU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFac;
	}
	
	// Lista de Facultades con permiso en una carga academica
	public ArrayList<CatFacultad> getListCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatFacultad> lisFac	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN " +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA " +
					"	WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"') " +
						") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				CatFacultad facultadU = new CatFacultad();
				facultadU.mapeaReg(rs);
				lisFac.add(facultadU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFac;
	}
	
	public ArrayList<CatFacultad> listFacultadesConCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatFacultad> lisFac	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			// Busca las facultades con materias en una carga
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE"
					+ " FROM ENOC.CAT_FACULTAD"
					+ " WHERE FACULTAD_ID IN"
					+ " 	(SELECT ENOC.FACULTAD(CARRERA_ID) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				CatFacultad facultadU = new CatFacultad();
				facultadU.mapeaReg(rs);
				lisFac.add(facultadU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getListConCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFac;
	}
	
	
	
	// Lista de Facultades con permiso en una carga academica con carreras oficiales ante la secretaria
	public ArrayList<CatFacultad> getListCargaOficial(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<CatFacultad> lisFac	= new ArrayList<CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			// Busca las facultades que tengan carreras autorizadas en la cargaId de parámetro y
			// que tengan carreras oficiales ante la secretaría de educación.
			comando = "SELECT AREA_ID, FACULTAD_ID, TITULO, NOMBRE_FACULTAD, CODIGO_PERSONAL, NOMBRE_CORTO, INV_REFERENTE" +
					" FROM ENOC.CAT_FACULTAD" + 
					" WHERE FACULTAD_ID IN " +
					"	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA " +
					"	WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = '"+cargaId+"') " +
					"	AND CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.MAPA_PLAN WHERE OFICIAL = 'S')" +
						") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				CatFacultad facultadU = new CatFacultad();
				facultadU.mapeaReg(rs);
				lisFac.add(facultadU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getListCargaOficial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFac;
	}
	
	public HashMap<String, CatFacultad> getMapFacultad(Connection conn, String orden) throws SQLException{
		
		HashMap<String, CatFacultad> lisFac	= new HashMap<String, CatFacultad>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CAT_FACULTAD " + orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatFacultad facultadU = new CatFacultad();
				facultadU.mapeaReg(rs);
				lisFac.put(facultadU.getFacultadId(), facultadU);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatFacultadUtil|getMapFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFac;
	}
}