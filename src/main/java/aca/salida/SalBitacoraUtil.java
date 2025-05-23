package aca.salida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalBitacoraUtil {

	public ArrayList<SalBitacora> getListAll(Connection conn, String salidaId, String orden ) throws SQLException{
		ArrayList<SalBitacora> listSalidaGrupo 	= new ArrayList<SalBitacora>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT SALIDA_ID, CODIGO_PERSONAL, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, USUARIO "+			        
					" FROM ENOC.SAL_BITACORA WHERE SALIDA_ID ='"+salidaId+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				SalBitacora grupo = new SalBitacora();				
				grupo.mapeaReg(rs);
				listSalidaGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacoraUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listSalidaGrupo;
	}
	
	public static String getFecha( Connection conn, String salidaId, String estado) throws SQLException{
		PreparedStatement ps = null;
    	ResultSet rs = null;
    	String fecha = "";
    	
		try{ 
	    	ps = conn.prepareStatement("SELECT FECHA" +
				" FROM ENOC.SAL_BITACORA WHERE SALIDA_ID = TO_NUMBER(?,'99999') AND ESTADO = ?"); 
			ps.setString(1,salidaId);
			ps.setString(2,estado);
			rs = ps.executeQuery();
			if (rs.next()){
				fecha = rs.getString("FECHA");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.salida.SalBitacoraUtil|getFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return fecha;
	}
}