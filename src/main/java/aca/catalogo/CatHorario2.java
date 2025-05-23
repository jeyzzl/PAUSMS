package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatHorario2 {
	private String facultad;
	private String periodo;
	private String turno;
	private String horaIni;
	private String horaFin;
		
	public CatHorario2(){
		facultad	= "";
		periodo 	= "";
		turno		= "";
		horaIni		= "";
		horaFin		= "";
	}
	
	
	
	public String getFacultad() {
		return facultad;
	}



	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}



	public String getHoraIni() {
		return horaIni;
	}



	public void setHoraIni(String horaIni) {
		this.horaIni = horaIni;
	}



	public String getHoraFin() {
		return horaFin;
	}



	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}



	public String getPeriodo(){
		return periodo;
	}
	
	public void setPeriodo( String periodo){
		this.periodo = periodo;
	}
	
	public String getTurno(){
		return turno;
	}
	
	public void setTurno( String turno){
		this.turno = turno;
	}
	

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_HORARIO2 "+ 
				"(FACULTAD, PERIODO, TURNO, HORA_INICIO, HORA_FINAL) "+
				"VALUES( ?, TO_NUMBER(?,'99'), TO_DATE(?,'DD/MM/YYYY'),  TO_DATE(?,'DD/MM/YYYY') )");
			ps.setString(1, facultad);
			ps.setString(2, periodo);
			ps.setString(3, turno);
			ps.setString(4, horaIni);
			ps.setString(5, horaFin);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_HORARIO2 "+ 
				" SET HORA_INICIO = TO_DATE(?,'DD/MM/YYYY')," +
				" HORA_FINAL = TO_DATE(?,'DD/MM/YYYY'), " +
				" TURNO = ? "+				
				" WHERE FACULTAD = ? AND PERIODO = TO_NUMBER(?,'99') ");			
			
			ps.setString(1, horaIni);
			ps.setString(2, horaFin);
			ps.setString(3, turno);			
			ps.setString(4, facultad);			
			ps.setString(5, periodo);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_HORARIO2 "+ 
				"WHERE PERIODO = TO_NUMBER(?,'99') AND FACULTAD = ? ");
			ps.setString(1, periodo);
			ps.setString(2, turno);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		facultad 	= rs.getString("FACULTAD");
		periodo 	= rs.getString("PERIODO");
		turno 		= rs.getString("TURNO");
		horaIni 	= rs.getString("HORA_INICIO");
		horaFin 	= rs.getString("HORA_FINAL");
	}
	
	public void mapeaRegId( Connection conn, String periodo, String facultad) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT PERIODO, TURNO, HORA_INICIO, HORA_FINAL, FACULTAD "+
				"FROM ENOC.CAT_HORARIO2 "+ 
				"WHERE PERIODO = TO_NUMBER(?,'99') "+
				"AND FACULTAD = ? ");
			ps.setString(1, periodo);
			ps.setString(2, facultad);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_HORARIO WHERE PERIODO = TO_NUMBER(?,'99') AND FACULTAD = ? "); 
			ps.setString(1, periodo);
			ps.setString(2, facultad);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	public String maximoReg(Connection conn, String turno) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(PERIODO)+1 MAXIMO FROM ENOC.CAT_HORARIO WHERE FACULTAD = ?"); 
			ps.setString(1,turno);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if (maximo==null) maximo ="1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorario|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;		
	}
}
