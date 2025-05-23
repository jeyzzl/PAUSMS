package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MusiMaestroUtil {

	public ArrayList<MusiMaestro> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<MusiMaestro> lisM = new ArrayList<MusiMaestro>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT MAESTRO_ID, NOMBRE, APELLIDO_MATERNO, APELLIDO_PATERNO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, " +
					  " TELEFONO, CELULAR, CORREO FROM ENOC.MUSI_MAESTRO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MusiMaestro m = new MusiMaestro();
				m.mapeaReg(rs);
				lisM.add(m);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMaestroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisM;
	}
	
	public ArrayList<MusiMaestro> getListMaestros(Connection conn, String periodoId, String orden) throws SQLException{
		
		ArrayList<MusiMaestro> lisM = new ArrayList<MusiMaestro>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT MAESTRO_ID, NOMBRE, APELLIDO_MATERNO, APELLIDO_PATERNO, F_NACIMIENTO, TELEFONO, CELULAR, CORREO" +
					  " FROM ENOC.MUSI_MAESTRO" +
					  " WHERE MAESTRO_ID IN (SELECT MAESTRO_ID FROM ENOC.MUSI_CALCULO_DET WHERE PERIODO_ID = '"+periodoId+"')" +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MusiMaestro m = new MusiMaestro();
				m.mapeaReg(rs);
				lisM.add(m);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiMaestroUtil|getListMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisM;
	}

}