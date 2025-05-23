// Bean de Cursos Importados
package  aca.kardex;

import java.sql.*;

public class KrdxCursoImp{
	private String codigoPersonal;
	private String folio;
	private String fCreada;
	private String cursoId;
	private String cursoId2;
	private String convalidacion;
	private String titulo;
	private String optativa;
	private String tipoCalId;
	private String nota;
	private String notaExtra;
	private String fExtra;
	private String notaConva;
	private String observaciones;
	private String optativaNombre;
	private String usuario;
	private String fecha;
	
	
	public KrdxCursoImp(){
		codigoPersonal	= "";
		folio			= "";
		fCreada			= "";
		cursoId			= "";
		cursoId2		= "";
		convalidacion	= "";
		titulo			= "";
		optativa		= "";
		tipoCalId		= "";
		nota			= "";
		notaExtra		= "";
		fExtra			= "";
		notaConva 		= "";
		observaciones	= "";
		optativaNombre	= "";
		usuario			= "";
		fecha			= "";
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the fCreada
	 */
	public String getFCreada() {
		return fCreada;
	}
	
	/**
	 * @param creada the fCreada to set
	 */
	public void setFCreada(String creada) {
		fCreada = creada;
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
	 * @return the convalidacion
	 */
	public String getConvalidacion() {
		return convalidacion;
	}

	/**
	 * @param convalidacion the convalidacion to set
	 */
	public void setConvalidacion(String convalidacion) {
		this.convalidacion = convalidacion;
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
	 * @return the optativa
	 */
	public String getOptativa() {
		return optativa;
	}

	/**
	 * @param optativa the optativa to set
	 */
	public void setOptativa(String optativa) {
		this.optativa = optativa;
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
	 * @return the notaConva
	 */
	public String getNotaConva() {
		return notaConva;
	}

	/**
	 * @param notaConva the notaConva to set
	 */
	public void setNotaConva(String notaConva) {
		this.notaConva = notaConva;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the optativaNombre
	 */
	public String getOptativaNombre() {
		return optativaNombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param optativaNombre the optativaNombre to set
	 */
	public void setOptativaNombre(String optativaNombre) {
		this.optativaNombre = optativaNombre;
	}
	
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_CURSO_IMP " 
				+ " (CODIGO_PERSONAL, FOLIO, F_CREADA, CURSO_ID, CURSO_ID2, CONVALIDACION, " 
				+ " TITULO, OPTATIVA, TIPOCAL_ID, NOTA, NOTA_EXTRA, F_EXTRA, NOTA_CONVA, OBSERVACIONES, OPTATIVA_NOMBRE, USUARIO, FECHA ) " 
				+ " VALUES( ?, " 
				+ " TO_NUMBER(?,'999'), " 
				+ " TO_DATE(?,'DD/MM/YYYY'), " 
				+ " ?, " 
				+ " ?, " 
				+ " ?, "
				+ " ?, " 
				+ " ?, " 
				+ " ?, " 
				+ " TO_NUMBER(?,'99999.99'), " 
				+ " TO_NUMBER(?,'99999.99'), " 
				+ " TO_DATE(?,'DD/MM/YYYY'), " 				
				+ " ?,?,?,?,"
				+ " TO_DATE(?,'DD/MM/YYYY'))");
					
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			ps.setString(3, fCreada);
			ps.setString(4, cursoId);
			ps.setString(5, cursoId2);
			ps.setString(6, convalidacion);
			ps.setString(7, titulo);
			ps.setString(8, optativa);
			ps.setString(9, tipoCalId);
			ps.setString(10,nota);
			ps.setString(11,notaExtra);
			ps.setString(12,fExtra);
			ps.setString(13,notaConva);
			ps.setString(14,observaciones);
			ps.setString(15,optativaNombre);
			ps.setString(16,usuario);
			ps.setString(17,fecha);

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxCursoImp|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean insertConvalida(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_CURSO_IMP "  
								+ "(CODIGO_PERSONAL, CURSO_ID, TIPOCAL_ID, F_CREADA, NOTA , OBSERVACIONES, CURSO_ID2, CONVALIDACION, OPTATIVA, FOLIO, TITULO, USUARIO, FECHA ) "
								+ "VALUES (?, ?, '1', TO_DATE(?,'DD/MM/YYYY'), " +
								"TO_NUMBER(?,'999'), 'Materia convalidada', ?, 'I', 'N', " +
								"TO_NUMBER(?,'999'), 'N', ?, TO_DATE(?,'DD/MM/YYYY'))");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			ps.setString(3, fCreada);
			ps.setString(4, nota);
			ps.setString(5, cursoId2);
			ps.setString(6, folio);	
			ps.setString(7, usuario);
			ps.setString(8, fecha);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|insertConvalida|:"+eCurso_id2);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;		
		try{
			//System.out.println("Datos:"+nota+":"+notaConva+":"+folio);
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_IMP" + 
				" SET" +
				" F_CREADA = TO_DATE(?,'DD/MM/YYYY')," +
				" CURSO_ID = ?," +
				" CURSO_ID2 = ?," +
				" CONVALIDACION = ?," +
				" TITULO = ?," +
				" OPTATIVA = ?," +				
				" TIPOCAL_ID  = ?," +
				" NOTA = TO_NUMBER(?,'999')," +
				" NOTA_EXTRA = TO_NUMBER(?,'999')," +
				" F_EXTRA = TO_DATE(?,'DD/MM/YYYY')," +
				" NOTA_CONVA = ?,"+
				" OBSERVACIONES = ?," +
				" OPTATIVA_NOMBRE = ?," +
				" USUARIO = ?, "+
				" FECHA = TO_DATE(?,'DD/MM/YYYY') " +
				" WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, fCreada);
			ps.setString(2, cursoId);
			ps.setString(3, cursoId2);
			ps.setString(4, convalidacion);
			ps.setString(5, titulo);
			ps.setString(6, optativa);
			ps.setString(7, tipoCalId);
			ps.setString(8, nota);
			ps.setString(9, notaExtra);
			ps.setString(10, fExtra);
			ps.setString(11, notaConva);
			ps.setString(12, observaciones);
			ps.setString(13, optativaNombre);
			ps.setString(14, usuario);
			ps.setString(15, fecha);
			ps.setString(16, codigoPersonal);
			ps.setString(17, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|updateReg|:"+eCurso_id2);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_CURSO_IMP "+ 
				"WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|deletReg|:"+eCurso_id2);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		folio 			= rs.getString("FOLIO");
		fCreada			= rs.getString("F_CREADA");
		cursoId	 		= rs.getString("CURSO_ID");
		cursoId2		= rs.getString("CURSO_ID2");
		convalidacion	= rs.getString("CONVALIDACION");
		titulo			= rs.getString("TITULO");
		optativa 		= rs.getString("OPTATIVA");
		tipoCalId		= rs.getString("TIPOCAL_ID");
		nota 			= rs.getString("NOTA");
		notaExtra 		= rs.getString("NOTA_EXTRA");
		fExtra 			= rs.getString("F_EXTRA");
		notaConva 		= rs.getString("NOTA_CONVA");
		observaciones	= rs.getString("OBSERVACIONES");
		optativaNombre	= rs.getString("OPTATIVA_NOMBRE");
		usuario			= rs.getString("USUARIO");
		fecha			= rs.getString("FECHA");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String folio ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL, FOLIO,  " +
										" TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA," +
										" CURSO_ID, CURSO_ID2, CONVALIDACION, TITULO, OPTATIVA," +
										" TIPOCAL_ID, NOTA, NOTA_EXTRA," +
										" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA," +
										" NOTA_CONVA," +													
										" OBSERVACIONES," +
										" OPTATIVA_NOMBRE," +
										" USUARIO, " +
										" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA" +
										" FROM ENOC.KRDX_CURSO_IMP" + 
										" WHERE CODIGO_PERSONAL = ?" +
										" AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|mapeaRegId|:"+eCurso_id2);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);	
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|existeReg|:"+eCurso_id2);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeCursoIdReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND CURSO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);	
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|existeCursoIdReg|:"+eCurso_id2);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean registrarConvInternas(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_CURSO_IMP" + 
					" SET CONVALIDACION = 'I'" +
					" WHERE CONVALIDACION = 'S'" +
					" AND TIPOCAL_ID = '1'" +
					" AND (NOTA >= 70 OR NOTA_EXTRA >= 70)");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|registrarConvInternas|:"+eCurso_id2);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean tieneImportados(Connection conn, String codigoPersonal, String year, String planId) throws SQLException{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		ResultSet rs			= null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND F_CREADA BETWEEN TO_DATE('01/08/"+year+"','DD/MM/YYYY') AND TO_DATE('31/07/"+(Integer.parseInt(year)+1)+"','DD/MM/YYYY') " +
					" AND ENOC.CURSO_PLAN(CURSO_ID) = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;	
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|tieneImportados|:"+eCurso_id2);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO" +
					" FROM ENOC.KRDX_CURSO_IMP" + 
					" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.krdxCursoImp|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean tieneImp(Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		ResultSet rs			= null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_ID FROM ENOC.KRDX_CURSO_IMP" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CURSO_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;	
			else
				ok = false;
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.KrdxCursoImp|tieneImp|:"+eCurso_id2);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}