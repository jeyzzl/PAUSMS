	package aca.rotaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RotProgramacion {

	private String programacionId;
	private String hospitalId;
	private String especialidadId;
	private String codigoPersonal;
	private String fInicio;
	private String fFinal;
	private String inscripcion;
	private String pago;
	private String anual;
	private String integrada;
	
	public RotProgramacion(){	
		programacionId		= "";
		hospitalId			= "";
		especialidadId		= "";
		codigoPersonal 		= "";
		fInicio				= "";
		fFinal				= "";
		inscripcion			= "";
		pago				= "";
		anual				= "";
		integrada			= "";
		
	}

	public String getProgramacionId() {
		return programacionId;
	}
	
	public void setProgramacionId(String programacionId) {
		this.programacionId = programacionId;
	}
	
	public String getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
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

	public String getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(String inscripcion) {
		this.inscripcion = inscripcion;
	}


	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getAnual() {
		return anual;
	}

	public void setAnual(String anual) {
		this.anual = anual;
	}

	public String getIntegrada() {
		return integrada;
	}

	public void setIntegrada(String integrada) {
		this.integrada = integrada;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROT_PROGRAMACION(PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL, " + 
					" F_INICIO, F_FINAL, INSCRIPCION, PAGO, ANUAL, INTEGRADA) " +
					" VALUES(TO_NUMBER(?,'9999999'),TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),?, TO_DATE(?,'DD/MM/YYYY')," +
					" TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'999999.99'), TO_NUMBER(?,'999999.99'))");
			ps.setString(1,programacionId);
			ps.setString(2,hospitalId);		
			ps.setString(3,especialidadId);
			ps.setString(4,codigoPersonal);
			ps.setString(5,fInicio);
			ps.setString(6,fFinal);
			ps.setString(7,inscripcion);
			ps.setString(8,pago);
			ps.setString(9,anual);
			ps.setString(10,integrada);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROT_PROGRAMACION SET HOSPITAL_ID = TO_NUMBER(?,'999') , " +
					" ESPECIALIDAD_ID =  TO_NUMBER(?,'999'), CODIGO_PERSONAL = ?, F_INICIO = TO_DATE(?,'DD/MM/YYYY'), F_FINAL = TO_DATE(?,'DD/MM/YYYY')," +
					" INSCRIPCION = TO_NUMBER(?,'99999.99'), PAGO = TO_NUMBER(?,'99999.99'), ANUAL = TO_NUMBER(?,'999999.99')," +
					" INTEGRADA = TO_NUMBER(?,'999999.99') " +
					" WHERE PROGRAMACION_ID = TO_NUMBER(?,'9999999')  ");			
			
			ps.setString(1,hospitalId);
			ps.setString(2,especialidadId);		
			ps.setString(3,codigoPersonal);
			ps.setString(4,fInicio);
			ps.setString(5,fFinal);
			ps.setString(6,inscripcion);
			ps.setString(7,pago);
			ps.setString(8,anual);
			ps.setString(9,integrada);
			ps.setString(10,programacionId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? "); 
			ps.setString(1,programacionId);		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		programacionId	  	= rs.getString("PROGRAMACION_ID");
		hospitalId		  	= rs.getString("HOSPITAL_ID");
		especialidadId  	= rs.getString("ESPECIALIDAD_ID");
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");				
		fInicio				= rs.getString("F_INICIO");
		fFinal				= rs.getString("F_FINAL");
		inscripcion			= rs.getString("INSCRIPCION");
		pago				= rs.getString("PAGO");
		anual				= rs.getString("ANUAL");
		integrada			= rs.getString("INTEGRADA");
	}
	
	public void mapeaRegId(Connection con, String programacionId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? "); 
			ps.setString(1,programacionId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_PROGRAMACION WHERE PROGRAMACION_ID = ? "); 
			ps.setString(1, especialidadId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(PROGRAMACION_ID)+1) AS MAXIMO FROM ENOC.ROT_PROGRAMACION");

			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public boolean existeProgramacion(Connection conn, String hospitalId, String especialidadId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = ? AND ESPECIALIDAD_ID = ? "); 
			ps.setString(1, hospitalId);
			ps.setString(2, especialidadId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacion|existeProgramacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}
