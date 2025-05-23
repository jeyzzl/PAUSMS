package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmTutor {
	private String folio;
	private String tutor;
	private String nombre;
	private String calle;
	private String numero;
	private String colonia;
	private String codigoPostal;
	private String paisId;
	private String estadoId;
	private String ciudadId;
	private String telefono;
	private String estado;
	private String ciudad;
	
	public AdmTutor(){
		folio 		= "";
		tutor		= "";
		nombre	 	= "";
		calle		= "";
		numero		= "";
		colonia		= "";
		codigoPostal= "";
		paisId		= "";
		estadoId	= "";
		ciudadId  	= "";
		telefono	= "";
		estado		= "";
		ciudad  	= "";
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPaisId() {
		return paisId;
	}

	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public String getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(String estadoId) {
		this.estadoId = estadoId;
	}

	public String getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(String ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	

	/**
	 * @return the tutor
	 */
	public String getTutor() {
		return tutor;
	}

	/**
	 * @param tutor the tutor to set
	 */
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (estado.equals("")||estado==null) estado = " ";
			if (ciudad.equals("")||ciudad==null) ciudad = " ";
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_TUTOR"+ 
				"(FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD) "+
				" VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9'), ?, ?, ?, ?, ?, " +
				" TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?,?, ? )");
			ps.setString(1, folio);
			ps.setString(2, tutor);
			ps.setString(3, nombre);
			ps.setString(4, calle);
			ps.setString(5, numero);
			ps.setString(6, colonia);
			ps.setString(7, codigoPostal);
			ps.setString(8, paisId);
			ps.setString(9, estadoId);
			ps.setString(10, ciudadId);
			ps.setString(11, telefono);
			ps.setString(12, estado);
			ps.setString(13, ciudad);

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutor|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_TUTOR SET " +
					" TUTOR = TO_NUMBER(?,'9')," +		
					" NOMBRE = ?, " +
					" CALLE = ?, " +
					" NUMERO = ?, " +		
					" COLONIA = ?, " +		
					" CODIGOPOSTAL = ?, " +		
					" PAIS_ID = TO_NUMBER(?,'999'), " +		
					" ESTADO_ID = TO_NUMBER(?,'999'), " +	
					" CIUDAD_ID = TO_NUMBER(?,'999'), " +
					" TELEFONO = ?, " +
					" ESTADO = ?, " +
					" CIUDAD = ? " +			
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1,  tutor);
			ps.setString(2,  nombre);
			ps.setString(3,  calle);
			ps.setString(4,  numero);
			ps.setString(5,  colonia);
			ps.setString(6,  codigoPostal);
			ps.setString(7,  paisId);
			ps.setString(8,  estadoId);
			ps.setString(9,  ciudadId);
			ps.setString(10, telefono);
			ps.setString(11,  estado);
			ps.setString(12, ciudad);
			ps.setString(13, folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutor|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_TUTOR "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmTutor|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		tutor		    = rs.getString("TUTOR");
		nombre		    = rs.getString("NOMBRE");
		calle			= rs.getString("CALLE");
		numero		    = rs.getString("NUMERO");
		colonia		    = rs.getString("COLONIA");
		codigoPostal    = rs.getString("CODIGOPOSTAL");
		paisId		    = rs.getString("PAIS_ID");
		estadoId	    = rs.getString("ESTADO_ID");
		ciudadId	    = rs.getString("CIUDAD_ID");
		telefono	    = rs.getString("TELEFONO");
		estado		    = rs.getString("ESTADO");
		ciudad		    = rs.getString("CIUDAD");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, TUTOR, NOMBRE, CALLE, NUMERO, COLONIA, CODIGOPOSTAL, " +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, TELEFONO, ESTADO, CIUDAD " +
				" FROM SALOMON.ADM_TUTOR "+ 
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_TUTOR "+ 
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