package adm.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdmSalud {
	private String folio;
	private String enfermedad;
	private String enfermedadDato;
	private String impedimento;
	private String impedimentoDato;
	
	public AdmSalud(){
		folio 			= "";
		enfermedad		= "";
		enfermedadDato 	= "";
		impedimento		= "";
		impedimentoDato	= "";
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getEnfermedad() {
		return enfermedad;
	}

	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}

	public String getEnfermedadDato() {
		return enfermedadDato;
	}

	public void setEnfermedadDato(String enfermedadDato) {
		this.enfermedadDato = enfermedadDato;
	}

	public String getImpedimento() {
		return impedimento;
	}

	public void setImpedimento(String impedimento) {
		this.impedimento = impedimento;
	}

	public String getImpedimentoDato() {
		return impedimentoDato;
	}

	public void setImpedimentoDato(String impedimentoDato) {
		this.impedimentoDato = impedimentoDato;
	}


	public boolean insertReg(Connection conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			if (enfermedad.equals("N")) enfermedadDato = "-";
			if (impedimento.equals("N")) impedimentoDato = "-";
			
			ps = conn.prepareStatement("INSERT INTO SALOMON.ADM_SALUD"+ 
				"(FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO) "+
				"VALUES( TO_NUMBER(?,'9999999'), ?, ?, ?, ?)");
			ps.setString(1, folio);
			ps.setString(2, enfermedad);
			ps.setString(3, enfermedadDato);
			ps.setString(4, impedimento);
			ps.setString(5, impedimentoDato);

			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSalud|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (enfermedad.equals("N")) enfermedadDato = "-";
			if (impedimento.equals("N")) impedimentoDato = "-";
			
			ps = conn.prepareStatement("UPDATE SALOMON.ADM_SALUD " + 
					" SET ENFERMEDAD = ?, " +
					" ENFERMEDAD_DATO = ?, " +
					" IMPEDIMENTO = ?, " +
					" IMPEDIMENTO_DATO = ? " +		
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			
			ps.setString(1,  enfermedad);
			ps.setString(2,  enfermedadDato);
			ps.setString(3,  impedimento);
			ps.setString(4,  impedimentoDato);
			ps.setString(5,  folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
				conn.commit();
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSalud|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_SALUD "+ 
					" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.alumno.AdmSalud|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio			= rs.getString("FOLIO");
		enfermedad		= rs.getString("ENFERMEDAD");
		enfermedadDato  = rs.getString("ENFERMEDAD_DATO");
		impedimento		= rs.getString("IMPEDIMENTO");
		impedimentoDato = rs.getString("IMPEDIMENTO_DATO");
	}
	
	public void mapeaRegId( Connection conn, String folio ) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, ENFERMEDAD, ENFERMEDAD_DATO, IMPEDIMENTO, IMPEDIMENTO_DATO" +
				" FROM SALOMON.ADM_SALUD "+ 
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
			ps = conn.prepareStatement("SELECT * FROM SALOMON.ADM_SALUD "+ 
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