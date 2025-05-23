package aca.por;

import java.sql.*;

public class AlumnoNotas {
	private String codigoPersonal;
	private String cursoCargaId;
	private String planId;	
	private String cursoId;
	private String creditos;
	private String ciclo;
	private String nota;
	private String notaExtra;
	private String convalidacion;
	private String tipocalId;
	
	public AlumnoNotas() {
		codigoPersonal = "";
		planId 		   = "";
		cursoId 	   = "";
		creditos 	   = "";
		ciclo 		   = "";
		nota 		   = "";
		notaExtra      = "";
		convalidacion  = "";
		tipocalId 	   = "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	
	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public String getPlanId() {
		return planId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public String getCreditos() {
		return creditos;
	}

	public String getCiclo() {
		return ciclo;
	}

	public String getNota() {
		return nota;
	}

	public String getNotaExtra() {
		return notaExtra;
	}

	public String getConvalidacion() {
		return convalidacion;
	}

	public String getTipocalId() {
		return tipocalId;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException {
		codigoPersonal = rs.getString("CODIGO_PERSONAL");
		cursoCargaId   = rs.getString("CURSO_CARGA_ID");
		planId 		   = rs.getString("PLAN_ID");
		cursoId 	   = rs.getString("CURSO_ID");
		creditos 	   = rs.getString("CREDITOS");
		ciclo 		   = rs.getString("CICLO");
		nota 		   = rs.getString("NOTA");
		notaExtra 	   = rs.getString("NOTA_EXTRA");
	 	convalidacion  = rs.getString("CONVALIDACION");
		tipocalId 	   = rs.getString("TIPOCAL_ID");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String cursoCargaId, String planId, String cursoId)throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT" 
				+ " CODIGO_PERSONAL,"
				+ " CURSO_CARGA_ID,"
				+ " PLAN_ID,"
				+ " CURSO_ID,"
				+ " CICLO,"
				+ " NOTA,"
				+ " NOTA_EXTRA,"
				+ " CONVALIDACION,"
				+ " TIPOCAL_ID"
				+ " FROM ENOC.ALUMNO_NOTAS"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CURSO_CARGA_ID = ?"
				+ " AND PLAN_ID = ?"
				+ " AND CURSO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, planId);
			ps.setString(4, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				mapeaReg(rs);
			}				
		} catch (Exception ex) {
			System.out.println("Error - aca.por.AlumnoNotas|mapeaRegId|:"+ex);
		}finally {
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException {
		boolean 		  ok = false;
		ResultSet		  rs = null;
		PreparedStatement ps = null;
		
		try {
			ps  = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CURSO_CARGA_ID =?"
				+ " AND PLAN_ID = ?"
				+ " AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, planId);
			ps.setString(4, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		} catch (Exception ex) {
			System.out.println("Error - aca.por.AlumnoNotas|existeReg|:"+ex);
		}finally {
			if (rs != null) rs.close();
			if (ps != null) ps.close();
		}
		
		return ok;
	}
		
	
}
