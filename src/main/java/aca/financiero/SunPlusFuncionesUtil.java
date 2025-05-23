/**
 * 
 */
package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Etorres
 *
 */
public class SunPlusFuncionesUtil {	
	
	
	public SunPlusFunciones mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		SunPlusFunciones funcion = new SunPlusFunciones();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT DEPARTAMENTO, FUNCION FROM ENOC.SUNPLUS_FUNCION WHERE DEPARTAMENTO = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				funcion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SunPlusFunciones|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return funcion;
	}
	
	public boolean existeReg(Connection conn, String idCcosto) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.SUNPLUS_FUNCION WHERE DEPARTAMENTO = ?");
			
			ps.setString(1, idCcosto);			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SunPlusFuncionesUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<SunPlusFunciones> lisFunciones(Connection conn, String orden ) throws SQLException{
		ArrayList<SunPlusFunciones> lisFunciones	= new ArrayList<SunPlusFunciones>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT DEPARTAMENTO, FUNCION FROM ENOC.SUNPLUS_FUNCION "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				SunPlusFunciones funcion = new SunPlusFunciones();
				funcion.mapeaReg(rs);
				lisFunciones.add(funcion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SunPlusFuncionesUtil|lisFunciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisFunciones;
	}
	
	// Map de alumnos con permiso financiero
	public static HashMap<String,String> mapaFunciones(Connection conn) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();		
		Statement st 		= conn.createStatement();
		ResultSet rs 			= null;
		String comando = "";
		
		try{
			comando = "SELECT DEPARTAMENTO, FUNCION FROM ENOC.SUNPLUS_FUNCION";			
			rs= st.executeQuery(comando);		
			while(rs.next()){
				map.put(rs.getString("DEPARTAMENTO"), rs.getString("FUNCION"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SunPlusFuncionesUtil|mapaFunciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}