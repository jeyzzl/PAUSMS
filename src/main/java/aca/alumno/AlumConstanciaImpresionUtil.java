package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumConstanciaImpresionUtil {
	
	public boolean insertReg(Connection conn, AlumConstanciaImpresion alumConstanciaImpresion) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_CONSTANCIA_IMPRESION"+ 
				"(CONSTANCIA_ID, CODIGO_PERSONAL, FECHA_IMPRESION) "+
				"VALUES( TO_NUMBER(?,'9999'), ?, now() )");
					
			ps.setString(1, alumConstanciaImpresion.getConstanciaId());
			ps.setString(2, alumConstanciaImpresion.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaImpresionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public AlumConstanciaImpresion mapeaRegId( Connection conn, String constanciaId, String codigoPersonal, String fechaImpresion) throws SQLException{
		AlumConstanciaImpresion alumConstanciaImpresion = new AlumConstanciaImpresion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT * "+
				"FROM ENOC.ALUM_CONSTANCIA_IMPRESION "+ 
				"WHERE CONSTANCIA_ID = ? AND CODIGO_PERSONAL = ? AND FECHA_IMPRESION = ? ");
			ps.setString(1, constanciaId);	
			ps.setString(2, codigoPersonal);	
			ps.setString(3, fechaImpresion);	
			
			rs = ps.executeQuery();
			if (rs.next()){
				alumConstanciaImpresion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaImpresionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumConstanciaImpresion;
	}

	public static ArrayList<AlumConstanciaImpresion> getListUsuario(Connection conn, String constanciaId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<AlumConstanciaImpresion> list	= new ArrayList<AlumConstanciaImpresion>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CONSTANCIA_ID, CODIGO_PERSONAL, to_char(FECHA_IMPRESION, 'MM/DD/YYYY HH12:MI:SS' ) AS FECHA_IMPRESION " +
 				" FROM ENOC.ALUM_CONSTANCIA_IMPRESION WHERE CONSTANCIA_ID = '"+constanciaId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumConstanciaImpresion obj = new AlumConstanciaImpresion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumConstanciaImpresionUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return list;
	}
	
}
