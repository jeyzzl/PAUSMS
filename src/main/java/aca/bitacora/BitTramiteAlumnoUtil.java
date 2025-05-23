package aca.bitacora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BitTramiteAlumnoUtil {

	public boolean insertReg(Connection conn, BitTramiteAlumno tramite) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BIT_TRAMITE_ALUMNO"
					+ " (FOLIO, CODIGO_PERSONAL, TRAMITE_ID, FECHA_INICIO, FECHA_FINAL, ESTADO, AREA_ID, AREA_ORIGEN, FOLIO_ORIGEN, USUARIO, COMENTARIO)"
					+ " VALUES(?, ?, TO_NUMBER(?,'9999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'99'),TO_NUMBER(?,'999'), TO_NUMBER(?,'9'), ?, ?, ?)");
					
			ps.setString(1,	tramite.getFolio());
			ps.setString(2, tramite.getCodigoPersonal());
			ps.setString(3, tramite.getTramiteId());
			ps.setString(4, tramite.getFechaInicio());
			ps.setString(5, tramite.getFechaFinal());
			ps.setString(6, tramite.getEstado() );
			ps.setString(7, tramite.getAreaId());
			ps.setString(8, tramite.getAreaOrigen());
			ps.setString(9, tramite.getFolioOrigen());
			ps.setString(10, tramite.getUsuario());
			ps.setString(11, tramite.getComentario());

			if (ps.executeUpdate()== 1)
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BitTramiteAlumno tramite) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BIT_TRAMITE_ALUMNO"
					+ " SET CODIGO_PERSONAL = ?,"
					+ " TRAMITE_ID = ?,"
					+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FINAL =  TO_DATE(?,'DD/MM/YYYY'),"
					+ " ESTADO = TO_NUMBER(?,'99'),"
					+ " AREA_ID = TO_NUMBER(?,'999'),"
					+ " AREA_ORIGEN = TO_NUMBER(?,'999'),"
					+ " FOLIO_ORIGEN = ?,"
					+ " USUARIO = ?,"
					+ " COMENTARIO = ?"
					+ " WHERE FOLIO = ?");
			
			ps.setString(1, tramite.getCodigoPersonal());
			ps.setString(2, tramite.getTramiteId());
			ps.setString(3, tramite.getFechaInicio());
			ps.setString(4, tramite.getFechaFinal());
			ps.setString(5, tramite.getEstado() );
			ps.setString(6, tramite.getAreaId());
			ps.setString(7, tramite.getAreaOrigen());
			ps.setString(8, tramite.getFolioOrigen());			
			ps.setString(9, tramite.getUsuario());
			ps.setString(10, tramite.getComentario());
			ps.setString(11, tramite.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String folio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO = ?");
			
			ps.setString(1, folio);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BitTramiteAlumno mapeaRegId( Connection conn, String folio) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		BitTramiteAlumno tramite 		= new BitTramiteAlumno();
		try{
			ps = conn.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " ESTADO, AREA_ID, AREA_ORIGEN, FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO = '"+folio+"'");
			rs = ps.executeQuery();
			if (rs.next()){
				tramite.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return tramite;
	}
	
	public boolean existeReg( Connection conn, String folio) throws SQLException{
		
		PreparedStatement ps 	= null; 
		ResultSet rs 			= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO = '"+folio+"'");
			rs = ps.executeQuery();
			if (rs.next()){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String year) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo			= year+"-0001";
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(TO_NUMBER(MAX(SUBSTR(FOLIO,6,4)),'9999')+1,1) AS MAXIMO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE SUBSTR(FOLIO,1,4) = '"+year+"'"); 
			rs = ps.executeQuery();
			if (rs.next()){
				maximo = rs.getString("MAXIMO");
				if (maximo.length()==1) maximo = year+"-000"+maximo;
				if (maximo.length()==2) maximo = year+"-00"+maximo;
				if (maximo.length()==3) maximo = year+"-0"+maximo;
				if (maximo.length()==4) maximo = year+"-"+maximo;
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public int getNumDependientes(Connection conn, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int dep					= 0;
		
		try{
			ps = conn.prepareStatement ("SELECT COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = '"+folio+"'"); 
			rs = ps.executeQuery();
			if (rs.next()){
				dep = rs.getInt("TOTAL");
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getNumDependientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return dep;
	}
	
	public int getNumDependientesSinTerminar(Connection conn, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int dep					= 0;
		
		try{
			ps = conn.prepareStatement ("SELECT COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = '"+folio+"' AND ESTADO < 4"); 
			rs = ps.executeQuery();
			if (rs.next()){
				dep = rs.getInt("TOTAL");
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getNumDependientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return dep;
	}
	
	public String getDependientes(Connection conn, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String dep				= "";
		
		try{
			ps = conn.prepareStatement ("SELECT FOLIO FROM ENOC.BIT_TRAMITE_ALUMNO WHERE FOLIO_ORIGEN = '"+folio+"'"); 
			rs = ps.executeQuery();
			while(rs.next()){
				dep = dep +","+rs.getString("FOLIO");
			}
			if (dep.length()>0) dep = dep.substring(1, dep.length());
				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getDependientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return dep;
	}
	
public String getTiempoMinimoTramite(Connection conn, String tramiteId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String minimo			= "0";
		int dias				= 0;
		
		try{
			ps = conn.prepareStatement ("SELECT FECHA_INICIO,FECHA_FINAL FROM BIT_TRAMITE_ALUMNO WHERE FECHA_FINAL IS NOT NULL AND TRAMITE_ID = "+tramiteId); 
			rs = ps.executeQuery();
			while(rs.next()){
				
				String fechaIni = rs.getString("FECHA_INICIO");
				String fechaFin = rs.getString("FECHA_FINAL");
				
				fechaIni = fechaIni.substring(0, 10);
				fechaFin = fechaFin.substring(0, 10);
				
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");	

				Date dateIni = dateFormat.parse(fechaIni);
				Date dateFin = dateFormat.parse(fechaFin);
				
				if(dias == 0) {
					dias = (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000); //60*60*24
				}else if(dias > (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000)) {
					dias = (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000); //60*60*24
				}
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getTiempoMinimoTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		minimo = String.valueOf(dias);
		return minimo;
	}
	
	public String getTiempoMaximoTramite(Connection conn, String tramiteId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String maximo			= "0";
		int dias				= 0;
		
		try{
			ps = conn.prepareStatement ("SELECT FECHA_INICIO,FECHA_FINAL FROM BIT_TRAMITE_ALUMNO WHERE FECHA_FINAL IS NOT NULL AND TRAMITE_ID = "+tramiteId); 
			rs = ps.executeQuery();
			while(rs.next()){
				
				String fechaIni = rs.getString("FECHA_INICIO");
				String fechaFin = rs.getString("FECHA_FINAL");
				
				fechaIni = fechaIni.substring(0, 10);
				fechaFin = fechaFin.substring(0, 10);
				
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");	

				Date dateIni = dateFormat.parse(fechaIni);
				Date dateFin = dateFormat.parse(fechaFin);
				
				if(dias == 0) {
					dias = (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000); //60*60*24
				}else if(dias < (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000)) {
					dias = (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000); //60*60*24
				}
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getTiempoMaximoTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		maximo = String.valueOf(dias);
		return maximo;
	}
	
	public String getPromedioTramite(Connection conn, String tramiteId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String promedio			= "0";
		int dias				= 0;
		int alumnos 			= 0;
		
		try{
			ps = conn.prepareStatement ("SELECT FECHA_INICIO,FECHA_FINAL FROM BIT_TRAMITE_ALUMNO WHERE FECHA_FINAL IS NOT NULL AND TRAMITE_ID = "+tramiteId); 
			rs = ps.executeQuery();
			while(rs.next()){
				alumnos++;
				String fechaIni = rs.getString("FECHA_INICIO");
				String fechaFin = rs.getString("FECHA_FINAL");
				
				fechaIni = fechaIni.substring(0, 10);
				fechaFin = fechaFin.substring(0, 10);
				
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");	

				Date dateIni = dateFormat.parse(fechaIni);
				Date dateFin = dateFormat.parse(fechaFin);

				dias = dias + (int) ((dateFin.getTime() - dateIni.getTime())/ 86400000); //60*60*24
				
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|getPromedioTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		if(alumnos != 0) {
			int prom = dias/alumnos;
			promedio = String.valueOf(prom);
		}
		return promedio;
	}
	
	public ArrayList<BitTramiteAlumno> lisTramite(Connection conn, String codigoAlumno, String orden) throws SQLException{
		ArrayList<BitTramiteAlumno> lisTramite 	= new ArrayList<BitTramiteAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramiteAlumno tramite = new BitTramiteAlumno();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTramite;
	}
	
	public ArrayList<BitTramiteAlumno> lisTramitePrincipal(Connection conn, String codigoAlumno, String orden) throws SQLException{
		ArrayList<BitTramiteAlumno> lisTramite 	= new ArrayList<BitTramiteAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoAlumno+"'"
					+ " AND FOLIO_ORIGEN = '-' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramiteAlumno tramite = new BitTramiteAlumno();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisTramitePrincipal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTramite;
	}
	
	public ArrayList<BitTramiteAlumno> lisTramiteOrigen(Connection conn, String codigoAlumno, String orden) throws SQLException{
		ArrayList<BitTramiteAlumno> lisTramite 	= new ArrayList<BitTramiteAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = '"+codigoAlumno+"'"
					+ " AND AREA_ORIGEN = 0 "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramiteAlumno tramite = new BitTramiteAlumno();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisTramite|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTramite;
	}
	
	public ArrayList<BitTramiteAlumno> lisTramitesDependientes(Connection conn, String folio, String orden) throws SQLException{
		ArrayList<BitTramiteAlumno> lisTramite 	= new ArrayList<BitTramiteAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FOLIO_ORIGEN = '"+folio+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramiteAlumno tramite = new BitTramiteAlumno();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisTramitesDependientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTramite;
	}
	
	public ArrayList<BitTramiteAlumno> lisTramiteFiltros(Connection conn, String areaId, String tramiteId, String estadoId, String fechaInicio, String fechaFinal, String orden) throws SQLException{
		ArrayList<BitTramiteAlumno> lisTramite 	= new ArrayList<BitTramiteAlumno>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String condicion			= "";
		
		try{
			if (!tramiteId.equals("0")) {
				condicion = condicion + " AND TRAMITE_ID = TO_NUMBER('"+tramiteId+"','99')";
			}
			if (!estadoId.equals("0")) {
				condicion = condicion + " AND ESTADO = TO_NUMBER('"+estadoId+"','99')";
			}
			if (fechaInicio!=null && fechaFinal != null) {
				condicion = condicion + " AND FECHA_INICIO BETWEEN TO_DATE('"+fechaInicio+"','DD/MM/YYYY') AND TO_DATE('"+fechaFinal+"','DD/MM/YYYY')";
			}
			
			comando = " SELECT FOLIO,CODIGO_PERSONAL,TRAMITE_ID, TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,ESTADO,AREA_ID,AREA_ORIGEN,FOLIO_ORIGEN, USUARIO, COMENTARIO"
					+ " FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE AREA_ID = TO_NUMBER('"+areaId+"','99') "
					+ condicion + " "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				BitTramiteAlumno tramite = new BitTramiteAlumno();
				tramite.mapeaReg(rs);
				lisTramite.add(tramite);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisTramiteFiltros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisTramite;
	}
	
	public HashMap<String, String> mapCuentaTramites(Connection conn, String fechaIni, String fechaFin, String estados) throws SQLException{
		HashMap<String, String> mapTramite 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT TRAMITE_ID, COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FECHA_INICIO BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " AND ESTADO IN ("+estados+")"
					+ " GROUP BY TRAMITE_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTramite.put(rs.getString("TRAMITE_ID"), rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapCuentaTramites|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}
	
	public HashMap<String, String> mapCuentaHijos(Connection conn, String estados) throws SQLException{
		HashMap<String, String> mapTramite 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT FOLIO_ORIGEN, COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE ESTADO IN ("+estados+")"
					+ " GROUP BY FOLIO_ORIGEN";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTramite.put(rs.getString("FOLIO_ORIGEN"), rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapCuentaHijos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}
	
	public HashMap<String, String> mapTramitesPorEstado(Connection conn, String fechaIni, String fechaFin) throws SQLException{
		HashMap<String, String> mapTramite 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT TRAMITE_ID, ESTADO, COUNT(*) AS TOTAL FROM ENOC.BIT_TRAMITE_ALUMNO"
					+ " WHERE FECHA_INICIO BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"
					+ " GROUP BY TRAMITE_ID, ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTramite.put(rs.getString("TRAMITE_ID")+rs.getString("ESTADO"), rs.getString("TOTAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapTramitesPorEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}
	
	public HashMap<String, String> mapTramites(Connection conn) throws SQLException{
		HashMap<String, String> mapTramite 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT TRAMITE_ID FROM ENOC.BIT_TRAMITE_ALUMNO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTramite.put(rs.getString("TRAMITE_ID"), rs.getString("TRAMITE_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapTramites|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}
	
	public HashMap<String, String> mapTramitesSinOrigenSinEtiquetas(Connection conn) throws SQLException{
		HashMap<String, String> mapTramite 	= new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT * FROM ENOC.BIT_TRAMITE_ALUMNO "
					+ " WHERE FOLIO NOT IN (SELECT DISTINCT(FOLIO_ORIGEN) FROM ENOC.BIT_TRAMITE_ALUMNO)"
					+ " AND FOLIO NOT IN(SELECT FOLIO FROM ENOC.BIT_ETIQUETA)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapTramite.put(rs.getString("FOLIO"), rs.getString("TRAMITE_ID"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.TramiteUtil|mapTramitesSinOrigenSinEtiquetas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapTramite;
	}
	
}
