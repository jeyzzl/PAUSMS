package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmPadres {
	private String folio;
	private String padreNombre;
	private String padreApellido;
	private String padreReligion;
	private String padreNacionalidad;
	private String padreOcupacion;
	private String madreNombre;
	private String madreApellido;
	private String madreReligion;
	private String madreNacionalidad;
	private String madreOcupacion;
	
	public AdmPadres(){
		folio 			 = "";
		padreNombre		 = "";
		padreApellido 	 = "";
		padreReligion	 = "";
		padreNacionalidad= "";
		padreOcupacion	 = "";
		madreNombre		 = "";
		madreApellido	 = "";
		madreReligion	 = "";
		madreNacionalidad= "";
		madreOcupacion	 = "";
	}


	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getPadreNombre() {
		return padreNombre;
	}

	public void setPadreNombre(String padreNombre) {
		this.padreNombre = padreNombre;
	}

	public String getPadreApellido() {
		return padreApellido;
	}

	public void setPadreApellido(String padreApellido) {
		this.padreApellido = padreApellido;
	}

	public String getPadreReligion() {
		return padreReligion;
	}

	public void setPadreReligion(String padreReligion) {
		this.padreReligion = padreReligion;
	}

	public String getPadreNacionalidad() {
		return padreNacionalidad;
	}

	public void setPadreNacionalidad(String padreNacionalidad) {
		this.padreNacionalidad = padreNacionalidad;
	}

	public String getPadreOcupacion() {
		return padreOcupacion;
	}

	public void setPadreOcupacion(String padreOcupacion) {
		this.padreOcupacion = padreOcupacion;
	}

	public String getMadreNombre() {
		return madreNombre;
	}

	public void setMadreNombre(String madreNombre) {
		this.madreNombre = madreNombre;
	}

	public String getMadreApellido() {
		return madreApellido;
	}

	public void setMadreApellido(String madreApellido) {
		this.madreApellido = madreApellido;
	}

	public String getMadreReligion() {
		return madreReligion;
	}

	public void setMadreReligion(String madreReligion) {
		this.madreReligion = madreReligion;
	}

	public String getMadreNacionalidad() {
		return madreNacionalidad;
	}

	public void setMadreNacionalidad(String madreNacionalidad) {
		this.madreNacionalidad = madreNacionalidad;
	}

	public String getMadreOcupacion() {
		return madreOcupacion;
	}

	public void setMadreOcupacion(String madreOcupacion) {
		this.madreOcupacion = madreOcupacion;
	}


	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_PADRES"+ 
				"(FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD, PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, " +
				" MADRE_NACIONALIDAD, MADRE_OCUPACION ) "+
				"VALUES( TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ?, ?, ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'999'), ? )");
			ps.setString(1, folio);
			ps.setString(2, padreNombre);
			ps.setString(3, padreApellido);
			ps.setString(4, padreReligion);
			ps.setString(5, padreNacionalidad);
			ps.setString(6, padreOcupacion);
			ps.setString(7, madreNombre);
			ps.setString(8, madreApellido);
			ps.setString(9, madreReligion);
			ps.setString(10, madreNacionalidad);
			ps.setString(11, madreOcupacion);

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadres|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_PADRES " + 
					" SET PADRE_NOMBRE = ?, " +
					" PADRE_APELLIDO = ?, " +
					" PADRE_RELIGION = TO_NUMBER(?,'99'), " +
					" PADRE_NACIONALIDAD = TO_NUMBER(?,'999'), " +	
					" PADRE_OCUPACION = ?, " +	
					" MADRE_NOMBRE = ?, " +
					" MADRE_APELLIDO = ?, " +
					" MADRE_RELIGION = TO_NUMBER(?,'99'), " +
					" MADRE_NACIONALIDAD = TO_NUMBER(?,'999'), " +	
					" MADRE_OCUPACION = ? " +			
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1, padreNombre);
			ps.setString(2, padreApellido);
			ps.setString(3, padreReligion);
			ps.setString(4, padreNacionalidad);
			ps.setString(5, padreOcupacion);
			ps.setString(6, madreNombre);
			ps.setString(7, madreApellido);
			ps.setString(8, madreReligion);
			ps.setString(9, madreNacionalidad);
			ps.setString(10, madreOcupacion);
			ps.setString(11, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadres|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_PADRES "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmPadres|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			  = rs.getString("FOLIO");
		padreNombre		  = rs.getString("PADRE_NOMBRE");
		padreApellido     = rs.getString("PADRE_APELLIDO");
		padreReligion	  = rs.getString("PADRE_RELIGION");
		padreNacionalidad = rs.getString("PADRE_NACIONALIDAD");
		padreOcupacion    = rs.getString("PADRE_OCUPACION");
		madreNombre		  = rs.getString("MADRE_NOMBRE");
		madreApellido     = rs.getString("MADRE_APELLIDO");
		madreReligion	  = rs.getString("MADRE_RELIGION");
		madreNacionalidad = rs.getString("MADRE_NACIONALIDAD");
		madreOcupacion    = rs.getString("MADRE_OCUPACION");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, PADRE_NOMBRE, PADRE_APELLIDO, PADRE_RELIGION, PADRE_NACIONALIDAD," +
				" PADRE_OCUPACION, MADRE_NOMBRE, MADRE_APELLIDO, MADRE_RELIGION, MADRE_NACIONALIDAD, MADRE_OCUPACION " +
				" FROM SALOMON.ADM_PADRES "+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
		ps.setString(1, folio);		
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_PADRES "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
						
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}