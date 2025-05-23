package aca.musica;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class MusiPeriodoUtil {

public ArrayList<MusiPeriodo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MusiPeriodo> lisPeriodo	= new ArrayList<MusiPeriodo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT PERIODO_ID, PERIODO_NOMBRE,  TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL," +
					" CICLO_ESCOLAR, ESTADO, NUMPAGARE, COSTOPAGARE " +
					"FROM ENOC.MUSI_PERIODO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MusiPeriodo mp = new MusiPeriodo();
				mp.mapeaReg(rs);
				lisPeriodo.add(mp);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.musica.MusiPeriodoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisPeriodo;
	}
}