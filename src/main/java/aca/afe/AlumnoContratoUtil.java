package aca.afe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumnoContratoUtil {
	
	public AlumnoContrato mapeaRegId( Connection conn, String codigoPersonal, String cargaId ) throws SQLException, IOException{
 		AlumnoContrato alumnoC = new AlumnoContrato();
		PreparedStatement ps = null;
 		ResultSet rs = null;
 		try{
	 		ps = conn.prepareStatement("SELECT "+
	 			" CODIGO_PERSONAL, CARGA_ID, PLAZA_ID, STATUS, NUM_HORAS,PRECIO_HORA,TIPO_PLAZA, "+
	 			" TIPO_BECA,PUESTO_ID, TURNO, DIAS, DEPARTAMENTO,JEFE,INDUSTRIAL "+
	 			" FROM ENOC.ALUMNO_CONTRATO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? "); 
	 		ps.setString(1, codigoPersonal);
	 		ps.setString(2, cargaId);
	 		rs = ps.executeQuery();
	 		if (rs.next()){
	 			alumnoC.mapeaReg(rs);
	 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoContratoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 		return alumnoC;
 	}

	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUMNO_CONTRATO "+
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoContratoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	
	public ArrayList<AlumnoContrato> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumnoContrato> lisAcceso 	= new ArrayList<AlumnoContrato>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{			
			comando = "SELECT MATRICULA,DEP_CAJA, DEP_BANCO, DEP_DIEZMOS, DEP_COMPRAS,DEP_COMPRAS_BONIFICABLES," +
					" TOTAL_DEPOSITOS, BONIFICACION_GEMA, BONIFICACION_UM " +
					" FROM ENOC.COLPORTOR_BONIFICACION_GEMA "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumnoContrato acceso = new AlumnoContrato();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.afe.AlumnoContratoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}

}