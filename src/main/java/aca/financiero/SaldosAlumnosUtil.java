package aca.financiero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SaldosAlumnosUtil {
	
	public static HashMap<String, SaldosAlumnos> getListInscritos(Connection conn, String orden)throws SQLException{
		HashMap<String, SaldosAlumnos> lisSaldosAlumnos = new HashMap<String, SaldosAlumnos>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA IN(SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) "+orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				SaldosAlumnos saldosAlumnos = new SaldosAlumnos();
				saldosAlumnos.mapeaReg(rs);
				lisSaldosAlumnos.put(saldosAlumnos.getMatricula(), saldosAlumnos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SaldosAlumnosUtil|getListInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisSaldosAlumnos;
	}
	
	public static HashMap<String, SaldosAlumnos> mapInscritosCarga(Connection conn, String cargaId)throws SQLException{
		HashMap<String, SaldosAlumnos> map = new HashMap<String, SaldosAlumnos>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM NOE.SALDOS_ALUMNOS WHERE MATRICULA IN(SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = '"+cargaId+"' AND ESTADO = 'I')";
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				SaldosAlumnos saldosAlumnos = new SaldosAlumnos();
				saldosAlumnos.mapeaReg(rs);
				map.put(saldosAlumnos.getMatricula(), saldosAlumnos);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SaldosAlumnosUtil|mapInscritosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
}