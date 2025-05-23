package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContSaldosRip {
	private String id;
	private String version;	
	private String matricula;
	private String saldo;
	private String naturaleza;
	private String quienRegistro;
	private String cuandoRegistro;
	private String quienModifico;
	private String cuandoModifico;
	private String status;
	private String contabilidad;
	
	// Constructor
	public ContSaldosRip(){		
		id 				= "";
		version 		= "";	
		matricula 		= "";
		saldo 			= "";
		naturaleza 		= "";
		quienRegistro 	= "";
		cuandoRegistro 	= "";
		quienModifico 	= "";
		cuandoModifico 	= "";
		status 			= "";
		contabilidad 	= "";
		
	}
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}


	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	/**
	 * @return the saldo
	 */
	public String getSaldo() {
		return saldo;
	}


	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}


	/**
	 * @return the naturaleza
	 */
	public String getNaturaleza() {
		return naturaleza;
	}


	/**
	 * @param naturaleza the naturaleza to set
	 */
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}


	/**
	 * @return the quienRegistro
	 */
	public String getQuienRegistro() {
		return quienRegistro;
	}


	/**
	 * @param quienRegistro the quienRegistro to set
	 */
	public void setQuienRegistro(String quienRegistro) {
		this.quienRegistro = quienRegistro;
	}


	/**
	 * @return the cuandoRegistro
	 */
	public String getCuandoRegistro() {
		return cuandoRegistro;
	}


	/**
	 * @param cuandoRegistro the cuandoRegistro to set
	 */
	public void setCuandoRegistro(String cuandoRegistro) {
		this.cuandoRegistro = cuandoRegistro;
	}


	/**
	 * @return the quienModifico
	 */
	public String getQuienModifico() {
		return quienModifico;
	}


	/**
	 * @param quienModifico the quienModifico to set
	 */
	public void setQuienModifico(String quienModifico) {
		this.quienModifico = quienModifico;
	}


	/**
	 * @return the cuandoModifico
	 */
	public String getCuandoModifico() {
		return cuandoModifico;
	}


	/**
	 * @param cuandoModifico the cuandoModifico to set
	 */
	public void setCuandoModifico(String cuandoModifico) {
		this.cuandoModifico = cuandoModifico;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the contabilidad
	 */
	public String getContabilidad() {
		return contabilidad;
	}


	/**
	 * @param contabilidad the contabilidad to set
	 */
	public void setContabilidad(String contabilidad) {
		this.contabilidad = contabilidad;
	}


	public void mapeaReg(ResultSet rs ) throws SQLException{
		id 				= rs.getString("ID");
		version 		= rs.getString("VERSION");
		matricula 		= rs.getString("MATRICULA");
		saldo 			= rs.getString("SALDO");
		naturaleza 		= rs.getString("NATURALEZA");
		quienRegistro 	= rs.getString("QUIEN_REGISTRO");
		cuandoRegistro 	= rs.getString("CUANDO_REGISTRO");
		quienModifico 	= rs.getString("QUIEN_MODIFICO");
		cuandoModifico 	= rs.getString("CUANDO_MODIFICO");
		status 			= rs.getString("STATUS");
		contabilidad 	= rs.getString("CONTABILIDAD");		
	}	
	

	public void mapeaRegId(Connection conn, String id) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID, VERSION, MATRICULA, SALDO, NATURALEZA,"
	 				+ " QUIEN_REGISTRO, TO_CHAR(CUANDO_REGISTRO,'DD/MM/YYYY') AS CUANDO_REGISTRO,"
	 				+ " QUIEN_MODIFICO, TO_CHAR(CUANDO_MODIFICO,'DD/MM/YYYY') AS CUANDO_MODIFICO,"
	 				+ " STATUS, CONTABILIDAD"
	 				+ " FROM MATEO.CONT_CCOSTO"
	 				+ " WHERE ID = ?");
	 		ps.setString(1, id);	 			 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContSaldosRip|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public void mapeaRegId(Connection conn, String matricula, String status) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT ID, VERSION, MATRICULA, SALDO, NATURALEZA,"
	 				+ " QUIEN_REGISTRO, TO_CHAR(CUANDO_REGISTRO,'DD/MM/YYYY') AS CUANDO_REGISTRO,"
	 				+ " QUIEN_MODIFICO, TO_CHAR(CUANDO_MODIFICO,'DD/MM/YYYY') AS CUANDO_MODIFICO,"
	 				+ " STATUS, CONTABILIDAD"	 					 				
	 				+ " FROM MATEO.CONT_SALDOS_RIP"
	 				+ " WHERE MATRICULA = ? AND STATUS = ?");
	 		ps.setString(1, matricula);
	 		ps.setString(2, status);
	 		
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContSaldosRip|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public boolean existeReg(Connection conn, String matricula, String status) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA FROM MATEO.CONT_SALDOS_RIP WHERE MATRICULA = ? AND STATUS = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, status);
	 		
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContSaldosRip|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
}