/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author elifo
 *
 */
public class HcaRangoUtil {
	
	public boolean insertReg(Connection conn, HcaRango hcaRango) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_RANGO(NIVEL_ID, MODALIDAD_ID, RANGO_ID, RANGO_INI, RANGO_FIN, VALOR)" +
				" VALUES(TO_NUMBER(?, '99'), " +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '999.99'))");
			
			ps.setString(1, hcaRango.getNivelId());
			ps.setString(2, hcaRango.getModalidadId());
			ps.setString(3, hcaRango.getRangoId());
			ps.setString(4, hcaRango.getRangoIni());
			ps.setString(5, hcaRango.getRangoFin());
			ps.setString(6, hcaRango.getValor());
						
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
			
			if(ps!=null) ps.close();
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|insertReg|:"+ex);
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaRango hcaRango) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_RANGO" + 
				" SET RANGO_INI = TO_NUMBER(?,'99')," +
				" RANGO_FIN = TO_NUMBER(?,'99')," +
				" VALOR = TO_NUMBER(?,'999.99')" +
				" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
				" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
				" AND RANGO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, hcaRango.getRangoIni());
			ps.setString(2, hcaRango.getRangoFin());
			ps.setString(3, hcaRango.getValor());
			ps.setString(4, hcaRango.getNivelId());
			ps.setString(5, hcaRango.getModalidadId());
			ps.setString(6, hcaRango.getRangoId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}	
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String nivelId, String modalidadId, String rangoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_RANGO"+ 
				" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
				" AND MODALIDAD_ID = TO_NUMBER(?,'99')" +
				" AND RANGO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			ps.setString(3, rangoId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaRango mapeaRegId(Connection con, String nivelId, String modalidadId, String rangoId) throws SQLException{
		HcaRango hcaRango = new HcaRango();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT VALOR, RANGO_INI, RANGO_FIN, RANGO_ID, MODALIDAD_ID, NIVEL_ID FROM ENOC.HCA_RANGO" + 
					" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
					" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
					" AND RANGO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			ps.setString(3, rangoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				hcaRango.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return hcaRango;
	}
	
	public boolean existeReg(Connection conn, String nivelId, String modalidadId, String rangoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_RANGO" + 
					" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
					" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
					" AND RANGO_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			ps.setString(3, rangoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return ok;
	}
	
	public String maximoReg(Connection conn, String nivelId, String modalidadId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(RANGO_ID)+1,1) AS MAXIMO " +
					" FROM ENOC.HCA_RANGO" + 
					" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
					" AND MODALIDAD_ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return maximo;
	}
	
	public static String getValor(Connection conn, String nivelId, String modalidadId, String numAlumnos) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String valor			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT VALOR FROM ENOC.HCA_RANGO" + 
					" WHERE NIVEL_ID = TO_NUMBER(?, '99')" +
					" AND MODALIDAD_ID = TO_NUMBER(?, '99')" +
					" AND TO_NUMBER(?, '999') BETWEEN RANGO_INI AND RANGO_FIN");
			
			ps.setString(1, nivelId);
			ps.setString(2, modalidadId);
			ps.setString(3, numAlumnos);
			
			rs = ps.executeQuery();
			if (rs.next())
				valor = rs.getString("VALOR");
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|getValor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return valor;
	}
	
	public ArrayList<HcaRango> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<HcaRango> lisRango	= new ArrayList<HcaRango>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT * " + 
					" FROM ENOC.HCA_RANGO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaRango rango = new HcaRango();
				rango.mapeaReg(rs);
				lisRango.add(rango);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaRangoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisRango;
	}
}