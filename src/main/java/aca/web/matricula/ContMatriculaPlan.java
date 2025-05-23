package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaFinanciero;
import aca.carga.spring.CargaFinancieroDao;
import aca.catalogo.spring.CatPeriodo;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPlan;

@Controller
public class ContMatriculaPlan {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;	
	
	@Autowired
	aca.catalogo.spring.CatPeriodoDao catPeriodoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;

	@Autowired
	AlumPlanDao alumPlanDao;

	@Autowired
	AlumEstadoDao alumEstadoDao;

	@Autowired
	CargaAlumnoDao cargaAlumnoDao;

	@Autowired
	CargaFinancieroDao cargaFinancieroDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	ResDatosDao resDatosDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	ParametrosDao parametrosDao;
	
	@RequestMapping("/matricula/plan/carga")
	public String matriculaCargaAlumno(HttpServletRequest request, Model modelo){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String periodoSesion 	= "0";
		String cargaSesion 		= "0";
		String bloqueSesion		= "0";
		String nombreInstitucion = "-";
		String codigoPersonal	= "0";		
		String codigoAlumno		= "0";		
		String nombreAlumno 	= "-";
		boolean esAlumno		= false;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){        	
        	codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
        	nombreAlumno 	= usuariosDao.getNombreUsuario(codigoAlumno, "NOMBRE");
        	if (codigoAlumno.subSequence(0, 1).equals("0") || codigoAlumno.subSequence(0, 1).equals("1") || codigoAlumno.subSequence(0, 1).equals("2")){
        		esAlumno 	= true;
        	}
        	periodoSesion 	= (String)sesion.getAttribute("periodo");
        	cargaSesion 	= (String)sesion.getAttribute("cargaId");
        	bloqueSesion 	= (String)sesion.getAttribute("bloqueId");
        } 
        
        nombreInstitucion = parametrosDao.getInstitucion("1");
        
		List<CatPeriodo> lisPeriodos = catPeriodoDao.getListAll(" ORDER BY PERIODO_ID DESC");		
		if(periodoId.equals("0") && lisPeriodos.size() > 0) {
			if(periodoSesion != null) {
				periodoId = periodoSesion;
			}else {
				periodoId = lisPeriodos.get(0).getPeriodoId();
			}
		}else if(!periodoId.equals("0")){
			sesion.setAttribute("periodo", periodoId);
		}		
		
		List<Carga> lisCargas 	= cargaDao.getListPeriodo(periodoId," AND ESTADO = '1' ORDER BY PERIODO,CARGA_ID");
		if (cargaId.equals("0")) cargaId = cargaSesion;
		boolean existeCarga 	= false;
		for (Carga carga : lisCargas) {
			if (carga.getCargaId().equals(cargaId)) existeCarga = true;
		}
		if (existeCarga==false && lisCargas.size() > 0) {
			cargaId = lisCargas.get(0).getCargaId();
			sesion.setAttribute("cargaId", cargaId);
		}else if(existeCarga) {
			sesion.setAttribute("cargaId", cargaId);
		}	
		
		List<CargaBloque> lisBloques = cargaBloqueDao.getLista(cargaId, "ORDER BY BLOQUE_ID DESC");
		
		if (bloqueId.equals("0") && lisBloques.size() > 0) {
			if(bloqueSesion != null) {
				bloqueId = bloqueSesion;
			}else {
				bloqueId = lisBloques.get(0).getCargaId();
			}
		}else if(!bloqueId.equals("0")) {
			sesion.setAttribute("bloqueId", bloqueId);
		}
		
		if (planId.equals("0")) planId = alumPlanDao.getPlanActual(codigoAlumno);
		
		Acceso acceso 				= accesoDao.mapeaRegId(codigoPersonal);		
		AlumAcademico alumAcademico = new AlumAcademico();		
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		}
		
		List<AlumPlan> lisPlanes 					= alumPlanDao.getLista(codigoAlumno, "ORDER BY PLAN_ID");	
		List<CargaAlumno> lisCargasAlumno			= cargaAlumnoDao.getCargasAlumno(codigoAlumno);
		HashMap<String, MapaPlan> mapaPlan			= mapaPlanDao.mapPlanes("'V','A','I'");
		HashMap<String, CargaBloque> mapaBloques	= cargaBloqueDao.mapaBloques();
		HashMap<String, String> mapaMaterias		= alumnoCursoDao.mapaMateriasPorCargayBloque(codigoAlumno);
		
		HashMap<String,String> mapaInscripciones	= alumEstadoDao.mapaInscripcionesAlumno(codigoAlumno);
		
		ResDatos resDatos = new ResDatos();
		
		if(resDatosDao.existeReg(codigoAlumno)) {
			resDatos = resDatosDao.mapeaRegId(codigoAlumno);
		}
		
		boolean reingreso = false;
		if(alumEstadoDao.tienePlan(codigoAlumno, planId, "I")) {
			reingreso = true;
		}
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("nombreInstitucion", nombreInstitucion);
		modelo.addAttribute("lisPeriodos", lisPeriodos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisBloques", lisBloques);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisCargasAlumno", lisCargasAlumno);
		modelo.addAttribute("mapaPlan", mapaPlan);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("esAlumno", esAlumno);
		modelo.addAttribute("PeriodoId", periodoId);
		modelo.addAttribute("CargaId", cargaId);
		modelo.addAttribute("BloqueId", bloqueId);
		modelo.addAttribute("PlanId", planId);
		modelo.addAttribute("mapaBloques", mapaBloques);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapaInscripciones", mapaInscripciones);
		modelo.addAttribute("resDatos", resDatos);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("reingreso", reingreso);
		
		return "matricula/plan/carga";
	}	
	
	@RequestMapping("/matricula/plan/guardar")
	public String matriculaPlanGuardar(HttpServletRequest request){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String grupo			= request.getParameter("Grupo")==null?"XX":request.getParameter("Grupo");
		String modo				= request.getParameter("ModoId")==null?"XX":request.getParameter("ModoId");
		String ingreso			= request.getParameter("Ingreso")==null?"X":request.getParameter("Ingreso");
		String codigoAlumno		= "0";
		String codigoPersonal 	= "0";
		String mensaje 			= "0";
		
		CargaAlumno cargaAlumno = new CargaAlumno();		
		AlumEstado alumEstado = new AlumEstado();
		CargaFinanciero cargaFin = new CargaFinanciero();
				
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
		if (sesion != null){
        	codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal	= (String) sesion.getAttribute("codigoPersonal");
        	sesion.setAttribute("periodo", periodoId);
        	sesion.setAttribute("cargaId", cargaId);
        	sesion.setAttribute("bloqueId", bloqueId);        	
        } 
		
		if(cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			mensaje = "1";
		}else{
			cargaAlumno.setCodigoPersonal(codigoAlumno);
			cargaAlumno.setCargaId(cargaId);
			cargaAlumno.setBloqueId(bloqueId);
			cargaAlumno.setPlanId(planId);
			cargaAlumno.setFecha(aca.util.Fecha.getHoy());
			cargaAlumno.setEstado("1");
			cargaAlumno.setGrupo(grupo);
			cargaAlumno.setModo(modo);
			cargaAlumno.setConfirmar("N");
			cargaAlumno.setIngreso(ingreso);
			
			if(cargaAlumnoDao.insertReg(cargaAlumno)) {
				mensaje = "2";
			}
		}
		
		if(alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			mensaje = "1";
		}else{
			alumEstado.setCargaId(cargaId);
			alumEstado.setBloqueId(bloqueId);
			alumEstado.setEstado("M");
			alumEstado.setFecha(aca.util.Fecha.getHoy());
			alumEstado.setPlanId(planId);
			alumEstado.setCodigoPersonal(codigoAlumno);
			alumEstado.setModalidadId("0");
			alumEstado.setTipoalumnoId("0");
			alumEstado.setFacultadId("0");
			alumEstado.setCarreraId("0");
			alumEstado.setResidenciaId("0");
			alumEstado.setDormitorio("0");
			alumEstado.setCiclo("1");
			alumEstado.setGrado("0");
			alumEstado.setClasFin("0");
			
			
			if(alumEstadoDao.insertReg(alumEstado)) {
				mensaje = "2";
			}
		}
		
		return "redirect:/matricula/plan/carga?PeriodoId="+periodoId+"&CargaId="+cargaId+"&PlanId="+planId+"&Mensaje="+mensaje;
	}

	@RequestMapping("/matricula/plan/borrarCarga")
	public String matriculaPlanBorrarCarga(HttpServletRequest request){
		
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String planId		= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();	
		String codigoAlumno		= "0";		
		
		if (sesion != null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		} 
		
		if(cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			cargaAlumnoDao.deleteReg(codigoAlumno, cargaId, bloqueId, planId);
		}
		
		if(alumEstadoDao.existeReg(codigoAlumno, cargaId, bloqueId)) {
			alumEstadoDao.deleteReg(codigoAlumno, cargaId, bloqueId);
		}
		
		return "redirect:/matricula/plan/carga";
	}
	
	@RequestMapping("/matricula/plan/cambiarModo")
	public String matriculaPlanCambiarModo(HttpServletRequest request){
		
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String bloqueId		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
		String modo			= request.getParameter("Modo")==null?"0":request.getParameter("Modo");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();	
		String codigoAlumno		= "0";		
		
		if (sesion != null){
			codigoAlumno	= (String) sesion.getAttribute("codigoAlumno");
		} 
		
		if(cargaAlumnoDao.existeReg(codigoAlumno, cargaId, bloqueId)){
			cargaAlumnoDao.updateModo(codigoAlumno, cargaId, bloqueId, modo);
		}
		
		return "redirect:/matricula/plan/carga";
	}
	
}