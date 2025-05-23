// Clase para la vista CARGA_ACADEMICA
package  aca.vista;

import java.sql.*;

public class AlumnoEvaluacion{
	private String cursoCargaId;
	private String codigoPersonal;
	private String evaluacionId;
	private String nombreEvaluacion;
	private String estrategiaId;
	private String fecha;
	private String valor;
	private String tipo;
	private String nota;	
	
	public AlumnoEvaluacion(){
		cursoCargaId		= "";
		codigoPersonal		= "";
		evaluacionId		= "";
		nombreEvaluacion	= "";
		estrategiaId		= "";
		fecha				= "";
		valor				= "";
		tipo				= "";
		nota				= "";
	}
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
		
	public String getCodigoPersonal(){
		return codigoPersonal;
	}	
		
	public String getEvaluacionId(){
		return evaluacionId;
	}	
		
	public String getNombreEvaluacion(){
		return nombreEvaluacion;
	}	
		
	public String getEstrategiaId(){
		return estrategiaId;
	}
	
	public String getFecha(){
		return fecha;
	}	
		
	public String getValor(){
		return valor;
	}	
		
	public String getTipo(){
		return tipo;
	}
	
	public String getNota(){
		return nota;
	}	
		
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cursoCargaId 		= rs.getString("CURSO_CARGA_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		evaluacionId		= rs.getString("EVALUACION_ID");
		nombreEvaluacion	= rs.getString("NOMBRE_EVALUACION");
		estrategiaId 		= rs.getString("ESTRATEGIA_ID");
		fecha				= rs.getString("FECHA");
		valor 				= rs.getString("VALOR");
		tipo 				= rs.getString("TIPO");
		nota				= rs.getString("NOTA");		
	}
	
	public void mapeaRegId( Connection conn, String cursoCargaId, String codigoPersonal, String evaluacionId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, "+
				"CODIGO_PERSONAL, "+
				"EVALUACION_ID, "+
				"NOMBRE_EVALUACION, "+
				"ESTRATEGIA_ID, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, "+
				"VALOR, "+
				"TIPO, "+
				"NOTA "+
				"FROM ENOC.ALUMNO_EVALUACION "+
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CODIGO_PERSONAL = ? "+
				"AND TO_CHAR(EVALUACION_ID,'99') = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEvaluacion|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO "+
				"WHERE CURSO_CARGA_ID = ? "+
				"AND CODIGO_PERSONAL = ? "+
				"AND TO_CHAR(EVALUACION_ID,'99') = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.AlumnoEvaluacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}