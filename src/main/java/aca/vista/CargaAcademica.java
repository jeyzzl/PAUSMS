// Clase para la vista CARGA_ACADEMICA
package  aca.vista;

import java.sql.*;
import java.util.ArrayList;

public class CargaAcademica{
	private String cursoCargaId;
	private String cargaId;
	private String bloqueId;
	private String codigoPersonal;
	private String nombre;
	private String carreraId;
	private String grupo;
	private String modalidadId;
	private String estado;
	private String fInicio;
	private String fFinal;	
	private String fEntrega;
	private String origen;	
	private String planId;
	private String cursoId;
	private String nombreCurso;
	private String ciclo;
	private String creditos;
	private String ht;
	private String Hp;
	private String Hi;
	private String notaAprobatoria;	
	private String valeucas;
	private String numAlum;
	private String semanas;
	private String optativa;
	
	public CargaAcademica(){
		cursoCargaId	= "";
		cargaId			= "";
		bloqueId		= "";
		codigoPersonal	= "";
		nombre			= "";
		carreraId		= "";
		grupo			= "";
		modalidadId		= "";
		estado			= "";
		fInicio			= "";
		fFinal			= "";
		fEntrega		= "";
		origen			= "";	
		planId			= "";
		cursoId			= "";
		nombreCurso		= "";
		ciclo			= "";
		creditos		= "";
		ht				= "";
		Hp				= "";
		Hi				= "";
		notaAprobatoria	= "";		
		valeucas		= "";
		numAlum			= "";
		semanas			= "";
		optativa 		= "";
	}
		
	/**
	 * @return the horas
	 */
	public String getSemanas() {
		return semanas;
	}

	/**
	 * @return the bloqueId
	 */
	public String getBloqueId() {
		return bloqueId;
	}

	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}

	/**
	 * @return the carreraId
	 */
	public String getCarreraId() {
		return carreraId;
	}

	/**
	 * @return the ciclo
	 */
	public String getCiclo() {
		return ciclo;
	}

	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	/**
	 * @return the creditos
	 */
	public String getCreditos() {
		return creditos;
	}

	/**
	 * @return the cursoCargaId
	 */
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	/**
	 * @return the cursoId
	 */
	public String getCursoId() {
		return cursoId;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @return the fEntrega
	 */
	public String getFEntrega() {
		return fEntrega;
	}

	/**
	 * @return the fFinal
	 */
	public String getFFinal() {
		return fFinal;
	}

	/**
	 * @return the fInicio
	 */
	public String getFInicio() {
		return fInicio;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}	

	/**
	 * @return the hp
	 */
	public String getHp() {
		return Hp;
	}

	/**
	 * @return the ht
	 */
	public String getHt() {
		return ht;
	}

	/**
	 * @return the modalidadId
	 */
	public String getModalidadId() {
		return modalidadId;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the nombreCurso
	 */
	public String getNombreCurso() {
		return nombreCurso;
	}

	/**
	 * @return the notaAprobatoria
	 */
	public String getNotaAprobatoria() {
		return notaAprobatoria;
	}

	/**
	 * @return the numAlum
	 */
	public String getNumAlum() {
		return numAlum;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}

	/**
	 * @return the valeucas
	 */
	public String getValeucas() {
		return valeucas;
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

	public String getHi() {
		return Hi;
	}

	public void setHi(String hi) {
		Hi = hi;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		cargaId 			= rs.getString("CARGA_ID");
		bloqueId			= rs.getString("BLOQUE_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		nombre				= rs.getString("NOMBRE");
		carreraId 			= rs.getString("CARRERA_ID");
		grupo				= rs.getString("GRUPO");
		modalidadId 		= rs.getString("MODALIDAD_ID");
		estado 				= rs.getString("ESTADO");
		fInicio				= rs.getString("F_INICIO");
		fFinal 				= rs.getString("F_FINAL");		
		fEntrega 			= rs.getString("F_ENTREGA");
		origen 				= rs.getString("ORIGEN");
		planId 				= rs.getString("PLAN_ID");
		cursoId 			= rs.getString("CURSO_ID");
		nombreCurso			= rs.getString("NOMBRE_CURSO");
		ciclo 				= rs.getString("CICLO");
		creditos 			= rs.getString("CREDITOS");
		ht 					= rs.getString("HT");
		Hp 					= rs.getString("HP");
		Hi 					= rs.getString("HI");
		notaAprobatoria		= rs.getString("NOTA_APROBATORIA");		
		valeucas			= rs.getString("VALEUCAS");
		numAlum				= rs.getString("NUM_ALUM");
		semanas				= rs.getString("SEMANAS");
		optativa			= rs.getString("OPTATIVA");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String cursoId ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(HI,'99') AS HI, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CURSO_ID = ? ");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, CARGA_ID, "+
				"TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID, "+
				"CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO, "+
				"TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID, "+
				"ESTADO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA, "+
				"ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO, "+
				"TO_CHAR(CICLO,'99') AS CICLO, "+
				"TO_CHAR(CREDITOS,'99.99') AS CREDITOS, "+
				"TO_CHAR(HT,'99') AS HT, "+
				"TO_CHAR(HP,'99') AS HP, "+
				"TO_CHAR(HI,'99') AS HI, "+
				"TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA, "+
				"VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA "+
				"FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND ORIGEN = 'O' ");
			ps.setString(1, cursoCargaId);			
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String cursoId, String origen) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"
					+ " CURSO_CARGA_ID, CARGA_ID,"
					+ " TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID,"
					+ " CODIGO_PERSONAL, NOMBRE, CARRERA_ID, GRUPO,"
					+ " TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID,"
					+ " ESTADO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"
					+ " ORIGEN, PLAN_ID, CURSO_ID, NOMBRE_CURSO,"
					+ " TO_CHAR(CICLO,'99') AS CICLO,"
					+ " TO_CHAR(CREDITOS,'99.99') AS CREDITOS,"
					+ " TO_CHAR(HT,'99') AS HT,"
					+ " TO_CHAR(HP,'99') AS HP,"
					+ " TO_CHAR(HI,'99') AS HI,"
					+ " TO_CHAR(NOTA_APROBATORIA,'99') AS NOTA_APROBATORIA,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA"
					+ " FROM ENOC.CARGA_ACADEMICA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND CURSO_ID = ?"
					+ " AND ORIGEN = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			ps.setString(3, origen);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CURSO_CARGA_ID = ? AND CURSO_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static boolean esOfertada(Connection conn, String cargaId, String cursoId, String modalidadId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_ACADEMICA "+ 
				"WHERE CARGA_ID = ?" +
				" AND CURSO_ID = ?" +
				" AND MODALIDAD_ID = TO_NUMBER(?, '999')");
			ps.setString(1, cargaId);
			ps.setString(2, cursoId);
			ps.setString(3, modalidadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|esOfertada|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreMateria(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 	rs			= null;
		String materia			= "";	
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CURSO FROM ENOC.CARGA_ACADEMICA " + 
					"WHERE CURSO_CARGA_ID = ? " +
					"AND ORIGEN = 'O'");
			ps.setString(1, cursoCargaId);			
			
			rs = ps.executeQuery();
			if (rs.next())
				materia = rs.getString("NOMBRE_CURSO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|getNombreMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return materia;
	}
	
	public static String getOptativa(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 	rs			= null;
		String materia			= "";
		
		try{
			ps = conn.prepareStatement("SELECT OPTATIVA FROM ENOC.CARGA_ACADEMICA " + 
					"WHERE CURSO_CARGA_ID = ? " +
					"AND ORIGEN = 'O'");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				materia = rs.getString("OPTATIVA");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|getOptativa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return materia;
	}
	
	public ArrayList<CargaAcademica> getMateriasDeFacultad(Connection conn, String cargaId, String facultadId ) throws SQLException{
		ArrayList<CargaAcademica> lisMaterias	= new ArrayList<CargaAcademica>();
		PreparedStatement ps	= null;
		ResultSet 	rs			= null;
		
		try{
			ps = conn.prepareStatement(" SELECT * FROM ENOC.CARGA_ACADEMICA "
					+ " WHERE CARGA_ID = ? "
					+ " AND ENOC.FACULTAD(CARRERA_ID) = ? "
					+ " AND ORIGEN = 'O' ");
			ps.setString(1, cargaId);
			ps.setString(2, facultadId);
			
			rs = ps.executeQuery();
			while (rs.next()){
				
				CargaAcademica materia = new CargaAcademica();
				materia.mapeaReg(rs);
				lisMaterias.add(materia);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.CargaAcademica|getMateriasDeFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return lisMaterias;
	}
	
	
}