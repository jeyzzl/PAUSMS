package aca.internado;

	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.ArrayList;	

	public class IntAlumReporteUtil {
		
		public boolean insertReg(Connection conn, IntAlumReporte intAlumRep ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("INSERT INTO ENOC.INT_ALUM_REPORTE(CODIGO_PERSONAL, FOLIO, FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO)"
						+ " VALUES(?,?,TO_DATE(?, 'DD/MM/YYYY'),?,?,?,?,?)");
				ps.setString(1, intAlumRep.getCodigoPersonal());
				ps.setString(2, intAlumRep.getFolio());
				ps.setString(3, intAlumRep.getFecha());
				ps.setString(4, intAlumRep.getReporteId());
				ps.setString(5, intAlumRep.getComentario());
				ps.setString(6, intAlumRep.getUsuario());
				ps.setString(7, intAlumRep.getCantidad());
				ps.setString(8, intAlumRep.getDormitorio());
				
				if ( ps.executeUpdate()== 1){
					ok = true;					
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|insertReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		
		public boolean updateReg(Connection conn, IntAlumReporte intAlumRep ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("UPDATE ENOC.INT_ALUM_REPORTE"
						+ " SET FECHA = TO_DATE(?, 'DD/MM/YYYY'),"
						+ " REPORTE_ID = ?,"
						+ " COMENTARIO = ?,"
						+ " USUARIO = ?,"
						+ " CANTIDAD = ?"
						+ " WHERE CODIGO_PERSONAL = ?"
						+ " AND FOLIO = TO_NUMBER(?,'99')");	
				
				ps.setString(1, intAlumRep.getFecha());
				ps.setString(2, intAlumRep.getReporteId());
				ps.setString(3, intAlumRep.getComentario());
				ps.setString(4, intAlumRep.getUsuario());
				ps.setString(5, intAlumRep.getCantidad());
				ps.setString(6, intAlumRep.getCodigoPersonal());
				ps.setString(7, intAlumRep.getFolio());

				
				if ( ps.executeUpdate()== 1){
					ok = true;					
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|updateReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		
		public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws SQLException{
			boolean ok = false;
			PreparedStatement ps = null;
			try{
				ps = conn.prepareStatement("DELETE FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
				ps.setString(1, codigoPersonal);
				ps.setString(2, folio);
				
				
				if ( ps.executeUpdate()== 1){
					ok = true;					
				}else{
					ok = false;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|deleteReg|:"+ex);
			}finally{
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		public IntAlumReporte mapeaRegId(Connection con, String CodigoPersonal, String folio) throws SQLException{
			IntAlumReporte intAlumRep = new IntAlumReporte();
			PreparedStatement ps = null;
			ResultSet rs = null;		
			try{ 
				ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO"
						+ " FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
				 
				ps.setString(1, CodigoPersonal);
				ps.setString(2, folio);
				
				rs = ps.executeQuery();
				
				if(rs.next()){
					intAlumRep.mapeaReg(rs);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|mapeaRegId|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			return intAlumRep;
		}
		
		public boolean existeReg(Connection con, String codigoPersonal, String folio ) throws SQLException{
			PreparedStatement ps = null;		
			ResultSet rs = null;
			boolean ok = false;
			try{ 
				ps = con.prepareStatement("SELECT * FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '99')");
				 
				ps.setString(1, codigoPersonal);
				ps.setString(2, folio);
				
				rs = ps.executeQuery();			
				if(rs.next()){
					ok = true;
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|existeReg|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			return ok;
		}
		
		public String maximoReg(Connection con, String codigoPersonal ) throws SQLException{
			PreparedStatement ps 	= null;
			ResultSet rs 			= null;
			String maximo			= "1";
			
			try{ 
				ps = con.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INT_ALUM_REPORTE WHERE CODIGO_PERSONAL = ?");
				 
				ps.setString(1,codigoPersonal);			
				rs = ps.executeQuery();
				
				if(rs.next()){
					maximo = rs.getString("MAXIMO");
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|existeReg|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { ps.close(); } catch (Exception ignore) { }
			}
			return maximo;
		}
		
		public ArrayList<IntAlumReporte> getListAll(Connection conn, String orden) throws SQLException{
			ArrayList<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";
			try{			
				comando = "SELECT CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO" +
		 				" FROM ENOC.INT_ALUM_REPORTE "+orden;
				rs = st.executeQuery(comando);
				while (rs.next()){
					IntAlumReporte acceso = new IntAlumReporte();
					acceso.mapeaReg(rs);
					lista.add(acceso);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return lista;
		}
		
		public ArrayList<IntAlumReporte> listIntAlumReporte(Connection conn, String codigoPersonal ) throws SQLException{
			ArrayList<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";
			try{			
				comando = "SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO" +
		 				" FROM ENOC.INT_ALUM_REPORTE "+
						" WHERE CODIGO_PERSONAL="+codigoPersonal;
				rs = st.executeQuery(comando);
				while (rs.next()){
					IntAlumReporte acceso = new IntAlumReporte();
					acceso.mapeaReg(rs);
					lista.add(acceso);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|listIntAlumReporte|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return lista;
		}
		
		public ArrayList<IntAlumReporte> listReportesPorDormi(Connection conn, String fechaIni, String fechaFin, String dormitorio, String orden) throws SQLException{
			ArrayList<IntAlumReporte> lista 	= new ArrayList<IntAlumReporte>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando	= "";
			try{			
				comando = " SELECT CODIGO_PERSONAL, FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REPORTE_ID, COMENTARIO, USUARIO, CANTIDAD, DORMITORIO"
						+ " FROM ENOC.INT_ALUM_REPORTE"
						+ " WHERE DORMITORIO = '"+dormitorio+"'"
						+ " AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') "+ orden;
				rs = st.executeQuery(comando);
				while (rs.next()){
					IntAlumReporte acceso = new IntAlumReporte();
					acceso.mapeaReg(rs);
					lista.add(acceso);
				}
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|listReportesPorFecha|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return lista;
		}

		
		/*
		public static HashMap<String, String> mapCostoCredito(Connection conn, String cargas, String estados) throws SQLException{
			
			HashMap<String,String> map		= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, MAX(COALESCE(COSTO_CREDITO,0)) AS COSTO"
						+ " FROM MATEO.FES_CC_MATERIA"
						+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
						+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND USUARIO IN("+estados+"))"
						+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("COSTO"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|mapCostoCredito|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}
		
		public static HashMap<String, String> mapCreditosCarga(Connection conn, String cargas, String estados) throws SQLException{
			
			HashMap<String,String> map		= new HashMap<String,String>();
			Statement st 							= conn.createStatement();
			ResultSet rs		 					= null;
			String comando							= "";
					
			try{
				comando = " SELECT MATRICULA, CARGA_ID, BLOQUE, SUM(COALESCE(CREDITOS,0)) AS TOTAL"
						+ " FROM MATEO.FES_CC_MATERIA"
						+ " WHERE MATRICULA||CARGA_ID||BLOQUE IN"
						+ " (SELECT CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+cargas+") AND USUARIO IN("+estados+"))"
						+ " GROUP BY MATRICULA, CARGA_ID, BLOQUE";
				rs = st.executeQuery(comando);			
				while (rs.next()){				
					map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE"), rs.getString("TOTAL"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.internado.IntAlumReporteUtil|mapCreditosCarga|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return map;
		}*/
	}