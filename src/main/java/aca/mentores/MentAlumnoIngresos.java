package aca.mentores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MentAlumnoIngresos {
	private String codigoPersonal;
	private String cargaId;
	private String propios;
	private String semestre;
	private String colportaje;
	private String beca;
	
	public MentAlumnoIngresos(){
		codigoPersonal		= "";
		cargaId				= "";
		propios				= "";
		semestre			= "";
		colportaje			= "";
		beca				= "";
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getPropios() {
		return propios;
	}

	public void setPropios(String propios) {
		this.propios = propios;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getColportaje() {
		return colportaje;
	}

	public void setColportaje(String colportaje) {
		this.colportaje = colportaje;
	}

	public String getBeca() {
		return beca;
	}

	public void setBeca(String beca) {
		this.beca = beca;
	}


	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps= null;
		try{
			ps= conn.prepareStatement("INSERT INTO ENOC.MENT_ALUMNO_INGRESOS" + 
					" (CODIGO_PERSONAL, CARGA_ID, PROPIOS, SEMESTRE, COLPORTAJE, BECA)" +
					" VALUES( ?, ?, TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'), TO_NUMBER(?,'99999.99'))");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, propios);
			ps.setString(4, semestre);
			ps.setString(5, colportaje);
			ps.setString(6, beca);
			
			if(ps.executeUpdate() == 1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn) throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MENT_ALUMNO_INGRESOS" + 
					" SET PROPIOS = TO_NUMBER(?,'99999.99')," +
					" SEMESTRE = TO_NUMBER(?,'99999.99')," +
					" COLPORTAJE = TO_NUMBER(?,'99999.99')," +
					" BECA = TO_NUMBER(?,'99999.99')" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, propios);
			ps.setString(2, semestre);
			ps.setString(3, colportaje);
			ps.setString(4, beca);
			ps.setString(5, codigoPersonal);
			ps.setString(6, cargaId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|updateReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
		
	public boolean deleteReg(Connection conn)throws Exception{
		boolean ok = true;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MENT_ALUMNO_INGRESOS"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			if(ps.executeUpdate()==1)
				ok = true;
			else 
				ok = false;
		}catch (Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|deleteReg|:"+ex);
		}finally{
			ps.close();
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigoPersonal		= rs.getString("CODIGO_PERSONAL");
		cargaId				= rs.getString("CARGA_ID");
		propios				= rs.getString("PROPIOS");
		semestre			= rs.getString("SEMESTRE");
		colportaje			= rs.getString("COLPORTAJE");
		beca				= rs.getString("BECA");
	}
	
	public void mapeaRegId(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID," +
					" PROPIOS, SEMESTRE, COLPORTAJE, BECA"+
					" FROM ENOC.MENT_ALUMNO_INGRESOS" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MENT_ALUMNO_INGRESOS" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
		
		ps.setString(1, codigoPersonal);
		ps.setString(2, cargaId);	
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentAlumnoIngresos|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
}