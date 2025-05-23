package aca.saii.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SaiiDatosExtraDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertDat( SaiiDatosExtra saiiDatosExtra ) {
		boolean ok = false;
		
		try {
			String comando = "INSERT INTO DANIEL.EFE_INF_EXTRA" + 
					"(" + 
					"   PLAN_ID,PLAN_NOMBRE,FECHA,DIS_N_ING,DIS_R_ING,DIS_TOTAL,REG_ALUM_H,REG_ALUM_M,REG_ALUM_T,CUR_REM_H,CUR_REM_M,CUR_REM_T," + 
					"	RET_SEG_H,RET_SEG_M,RET_SEG_T,DESERT_H,DESERT_M,DESERT_T,DESERT_19,DESERT_20_24,DESERT_25_29,DESERT_30,DESERT_DIS," + 
					"	TRAB_SIM_H,TRAB_SIM_M,TRAB_SIM_T,MOVIL_NAC_H,MOVIL_NAC_M,MOVIL_NAC_T,MOVIL_NAC_19,MOVIL_NAC_20_24,MOVIL_NAC_25_29,MOVIL_NAC_30," + 
					"	MOVIL_NAC_DIS,MOVIL_INT_H,MOVIL_INT_M,MOVIL_INT_T,MOVIL_INT_19,MOVIL_INT_20_24,MOVIL_INT_25_29,MOVIL_INT_30,MOVIL_INT_DIS,SERV_COM_H," + 
					"	SERV_COM_M,SERV_COM_T,SERV_SOC_H,SERV_SOC_M,SERV_SOC_T,EGEL,EGEL_SOB,TITU_PROG,CONC,EGRE_TRAB_H,EGRE_TRAB_M,EGRE_TRAB_T," + 
					"	EGRE_TRAB_19,EGRE_TRAB_20_24,EGRE_TRAB_25_29,EGRE_TRAB_30,EGRE_TRAB_DIS, DOCENTES_T, DOCENTES_H, DOCENTES_M, DOCENTES_30, DOCENTES_31_49, DOCENTES_50," + 
					"   DOCENTES_DISC, DOCENTES_EXT, DOCENTES_0_5, DOCENTES_6_15, DOCENTES_16_25, DOCENTES_26, DOCENTES_F_D, DOCENTES_F_M, DOCENTES_F_ESP, DOCENTES_F_LIC," +
					"   DOCENTES_F_OTRO, DOCENTES_T_COMP, DOCENTES_COMP_H, DOCENTES_COMP_M, DOCENTES_MED_T, DOCENTES_MED_H, DOCENTES_MED_M, DOCENTES_SNI," +
					"   DOCENTES_FPD, DOCENTES_CGA, DOCENTES_MOV_NAC, DOCENTES_MOV_INT, ASPIRANTES_ADM, BECADOS_T, BECADOS_H, BECADOS_M, ULT_GRAD_DIS," +
					"   EGRESADOS_T, EGRESADOS_H, EGRESADOS_M, EGRESADOS_19, EGRESADOS_20_24, EGRESADOS_25_29, EGRESADOS_30, EGRESADOS_DIS," +
					"   TOTAL_EDAD, TOTAL_NI_E, NIVEL_PREV, TOTAL_D_E, TOTAL_D_A, TOTAL_D_F, QUEST_1, TOTAL_RI_E, TOTAL_DES_E, TOTAL_MN_E, TOTAL_MI_E," +
					"   QUEST_2, TOTAL_UG_E, TOTAL_EGRE_E, PROM_EGRE, QUEST_3, QUEST_4, TOTAL_TRAB_E, POR_EGRE_TRAB" +
					")" + 
					"values(?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," + 
					"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] parametros = new Object[] {
					saiiDatosExtra.getPLAN_ID(),
					saiiDatosExtra.getPLAN_NOMBRE(),
					saiiDatosExtra.getFECHA(),
					saiiDatosExtra.getDIS_N_ING(),
					saiiDatosExtra.getDIS_R_ING(),
					saiiDatosExtra.getDIS_TOTAL(),
					saiiDatosExtra.getREG_ALUM_H(),
					saiiDatosExtra.getREG_ALUM_M(),
					saiiDatosExtra.getREG_ALUM_T(),
					saiiDatosExtra.getCUR_REM_H(),
					saiiDatosExtra.getCUR_REM_M(),
					saiiDatosExtra.getCUR_REM_T(),
					saiiDatosExtra.getRET_SEG_H(),
					saiiDatosExtra.getRET_SEG_M(),
					saiiDatosExtra.getRET_SEG_T(),
					saiiDatosExtra.getDESERT_H(),
					saiiDatosExtra.getDESERT_M(),
					saiiDatosExtra.getDESERT_T(),
					saiiDatosExtra.getDESERT_19(),
					saiiDatosExtra.getDESERT_20_24(),
					saiiDatosExtra.getDESERT_25_29(),
					saiiDatosExtra.getDESERT_30(),
					saiiDatosExtra.getDESERT_DIS(),
					saiiDatosExtra.getTRAB_SIM_H(),
					saiiDatosExtra.getTRAB_SIM_M(),
					saiiDatosExtra.getTRAB_SIM_T(),
					saiiDatosExtra.getMOVIL_NAC_H(),
					saiiDatosExtra.getMOVIL_NAC_M(),
					saiiDatosExtra.getMOVIL_NAC_T(),
					saiiDatosExtra.getMOVIL_NAC_19(),
					saiiDatosExtra.getMOVIL_NAC_20_24(),
					saiiDatosExtra.getMOVIL_NAC_25_29(),
					saiiDatosExtra.getMOVIL_NAC_30(),
					saiiDatosExtra.getMOVIL_NAC_DIS(),
					saiiDatosExtra.getMOVIL_INT_H(),
					saiiDatosExtra.getMOVIL_INT_M(),
					saiiDatosExtra.getMOVIL_INT_T(),
					saiiDatosExtra.getMOVIL_INT_19(),
					saiiDatosExtra.getMOVIL_INT_20_24(),
					saiiDatosExtra.getMOVIL_INT_25_29(),
					saiiDatosExtra.getMOVIL_INT_30(),
					saiiDatosExtra.getMOVIL_INT_DIS(),
					saiiDatosExtra.getSERV_COM_H(),
					saiiDatosExtra.getSERV_COM_M(),
					saiiDatosExtra.getSERV_COM_T(),
					saiiDatosExtra.getSERV_SOC_H(),
					saiiDatosExtra.getSERV_SOC_M(),
					saiiDatosExtra.getSERV_SOC_T(),
					saiiDatosExtra.getEGEL(),
					saiiDatosExtra.getEGEL_SOB(),
					saiiDatosExtra.getTITU_PROG(),
					saiiDatosExtra.getCONC(),
					saiiDatosExtra.getEGRE_TRAB_H(),
					saiiDatosExtra.getEGRE_TRAB_M(),
					saiiDatosExtra.getEGRE_TRAB_T(),
					saiiDatosExtra.getEGRE_TRAB_19(),
					saiiDatosExtra.getEGRE_TRAB_20_24(),
					saiiDatosExtra.getEGRE_TRAB_25_29(),
					saiiDatosExtra.getEGRE_TRAB_30(),
					saiiDatosExtra.getEGRE_TRAB_DIS(),
					saiiDatosExtra.getDOCENTES_T(),
					saiiDatosExtra.getDOCENTES_H(),
					saiiDatosExtra.getDOCENTES_M(),
					saiiDatosExtra.getDOCENTES_30(),
					saiiDatosExtra.getDOCENTES_31_49(),
					saiiDatosExtra.getDOCENTES_50(),
					saiiDatosExtra.getDOCENTES_DISC(),
					saiiDatosExtra.getDOCENTES_EXT(),
					saiiDatosExtra.getDOCENTES_0_5(),
					saiiDatosExtra.getDOCENTES_6_15(),
					saiiDatosExtra.getDOCENTES_16_25(),
					saiiDatosExtra.getDOCENTES_26(),
					saiiDatosExtra.getDOCENTES_F_D(),
					saiiDatosExtra.getDOCENTES_F_M(),
					saiiDatosExtra.getDOCENTES_F_ESP(),
					saiiDatosExtra.getDOCENTES_F_LIC(),
					saiiDatosExtra.getDOCENTES_F_OTRO(),
					saiiDatosExtra.getDOCENTES_T_COMP(),
					saiiDatosExtra.getDOCENTES_COMP_H(),
					saiiDatosExtra.getDOCENTES_COMP_M(),
					saiiDatosExtra.getDOCENTES_MED_T(),
					saiiDatosExtra.getDOCENTES_MED_H(),
					saiiDatosExtra.getDOCENTES_MED_M(),
					saiiDatosExtra.getDOCENTES_SNI(),
					saiiDatosExtra.getDOCENTES_FPD(),
					saiiDatosExtra.getDOCENTES_CGA(),
					saiiDatosExtra.getDOCENTES_MOV_NAC(),
					saiiDatosExtra.getDOCENTES_MOV_INT(),
					saiiDatosExtra.getASPIRANTES_ADM(),
					saiiDatosExtra.getBECADOS_T(),
					saiiDatosExtra.getBECADOS_H(),
					saiiDatosExtra.getBECADOS_M(),
					saiiDatosExtra.getULT_GRAD_DIS(),
					saiiDatosExtra.getEGRESADOS_T(),
					saiiDatosExtra.getEGRESADOS_H(),
					saiiDatosExtra.getEGRESADOS_M(),
					saiiDatosExtra.getEGRESADOS_19(),
					saiiDatosExtra.getEGRESADOS_20_24(),
					saiiDatosExtra.getEGRESADOS_25_29(),
					saiiDatosExtra.getEGRESADOS_30(),
					saiiDatosExtra.getEGRESADOS_DIS(),
					saiiDatosExtra.getTOTAL_EDAD(),
					saiiDatosExtra.getTOTAL_NI_E(),
					saiiDatosExtra.getNIVEL_PREV(),
					saiiDatosExtra.getTOTAL_D_E(),
					saiiDatosExtra.getTOTAL_D_A(),
					saiiDatosExtra.getTOTAL_D_F(),
					saiiDatosExtra.getQUEST_1(),
					saiiDatosExtra.getTOTAL_RI_E(),
					saiiDatosExtra.getTOTAL_DES_E(),
					saiiDatosExtra.getTOTAL_MN_E(),
					saiiDatosExtra.getTOTAL_MI_E(),
					saiiDatosExtra.getQUEST_2(),
					saiiDatosExtra.getTOTAL_UG_E(),
					saiiDatosExtra.getTOTAL_EGRE_E(),
					saiiDatosExtra.getPROM_EGRE(),
					saiiDatosExtra.getQUEST_3(),
					saiiDatosExtra.getQUEST_4(),
					saiiDatosExtra.getTOTAL_TRAB_E(),
					saiiDatosExtra.getPOR_EGRE_TRAB()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiDatosExtra|insertDat|:"+ex);
		}
		return ok;
	}
	
	public boolean updateDat( SaiiDatosExtra saiiDatosExtra ) {
		boolean ok = false;
		
		try {
			String comando = "UPDATE DANIEL.EFE_INF_EXTRA SET"
					+ " DIS_N_ING = ?,DIS_R_ING = ?,DIS_TOTAL = ?,REG_ALUM_H = ?,REG_ALUM_M = ?,REG_ALUM_T = ?,CUR_REM_H = ?,CUR_REM_M = ?,CUR_REM_T = ?,"
					+ " RET_SEG_H = ?,RET_SEG_M = ?,RET_SEG_T = ?,DESERT_H = ?,DESERT_M = ?,DESERT_T = ?,DESERT_19 = ?,DESERT_20_24 = ?,DESERT_25_29 = ?,DESERT_30 = ?,DESERT_DIS = ?,"
					+ " TRAB_SIM_H = ?,TRAB_SIM_M = ?,TRAB_SIM_T = ?,MOVIL_NAC_H = ?,MOVIL_NAC_M = ?,MOVIL_NAC_T = ?,MOVIL_NAC_19 = ?,MOVIL_NAC_20_24 = ?,MOVIL_NAC_25_29 = ?,MOVIL_NAC_30 = ?,"
					+ " MOVIL_NAC_DIS = ?,MOVIL_INT_H = ?,MOVIL_INT_M = ?,MOVIL_INT_T = ?,MOVIL_INT_19 = ?,MOVIL_INT_20_24 = ?,MOVIL_INT_25_29 = ?,MOVIL_INT_30 = ?,MOVIL_INT_DIS = ?,SERV_COM_H = ?,"
					+ " SERV_COM_M = ?,SERV_COM_T = ?,SERV_SOC_H = ?,SERV_SOC_M = ?,SERV_SOC_T = ?,EGEL = ?,EGEL_SOB = ?,TITU_PROG = ?,CONC = ?,EGRE_TRAB_H = ?,EGRE_TRAB_M = ?,EGRE_TRAB_T = ?,"
					+ " EGRE_TRAB_19 = ?,EGRE_TRAB_20_24 = ?,EGRE_TRAB_25_29 = ?,EGRE_TRAB_30 = ?,EGRE_TRAB_DIS = ?,"
					+ " DOCENTES_T = ?, DOCENTES_H = ?, DOCENTES_M = ?, DOCENTES_30 = ?, DOCENTES_31_49 = ?, DOCENTES_50 = ?, DOCENTES_DISC = ?, DOCENTES_EXT = ?, DOCENTES_0_5 = ?,"
					+ " DOCENTES_6_15 = ?, DOCENTES_16_25 = ?, DOCENTES_26 = ?, DOCENTES_F_D = ?, DOCENTES_F_M = ?, DOCENTES_F_ESP = ?, DOCENTES_F_LIC = ?, DOCENTES_F_OTRO = ?,"
					+ " DOCENTES_T_COMP = ?, DOCENTES_COMP_H = ?, DOCENTES_COMP_M = ?, DOCENTES_MED_T = ?, DOCENTES_MED_H = ?, DOCENTES_MED_M = ?, DOCENTES_SNI = ?, DOCENTES_FPD = ?,"
					+ " DOCENTES_CGA = ?, DOCENTES_MOV_NAC = ?, DOCENTES_MOV_INT = ?, ASPIRANTES_ADM = ?, BECADOS_T = ?, BECADOS_H = ?, BECADOS_M = ?, ULT_GRAD_DIS = ?,"
					+ " EGRESADOS_T = ?, EGRESADOS_H = ?, EGRESADOS_M = ?, EGRESADOS_19 = ?, EGRESADOS_20_24 = ?, EGRESADOS_25_29 = ?, EGRESADOS_30 = ?, EGRESADOS_DIS = ?,"
					+ " TOTAL_EDAD = ?, TOTAL_NI_E = ?, NIVEL_PREV = ?, TOTAL_D_E = ?, TOTAL_D_A = ?, TOTAL_D_F = ?, QUEST_1 = ?, TOTAL_RI_E = ?, TOTAL_DES_E = ?, TOTAL_MN_E = ?,"
					+ " TOTAL_MI_E = ?, QUEST_2 = ?, TOTAL_UG_E = ?, TOTAL_EGRE_E = ?, PROM_EGRE = ?, QUEST_3 = ?, QUEST_4 = ?, TOTAL_TRAB_E = ?, POR_EGRE_TRAB = ?, PLAN_NOMBRE = ?"
					+ " WHERE PLAN_ID = ? AND FECHA = TO_DATE(?,'DD/MM/YYYY')";
			Object[] parametros = new Object[] {
					
					saiiDatosExtra.getDIS_N_ING(),
					saiiDatosExtra.getDIS_R_ING(),
					saiiDatosExtra.getDIS_TOTAL(),
					saiiDatosExtra.getREG_ALUM_H(),
					saiiDatosExtra.getREG_ALUM_M(),
					saiiDatosExtra.getREG_ALUM_T(),
					saiiDatosExtra.getCUR_REM_H(),
					saiiDatosExtra.getCUR_REM_M(),
					saiiDatosExtra.getCUR_REM_T(),
					saiiDatosExtra.getRET_SEG_H(),
					saiiDatosExtra.getRET_SEG_M(),
					saiiDatosExtra.getRET_SEG_T(),
					saiiDatosExtra.getDESERT_H(),
					saiiDatosExtra.getDESERT_M(),
					saiiDatosExtra.getDESERT_T(),
					saiiDatosExtra.getDESERT_19(),
					saiiDatosExtra.getDESERT_20_24(),
					saiiDatosExtra.getDESERT_25_29(),
					saiiDatosExtra.getDESERT_30(),
					saiiDatosExtra.getDESERT_DIS(),
					saiiDatosExtra.getTRAB_SIM_H(),
					saiiDatosExtra.getTRAB_SIM_M(),
					saiiDatosExtra.getTRAB_SIM_T(),
					saiiDatosExtra.getMOVIL_NAC_H(),
					saiiDatosExtra.getMOVIL_NAC_M(),
					saiiDatosExtra.getMOVIL_NAC_T(),
					saiiDatosExtra.getMOVIL_NAC_19(),
					saiiDatosExtra.getMOVIL_NAC_20_24(),
					saiiDatosExtra.getMOVIL_NAC_25_29(),
					saiiDatosExtra.getMOVIL_NAC_30(),
					saiiDatosExtra.getMOVIL_NAC_DIS(),
					saiiDatosExtra.getMOVIL_INT_H(),
					saiiDatosExtra.getMOVIL_INT_M(),
					saiiDatosExtra.getMOVIL_INT_T(),
					saiiDatosExtra.getMOVIL_INT_19(),
					saiiDatosExtra.getMOVIL_INT_20_24(),
					saiiDatosExtra.getMOVIL_INT_25_29(),
					saiiDatosExtra.getMOVIL_INT_30(),
					saiiDatosExtra.getMOVIL_INT_DIS(),
					saiiDatosExtra.getSERV_COM_H(),
					saiiDatosExtra.getSERV_COM_M(),
					saiiDatosExtra.getSERV_COM_T(),
					saiiDatosExtra.getSERV_SOC_H(),
					saiiDatosExtra.getSERV_SOC_M(),
					saiiDatosExtra.getSERV_SOC_T(),
					saiiDatosExtra.getEGEL(),
					saiiDatosExtra.getEGEL_SOB(),
					saiiDatosExtra.getTITU_PROG(),
					saiiDatosExtra.getCONC(),
					saiiDatosExtra.getEGRE_TRAB_H(),
					saiiDatosExtra.getEGRE_TRAB_M(),
					saiiDatosExtra.getEGRE_TRAB_T(),
					saiiDatosExtra.getEGRE_TRAB_19(),
					saiiDatosExtra.getEGRE_TRAB_20_24(),
					saiiDatosExtra.getEGRE_TRAB_25_29(),
					saiiDatosExtra.getEGRE_TRAB_30(),
					saiiDatosExtra.getEGRE_TRAB_DIS(),
					saiiDatosExtra.getDOCENTES_T(),
					saiiDatosExtra.getDOCENTES_H(),
					saiiDatosExtra.getDOCENTES_M(),
					saiiDatosExtra.getDOCENTES_30(),
					saiiDatosExtra.getDOCENTES_31_49(),
					saiiDatosExtra.getDOCENTES_50(),
					saiiDatosExtra.getDOCENTES_DISC(),
					saiiDatosExtra.getDOCENTES_EXT(),
					saiiDatosExtra.getDOCENTES_0_5(),
					saiiDatosExtra.getDOCENTES_6_15(),
					saiiDatosExtra.getDOCENTES_16_25(),
					saiiDatosExtra.getDOCENTES_26(),
					saiiDatosExtra.getDOCENTES_F_D(),
					saiiDatosExtra.getDOCENTES_F_M(),
					saiiDatosExtra.getDOCENTES_F_ESP(),
					saiiDatosExtra.getDOCENTES_F_LIC(),
					saiiDatosExtra.getDOCENTES_F_OTRO(),
					saiiDatosExtra.getDOCENTES_T_COMP(),
					saiiDatosExtra.getDOCENTES_COMP_H(),
					saiiDatosExtra.getDOCENTES_COMP_M(),
					saiiDatosExtra.getDOCENTES_MED_T(),
					saiiDatosExtra.getDOCENTES_MED_H(),
					saiiDatosExtra.getDOCENTES_MED_M(),
					saiiDatosExtra.getDOCENTES_SNI(),
					saiiDatosExtra.getDOCENTES_FPD(),
					saiiDatosExtra.getDOCENTES_CGA(),
					saiiDatosExtra.getDOCENTES_MOV_NAC(),
					saiiDatosExtra.getDOCENTES_MOV_INT(),
					saiiDatosExtra.getASPIRANTES_ADM(),
					saiiDatosExtra.getBECADOS_T(),
					saiiDatosExtra.getBECADOS_H(),
					saiiDatosExtra.getBECADOS_M(),
					saiiDatosExtra.getULT_GRAD_DIS(),
					saiiDatosExtra.getEGRESADOS_T(),
					saiiDatosExtra.getEGRESADOS_H(),
					saiiDatosExtra.getEGRESADOS_M(),
					saiiDatosExtra.getEGRESADOS_19(),
					saiiDatosExtra.getEGRESADOS_20_24(),
					saiiDatosExtra.getEGRESADOS_25_29(),
					saiiDatosExtra.getEGRESADOS_30(),
					saiiDatosExtra.getEGRESADOS_DIS(),
					saiiDatosExtra.getTOTAL_EDAD(),
					saiiDatosExtra.getTOTAL_NI_E(),
					saiiDatosExtra.getNIVEL_PREV(),
					saiiDatosExtra.getTOTAL_D_E(),
					saiiDatosExtra.getTOTAL_D_A(),
					saiiDatosExtra.getTOTAL_D_F(),
					saiiDatosExtra.getQUEST_1(),
					saiiDatosExtra.getTOTAL_RI_E(),
					saiiDatosExtra.getTOTAL_DES_E(),
					saiiDatosExtra.getTOTAL_MN_E(),
					saiiDatosExtra.getTOTAL_MI_E(),
					saiiDatosExtra.getQUEST_2(),
					saiiDatosExtra.getTOTAL_UG_E(),
					saiiDatosExtra.getTOTAL_EGRE_E(),
					saiiDatosExtra.getPROM_EGRE(),
					saiiDatosExtra.getQUEST_3(),
					saiiDatosExtra.getQUEST_4(),
					saiiDatosExtra.getTOTAL_TRAB_E(),
					saiiDatosExtra.getPOR_EGRE_TRAB(),
					saiiDatosExtra.getPLAN_NOMBRE(),
					saiiDatosExtra.getPLAN_ID(),
					saiiDatosExtra.getFECHA()
					};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiDatosExtra|updateDat|:"+ex);
		}
		return ok;
	}
    
    public boolean deleteReg(String planId, String fecha) {
    	boolean ok = false;

        try {
             String comando =
                    " DELETE FROM DANIEL.EFE_INF_EXTRA WHERE PLAN_ID = ? AND FECHA = TO_DATE(?,'DD/MM/YYYY')";
             Object[] parametros = new Object[] {planId, fecha};
             if (enocJdbc.update(comando, parametros)==1)
 				ok = true;
 			else
 				ok = false;
        }catch (Exception ex){
            System.out.println("Error - aca.saii.spring.SaiiDatosExtra|deleteReg|:" + ex);
        }
        return ok;
    }

    public boolean existeReg(String grupoId, String fecha) {
		boolean ok = false;
		try{
			String comando = "SELECT COUNT(*) FROM DANIEL.EFE_INF_EXTRA WHERE PLAN_ID = ? AND FECHA = TO_DATE(?,'DD/MM/YYYY')";
			Object[] parametros = new Object[] {grupoId, fecha};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiDatosExtra|existeReg|:"+ex);
		}
		return ok;
	}
    
    public SaiiDatosExtra mapeaRegId(String plan_id, String fecha) {
    	SaiiDatosExtra saiiDatosExtra = new SaiiDatosExtra();
		try{ 
	    	String comando = "SELECT * FROM DANIEL.EFE_INF_EXTRA WHERE PLAN_ID = ? AND FECHA = TO_DATE(?,'DD/MM/YYYY')";	    	
	    	Object[] parametros = new Object[] {plan_id, fecha};
	    	saiiDatosExtra = enocJdbc.queryForObject(comando, new SaiiDatosExtraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiDatosExtraDao|mapeaRegId|:"+ex);
		}
		return saiiDatosExtra;
	}
    
    public List<SaiiDatosExtra> getAllbyFecha(String fecha) {
    	List<SaiiDatosExtra> lista = new ArrayList<SaiiDatosExtra>();
		try{ 
	    	String comando = "SELECT * FROM DANIEL.EFE_INF_EXTRA WHERE FECHA = TO_DATE(?,'DD/MM/YYYY')";	    	
	    	Object[] parametros = new Object[] {fecha};
	    	lista = enocJdbc.query(comando, new SaiiDatosExtraMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.saii.spring.SaiiDatosExtraDao|getAllbyFecha|:"+ex);
		}
		return lista;
	}
    
}