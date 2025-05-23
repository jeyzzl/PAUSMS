package aca.financiero;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FesCcMateria {
	private String matricula;
	private String cargaId;
	private String bloque;
	private String cursoCargaId;
	private String cursoId;
	private String costoCredito;
	private String costoCurso;
	
	public FesCcMateria(){
		matricula		= "";
		cargaId			= "";
		bloque			= "";      
		cursoCargaId	= "";
		cursoId			= "";
		costoCredito	= "";
		costoCurso		= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCursoCargaId() {
		return cursoCargaId;
	}

	public void setCursoCargaId(String cursoCargaId) {
		this.cursoCargaId = cursoCargaId;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getCostoCredito() {
		return costoCredito;
	}

	public void setCostoCredito(String costoCredito) {
		this.costoCredito = costoCredito;
	}

	public String getCostoCurso() {
		return costoCurso;
	}

	public void setCostoCurso(String costoCurso) {
		this.costoCurso = costoCurso;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula		= rs.getString("MATRICULA");	
		cargaId			= rs.getString("CARGA_ID");    
		bloque			= rs.getString("BLOQUE");            
		cursoCargaId	= rs.getString("CURSO_CARGA_ID");       
		cursoId			= rs.getString("CURSO_ID");
		costoCredito	= rs.getString("COSTO_CREDITO");     
		costoCurso		= rs.getString("COSTO_CURSO");
	}	
	
 	public void mapeaRegId(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException, IOException{
 		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT MATRICULA, CARGA_ID, BLOQUE, CURSO_CARGA_ID, CURSO_ID, COSTO_CREDITO, COSTO_CURSO" +
	 				" FROM MATEO.COSTO_CURSO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
	 		ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateria|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
	public boolean existeReg(Connection conn, String matricula, String cargaId, String bloqueId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MATRICULA FROM MATEO.COSTO_CURSO WHERE MATRICULA = ? AND CARGA_ID = ? AND BLOQUE = ?");
			ps.setString(1, matricula);
	 		ps.setString(2, cargaId);
	 		ps.setString(3, bloqueId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.FesCcMateria|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}