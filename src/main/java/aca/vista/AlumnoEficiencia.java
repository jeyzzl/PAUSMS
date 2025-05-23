package aca.vista;

import java.sql.*;

public class AlumnoEficiencia {
	private String codigoPersonal;
	private String cursoCargaId;
	private String evaluacionId;
	private String tipo;
	private String valor;
	private String nota;	
	private String evaluadas;
		
	public AlumnoEficiencia(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		evaluacionId	= "";
		tipo			= "";
		valor			= "";
		nota			= "";
		evaluadas		= "";
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
	 * @return the evaluacionId
	 */
	public String getEvaluacionId() {
		return evaluacionId;
	}

	/**
	 * @param evaluacionId the evaluacionId to set
	 */
	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
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
	 * @return the evaluadas
	 */
	public String getEvaluadas() {
		return evaluadas;
	}

	/**
	 * @param evaluadas the evaluadas to set
	 */
	public void setEvaluadas(String evaluadas) {
		this.evaluadas = evaluadas;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		cursoCargaId		= rs.getString("CURSO_CARGA_ID");
		evaluacionId 		= rs.getString("EVALUACION_ID");
		tipo				= rs.getString("TIPO");
		valor				= rs.getString("VALOR");
		nota				= rs.getString("NOTA");
		evaluadas 			= rs.getString("EVALUADAS");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, "+
					"CURSO_CARGA_ID, EVALUACION_ID, TIPO, VALOR, NOTA,"+ 
					"EVALUADAS "+
					"FROM ENOC.ALUMNO_EFICIENCIA "+
					"WHERE CODIGO_PERSONAL = ? " +
					"AND CURSO_CARGA_ID = ?" +
					"AND EVALUACION_ID");
			ps.setString(1, codigoPersonal);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficiencia|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_EFICIENCIA "+
			"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEficiencia|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}