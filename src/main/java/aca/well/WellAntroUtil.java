package aca.well;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class WellAntroUtil {
	
	public boolean insertReg(Connection conn, WellAntro wellAntro ) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{
 			ps = conn.prepareStatement("INSERT INTO ENOC.WELL_ANTRO"+ 
 				"(PERIODO_ID, CODIGO_PERSONAL, FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, DIETA) "+
 				"VALUES( ?,?, TO_DATE(?,'DD/MM/YYYY'),TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.99'),TO_NUMBER(?,'999.99'),?)");
 			ps.setString(1, wellAntro.getPeriodoId());
 			ps.setString(2, wellAntro.getCodigoPersonal());
 			ps.setString(3, wellAntro.getFecha());
 			ps.setString(4, wellAntro.getPeso());
 			ps.setString(5, wellAntro.getTalla());
 			ps.setString(6, wellAntro.getCintura());
 			ps.setString(7, wellAntro.getGrasa());
 			ps.setString(8, wellAntro.getMusculo());
 			ps.setString(9, wellAntro.getImc());
 			ps.setString(10, wellAntro.getDieta());
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;				
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.well.WellAntroUtil|insertReg|:"+ex);			
 		}finally{
 			try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}	
	
	public boolean updateReg(Connection conn, WellAntro wellAntro ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.WELL_ANTRO SET"					
	 				+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),"
	 				+ " PESO = TO_NUMBER(?,'999.99'),"
	 				+ " TALLA = TO_NUMBER(?,'999.99'),"
	 				+ " CINTURA = TO_NUMBER(?,'999.99'),"
	 				+ " GRASA = TO_NUMBER(?,'999.99'),"
	 				+ " MUSCULO = TO_NUMBER(?,'999.99'),"
	 				+ " IMC = TO_NUMBER(?,'999.99'),"
	 				+ " DIETA = ? "
	 				+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");		
				
	 			ps.setString(1, wellAntro.getFecha());
	 			ps.setString(2, wellAntro.getPeso());
	 			ps.setString(3, wellAntro.getTalla());
	 			ps.setString(4, wellAntro.getCintura());
	 			ps.setString(5, wellAntro.getGrasa());
	 			ps.setString(6, wellAntro.getMusculo());
	 			ps.setString(7, wellAntro.getImc());
	 			ps.setString(8, wellAntro.getDieta());
	 			ps.setString(9, wellAntro.getPeriodoId());
	 			ps.setString(10, wellAntro.getCodigoPersonal());
		
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public boolean delete(Connection conn, String periodoId, String codigoPersonal ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.WELL_ANTRO "
					+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public WellAntro mapeaRegId( Connection conn, String periodoId, String codigoPersonal ) throws SQLException, IOException{
 		ResultSet rs = null;
 		PreparedStatement ps = null; 
 		WellAntro wellAntro = new WellAntro();
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, DIETA "+
	 			" FROM ENOC.WELL_ANTRO WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? "); 
	 		ps.setString(1, periodoId);
	 		ps.setString(2, codigoPersonal);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			wellAntro.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntro|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return wellAntro;
 	}
	
	public boolean existeReg(Connection conn, String periodoId, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.WELL_ANTRO "
					+ " WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?");
			ps.setString(1, periodoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<WellAntro> getListAll(Connection conn, String periodoId ) throws SQLException{
		
		ArrayList<WellAntro> listAntro 	= new ArrayList<WellAntro>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT "
					+ " PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, DIETA "
					+ " FROM ENOC.WELL_ANTRO "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				WellAntro wellAntro = new WellAntro();
				wellAntro.mapeaReg(rs);
				listAntro.add(wellAntro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return listAntro;
	}
	
	public ArrayList<WellAntro> getListAllEmpleado(Connection conn, String codigoPersonal ) throws SQLException{
		
		ArrayList<WellAntro> listAntro 	= new ArrayList<WellAntro>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT "
					+ " PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, DIETA "
					+ " FROM ENOC.WELL_ANTRO WHERE CODIGO_PERSONAL = "+codigoPersonal; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				WellAntro wellAntro = new WellAntro();
				wellAntro.mapeaReg(rs);
				listAntro.add(wellAntro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|getListAllEmpleado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return listAntro;
	}
	
	public HashMap<String, WellAntro> mapEmpleadosPorPeriodo(Connection conn, String periodoId) throws SQLException{
		
		HashMap<String, WellAntro> map		= new HashMap<String, WellAntro>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = " SELECT PERIODO_ID, CODIGO_PERSONAL, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PESO, TALLA, CINTURA, GRASA, MUSCULO, IMC, DIETA FROM ENOC.WELL_ANTRO "
					+ " WHERE PERIODO_ID = '"+periodoId+"' ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){	
				WellAntro emp = new WellAntro();
				emp.mapeaReg(rs);
				map.put(rs.getString("CODIGO_PERSONAL"), emp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.well.WellAntroUtil|mapAlumnosPorPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		return map;
	}
	
}
