package aca.form;

import java.sql.*;

public class FormReingreso {
	private String cargaId;
	private String codigoPersonal;
	private String reingreso;
	private String razones;
	private String obstaculos;
	private String residencia;
	private String planes;
	private String cargaAct;
	private String cargaPro;
	private String fecha;
	private String mentorId;
	private String direccion;
	
	public FormReingreso(){
		cargaId = "";
		codigoPersonal= "";
		reingreso= "";
		razones= "";
		obstaculos= "";
		residencia= "";
		planes= "";
		cargaAct= "";
		cargaPro= "";
		fecha= "";
		mentorId= "";
		direccion= "";
	}

	public String getCargaAct() {
		return cargaAct;
	}
	

	public void setCargaAct(String cargaAct) {
		this.cargaAct = cargaAct;
	}
	

	public String getCargaId() {
		return cargaId;
	}
	

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}
	

	public String getCargaPro() {
		return cargaPro;
	}
	

	public void setCargaPro(String cargaPro) {
		this.cargaPro = cargaPro;
	}
	

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	

	public String getDireccion() {
		return direccion;
	}
	

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	

	public String getFecha() {
		return fecha;
	}
	

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

	public String getMentorId() {
		return mentorId;
	}
	

	public void setMentorId(String mentorId) {
		this.mentorId = mentorId;
	}
	

	public String getObstaculos() {
		return obstaculos;
	}
	

	public void setObstaculos(String obstaculos) {
		this.obstaculos = obstaculos;
	}
	

	public String getPlanes() {
		return planes;
	}
	

	public void setPlanes(String planes) {
		this.planes = planes;
	}
	

	public String getRazones() {
		return razones;
	}
	

	public void setRazones(String razones) {
		this.razones = razones;
	}
	

	public String getReingreso() {
		return reingreso;
	}
	

	public void setReingreso(String reingreso) {
		this.reingreso = reingreso;
	}
	

	public String getResidencia() {
		return residencia;
	}
	

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}
	
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.FORM_REINGRESO (CARGA_ID, CODIGO_PERSONAL, " + 
					"REINGRESO, RAZONES, OBSTACULOS, RESIDENCIA, PLANES, " +
					"CARGAACT, CARGAPRO, FECHA, MENTOR_ID, DIRECCION) " +
					"VALUES(?,?,?,?,?,?,?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?)");
			ps.setString(1, cargaId);
			ps.setString(2, codigoPersonal);
			ps.setString(3, reingreso);
			ps.setString(4, razones);
			ps.setString(5, obstaculos);
			ps.setString(6, residencia);
			ps.setString(7, planes);
			ps.setString(8, cargaAct);
			ps.setString(9, cargaPro);
			ps.setString(10, fecha);
			ps.setString(11, mentorId);
			ps.setString(12, direccion);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormReingreso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.FORM_REINGRESO SET REINGRESO= ?, " + 
					"RAZONES= ?, OBSTACULOS= ?, RESIDENCIA= ?, PLANES= ?, " +
					"CARGAACT= ?, CARGAPRO= ?, FECHA=TO_DATE(?,'DD/MM/YYYY'), " +
					"MENTOR_ID= ?, DIRECCION= ? WHERE CODIGO_PERSONAL = ? AND CARGA_ID= ? ");
			ps.setString(1, reingreso);
			ps.setString(2, razones);
			ps.setString(3, obstaculos);
			ps.setString(4, residencia);
			ps.setString(5, planes);
			ps.setString(6, cargaAct);
			ps.setString(7, cargaPro);
			ps.setString(8, fecha);
			ps.setString(9, mentorId);
			ps.setString(10, direccion);
			ps.setString(11, codigoPersonal);
			ps.setString(12, cargaId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormReingreso|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.FORM_REINGRESO "+ 
				"WHERE CODIGO_PERSONAL= ?");
			ps.setString(1, codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormReingreso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	public void mapeaReg(ResultSet rs ) throws SQLException{
		cargaId = 			rs.getString("CARGA_ID");
		codigoPersonal= 	rs.getString("CODIGO_PERSONAL");
		reingreso= 			rs.getString("REINGRESO");
		razones= 			rs.getString("RAZONES");
		obstaculos= 		rs.getString("OBSTACULOS");
		residencia= 		rs.getString("RESIDENCIA");
		planes= 			rs.getString("PLANES");
		cargaAct= 			rs.getString("CARGAACT");
		cargaPro= 			rs.getString("CARGAPRO");
		fecha= 				rs.getString("FECHA");
		mentorId= 			rs.getString("MENTOR_ID");
		direccion= 			rs.getString("DIRECCION");
	}
	
	public void mapeaRegId( Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID, CODIGO_PERSONAL, " +
					"REINGRESO, RAZONES, OBSTACULOS, RESIDENCIA, PLANES, " +
					"CARGAACT, CARGAPRO, FECHA, MENTOR_ID, DIRECCION " +
					"FROM ENOC.FORM_REINGRESO WHERE CODIGO_PERSONAL= ? "); 
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.form.FormReingreso|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
			
	}
}