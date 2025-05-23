// Bean de Alum_Foto
package  aca.cultural;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnuariosEntregados{
	private String ejercicioId;
	private String matricula;
	private String nombre;
	private String fecha;
	private String usuario;
	private String carrera;
	private String semestre;
	
		
	public AnuariosEntregados(){
		ejercicioId		="";
		matricula		="";
		nombre			="";
		fecha			="";
		usuario 		="";
		carrera 		="";
		semestre 		="";
	}	
	
	public String getEjercicioId() {
		return ejercicioId;
	}

	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		ejercicioId			= rs.getString("EJERCICIO_ID");
		matricula			= rs.getString("MATRICULA");
		nombre				= rs.getString("NOMBRE");
		fecha				= rs.getString("FECHA");
		usuario 			= rs.getString("USUARIO");
		carrera 			= rs.getString("CARRERA");
		semestre 			= rs.getString("SEMESTRE");
	}
	
	public void mapeaRegId( Connection conn, String ejercicioId, String matricula) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EJERICIO_ID, MATRICULA, NOMBRE, TO_CHAR(FECHA, 'DD/MM/YYYY/HH24:MI') AS FECHA, USUARIO, CARRERA, SEMESTRE"+
			" FROM ENOC.ANUARIOS_ENTREGADOS"+ 
			" WHERE EJERCICIO_ID = ? AND MATRICULA = ? ");
		ps.setString(1, ejercicioId);
		ps.setString(2, matricula);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.AnuariosEntregadosUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
	}
}