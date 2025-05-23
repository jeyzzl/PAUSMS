 // Bean de datos personales del alumno 
 package  aca.well;

 import java.io.IOException;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;

 /**
 * @author 
 *
 */
public class WellCondicion{
 	private String codigoPersonal;
 	private String fuerza;
 	private String aerobico;
 	private String actividad;
 	private String cuota;
 	private String interes;
 	private String prescripcion; 	
 	private String obstaculo;
 	private String universidad; 	
 	private String recursos; 
 	
 	public WellCondicion(){ 		
 		codigoPersonal		= "";
 		fuerza				= "0";
 		aerobico			= "0";
 		actividad			= "0";
 		cuota				= "0";
 		interes				= "0";
 		prescripcion		= "0";
 		obstaculo			= "0";
 		universidad			= "0";
 		recursos			= "0";
 	}
 	
 	
	/**
	 * @return the codigoPersonal
	 */
	public String getCodigoPersonal() {
		return codigoPersonal;
	}


	/**
	 * @param codigoPersonal the codigoPersonal to set
	 */
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}


	/**
	 * @return the fuerza
	 */
	public String getFuerza() {
		return fuerza;
	}


	/**
	 * @param fuerza the fuerza to set
	 */
	public void setFuerza(String fuerza) {
		this.fuerza = fuerza;
	}


	/**
	 * @return the aerobico
	 */
	public String getAerobico() {
		return aerobico;
	}


	/**
	 * @param aerobico the aerobico to set
	 */
	public void setAerobico(String aerobico) {
		this.aerobico = aerobico;
	}


	/**
	 * @return the actividad
	 */
	public String getActividad() {
		return actividad;
	}


	/**
	 * @param actividad the actividad to set
	 */
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}


	/**
	 * @return the cuota
	 */
	public String getCuota() {
		return cuota;
	}


	/**
	 * @param cuota the cuota to set
	 */
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}


	/**
	 * @return the interes
	 */
	public String getInteres() {
		return interes;
	}


	/**
	 * @param interes the interes to set
	 */
	public void setInteres(String interes) {
		this.interes = interes;
	}


	/**
	 * @return the prescripcion
	 */
	public String getPrescripcion() {
		return prescripcion;
	}


	/**
	 * @param prescripcion the prescripcion to set
	 */
	public void setPrescripcion(String prescripcion) {
		this.prescripcion = prescripcion;
	}


	/**
	 * @return the obstaculo
	 */
	public String getObstaculo() {
		return obstaculo;
	}


	/**
	 * @param obstaculo the obstaculo to set
	 */
	public void setObstaculo(String obstaculo) {
		this.obstaculo = obstaculo;
	}


	/**
	 * @return the universidad
	 */
	public String getUniversidad() {
		return universidad;
	}


	/**
	 * @param universidad the universidad to set
	 */
	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}


	/**
	 * @return the recursos
	 */
	public String getRecursos() {
		return recursos;
	}


	/**
	 * @param recursos the recursos to set
	 */
	public void setRecursos(String recursos) {
		this.recursos = recursos;
	}


	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELL_CONDICION"+ 
 				"(CODIGO_PERSONAL, FUERZA, AEROBICO, ACTIVIDAD, CUOTA, INTERES, PRESCRIPCION, OBSTACULO, UNIVERSIDAD, RECURSOS ) "+
 				"VALUES( ?,?,?,?,?,?,?,?,?,?)");
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, fuerza);
 			ps.setString(3, aerobico);
 			ps.setString(4, actividad);
 			ps.setString(5, cuota);
 			ps.setString(6, interes);
 			ps.setString(7, prescripcion);
 			ps.setString(8, obstaculo);
 			ps.setString(9, universidad);
 			ps.setString(10, recursos); 			
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellCondicion|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{
 		PreparedStatement ps 		= null;		
 		boolean ok = false;
 		
 		try{
 			ps = conn.prepareStatement("UPDATE ENOC.WELL_CONDICION"	 
 				+ " SET FUERZA = ?," 				
 				+ " AEROBICO = ?,"
 				+ " ACTIVIDAD = ?,"
 				+ " CUOTA = ?,"
 				+ " INTERES = ?,"
 				+ " PRESCRIPCION = ?,"
 				+ " OBSTACULO = ?,"
 				+ " UNIVERSIDAD = ?, "
 				+ " RECURSOS = ? "
 				+ " WHERE CODIGO_PERSONAL = ?");
 			
 			ps.setString(1, fuerza);
 			ps.setString(2, aerobico);
 			ps.setString(3, actividad);
 			ps.setString(4, cuota);
 			ps.setString(5, interes);
 			ps.setString(6, prescripcion);
 			ps.setString(7, obstaculo);
 			ps.setString(8, universidad);
 			ps.setString(9, recursos);
 			ps.setString(10, codigoPersonal);
			if (ps.executeUpdate()==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellCondicion|updateReg|:"+ex);		
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.WELL_CONDICION "+ 
 				"WHERE CODIGO_PERSONAL = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellCondicion|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		fuerza				= rs.getString("FUERZA");
		aerobico			= rs.getString("AEROBICO");
		actividad			= rs.getString("ACTIVIDAD");
		cuota				= rs.getString("CUOTA");
		interes				= rs.getString("INTERES");
		prescripcion		= rs.getString("PRESCRIPCION");
		obstaculo			= rs.getString("OBSTACULO");
		universidad			= rs.getString("UNIVERSIDAD");
		recursos			= rs.getString("RECURSOS");
 	}
  	
 	public void mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, FUERZA, AEROBICO, ACTIVIDAD, CUOTA, INTERES, PRESCRIPCION, OBSTACULO, UNIVERSIDAD, RECURSOS"
	 			+ " FROM ENOC.WELL_CONDICION WHERE CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, codigoPersonal);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.well.WellCondicion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	


 	public boolean existeReg(Connection conn) throws SQLException{
 		boolean 		ok 	= false;
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_CONDICION"
 					+ " WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellCondicion|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
 }