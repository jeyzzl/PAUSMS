package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CargaPlanEval {
	private String cursoCargaId;	
	private String evaluacionId;
	private String evaluacionNombre;
	private String fecha;
	private String valor;
	private String actividadId;
		
	public CargaPlanEval(){
	    cursoCargaId        = "";	
	    evaluacionId        = "";
	    evaluacionNombre    = "";
	    fecha               = "";
		valor               = "";
		actividadId 		= ""; 
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(String evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public String getEvaluacionNombre() {
		return evaluacionNombre;
	}

	public void setEvaluacionNombre(String evaluacionNombre) {
		this.evaluacionNombre = evaluacionNombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}	

	/**
	 * @return the actividadId
	 */
	public String getActividadId() {
		return actividadId;
	}

	/**
	 * @param actividadId the actividadId to set
	 */
	public void setActividadId(String actividadId) {
		this.actividadId = actividadId;
	}	
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		evaluacionId 		= rs.getString("EVALUACION_ID");
		evaluacionNombre 	= rs.getString("EVALUACION_NOMBRE");
		fecha 			    =  rs.getString("FECHA");
		valor 			    =  rs.getString("VALOR");
		actividadId 	    =  rs.getString("ACTIVIDAD_ID");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, EVALUACION_ID," +
					" EVALUACION_NOMBRE, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, VALOR, ACTIVIDAD_ID " +
					" FROM ENOC.CARGA_PLAN_EVAL " + 
					" WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaPlanEval|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
	}
	
}