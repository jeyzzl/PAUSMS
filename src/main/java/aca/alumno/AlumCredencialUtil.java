// Clase para la tabla de Modulo
package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumCredencialUtil{
	
	public boolean insertReg(Connection conn, AlumCredencial alumCredencial ) throws SQLException{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_CREDENCIAL"+ 
				"(CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, FECHA, COTEJADO, PERIODO1, PERIODO2, PERIODO3) "+
				"VALUES( ?, ?, ?, UPPER(?)," +
				"TO_DATE( TO_CHAR(now(),'DD-MM-YYYY')||TO_CHAR(now(),'HH:MI:SS AM'),'DD-MM-YYYY HH:MI:SS AM'), ?, ?, ?, ?)");
			ps.setString(1, alumCredencial.getCodigoPersonal());
			ps.setString(2, alumCredencial.getNombres());
			ps.setString(3, alumCredencial.getApellidos());
			ps.setString(4, alumCredencial.getCarrera());			
			ps.setString(5, alumCredencial.getCotejado());
			ps.setString(6, alumCredencial.getPeriodo1());
			ps.setString(7, alumCredencial.getPeriodo2());
			ps.setString(8, alumCredencial.getPeriodo3());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn, AlumCredencial alumCredencial ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_CREDENCIAL "+ 
				" SET NOMBRES = ?,"+
				" APELLIDOS = ?,"+
				" CARRERA = UPPER(?),"+
				" FECHA = TO_DATE( TO_CHAR(now(),'DD-MM-YYYY')||TO_CHAR(now(),'HH:MI:SS AM'),'DD-MM-YYYY HH:MI:SS AM'),"+
				" COTEJADO = ?,"+
				" PERIODO1 = ?,"+
				" PERIODO2 = ?,"+
				" PERIODO3 = ?"+
				" WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, alumCredencial.getNombres());
			ps.setString(2, alumCredencial.getApellidos());
			ps.setString(3, alumCredencial.getCarrera());
			ps.setString(4, alumCredencial.getCotejado());			
			ps.setString(5, alumCredencial.getPeriodo1());
			ps.setString(6, alumCredencial.getPeriodo2());
			ps.setString(7, alumCredencial.getPeriodo3());
			ps.setString(8, alumCredencial.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateFoto(Connection conn, byte[] foto, String codigoPersonal ) throws SQLException{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_CREDENCIAL "+ 
				"SET FOTO = ? "+				
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setBytes(1, foto);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|updateFoto|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	/*
	 * Actualiza la foto del alumno, tomandola del directorio WEB_INF/fotos en el servidor de aplicaciones 
	 * */
//	public boolean updateFoto(Connection conn, String dirFoto ) throws Exception{
//		PreparedStatement ps 	= null;
//		boolean ok 				= false;
//		byte[] foto				= null;
//		try{
//			java.io.File f = new java.io.File(dirFoto);
//			if(f.exists()){
//				foto = new byte[(int)f.length()];
//			}
//			
//			ps = conn.prepareStatement("UPDATE ENOC.ALUM_CREDENCIAL "+ 
//				"SET FOTO = ? "+				
//				"WHERE CODIGO_PERSONAL = ? ");
//			ps.setBytes(1, foto);
//			ps.setString(2, codigoPersonal);
//			
//			if (ps.executeUpdate()== 1)
//				ok = true;	
//			else
//				ok = false;	
//			
//		}catch(Exception ex){
//			System.out.println("Error - aca.alumno.AlumCredencialUtil|updateFoto|:"+ex);		
//		}finally{
//			try { ps.close(); } catch (Exception ignore) { }
//			foto=null;
//		}
//		return ok;
//	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_CREDENCIAL "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumCredencial mapeaRegId( Connection conn, String codigoPersonal, String folio ) throws SQLException{
		AlumCredencial alumCredencial = new AlumCredencial();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT"+
				" CODIGO_PERSONAL, NOMBRES, CARRERA, COTEJADO, PERIODO1, PERIODO2, PERIODO3"+
				" FROM ENOC.ALUM_CREDENCIAL"+ 
				" WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){				
				alumCredencial.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumCredencial;
	}

	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_CREDENCIAL "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public static boolean existeFotoArchivo(String dirFoto) throws Exception{				
		boolean ok = false;
		try{
			java.io.File f = new java.io.File(dirFoto);		
			if(f.exists()){
				ok	= true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|existeFotoArchivo|:"+ex);
		}		
		return ok;
	}	
	
	public static String getFechaActualizacion(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;		
		ResultSet 		rs		= null;
		String actualizado 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(FECHA,'DD/MM/YYYY HH:MI:SS AM'),'---') AS ACTUALIZADO FROM ENOC.ALUM_CREDENCIAL "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				actualizado = rs.getString("ACTUALIZADO");
			}		
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|getFechaActualizacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return actualizado;
	}
	
	public ArrayList<AlumCredencial> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumCredencial> lisCredencial	= new ArrayList<AlumCredencial>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, COTEJADO, PERIODO1, PERIODO2, PERIODO3 FROM ENOC.ALUM_CREDENCIAL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				AlumCredencial alumno = new AlumCredencial();
				alumno.mapeaReg(rs);
				lisCredencial.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumCredencialUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCredencial;
	}	
}