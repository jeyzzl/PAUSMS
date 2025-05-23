package aca.rotaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RotHospital {

	private String hospitalId;
	private String hospitalNombre;
	private String hospitalCorto;
	private String institucionId;
	private String calle;
	private String colonia;
	private String munEdo;
	private String pais;
	private String telefono;
	private String fax;
	private String medico;
	private String puesto;	
	private String saludo;
	
	public RotHospital(){		
		hospitalId		= "";
		hospitalNombre	= "";
		hospitalCorto	= "";
		institucionId 	= "";
		calle			= "";
		colonia			= "";
		munEdo			= "";
		pais			= "";
		telefono		= "";
		fax				= "";
		medico			= "";
		puesto			= "";
		saludo			= "";
	}
	
	

	public String getHospitalId() {
		return hospitalId;
	}



	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}



	public String getHospitalNombre() {
		return hospitalNombre;
	}



	public void setHospitalNombre(String hospitalNombre) {
		this.hospitalNombre = hospitalNombre;
	}



	public String getHospitalCorto() {
		return hospitalCorto;
	}



	public void setHospitalCorto(String hospitalCorto) {
		this.hospitalCorto = hospitalCorto;
	}



	public String getInstitucionId() {
		return institucionId;
	}



	public void setInstitucionId(String institucionId) {
		this.institucionId = institucionId;
	}



	public String getCalle() {
		return calle;
	}



	public void setCalle(String calle) {
		this.calle = calle;
	}



	public String getColonia() {
		return colonia;
	}



	public void setColonia(String colonia) {
		this.colonia = colonia;
	}



	public String getMunEdo() {
		return munEdo;
	}



	public void setMunEdo(String munEdo) {
		this.munEdo = munEdo;
	}



	public String getPais() {
		return pais;
	}



	public void setPais(String pais) {
		this.pais = pais;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public String getFax() {
		return fax;
	}



	public void setFax(String fax) {
		this.fax = fax;
	}



	public String getMedico() {
		return medico;
	}



	public void setMedico(String medico) {
		this.medico = medico;
	}



	public String getPuesto() {
		return puesto;
	}



	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}



	public String getSaludo() {
		return saludo;
	}



	public void setSaludo(String saludo) {
		this.saludo = saludo;
	}


	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROT_HOSPITAL(HOSPITAL_ID, HOSPITAL_NOMBRE, HOSPITAL_CORTO, " + 
					" INSTITUCION_ID, CALLE, COLONIA, MUN_EDO, PAIS, TELEFONO, FAX, MEDICO, PUESTO, SALUDO) " +
					" VALUES(TO_NUMBER(?,'999'),?,?,TO_NUMBER(?,'99'),?,?,?,?,?,?,?,?,?)");
			ps.setString(1,hospitalId);
			ps.setString(2,hospitalNombre);
			ps.setString(3,hospitalCorto);		
			ps.setString(4,institucionId);
			ps.setString(5,calle);
			ps.setString(6,colonia);
			ps.setString(7,munEdo);
			ps.setString(8,pais);
			ps.setString(9,telefono);
			ps.setString(10,fax);
			ps.setString(11,medico);
			ps.setString(12,puesto);
			ps.setString(13,saludo);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospitales|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROT_HOSPITAL SET HOSPITAL_NOMBRE = ? , " +
					" HOSPITAL_CORTO =  ?, INSTITUCION_ID = TO_NUMBER(?,'99'), CALLE = ?, COLONIA = ?," +
					" MUN_EDO = ?, PAIS = ?, TELEFONO = ?, FAX = ?, MEDICO = ?, PUESTO = ?, SALUDO = ?  " +
					" WHERE HOSPITAL_ID = ?");			
			
			ps.setString(1,hospitalNombre);
			ps.setString(2,hospitalCorto);		
			ps.setString(3,institucionId);
			ps.setString(4,calle);
			ps.setString(5,colonia);
			ps.setString(6,munEdo);
			ps.setString(7,pais);
			ps.setString(8,telefono);
			ps.setString(9,fax);
			ps.setString(10,medico);
			ps.setString(11,puesto);
			ps.setString(12,saludo);
			ps.setString(13,hospitalId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ?"); 
			ps.setString(1,hospitalId);			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		hospitalId 		= rs.getString("HOSPITAL_ID");
		hospitalNombre  = rs.getString("HOSPITAL_NOMBRE");
		hospitalCorto	= rs.getString("HOSPITAL_CORTO");				
		institucionId	= rs.getString("INSTITUCION_ID");
		calle			= rs.getString("CALLE");
		colonia			= rs.getString("COLONIA");
		munEdo			= rs.getString("MUN_EDO");
		pais			= rs.getString("PAIS");
		telefono		= rs.getString("TELEFONO");
		fax				= rs.getString("FAX");
		medico			= rs.getString("MEDICO");
		puesto			= rs.getString("PUESTO");
		saludo			= rs.getString("SALUDO");
	}
	
	public void mapeaRegId(Connection con, String hospitalId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT HOSPITAL_ID, HOSPITAL_NOMBRE, HOSPITAL_CORTO, " +
					" INSTITUCION_ID, CALLE, COLONIA, MUN_EDO, PAIS, TELEFONO, FAX, MEDICO, PUESTO, SALUDO" +
					" FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ? "); 
			ps.setString(1,hospitalId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = ? "); 
			ps.setString(1, hospitalId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeInstitucion(Connection conn, String institucionId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = ? "); 
			ps.setString(1, institucionId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|existeInstitucion|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(HOSPITAL_ID)+1 MAXIMO FROM ENOC.ROT_HOSPITAL"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getNombre(Connection conn, String hospitalId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT HOSPITAL_NOMBRE FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = "+hospitalId+" "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("HOSPITAL_NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static HashMap<String,String> getNombres(Connection conn) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT HOSPITAL_ID, HOSPITAL_NOMBRE FROM ENOC.ROT_HOSPITAL ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("HOSPITAL_ID");
				map.put(llave, rs.getString("HOSPITAL_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|getNombres|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}	

	public static String getNombreCorto(Connection conn, String hospitalId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT HOSPITAL_CORTO FROM ENOC.ROT_HOSPITAL WHERE HOSPITAL_ID = "+hospitalId+" "); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("HOSPITAL_CORTO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotHospital|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
}
