/**
 * 
 */
package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AlumDescuentoUtil {
	
	public boolean insertReg(Connection conn, AlumDescuento alumDescuento) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_DESCUENTO"+ 
				"(CODIGO_PERSONAL,CARGA_ID,DESCUENTO_ID,FECHA,MATRICULA,TIPO_MATRICULA,ENSENANZA,TIPO_ENSENANZA,INTERNADO,TIPO_INTERNADO,TOTAL,OBSERVACIONES,USUARIO, APLICADO) "+
				"VALUES(?, ?, TO_NUMBER(?, '99'), TO_DATE(?, 'DD/MM/YYYY'), TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, TO_NUMBER(?, '999999.99'), ?, ?, ? )");
				
			ps.setString(1, alumDescuento.getCodigoPersonal());    
			ps.setString(2, alumDescuento.getCargaId());           
			ps.setString(3, alumDescuento.getDescuentoId());        
			ps.setString(4, alumDescuento.getFecha());             
			ps.setString(5, alumDescuento.getMatricula());         
			ps.setString(6, alumDescuento.getTipoMatricula()); 	  
			ps.setString(7, alumDescuento.getEnsenanza());         
			ps.setString(8, alumDescuento.getTipoEnsenanza());     
			ps.setString(9, alumDescuento.getInternado());         
			ps.setString(10, alumDescuento.getTipoInternado());     
			ps.setString(11, alumDescuento.getTotal());             
			ps.setString(12, alumDescuento.getObservaciones());     
			ps.setString(13, alumDescuento.getUsuario());           
			ps.setString(14, alumDescuento.getAplicado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	} 
	
	public boolean updateReg(Connection conn, AlumDescuento alumDescuento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_DESCUENTO" 
				+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
				+ " MATRICULA = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_MATRICULA = ?, "
				+ " ENSENANZA = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_ENSENANZA = ?, "
				+ " INTERNADO = TO_NUMBER(?, '999999.99'),"
				+ " TIPO_INTERNADO = ?,"
				+ " TOTAL = TO_NUMBER(?, '999999.99'),"
				+ " OBSERVACIONES = ?,"
				+ " USUARIO = ?,"
				+ " APLICADO = ?"
				+ " WHERE CODIGO_PERSONAL = ?"
				+ " AND CARGA_ID = ?"								
				+ " AND DESCUENTO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, alumDescuento.getFecha());             
			ps.setString(2, alumDescuento.getMatricula());         
			ps.setString(3, alumDescuento.getTipoMatricula()); 	  
			ps.setString(4, alumDescuento.getEnsenanza());         
			ps.setString(5, alumDescuento.getTipoEnsenanza());     
			ps.setString(6, alumDescuento.getInternado());         
			ps.setString(7, alumDescuento.getTipoInternado());     
			ps.setString(8, alumDescuento.getTotal());             
			ps.setString(9, alumDescuento.getObservaciones());     
			ps.setString(10, alumDescuento.getUsuario());
			ps.setString(11, alumDescuento.getAplicado());
			ps.setString(12, alumDescuento.getCodigoPersonal());
			ps.setString(13, alumDescuento.getCargaId());			
			ps.setString(14, alumDescuento.getDescuentoId());		
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId, String descuentoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND CARGA_ID = ?"
					+ " AND DESCUENTO_ID = TO_NUMBER(?, '99') ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, descuentoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumDescuento mapeaRegId( Connection conn, String codigoPersonal, String cargaId, String descuentoId) throws SQLException{
		AlumDescuento alumDescuento = new AlumDescuento();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA ,"
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL, OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND DESCUENTO_ID = TO_NUMBER(?, '99') ");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, descuentoId);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumDescuento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumDescuento;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId, String descuentoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_DESCUENTO "+ 
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? AND DESCUENTO_ID = TO_NUMBER(?, '99')");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			ps.setString(3, descuentoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cargaId) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(DESCUENTO_ID)+1 MAXIMO FROM ENOC.ALUM_DESCUENTO WHERE CARGA_ID = '"+cargaId+"' "); 
			//ps.setString(1,  eventoId);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public String getDescuentoId(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		String tipo 			= "none";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT DESCUENTO_ID FROM ENOC.ALUM_DESCUENTO WHERE CODIGO_PERSONAL= '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' "); 
			
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("DESCUENTO_ID");
				
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|getDescuentoId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;		
	}
	
	public ArrayList<AlumDescuento> getAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<AlumDescuento> lisPre	= new ArrayList<AlumDescuento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL,CARGA_ID,DESCUENTO_ID,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " MATRICULA,TIPO_MATRICULA,ENSENANZA,TIPO_ENSENANZA,INTERNADO,TIPO_INTERNADO,TOTAL,OBSERVACIONES,USUARIO,APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumDescuento alumPre = new AlumDescuento();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|getAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<AlumDescuento> getAllCarga(Connection conn, String cargaId, String orden) throws SQLException{
		
		ArrayList<AlumDescuento> lisPre	= new ArrayList<AlumDescuento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, "
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL, "
					+ " OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumDescuento alumPre = new AlumDescuento();
				alumPre.mapeaReg(rs);
				lisPre.add(alumPre);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|getAllCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPre;
	}
	
	public ArrayList<AlumDescuento> lisPorFechas(Connection conn, String fechaIni, String fechaFin, String orden) throws SQLException{
		
		ArrayList<AlumDescuento> lista	= new ArrayList<AlumDescuento>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, DESCUENTO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA,"
					+ " MATRICULA, TIPO_MATRICULA, ENSENANZA, TIPO_ENSENANZA, INTERNADO, TIPO_INTERNADO, TOTAL,"
					+ " OBSERVACIONES, USUARIO, APLICADO"
					+ " FROM ENOC.ALUM_DESCUENTO"
					+ " WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumDescuento descuento = new AlumDescuento();
				descuento.mapeaReg(rs);
				lista.add(descuento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDescuentoUtil|lisPorFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lista;
	}
	
	// Map que obtiene los importes cobrados en los conceptos del calculo de cobro
	public HashMap<String, String> mapImporteConcepto(Connection conn, String tipoMov) throws SQLException{
		
		HashMap<String, String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT MATRICULA, CARGA_ID, TIPOMOV, IMPORTE FROM MATEO.FES_CC_MOVIMIENTO"+
					" WHERE TIPOMOV  IN ("+tipoMov+") "+
					" AND MATRICULA||CARGA_ID IN (SELECT CODIGO_PERSONAL||CARGA_ID FROM ENOC.ALUM_DESCUENTO)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("TIPOMOV"), rs.getString("IMPORTE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumUtil|mapImporteConcepto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}