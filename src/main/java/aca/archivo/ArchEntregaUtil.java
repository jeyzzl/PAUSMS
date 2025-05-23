//Clase  para la tabla ARCH_DOCUMENTOS
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchEntregaUtil {
	
	public boolean insertReg(Connection conn, ArchEntrega archEntrega) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ARCH_ENTREGA"+ 
				"(CODIGO_PERSONAL, FOLIO, DOCUMENTOS, FECHA)"+
				" VALUES(?, TO_NUMBER(?, '99'), ?, NOW())");
					
			ps.setString(1,	archEntrega.getCodigoPersonal());
			ps.setString(2, archEntrega.getFolio());
			ps.setString(3, archEntrega.getDocumentos());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ArchEntrega archEntrega) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_ENTREGA"
					+ " SET IDENTIFICACION = ?,"
					+ " PODER = ?,"
					+ " ENVIO = ?,"
					+ " FIRMA = ?,"
					+ " EXTRA = ?," 
					+ " DOCUMENTOS = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')");
			
			ps.setBytes(1, archEntrega.getIdentificacion());
			ps.setBytes(2, archEntrega.getPoder());
			ps.setBytes(3, archEntrega.getEnvio());
			ps.setBytes(4, archEntrega.getFirma());
			ps.setBytes(5, archEntrega.getExtra());
			ps.setString(6,	archEntrega.getDocumentos());
			ps.setString(7,	archEntrega.getCodigoPersonal());
			ps.setString(8, archEntrega.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String folio) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_ENTREGA"		
						+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = TO_NUMBER('"+folio+"', '99')");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|deleteReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteImagen(Connection conn, String codigoPersonal, String folio, String imagen) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_ENTREGA SET "+imagen+" = NULL "		
						+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = TO_NUMBER('"+folio+"', '99')");
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|deleteImagen|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchEntrega getEntrega(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		ArchEntrega entrega 	= new ArchEntrega();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS, IDENTIFICACION, PODER, ENVIO, FIRMA, EXTRA"
					+ " FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"'"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				entrega.mapeaRegAll(rs);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|getEntrega|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return entrega;
	}
	
	public String getDocumentos(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String documentos		= "";
		
		try{
			comando = " SELECT COALESCE(DOCUMENTOS,'-') AS DOCUMENTOS FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				documentos = rs.getString("DOCUMENTOS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|getDocumentos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return documentos;
	}
	
	public ArrayList<ArchEntrega> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<ArchEntrega> lista 	= new ArrayList<ArchEntrega>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS, IDENTIFICACION, PODER, ENVIO, FIRMA"
					+ " FROM ENOC.ARCH_ENTREGA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchEntrega entrega = new ArchEntrega();
				entrega.mapeaReg(rs);
				lista.add(entrega);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ArchEntrega> getListaSinArchivos(Connection conn, String codigoPersonal) throws SQLException{
		
		ArrayList<ArchEntrega> lista 	= new ArrayList<ArchEntrega>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS"
					+ " FROM ENOC.ARCH_ENTREGA"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArchEntrega entrega = new ArchEntrega();
				entrega.mapeaReg(rs);
				lista.add(entrega);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|getListaSinArchivos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public ArrayList<ArchEntrega> getLista(Connection conn, String codigoPersonal) throws SQLException{
		
		ArrayList<ArchEntrega> lista 	= new ArrayList<ArchEntrega>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, FECHA, DOCUMENTOS, IDENTIFICACION, PODER, ENVIO, FIRMA, EXTRA"
					+ " FROM ENOC.ARCH_ENTREGA"
					+ " WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ArchEntrega entrega = new ArchEntrega();
				entrega.mapeaRegAll(rs);
				lista.add(entrega);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchEntregaUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lista;
	}
	
	public boolean tieneIdentificacion(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT CODIGO_PERSONAL FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"' AND IDENTIFICACION IS NOT NULL"); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|tieneIdentificacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean tienePoder(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT CODIGO_PERSONAL FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"' AND PODER IS NOT NULL"); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|tienePoder|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean tieneEnvio(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT CODIGO_PERSONAL FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"' AND ENVIO IS NOT NULL");
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|tieneIdentificacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean tieneFirma(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT CODIGO_PERSONAL FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"' AND FIRMA IS NOT NULL");
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|tieneFirma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean tieneExtra(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT CODIGO_PERSONAL FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"' AND EXTRA IS NOT NULL");
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|tieneExtra|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoFolio(Connection conn, String codigoPersonal) throws SQLException{
		
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|maximoFolio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public HashMap<String,String> getDocId (Connection conn, String codigoPersonal, String folio) throws SQLException{
		HashMap<String,String> mapa   = new HashMap<String,String>();
		ResultSet rs				  = null;
		PreparedStatement ps		  = null;
		
		try{
			ps = conn.prepareStatement("SELECT DOCUMENTOS FROM ENOC.ARCH_ENTREGA WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND FOLIO = '"+folio+"'");
			rs = ps.executeQuery();
			if(rs.next()){
				String doc 		= rs.getString("DOCUMENTOS");
				String docId[]	= doc.split("-");
				for(String documento : docId){
					mapa.put(documento, documento);
				}
			}
		}catch(Exception ex){
			System.out.println("Error -  aca.archivo.ArchEntregaUtil|getDocId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		return mapa;
	}
	
}