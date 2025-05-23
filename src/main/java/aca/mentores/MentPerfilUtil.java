/**
 * 
 */
package aca.mentores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * @author Jose Torres
 *
 */
public class MentPerfilUtil {
	public ArrayList<MentPerfil> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<MentPerfil> lisMentPerfil		= new ArrayList<MentPerfil>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
				" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
				" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
				" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
				" COM_OTRO, COM_3, COM_2, COM_1," +
				" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
				" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
				" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
				" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
				" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
				" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO"+
				" FROM ENOC.MENT_PERFIL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentPerfil mentPer = new MentPerfil();
				mentPer.mapeaReg(rs);
				lisMentPerfil.add(mentPer);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfilUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMentPerfil;
	}
	
	public TreeMap<String,MentPerfil> getMapMentPerfil(Connection conn, String periodoId, String orden ) throws SQLException{
		
		TreeMap<String,MentPerfil> mapEvaluacion	= new TreeMap<String,MentPerfil>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, MENTOR_ID," +
			" FOLIO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, REL_ASDB, REL_ASD," +
			" REL_NASD, RES_INT, RES_EXT, RES_TIPO," +
			" COM_COMEDOR, COM_SNACK, COM_ASISTENCIA, COM_CASA," +
			" COM_OTRO, COM_3, COM_2, COM_1," +
			" DEV_DIARIA, DEV_SEMANA, DEV_MENOS, IGL_UM," +
			" IGL_OTRA, IGL_NINGUNA, PRO_FAMILIAR, PRO_FINANCIERO," +
			" PRO_MATERIA, PRO_PENDIENTE, LID_ESPIRITUAL, LID_POSICION," +
			" TRA_NADA, TRA_MEDIO, TRA_COMPLETO, EST_DESAFIOS," +
			" EST_RELACIONES, EST_PROGRESO, EST_REGRESA, RIESGO_PERSONAL," +
			" RIESGO_INTEGRACION, RIESGO_RELACIONES, RIESGO_ACADEMICO, RIESGO_FINANCIERO"+
			" FROM ENOC.MENT_PERFIL "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				MentPerfil mentPer = new MentPerfil();
				mentPer.mapeaReg(rs);				
				llave = mentPer.getCodigoPersonal()+mentPer.getCargaId()+mentPer.getMentorId()+mentPer.getFolio();
				mapEvaluacion.put(llave, mentPer );
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.EvaluacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapEvaluacion;
	}
	public HashMap<String, MentPerfil> getMapMentPerfil(Connection conn, String periodo) throws SQLException{
		
		HashMap<String, MentPerfil> list		= new HashMap<String,MentPerfil>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID='"+periodo+"' AND STATUS='A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				MentPerfil personal = new MentPerfil();
				personal.mapeaReg(rs);
				list.put(personal.getCodigoPersonal(), personal);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.mentores.MentPerfilesUtil|getMapMentPerfil|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
}