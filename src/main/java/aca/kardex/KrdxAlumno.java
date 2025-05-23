// Bean del Kardex del Alumno( Formato del Kardex: determina que materias valen para el kardex del alumno).
package  aca.kardex;

import java.sql.*;

public class KrdxAlumno{
	private String codigoPersonal;
	private String cursoCargaId;
	private String folio;
	private String fCreada;
	private String actImp;
	private String certificado;		
	
	public KrdxAlumno(){
		codigoPersonal	= "";
		cursoCargaId	= "";
		folio			= "";
		fCreada		= "";
		actImp			= "";
		certificado		= "";
	}
	
	public String getCodigoPersonal(){
		return codigoPersonal;
	}
	
	public void setCodigoPersonal( String codigoPersonal){
		this.codigoPersonal = codigoPersonal;
	}	
	
	public String getCursoCargaId(){
		return cursoCargaId;
	}
	
	public void setCursoCargaId( String cursoCargaId){
		this.cursoCargaId = cursoCargaId;
	}	
	
	public String getFolio(){
		return folio;
	}
	
	public void setFolio( String folio){
		this.folio = folio;
	}
	
	public String getFCreada(){
		return folio;
	}
	
	public void setFCreada( String fCreada){
		this.fCreada = fCreada;
	}
	
	public String getActImp(){
		return actImp;
	}
	
	public void setActImp( String actImp){
		this.actImp = actImp;
	}
	
	public String getCertificado(){
		return certificado;
	}
	
	public void setCertificado( String certificado){
		this.certificado = certificado;
	}		
		
	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.KRDX_ALUMNO"+ 
				"(CODIGO_PERSONAL, CURSO_CARGA_ID, FOLIO, F_CREADA, ACT_IMP, CERTIFICADO) "+			
				"VALUES( ?, ?, "+
				"TO_NUMBER(?,'999'), "+				
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, ? )");				
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, folio);
			ps.setString(4, fCreada);
			ps.setString(5, actImp);
			ps.setString(6, certificado);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumno|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.KRDX_ALUMNO "+ 
				"SET "+				
				"F_CREADA = TO_DATE(?,'DD/MM/YYYY'), "+
				"ACT_IMP = ?, "+
				"CERTIFICADO ?  "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, fCreada);
			ps.setString(2, actImp);
			ps.setString(3, certificado);
			ps.setString(4, codigoPersonal);
			ps.setString(5, cursoCargaId);
			ps.setString(6, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumno|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumno|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal = rs.getString("CODIGO_PERSONAL");
		cursoCargaId 	= rs.getString("CURSO_CARGA_ID");
		folio			= rs.getString("FOLIO");
		fCreada	 	= rs.getString("F_CREADA");
		actImp 		= rs.getString("ACT_IMP");
		certificado		= rs.getString("CERTIFICADO");		
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal, String Curso_carga_id, String Folio ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{ 
			ps = conn.prepareStatement("SELECT "+
				"codigoPersonal, CURSO_CARGA_ID, FOLIO, F_CREADA, ACT_IMP, CERTIFICADO "+			
				"FROM ENOC.KRDX_ALUMNO "+ 
				"WHERE codigoPersonal = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, Curso_carga_id);
			ps.setString(3, Folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumno|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.KRDX_ALUMNO "+ 
				"WHERE codigoPersonal = ? "+
				"AND CURSO_CARGA_ID = ? "+
				"AND FOLIO = TO_NUMBER(?,'999')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoCargaId);
			ps.setString(3, folio);			
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.KrdxAlumno|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}