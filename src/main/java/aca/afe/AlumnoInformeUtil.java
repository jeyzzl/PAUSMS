package aca.afe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumnoInformeUtil {
	
	public AlumnoInforme mapeaRegId(Connection con, String codigoPersonal, String cargaId) throws SQLException{
		AlumnoInforme alumnoI = new AlumnoInforme();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = con.prepareStatement("SELECT CODIGO_PERSONAL, CARGA_ID, EJERCICIO_ID, "+
			"CCOSTO_ID, DESCRIPCION, CONTRATO_ID, FECHA_REGISTRO, STATUS, NUM_HORAS, BECA_ADICIONAL "+	
			"FROM ENOC.ALUMNO_INFORME WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? "); 
		ps.setString(1,codigoPersonal);
		ps.setString(2,cargaId);
		rs = ps.executeQuery();
		
		if(rs.next()){
			alumnoI.mapeaReg(rs);
		}
		
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoInformeUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumnoI;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_INFORME "+
					"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1,codigoPersonal);
					
			
			rs = ps.executeQuery();
				if (rs.next()){
				ok = true;
			}else{
			
				ok = false;
			}
		}catch(Exception ex){
		
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
		
	public ArrayList<AlumnoInforme> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumnoInforme> lisInforme 	= new ArrayList<AlumnoInforme>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{			
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, EJERCICIO_ID, "+
					"CCOSTO_ID, DESCRIPCION, CONTRATO_ID, FECHA_REGISTRO, STATUS, NUM_HORAS, BECA_ADICIONAL "+
					" FROM ENOC.ALUMNO_INFORME "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoInforme informe = new AlumnoInforme();
				informe.mapeaReg(rs);
				lisInforme.add(informe);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoInformeUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisInforme;
	}	
	
	public ArrayList<AlumnoInforme> getListMeses(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		
		ArrayList<AlumnoInforme> listMeses	= new ArrayList<AlumnoInforme>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, EJERCICIO_ID, "+
					"CCOSTO_ID, DESCRIPCION, CONTRATO_ID, FECHA_REGISTRO, STATUS, NUM_HORAS, BECA_ADICIONAL "+
					" FROM ENOC.ALUMNO_INFORME " +
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"' ORDER BY FECHA_REGISTRO";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoInforme mes = new AlumnoInforme();
				mes.mapeaReg(rs);
				listMeses.add(mes);
				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoInformeUtil|getListMeses|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return listMeses;
	}
	
}