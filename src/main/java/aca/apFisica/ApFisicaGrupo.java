// Clase para la tabla de Modulo
package aca.apFisica;
import java.sql.*;

public class ApFisicaGrupo{	
	private String grupoID;
	private String nombreGrupo;
	private String lugar;
	private String instructor;
	private String cupo;
	private String fInicio;
	private String fFinal;
	private String dia1;
	private String cargas;
	private String clave;
	private String descripcion;
	private String hora;
	private String fCierre;
	private String acceso;
	private String liga;
	
	// Constructor
	public ApFisicaGrupo(){
		grupoID			= "0";
		nombreGrupo		= "-";
		lugar			= "-";
		instructor		= "-";
		cupo			= "0";
		fInicio			= "";
		fFinal			= "";
		dia1			= "0";
		cargas			= "-";
		clave			= "";
		descripcion		= "-";
		hora			= "0";
		fCierre			= "";
		acceso			= "";
		liga 			= "-";
	}
	
	public String getGrupoID() {
		return grupoID;
	}

	public void setGrupoID(String grupoID) {
		this.grupoID = grupoID;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getCupo() {
		return cupo;
	}

	public void setCupo(String cupo) {
		this.cupo = cupo;
	}

	public String getfInicio() {
		return fInicio;
	}

	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	public String getfFinal() {
		return fFinal;
	}

	public void setfFinal(String fFinal) {
		this.fFinal = fFinal;
	}

	public String getDia1() {
		return dia1;
	}

	public void setDia1(String dia1) {
		this.dia1 = dia1;
	}
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}	

	public String getCargas() {
		return cargas;
	}

	public void setCargas(String cargas) {
		this.cargas = cargas;
	}
	
	public String getfCierre() {
		return fCierre;
	}

	public void setfCierre(String fCierre) {
		this.fCierre = fCierre;
	}

	public String getAcceso() {
		return acceso;
	}

	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}
	
	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		grupoID			= rs.getString("GRUPO_ID");
		nombreGrupo		= rs.getString("NOMBRE_GRUPO");
		lugar			= rs.getString("LUGAR");
		instructor		= rs.getString("INSTRUCTOR");
		cupo			= rs.getString("CUPO");
		fInicio			= rs.getString("F_INICIO");
		fFinal			= rs.getString("F_FINAL");
		dia1			= rs.getString("DIA1");
		cargas			= rs.getString("CARGAS");
		clave			= rs.getString("CLAVE");
		descripcion		= rs.getString("DESCRIPCION");
		hora			= rs.getString("HORA");
		fCierre			= rs.getString("F_CIERRE");
		acceso			= rs.getString("ACCESO");
		liga			= rs.getString("LIGA");
	}
	
	public void mapeaRegId(Connection conn, String grupoID) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA"
					+ " FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = '"+grupoID+"' "); 
			rs = ps.executeQuery();			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
 			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|mapeaRegId|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
	}
}