/**
 * 
 */
package aca.vista.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Elifo
 *
 */
public class MenPerfilFao {
	public ArrayList<MenPerfil> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MenPerfil> lisMenPerfil		= new ArrayList<MenPerfil>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, CARGA_ID, FOLIO," +
				" MENTOR_ID,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO"+
				" FROM ENOC.MEN_PERFIL "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MenPerfil menPer = new MenPerfil();
				menPer.mapeaReg(rs);
				lisMenPerfil.add(menPer);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenPerfilUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMenPerfil;
	}
	
	public ArrayList<MenPerfil> getListCargas(Connection conn, String cargas, String orden ) throws SQLException{
		
		ArrayList<MenPerfil> lisMenPerfil		= new ArrayList<MenPerfil>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, CARGA_ID, FOLIO," +
				" MENTOR_ID,TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO, USUARIO"+
				" FROM ENOC.MEN_PERFIL " +
				" WHERE CARGA_ID IN ("+cargas+") "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MenPerfil menPer = new MenPerfil();
				menPer.mapeaReg(rs);
				lisMenPerfil.add(menPer);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.MenPerfilUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisMenPerfil;
	}
}