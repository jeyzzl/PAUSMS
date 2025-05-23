package aca.saii.spring;

public class SaiiDatosExtra {
    String PLAN_ID;
	String PLAN_NOMBRE;
	String FECHA; 
	int DIS_N_ING; 
	int DIS_R_ING; 
	int DIS_TOTAL; 
	int REG_ALUM_H; 
	int REG_ALUM_M; 
	int REG_ALUM_T; 
	int CUR_REM_H; 
	int CUR_REM_M; 
	int CUR_REM_T; 
	int RET_SEG_H; 
	int RET_SEG_M; 
	int RET_SEG_T; 
	int DESERT_H; 
	int DESERT_M; 
	int DESERT_T; 
	int DESERT_19; 
	int DESERT_20_24; 
	int DESERT_25_29; 
	int DESERT_30; 
	int DESERT_DIS; 
	int TRAB_SIM_H; 
	int TRAB_SIM_M; 
	int TRAB_SIM_T; 
	int MOVIL_NAC_H; 
	int MOVIL_NAC_M; 
	int MOVIL_NAC_T; 
	int MOVIL_NAC_19; 
	int MOVIL_NAC_20_24; 
	int MOVIL_NAC_25_29; 
	int MOVIL_NAC_30; 
	int MOVIL_NAC_DIS; 
	int MOVIL_INT_H; 
	int MOVIL_INT_M; 
	int MOVIL_INT_T; 
	int MOVIL_INT_19; 
	int MOVIL_INT_20_24; 
	int MOVIL_INT_25_29; 
	int MOVIL_INT_30; 
	int MOVIL_INT_DIS; 
	int SERV_COM_H; 
	int SERV_COM_M; 
	int SERV_COM_T; 
	int SERV_SOC_H; 
	int SERV_SOC_M; 
	int SERV_SOC_T; 
	int EGEL; 
	int EGEL_SOB; 
	int TITU_PROG; 
	int CONC; 
	int EGRE_TRAB_H; 
	int EGRE_TRAB_M; 
	int EGRE_TRAB_T; 
	int EGRE_TRAB_19; 
	int EGRE_TRAB_20_24; 
	int EGRE_TRAB_25_29; 
	int EGRE_TRAB_30; 
	int EGRE_TRAB_DIS;
	int DOCENTES_T;
	int DOCENTES_H;
	int DOCENTES_M;
	int DOCENTES_30;
	int DOCENTES_31_49;
	int DOCENTES_50;
	int DOCENTES_DISC;
	int DOCENTES_EXT;
	int DOCENTES_0_5;
	int DOCENTES_6_15;
	int DOCENTES_16_25;
	int DOCENTES_26;
	int DOCENTES_F_D;
	int DOCENTES_F_M;
	int DOCENTES_F_ESP;
	int DOCENTES_F_LIC;
	int DOCENTES_F_OTRO;
	int DOCENTES_T_COMP;
	int DOCENTES_COMP_H;
	int DOCENTES_COMP_M;
	int DOCENTES_MED_T;
	int DOCENTES_MED_H;
	int DOCENTES_MED_M;
	int DOCENTES_SNI;
	int DOCENTES_FPD;
	int DOCENTES_CGA;
	int DOCENTES_MOV_NAC;
	int DOCENTES_MOV_INT;
	int ASPIRANTES_ADM;
	int BECADOS_T;
	int BECADOS_H;
	int BECADOS_M;
	int ULT_GRAD_DIS;
	int EGRESADOS_T;
	int EGRESADOS_H;
	int EGRESADOS_M;
	int EGRESADOS_19;
	int EGRESADOS_20_24;
	int EGRESADOS_25_29;
	int EGRESADOS_30;
	int EGRESADOS_DIS;
	int TOTAL_EDAD;
	int TOTAL_NI_E;
	int NIVEL_PREV;
	int TOTAL_D_E;
	int TOTAL_D_A;
	int TOTAL_D_F;
	String QUEST_1;
	int TOTAL_RI_E;
	int TOTAL_DES_E;
	int TOTAL_MN_E;
	int TOTAL_MI_E;
	String QUEST_2;
	int TOTAL_UG_E;
	int TOTAL_EGRE_E;
	double PROM_EGRE;
	String QUEST_3;
	String QUEST_4;
	int TOTAL_TRAB_E;
	int POR_EGRE_TRAB;
	
	public SaiiDatosExtra(){
	PLAN_ID = "-";
	PLAN_NOMBRE = "-";
	FECHA = aca.util.Fecha.getHoy(); 
	
	DIS_N_ING = -1; 
	DIS_R_ING = -1; 
	DIS_TOTAL = -1; 
	REG_ALUM_H = -1; 
	REG_ALUM_M = -1; 
	REG_ALUM_T = -1; 
	CUR_REM_H = -1; 
	CUR_REM_M = -1; 
	CUR_REM_T = -1; 
	RET_SEG_H = -1; 
	RET_SEG_M = -1; 
	RET_SEG_T = -1; 
	DESERT_H = -1; 
	DESERT_M = -1; 
	DESERT_T = -1; 
	DESERT_19 = -1; 
	DESERT_20_24 = -1; 
	DESERT_25_29 = -1; 
	DESERT_30 = -1; 
	DESERT_DIS = -1; 
	TRAB_SIM_H = -1; 
	TRAB_SIM_M = -1; 
	TRAB_SIM_T = -1; 
	MOVIL_NAC_H = -1; 
	MOVIL_NAC_M = -1; 
	MOVIL_NAC_T = -1; 
	MOVIL_NAC_19 = -1; 
	MOVIL_NAC_20_24 = -1; 
	MOVIL_NAC_25_29 = -1; 
	MOVIL_NAC_30 = -1; 
	MOVIL_NAC_DIS = -1; 
	MOVIL_INT_H = -1; 
	MOVIL_INT_M = -1; 
	MOVIL_INT_T = -1; 
	MOVIL_INT_19 = -1; 
	MOVIL_INT_20_24 = -1; 
	MOVIL_INT_25_29 = -1; 
	MOVIL_INT_30 = -1; 
	MOVIL_INT_DIS = -1; 
	SERV_COM_H = -1; 
	SERV_COM_M = -1; 
	SERV_COM_T = -1; 
	SERV_SOC_H = -1; 
	SERV_SOC_M = -1; 
	SERV_SOC_T = -1; 
	EGEL = -1; 
	EGEL_SOB = -1; 
	TITU_PROG = -1; 
	CONC = -1; 
	EGRE_TRAB_H = -1; 
	EGRE_TRAB_M = -1; 
	EGRE_TRAB_T = -1; 
	EGRE_TRAB_19 = -1; 
	EGRE_TRAB_20_24 = -1; 
	EGRE_TRAB_25_29 = -1; 
	EGRE_TRAB_30 = -1; 
	EGRE_TRAB_DIS = -1;
	DOCENTES_T = -1;
	DOCENTES_H = -1;
	DOCENTES_M = -1;
	DOCENTES_30 = -1;
	DOCENTES_31_49 = -1;
	DOCENTES_50 = -1;
	DOCENTES_DISC = -1;
	DOCENTES_EXT = -1;
	DOCENTES_0_5 = -1;
	DOCENTES_6_15 = -1;
	DOCENTES_16_25 = -1;
	DOCENTES_26 = -1;
	DOCENTES_F_D = -1;
	DOCENTES_F_M = -1;
	DOCENTES_F_ESP = -1;
	DOCENTES_F_LIC = -1;
	DOCENTES_F_OTRO = -1;
	DOCENTES_T_COMP = -1;
	DOCENTES_COMP_H = -1;
	DOCENTES_COMP_M = -1;
	DOCENTES_MED_T = -1;
	DOCENTES_MED_H = -1;
	DOCENTES_MED_M = -1;
	DOCENTES_SNI = -1;
	DOCENTES_FPD = -1;
	DOCENTES_CGA = -1;
	DOCENTES_MOV_NAC = -1;
	DOCENTES_MOV_INT = -1;
	ASPIRANTES_ADM = -1;
	BECADOS_T = -1;
	BECADOS_H = -1;
	BECADOS_M = -1;
	ULT_GRAD_DIS = -1;
	EGRESADOS_T = -1;
	EGRESADOS_H = -1;
	EGRESADOS_M = -1;
	EGRESADOS_19 = -1;
	EGRESADOS_20_24 = -1;
	EGRESADOS_25_29 = -1;
	EGRESADOS_30 = -1;
	EGRESADOS_DIS = -1;
	TOTAL_EDAD = -1;
	TOTAL_NI_E = -1;
	NIVEL_PREV = -1;
	TOTAL_D_E = -1;
	TOTAL_D_A = -1;
	TOTAL_D_F = -1;
	QUEST_1 = "-";
	TOTAL_RI_E = -1;
	TOTAL_DES_E = -1;
	TOTAL_MN_E = -1;
	TOTAL_MI_E = -1;
	QUEST_2 = "-";
	TOTAL_UG_E = -1;
	TOTAL_EGRE_E = -1;
	PROM_EGRE = -1.0;
	QUEST_3 = "-";
	QUEST_4 = "-";
	TOTAL_TRAB_E = -1;     
	POR_EGRE_TRAB = -1;
	}
	
	
	public int getTOTAL_EDAD() {
		return TOTAL_EDAD;
	}
	public void setTOTAL_EDAD(int tOTAL_EDAD) {
		TOTAL_EDAD = tOTAL_EDAD;
	}
	public int getTOTAL_NI_E() {
		return TOTAL_NI_E;
	}
	public void setTOTAL_NI_E(int tOTAL_NI_E) {
		TOTAL_NI_E = tOTAL_NI_E;
	}
	public int getNIVEL_PREV() {
		return NIVEL_PREV;
	}
	public void setNIVEL_PREV(int nIVEL_PREV) {
		NIVEL_PREV = nIVEL_PREV;
	}
	public int getTOTAL_D_E() {
		return TOTAL_D_E;
	}
	public void setTOTAL_D_E(int tOTAL_D_E) {
		TOTAL_D_E = tOTAL_D_E;
	}
	public int getTOTAL_D_A() {
		return TOTAL_D_A;
	}
	public void setTOTAL_D_A(int tOTAL_D_A) {
		TOTAL_D_A = tOTAL_D_A;
	}
	public int getTOTAL_D_F() {
		return TOTAL_D_F;
	}
	public void setTOTAL_D_F(int tOTAL_D_F) {
		TOTAL_D_F = tOTAL_D_F;
	}
	public int getTOTAL_RI_E() {
		return TOTAL_RI_E;
	}
	public void setTOTAL_RI_E(int tOTAL_RI_E) {
		TOTAL_RI_E = tOTAL_RI_E;
	}
	public int getTOTAL_DES_E() {
		return TOTAL_DES_E;
	}
	public void setTOTAL_DES_E(int tOTAL_DES_E) {
		TOTAL_DES_E = tOTAL_DES_E;
	}
	public int getTOTAL_MN_E() {
		return TOTAL_MN_E;
	}
	public void setTOTAL_MN_E(int tOTAL_MN_E) {
		TOTAL_MN_E = tOTAL_MN_E;
	}
	public int getTOTAL_MI_E() {
		return TOTAL_MI_E;
	}
	public void setTOTAL_MI_E(int tOTAL_MI_E) {
		TOTAL_MI_E = tOTAL_MI_E;
	}
	public int getTOTAL_UG_E() {
		return TOTAL_UG_E;
	}
	public void setTOTAL_UG_E(int tOTAL_UG_E) {
		TOTAL_UG_E = tOTAL_UG_E;
	}
	public int getTOTAL_EGRE_E() {
		return TOTAL_EGRE_E;
	}
	public void setTOTAL_EGRE_E(int tOTAL_EGRE_E) {
		TOTAL_EGRE_E = tOTAL_EGRE_E;
	}
	public double getPROM_EGRE() {
		return PROM_EGRE;
	}
	public void setPROM_EGRE(double pROM_EGRE) {
		PROM_EGRE = pROM_EGRE;
	}
	public String getQUEST_1() {
		return QUEST_1;
	}
	public void setQUEST_1(String qUEST_1) {
		QUEST_1 = qUEST_1;
	}
	public String getQUEST_2() {
		return QUEST_2;
	}
	public void setQUEST_2(String qUEST_2) {
		QUEST_2 = qUEST_2;
	}
	public String getQUEST_3() {
		return QUEST_3;
	}
	public void setQUEST_3(String qUEST_3) {
		QUEST_3 = qUEST_3;
	}
	public String getQUEST_4() {
		return QUEST_4;
	}
	public void setQUEST_4(String qUEST_4) {
		QUEST_4 = qUEST_4;
	}
	public int getTOTAL_TRAB_E() {
		return TOTAL_TRAB_E;
	}
	public void setTOTAL_TRAB_E(int tOTAL_TRAB_E) {
		TOTAL_TRAB_E = tOTAL_TRAB_E;
	}
	public int getPOR_EGRE_TRAB() {
		return POR_EGRE_TRAB;
	}
	public void setPOR_EGRE_TRAB(int pOR_EGRE_TRAB) {
		POR_EGRE_TRAB = pOR_EGRE_TRAB;
	}
	public int getDOCENTES_T() {
		return DOCENTES_T;
	}
	public void setDOCENTES_T(int dOCENTES_T) {
		DOCENTES_T = dOCENTES_T;
	}
	public int getDOCENTES_H() {
		return DOCENTES_H;
	}
	public void setDOCENTES_H(int dOCENTES_H) {
		DOCENTES_H = dOCENTES_H;
	}
	public int getDOCENTES_M() {
		return DOCENTES_M;
	}
	public void setDOCENTES_M(int dOCENTES_M) {
		DOCENTES_M = dOCENTES_M;
	}
	public int getDOCENTES_30() {
		return DOCENTES_30;
	}
	public void setDOCENTES_30(int dOCENTES_30) {
		DOCENTES_30 = dOCENTES_30;
	}
	public int getDOCENTES_31_49() {
		return DOCENTES_31_49;
	}
	public void setDOCENTES_31_49(int dOCENTES_31_49) {
		DOCENTES_31_49 = dOCENTES_31_49;
	}
	public int getDOCENTES_50() {
		return DOCENTES_50;
	}
	public void setDOCENTES_50(int dOCENTES_50) {
		DOCENTES_50 = dOCENTES_50;
	}
	public int getDOCENTES_DISC() {
		return DOCENTES_DISC;
	}
	public void setDOCENTES_DISC(int dOCENTES_DISC) {
		DOCENTES_DISC = dOCENTES_DISC;
	}
	public int getDOCENTES_EXT() {
		return DOCENTES_EXT;
	}
	public void setDOCENTES_EXT(int dOCENTES_EXT) {
		DOCENTES_EXT = dOCENTES_EXT;
	}
	public int getDOCENTES_0_5() {
		return DOCENTES_0_5;
	}
	public void setDOCENTES_0_5(int dOCENTES_0_5) {
		DOCENTES_0_5 = dOCENTES_0_5;
	}
	public int getDOCENTES_6_15() {
		return DOCENTES_6_15;
	}
	public void setDOCENTES_6_15(int dOCENTES_6_15) {
		DOCENTES_6_15 = dOCENTES_6_15;
	}
	public int getDOCENTES_16_25() {
		return DOCENTES_16_25;
	}
	public void setDOCENTES_16_25(int dOCENTES_16_25) {
		DOCENTES_16_25 = dOCENTES_16_25;
	}
	public int getDOCENTES_26() {
		return DOCENTES_26;
	}
	public void setDOCENTES_26(int dOCENTES_26) {
		DOCENTES_26 = dOCENTES_26;
	}
	public int getDOCENTES_F_D() {
		return DOCENTES_F_D;
	}
	public void setDOCENTES_F_D(int dOCENTES_F_D) {
		DOCENTES_F_D = dOCENTES_F_D;
	}
	public int getDOCENTES_F_M() {
		return DOCENTES_F_M;
	}
	public void setDOCENTES_F_M(int dOCENTES_F_M) {
		DOCENTES_F_M = dOCENTES_F_M;
	}
	public int getDOCENTES_F_ESP() {
		return DOCENTES_F_ESP;
	}
	public void setDOCENTES_F_ESP(int dOCENTES_F_ESP) {
		DOCENTES_F_ESP = dOCENTES_F_ESP;
	}
	public int getDOCENTES_F_LIC() {
		return DOCENTES_F_LIC;
	}
	public void setDOCENTES_F_LIC(int dOCENTES_F_LIC) {
		DOCENTES_F_LIC = dOCENTES_F_LIC;
	}
	public int getDOCENTES_F_OTRO() {
		return DOCENTES_F_OTRO;
	}
	public void setDOCENTES_F_OTRO(int dOCENTES_F_OTRO) {
		DOCENTES_F_OTRO = dOCENTES_F_OTRO;
	}
	public int getDOCENTES_T_COMP() {
		return DOCENTES_T_COMP;
	}
	public void setDOCENTES_T_COMP(int dOCENTES_T_COMP) {
		DOCENTES_T_COMP = dOCENTES_T_COMP;
	}
	public int getDOCENTES_COMP_H() {
		return DOCENTES_COMP_H;
	}
	public void setDOCENTES_COMP_H(int dOCENTES_COMP_H) {
		DOCENTES_COMP_H = dOCENTES_COMP_H;
	}
	public int getDOCENTES_COMP_M() {
		return DOCENTES_COMP_M;
	}
	public void setDOCENTES_COMP_M(int dOCENTES_COMP_M) {
		DOCENTES_COMP_M = dOCENTES_COMP_M;
	}
	public int getDOCENTES_MED_T() {
		return DOCENTES_MED_T;
	}
	public void setDOCENTES_MED_T(int dOCENTES_MED_T) {
		DOCENTES_MED_T = dOCENTES_MED_T;
	}
	public int getDOCENTES_MED_H() {
		return DOCENTES_MED_H;
	}
	public void setDOCENTES_MED_H(int dOCENTES_MED_H) {
		DOCENTES_MED_H = dOCENTES_MED_H;
	}
	public int getDOCENTES_MED_M() {
		return DOCENTES_MED_M;
	}
	public void setDOCENTES_MED_M(int dOCENTES_MED_M) {
		DOCENTES_MED_M = dOCENTES_MED_M;
	}
	public int getDOCENTES_SNI() {
		return DOCENTES_SNI;
	}
	public void setDOCENTES_SNI(int dOCENTES_SNI) {
		DOCENTES_SNI = dOCENTES_SNI;
	}
	public int getDOCENTES_FPD() {
		return DOCENTES_FPD;
	}
	public void setDOCENTES_FPD(int dOCENTES_FPD) {
		DOCENTES_FPD = dOCENTES_FPD;
	}
	public int getDOCENTES_CGA() {
		return DOCENTES_CGA;
	}
	public void setDOCENTES_CGA(int dOCENTES_CGA) {
		DOCENTES_CGA = dOCENTES_CGA;
	}
	public int getDOCENTES_MOV_NAC() {
		return DOCENTES_MOV_NAC;
	}
	public void setDOCENTES_MOV_NAC(int dOCENTES_MOV_NAC) {
		DOCENTES_MOV_NAC = dOCENTES_MOV_NAC;
	}
	public int getDOCENTES_MOV_INT() {
		return DOCENTES_MOV_INT;
	}
	public void setDOCENTES_MOV_INT(int dOCENTES_MOV_INT) {
		DOCENTES_MOV_INT = dOCENTES_MOV_INT;
	}
	public int getASPIRANTES_ADM() {
		return ASPIRANTES_ADM;
	}
	public void setASPIRANTES_ADM(int aSPIRANTES_ADM) {
		ASPIRANTES_ADM = aSPIRANTES_ADM;
	}
	public int getBECADOS_T() {
		return BECADOS_T;
	}
	public void setBECADOS_T(int bECADOS_T) {
		BECADOS_T = bECADOS_T;
	}
	public int getBECADOS_H() {
		return BECADOS_H;
	}
	public void setBECADOS_H(int bECADOS_H) {
		BECADOS_H = bECADOS_H;
	}
	public int getBECADOS_M() {
		return BECADOS_M;
	}
	public void setBECADOS_M(int bECADOS_M) {
		BECADOS_M = bECADOS_M;
	}
	public int getULT_GRAD_DIS() {
		return ULT_GRAD_DIS;
	}
	public void setULT_GRAD_DIS(int uLT_GRAD_DIS) {
		ULT_GRAD_DIS = uLT_GRAD_DIS;
	}
	public int getEGRESADOS_T() {
		return EGRESADOS_T;
	}
	public void setEGRESADOS_T(int eGRESADOS_T) {
		EGRESADOS_T = eGRESADOS_T;
	}
	public int getEGRESADOS_H() {
		return EGRESADOS_H;
	}
	public void setEGRESADOS_H(int eGRESADOS_H) {
		EGRESADOS_H = eGRESADOS_H;
	}
	public int getEGRESADOS_M() {
		return EGRESADOS_M;
	}
	public void setEGRESADOS_M(int eGRESADOS_M) {
		EGRESADOS_M = eGRESADOS_M;
	}
	public int getEGRESADOS_19() {
		return EGRESADOS_19;
	}
	public void setEGRESADOS_19(int eGRESADOS_19) {
		EGRESADOS_19 = eGRESADOS_19;
	}
	public int getEGRESADOS_20_24() {
		return EGRESADOS_20_24;
	}
	public void setEGRESADOS_20_24(int eGRESADOS_20_24) {
		EGRESADOS_20_24 = eGRESADOS_20_24;
	}
	public int getEGRESADOS_25_29() {
		return EGRESADOS_25_29;
	}
	public void setEGRESADOS_25_29(int eGRESADOS_25_29) {
		EGRESADOS_25_29 = eGRESADOS_25_29;
	}
	public int getEGRESADOS_30() {
		return EGRESADOS_30;
	}
	public void setEGRESADOS_30(int eGRESADOS_30) {
		EGRESADOS_30 = eGRESADOS_30;
	}
	public int getEGRESADOS_DIS() {
		return EGRESADOS_DIS;
	}
	public void setEGRESADOS_DIS(int eGRESADOS_DIS) {
		EGRESADOS_DIS = eGRESADOS_DIS;
	}
	public String getPLAN_ID() {
		return PLAN_ID;
	}
	public void setPLAN_ID(String pLAN_ID) {
		PLAN_ID = pLAN_ID;
	}
	public String getPLAN_NOMBRE() {
		return PLAN_NOMBRE;
	}
	public void setPLAN_NOMBRE(String pLAN_NOMBRE) {
		PLAN_NOMBRE = pLAN_NOMBRE;
	}
	public String getFECHA() {
		return FECHA;
	}
	public void setFECHA(String fECHA) {
		FECHA = fECHA;
	}
	public int getDIS_N_ING() {
		return DIS_N_ING;
	}
	public void setDIS_N_ING(int dIS_N_ING) {
		DIS_N_ING = dIS_N_ING;
	}
	public int getDIS_R_ING() {
		return DIS_R_ING;
	}
	public void setDIS_R_ING(int dIS_R_ING) {
		DIS_R_ING = dIS_R_ING;
	}
	public int getDIS_TOTAL() {
		return DIS_TOTAL;
	}
	public void setDIS_TOTAL(int dIS_TOTAL) {
		DIS_TOTAL = dIS_TOTAL;
	}
	public int getREG_ALUM_H() {
		return REG_ALUM_H;
	}
	public void setREG_ALUM_H(int rEG_ALUM_H) {
		REG_ALUM_H = rEG_ALUM_H;
	}
	public int getREG_ALUM_M() {
		return REG_ALUM_M;
	}
	public void setREG_ALUM_M(int rEG_ALUM_M) {
		REG_ALUM_M = rEG_ALUM_M;
	}
	public int getREG_ALUM_T() {
		return REG_ALUM_T;
	}
	public void setREG_ALUM_T(int rEG_ALUM_T) {
		REG_ALUM_T = rEG_ALUM_T;
	}
	public int getCUR_REM_H() {
		return CUR_REM_H;
	}
	public void setCUR_REM_H(int cUR_REM_H) {
		CUR_REM_H = cUR_REM_H;
	}
	public int getCUR_REM_M() {
		return CUR_REM_M;
	}
	public void setCUR_REM_M(int cUR_REM_M) {
		CUR_REM_M = cUR_REM_M;
	}
	public int getCUR_REM_T() {
		return CUR_REM_T;
	}
	public void setCUR_REM_T(int cUR_REM_T) {
		CUR_REM_T = cUR_REM_T;
	}
	public int getRET_SEG_H() {
		return RET_SEG_H;
	}
	public void setRET_SEG_H(int rET_SEG_H) {
		RET_SEG_H = rET_SEG_H;
	}
	public int getRET_SEG_M() {
		return RET_SEG_M;
	}
	public void setRET_SEG_M(int rET_SEG_M) {
		RET_SEG_M = rET_SEG_M;
	}
	public int getRET_SEG_T() {
		return RET_SEG_T;
	}
	public void setRET_SEG_T(int rET_SEG_T) {
		RET_SEG_T = rET_SEG_T;
	}
	public int getDESERT_H() {
		return DESERT_H;
	}
	public void setDESERT_H(int dESERT_H) {
		DESERT_H = dESERT_H;
	}
	public int getDESERT_M() {
		return DESERT_M;
	}
	public void setDESERT_M(int dESERT_M) {
		DESERT_M = dESERT_M;
	}
	public int getDESERT_T() {
		return DESERT_T;
	}
	public void setDESERT_T(int dESERT_T) {
		DESERT_T = dESERT_T;
	}
	public int getDESERT_19() {
		return DESERT_19;
	}
	public void setDESERT_19(int dESERT_19) {
		DESERT_19 = dESERT_19;
	}
	public int getDESERT_20_24() {
		return DESERT_20_24;
	}
	public void setDESERT_20_24(int dESERT_20_24) {
		DESERT_20_24 = dESERT_20_24;
	}
	public int getDESERT_25_29() {
		return DESERT_25_29;
	}
	public void setDESERT_25_29(int dESERT_25_29) {
		DESERT_25_29 = dESERT_25_29;
	}
	public int getDESERT_30() {
		return DESERT_30;
	}
	public void setDESERT_30(int dESERT_30) {
		DESERT_30 = dESERT_30;
	}
	public int getDESERT_DIS() {
		return DESERT_DIS;
	}
	public void setDESERT_DIS(int dESERT_DIS) {
		DESERT_DIS = dESERT_DIS;
	}
	public int getTRAB_SIM_H() {
		return TRAB_SIM_H;
	}
	public void setTRAB_SIM_H(int tRAB_SIM_H) {
		TRAB_SIM_H = tRAB_SIM_H;
	}
	public int getTRAB_SIM_M() {
		return TRAB_SIM_M;
	}
	public void setTRAB_SIM_M(int tRAB_SIM_M) {
		TRAB_SIM_M = tRAB_SIM_M;
	}
	public int getTRAB_SIM_T() {
		return TRAB_SIM_T;
	}
	public void setTRAB_SIM_T(int tRAB_SIM_T) {
		TRAB_SIM_T = tRAB_SIM_T;
	}
	public int getMOVIL_NAC_H() {
		return MOVIL_NAC_H;
	}
	public void setMOVIL_NAC_H(int mOVIL_NAC_H) {
		MOVIL_NAC_H = mOVIL_NAC_H;
	}
	public int getMOVIL_NAC_M() {
		return MOVIL_NAC_M;
	}
	public void setMOVIL_NAC_M(int mOVIL_NAC_M) {
		MOVIL_NAC_M = mOVIL_NAC_M;
	}
	public int getMOVIL_NAC_T() {
		return MOVIL_NAC_T;
	}
	public void setMOVIL_NAC_T(int mOVIL_NAC_T) {
		MOVIL_NAC_T = mOVIL_NAC_T;
	}
	public int getMOVIL_NAC_19() {
		return MOVIL_NAC_19;
	}
	public void setMOVIL_NAC_19(int mOVIL_NAC_19) {
		MOVIL_NAC_19 = mOVIL_NAC_19;
	}
	public int getMOVIL_NAC_20_24() {
		return MOVIL_NAC_20_24;
	}
	public void setMOVIL_NAC_20_24(int mOVIL_NAC_20_24) {
		MOVIL_NAC_20_24 = mOVIL_NAC_20_24;
	}
	public int getMOVIL_NAC_25_29() {
		return MOVIL_NAC_25_29;
	}
	public void setMOVIL_NAC_25_29(int mOVIL_NAC_25_29) {
		MOVIL_NAC_25_29 = mOVIL_NAC_25_29;
	}
	public int getMOVIL_NAC_30() {
		return MOVIL_NAC_30;
	}
	public void setMOVIL_NAC_30(int mOVIL_NAC_30) {
		MOVIL_NAC_30 = mOVIL_NAC_30;
	}
	public int getMOVIL_NAC_DIS() {
		return MOVIL_NAC_DIS;
	}
	public void setMOVIL_NAC_DIS(int mOVIL_NAC_DIS) {
		MOVIL_NAC_DIS = mOVIL_NAC_DIS;
	}
	public int getMOVIL_INT_H() {
		return MOVIL_INT_H;
	}
	public void setMOVIL_INT_H(int mOVIL_INT_H) {
		MOVIL_INT_H = mOVIL_INT_H;
	}
	public int getMOVIL_INT_M() {
		return MOVIL_INT_M;
	}
	public void setMOVIL_INT_M(int mOVIL_INT_M) {
		MOVIL_INT_M = mOVIL_INT_M;
	}
	public int getMOVIL_INT_T() {
		return MOVIL_INT_T;
	}
	public void setMOVIL_INT_T(int mOVIL_INT_T) {
		MOVIL_INT_T = mOVIL_INT_T;
	}
	public int getMOVIL_INT_19() {
		return MOVIL_INT_19;
	}
	public void setMOVIL_INT_19(int mOVIL_INT_19) {
		MOVIL_INT_19 = mOVIL_INT_19;
	}
	public int getMOVIL_INT_20_24() {
		return MOVIL_INT_20_24;
	}
	public void setMOVIL_INT_20_24(int mOVIL_INT_20_24) {
		MOVIL_INT_20_24 = mOVIL_INT_20_24;
	}
	public int getMOVIL_INT_25_29() {
		return MOVIL_INT_25_29;
	}
	public void setMOVIL_INT_25_29(int mOVIL_INT_25_29) {
		MOVIL_INT_25_29 = mOVIL_INT_25_29;
	}
	public int getMOVIL_INT_30() {
		return MOVIL_INT_30;
	}
	public void setMOVIL_INT_30(int mOVIL_INT_30) {
		MOVIL_INT_30 = mOVIL_INT_30;
	}
	public int getMOVIL_INT_DIS() {
		return MOVIL_INT_DIS;
	}
	public void setMOVIL_INT_DIS(int mOVIL_INT_DIS) {
		MOVIL_INT_DIS = mOVIL_INT_DIS;
	}
	public int getSERV_COM_H() {
		return SERV_COM_H;
	}
	public void setSERV_COM_H(int sERV_COM_H) {
		SERV_COM_H = sERV_COM_H;
	}
	public int getSERV_COM_M() {
		return SERV_COM_M;
	}
	public void setSERV_COM_M(int sERV_COM_M) {
		SERV_COM_M = sERV_COM_M;
	}
	public int getSERV_COM_T() {
		return SERV_COM_T;
	}
	public void setSERV_COM_T(int sERV_COM_T) {
		SERV_COM_T = sERV_COM_T;
	}
	public int getSERV_SOC_H() {
		return SERV_SOC_H;
	}
	public void setSERV_SOC_H(int sERV_SOC_H) {
		SERV_SOC_H = sERV_SOC_H;
	}
	public int getSERV_SOC_M() {
		return SERV_SOC_M;
	}
	public void setSERV_SOC_M(int sERV_SOC_M) {
		SERV_SOC_M = sERV_SOC_M;
	}
	public int getSERV_SOC_T() {
		return SERV_SOC_T;
	}
	public void setSERV_SOC_T(int sERV_SOC_T) {
		SERV_SOC_T = sERV_SOC_T;
	}
	public int getEGEL() {
		return EGEL;
	}
	public void setEGEL(int eGEL) {
		EGEL = eGEL;
	}
	public int getEGEL_SOB() {
		return EGEL_SOB;
	}
	public void setEGEL_SOB(int eGEL_SOB) {
		EGEL_SOB = eGEL_SOB;
	}
	public int getTITU_PROG() {
		return TITU_PROG;
	}
	public void setTITU_PROG(int tITU_PROG) {
		TITU_PROG = tITU_PROG;
	}
	public int getCONC() {
		return CONC;
	}
	public void setCONC(int cONC) {
		CONC = cONC;
	}
	public int getEGRE_TRAB_H() {
		return EGRE_TRAB_H;
	}
	public void setEGRE_TRAB_H(int eGRE_TRAB_H) {
		EGRE_TRAB_H = eGRE_TRAB_H;
	}
	public int getEGRE_TRAB_M() {
		return EGRE_TRAB_M;
	}
	public void setEGRE_TRAB_M(int eGRE_TRAB_M) {
		EGRE_TRAB_M = eGRE_TRAB_M;
	}
	public int getEGRE_TRAB_T() {
		return EGRE_TRAB_T;
	}
	public void setEGRE_TRAB_T(int eGRE_TRAB_T) {
		EGRE_TRAB_T = eGRE_TRAB_T;
	}
	public int getEGRE_TRAB_19() {
		return EGRE_TRAB_19;
	}
	public void setEGRE_TRAB_19(int eGRE_TRAB_19) {
		EGRE_TRAB_19 = eGRE_TRAB_19;
	}
	public int getEGRE_TRAB_20_24() {
		return EGRE_TRAB_20_24;
	}
	public void setEGRE_TRAB_20_24(int eGRE_TRAB_20_24) {
		EGRE_TRAB_20_24 = eGRE_TRAB_20_24;
	}
	public int getEGRE_TRAB_25_29() {
		return EGRE_TRAB_25_29;
	}
	public void setEGRE_TRAB_25_29(int eGRE_TRAB_25_29) {
		EGRE_TRAB_25_29 = eGRE_TRAB_25_29;
	}
	public int getEGRE_TRAB_30() {
		return EGRE_TRAB_30;
	}
	public void setEGRE_TRAB_30(int eGRE_TRAB_30) {
		EGRE_TRAB_30 = eGRE_TRAB_30;
	}
	public int getEGRE_TRAB_DIS() {
		return EGRE_TRAB_DIS;
	}
	public void setEGRE_TRAB_DIS(int eGRE_TRAB_DIS) {
		EGRE_TRAB_DIS = eGRE_TRAB_DIS;
	}
	
	
}