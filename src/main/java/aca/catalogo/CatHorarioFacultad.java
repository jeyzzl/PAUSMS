package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatHorarioFacultad {
	private String horarioId;
	private String periodo;
	private String turno;
	private String horaInicio;
	private String horaFinal;
	private String minutoInicio;
	private String minutoFinal;
	private String nombre;
	private String tipo;
	
	public CatHorarioFacultad(){
		horarioId		= "0";
		periodo			= "0";
		turno			= "M";
		horaInicio		= "0";
		horaFinal		= "0";
		minutoInicio	= "0";
		minutoFinal		= "0";
		nombre			= "-";
		tipo			= "1";
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(String horarioId) {
		this.horarioId = horarioId;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(String minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public String getMinutoFinal() {
		return minutoFinal;
	}

	public void setMinutoFinal(String minutoFinal) {
		this.minutoFinal = minutoFinal;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAT_HORARIOFACULTAD "+ 
				"(HORARIO_ID, PERIODO, TURNO, HORA_INICIO, HORA_FINAL, MINUTO_INICIO, MINUTO_FINAL, NOMBRE, TIPO) "+
				"VALUES( TO_NUMBER(?,'9999999'), TO_NUMBER(?,'99'), ?, ?, ?,?,?,?,?)");
			ps.setString(1, horarioId);
			ps.setString(2, periodo);
			ps.setString(3, turno);
			ps.setString(4, horaInicio);
			ps.setString(5, horaFinal);
			ps.setString(6, minutoInicio);
			ps.setString(7, minutoFinal);
			ps.setString(8, nombre);
			ps.setString(9, tipo);
			
			if (ps.executeUpdate()== 1)
				ok = true;		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAT_HORARIOFACULTAD "+ 
				" SET HORA_INICIO = ?," +
				" HORA_FINAL = ?," +
				" MINUTO_INICIO  =?,"+
				" MINUTO_FINAL =?,"+
				" TURNO = ?,"+				
				" NOMBRE = ?,"+
				" TIPO = ?"+
				" WHERE HORARIO_ID = TO_NUMBER(?,'999999999') AND PERIODO = TO_NUMBER(?,'99') ");			
			ps.setString(1, horaInicio);
			ps.setString(2, horaFinal);
			ps.setString(3, minutoInicio);
			ps.setString(4, minutoFinal);
			ps.setString(5, turno);
			ps.setString(6, nombre);
			ps.setString(7, tipo);
			ps.setString(8, horarioId);
			ps.setString(9, periodo);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAT_HORARIOFACULTAD "+ 
				"WHERE PERIODO = TO_NUMBER(?,'99') AND HORARIO_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, periodo);
			ps.setString(2, horarioId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		horarioId 		= rs.getString("HORARIO_ID");
		periodo 		= rs.getString("PERIODO");
		turno 			= rs.getString("TURNO");
		horaInicio 		= rs.getString("HORA_INICIO");
		horaFinal 		= rs.getString("HORA_FINAL");
		minutoInicio	= rs.getString("MINUTO_INICIO");
		minutoFinal		= rs.getString("MINUTO_FINAL");
		nombre			= rs.getString("NOMBRE");
		tipo			= rs.getString("TIPO");
	}
	
	public void mapeaRegId( Connection conn, String periodo, String horarioId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_HORARIOFACULTAD "+ 
				" WHERE PERIODO = TO_NUMBER(?,'99') "+
				" AND HORARIO_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, periodo);
			ps.setString(2, horarioId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|mapeaRegId|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAT_HORARIOFACULTAD WHERE PERIODO = TO_NUMBER(?,'99') AND HORARIO_ID = TO_NUMBER(?,'9999999') "); 
			ps.setString(1, periodo);
			ps.setString(2, horarioId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT MAX(PERIODO)+1 MAXIMO FROM ENOC.CAT_HORARIOFACULTAD WHERE HORARIO_ID = ?"); 
			ps.setString(1,turno);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			if (maximo==null) maximo ="1";
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CatHorarioFacultad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;		
	}	
}
