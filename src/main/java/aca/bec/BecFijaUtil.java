package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecFijaUtil {

	public boolean insertReg(Connection conn, BecFija becFija) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_FIJA"+ 
					"(ID_EJERCICIO, ID_CCOSTO, FECHA, USUARIO)" +
					" VALUES( ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ? )");
						
			ps.setString(1,  becFija.getIdEjercicio());
			ps.setString(2,  becFija.getIdCcosto());
			ps.setString(3,  becFija.getFecha());
			ps.setString(4,  becFija.getUsuario());
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|insertReg|:"+ex);
		}
		finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, BecFija becFija) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_FIJA "+ 
					"SET FECHA = TO_DATE(?,'DD/MM/YYYY') " +
					"USUARIO = ? " +
					"WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
			
			ps.setString(1,  becFija.getFecha());
			ps.setString(2,  becFija.getUsuario());
			ps.setString(3,  becFija.getIdEjercicio());
			ps.setString(4,  becFija.getIdCcosto());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|updateReg|:"+ex);
		}
		finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String idEjercicio, String idCcosto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_FIJA"+ 
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?");
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ?"); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			
			rs= ps.executeQuery();		
			if(rs.next()){

				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegCcosto(Connection conn, String idEjercicio, String ccosto) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = '"+ccosto+"'"); 
			ps.setString(1,  idEjercicio);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public BecFija mapeaRegId(Connection conn, String idEjercicio, String idCcosto) throws SQLException{
		BecFija becFija = new BecFija();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becFija.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becFija;
	}
	
	public ArrayList<BecFija> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<BecFija> lis 		= new ArrayList<BecFija>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_FIJA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecFija obj= new BecFija();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
public static HashMap <String,BecFija> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,BecFija> map = new HashMap<String,BecFija>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_FIJA "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				BecFija obj = new BecFija();
				obj.mapeaReg(rs);
				llave = obj.getIdCcosto()+obj.getIdEjercicio();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecaFijaUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public ArrayList<BecFija> getListEjercicio(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<BecFija> lis 			= new ArrayList<BecFija>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT ID_EJERCICIO, ID_CCOSTO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = '"+ejercicioId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecFija obj= new BecFija();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|getListEjercicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> getListCentros(Connection conn, String ejercicioId, String orden) throws SQLException{
		
		ArrayList<String> lis 			= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT ID_CCOSTO FROM ENOC.BEC_FIJA WHERE ID_EJERCICIO = '"+ejercicioId+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				String obj= rs.getString("ID_CCOSTO");
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecFijaUtil|getListCentros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}

}
