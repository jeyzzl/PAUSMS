package aca.musica;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusiHorario {
	private String horarioId;
	private String maestroId;
	private String horaInicio;
	private String horaFinal;
	private String minInicio;
	private String minFinal;
	private String cupo;
	private String salonId;
	private String dia;
	private String estado;
	
	public MusiHorario(){
		horarioId	="";
		maestroId	="";
		horaInicio	="";
		horaFinal	="";
		minInicio	="";
		minFinal	="";
		cupo		="";
		salonId		="";
		dia			="";
		estado		="";
		
	}

	/**
	 * @return the horarioId
	 */
	public String getHorarioId() {
		return horarioId;
	}

	/**
	 * @param horarioId the horarioId to set
	 */
	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	/**
	 * @return the maestroId
	 */
	public String getMaestroId() {
		return maestroId;
	}

	/**
	 * @param maestroId the maestroId to set
	 */
	public void setMaestroId(String maestroId) {
		this.maestroId = maestroId;
	}

	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the horaFinal
	 */
	public String getHoraFinal() {
		return horaFinal;
	}

	/**
	 * @param horaFinal the horaFinal to set
	 */
	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	/**
	 * @return the cupo
	 */
	public String getCupo() {
		return cupo;
	}

	/**
	 * @param cupo the cupo to set
	 */
	public void setCupo(String cupo) {
		this.cupo = cupo;
	}
	
	/**
	 * @return the salonId
	 */
	public String getSalonId() {
		return salonId;
	}

	/**
	 * @param salonId the salonId to set
	 */
	public void setSalonId(String salonId) {
		this.salonId = salonId;
	}

	/**
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the minInicio
	 */
	public String getMinInicio() {
		return minInicio;
	}

	/**
	 * @param minInicio the minInicio to set
	 */
	public void setMinInicio(String minInicio) {
		this.minInicio = minInicio;
	}

	/**
	 * @return the minFinal
	 */
	public String getMinFinal() {
		return minFinal;
	}

	/**
	 * @param minFinal the minFinal to set
	 */
	public void setMinFinal(String minFinal) {
		this.minFinal = minFinal;
	}
	
		
	public boolean insertReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.MUSI_HORARIO"+ 
 				"(HORARIO_ID, MAESTRO_ID, HORA_INICIO, HORA_FINAL, MIN_INICIO, MIN_FINAL, CUPO, "+
 				" SALON_ID, DIA, ESTADO) "+
 				" VALUES( ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, "+
 				" TO_NUMBER(?,'99'), TO_NUMBER(?,'99'), TO_NUMBER(?,'9'), ? )");
 			ps.setString(1, horarioId);
 			ps.setString(2, maestroId);
 			ps.setString(3, horaInicio);
 			ps.setString(4, horaFinal);
 			ps.setString(5, minInicio);
 			ps.setString(6, minFinal);
 			ps.setString(7, cupo);
 			ps.setString(8, salonId);
 			ps.setString(9, dia);
 			ps.setString(10, estado);
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.alumno.MusiHorario|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
 	
 	public boolean updateReg(Connection conn ) throws Exception{ 		
 		Statement st 		= conn.createStatement(); 		
 		String comando = "";
 		boolean ok = false;
 		
 		try{
 			comando = "UPDATE ENOC.MUSI_HORARIO"+			 
 				" SET MAESTRO_ID = TO_NUMBER(?,'99'),"+
 				" HORA_INICIO = ?,"+
 				" HORA_FINAL = ?,"+
 				" MIN_INICIO = ?,"+
 				" MIN_FINAL = ?,"+
 				" CUPO = TO_NUMBER(?,'99'),"+ 				 
 				" SALON_ID = TO_NUMBER(?,'99'),"+
 				" DIA = TO_NUMBER(?,'9'),"+
 				" ESTADO = ? "+
 				" WHERE HORARIO_ID = ?"; 				
			if (st.executeUpdate(comando)==1){
				ok=true;
			}
			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiHoario|updateReg|:"+ex);		
 		}finally{
 			try { st.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 
 	
 	public boolean deleteReg(Connection conn ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("DELETE FROM ENOC.MUSI_HORARIO "+ 
 				"WHERE HORARIO_ID = ? ");
 			ps.setString(1, horarioId);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiHorario|deleteReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		return ok;
 	}
 	
 	public void mapeaReg(ResultSet rs ) throws SQLException, IOException{
 		horarioId 			= rs.getString("HORARIO_ID");
 		maestroId  			= rs.getString("MAESTRO_ID");
 		horaInicio		 	= rs.getString("HORA_INICIO");
 		horaFinal			= rs.getString("HORA_FINAL");
 		minInicio		 	= rs.getString("MIN_INICIO");
 		minFinal		 	= rs.getString("MIN_FINAL"); 		
 		cupo		 	    = rs.getString("CUPO");
 		salonId		 	    = rs.getString("SALON_ID");
 		dia		 		    = rs.getString("DIA");
 		estado		 	    = rs.getString("ESTADO");
 		
 	}
  	
 	public void mapeaRegId( Connection conn, String horarioId ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" HORARIO_ID, MAESTRO_ID, HORA_INICIO, HORA_FINAL, "+
	 			" MIN_INICIO, MIN_FINAL, CUPO, SALON_ID, DIA, ESTADO "+
	 			" FROM ENOC.MUSI_MAESTRO WHERE HORARIO_ID = ? "); 
	 		ps.setString(1, horarioId);	
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiHorario|mapeaRegId|:"+ex);
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
 			ps = conn.prepareStatement("SELECT * FROM ENOC.MUSI_HORARIO "+ 
 				"WHERE HORARIO_ID = ?");
 			ps.setString(1, 	horarioId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.musica.MusiHorario|existeReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	} 	
		
	public String maximoReg(Connection conn, String periodoId) throws SQLException{
		String maximo 			= "0001";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TRIM(TO_CHAR(MAX(TO_NUMBER(SUBSTR(HORARIO_ID,8,11),'9999'))+1,'0000')),'0001') AS MAXIMO " +
					"FROM ENOC.MUSI_HORARIO WHERE SUBSTR(HORARIO_ID,1,6) = '"+periodoId+"'"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = periodoId+"-"+rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiHorario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	public  String getNombreDia(String dia){
		int numDia 		= Integer.parseInt(dia);
		String nombreDia		= "";
		
		switch(numDia){
			case 1:{
				nombreDia = "Domingo";
				break;
			}
			case 2:{
				nombreDia = "Lunes";
				break;
			}
			case 3:{
				nombreDia = "Martes";
				break;
			}
			case 4:{
				nombreDia = "Miercoles";
				break;
			}
			case 5:{
				nombreDia = "Jueves";
				break;
			}
			case 6:{
				nombreDia = "Viernes";
				break;
			}
			case 7:{
				nombreDia = "Sabado";
				break;
			}
		}	
		return nombreDia;
		
	  
    }
	

	

	

}