 // BEANS DE LA TABLA COND_ALUMNO
 
package aca.disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CondAlumno {
	private String matricula;	
	private String periodoId;
	private String folio;
	private String idReporte;	
	private String idLugar;
	private String idJuez;
	private String empleado;
	private String fecha;
	private String cantidad;
	private String comentario;
	
	public CondAlumno(){
		matricula 	= "";
		periodoId  	= "";
		folio	  	= "";
		idReporte 	= "";
		idLugar  	= "";
		idJuez	  	= "";
		empleado 	= "";
		fecha  		= "";
		cantidad	= "0";
		comentario	= "";
	}
	
	public String getMatricula(){
		return matricula;
	}
	
	public void setMatricula( String matricula){
		this.matricula = matricula;
	}
	
	public String getPeriodoId(){
		return periodoId;
	}
	
	public void setPeriodoId( String periodoId){
		this.periodoId = periodoId;
	}
	
	public String getFolio(){
		return folio;
	}
	
	public void setFolio( String folio){
		this.folio = folio;
	}
	public String getIdReporte(){
		return idReporte;
	}
	
	public void setIdReporte( String idReporte){
		this.idReporte = idReporte;
	}
	
	public String getIdLugar(){
		return idLugar;
	}
	
	public void setIdLugar( String idLugar){
		this.idLugar = idLugar;
	}
	
	public String getIdJuez(){
		return idJuez;
	}
	
	public void setIdJuez( String idJuez){
		this.idJuez = idJuez;
	}
	public String getEmpleado(){
		return empleado;
	}
	
	public void setEmpleado( String empleado){
		this.empleado = empleado;
	}
	
	public String getFecha(){
		return fecha;
	}
	
	public void setFecha( String fecha){
		this.fecha = fecha;
	}
	
	public String getCantidad(){
		return cantidad;
	}
	
	public void setCantidad( String cantidad){
		this.cantidad = cantidad;
	}
	public String getComentario(){
		return comentario;
	}
	
	public void setComentario( String comentario){
		this.comentario = comentario;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula 	= rs.getString("MATRICULA");
		periodoId	= rs.getString("PERIODO_ID");
		folio	 	= rs.getString("FOLIO");
		idReporte 	= rs.getString("IDREPORTE");
		idLugar	 	= rs.getString("IDLUGAR");
		idJuez	 	= rs.getString("IDJUEZ");
		empleado 	= rs.getString("EMPLEADO");
		fecha	 	= rs.getString("FECHA");
		cantidad	= rs.getString("CANTIDAD");
		comentario	= rs.getString("COMENTARIO");
	}
	
	public void mapeaRegId(Connection con, String matricula, String folio, String periodoId) throws SQLException{		
		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT MATRICULA, PERIODO_ID, FOLIO, " +
					"IDREPORTE, IDLUGAR, IDJUEZ, EMPLEADO, TO_CHAR(FECHA, 'DD/MM/YYYY') FECHA, " +
					"CANTIDAD, COMENTARIO FROM ENOC.COND_ALUMNO " + 
					"WHERE MATRICULA = ? AND FOLIO = ? AND PERIODO_ID = ?");				
			ps.setString(1, matricula);
			ps.setString(2, folio);
			ps.setString(3, periodoId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
}