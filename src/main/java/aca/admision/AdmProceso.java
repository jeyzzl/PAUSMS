// Bean de documentos de admision del alumno
package  aca.admision;

import java.sql.*;

public class AdmProceso{
	private String folio;
	private String fechaRegistro;	
	private String fechaSolicitud;
	private String fechaDocumentos;
	private String fechaAdmision;
	private String fechaCarta;
	
	
	public AdmProceso(){
		folio 				= "";
		fechaRegistro 		= "";
		fechaSolicitud		= "";
		fechaDocumentos		= "";
		fechaAdmision		= "";
		fechaCarta			= "";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getFechaDocumentos() {
		return fechaDocumentos;
	}

	public void setFechaDocumentos(String fechaDocumentos) {
		this.fechaDocumentos = fechaDocumentos;
	}

	public String getFechaAdmision() {
		return fechaAdmision;
	}

	public void setFechaAdmision(String fechaAdmision) {
		this.fechaAdmision = fechaAdmision;
	}

	public String getFechaCarta() {
		return fechaCarta;
	}

	public void setFechaCarta(String fechaCarta) {
		this.fechaCarta = fechaCarta;
	}

	public boolean insertReg(Connection Conn) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = Conn.prepareStatement("INSERT INTO SALOMON.ADM_PROCESO"+ 
				"(FOLIO, F_REGISTRO, F_SOLICITUD, F_DOCUMENTOS, F_ADMISION, F_CARTA) "+
				"VALUES(TO_NUMBER(?,'9999999'), now(), NULL, NULL, NULL, NULL)");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmProceso|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM SALOMON.ADM_PROCESO"+ 
				" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmProceso|deleteReg|:"+ex);
			ok = false;
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
				
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio 				= rs.getString("FOLIO");
		fechaRegistro	 	= rs.getString("F_REGISTRO");		
		fechaSolicitud 		= rs.getString("F_SOLICITUD");
		fechaDocumentos		= rs.getString("F_DOCUMENTOS");
		fechaAdmision		= rs.getString("F_ADMISION");
		fechaCarta			= rs.getString("F_CARTA");
	}
	
	public void mapeaRegId( Connection conn, String folio) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = conn.prepareStatement("SELECT FOLIO, TO_CHAR(F_REGISTRO, 'DD/MM/YYYY') AS F_REGISTRO," +
				" TO_CHAR(F_SOLICITUD, 'DD/MM/YYYY') AS F_SOLICITUD, TO_CHAR(F_DOCUMENTOS, 'DD/MM/YYYY') AS F_DOCUMENTOS," +
				" TO_CHAR(F_ADMISION, 'DD/MM/YYYY') AS F_ADMISION, TO_CHAR(F_CARTA, 'DD/MM/YYYY') AS F_CARTA"+
			" FROM SALOMON.ADM_PROCESO " + 
			" WHERE FOLIO = TO_NUMBER(?, '9999999')");
		ps.setString(1, folio);
		
		rs = ps.executeQuery();
		if (rs.next()){
			mapeaReg(rs);
		}
		try { rs.close(); } catch (Exception ignore) { }
		try { ps.close(); } catch (Exception ignore) { }		
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT FOLIO FROM SALOMON.ADM_PROCESO "+ 
				"WHERE FOLIO = TO_NUMBER(?,'9999999')");
			ps.setString(1,folio);
			
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmProceso|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateFecha(Connection conn, int numFecha) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if(numFecha==1){
				ps = conn.prepareStatement("UPDATE SALOMON.ADM_PROCESO"+ 
						" SET F_REGISTRO = now() "+
						" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			}
			else if(numFecha==2){
				ps = conn.prepareStatement("UPDATE SALOMON.ADM_PROCESO"+ 
						" SET F_SOLICITUD = now() "+
						" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			}
			else if(numFecha==3){
				ps = conn.prepareStatement("UPDATE SALOMON.ADM_PROCESO"+ 
						" SET F_DOCUMENTOS = now() "+
						" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			}
			else if(numFecha==4){
				ps = conn.prepareStatement("UPDATE SALOMON.ADM_PROCESO"+ 
						" SET F_ADMISION = now() "+
						" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			}
			else if(numFecha==5){
				ps = conn.prepareStatement("UPDATE SALOMON.ADM_PROCESO"+ 
						" SET F_CARTA = now() "+
						" WHERE FOLIO = TO_NUMBER(?,'9999999')");
			}
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - adm.documento.AdmProceso|updateReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
}