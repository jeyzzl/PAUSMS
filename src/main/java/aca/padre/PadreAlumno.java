 // Bean de datos personales del padre 
 package  aca.padre;
 
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 public class PadreAlumno{
 	private String padreId;
 	private String codigoPersonal;
 	private String fechaAlta;
 	private String fechaAutoriza;
 	private String estado;
 	
 	public PadreAlumno(){
 		padreId				= "";
 		codigoPersonal		= "";
 		fechaAlta			= "";
 		fechaAutoriza		= "";
 		estado				= "";
 	}
 	

	/**
	 * @return the padreId
	 */
	public String getPadreId() {
		return padreId;
	}

	/**
	 * @param padreId the padreId to set
	 */
	public void setPadreId(String padreId) {
		this.padreId = padreId;
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
	 * @return the fechaAlta
	 */
	public String getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the fechaAutoriza
	 */
	public String getFechaAutoriza() {
		return fechaAutoriza;
	}

	/**
	 * @param fechaAutoriza the fechaAutoriza to set
	 */
	public void setFechaAutoriza(String fechaAutoriza) {
		this.fechaAutoriza = fechaAutoriza;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}	

	
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException{
 		padreId 			= rs.getString("PADRE_ID");
 		codigoPersonal 		= rs.getString("CODIGO_PERSONAL"); 		
 		fechaAlta			= rs.getString("FECHA_ALTA");
 		fechaAutoriza		= rs.getString("FECHA_AUTORIZA");
 		estado 				= rs.getString("ESTADO"); 		
 	}
 	
 	public void mapeaRegId( Connection conn, String padreId, String codigoPersonal ) throws SQLException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT PADRE_ID, CODIGO_PERSONAL,"
	 			+ " TO_CHAR(FECHA_ALTA,'DD/MM/YYYY') AS FECHA_ALTA,"
	 			+ " TO_CHAR(FECHA_AUTORIZA,'DD/MM/YYYY') AS FECHA_AUTORIZA,"
	 			+ " ESTADO"
	 			+ " FROM ENOC.PADRE_ALUMNO"
	 			+ " WHERE PADRE_ID = ? AND CODIGO_PERSONAL = ?");
	 		ps.setString(1, padreId);
	 		ps.setString(2, codigoPersonal);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
 			System.out.println("Error - aca.padre.PadreAlumno|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 	}	
 }