package aca.envio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnvioDocumentoUtil {

	public boolean insertReg(Connection conn, EnvioDocumento envDoc) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO" +
				" ENVIO_DOCUMENTO(FOLIO, MATRICULA, DESTINATARIO, SERVICIO_ID," +
				" FECHA, DOCUMENTOS, NUMGUIA, PAIS_ID, ESTADO_ID, DIRECCION, PAGO)" +
				" VALUES(TO_NUMBER(?,'9999999'), ?, ?, TO_NUMBER(?,'99')," +
				" TO_DATE(?,'DD/MM/YYYY'), ?, ?, TO_NUMBER(?,'999'), TO_NUMBER(?,'999'), ?, TO_NUMBER(?,'99999.99'))");
			
			ps.setString(1, envDoc.getFolio());
			ps.setString(2, envDoc.getMatricula());
			ps.setString(3, envDoc.getDestinatario());			
			ps.setString(4, envDoc.getServicioId());
			ps.setString(5, envDoc.getFecha());
			ps.setString(6, envDoc.getDocumentos());
			ps.setString(7, envDoc.getNumguia());
			ps.setString(8, envDoc.getPaisId());
			ps.setString(9, envDoc.getEstadoId());
			ps.setString(10, envDoc.getDireccion());
			ps.setString(11, envDoc.getPago());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, EnvioDocumento envDoc) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENVIO_DOCUMENTO" +
				" SET MATRICULA = ?," +
				" DESTINATARIO = ?," +
				" SERVICIO_ID = TO_NUMBER(?,'99')," +
				" FECHA = TO_DATE(?, 'DD/MM/YYYY')," +
				" DOCUMENTOS = ?," +
				" NUMGUIA = ?, " +
				" PAIS_ID = TO_NUMBER(?,'999')," +
				" ESTADO_ID = TO_NUMBER(?,'999')," +
				" DIRECCION = ?," +
				" PAGO = TO_NUMBER(?, '99999.99')" +
				" WHERE FOLIO = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, envDoc.getMatricula());
			ps.setString(2, envDoc.getDestinatario());			
			ps.setString(3, envDoc.getServicioId());
			ps.setString(4, envDoc.getFecha());
			ps.setString(5, envDoc.getDocumentos());
			ps.setString(6, envDoc.getNumguia());
			ps.setString(7, envDoc.getPaisId());
			ps.setString(8, envDoc.getEstadoId());
			ps.setString(9, envDoc.getDireccion());
			ps.setString(10, envDoc.getPago());
			ps.setString(11, envDoc.getFolio());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String folio) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENVIO_DOCUMENTO"+
				" WHERE FOLIO = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, folio);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public EnvioDocumento mapeaRegId(Connection con, String idEmpleado, String folio) throws SQLException{
		EnvioDocumento envDoc = new EnvioDocumento();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT FOLIO, MATRICULA, DESTINATARIO, SERVICIO_ID," +
					" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, DOCUMENTOS, NUMGUIA, PAIS_ID, ESTADO_ID," +
					" DIRECCION, PAGO FROM ENVIO_DOCUMENTO " +
					" WHERE FOLIO = TO_NUMBER(?, '9999999')");
			
			ps.setString(1, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				envDoc.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return envDoc;
	}
	
	public boolean existeReg(Connection conn, String folio) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENVIO_DOCUMENTO" +
					" WHERE FOLIO = TO_NUMBER(?, '9999999')");
		
		ps.setString(1, folio);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
		return ok;
	}

	public ArrayList<EnvioDocumento> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<EnvioDocumento> listDoc		= new ArrayList<EnvioDocumento>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT FOLIO, MATRICULO, DESTINATARIO, SERVICIO_ID," +
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, DOCUMENTOS, NUMGUIA, PAIS_ID, ESTADO_ID," +
				" DIRECCION, PAGO" +					
				" FROM ENVIO_DOCUMENTO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				EnvioDocumento doc = new EnvioDocumento();
				doc.mapeaReg(rs);
				listDoc.add(doc);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.envio.EnvioDocumentoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listDoc;
	}

}