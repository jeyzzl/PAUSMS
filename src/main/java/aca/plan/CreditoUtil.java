// Clase Util para la tabla de Mapa_Plan
package aca.plan;

import java.sql.*;
import java.util.ArrayList;

public class CreditoUtil{
	
	public boolean insertReg(Connection conn, MapaCredito mapaCredito ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.MAPA_CREDITO"+ 
				"(PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO ) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99.99'), "+
				"TO_NUMBER(?,'99.99'), ?)");
					
			ps.setString(1, mapaCredito.getPlanId());
			ps.setString(2, mapaCredito.getCiclo());
			ps.setString(3, mapaCredito.getCreditos());
			ps.setString(4, mapaCredito.getOptativos());
			ps.setString(5, mapaCredito.getTitulo());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, MapaCredito mapaCredito ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.MAPA_CREDITO " + 
				"SET CREDITOS = TO_NUMBER(?,'99.99'), " +
				"OPTATIVOS = TO_NUMBER(?,'99.99'), " +
				"TITULO = ? " +
				"WHERE PLAN_ID = ? "+
				"AND CICLO = TO_NUMBER(?,'99')");
			ps.setString(1, mapaCredito.getCreditos());			
			ps.setString(2, mapaCredito.getOptativos());
			ps.setString(3, mapaCredito.getTitulo());
			ps.setString(4, mapaCredito.getPlanId());
			ps.setString(5, mapaCredito.getCiclo());			
						
			if (ps.executeUpdate()== 1){
				ok = true;				
				
			}else{
				ok = false;
				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String planId, String ciclo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.MAPA_CREDITO " + 
					"WHERE PLAN_ID = ? "+
					"AND CICLO = TO_NUMBER(?,'99')");
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public MapaCredito mapeaRegId( Connection conn, String planId, String ciclo) throws SQLException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		MapaCredito mapaCredito = new MapaCredito();
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CICLO, "+
					"TO_CHAR(COALESCE(CREDITOS,0),'99.99') AS CREDITOS, TO_CHAR(COALESCE(OPTATIVOS,0),'99.99') AS OPTATIVOS, TITULO "+													
					"FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO =  TO_NUMBER(?,'99')"); 
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapaCredito.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return mapaCredito;		
	}	
	
	public boolean existeReg(Connection conn, String planId, String ciclo) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO= TO_NUMBER(?,'99')"); 
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			rs = ps.executeQuery();
			
			if (rs.next()){
				ok = true;
			}else{
				ok = false;
			}

		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static float creditosPlan( Connection conn, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		float nCreditos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS+OPTATIVOS) AS CRED FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			if (rs.next()){
				nCreditos = rs.getFloat("CRED");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|creditosPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float creditosObligatorios(Connection conn, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		float nCreditos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT SUM(CREDITOS) AS CRED FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			if (rs.next()){
				nCreditos = rs.getFloat("CRED");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|creditosObligatorios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static float creditosElecPlan( Connection conn, String planId) throws SQLException, Exception{
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;		
		float nCreditos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT SUM(OPTATIVOS) AS OPTATIVOS FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ?"); 
			ps.setString(1, planId);			
			rs = ps.executeQuery();
			if (rs.next()){
				nCreditos = rs.getFloat("OPTATIVOS");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|creditosElecPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nCreditos;
	}
	
	public static String getTitulo(Connection conn, String planId, String ciclo) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		String titulo = "";
				
		try{
			ps = conn.prepareStatement("SELECT TITULO FROM ENOC.MAPA_CREDITO WHERE PLAN_ID = ? AND CICLO = TO_NUMBER(?,'99') "); 
			ps.setString(1, planId);
			ps.setString(2, ciclo);
			rs = ps.executeQuery();
			if (rs.next() && rs.getString("TITULO")!=null && !rs.getString("TITULO").equals("null")){
				titulo = rs.getString("TITULO");				
			}else{				
				switch(Integer.parseInt(ciclo)){
					case 6:  titulo = "SEXTO"; 			break;
					case 7:	 titulo = "SEPTIMO";		break;
					case 8:	 titulo = "OCTAVO";			break;
					case 9:	 titulo = "NOVENO";			break;
					case 10: titulo = "DECIMO";			break;
					case 11: titulo = "UNDECIMO";		break;
					case 12: titulo = "DUODECIMO";		break;
					case 13: titulo = "DECIMO TERCERO";	break;
					case 14: titulo = "DECIMO CUARTO";	break;
					case 15: titulo = "DECIMO QUINTO";	break;
					case 16: titulo = "DECIMO SEXTO";	break;
					case 17: titulo = "DECIMO SEPTIMO";	break;
					case 18: titulo = "DECIMO OCTAVO";	break;
					case 19: titulo = "DECIMO NOVENO";	break;
					case 20: titulo = "VIGESIMO";		break;
				}				
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|getTitulo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return titulo;
	}
		
	public ArrayList<MapaCredito> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MapaCredito> lisCredito	= new ArrayList<MapaCredito>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO FROM ENOC.MAPA_CREDITO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCredito credito = new MapaCredito();
				credito.mapeaReg(rs);
				lisCredito.add(credito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCredito;
	}
	
	public ArrayList<MapaCredito> getLista(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<MapaCredito> lisCredito	= new ArrayList<MapaCredito>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PLAN_ID, CICLO, CREDITOS, OPTATIVOS, TITULO FROM ENOC.MAPA_CREDITO "+
				"WHERE PLAN_ID = '"+planId+"' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MapaCredito credito = new MapaCredito();
				credito.mapeaReg(rs);
				lisCredito.add(credito);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCredito;
	}
	
	public ArrayList<String> getSemetres(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CICLO) AS SEMESTRE FROM ENOC.MAPA_CURSO WHERE PLAN_ID = '"+planId+"' " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("SEMESTRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.CreditoUtil|getSemestres|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
}