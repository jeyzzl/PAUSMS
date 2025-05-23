package aca.objeto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecaUtil {
	
	public ArrayList<Beca> getList(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Beca> lisRep	= new ArrayList<Beca>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando =	" SELECT CONTRATOS.MATRICULA,ENOC.ALUM_NOMBRE(CONTRATOS.MATRICULA) NOMBRE,CONTRATOS.CARGA_ID, CONTRATOS.BLOQUE_ID,  " +
					" (CONTRATOS.NUMERO_HORAS*7.39) IMPORTEDOS," +
					" PLAZA.TIPOPLAZA, PLAZA.CCOSTO_ID, PLAZA.IMPORTE, PLAZA.TIPO_BECA, " +
					" (CASE " +
					" WHEN PLAZA.TIPO_BECA = 1 THEN 'BECA RECTORIA' WHEN PLAZA.TIPO_BECA = 2 THEN 'PLAN PROMOCIONAL EDUCACIONAL' " +
					" WHEN PLAZA.TIPO_BECA = 3 THEN 'BECAS ESPECIALES VRF' WHEN PLAZA.TIPO_BECA = 4 THEN 'PLANES ESPECIALES UNIONES'" +
					" WHEN PLAZA.TIPO_BECA = 5 THEN 'BECAS EDUCATIVAS HLC' WHEN PLAZA.TIPO_BECA = 6 THEN 'BECAS MUNICIPALES'" +
					" WHEN PLAZA.TIPO_BECA = 7 THEN 'BECAS ESPECIALES DORMITORIOS' WHEN PLAZA.TIPO_BECA = 10 THEN 'OTROS'" +
					" ELSE 'UNKNOWN'" +
					" END) NOMBRE_BECA, " +
					" ABS(COALESCE(ALUMNOBECA.BECA, 0)) AS BECA, ABS(COALESCE(ALUMNOBECA.BECA1, 0)) AS BECA1, " +
					" ABS(COALESCE(ALUMNOBECA.BECA2, 0)) AS BECA2, ABS(COALESCE(ALUMNOBECA.BECA3, 0)) AS BECA3 , " +
					" ENOC.ALUM_CARRERA_ID(CONTRATOS.MATRICULA) CARRERA, ENOC.ALUMNO_FAC_ID(CONTRATOS.MATRICULA) AS FACULTAD" +
					" FROM (" +
					" SELECT CA.MATRICULA, CA.NUMERO_HORAS,CA.PLAZA_ID, CA.CARGA_ID, CA.BLOQUE_ID FROM NOE.AFE_CONTRATO_ALUMNO CA" +
					" WHERE CA.STATUS ='I' ) CONTRATOS," +
					" (" +
					" SELECT PL.ID, PL.TIPOPLAZA, PL.CCOSTO_ID, PL.IMPORTE, PL.TIPO_BECA " +
					" FROM NOE.AFE_PLAZA PL ) PLAZA " +
					" ,(" +
					" SELECT AB.MATRICULA, AB.BECA, AB.BECA1, AB.BECA2, AB.BECA3" +
					" FROM NOE.ALUMNO_BECA AB" +
					" WHERE AB.STATUS='I'" +
					" ) ALUMNOBECA, " +
					" (" +
					" SELECT CC.ID_CCOSTO, CC.NOMBRE FROM MATEO.CONT_CCOSTO CC WHERE ID_EJERCICIO='001-2012') CCOSTO" +
					" ,(" +
					" SELECT TB.TIPO_BECA, TB.NOMBRE FROM NOE.TIPO_BECA TB ) TIPOBECA " +
					" WHERE " +
					" PLAZA.ID LIKE CONTRATOS.PLAZA_ID " +
					" AND ALUMNOBECA.MATRICULA = CONTRATOS.MATRICULA(+) " +
					" AND CCOSTO.ID_CCOSTO=PLAZA.CCOSTO_ID" +
					" AND TIPOBECA.TIPO_BECA(+) = PLAZA.TIPO_BECA" +
					" " + orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Beca alumno = new Beca();
				alumno.mapeaReg(rs);
				lisRep.add(alumno);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.BecaUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}			
		
		return lisRep;
	}
	
	public HashMap<String,String> getMapEnsenanza(Connection conn) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT " +
					" MATRICULA,CARGA_ID,BLOQUE, IMPORTE" +
					" FROM MATEO.FES_CC_MOVIMIENTO " +
					" WHERE TIPOMOV = '02' "; 			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				map.put(rs.getString("MATRICULA")+rs.getString("CARGA_ID")+rs.getString("BLOQUE") , rs.getString("IMPORTE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.objeto.BecaUtil|getMapEnsenanza|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}

}
