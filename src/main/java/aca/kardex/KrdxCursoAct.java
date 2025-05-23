// Bean de la tablas KRDX_CURSO_ACT 
package  aca.kardex;

import java.sql.*;
import java.util.HashMap;

public class KrdxCursoAct{
	private String codigoPersonal;
	private String cursoCargaId;
	private String cursoId;
	private String cursoId2;
	private String tipoCalId;
	private String nota;
	private String fNota;
	private String notaExtra;
	private String fExtra;
	private String tipo;
	private String titulo;
	private String fTitulo;
	private String comentario;
	private String correccion;
	
	public KrdxCursoAct(){
		codigoPersonal	= "0";
		cursoCargaId	= "0";
		cursoId			= "";
		cursoId2		= "";
		tipoCalId		= "";
		nota			= "0";
		fNota 			= "";
		notaExtra		= "0";
		fExtra			= "";
		tipo			= "";
		titulo			= "";
		fTitulo			= "";
		comentario		= "-";
		correccion 		= "N";
	}	
		
	
	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}


	/**
	 * @param cursoCargaId the cursoCargaId to set
	 */
	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}


	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}


	/**
	 * @param cursoId the cursoId to set
	 */
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}


	/**
	 * @return the cursoId2
	 */
	public String getCursoId2() {
		return cursoId2;
	}


	/**
	 * @param cursoId2 the cursoId2 to set
	 */
	public void setCursoId2(String cursoId2) {
		this.cursoId2 = cursoId2;
	}


	/**
	 * @return the tipoCalId
	 */
	public String getTipoCalId() {
		return tipoCalId;
	}


	/**
	 * @param tipoCalId the tipoCalId to set
	 */
	public void setTipoCalId(String tipoCalId) {
		this.tipoCalId = tipoCalId;
	}


	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}


	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}


	/**
	 * @return the fNota
	 */
	public String getFNota() {
		return fNota;
	}


	/**
	 * @param nota the fNota to set
	 */
	public void setFNota(String nota) {
		fNota = nota;
	}


	/**
	 * @return the notaExtra
	 */
	public String getNotaExtra() {
		return notaExtra;
	}


	/**
	 * @param notaExtra the notaExtra to set
	 */
	public void setNotaExtra(String notaExtra) {
		this.notaExtra = notaExtra;
	}


	/**
	 * @return the fExtra
	 */
	public String getFExtra() {
		return fExtra;
	}


	/**
	 * @param extra the fExtra to set
	 */
	public void setFExtra(String extra) {
		fExtra = extra;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 * @return the fTitulo
	 */
	public String getFTitulo() {
		return fTitulo;
	}


	/**
	 * @param titulo the fTitulo to set
	 */
	public void setFTitulo(String titulo) {
		fTitulo = titulo;
	}


	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}


	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	/**
	 * @return the correccion
	 */
	public String getCorreccion() {
		return correccion;
	}


	/**
	 * @param correccion the correccion to set
	 */
	public void setCorreccion(String correccion) {
		this.correccion = correccion;
	}
	
	/**
	 * @param conn the connection.
	 */
	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			this.titulo="N";
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_CURSO_ACT"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				"NOTA, F_NOTA, NOTA_EXTRA, F_EXTRA, TIPO, TITULO, COMENTARIO, CORRECCION ) "+
				"VALUES( ?, ?, ?, ?, ?, "+
				"TO_NUMBER(?,'999'), "+	
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+				
				" ?, 'N', ?, ?)");
					
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, cursoId);
			ps.setString(4, cursoId2);
			ps.setString(5, tipoCalId);
			ps.setString(6, nota);
			ps.setString(7, fNota);
			ps.setString(8, notaExtra);
			ps.setString(9, fExtra);
			ps.setString(10, tipo);
			ps.setString(11, comentario);
			ps.setString(12, correccion);
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	/**
	 * @param conn the connection.
	 */
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_ACT"+ 
				" SET "+
				" CURSO_ID = ?,"+
				" CURSO_ID2 = ?,"+
				" TIPOCAL_ID  = ?,"+
				" NOTA = TO_NUMBER(?,'999'),"+
				" F_NOTA = TO_DATE(?,'DD/MM/YYYY'),"+
				" NOTA_EXTRA = TO_NUMBER(?,'999'),"+
				" F_EXTRA = TO_DATE(?,'DD/MM/YYYY'),"+
				" TIPO = ?,"+
				" TITULO = ?,"+
				" COMENTARIO = ?," +
				" CORRECCION = ?"+
				" WHERE CODIGO_PERSONAL = ?"+
				" AND CURSO_CARGA_ID = ?");
			ps.setString(1, cursoId);
			ps.setString(2, cursoId2);
			ps.setString(3, tipoCalId);
			ps.setString(4, nota);
			ps.setString(5, fNota);
			ps.setString(6, notaExtra);
			ps.setString(7, fExtra);
			ps.setString(8, tipo);
			ps.setString(9, titulo);
			ps.setString(10, comentario);
			ps.setString(11, correccion);
			ps.setString(12,codigoPersonal);			
			ps.setString(13,cursoCargaId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	/**
	 * @param conn the connection.
	 */
	public boolean updateRegTitulo(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_ACT "+ 
				"SET "+				
				"NOTA = TO_NUMBER(?,'999'), "+				
				"F_TITULO = TO_DATE(?,'DD/MM/YYYY'), "+
				"TIPOCAL_ID  = ?  "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");			
			ps.setString(1, nota);			
			ps.setString(2, fTitulo);
			ps.setString(3, tipoCalId);
			ps.setString(4, codigoPersonal);
			ps.setString(5, cursoCargaId);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|updateRegTitulo|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	
	public boolean updateTipo(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_ACT "+ 
				"SET TIPO = ? WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
						
			ps.setString(1, tipo);
			ps.setString(2, codigoPersonal);
			ps.setString(3, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|updateTipo|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateTitulo(Connection conn) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_ACT "+ 
				"SET TITULO = ?, F_TITULO = TO_DATE(?,'DD/MM/YYYY'), "+
				"NOTA = TO_NUMBER(?,'999'), "+
				"TIPOCAL_ID  = ?  "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
						
			ps.setString(1, titulo);
			ps.setString(2, fTitulo);
			ps.setString(3, nota);
			ps.setString(4, tipoCalId);
			ps.setString(5, codigoPersonal);
			ps.setString(6, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|updateTitulo|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	/**
	 * @param conn The connection to set.
	 */		
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	/**
	 * @param "conn" the connection of DB and "cargaId" the period
	 */		
	public static boolean deleteAlumnosEnProceso(Connection conn, String cargaId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_ACT KCA"+ 
				" WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?" +
				" AND KCA.TIPOCAL_ID IN('M','C')" +
				" AND KCA.CURSO_CARGA_ID NOT IN" +
				"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL" + 
				"	WHERE CURSO_CARGA_ID||CODIGO_PERSONAL = KCA.CURSO_CARGA_ID||KCA.CODIGO_PERSONAL)");
			ps.setString(1, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|deleteAlumnosEnProceso|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	/**
	 * @param "conn" the connection of DB, "cargaId" the period and codigoPersonal the student 
	 */		
	public static boolean deleteAlumnoEnProceso(Connection conn, String cargaId, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_ACT KCA"+ 
				" WHERE SUBSTR(KCA.CURSO_CARGA_ID,1,6) = ?" +
				" AND CODIGO_PERSONAL = ?" +
				" AND KCA.TIPOCAL_ID IN('M','C')" +
				" AND KCA.CURSO_CARGA_ID NOT IN" +
				"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL" + 
				"	WHERE CURSO_CARGA_ID||CODIGO_PERSONAL = KCA.CURSO_CARGA_ID||KCA.CODIGO_PERSONAL)");
			ps.setString(1, cargaId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|deleteAlumnoEnProceso|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		cursoId	 		= rs.getString("CURSO_ID");
		cursoId2		= rs.getString("CURSO_ID2");
		tipoCalId		= rs.getString("TIPOCAL_ID");
		nota 			= rs.getString("NOTA");
		fNota 			= rs.getString("F_NOTA");
		notaExtra 		= rs.getString("NOTA_EXTRA");
		fExtra 			= rs.getString("F_EXTRA");
		tipo			= rs.getString("TIPO");
		titulo			= rs.getString("TITULO");
		fTitulo			= rs.getString("F_TITULO");
		comentario 		= rs.getString("COMENTARIO");
		correccion 		= rs.getString("CORRECCION");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoCargaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				"NOTA, COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, " +
				"TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"TIPO, TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"COMENTARIO, CORRECCION "+
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaCursoAprobado( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_CARGA_ID, CURSO_ID, CURSO_ID2, TIPOCAL_ID, "+
				"NOTA, COALESCE(NOTA_EXTRA,0) AS NOTA_EXTRA, " +
				"TO_CHAR(F_NOTA,'DD/MM/YYYY') AS F_NOTA, " +
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, " +
				"TIPO, TITULO, " +
				"TO_CHAR(F_TITULO,'DD/MM/YYYY') AS F_TITULO, " +
				"COMENTARIO, CORRECCION "+
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_ID = ?" +
				" AND TIPOCAL_ID = '1'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|mapeaCursoAprobado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int numMatCalculo(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT ALUM_MAT_ORI(CODIGO_PERSONAL,'"+cargaId+"') AS NUMMAT " +
				"FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatCalculo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}

	public static int numMatOrigen(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_CARGA_ID,1,6)=?" +
				"AND TIPO = 'O'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatInscritas(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_CARGA_ID,1,6)=?" +
				"AND TIPOCAL_ID = 'I'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatInscritas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatAlta(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			/*
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_CARGA_ID,1,6)=? "+
				"AND TIPO = 'A'");
			*/
			ps = conn.prepareStatement("SELECT ALUM_MAT_ALTA(CODIGO_PERSONAL,'"+cargaId+"') AS NUMMAT " +
			"FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
			
			ps.setString(1, codigoPersonal);
			//ps.setString(2, cargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatAlta|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatBaja(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{/*
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_CARGA_ID,1,6)=? "+
				"AND TIPO = 'B'");
			*/	
			ps = conn.prepareStatement("SELECT ALUM_MAT_BAJA(CODIGO_PERSONAL,'"+cargaId+"') AS NUMMAT " +
			"FROM ENOC.ALUM_PERSONAL WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1, codigoPersonal);
			//ps.setString(2, cargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatBaja|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatCursadas(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND SUBSTR(CURSO_CARGA_ID,1,6)=?" +
				"AND TIPOCAL_ID <> '3'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatCursadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static String alumCredCarga(Connection conn, String codigoPersonal, String year, String ciclo, String planId, String periodo) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String numCred		= "";
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) AS CREDITOS FROM ENOC.MAPA_CURSO" + 
					" WHERE (CURSO_ID IN (SELECT CURSO_ID FROM ENOC.KRDX_CURSO_ACT" + 
										" WHERE CODIGO_PERSONAL = ?" +
										" AND SUBSTR(CURSO_CARGA_ID, 1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA" + 
																			" WHERE PERIODO = '"+periodo+"'" +
																			" AND CICLO IN ('"+ciclo+"')))" +
						   " OR CURSO_ID IN (SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP" + 
						   				   " WHERE CODIGO_PERSONAL = ?" +
						   				   " AND F_CREADA BETWEEN TO_DATE('01/08/"+year+"','DD/MM/YYYY') AND TO_DATE('31/07/"+(Integer.parseInt(year)+1)+"','DD/MM/YYYY') ))" +
						   " AND PLAN_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, codigoPersonal);
			ps.setString(3, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numCred = rs.getString("CREDITOS")==null?"":rs.getString("CREDITOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|alumCredCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numCred;
	}
	
	public static String alumCredCarga(Connection conn, String codigoPersonal, String carga ) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String numCred		= "";
		try{
			ps = conn.prepareStatement("SELECT COALESCE(SUM(ENOC.CREDITOS(CURSO_ID)),0) AS NUMCREDITOS " +
					" FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL = ?" + 
					" AND SUBSTR(CURSO_CARGA_ID, 1,6) = ? AND TIPO IN('O','A')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, carga);
			
			rs = ps.executeQuery();
			if (rs.next())
				numCred = rs.getString("NUMCREDITOS")==null?"":rs.getString("NUMCREDITOS");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|alumCredCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numCred;
	}
	
	public static String credCargaPorTipo(Connection conn, String carga, String modalidades, String tipo, String tipocal ) throws SQLException{
		PreparedStatement 	ps	= null;
		Statement st 			= conn.createStatement();
		ResultSet 			rs	= null;
		String numCred			= "";
		String comando			= "";
		try{			
			comando = " SELECT COALESCE(SUM(ENOC.CREDITOS(CURSO_ID)),0) AS NUMCREDITOS"
					+ " FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE SUBSTR(CURSO_CARGA_ID, 1,6) = '"+carga+"'"
					+ " AND GRUPO_MODALIDAD(CURSO_CARGA_ID) IN ("+modalidades+")"
					+ " AND TIPO IN("+tipo+")"
					+ " AND TIPOCAL_ID IN ("+tipocal+")";	
			
			rs = st.executeQuery(comando);
			if (rs.next())
				numCred = rs.getString("NUMCREDITOS")==null?"":rs.getString("NUMCREDITOS");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|credCargaPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numCred;
	}
	
	public static int numMatCarga(Connection conn, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')");
			
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static String getTipoCalId(Connection conn, String codigoPersonal, String cursoCargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		String tipo				= "M";
		try{
			ps = conn.prepareStatement("SELECT TIPOCAL_ID FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CURSO_CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("TIPOCAL_ID");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|getTipoCalId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static boolean tieneMateriaDeAptitud(Connection conn, String codigoPersonal, String cursoId, String tipo ) throws SQLException{
		
		Statement st 	= conn.createStatement();
		ResultSet rs	= null;
		String comando 	= "";
		boolean ok		= false;
		try{			
			comando = "SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND SUBSTR(CURSO_ID,9,7) = '"+cursoId+"'"
					+ " AND TIPOCAL_ID IN ("+tipo+")"
					+ " AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)";
			
			rs = st.executeQuery(comando);
			if (rs.next()){
				ok = true;
			
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|tieneMateriaDeAptitud|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int numMatReprobadasCarga(Connection conn, String cargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')" +
					" AND TIPOCAL_ID IN ('2','4')");
			
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatReprobadasCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatCarrera(Connection conn, String cargaId, String carreraId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')");
			
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numMatReprobadasCarrera(Connection conn, String cargaId, String carreraId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?" +
					" AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')" +
					" AND TIPOCAL_ID IN ('2','4')");
			
			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatReprobadasCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumMateria(Connection conn, String carreraId, String cursoCargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?");
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumMat(Connection conn, String cursoCargaId, String estados) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT"
					+ " WHERE CURSO_CARGA_ID = ?"					
					+ " AND TIPOCAL_ID IN ("+estados+")");
			
			ps.setString(1, cursoCargaId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumCargaPlan(Connection conn, String cursoCargaId, String planId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ENOC.CURSO_PLAN(CURSO_ID) = ?"+
					" AND TIPOCAL_ID = 'M'");
			
			ps.setString(1, cursoCargaId);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumCargaPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumInscPlan(Connection conn, String cursoCargaId, String planId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ENOC.CURSO_PLAN(CURSO_ID) = ?"+
					" AND TIPOCAL_ID != 'M'");
			
			ps.setString(1, cursoCargaId);			
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumInscPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumInscMat(Connection conn, String cursoCargaId, String tipos) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +					
					" AND TIPOCAL_ID IN ("+tipos+")");
			
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumInscPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumGrupo(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND TIPOCAL_ID != 'M'");
			
			ps.setString(1, cursoCargaId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumReprobadosMateria(Connection conn, String carreraId, String cursoCargaId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?" +
					" AND TIPOCAL_ID IN ('2','4')");

			ps.setString(1, cursoCargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumReprobadosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que reprobaron la cantidad de materias dadas en la carga dada
	 * @param conn Conexixn a la base de datos
	 * @param cargaId La carga en la que se buscarxn los alumnos
	 * @param numMaterias La cantidad de materias que reprobaron los alumnos. Puede ser dado como "1" o "1,2,3,4,5"
	 * */
	public static int numAlumRepNumMateriasCarga(Connection conn, String cargaId, String numMaterias) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
									  " FROM ENOC.KRDX_CURSO_ACT" + 
									  " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
									  " AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')" +
									  " AND TIPOCAL_ID IN ('2','4')" +
									  " GROUP BY CODIGO_PERSONAL" +
									  " HAVING COUNT(CURSO_CARGA_ID) IN ("+numMaterias+")");

			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				numMat++;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumReprobadosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	/**
	 * @author Elifo
	 * @return El numero de alumnos que reprobaron la cantidad de materias dadas en la carga dada en la carrera dada
	 * @param conn Conexixn a la base de datos
	 * @param cargaId La carga en la que se buscarxn los alumnos
	 * @param carreraId La carrera en la que se buscarxn los alumnos
	 * @param numMaterias La cantidad de materias que reprobaron los alumnos. Puede ser dado como "1" o "1,2,3,4,5"
	 * */
	public static int numAlumRepNumMateriasCarrera(Connection conn, String cargaId, String carreraId, String numMaterias) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;		
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS NUMMAT" +
									  " FROM ENOC.KRDX_CURSO_ACT" + 
									  " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?" +
									  " AND ENOC.CARRERA(ENOC.CURSO_PLAN(CURSO_ID)) = ?" +
									  " AND ENOC.TIPOCURSO_ID(CURSO_ID) NOT IN ('4','6')" +
									  " AND TIPOCAL_ID IN ('2','4')" +
									  " GROUP BY CODIGO_PERSONAL" +
									  " HAVING COUNT(CURSO_CARGA_ID) IN ("+numMaterias+")");

			ps.setString(1, cargaId);
			ps.setString(2, carreraId);
			
			rs = ps.executeQuery();
			while(rs.next()){
				numMat++;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumReprobadosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	/**
	 * Obtiene el promedio del curso ...  	
	 **/
	public static String getPromedioAntesDeCorreccion(Connection conn, String codigoAlumno, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String nota 			= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT ROUND(ALUM_CURSO_PROMEDIO(CODIGO_PERSONAL,CURSO_CARGA_ID)) AS NOTA FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND CODIGO_PERSONAL= ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, codigoAlumno);
			
			rs = ps.executeQuery();
			if (rs.next())
				nota = rs.getString("NOTA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nota;
	}
	
	public static int numMatInsc(Connection conn, String codigoPersonal, String planId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMMAT " +
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND ENOC.CURSO_PLAN(CURSO_ID)= ?" +
				"AND TIPOCAL_ID = 'I'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numMatInsc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static int numAlumGrupo(Connection conn, String cursoCargaId, String tipocalId) throws SQLException{
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";	
		int numMat				= 0;
		
		try{
			comando = "SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
					" AND TIPOCAL_ID IN ("+tipocalId+")";			
			rs = st.executeQuery(comando);
			if (rs.next())
				numMat = rs.getInt("NUMMAT");
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}
	
	public static String alumAsignados(Connection conn, String cursoCargaId, String tipocalId) throws SQLException{
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String alumnos			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'" +
					" AND TIPOCAL_ID IN ("+tipocalId+")";			
			rs = st.executeQuery(comando);
			while ( rs.next() ){
				alumnos += rs.getInt("CODIGO_PERSONAL")+",";
			}
			alumnos = alumnos.substring(0, alumnos.length()-1);
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return alumnos;
	}
	
	public static int numAlumDifMateria(Connection conn, String cursoCargaId, String cursoId) throws SQLException{
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		int numMat				= 0;
		try{
			ps = conn.prepareStatement("SELECT COUNT(CURSO_CARGA_ID) AS NUMMAT FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CURSO_CARGA_ID = ?" +
					" AND CURSO_ID = ? " +
					" AND TIPOCAL_ID = '6' ");

			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numMat = rs.getInt("NUMMAT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoAct|numAlumDifMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numMat;
	}	
	
	public static HashMap<String,String> alumnos(Connection conn, String cargaId, String carreraId) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT CURSO_CARGA_ID, TIPOCAL_ID, COUNT(CODIGO_PERSONAL) AS CANTIDAD " +
					" FROM ENOC.KRDX_CURSO_ACT " +
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' " +
					" AND ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) = '"+carreraId+"'" +
					" GROUP BY CURSO_CARGA_ID,TIPOCAL_ID" +
					" ORDER BY 1,2 "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("CURSO_CARGA_ID")+rs.getString("TIPOCAL_ID"), rs.getString("CANTIDAD"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.krdx.KrdxCursoAct|alumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String,String> maestros(Connection conn, String cargaId, String carreraId) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT C.CODIGO_PERSONAL AS CODIGO_PERSONAL, A.TIPOCAL_ID AS TIPOCAL_ID, COUNT(A.CODIGO_PERSONAL) AS CANTIDAD" +
					" FROM ENOC.KRDX_CURSO_ACT A, ENOC.CARGA_GRUPO C" +
					" WHERE SUBSTR(A.CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
					" AND A.CURSO_CARGA_ID = C.CURSO_CARGA_ID" +
					" AND ENOC.ALUM_CARRERA_ID(A.CODIGO_PERSONAL) = '"+carreraId+"'" +
					" GROUP BY A.TIPOCAL_ID, C.CODIGO_PERSONAL" +
					" ORDER BY 1,2"; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				map.put(rs.getString("CODIGO_PERSONAL")+rs.getString("TIPOCAL_ID"), rs.getString("CANTIDAD"));
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.krdx.KrdxCursoAct|alumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String,String> mapReprobadasPorCargas(Connection conn, String codigoPersonal) throws SQLException{
		
		HashMap<String,String> map				= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS CARGA, COUNT(CODIGO_PERSONAL) AS TOTAL FROM KRDX_CURSO_ACT"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND ( TIPOCAL_ID IN ('2','4') OR (TIPOCAL_ID= '1' AND NOTA < 70) )"
					+ " GROUP BY SUBSTR(CURSO_CARGA_ID,1,6)";
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				map.put(rs.getString("CARGA"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.krdx.KrdxCursoAct|mapReprobadasPorCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	
}