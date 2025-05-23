// Clase para la tabla de Cursos Actuales
package aca.kardex;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ActualUtil{
		
	public ArrayList<KrdxCursoAct> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				"NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
				"NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"COMENTARIO, CORRECCION "+
				"FROM ENOC.KRDX_CURSO_ACT "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListAlumno(Connection conn, String codigo_id, String carga_id, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
				" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				" COMENTARIO, CORRECCION "+
				" FROM ENOC.KRDX_CURSO_ACT " +
				" WHERE CODIGO_PERSONAL = '"+codigo_id+"'" +
				" AND SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga_id+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual		= new ArrayList<KrdxCursoAct>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
					"NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
					"NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					"TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
					"COMENTARIO, CORRECCION "+
					"FROM ENOC.KRDX_CURSO_ACT "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+ 
					"AND ALUM_INSCRITO_CARGA(CODIGO_PERSONAL,SUBSTR(CURSO_CARGA_ID,1,6)) IN ('I','3') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}	
	
	public ArrayList<KrdxCursoAct> getListCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
					"NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
					"NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					"TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
					"COMENTARIO, CORRECCION "+
					"FROM ENOC.KRDX_CURSO_ACT "+ 
					"WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' "+ 
					"AND ALUM_INSCRITO_CARGA(CODIGO_PERSONAL,SUBSTR(CURSO_CARGA_ID,1,6)) IN ('I','3') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListCarga(Connection conn, String cargaId, String tipo, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"
					+ " NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
					+ " NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " COMENTARIO, CORRECCION"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ("+tipo+") "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListCurso(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
					"NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
					"NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
					"TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
					"COMENTARIO, CORRECCION "+
					"FROM ENOC.KRDX_CURSO_ACT "+ 
					"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ 
					"AND ENOC.ALUM_INSCRITO_CARGA(CODIGO_PERSONAL,SUBSTR(CURSO_CARGA_ID,1,6)) IN('I','3')" +
					" AND TIPOCAL_ID != 'M' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListCarreraCarga(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = '"+carreraId+"'" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListDiferidas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE TIPOCAL_ID IN('5','6') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListDiferidasCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%'" +
					" AND TIPOCAL_ID IN('5','6') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> getListDiferida(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%'" +
					" AND TIPOCAL_ID = '6' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<KrdxCursoAct> listExtrasPorCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"
					+ " NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA,"
					+ " NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"
					+ " TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO,"
					+ " COMENTARIO, CORRECCION"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"')"
					+ " AND NOTA_EXTRA IS NOT NULL"
					+ " AND F_EXTRA IS NOT NULL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public boolean existeAlumnoGrupo(Connection conn, String cursoCargaId ) throws SQLException{
		
		boolean ok 		= false;
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			
			rs = st.executeQuery(comando);
			if (rs.next())
				ok = true;
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|existeAlumnoGrupo|:"+ex);
		}finally{	
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public boolean existeAlumnoMateria(Connection conn, String cursoCargaId, String Curso_id ) throws SQLException{
	
		boolean ok 		= false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
	
		try{
			comando = "SELECT * FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+
				"AND CURSO_ID = '"+Curso_id+"'";
		
			rs = st.executeQuery(comando);
			if (rs.next())
				ok = true;
			else
				ok = false;			
		
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|existeAlumnoMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return ok;
	}
	
	public int getTipocalAlum(Connection Conn, String cursoCargaId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int tipo			= 0;
		
		try{
			comando = "SELECT  COUNT(*) AS TOTALTIPO " +
			"FROM ENOC.KRDX_CURSO_ACT " + 
			"WHERE CURSO_CARGA_ID ='"+cursoCargaId+"' " +
			"AND TIPOCAL_ID != 'M' "; 
			
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				tipo = rs.getInt("TOTALTIPO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getTipoAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public int getTipocalId(Connection Conn, String cursoCargaId, String tipoId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int tipo			= 0;
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS TOTALTIPO " +
			"FROM ENOC.KRDX_CURSO_ACT " + 
			"WHERE CURSO_CARGA_ID ='"+cursoCargaId+"' " + 
			"AND TIPOCAL_ID = '"+tipoId+"' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				tipo = rs.getInt("TOTALTIPO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getTipocalId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static int numAlumMatTipo(Connection Conn, String cursoCargaId, String tipoId) throws SQLException{
		
		Statement st 		= Conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		int tipo			= 0;
		
		try{
			comando = "SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTALTIPO" +
			" FROM ENOC.KRDX_CURSO_ACT" + 
			" WHERE CURSO_CARGA_ID ='"+cursoCargaId+"'" + 
			" AND TIPOCAL_ID IN("+tipoId+")";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				tipo = rs.getInt("TOTALTIPO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numAlumMatTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static int numAlumnos(Connection conn, String cursoCargaId, String tipoCal ) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numAlum				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMERO" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +					
					" AND TIPOCAL_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, tipoCal);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getInt("NUMERO");	
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlum;
	}
	
	public static int TotalAlumxMateria(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numAlum				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMERO" +
					" FROM ENOC.KRDX_CURSO_ACT " + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND TIPOCAL_ID != 'M'  ");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getInt("NUMERO");	
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|TotalAlumxMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlum;
	}
	
	public static int TotalAlumPen(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numAlum				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS PENDIENTE" +
					" FROM ENOC.KRDX_CURSO_ACT " + 
					" WHERE CURSO_CARGA_ID = ? " +
					" AND TIPOCAL_ID NOT IN('1','2','3','4','I') ");
			ps.setString(1, cursoCargaId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				numAlum = rs.getInt("PENDIENTE");	
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|TotalAlumPen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAlum;
	}
	
	public static int PromedioxMateria(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int promedio			= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(ENOC.ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID)),0) AS PROMEDIO  " +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ? " +
					" AND TIPOCAL_ID NOT IN ('M','3') ");
			ps.setString(1, cursoCargaId);
			
			
			rs = ps.executeQuery();
			if (rs.next())
				promedio = rs.getInt("PROMEDIO");	
			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|PromedioxMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return promedio;
	}
	
	
	public static int numAlumMatTipo(Connection conn, String codigoPersonal, String cargaId, String tipoCal ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";		
		int numAlum				= 0;
		try{
			comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMERO" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
					" AND SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +	
					" AND TIPOCAL_ID IN("+tipoCal+")";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				numAlum = rs.getInt("NUMERO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numAlumMatTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numAlum;
	}
	
	public static int numEmpMatTipo(Connection conn, String codigoPersonal, String cargaId, String tipoCal ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";		
		int numAlum			= 0;
		try{
			comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMERO" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND CODIGO_PERSONAL='"+codigoPersonal+"')"+
					" AND TIPOCAL_ID IN("+tipoCal+")";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				numAlum = rs.getInt("NUMERO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numEmpMatTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numAlum;
	}
	
	public HashMap<String, String> getNumAlumMatTipo(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL, COUNT(CURSO_ID) AS TOTAL"+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
					" AND TIPOCAL_ID IN ("+tipoCal+")"+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numAlumMatTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> getAlumMatPorTipos(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CURSO_CARGA_ID, COALESCE(COUNT(*),0) AS TOTAL FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN("+cargaId+") AND TIPOCAL_ID IN ("+tipoCal+") "
					+ " GROUP BY CURSO_CARGA_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getAlumMatPorTipos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapNumMatPorAlumnoyTipo(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(CURSO_ID) AS TOTAL"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ("+tipoCal+")"					
					+ " GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapNumMatPorAlumnoyTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapNumMatEvalPorAlumnoyTipo(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(CURSO_ID) AS TOTAL"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND TIPOCAL_ID IN ("+tipoCal+")"
					+ " AND CURSO_CARGA_ID IN (SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.KRDX_ALUMNO_EVAL WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"')"
					+ " GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapNumMatEvalPorAlumnoyTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> getNumAlumMatTipoModalidad(Connection conn, String cargaId, String modalidadId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = "SELECT  SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL, COUNT(CURSO_ID) AS TOTAL "+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
					" AND TIPOCAL_ID IN ("+tipoCal+") AND ENOC.ALUM_MOD_CARGA(CODIGO_PERSONAL, SUBSTR(CURSO_CARGA_ID,1,6)) IN ("+modalidadId+") "+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getNumAlumMatTipoModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public static int numAlumCredMat(Connection conn, String codigoPersonal, String cargaId, String tipoCal ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";		
		int numCred			= 0;
		try{
			comando = "SELECT COALESCE(SUM(ENOC.CREDITOS(CURSO_ID)),0) AS CREDITOS" +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"+
					" AND SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +	
					" AND TIPOCAL_ID IN("+tipoCal+")";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				numCred = rs.getInt("CREDITOS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|numAlumCredMat|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numCred;
	}
	
	public HashMap<String, String> getNumAlumCreditos(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = "SELECT  SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL," +
					" COALESCE(SUM((SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID )),0) AS CREDITOS"+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
					" AND TIPOCAL_ID IN ("+tipoCal+")"+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CREDITOS"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getNumAlumCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
public HashMap<String, String> getNumAlumCreditosModalidad(Connection conn, String cargaId, String modalidad, String tipoCal) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		try{
			comando = "SELECT  SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL," +
					" COALESCE(SUM((SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID )),0) AS CREDITOS, ENOC.ALUMNO_MODALIDAD_ID(CODIGO_PERSONAL)"+
					" FROM ENOC.KRDX_CURSO_ACT"+ 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"+
					" AND TIPOCAL_ID IN ("+tipoCal+") AND ENOC.ALUM_MOD_CARGA(CODIGO_PERSONAL, SUBSTR(CURSO_CARGA_ID,1,6)) IN ("+modalidad+")"+
					" GROUP BY SUBSTR(CURSO_CARGA_ID,1,6), CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CREDITOS"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getNumAlumCreditos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public ArrayList<KrdxCursoAct> getListDiferida(Connection conn, String orden ) throws SQLException{
		
		ArrayList<KrdxCursoAct> lisActual	= new ArrayList<KrdxCursoAct>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID,"+
					" NOTA, TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA," +
					" NOTA_EXTRA, TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
					" TIPO, TITULO, TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO," +
					" COMENTARIO, CORRECCION" +
					" FROM ENOC.KRDX_CURSO_ACT KCA"+ 
					" WHERE KCA.TIPOCAL_ID IN('5','6') AND KCA.CODIGO_PERSONAL||KCA.CURSO_ID "+
					" NOT IN(SELECT CODIGO_PERSONAL||CURSO_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL = KCA.CODIGO_PERSONAL AND CURSO_ID = KCA.CURSO_ID AND TIPOCAL_ID = '1')"+
					" AND KCA.CURSO_CARGA_ID "+
					" IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE ESTADO IN('3','4','5')) "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				KrdxCursoAct actual = new KrdxCursoAct();
				actual.mapeaReg(rs);
				lisActual.add(actual);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListCarreraCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<String> getListMaterias(Connection conn, String cargaId) throws SQLException{
	
		ArrayList<String> lisActual	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String materia 		= "";
		
		try{
			comando = "SELECT" +
					" ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA," +
					" CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPOCAL_ID FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE  SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +
					" AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID NOT IN (3,4,5,6))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				materia = rs.getString("CARRERA")+"@"+rs.getString("CODIGO_PERSONAL")+"@"+rs.getString("CURSO_CARGA_ID")
						+"@"+rs.getString("CURSO_ID")+"@"+rs.getString("TIPOCAL_ID");
				
				lisActual.add(materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public ArrayList<String> getListMaterias(Connection conn, String cargaId, String tipoCal) throws SQLException{
		
		ArrayList<String> lisActual	= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String materia 		= "";
		
		try{
			comando = " SELECT ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) AS CARRERA, CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, TIPOCAL_ID"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND CURSO_ID IN (SELECT CURSO_ID FROM ENOC.MAPA_CURSO WHERE TIPOCURSO_ID NOT IN (3,4,5,6))"
					+ " AND TIPOCAL_ID IN ("+tipoCal+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				materia = rs.getString("CARRERA")+"@"+rs.getString("CODIGO_PERSONAL")+"@"+rs.getString("CURSO_CARGA_ID")
						+"@"+rs.getString("CURSO_ID")+"@"+rs.getString("TIPOCAL_ID");
				
				lisActual.add(materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getListMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActual;
	}
	
	public HashMap<String,String> getMapInscritosPorCarrera(Connection conn, String cargaId) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CARRERA_ID, COUNT(CODIGO_PERSONAL) AS CUENTA FROM ENOC.ESTADISTICA " +
					" WHERE CARGA_ID = '"+cargaId+"' AND ESTADO = 'I' GROUP BY CARRERA_ID "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("CARRERA_ID"), rs.getString("CUENTA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getMapInscritosPorCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapMateriasPorAlumno(Connection conn, String cargaId, String modalidades, String tipo) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, COUNT(*) AS TOTAL FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidades+"))"
					+ " AND TIPOCAL_ID IN ("+tipo+")"
					+ " GROUP BY CODIGO_PERSONAL";					
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapMateriasPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapCreditosPorAlumno(Connection conn, String cargaId, String modalidades, String tipo) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT CODIGO_PERSONAL, SUM(ENOC.CREDITOS(CURSO_ID)) AS TOTAL FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidades+"))"
					+ " AND TIPOCAL_ID IN ("+tipo+")"
					+ " GROUP BY CODIGO_PERSONAL";		
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapCreditosPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapMatRepPorAlumno(Connection conn, String cargaId, String modalidades, String tipo, String tipoRep) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT KCA.CODIGO_PERSONAL, COUNT(*) AS TOTAL FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE KCA.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidades+"))"
					+ " AND KCA.TIPOCAL_ID IN ("+tipo+")"
					+ " AND KCA.CODIGO_PERSONAL||KCA.CURSO_ID IN"
					+ "		(SELECT CODIGO_PERSONAL||CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = KCA.CODIGO_PERSONAL AND TIPOCAL_ID IN ("+tipoRep+"))"
					+ " GROUP BY KCA.CODIGO_PERSONAL";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapMateriasPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapCredRepPorAlumno(Connection conn, String cargaId, String modalidades, String tipo, String tipoRep) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT KCA.CODIGO_PERSONAL, SUM(ENOC.CREDITOS(CURSO_ID)) AS TOTAL FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE KCA.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidades+"))"
					+ " AND KCA.TIPOCAL_ID IN ("+tipo+")"
					+ " AND KCA.CODIGO_PERSONAL||KCA.CURSO_ID IN"
					+ "		(SELECT CODIGO_PERSONAL||CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = KCA.CODIGO_PERSONAL AND TIPOCAL_ID IN ("+tipoRep+"))"
					+ " GROUP BY KCA.CODIGO_PERSONAL";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapCredRepPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapCostoRepPorAlumno(Connection conn, String cargaId, String modalidades, String tipo, String tipoRep) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT KCA.CODIGO_PERSONAL, SUM(ALUM_COSTO_CURSO(CODIGO_PERSONAL,CURSO_CARGA_ID,CURSO_ID)) AS TOTAL FROM ENOC.KRDX_CURSO_ACT KCA"
					+ " WHERE KCA.CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND MODALIDAD_ID IN ("+modalidades+"))"
					+ " AND KCA.TIPOCAL_ID IN ("+tipo+")"
					+ " AND KCA.CODIGO_PERSONAL||KCA.CURSO_ID IN"
					+ "		(SELECT CODIGO_PERSONAL||CURSO_ID FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = KCA.CODIGO_PERSONAL AND TIPOCAL_ID IN ("+tipoRep+"))"
					+ " GROUP BY KCA.CODIGO_PERSONAL";
			rs = st.executeQuery(comando);			
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapCostoRepPorAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String, Integer> getMapReprobadosPorCarga(Connection conn, String cargasId) throws SQLException{
		HashMap<String,Integer> map				= new HashMap<String,Integer>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, COUNT(CURSO_ID) AS REPROBADAS" +
					" FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN ("+cargasId+")" +
					" AND TIPOCAL_ID IN('2','4')" +
					" GROUP BY CODIGO_PERSONAL, SUBSTR(CURSO_CARGA_ID,1,6)"; 			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL"), Integer.parseInt(rs.getString("REPROBADAS")));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|getMapReprobadosPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String, String> mapCursos(Connection conn, String cursoCargaId) throws SQLException{
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CODIGO_PERSONAL, (SELECT NOMBRE_CURSO FROM ENOC.MAPA_CURSO WHERE CURSO_ID = A.CURSO_ID) AS CURSO" +
					"  FROM ENOC.KRDX_CURSO_ACT A WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "; 			
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CURSO") );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.ActualUtil|mapCursos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public static HashMap<String,String> getMapInscritosProm(Connection conn, String cargaId, String orden ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, " +
					" TO_CHAR(" +
					" SUM(" +
					" (SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID)*" +
					" (CASE WHEN NOTA_EXTRA IS NULL THEN NOTA WHEN NOTA_EXTRA =0 THEN NOTA ELSE NOTA_EXTRA END)" +
					" ) / CASE SUM((SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID)) WHEN 0 THEN 1 ELSE SUM((SELECT CREDITOS FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID)) END" +
					",'999.99')  AS PROM" +
					" FROM ENOC.KRDX_CURSO_ACT" +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6)= '"+cargaId+"' " +
					" AND TIPOCAL_ID IN ('1','2')" +
					" GROUP BY CODIGO_PERSONAL "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("PROM") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|getMapInscritosProm|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> mapAlumnoPuntos(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando =
					"SELECT"+
					  " CODIGO_PERSONAL, CURSO_CARGA_ID,"+
					  " SUM("+    
					    " CASE"+
					      " (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)"+
					    " WHEN '%' THEN COALESCE(NOTA,0)*(SELECT VALOR FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID)/100"+
					    " ELSE COALESCE(NOTA,0)"+
					    " END"+
					  " ) AS PUNTOS"+
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) IN('%','P')"+
					" GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";					
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID"), rs.getString("PUNTOS") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|mapAlumnoPuntos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> mapAlumnoExtras(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, SUM(COALESCE(NOTA,0)) AS PUNTOS " +
					" FROM ENOC.KRDX_ALUMNO_EVAL KAE"+
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'"+
					" AND (SELECT TIPO FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = KAE.CURSO_CARGA_ID AND EVALUACION_ID = KAE.EVALUACION_ID) = 'E'"+
					" GROUP BY CODIGO_PERSONAL, CURSO_CARGA_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("CURSO_CARGA_ID"), rs.getString("PUNTOS") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|mapAlumnoExtras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	/*
	 * 
	 * */
	public static HashMap<String,String> mapAlumnoEvaluados(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL,"+
				  " (SELECT"+
				    " COALESCE(SUM(VALOR),0) AS PUNTOS"+ 
				  " FROM ENOC.CARGA_GRUPO_EVALUACION CGE"+
				  " WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
				  " AND TIPO NOT IN ('E')"+				  
				  " AND EVALUACION_ID IN (SELECT EVALUACION_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID = CGE.CURSO_CARGA_ID AND CODIGO_PERSONAL = KCA.CODIGO_PERSONAL)" +
				  " )"+
				  " AS PUNTOS"+
				" FROM ENOC.KRDX_CURSO_ACT KCA"+
				" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("CODIGO_PERSONAL"), rs.getString("PUNTOS") );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|mapAlumnoEvaluados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	/*
	 * 
	 * */
	public static HashMap<String,String> mapLimiteExtra(Connection conn, String cursoCargaId) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, (SELECT NOTA_EXTRA FROM ENOC.MAPA_PLAN WHERE PLAN_ID = (SELECT PLAN_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KCA.CURSO_ID)) AS LIMITE_EXTRA "+
					" FROM ENOC.KRDX_CURSO_ACT KCA" +
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put( rs.getString("CODIGO_PERSONAL"), rs.getString("LIMITE_EXTRA") );
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|mapLimiteExtra|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> mapMatPorTipo(Connection conn, String cargaId, String tipo) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' AND TIPO = '"+tipo+"'"
					+ " GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				map.put( rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL") );
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.kardx.ActualUtil|mapMatPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}