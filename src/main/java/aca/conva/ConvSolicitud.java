package aca.conva;
//Bean de datos academicos del alumno
import java.sql.*;

public class ConvSolicitud{
	
	//private String convalidacionId;
	private String codigoPersonal;
	private String cursoId;
	private String usuario;
	private String fecha;
	private String tipo;	
	private String procesoId;
	private String carrera;
	private String nota;
	private String materia_O;
	private String creditos_O;
	private String nota_O;
	
	public ConvSolicitud(){
		codigoPersonal		= "";
		cursoId				= "";
		usuario				= "";
		fecha				= "";
		tipo				= "";	
		procesoId			= "";
		carrera				= "";
		nota				= "";
		materia_O			= "";
		creditos_O			= "";
		nota_O				= "";
	}
	
	/**
	 * @return Returns the carrera.
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera The carrera to set.
	 */
	
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	
	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(String procesoId) {
		this.procesoId = procesoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

		public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public String getCreditos_O() {
		return creditos_O;
	}

	public void setCreditos_O(String creditos_O) {
		this.creditos_O = creditos_O;
	}

	public String getMateria_O() {
		return materia_O;
	}

	public void setMateria_O(String materia_O) {
		this.materia_O = materia_O;
	}

	public String getNota_O() {
		return nota_O;
	}

	public void setNota_O(String nota_O) {
		this.nota_O = nota_O;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		cursoId 			= rs.getString("CURSO_ID");
		usuario 			= rs.getString("USUARIO");
		fecha	 			= rs.getString("FECHA");
		tipo 				= rs.getString("TIPO");		
		procesoId			= rs.getString("PROCESO_ID");
		carrera				= rs.getString("CARRERA");
		nota				= rs.getString("NOTA");
		materia_O			= rs.getString("MATERIA_O");
		creditos_O			= rs.getString("CREDITOS_O");
		nota_O				= rs.getString("NOTA_0");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, CARRERA, NOTA, MATERIA_O, CREDITOS_O, NOTA_O"+
				"FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ? " +
				"AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
		
	}

}