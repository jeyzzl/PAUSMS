package aca.web.alerta;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alerta.spring.AlertaAntroDao;
import aca.alerta.spring.AlertaDatosDao;
import aca.alerta.spring.AlertaDocAlum;
import aca.alerta.spring.AlertaDocAlumDao;
import aca.alerta.spring.AlertaHistorialDao;
import aca.alerta.spring.AlertaPeriodo;
import aca.alerta.spring.AlertaPeriodoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumCovid;
import aca.alumno.spring.AlumCovidDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.emp.spring.EmpleadoDao;
import aca.internado.spring.IntAcceso;
import aca.internado.spring.IntAccesoDao;
import aca.log.spring.LogProcesoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResAlumno;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContAlertaIngreso {
	
	@Autowired
	private AlumCovidDao alumCovidDao;
	
	@Autowired
	private AlertaPeriodoDao alertaPeriodoDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private EmpleadoDao empleadoDao;
	
	@Autowired
	private AlertaDocAlumDao alertaDocAlumDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private IntAccesoDao intAccesoDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	
	@RequestMapping("/alerta/ingreso/datos")
	public String alertaIngresoDatos(HttpServletRequest request, Model modelo){
		
		String codigoAlumno			= "0";
		String nombreAlumno			= "-";		
		String codigoPersonal		= "0";
		String carreraId			= "0";
		String planId				= "0";
		String carreraNombre		= "-";
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje		 		= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String filtro		 		= request.getParameter("Filtro")==null?"T":request.getParameter("Filtro");
		Acceso acceso 				= new Acceso();
		AlumAcademico alumAcademico = new AlumAcademico();
		IntAcceso intAcceso 		= new IntAcceso(); 
		 
		boolean tieneAcceso		= false;
		boolean esSuPreceptor	= false;
		boolean existeAlumno	= false;

		String dormitorio 	= "0";
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null){			
			codigoAlumno 		= (String) sesion.getAttribute("codigoAlumno");
			if (alumPersonalDao.existeReg(codigoAlumno)) {
				nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
				existeAlumno 	= true;
			}
			if (alumAcademicoDao.existeReg(codigoAlumno)) {
				alumAcademico 	= alumAcademicoDao.mapeaRegId(codigoAlumno);
			}
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");
			planId 				= alumPlanDao.getPlanActual(codigoAlumno);
			carreraId 			= mapaPlanDao.getCarreraId(planId);
			carreraNombre 		= mapaPlanDao.getCarreraSe(planId);
			
			dormitorio = intAccesoDao.getDormitorioId(codigoPersonal);
			boolean esPreceptor = false;
			if(intAccesoDao.existeRegId(codigoPersonal, "P")) {
				esPreceptor = true;
				intAcceso = intAccesoDao.mapeaRegId(codigoPersonal, "P");
			}

			if(alumAcademicoDao.existeReg(codigoAlumno)) {
				AlumAcademico alumno = alumAcademicoDao.mapeaRegId(codigoAlumno);
				if (esPreceptor &&  alumno.getDormitorio().equals(dormitorio)) {
					esSuPreceptor = true;
				}
			}
			
			acceso	 			= accesoDao.mapeaRegId(codigoPersonal);
			if (acceso.getAdministrador().equals("S") || acceso.getAccesos().contains(carreraId) || esSuPreceptor){
				tieneAcceso = true;
			}			
		}	
		
		List<AlertaPeriodo> lisPeriodos 		= alertaPeriodoDao.getAllActivos(" ORDER BY 1 DESC");
		if (periodoId.equals("0") && lisPeriodos.size() >= 1){
			periodoId = lisPeriodos.get(0).getPeriodoId();
		}
		
		List<AlumCovid> lisDatos 				= alumCovidDao.lisPorPeriodo(periodoId, " ORDER BY PERIODO_ID DESC");
		
		if(filtro.equals("P")) {
			lisDatos = alumCovidDao.lisPorPeriodo(periodoId, " AND POSITIVO_COVID = 'S' ORDER BY PERIODO_ID DESC");
		}else if(filtro.equals("S")) {
			lisDatos = alumCovidDao.lisPorPeriodo(periodoId, " AND SOSPECHOSO = 'S' ORDER BY PERIODO_ID DESC");
		}else if(filtro.equals("A")) {
			lisDatos = alumCovidDao.lisPorPeriodo(periodoId, " AND AISLAMIENTO = 'S' ORDER BY PERIODO_ID DESC");
		}
		
		HashMap<String, String> mapEdadCovid			= alumPersonalDao.mapEdadCovid();
		HashMap<String,AlumAcademico> mapAcademicoCovid	= alumAcademicoDao.mapAcademicoCovid();
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosCovid(periodoId);
		HashMap<String, String> mapaPlanAlumno			= alumPlanDao.mapaPlanesCovid(periodoId);
		HashMap<String, MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaDocumentos 			= alertaDocAlumDao.mapaPorPeriodo(periodoId);
		HashMap<String, String> mapaDormitorio			= alumAcademicoDao.getMapDormi(" WHERE DORMITORIO = "+dormitorio);
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("filtro", filtro);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("carreraNombre", carreraNombre);		
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("esSuPreceptor", esSuPreceptor);
		modelo.addAttribute("existeAlumno", existeAlumno);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("intAcceso", intAcceso);
		modelo.addAttribute("alumAcademico", alumAcademico);
		
		modelo.addAttribute("lisDatos", lisDatos);
		modelo.addAttribute("lisPeriodos", lisPeriodos);		
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);		
		modelo.addAttribute("mapaPlanAlumno", mapaPlanAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaDormitorio", mapaDormitorio);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapAcademicoCovid", mapAcademicoCovid);
		modelo.addAttribute("mapEdadCovid", mapEdadCovid);
		
		return "alerta/ingreso/datos";
	}
	
	@RequestMapping("/alerta/ingreso/nuevos")
	public String alertaRegistroNuevos(HttpServletRequest request, Model modelo){
				
		String codigoPersonal		= "0";		
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String dormitorio 			= "0";
		String nombreMaestro 		= "";
		
		Acceso acceso 				= new Acceso();
		AlumAcademico alumAcademico = new AlumAcademico();	
		IntAcceso intAcceso 		= new IntAcceso(); 
		
		boolean tieneAcceso		= false;
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null){			
			codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");					
			acceso	 			= accesoDao.mapeaRegId(codigoPersonal);
			dormitorio 			= intAccesoDao.getDormitorioId(codigoPersonal);
			
			nombreMaestro		= empleadoDao.getNombreEmpleado(codigoPersonal, "NOMBRE");
			
			if(intAccesoDao.existeRegId(codigoPersonal, "P")) {
				intAcceso = intAccesoDao.mapeaRegId(codigoPersonal, "P");
			}
			
			if (acceso.getAdministrador().equals("S") || !acceso.getAccesos().equals("X") || !acceso.getAccesos().equals("-")){
				tieneAcceso = true;
			}
		}		
		
		if(tieneAcceso) {
			dormitorio = "1,2,3,4";
		}
		
		List<AlumPersonal> lisNuevos 			= alumPersonalDao.lisNuevosDocumentos(periodoId, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");		
		HashMap<String, String> mapaPlanAlumno	= alumPlanDao.mapaPlanesConDocumentos(periodoId);
		HashMap<String, MapaPlan> mapaPlanes	= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,String> mapaDocumentos 	= alertaDocAlumDao.mapaPorPeriodo(periodoId);
		HashMap<String, String> mapDormi 		= alumAcademicoDao.getMapDormi(" WHERE DORMITORIO IN ("+dormitorio+")");
		
		
		modelo.addAttribute("periodoId", periodoId);		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("dormitorio", dormitorio);
		modelo.addAttribute("lisNuevos", lisNuevos);				
		modelo.addAttribute("mapaPlanAlumno", mapaPlanAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("intAcceso", intAcceso);
		modelo.addAttribute("mapDormi", mapDormi);
		modelo.addAttribute("nombreMaestro", nombreMaestro);
		modelo.addAttribute("alumAcademico", alumAcademico);
		
		return "alerta/ingreso/nuevos";
	}
	
	@RequestMapping("/alerta/ingreso/editar")
	public String alertaRegistroAccionCovid(HttpServletRequest request, Model modelo){
		
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
		String nombreAlumno		= "-";
		boolean existe 			= false;
		HttpSession sesion		= ((HttpServletRequest) request).getSession();
		AlumCovid alumCovid 	= new AlumCovid();
		
		if (codigoAlumno.equals("0") && sesion != null) {
			codigoAlumno 	= (String) sesion.getAttribute("codigoAlumno");
		}
		if(alumCovidDao.existeReg(codigoAlumno, periodoId)){
			existe 			= true;
			alumCovid 		= alumCovidDao.mapeaRegId(codigoAlumno, periodoId);			
		}	
		nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("existe", existe);		
		modelo.addAttribute("periodoId", periodoId);		
		modelo.addAttribute("alumCovid", alumCovid);
		modelo.addAttribute("mensaje", mensaje);
		
		return "alerta/ingreso/editar";		
	}
	
	@RequestMapping("/alerta/ingreso/grabar")
	public String alertaRegistroGrabar(HttpServletRequest request, Model model){				
		
		String usuario		= "-";
		HttpSession sesion	= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			usuario = (String) sesion.getAttribute("codigoPersonal");
		}
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String tipo				= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
		String llegada			= request.getParameter("Fechallegada")==null?"0":request.getParameter("Fechallegada");
		String vacuna	 		= request.getParameter("Vacuna")==null?"N":request.getParameter("Vacuna");
		String fechaVacuna	 	= request.getParameter("FechaVacuna")==null?"0":request.getParameter("FechaVacuna");
		String positivo			= request.getParameter("Positivo")==null?"N":request.getParameter("Positivo");
		String fechaPositivo	= request.getParameter("FechaPositivo")==null?null:request.getParameter("FechaPositivo");
		String sospechoso		= request.getParameter("Sospechoso")==null?"N":request.getParameter("Sospechoso");
		String fechaSospechoso	= request.getParameter("FechaSospechoso")==null?"0":request.getParameter("FechaSospechoso");
		String aislamiento 		= request.getParameter("Aislamiento")==null?"0":request.getParameter("Aislamiento");
		String finAislamiento	= request.getParameter("FinAislamiento")==null?null:request.getParameter("FinAislamiento");
		String validado			= request.getParameter("Validado")==null?"0":request.getParameter("Validado");	
		String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
		String fechaAlta		= aca.util.Fecha.getHoy();
		String mensaje 			= "0";
		
		AlumCovid alumCovid = new AlumCovid();
		
		alumCovid.setPeriodoId(periodoId);
		alumCovid.setCodigoPersonal(codigoAlumno);
		alumCovid.setTipo(tipo);
		alumCovid.setFechaLlegada(llegada);
		alumCovid.setVacuna(vacuna);
		alumCovid.setFechaVacuna(fechaVacuna);
		alumCovid.setPositivoCovid(positivo);
		alumCovid.setFechaPositivo(fechaPositivo);
		alumCovid.setSospechoso(sospechoso);
		alumCovid.setFechaSospechoso(fechaSospechoso);
		alumCovid.setAislamiento(aislamiento);
		alumCovid.setFinAislamiento(finAislamiento);
		alumCovid.setUsuarioAlta(usuario);
		alumCovid.setFechaAlta(fechaAlta);
		alumCovid.setComentario(comentario);
		alumCovid.setValidado(validado);

		if (alumCovidDao.existeReg(codigoAlumno,periodoId)){
			if (alumCovidDao.updateReg(alumCovid)){				
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}else {
			if (alumCovidDao.insertReg(alumCovid)){
				mensaje = "1";
			}else {
				mensaje = "2";
			}
		}		
		return "redirect:/alerta/ingreso/editar?CodigoAlumno="+codigoAlumno+"&PeriodoId="+periodoId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/alerta/ingreso/borrar")
	public String alertaRegistroBorrar(HttpServletRequest request, Model model){
		
		String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		
		String mensaje 			= "0";	
		if (alumCovidDao.existeReg(codigoAlumno,periodoId)){
			if (alumCovidDao.deleteReg(codigoAlumno, periodoId)){				
				mensaje = "¡Registro borrado!";
			}else {
				mensaje = "¡Error al intentar borrar!";
			}
		}
		
		return "redirect:/alerta/ingreso/datos?Mensaje="+mensaje;
	}
	
	@RequestMapping("/alerta/ingreso/descargarRetorno")
	public void alertaIngresoDescargarRetorno(HttpServletResponse response, HttpServletRequest request){
		
		String codigoAlumno 	= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		String documentoId	 	= request.getParameter("DocumentoId") == null ? "0" : request.getParameter("DocumentoId");
		String periodoId 		= alertaPeriodoDao.getPeriodoActivo();		
        
		AlertaDocAlum documento = new AlertaDocAlum();		
		if(alertaDocAlumDao.existeReg(codigoAlumno, periodoId, documentoId)) {
			documento = alertaDocAlumDao.mapeaRegId(codigoAlumno, periodoId, documentoId);
		}		
		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename=\""+documento.getNombre()+ "\"");
			response.getOutputStream().write(documento.getArchivo());
			response.flushBuffer();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}
	
}
