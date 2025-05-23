// Bean folio la tabla folio Adm_Contacto
package  adm.alumno;

import java.sql.*;

public class AdmAlumReq{
	private String folio;
	private String prerrequisito;
	private String promLic;
	private String promMae;
	private String phca;
	private String paep;
	private String servicio;
	
	public AdmAlumReq(){
		folio 			= "";
		prerrequisito 	= "";
		promLic 		= ""; 
		promMae			= "";
		phca			= "";
		paep 			= "";
		servicio		= "";
	}
	
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}


	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}


	/**
	 * @return the prerrequisito
	 */
	public String getPrerrequisito() {
		return prerrequisito;
	}

	/**
	 * @param prerrequisito the prerrequisito to set
	 */
	public void setPrerrequisito(String prerrequisito) {
		this.prerrequisito = prerrequisito;
	}


	/**
	 * @return the promLic
	 */
	public String getPromLic() {
		return promLic;
	}

	/**
	 * @param promLic the promLic to set
	 */
	public void setPromLic(String promLic) {
		this.promLic = promLic;
	}

	/**
	 * @return the promMae
	 */
	public String getPromMae() {
		return promMae;
	}

	/**
	 * @param promMae the promMae to set
	 */
	public void setPromMae(String promMae) {
		this.promMae = promMae;
	}

	/**
	 * @return the phca
	 */
	public String getPhca() {
		return phca;
	}

	/**
	 * @param phca the phca to set
	 */
	public void setPhca(String phca) {
		this.phca = phca;
	}

	/**
	 * @return the paep
	 */
	public String getPaep() {
		return paep;
	}
	
	/**
	 * @param paep the paep to set
	 */
	public void setPaep(String paep) {
		this.paep = paep;
	}
	/**
	 * @return the servicio
	 */
	public String getServicio() {
		return servicio;
	}

	/**
	 * @param servicio the servicio to set
	 */
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	
	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_ALUMREQ"+ 
				" (FOLIO, PRERREQUISITO, PROMLIC, PROMMAE, PHCA, PAEP, SERVICIO)"+
				" VALUES( TO_NUMBER(?,'9999999'), ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'),TO_NUMBER(?,'99'))");
			ps.setString(1, folio);
			ps.setString(2, prerrequisito);
			ps.setString(3, promLic);
			ps.setString(4, promMae);
			ps.setString(5, phca);
			ps.setString(6, paep);
			ps.setString(7, servicio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReq|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_ALUMREQ " + 
					"SET PRERREQUISITO = ?, " +
					"PROMLIC = TO_NUMBER(?,'999.99'), " +
					"PROMMAE =  TO_NUMBER(?,'999.99'), " +
					"PHCA = TO_NUMBER(?,'999.99'), " +
					"PAEP = TO_NUMBER(?,'999.99'), " +
					"SERVICIO = TO_NUMBER(?,'99')"+
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1,  prerrequisito);
			ps.setString(2,  promLic);
			ps.setString(3,  promMae);
			ps.setString(4,  phca);
			ps.setString(5,  paep);
			ps.setString(6,  servicio);
			ps.setString(7,  folio);
			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReq|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_ALUMREQ "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999') ");
			ps.setString(1, folio);
					
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmAlumReq|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 			= rs.getString("FOLIO");
		prerrequisito	= rs.getString("PRERREQUISITO");
		promLic			= rs.getString("PROMLIC");
		promMae	 		= rs.getString("PROMMAE");
		phca 			= rs.getString("PHCA");
		paep			= rs.getString("PAEP");
		servicio		= rs.getString("SERVICIO");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, PRERREQUISITO, PROMLIC, PROMMAE, PHCA, PAEP,SERVICIO "+
			"FROM SALOMON.ADM_ALUMREQ "+ 
			"WHERE FOLIO = TO_NUMBER(?,'9999999') ");
		ps.setString(1, folio);
				
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_ALUMREQ "+ 
					"WHERE FOLIO = TO_NUMBER(?,'9999999') ");
			ps.setString(1,folio);
					
			
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
	

/*
//PRUEBA DE METODOS
public static void main(String args[]){
	try{
		Connection Conn = null;
			
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.14:1521:ora1", "salomon", "elsabio");
			
		AdmAlumReq req = new AdmAlumReq();	
			
		req.setPrerrequisito("s");
		req.setPromLic("89.90");
		req.setPromMae("98.25");
		req.setPhca("98.99");
		req.setPaep("86.56");
		req.setFolio("52");
	
		//	 MODIFICAR DOCUMENTO
		if(req.existeReg(Conn)==true){
			System.out.println("El PROMEDIO  " + req.getPromLic()+"ya existe");
			req.updateReg(Conn);				
			System.out.println("Documento: "+ req.getPromLic()+":"+req.getFolio()+":"+req.getPrerrequisito()+":"+req.getPromMae()+":"+req.getPhca()+":"+req.getPaep());				
		}
	
		
		//	 GRABAR DOCUMENTO  
		if(req.existeReg(Conn)==false){
			if (req.insertReg(Conn)){
				System.out.println("Grabado..!");
			}else{
				System.out.println("Error..!");
			}
			}else{
				System.out.println("Ya existe..!");
			}
	
	
		// BORRAR DOCUMENTO
		if(req.existeReg(Conn)==true){
			if (req.deleteReg(Conn)){
				System.out.println("Borrado..!");
			}else{
				System.out.println("Error..!");
			}
		}else{
			System.out.println("No existe..!");
		}
		
					
			req = null;
			
			Conn.commit();
			Conn.close();
			
	}catch(Exception ex){
		System.out.println("Error: "+ex);
	}
}*/
}		