/**
 * 
 */
package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Jose Torres
 *
 */
public class AlumAptitudUtil {
	
	public boolean insertReg(Connection conn, AlumAptitud alumAptitud ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO"+
				" ENOC.ALUM_APTITUD(CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
				" RESISTENCIA, CARDIO, PESO, TALLA," +
				" IMC, GRASA, ABDOMEN, DIETA)"+
				" VALUES( ?, ?, TO_NUMBER(?, '99'), TO_NUMBER(?, '99')," +
				" TO_NUMBER(?, '99'), TO_NUMBER(?, '99.99'), TO_NUMBER(?, '999.99'), TO_NUMBER(?, '999.99')," +
				" TO_NUMBER(?, '99.9'), TO_NUMBER(?, '99.9'), TO_NUMBER(?, '999.99'), ? ) ");

			ps.setString(1, alumAptitud.getCodigoPersonal());
			ps.setString(2, alumAptitud.getCargaId());
			ps.setString(3, alumAptitud.getFuerza());
			ps.setString(4, alumAptitud.getFlexibilidad());
			ps.setString(5, alumAptitud.getResistencia());
			ps.setString(6, alumAptitud.getCardio());
			ps.setString(7, alumAptitud.getPeso());
			ps.setString(8, alumAptitud.getTalla());
			ps.setString(9, alumAptitud.getImc());
			ps.setString(10, alumAptitud.getGrasa());
			ps.setString(11, alumAptitud.getAbdomen());
			ps.setString(12, alumAptitud.getDieta());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumAptitud alumAptitud ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_APTITUD"+ 
				" SET FUERZA = TO_NUMBER(?, '99')," +
				" FLEXIBILIDAD = TO_NUMBER(?, '99')," +
				" RESISTENCIA = TO_NUMBER(?, '99')," +
				" CARDIO = TO_NUMBER(?, '99.99')," +
				" PESO = TO_NUMBER(?, '999.99')," +
				" TALLA = TO_NUMBER(?, '999.99')," +
				" IMC = TO_NUMBER(?, '99.9')," +
				" GRASA = TO_NUMBER(?, '99.9')," +
				" ABDOMEN = TO_NUMBER(?, '999.99'),"+
				" DIETA = ? "+
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");

			ps.setString(1, alumAptitud.getFuerza());
			ps.setString(2, alumAptitud.getFlexibilidad());
			ps.setString(3, alumAptitud.getResistencia());
			ps.setString(4, alumAptitud.getCardio());
			ps.setString(5, alumAptitud.getPeso());
			ps.setString(6, alumAptitud.getTalla());
			ps.setString(7, alumAptitud.getImc());
			ps.setString(8, alumAptitud.getGrasa());
			ps.setString(9, alumAptitud.getAbdomen());
			ps.setString(10, alumAptitud.getDieta());
			ps.setString(11, alumAptitud.getCodigoPersonal());
			ps.setString(12, alumAptitud.getCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_APTITUD"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");

			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumAptitud mapeaRegId( Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
		AlumAptitud aptitud = new AlumAptitud();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
					" RESISTENCIA, CARDIO, PESO, TALLA," +
					" IMC, GRASA, ABDOMEN, DIETA "+
					" FROM ENOC.ALUM_APTITUD WHERE CODIGO_PERSONAL = ?" + 
					" AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			rs = ps.executeQuery();
			if (rs.next()){				
				aptitud.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		
		return aptitud;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_APTITUD" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public ArrayList<AlumAptitud> getListPeriodo(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<AlumAptitud> listAptitud	= new ArrayList<AlumAptitud>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, CARGA_ID, FUERZA, FLEXIBILIDAD," +
				" RESISTENCIA, CARDIO, PESO, TALLA," +
				" IMC, GRASA, ABDOMEN, DIETA"+
 				" FROM ENOC.ALUM_APTITUD " + 
 				" WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumAptitud aptitud = new AlumAptitud();
				aptitud.mapeaReg(rs);
				listAptitud.add(aptitud);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumAptitudUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return listAptitud;
	}
}