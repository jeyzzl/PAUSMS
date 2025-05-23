package aca.web.bsc;

import java.util.HashMap;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.plan.spring.MapaPlan;
import aca.saii.spring.MailSender;
import aca.saii.spring.SaiiAlumno;
import aca.saii.spring.SaiiAlumnoDao;
import aca.saii.spring.SaiiDatosExtra;
import aca.saii.spring.SaiiPeriodo;
import aca.saii.spring.SaiiGrupo;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;

import java.io.ByteArrayInputStream;
import java.io.IOException; 

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContBscSaii {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	aca.saii.spring.SaiiPeriodoDao saiiPeriodoDao;
	
	@Autowired
	aca.sep.spring.SepAlumnoDao sepAlumnoDao;
	
	@Autowired
	aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired
	aca.saii.spring.SaiiDatosExtraDao saiiDatosExtraDao;
	
	@Autowired
	aca.saii.spring.SaiiGrupoDao saiiGrupoDao;
	
	@Autowired
	aca.catalogo.spring.CatEstadoDao catEstadoDao;
	
	@Autowired
	aca.catalogo.spring.CatPaisDao catPaisDao;
	
	@Autowired
	private SaiiAlumnoDao saiiAlumnoDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private AlumPlanDao alumPlanDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/bsc/saii/reporte")
	public String bscSaiiReporte(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 		= "";
		String grupoId	 	= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String usuario		= "0";	
		
		Acceso acceso 		= new Acceso();
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
			usuario 			= (String)sesion.getAttribute("codigoPersonal");
			acceso 				= accesoDao.mapeaRegId(usuario);		
		}
		List<SaiiPeriodo> lisPeriodos = saiiPeriodoDao.listAll(" ORDER BY PERIODO_ID DESC");
		if (periodoId.equals("0") && lisPeriodos.size()>0) {
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		fecha = saiiPeriodoDao.mapeaRegId(periodoId).getFecha();
		List<String> lisPlanes = sepAlumnoDao.listPlanes(fecha, " ORDER BY 1");		
		HashMap<String,MapaPlan> mapaPlanes 	= mapaPlanDao.mapPlanes("'V','A','I'");
		HashMap<String,String> mapaAlumnos 		= saiiAlumnoDao.mapaAlumnosPorPlan(fecha);
		HashMap<String,String> mapaGrupos 		= saiiGrupoDao.mapaGrupos(periodoId);
		
		modelo.addAttribute("acceso",acceso);
		modelo.addAttribute("periodoId",periodoId);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("grupoId",grupoId);
		modelo.addAttribute("lisPeriodos",lisPeriodos);
		modelo.addAttribute("lisPlanes",lisPlanes);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaGrupos",mapaGrupos);
		
		return "bsc/saii/reporte";
	}

	@RequestMapping("/bsc/saii/editarGrupo")
	public String bscSaiiEditarGrupo(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String mensaje 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		
		SaiiGrupo grupo = saiiGrupoDao.mapeaRegId(periodoId, planId);
		
		List<SaiiGrupo> lisGrupo 	  = saiiGrupoDao.lisGrupo();
		
		modelo.addAttribute("periodoId",periodoId);
		modelo.addAttribute("grupo",grupo);
		modelo.addAttribute("lisGrupo",lisGrupo);
		modelo.addAttribute("mensaje",mensaje);
		
		return "bsc/saii/editarGrupo";
	}	
	
	@RequestMapping("/bsc/saii/grabar")
	public String bscSaiiEditarGrabar(HttpServletRequest request, Model modelo){
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String grupoId 		= request.getParameter("Grupo")==null?"0":request.getParameter("Grupo");
		String mensaje 		= "";
		
		SaiiGrupo grupo = saiiGrupoDao.mapeaRegId(periodoId, planId);
		
		grupo.setGrupoId(grupoId);
		
		if(saiiGrupoDao.existeReg(periodoId, planId)) {
			if(saiiGrupoDao.updateReg(grupo)) {
				mensaje = "1";
			}
		}
		
		return "redirect:/bsc/saii/editarGrupo?Mensaje="+mensaje+"&PeriodoId="+periodoId+"&PlanId="+planId;
	}	
	
	@RequestMapping("/bsc/saii/asignar")
	public String bscSaiiAsignar(HttpServletRequest request, Model modelo){
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String fecha 			= saiiPeriodoDao.mapeaRegId(periodoId).getFecha();		
		List<String> lisPlanes 	= sepAlumnoDao.listPlanes(fecha, " ORDER BY 1");		 
		for (String plan : lisPlanes) {
			SaiiGrupo grupo = new SaiiGrupo();		
			grupo.setGrupoId(saiiGrupoDao.maximoReg());
			grupo.setPeriodoId(periodoId);
			grupo.setPlanId(plan);
			if (saiiGrupoDao.existeReg(periodoId, plan)==false){
				saiiGrupoDao.insertReg(grupo);
			}
		}
		
		return "redirect:/bsc/saii/reporte?PeriodoId="+periodoId;
	}
	
	@RequestMapping("/bsc/saii/plan")
	public String bscSaiiPlan(HttpServletRequest request, Model modelo){		
		
		String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String fecha 		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String planNombre	= mapaPlanDao.getNombrePlan(planId);
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("periodoId",periodoId);		
		}		
		//System.out.println("Datos"+fecha+":"+grupoId+":"+planId+":"+periodoId);
		int inTotalFem 			=  saiiAlumnoDao.getByIngreso_generoPorGrupo(fecha, grupoId, "M", "1");
		int inTotalMasc 		=  saiiAlumnoDao.getByIngreso_generoPorGrupo(fecha, grupoId, "H", "1");
		int inTotal    			=  inTotalFem + inTotalMasc;
		int inTotal19    		=  saiiAlumnoDao.getByIngreso_EdadPorGrupo(fecha, grupoId,  0, 19);
		int inTotal2024       	=  saiiAlumnoDao.getByIngreso_EdadPorGrupo(fecha, grupoId, 20, 24);
		int inTotal2529    		=  saiiAlumnoDao.getByIngreso_EdadPorGrupo(fecha, grupoId, 25, 29);
		int inTotal30    		=  saiiAlumnoDao.getByIngreso_EdadPorGrupo(fecha, grupoId, 30,100);
		int inTotalExtranjeros 	=  saiiAlumnoDao.getPrimerIngresoExtranjerosPorGrupo(fecha, grupoId);
		
		int reTotalFem 			=  saiiAlumnoDao.getByReIngreso_generoPorGrupo(fecha, grupoId, "M");
		int reTotalMasc 		=  saiiAlumnoDao.getByReIngreso_generoPorGrupo(fecha, grupoId, "H");
		int reTotal    			=  reTotalFem + reTotalMasc;
		int reTotal19    		=  saiiAlumnoDao.getByReIngreso_EdadPorGrupo(fecha, grupoId,  0, 19);
		int reTotal2024       	=  saiiAlumnoDao.getByReIngreso_EdadPorGrupo(fecha, grupoId, 20, 24);
		int reTotal2529    		=  saiiAlumnoDao.getByReIngreso_EdadPorGrupo(fecha, grupoId, 25, 29);
		int reTotal30    		=  saiiAlumnoDao.getByReIngreso_EdadPorGrupo(fecha, grupoId, 30,100);
		int reTotalExtranjeros 	=  saiiAlumnoDao.getReIngresoExtranjerosPorGrupo(fecha, grupoId); 
		
		int totalFem 			=  saiiAlumnoDao.getByTotal_generoPorGrupo(fecha, grupoId, "M");
		int totalMascu    		=  saiiAlumnoDao.getByTotal_generoPorGrupo(fecha, grupoId, "H");
		int total 		    	=  totalFem + totalMascu;
		int total19    			=  saiiAlumnoDao.getByTotal_EdadPorGrupo(fecha, grupoId,  0, 19);
		int total2024       	=  saiiAlumnoDao.getByTotal_EdadPorGrupo(fecha, grupoId, 20, 24);
		int total2529    		=  saiiAlumnoDao.getByTotal_EdadPorGrupo(fecha, grupoId, 25, 29);
		int total30    			=  saiiAlumnoDao.getByTotal_EdadPorGrupo(fecha, grupoId, 30,100);
		int totalExtranjeros   	=  saiiAlumnoDao.getTotalExtranjerosPorGrupo(fecha, grupoId); 
		
		int ultimoAñoTotalFem 	=  saiiAlumnoDao.getByUltimoAño_generoPorGrupo(fecha, grupoId, "M");
		int ultimoAñoTotalMascu =  saiiAlumnoDao.getByUltimoAño_generoPorGrupo(fecha, grupoId, "H");
		int ultimoAñoTotal 		=  ultimoAñoTotalFem + ultimoAñoTotalMascu;
		int ultimoAñoTotal19    =  saiiAlumnoDao.getByUltimoAño_EdadPorGrupo(fecha, grupoId,  0, 19);
		int ultimoAñoTotal2024  =  saiiAlumnoDao.getByUltimoAño_EdadPorGrupo(fecha, grupoId, 20, 24);
		int ultimoAñoTotal2529  =  saiiAlumnoDao.getByUltimoAño_EdadPorGrupo(fecha, grupoId, 25, 29);
		int ultimoAñoTotal30    =  saiiAlumnoDao.getByUltimoAño_EdadPorGrupo(fecha, grupoId, 30,100);
		
		int reprobadosMasc      =  saiiAlumnoDao.getTotalReprobadosPorGrupo(grupoId, fecha, "H");
		int reprobadosFem 		=  saiiAlumnoDao.getTotalReprobadosPorGrupo(grupoId, fecha, "M");
		int reprobados    		=  reprobadosMasc + reprobadosFem;
		
		float promedioTotalF		=  saiiAlumnoDao.getPromedioG(grupoId, fecha, "F");
		float promedioTotalM		=  saiiAlumnoDao.getPromedioG(grupoId, fecha, "M");
		float promedioTotal			=  (promedioTotalF + promedioTotalM)/2;
		
		HashMap<String, Integer> mapaReporte = new HashMap<>();
		mapaReporte.put("inTotalFem", inTotalFem);
		mapaReporte.put("inTotalMasc", inTotalMasc);
		mapaReporte.put("inTotal", inTotal);
		mapaReporte.put("inTotal19", inTotal19);
		mapaReporte.put("inTotal2024", inTotal2024);
		mapaReporte.put("inTotal2529", inTotal2529);
		mapaReporte.put("inTotal30", inTotal30);
		mapaReporte.put("inTotalExtranjeros", inTotalExtranjeros);
		
		mapaReporte.put("reTotalFem", reTotalFem);
		mapaReporte.put("reTotalMasc", reTotalMasc);
		mapaReporte.put("reTotal", reTotal);
		mapaReporte.put("reTotal19", reTotal19);
		mapaReporte.put("reTotal2024", reTotal2024);
		mapaReporte.put("reTotal2529", reTotal2529);
		mapaReporte.put("reTotal30", reTotal30);
		mapaReporte.put("reTotalExtranjeros", reTotalExtranjeros);
		
		mapaReporte.put("total", total);
		mapaReporte.put("totalFem", totalFem);
		mapaReporte.put("totalMascu", totalMascu);
		mapaReporte.put("total19", total19);
		mapaReporte.put("total2024", total2024);
		mapaReporte.put("total2529", total2529);
		mapaReporte.put("total30", total30);
		mapaReporte.put("totalExtranjeros", totalExtranjeros);
		
		mapaReporte.put("ultimoAñoTotal", ultimoAñoTotal);
		mapaReporte.put("ultimoAñoTotalFem", ultimoAñoTotalFem);
		mapaReporte.put("ultimoAñoTotalMascu", ultimoAñoTotalMascu);
		mapaReporte.put("ultimoAñoTotal19", ultimoAñoTotal19);
		mapaReporte.put("ultimoAñoTotal2024", ultimoAñoTotal2024);
		mapaReporte.put("ultimoAñoTotal2529", ultimoAñoTotal2529);
		mapaReporte.put("ultimoAñoTotal30", ultimoAñoTotal30);
		
		mapaReporte.put("reprobados", reprobados);
		mapaReporte.put("reprobadosMasc", reprobadosMasc);
		mapaReporte.put("reprobadosFem", reprobadosFem);
		
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("mapaReporte", mapaReporte);
		modelo.addAttribute("promedioTotalF", promedioTotalF);
		modelo.addAttribute("promedioTotalM", promedioTotalM);
		modelo.addAttribute("promedioTotal", promedioTotal);
		
		//Datos Extra
		SaiiDatosExtra datosExtra = new SaiiDatosExtra();
		
		if (saiiDatosExtraDao.existeReg(grupoId, fecha)) {
			datosExtra = saiiDatosExtraDao.mapeaRegId(grupoId, fecha);
		}
		modelo.addAttribute("datosExtra", datosExtra);
		
		return "bsc/saii/plan";
	}
	
	@RequestMapping("/bsc/saii/datos")
	public String bscSaiiDatos(HttpServletRequest request, Model modelo){
		
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String fecha 		= request.getParameter("Fecha")==null?"01/01/2020":request.getParameter("Fecha");
		
		String planNombre	= mapaPlanDao.getNombrePlan(planId);
		
		SaiiDatosExtra datosExtra = new SaiiDatosExtra();
		
		if (saiiDatosExtraDao.existeReg(grupoId, fecha)) {
			datosExtra = saiiDatosExtraDao.mapeaRegId(grupoId, fecha);
		}
		
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("datosExtra", datosExtra);
		
		return "bsc/saii/datos";
	}
	
	@RequestMapping("/bsc/saii/guardar")
	public String bscSaiiGuardar(HttpServletRequest request ){
		
		String planId 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String fecha 		= request.getParameter("Fecha")==null?"01/01/2020":request.getParameter("Fecha");
		String grupoId 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		
		String mensaje 		= "";
		
		//Datos
		String dis_n_ing 			= request.getParameter("DIS_N_ING")==null?"-1":request.getParameter("DIS_N_ING");
		String dis_r_ing 			= request.getParameter("DIS_R_ING")==null?"-1":request.getParameter("DIS_R_ING");
		String dis_total    		= request.getParameter("DIS_TOTAL")==null?"-1":request.getParameter("DIS_TOTAL");
		String reg_alum_h   		= request.getParameter("REG_ALUM_H")==null?"-1":request.getParameter("REG_ALUM_H");
		String reg_alum_m   		= request.getParameter("REG_ALUM_M")==null?"-1":request.getParameter("REG_ALUM_M");
		String reg_alum_t   		= request.getParameter("REG_ALUM_T")==null?"-1":request.getParameter("REG_ALUM_T");
		String cur_rem_h    		= request.getParameter("CUR_REM_H")==null?"-1":request.getParameter("CUR_REM_H");
		String cur_rem_m    		= request.getParameter("CUR_REM_M")==null?"-1":request.getParameter("CUR_REM_M");
		String cur_rem_t    		= request.getParameter("CUR_REM_T")==null?"-1":request.getParameter("CUR_REM_T");
		String ret_seg_h    		= request.getParameter("RET_SEG_H")==null?"-1":request.getParameter("RET_SEG_H");
		String ret_seg_m    		= request.getParameter("RET_SEG_M")==null?"-1":request.getParameter("RET_SEG_M");
		String ret_seg_t    		= request.getParameter("RET_SEG_T")==null?"-1":request.getParameter("RET_SEG_T");
		String desert_h     		= request.getParameter("DESERT_H")==null?"-1":request.getParameter("DESERT_H");
		String desert_m     		= request.getParameter("DESERT_M")==null?"-1":request.getParameter("DESERT_M");
		String desert_t     		= request.getParameter("DESERT_T")==null?"-1":request.getParameter("DESERT_T");
		String desert_19			= request.getParameter("DESERT_19")==null?"-1":request.getParameter("DESERT_19");
		String desert_20_24     	= request.getParameter("DESERT_20_24")==null?"-1":request.getParameter("DESERT_20_24");
		String desert_25_29     	= request.getParameter("DESERT_25_29")==null?"-1":request.getParameter("DESERT_25_29");
		String desert_30    		= request.getParameter("DESERT_30")==null?"-1":request.getParameter("DESERT_30");
		String desert_dis    		= request.getParameter("DESERT_DIS")==null?"-1":request.getParameter("DESERT_DIS");
		String trab_sim_h    		= request.getParameter("TRAB_SIM_H")==null?"-1":request.getParameter("TRAB_SIM_H");
		String trab_sim_m    		= request.getParameter("TRAB_SIM_M")==null?"-1":request.getParameter("TRAB_SIM_M");
		String trab_sim_t    		= request.getParameter("TRAB_SIM_T")==null?"-1":request.getParameter("TRAB_SIM_T");
		String movil_nac_h   		= request.getParameter("MOVIL_NAC_H")==null?"-1":request.getParameter("MOVIL_NAC_H");
		String movil_nac_m   		= request.getParameter("MOVIL_NAC_M")==null?"-1":request.getParameter("MOVIL_NAC_M");
		String movil_nac_t   		= request.getParameter("MOVIL_NAC_T")==null?"-1":request.getParameter("MOVIL_NAC_T");
		String movil_nac_19    		= request.getParameter("MOVIL_NAC_19")==null?"-1":request.getParameter("MOVIL_NAC_19");
		String movil_nac_20_24  	= request.getParameter("MOVIL_NAC_20_24")==null?"-1":request.getParameter("MOVIL_NAC_20_24");
		String movil_nac_25_29  	= request.getParameter("MOVIL_NAC_25_29")==null?"-1":request.getParameter("MOVIL_NAC_25_29");
		String movil_nac_30    		= request.getParameter("MOVIL_NAC_30")==null?"-1":request.getParameter("MOVIL_NAC_30");
		String movil_nac_dis    	= request.getParameter("MOVIL_NAC_DIS")==null?"-1":request.getParameter("MOVIL_NAC_DIS");
		String movil_int_h    		= request.getParameter("MOVIL_INT_H")==null?"-1":request.getParameter("MOVIL_INT_H");
		String movil_int_m    		= request.getParameter("MOVIL_INT_M")==null?"-1":request.getParameter("MOVIL_INT_M");
		String movil_int_t    		= request.getParameter("MOVIL_INT_T")==null?"-1":request.getParameter("MOVIL_INT_T");
		String movil_int_19    		= request.getParameter("MOVIL_INT_19")==null?"-1":request.getParameter("MOVIL_INT_19");
		String movil_int_20_24  	= request.getParameter("MOVIL_INT_20_24")==null?"-1":request.getParameter("MOVIL_INT_20_24");
		String movil_int_25_29  	= request.getParameter("MOVIL_INT_25_29")==null?"-1":request.getParameter("MOVIL_INT_25_29");
		String movil_int_30    		= request.getParameter("MOVIL_INT_30")==null?"-1":request.getParameter("MOVIL_INT_30");
		String movil_int_dis    	= request.getParameter("MOVIL_INT_DIS")==null?"-1":request.getParameter("MOVIL_INT_DIS");
		String serv_com_h 			= request.getParameter("SERV_COM_H")==null?"-1":request.getParameter("SERV_COM_H");
		String serv_com_m 			= request.getParameter("SERV_COM_M")==null?"-1":request.getParameter("SERV_COM_M");
		String serv_com_t 			= request.getParameter("SERV_COM_T")==null?"-1":request.getParameter("SERV_COM_T");
		String serv_soc_h 			= request.getParameter("SERV_SOC_H")==null?"-1":request.getParameter("SERV_SOC_H");
		String serv_soc_m 			= request.getParameter("SERV_SOC_M")==null?"-1":request.getParameter("SERV_SOC_M");
		String serv_soc_t 			= request.getParameter("SERV_SOC_T")==null?"-1":request.getParameter("SERV_SOC_T");
		String egel 				= request.getParameter("EGEL")==null?"-1":request.getParameter("EGEL");
		String egel_sob 			= request.getParameter("EGEL_SOB")==null?"-1":request.getParameter("EGEL_SOB");
		String titu_prog 			= request.getParameter("TITU_PROG")==null?"-1":request.getParameter("TITU_PROG");
		String conc 				= request.getParameter("CONC")==null?"-1":request.getParameter("CONC");
		String egre_trab_h 			= request.getParameter("EGRE_TRAB_H")==null?"-1":request.getParameter("EGRE_TRAB_H");
		String egre_trab_m 			= request.getParameter("EGRE_TRAB_M")==null?"-1":request.getParameter("EGRE_TRAB_M");
		String egre_trab_t 			= request.getParameter("EGRE_TRAB_T")==null?"-1":request.getParameter("EGRE_TRAB_T");
		String egre_trab_19 		= request.getParameter("EGRE_TRAB_19")==null?"-1":request.getParameter("EGRE_TRAB_19");
		String egre_trab_20_24 		= request.getParameter("EGRE_TRAB_20_24")==null?"-1":request.getParameter("EGRE_TRAB_20_24");
		String egre_trab_25_29 		= request.getParameter("EGRE_TRAB_25_29")==null?"-1":request.getParameter("EGRE_TRAB_25_29");
		String egre_trab_30 		= request.getParameter("EGRE_TRAB_30")==null?"-1":request.getParameter("EGRE_TRAB_30");
		String egre_trab_dis 		= request.getParameter("EGRE_TRAB_DIS")==null?"-1":request.getParameter("EGRE_TRAB_DIS");
		String docentes_t 			= request.getParameter("DOCENTES_T")==null?"-1":request.getParameter("DOCENTES_T");
		String docentes_h 			= request.getParameter("DOCENTES_H")==null?"-1":request.getParameter("DOCENTES_H");
		String docentes_m 			= request.getParameter("DOCENTES_M")==null?"-1":request.getParameter("DOCENTES_M");
		String docentes_30 			= request.getParameter("DOCENTES_30")==null?"-1":request.getParameter("DOCENTES_30");
		String docentes_31_49 		= request.getParameter("DOCENTES_31_49")==null?"-1":request.getParameter("DOCENTES_31_49");
		String docentes_50 			= request.getParameter("DOCENTES_50")==null?"-1":request.getParameter("DOCENTES_50");
		String docentes_disc 		= request.getParameter("DOCENTES_DISC")==null?"-1":request.getParameter("DOCENTES_DISC");
		String docentes_ext 		= request.getParameter("DOCENTES_EXT")==null?"-1":request.getParameter("DOCENTES_EXT");
		String docentes_0_5 		= request.getParameter("DOCENTES_0_5")==null?"-1":request.getParameter("DOCENTES_0_5");
		String docentes_6_15 		= request.getParameter("DOCENTES_6_15")==null?"-1":request.getParameter("DOCENTES_6_15");
		String docentes_16_25 		= request.getParameter("DOCENTES_16_25")==null?"-1":request.getParameter("DOCENTES_16_25");
		String docentes_26 			= request.getParameter("DOCENTES_26")==null?"-1":request.getParameter("DOCENTES_26");
		String docentes_f_d 		= request.getParameter("DOCENTES_F_D")==null?"-1":request.getParameter("DOCENTES_F_D");
		String docentes_f_m 		= request.getParameter("DOCENTES_F_M")==null?"-1":request.getParameter("DOCENTES_F_M");
		String docentes_f_esp 		= request.getParameter("DOCENTES_F_ESP")==null?"-1":request.getParameter("DOCENTES_F_ESP");
		String docentes_f_lic 		= request.getParameter("DOCENTES_F_LIC")==null?"-1":request.getParameter("DOCENTES_F_LIC");
		String docentes_f_otro 		= request.getParameter("DOCENTES_F_OTRO")==null?"-1":request.getParameter("DOCENTES_F_OTRO");
		String docentes_t_comp 		= request.getParameter("DOCENTES_T_COMP")==null?"-1":request.getParameter("DOCENTES_T_COMP");
		String docentes_comp_h 		= request.getParameter("DOCENTES_COMP_H")==null?"-1":request.getParameter("DOCENTES_COMP_H");
		String docentes_comp_m 		= request.getParameter("DOCENTES_COMP_M")==null?"-1":request.getParameter("DOCENTES_COMP_M");
		String docentes_med_t 		= request.getParameter("DOCENTES_MED_T")==null?"-1":request.getParameter("DOCENTES_MED_T");
		String docentes_med_h 		= request.getParameter("DOCENTES_MED_H")==null?"-1":request.getParameter("DOCENTES_MED_H");
		String docentes_med_m 		= request.getParameter("DOCENTES_MED_M")==null?"-1":request.getParameter("DOCENTES_MED_M");
		String docentes_sni 		= request.getParameter("DOCENTES_SNI")==null?"-1":request.getParameter("DOCENTES_SNI");
		String docentes_fpd 		= request.getParameter("DOCENTES_FPD")==null?"-1":request.getParameter("DOCENTES_FPD");
		String docentes_cga 		= request.getParameter("DOCENTES_CGA")==null?"-1":request.getParameter("DOCENTES_CGA");
		String docentes_mov_nac 	= request.getParameter("DOCENTES_MOV_NAC")==null?"-1":request.getParameter("DOCENTES_MOV_NAC");
		String docentes_mov_int 	= request.getParameter("DOCENTES_MOV_INT")==null?"-1":request.getParameter("DOCENTES_MOV_INT");
		String aspirantes_adm 		= request.getParameter("ASPIRANTES_ADM")==null?"-1":request.getParameter("ASPIRANTES_ADM");
		String becados_t 			= request.getParameter("BECADOS_T")==null?"-1":request.getParameter("BECADOS_T");
		String becados_h 			= request.getParameter("BECADOS_H")==null?"-1":request.getParameter("BECADOS_H");
		String becados_m 			= request.getParameter("BECADOS_M")==null?"-1":request.getParameter("BECADOS_M");
		String ult_grad_dis 		= request.getParameter("ULT_GRAD_DIS")==null?"-1":request.getParameter("ULT_GRAD_DIS");
		String egresados_t 			= request.getParameter("EGRESADOS_T")==null?"-1":request.getParameter("EGRESADOS_T");
		String egresados_h 			= request.getParameter("EGRESADOS_H")==null?"-1":request.getParameter("EGRESADOS_H");
		String egresados_m 			= request.getParameter("EGRESADOS_M")==null?"-1":request.getParameter("EGRESADOS_M");
		String egresados_19 		= request.getParameter("EGRESADOS_19")==null?"-1":request.getParameter("EGRESADOS_19");
		String egresados_20_24 		= request.getParameter("EGRESADOS_20_24")==null?"-1":request.getParameter("EGRESADOS_20_24");
		String egresados_25_29 		= request.getParameter("EGRESADOS_25_29")==null?"-1":request.getParameter("EGRESADOS_25_29");
		String egresados_30 		= request.getParameter("EGRESADOS_30")==null?"-1":request.getParameter("EGRESADOS_30");
		String egresados_dis 		= request.getParameter("EGRESADOS_DIS")==null?"-1":request.getParameter("EGRESADOS_DIS");
		String total_edad			= request.getParameter("TOTAL_EDAD")==null?"-1":request.getParameter("TOTAL_EDAD");
		String total_ni_e			= request.getParameter("TOTAL_NI_E")==null?"-1":request.getParameter("TOTAL_NI_E");
		String nivel_prev			= request.getParameter("NIVEL_PREV")==null?"-1":request.getParameter("NIVEL_PREV");
		String total_d_e			= request.getParameter("TOTAL_D_E")==null?"-1":request.getParameter("TOTAL_D_E");
		String total_d_a			= request.getParameter("TOTAL_D_A")==null?"-1":request.getParameter("TOTAL_D_A");
		String total_d_f			= request.getParameter("TOTAL_D_F")==null?"-1":request.getParameter("TOTAL_D_F");
		String quest_1				= request.getParameter("QUEST_1")==null?"-1":request.getParameter("QUEST_1");
		String total_ri_e			= request.getParameter("TOTAL_RI_E")==null?"-1":request.getParameter("TOTAL_RI_E");
		String total_des_e			= request.getParameter("TOTAL_DES_E")==null?"-1":request.getParameter("TOTAL_DES_E");
		String total_mn_e			= request.getParameter("TOTAL_MN_E")==null?"-1":request.getParameter("TOTAL_MN_E");
		String total_mi_e			= request.getParameter("TOTAL_MI_E")==null?"-1":request.getParameter("TOTAL_MI_E");
		String quest_2				= request.getParameter("QUEST_2")==null?"-1":request.getParameter("QUEST_2");
		String total_ug_e			= request.getParameter("TOTAL_UG_E")==null?"-1":request.getParameter("TOTAL_UG_E");
		String total_egre_e			= request.getParameter("TOTAL_EGRE_E")==null?"-1":request.getParameter("TOTAL_EGRE_E");
		String prom_egre			= request.getParameter("PROM_EGRE")==null?"-1":request.getParameter("PROM_EGRE");
		String quest_3				= request.getParameter("QUEST_3")==null?"-1":request.getParameter("QUEST_3");
		String quest_4				= request.getParameter("QUEST_4")==null?"-1":request.getParameter("QUEST_4");
		String total_trab_e			= request.getParameter("TOTAL_TRAB_E")==null?"-1":request.getParameter("TOTAL_TRAB_E");
		String por_egre_trab		= request.getParameter("POR_EGRE_TRAB")==null?"-1":request.getParameter("POR_EGRE_TRAB");
		
		SaiiDatosExtra datosExtra = new SaiiDatosExtra();		
		if(saiiDatosExtraDao.existeReg(grupoId, fecha)) {
			datosExtra 		= saiiDatosExtraDao.mapeaRegId(grupoId, fecha);	
			datosExtra.setPLAN_ID(grupoId);
			datosExtra.setPLAN_NOMBRE(mapaPlanDao.getNombrePlan(planId));
			datosExtra.setFECHA(fecha);
			datosExtra.setDIS_N_ING(Integer.parseInt(dis_n_ing));
			datosExtra.setDIS_R_ING(Integer.parseInt(dis_r_ing));
			datosExtra.setDIS_TOTAL(Integer.parseInt(dis_total));
			datosExtra.setREG_ALUM_H(Integer.parseInt(reg_alum_h));
			datosExtra.setREG_ALUM_M(Integer.parseInt(reg_alum_m));
			datosExtra.setREG_ALUM_T(Integer.parseInt(reg_alum_t));
			datosExtra.setCUR_REM_H(Integer.parseInt(cur_rem_h));
			datosExtra.setCUR_REM_M(Integer.parseInt(cur_rem_m));
			datosExtra.setCUR_REM_T(Integer.parseInt(cur_rem_t));
			datosExtra.setRET_SEG_H(Integer.parseInt(ret_seg_h));
			datosExtra.setRET_SEG_M(Integer.parseInt(ret_seg_m));
			datosExtra.setRET_SEG_T(Integer.parseInt(ret_seg_t));
			datosExtra.setDESERT_H(Integer.parseInt(desert_h));
			datosExtra.setDESERT_M(Integer.parseInt(desert_m));
			datosExtra.setDESERT_T(Integer.parseInt(desert_t));
			datosExtra.setDESERT_19(Integer.parseInt(desert_19));
			datosExtra.setDESERT_20_24(Integer.parseInt(desert_20_24));
			datosExtra.setDESERT_25_29(Integer.parseInt(desert_25_29));
			datosExtra.setDESERT_30(Integer.parseInt(desert_30));
			datosExtra.setDESERT_DIS(Integer.parseInt(desert_dis));
			datosExtra.setTRAB_SIM_H(Integer.parseInt(trab_sim_h));
			datosExtra.setTRAB_SIM_M(Integer.parseInt(trab_sim_m));
			datosExtra.setTRAB_SIM_T(Integer.parseInt(trab_sim_t));
			datosExtra.setMOVIL_NAC_H(Integer.parseInt(movil_nac_h));
			datosExtra.setMOVIL_NAC_M(Integer.parseInt(movil_nac_m));
			datosExtra.setMOVIL_NAC_T(Integer.parseInt(movil_nac_t));
			datosExtra.setMOVIL_NAC_19(Integer.parseInt(movil_nac_19));
			datosExtra.setMOVIL_NAC_20_24(Integer.parseInt(movil_nac_20_24));
			datosExtra.setMOVIL_NAC_25_29(Integer.parseInt(movil_nac_25_29));
			datosExtra.setMOVIL_NAC_30(Integer.parseInt(movil_nac_30));
			datosExtra.setMOVIL_NAC_DIS(Integer.parseInt(movil_nac_dis));
			datosExtra.setMOVIL_INT_H(Integer.parseInt(movil_int_h));
			datosExtra.setMOVIL_INT_M(Integer.parseInt(movil_int_m));
			datosExtra.setMOVIL_INT_T(Integer.parseInt(movil_int_t));
			datosExtra.setMOVIL_INT_19(Integer.parseInt(movil_int_19));
			datosExtra.setMOVIL_INT_20_24(Integer.parseInt(movil_int_20_24));
			datosExtra.setMOVIL_INT_25_29(Integer.parseInt(movil_int_25_29));
			datosExtra.setMOVIL_INT_30(Integer.parseInt(movil_int_30));
			datosExtra.setMOVIL_INT_DIS(Integer.parseInt(movil_int_dis));
			datosExtra.setSERV_COM_H(Integer.parseInt(serv_com_h));
			datosExtra.setSERV_COM_M(Integer.parseInt(serv_com_m));
			datosExtra.setSERV_COM_T(Integer.parseInt(serv_com_t));
			datosExtra.setSERV_SOC_H(Integer.parseInt(serv_soc_h));
			datosExtra.setSERV_SOC_M(Integer.parseInt(serv_soc_m));
			datosExtra.setSERV_SOC_T(Integer.parseInt(serv_soc_t));
			datosExtra.setEGEL(Integer.parseInt(egel));
			datosExtra.setEGEL_SOB(Integer.parseInt(egel_sob));
			datosExtra.setTITU_PROG(Integer.parseInt(titu_prog));
			datosExtra.setCONC(Integer.parseInt(conc));
			datosExtra.setEGRE_TRAB_H(Integer.parseInt(egre_trab_h));
			datosExtra.setEGRE_TRAB_M(Integer.parseInt(egre_trab_m));
			datosExtra.setEGRE_TRAB_T(Integer.parseInt(egre_trab_t));
			datosExtra.setEGRE_TRAB_19(Integer.parseInt(egre_trab_19));
			datosExtra.setEGRE_TRAB_20_24(Integer.parseInt(egre_trab_20_24));
			datosExtra.setEGRE_TRAB_25_29(Integer.parseInt(egre_trab_25_29));
			datosExtra.setEGRE_TRAB_30(Integer.parseInt(egre_trab_30));
			datosExtra.setEGRE_TRAB_DIS(Integer.parseInt(egre_trab_dis));
			datosExtra.setDOCENTES_T(Integer.parseInt(docentes_t));
			datosExtra.setDOCENTES_H(Integer.parseInt(docentes_h));
			datosExtra.setDOCENTES_M(Integer.parseInt(docentes_m));
			datosExtra.setDOCENTES_30(Integer.parseInt(docentes_30));
			datosExtra.setDOCENTES_31_49(Integer.parseInt(docentes_31_49));
			datosExtra.setDOCENTES_50(Integer.parseInt(docentes_50));
			datosExtra.setDOCENTES_DISC(Integer.parseInt(docentes_disc));
			datosExtra.setDOCENTES_EXT(Integer.parseInt(docentes_ext));
			datosExtra.setDOCENTES_0_5(Integer.parseInt(docentes_0_5));
			datosExtra.setDOCENTES_6_15(Integer.parseInt(docentes_6_15));
			datosExtra.setDOCENTES_16_25(Integer.parseInt(docentes_16_25));
			datosExtra.setDOCENTES_26(Integer.parseInt(docentes_26));
			datosExtra.setDOCENTES_F_D(Integer.parseInt(docentes_f_d));
			datosExtra.setDOCENTES_F_M(Integer.parseInt(docentes_f_m));
			datosExtra.setDOCENTES_F_ESP(Integer.parseInt(docentes_f_esp));
			datosExtra.setDOCENTES_F_LIC(Integer.parseInt(docentes_f_lic));
			datosExtra.setDOCENTES_F_OTRO(Integer.parseInt(docentes_f_otro));
			datosExtra.setDOCENTES_T_COMP(Integer.parseInt(docentes_t_comp));
			datosExtra.setDOCENTES_COMP_H(Integer.parseInt(docentes_comp_h));
			datosExtra.setDOCENTES_COMP_M(Integer.parseInt(docentes_comp_m));
			datosExtra.setDOCENTES_MED_T(Integer.parseInt(docentes_med_t));
			datosExtra.setDOCENTES_MED_H(Integer.parseInt(docentes_med_h));
			datosExtra.setDOCENTES_MED_M(Integer.parseInt(docentes_med_m));
			datosExtra.setDOCENTES_SNI(Integer.parseInt(docentes_sni));
			datosExtra.setDOCENTES_FPD(Integer.parseInt(docentes_fpd));
			datosExtra.setDOCENTES_CGA(Integer.parseInt(docentes_cga));
			datosExtra.setDOCENTES_MOV_NAC(Integer.parseInt(docentes_mov_nac));
			datosExtra.setDOCENTES_MOV_INT(Integer.parseInt(docentes_mov_int));
			datosExtra.setASPIRANTES_ADM(Integer.parseInt(aspirantes_adm));
			datosExtra.setBECADOS_T(Integer.parseInt(becados_t));
			datosExtra.setBECADOS_H(Integer.parseInt(becados_h));
			datosExtra.setBECADOS_M(Integer.parseInt(becados_m));
			datosExtra.setULT_GRAD_DIS(Integer.parseInt(ult_grad_dis));
			datosExtra.setEGRESADOS_T(Integer.parseInt(egresados_t));
			datosExtra.setEGRESADOS_H(Integer.parseInt(egresados_h));
			datosExtra.setEGRESADOS_M(Integer.parseInt(egresados_m));
			datosExtra.setEGRESADOS_19(Integer.parseInt(egresados_19));
			datosExtra.setEGRESADOS_20_24(Integer.parseInt(egresados_20_24));
			datosExtra.setEGRESADOS_25_29(Integer.parseInt(egresados_25_29));
			datosExtra.setEGRESADOS_30(Integer.parseInt(egresados_30));
			datosExtra.setEGRESADOS_DIS(Integer.parseInt(egresados_dis));
			datosExtra.setTOTAL_EDAD(Integer.parseInt(total_edad));
			datosExtra.setTOTAL_NI_E(Integer.parseInt(total_ni_e));
			datosExtra.setNIVEL_PREV(Integer.parseInt(nivel_prev));
			datosExtra.setTOTAL_D_E(Integer.parseInt(total_d_e));
			datosExtra.setTOTAL_D_A(Integer.parseInt(total_d_a));
			datosExtra.setTOTAL_D_F(Integer.parseInt(total_d_f));
			datosExtra.setQUEST_1(quest_1);
			datosExtra.setTOTAL_RI_E(Integer.parseInt(total_ri_e));
			datosExtra.setTOTAL_DES_E(Integer.parseInt(total_des_e));
			datosExtra.setTOTAL_MN_E(Integer.parseInt(total_mn_e));
			datosExtra.setTOTAL_MI_E(Integer.parseInt(total_mi_e));
			datosExtra.setQUEST_2(quest_2);
			datosExtra.setTOTAL_UG_E(Integer.parseInt(total_ug_e));
			datosExtra.setTOTAL_EGRE_E(Integer.parseInt(total_egre_e));
			datosExtra.setPROM_EGRE(Float.parseFloat(prom_egre));
			datosExtra.setQUEST_3(quest_3);
			datosExtra.setQUEST_4(quest_4);
			datosExtra.setTOTAL_TRAB_E(Integer.parseInt(total_trab_e));
			datosExtra.setPOR_EGRE_TRAB(Integer.parseInt(por_egre_trab));
			
			if (saiiDatosExtraDao.updateDat(datosExtra)) {
				mensaje = "0";
				//System.out.println(mensaje);
			}
			
		}else {
			datosExtra.setPLAN_ID(grupoId);
			datosExtra.setPLAN_NOMBRE(mapaPlanDao.getNombrePlan(planId));
			datosExtra.setFECHA(fecha);
			datosExtra.setDIS_N_ING(Integer.parseInt(dis_n_ing));
			datosExtra.setDIS_R_ING(Integer.parseInt(dis_r_ing));
			datosExtra.setDIS_TOTAL(Integer.parseInt(dis_total));
			datosExtra.setREG_ALUM_H(Integer.parseInt(reg_alum_h));
			datosExtra.setREG_ALUM_M(Integer.parseInt(reg_alum_m));
			datosExtra.setREG_ALUM_T(Integer.parseInt(reg_alum_t));
			datosExtra.setCUR_REM_H(Integer.parseInt(cur_rem_h));
			datosExtra.setCUR_REM_M(Integer.parseInt(cur_rem_m));
			datosExtra.setCUR_REM_T(Integer.parseInt(cur_rem_t));
			datosExtra.setRET_SEG_H(Integer.parseInt(ret_seg_h));
			datosExtra.setRET_SEG_M(Integer.parseInt(ret_seg_m));
			datosExtra.setRET_SEG_T(Integer.parseInt(ret_seg_t));
			datosExtra.setDESERT_H(Integer.parseInt(desert_h));
			datosExtra.setDESERT_M(Integer.parseInt(desert_m));
			datosExtra.setDESERT_T(Integer.parseInt(desert_t));
			datosExtra.setDESERT_19(Integer.parseInt(desert_19));
			datosExtra.setDESERT_20_24(Integer.parseInt(desert_20_24));
			datosExtra.setDESERT_25_29(Integer.parseInt(desert_25_29));
			datosExtra.setDESERT_30(Integer.parseInt(desert_30));
			datosExtra.setDESERT_DIS(Integer.parseInt(desert_dis));
			datosExtra.setTRAB_SIM_H(Integer.parseInt(trab_sim_h));
			datosExtra.setTRAB_SIM_M(Integer.parseInt(trab_sim_m));
			datosExtra.setTRAB_SIM_T(Integer.parseInt(trab_sim_t));
			datosExtra.setMOVIL_NAC_H(Integer.parseInt(movil_nac_h));
			datosExtra.setMOVIL_NAC_M(Integer.parseInt(movil_nac_m));
			datosExtra.setMOVIL_NAC_T(Integer.parseInt(movil_nac_t));
			datosExtra.setMOVIL_NAC_19(Integer.parseInt(movil_nac_19));
			datosExtra.setMOVIL_NAC_20_24(Integer.parseInt(movil_nac_20_24));
			datosExtra.setMOVIL_NAC_25_29(Integer.parseInt(movil_nac_25_29));
			datosExtra.setMOVIL_INT_H(Integer.parseInt(movil_int_h));
			datosExtra.setMOVIL_INT_M(Integer.parseInt(movil_int_m));
			datosExtra.setMOVIL_INT_T(Integer.parseInt(movil_int_t));
			datosExtra.setMOVIL_INT_19(Integer.parseInt(movil_int_19));
			datosExtra.setMOVIL_INT_20_24(Integer.parseInt(movil_int_20_24));
			datosExtra.setMOVIL_INT_25_29(Integer.parseInt(movil_int_25_29));
			datosExtra.setMOVIL_INT_30(Integer.parseInt(movil_int_30));
			datosExtra.setMOVIL_INT_DIS(Integer.parseInt(movil_int_dis));
			datosExtra.setSERV_COM_H(Integer.parseInt(serv_com_h));
			datosExtra.setSERV_COM_M(Integer.parseInt(serv_com_m));
			datosExtra.setSERV_COM_T(Integer.parseInt(serv_com_t));
			datosExtra.setSERV_SOC_H(Integer.parseInt(serv_soc_h));
			datosExtra.setSERV_SOC_M(Integer.parseInt(serv_soc_m));
			datosExtra.setSERV_SOC_T(Integer.parseInt(serv_soc_t));
			datosExtra.setEGEL(Integer.parseInt(egel));
			datosExtra.setEGEL_SOB(Integer.parseInt(egel_sob));
			datosExtra.setTITU_PROG(Integer.parseInt(titu_prog));
			datosExtra.setCONC(Integer.parseInt(conc));
			datosExtra.setEGRE_TRAB_H(Integer.parseInt(egre_trab_h));
			datosExtra.setEGRE_TRAB_M(Integer.parseInt(egre_trab_m));
			datosExtra.setEGRE_TRAB_T(Integer.parseInt(egre_trab_t));
			datosExtra.setEGRE_TRAB_19(Integer.parseInt(egre_trab_19));
			datosExtra.setEGRE_TRAB_20_24(Integer.parseInt(egre_trab_20_24));
			datosExtra.setEGRE_TRAB_25_29(Integer.parseInt(egre_trab_25_29));
			datosExtra.setEGRE_TRAB_30(Integer.parseInt(egre_trab_30));
			datosExtra.setEGRE_TRAB_DIS(Integer.parseInt(egre_trab_dis));
			datosExtra.setDOCENTES_T(Integer.parseInt(docentes_t));
			datosExtra.setDOCENTES_H(Integer.parseInt(docentes_h));
			datosExtra.setDOCENTES_M(Integer.parseInt(docentes_m));
			datosExtra.setDOCENTES_30(Integer.parseInt(docentes_30));
			datosExtra.setDOCENTES_31_49(Integer.parseInt(docentes_31_49));
			datosExtra.setDOCENTES_50(Integer.parseInt(docentes_50));
			datosExtra.setDOCENTES_DISC(Integer.parseInt(docentes_disc));
			datosExtra.setDOCENTES_EXT(Integer.parseInt(docentes_ext));
			datosExtra.setDOCENTES_0_5(Integer.parseInt(docentes_0_5));
			datosExtra.setDOCENTES_6_15(Integer.parseInt(docentes_6_15));
			datosExtra.setDOCENTES_16_25(Integer.parseInt(docentes_16_25));
			datosExtra.setDOCENTES_26(Integer.parseInt(docentes_26));
			datosExtra.setDOCENTES_F_D(Integer.parseInt(docentes_f_d));
			datosExtra.setDOCENTES_F_M(Integer.parseInt(docentes_f_m));
			datosExtra.setDOCENTES_F_ESP(Integer.parseInt(docentes_f_esp));
			datosExtra.setDOCENTES_F_LIC(Integer.parseInt(docentes_f_lic));
			datosExtra.setDOCENTES_F_OTRO(Integer.parseInt(docentes_f_otro));
			datosExtra.setDOCENTES_T_COMP(Integer.parseInt(docentes_t_comp));
			datosExtra.setDOCENTES_COMP_H(Integer.parseInt(docentes_comp_h));
			datosExtra.setDOCENTES_COMP_M(Integer.parseInt(docentes_comp_m));
			datosExtra.setDOCENTES_MED_T(Integer.parseInt(docentes_med_t));
			datosExtra.setDOCENTES_MED_H(Integer.parseInt(docentes_med_h));
			datosExtra.setDOCENTES_MED_M(Integer.parseInt(docentes_med_m));
			datosExtra.setDOCENTES_SNI(Integer.parseInt(docentes_sni));
			datosExtra.setDOCENTES_FPD(Integer.parseInt(docentes_fpd));
			datosExtra.setDOCENTES_CGA(Integer.parseInt(docentes_cga));
			datosExtra.setDOCENTES_MOV_NAC(Integer.parseInt(docentes_mov_nac));
			datosExtra.setDOCENTES_MOV_INT(Integer.parseInt(docentes_mov_int));
			datosExtra.setASPIRANTES_ADM(Integer.parseInt(aspirantes_adm));
			datosExtra.setBECADOS_T(Integer.parseInt(becados_t));
			datosExtra.setBECADOS_H(Integer.parseInt(becados_h));
			datosExtra.setBECADOS_M(Integer.parseInt(becados_m));
			datosExtra.setULT_GRAD_DIS(Integer.parseInt(ult_grad_dis));
			datosExtra.setEGRESADOS_T(Integer.parseInt(egresados_t));
			datosExtra.setEGRESADOS_H(Integer.parseInt(egresados_h));
			datosExtra.setEGRESADOS_M(Integer.parseInt(egresados_m));
			datosExtra.setEGRESADOS_19(Integer.parseInt(egresados_19));
			datosExtra.setEGRESADOS_20_24(Integer.parseInt(egresados_20_24));
			datosExtra.setEGRESADOS_25_29(Integer.parseInt(egresados_25_29));
			datosExtra.setEGRESADOS_30(Integer.parseInt(egresados_30));
			datosExtra.setEGRESADOS_DIS(Integer.parseInt(egresados_dis));
			datosExtra.setTOTAL_EDAD(Integer.parseInt(total_edad));
			datosExtra.setTOTAL_NI_E(Integer.parseInt(total_ni_e));
			datosExtra.setNIVEL_PREV(Integer.parseInt(nivel_prev));
			datosExtra.setTOTAL_D_E(Integer.parseInt(total_d_e));
			datosExtra.setTOTAL_D_A(Integer.parseInt(total_d_a));
			datosExtra.setTOTAL_D_F(Integer.parseInt(total_d_f));
			datosExtra.setQUEST_1(quest_1);
			datosExtra.setTOTAL_RI_E(Integer.parseInt(total_ri_e));
			datosExtra.setTOTAL_DES_E(Integer.parseInt(total_des_e));
			datosExtra.setTOTAL_MN_E(Integer.parseInt(total_mn_e));
			datosExtra.setTOTAL_MI_E(Integer.parseInt(total_mi_e));
			datosExtra.setQUEST_2(quest_2);
			datosExtra.setTOTAL_UG_E(Integer.parseInt(total_ug_e));
			datosExtra.setTOTAL_EGRE_E(Integer.parseInt(total_egre_e));
			datosExtra.setPROM_EGRE(Double.parseDouble(prom_egre));
			datosExtra.setQUEST_3(quest_3);
			datosExtra.setQUEST_4(quest_4);
			datosExtra.setTOTAL_TRAB_E(Integer.parseInt(total_trab_e));
			datosExtra.setPOR_EGRE_TRAB(Integer.parseInt(por_egre_trab));
			
			if (saiiDatosExtraDao.insertDat(datosExtra)) {
				mensaje = "1";
				//System.out.println(mensaje);
			}
		}
		
		return "redirect:/bsc/saii/datos?PlanId="+planId+"&Fecha="+fecha+"&GrupoId="+grupoId+"&Mensaje="+mensaje;
	}
	


	@RequestMapping("/bsc/saii/descargardatosextra/{d}/{m}/{y}")
	    public ResponseEntity<InputStreamResource> downloadExtra(@PathVariable String d,@PathVariable String m, @PathVariable String y) throws IOException {
			String fecha = d + "/" + m + "/" + y;
	        List<SaiiDatosExtra> ListaSaiiDatosExtra = null;
			ListaSaiiDatosExtra = (List<SaiiDatosExtra>) saiiDatosExtraDao.getAllbyFecha(fecha);//llamar a dao
	    
	    ByteArrayInputStream in = aca.saii.spring.Exceldownloader.SaiiDatosExtrasToExcel(ListaSaiiDatosExtra); //asegura la ubicacion del metodo
	    // return IOUtils.toByteArray(in);
	    
	    HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=SAII.DATOS.EXTRA.SAII.xlsx");
	    
	     return ResponseEntity
	                  .ok()
	                  .headers(headers)
	                  .body(new InputStreamResource(in));
	    }
	
	@RequestMapping("/bsc/saii/descargardatos/{d}/{m}/{y}")
    public ResponseEntity<InputStreamResource> downloadData(@PathVariable String d,@PathVariable String m, @PathVariable String y) throws IOException {
		String fecha = d + "/" + m + "/" + y;
        List<SaiiDatosExtra> ListaSaiiDatosExtra = null;
		ListaSaiiDatosExtra = (List<SaiiDatosExtra>) saiiDatosExtraDao.getAllbyFecha(fecha);//llamar a dao
    
    ByteArrayInputStream in = aca.saii.spring.Exceldownloader.SaiiDatosExtrasToExcel(ListaSaiiDatosExtra); //asegura la ubicacion del metodo
    // return IOUtils.toByteArray(in);
    
    HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=SAII.DATOS.EXTRA.SAII.xlsx");
    
     return ResponseEntity
                  .ok()
                  .headers(headers)
                  .body(new InputStreamResource(in));
    }
	
	@RequestMapping("/bsc/saii/avisar")
	public String avisar(){
		MailSender M = new MailSender();
		try {
			M.sendEmails();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mensaje = "1"; 
		return "redirect:/bsc/saii/datos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/bsc/saii/alumnos")
	public String bscSaiiAlumnos(HttpServletRequest request, Model modelo){
		
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String grupoId	 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String fecha	 		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String planUm	 		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		SaiiPeriodo saiiPeriodo 		= saiiPeriodoDao.mapeaRegId(periodoId);
		String periodoNombre 			= saiiPeriodo.getPeriodoNombre();
		AlumAcademico alumAcademico 	= new AlumAcademico();
		String nombreModalidad 			= catModalidadDao.getNombreModalidad(alumAcademico.getModalidadId());
		
		List<SaiiAlumno> lisAlumnosPlanGrupo 	= saiiAlumnoDao.lisAlumnosPlanGrupo(fecha, grupoId);
		HashMap<String, CatEstado> mapEstados	= catEstadoDao.getMapAll();
		HashMap<String, CatPais> mapPaises 		= catPaisDao.getMapAll();
		
		modelo.addAttribute("periodoId",periodoId);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("grupoId",grupoId);
		modelo.addAttribute("planUm",planUm);
		modelo.addAttribute("periodoNombre",periodoNombre);
		modelo.addAttribute("nombreModalidad",nombreModalidad);
		modelo.addAttribute("lisAlumnosPlanGrupo",lisAlumnosPlanGrupo);
		modelo.addAttribute("mapEstados",mapEstados);
		modelo.addAttribute("mapPaises",mapPaises);
		
		return "bsc/saii/alumnos";
	}
	
	@RequestMapping("/bsc/saii/nuevoAlumno")
	public String bscSaiiNuevoAlumno(HttpServletRequest request, Model modelo){
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String grupoId	 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String folio	 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String planUm	 		= request.getParameter("PlanUm")==null?"0":request.getParameter("PlanUm");
		String mensaje	 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String fecha	 		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
		String matricula 		= "0";
		boolean tienePlan		= true;
		String genero	 		= "";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
        }

        SaiiAlumno alumno 			= new SaiiAlumno();
        AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);
        
        MapaPlan mapaPlan = mapaPlanDao.mapeaRegId(planUm);
        
        HashMap<String,CatPais> mapaCatPais = catPaisDao.getMapAll();
        String estado = catEstadoDao.getEstado(alumPersonal.getPaisId(), alumPersonal.getEstadoId());
        
		if(saiiAlumnoDao.existeReg(folio)){
			alumno = saiiAlumnoDao.mapeaRegId(folio);
			estado = catEstadoDao.getEstado(alumno.getPaisId(), alumno.getEstadoId());
		}else {
			
			if(alumPersonal.getSexo().equals("F")) {
				genero = "M";
			}else {
				genero = "H";
			}
			
			alumno.setFolio(saiiAlumnoDao.maximoReg());
			alumno.setCodigoPersonal(matricula);
			alumno.setNombre(alumPersonal.getNombre());
			alumno.setPaterno(alumPersonal.getApellidoPaterno());
			alumno.setMaterno(alumPersonal.getApellidoMaterno());
			alumno.setPaisId(alumPersonal.getPaisId());
			alumno.setEstadoId(alumPersonal.getEstadoId());
			alumno.setGenero(genero);
			alumno.setCurp(alumPersonal.getCurp());
			alumno.setPlanUm(planUm);
			alumno.setPlanSep(mapaPlan.getPlanSE());
		}
		
		if(!alumPlanDao.existeReg(alumno.getCodigoPersonal(), planUm)){
			tienePlan = false;
		}
		
		modelo.addAttribute("periodoId",periodoId);
		modelo.addAttribute("grupoId",grupoId);
		modelo.addAttribute("alumno",alumno);
		modelo.addAttribute("mensaje",mensaje);
		modelo.addAttribute("estado",estado);
		modelo.addAttribute("fecha",fecha);
		modelo.addAttribute("planUm",planUm);
		modelo.addAttribute("tienePlan",tienePlan);
		modelo.addAttribute("mapaCatPais",mapaCatPais);
		modelo.addAttribute("alumPersonal",alumPersonal);
		
		return "bsc/saii/nuevoAlumno";
	}

	@RequestMapping("/bsc/saii/grabarAlumno")
	public String bscSaiiGrabarAlumno(HttpServletRequest request){
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String grupoId	 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String folio	 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String matricula 		= "0";
		String mensaje	 		= "0";
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	matricula 	   		= (String) sesion.getAttribute("codigoAlumno");        	
        }
        
        AlumPersonal alumPersonal 	= alumPersonalDao.mapeaRegId(matricula);

        String planUm	 		= request.getParameter("PlanUm")==null?"0":request.getParameter("PlanUm");
        String planSep	 		= request.getParameter("PlanSep")==null?"0":request.getParameter("PlanSep");
        String ciclo	 		= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");
        String fecha	 		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");
        String grado	 		= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		
		SaiiAlumno alumno 			= new SaiiAlumno();
		 
		alumno.setFolio(folio);
		alumno.setCodigoPersonal(matricula);
		alumno.setNombre(alumPersonal.getNombre());
		alumno.setPaterno(alumPersonal.getApellidoPaterno());
		alumno.setMaterno(alumPersonal.getApellidoMaterno());
		alumno.setPaisId(alumPersonal.getPaisId());
		alumno.setEstadoId(alumPersonal.getEstadoId());
		alumno.setGenero(alumPersonal.getSexo());
		alumno.setCurp(alumPersonal.getCurp());
		alumno.setPlanUm(planUm);
		alumno.setPlantel("PLANTEL 1");
		alumno.setPlanSep(planSep);
		alumno.setCiclo(ciclo);
		alumno.setFecha(fecha);
		alumno.setGrado(grado);
		alumno.setPrepaLugar("0");
		alumno.setUsado("N");
		alumno.setEdad(String.valueOf(aca.util.Fecha.edad(alumPersonal.getFNacimiento(), fecha)));
		
		if(saiiAlumnoDao.existeReg(folio)){
			saiiAlumnoDao.updateReg(alumno);
			mensaje = "1";
		}else {
			saiiAlumnoDao.insertReg(alumno);
			mensaje = "1";
		}
		
		return "redirect:/bsc/saii/nuevoAlumno?PeriodoId="+periodoId+"&GrupoId="+grupoId+"&Folio="+folio+"&PlanUm="+planUm+"&Mensaje="+mensaje+"&Fecha="+fecha;
	}

	@RequestMapping("/bsc/saii/borrarAlumno")
	public String bscSaiiBorrarAlumno(HttpServletRequest request){
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String grupoId	 		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		String folio	 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String fecha	 		= request.getParameter("Fecha")==null?"01/01/2020":request.getParameter("Fecha");
		
		if(saiiAlumnoDao.existeReg(folio)){
			saiiAlumnoDao.deleteReg(folio);
		}
		
		return "redirect:/bsc/saii/alumnos?PeriodoId="+periodoId+"&GrupoId="+grupoId+"&Fecha="+fecha;
	}
	
	@RequestMapping("/bsc/saii/estadistica")
	public String bscSaiiEstadistica(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscSaii|Estadistica:");
		return "bsc/saii/estadistica";
	}	

	@RequestMapping("/bsc/saii/plan_rvoe")
	public String bscSaiiPlanRvoe(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBscSaii|PlanRvoe:");
		return "bsc/saii/plan_rvoe";
	}	
	
}