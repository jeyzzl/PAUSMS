package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecPlazasUtil {
	
	public boolean insertReg(Connection conn, BecPlazas becPlazas) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_PLAZAS"+ 
				"(ID_EJERCICIO, ID_CCOSTO, PERIODO_ID, " +
				" NUM_PLAZAS, NUM_INDUSTRIALES, NUM_TEMPORALES, NUM_PREINDUSTRIALES, NUM_POSGRADO)"+
				" VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
					
			ps.setString(1,  becPlazas.getIdEjercicio());
			ps.setString(2,  becPlazas.getIdCcosto());
			ps.setString(3,  becPlazas.getPeriodoId());
			ps.setString(4,  becPlazas.getNumPlazas());
			ps.setString(5,  becPlazas.getNumIndustriales());
			ps.setString(6,  becPlazas.getNumTemporales());
			ps.setString(7,  becPlazas.getNumPreIndustriales());
			ps.setString(8,  becPlazas.getNumPosgrado());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazasUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecPlazas becPlazas) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PLAZAS"+ 
				" SET NUM_PLAZAS = ?, NUM_INDUSTRIALES = ?, NUM_TEMPORALES = ?, NUM_PREINDUSTRIALES = ?, NUM_POSGRADO = ?"+				
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? ");
			
			ps.setString(1,  becPlazas.getNumPlazas());
			ps.setString(2,  becPlazas.getNumIndustriales());
			ps.setString(3,  becPlazas.getNumTemporales());
			ps.setString(4,  becPlazas.getNumPreIndustriales());
			ps.setString(5,  becPlazas.getNumPosgrado());
			ps.setString(6,  becPlazas.getIdEjercicio());
			ps.setString(7,  becPlazas.getIdCcosto());
			ps.setString(8,  becPlazas.getPeriodoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazasUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeReg(Connection conn, String idEjercicio, String idCcosto, String periodoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  periodoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazasUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String idEjercicio, String idCcosto, String periodoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_PLAZAS"+ 
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? ");
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazasUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecPlazas mapeaRegId(Connection conn, String idEjercicio, String idCcosto, String periodoId ) throws SQLException{
		BecPlazas becPlazas = new BecPlazas();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT NUM_PREINDUSTRIALES, NUM_POSGRADO, NUM_TEMPORALES, NUM_INDUSTRIALES, NUM_PLAZAS, ID_EJERCICIO, ID_CCOSTO, PERIODO_ID" +
					" FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND PERIODO_ID = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becPlazas.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPlazasUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPlazas;
	}
	
		
		public ArrayList<BecPlazas> getListAll(Connection conn, String orden) throws SQLException{
				
			ArrayList<BecPlazas> lis 		= new ArrayList<BecPlazas>();
			Statement st 					= conn.createStatement();
			ResultSet rs 					= null;
			String comando					= "";
			
			try{
				comando = "SELECT NUM_PREINDUSTRIALES, NUM_POSGRADO, NUM_TEMPORALES, NUM_INDUSTRIALES, NUM_PLAZAS, ID_EJERCICIO, ID_CCOSTO, FROM ENOC.BEC_ACCESO "+orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					BecPlazas obj= new BecPlazas();
					obj.mapeaReg(rs);
					lis.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil|getListAll|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lis;
		}		
		
		public HashMap<String,BecPlazas> getBecPlazas(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, BecPlazas> mapa = new HashMap<String, BecPlazas>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";			
			String llave			= "";
			try{
				comando = "SELECT * FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' ";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					BecPlazas obj = new BecPlazas();
					obj.mapeaReg(rs);
					llave = obj.getIdCcosto();
					mapa.put(llave, obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		// NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES
		public HashMap<String,String> getBecPlazasDepto(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = " SELECT ID_CCOSTO, NUM_PLAZAS+NUM_TEMPORALES+NUM_POSGRADO+NUM_INDUSTRIALES+NUM_PREINDUSTRIALES AS TOTAL"
						+ " FROM ENOC.BEC_PLAZAS"
						+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'"
						+ " AND PERIODO_ID = '"+periodoId+"'";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("TOTAL"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasDepto|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasBasicas(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT ID_CCOSTO, SUM(NUM_PLAZAS) AS SUMA FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO ORDER BY 1";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("SUMA"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasBasicas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasTemporales(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT ID_CCOSTO, SUM(NUM_TEMPORALES) AS SUMA FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO ORDER BY 1";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("SUMA"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasTemporales|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasIndustriales(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT ID_CCOSTO, SUM(NUM_INDUSTRIALES) AS SUMA FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO ORDER BY 1";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("SUMA"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasIndustriales|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasPreindustriales(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT ID_CCOSTO, SUM(NUM_PREINDUSTRIALES) AS SUMA FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO ORDER BY 1";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("SUMA"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasPreindustriales|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasPosgrado(Connection conn, String idEjercicio, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT ID_CCOSTO, SUM(NUM_POSGRADO) AS SUMA FROM ENOC.BEC_PLAZAS WHERE ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO ORDER BY 1";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("ID_CCOSTO"), rs.getString("SUMA"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasPosgrado|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
		
		public HashMap<String,String> getBecPlazasAsignadas(Connection conn, String idEjercicio, String idCcosto, String periodoId) throws SQLException{
			
			HashMap<String, String> mapa = new HashMap<String, String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT TIPO, COUNT(CODIGO_PERSONAL) AS CONTEO FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = " +
						"'"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY ID_CCOSTO, TIPO";
				rs = st.executeQuery(comando);
				while (rs.next()){					
					mapa.put(rs.getString("TIPO"), rs.getString("CONTEO"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPlazasUtil |getBecPlazasAsignadas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
}
