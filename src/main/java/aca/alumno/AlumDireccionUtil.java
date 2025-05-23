package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumDireccionUtil {
	
	public boolean insertReg(Connection conn, AlumDireccion alumDireccion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{

			
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_DIRECCION"+ 
				"(CODIGO_PERSONAL, CALLE, COLONIA, COD_POSTAL, APARTADO, " +
				"PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
				"FECHA, ESTADO, TELEFONO, NUMERO) "+
				"VALUES( ?, ?, ?, ?,?," +
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_NUMBER(?,'999'), "+
				"TO_DATE(?,'DD/MM/YYYY'), ?, ?, ? ) ");
			
			ps.setString(1, alumDireccion.getCodigoPersonal());
			ps.setString(2, alumDireccion.getCalle());
			ps.setString(3, alumDireccion.getColonia());
			ps.setString(4, alumDireccion.getCodPostal());
			ps.setString(5, alumDireccion.getApartado());
			ps.setString(6, alumDireccion.getPaisId());
			ps.setString(7, alumDireccion.getEstadoId());
			ps.setString(8, alumDireccion.getCiudadId());
			ps.setString(9, alumDireccion.getFecha());
			ps.setString(10, alumDireccion.getEstado());
			ps.setString(11, alumDireccion.getTelefono());
			ps.setString(12, alumDireccion.getNumero());

						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	

	public boolean updateReg(Connection conn, AlumDireccion alumDireccion ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_DIRECCION"+ 
				" SET "+
				" CALLE = ?,"+
				" COLONIA = ?,"+
				" COD_POSTAL = ?,"+
				" APARTADO = ?,"+
				" PAIS_ID = TO_NUMBER(?,'999'),"+
				" ESTADO_ID = TO_NUMBER(?,'999'),"+
				" CIUDAD_ID = TO_NUMBER(?,'999'),"+
				" FECHA = TO_DATE(?,'DD/MM/YYYY')," +
				" ESTADO = ? ," +
				" TELEFONO = ?, " +
				" NUMERO = ? "+
				" WHERE CODIGO_PERSONAL = ?");			
			ps.setString(1, alumDireccion.getCalle());
			ps.setString(2, alumDireccion.getColonia());
			ps.setString(3, alumDireccion.getCodPostal());
			ps.setString(4, alumDireccion.getApartado());
			ps.setString(5, alumDireccion.getPaisId());
			ps.setString(6, alumDireccion.getEstadoId());
			ps.setString(7, alumDireccion.getCiudadId());			
			ps.setString(8, alumDireccion.getFecha());
			ps.setString(9, alumDireccion.getEstado());
			ps.setString(10, alumDireccion.getTelefono());
			ps.setString(11, alumDireccion.getNumero());
			ps.setString(12, alumDireccion.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
		

	public boolean deleteReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_DIRECCION "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumDireccion mapeaRegId( Connection conn, String codigoPersonal ) throws SQLException{
		AlumDireccion alumDireccion = new AlumDireccion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, "+
				" CALLE, COLONIA, COD_POSTAL, APARTADO, " +
				" PAIS_ID, ESTADO_ID, CIUDAD_ID, " +
				" TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO, TELEFONO, NUMERO " +
				" FROM ENOC.ALUM_DIRECCION "+ 
				" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumDireccion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumDireccion;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_DIRECCION "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<AlumDireccion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumDireccion> lisDireccion		= new ArrayList<AlumDireccion>();
		Statement st 		                		= conn.createStatement();
		ResultSet rs 				                = null;
		String comando                     			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CALLE, COLONIA, " +
					" COD_POSTAL, APARATO, PAIS_ID, ESTADO_ID, CIUDAD_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA," +
					" ESTADO, TELEFONO, NUMERO FROM ENOC.ALUM_DIRECCION "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumDireccion actividad = new AlumDireccion();
				actividad.mapeaReg(rs);
				lisDireccion.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisDireccion;
	}
	
	public ArrayList<AlumDireccion> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<AlumDireccion> lisActiv	= new ArrayList<AlumDireccion>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CALLE, COLONIA," +
					" COD_POSTAL, APARTADO, PAIS_ID, ESTADO_ID, CIUDAD_ID, TO_CHAR (FECHA, 'DD/MM/YYYY') AS FECHA, "+
					" ESTADO, TELEFONO, NUMERO FROM ENOC.ALUM_DIRECCION "+ 
					"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumDireccion actividad = new AlumDireccion();
				actividad.mapeaReg(rs);
				lisActiv.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumDireccionUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActiv;
	}

}