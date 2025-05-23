// Bean del Catalogo de Bloques
package  aca.carga;
import java.sql.*;

public class CargaGrupoEvaluacion{
	private String cursoCargaId;
	private String evaluacionId;
	private String nombreEvaluacion;
	private String fecha;
	private String estrategiaId;	
	private String valor;
	private String tipo;
	private String evaluacionE42;
	
	public CargaGrupoEvaluacion(){
		cursoCargaId		= "";
		evaluacionId		= "";
		nombreEvaluacion	= "";
		fecha				= "";
		estrategiaId		= "";
		valor				= "";
		tipo				= "";
		evaluacionE42		= "";
	}
	
	public String getEvaluacionE42() {
		return evaluacionE42;
	}

	public void setEvaluacionE42(String evaluacionE42) {
		this.evaluacionE42 = evaluacionE42;
	}

	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getEvaluacionId(){
		return evaluacionId;
	}
	
	public void setEvaluacionId( String evaluacionId){
		this.evaluacionId = evaluacionId;
	}	
	
	public String getNombreEvaluacion(){
		return nombreEvaluacion;
	}
	
	public void setNombreEvaluacion( String nombreEvaluacion){
		this.nombreEvaluacion = nombreEvaluacion;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public void setFecha( String fecha){
		this.fecha = fecha;
	}
	
	public String getEstrategiaId(){
		return estrategiaId;
	}
	
	public void setEstrategiaId( String estrategiaId){
		this.estrategiaId = estrategiaId;
	}
	
	public String getValor(){
		return valor;
	}
	
	public void setValor( String valor){
		this.valor = valor;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo( String tipo){
		this.tipo = tipo;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		evaluacionId 		= rs.getString("EVALUACION_ID");
		nombreEvaluacion	= rs.getString("NOMBRE_EVALUACION")==null?"X":rs.getString("NOMBRE_EVALUACION");
		fecha	 			= rs.getString("FECHA");
		estrategiaId 		= rs.getString("ESTRATEGIA_ID");
		valor 				= rs.getString("VALOR");
		tipo 				= rs.getString("TIPO");
		evaluacionE42		= rs.getString("EVALUACION_E42");
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String evaluacionId ) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
	
	}
}