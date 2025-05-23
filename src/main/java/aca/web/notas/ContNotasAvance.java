package aca.web.notas;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchivoDao;
import aca.catalogo.spring.CatAvance;
import aca.catalogo.spring.CatAvanceDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.financiero.spring.FinSaldo;
import aca.graduacion.spring.AlumEgreso;
import aca.graduacion.spring.AlumEgresoDao;
import aca.graduacion.spring.AlumEventoDao;
import aca.parametros.spring.ParametrosDao;
import aca.plan.spring.MapaCreditoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlanDao;
import aca.ssoc.spring.SsocDocAlum;
import aca.ssoc.spring.SsocDocAlumDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContNotasAvance {	

	@Autowired
	private CatAvanceDao catAvanceDao;

	@Autowired
	private AlumPlanDao alumPlanDao;

	@Autowired
	private AlumPersonalDao alumPersonalDao;

	@Autowired
	private AlumAcademicoDao alumAcademicoDao;

	@Autowired
	private MapaPlanDao mapaPlanDao;

	@Autowired
	private ArchivoDao archivoDao;

	@Autowired
	private MapaCreditoDao mapaCreditoDao;

	@Autowired
	private AlumnoCursoDao alumnoCursoDao;

	@Autowired
	private MapaCursoDao mapaCursoDao;

	@Autowired
	private ParametrosDao parametrosDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	SsocDocAlumDao ssocDocAlumDao;
	
	@Autowired
	private AlumEgresoDao alumEgresoDao;
	
	@Autowired
	private AlumEventoDao alumEventoDao;
	
	@Autowired
	private ArchDocAlumDao archDocAlumDao;
	
	
	@RequestMapping("/notas/avance/avance")
	public String notasAvanceAvance(HttpServletRequest request, Model modelo){	
		
		String matricula 			= "0";
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");	
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			matricula 				= (String) sesion.getAttribute("codigoAlumno");
			if (planId.equals("0")) planId = alumPlanDao.getPlanActual(matricula);
		}
		String nivelId				= mapaPlanDao.getNivelId(planId);
		boolean tieneCarta			= archDocAlumDao.existeReg(matricula, "14");
			
		List<CatAvance> lisAvance 		= catAvanceDao.getListAll("ORDER BY 2");
		List<String> lisPlanes 			= alumPlanDao.getPlanesAlumno(matricula);	
		
		FinSaldo finSaldo 	= new FinSaldo();
		try {		
			RestTemplate restTemplate = new RestTemplate();
			finSaldo = restTemplate.getForObject("http://172.16.251.110:8080/infor/reportes/api/fe/saldo/"+matricula, FinSaldo.class);
		}catch(Exception ex) {
			System.out.println("Error en saldo:"+matricula);
		}
		
		String eventoDatos 		= "No estás registrado en ningún evento.";
		boolean tieneEvento 	= false;
		AlumEgreso alumEgreso 	= new AlumEgreso();
		if (alumEgresoDao.existeRegAlum(matricula, planId)) {
			alumEgreso 	= alumEgresoDao.mapeaPorCodigoyPlan(matricula, planId);
			tieneEvento = true;
			if (alumEgreso.getPermiso().equals("S")) {
				eventoDatos = "Permiso para participar en el evento: "+alumEventoDao.mapeaRegId(alumEgreso.getEventoId()).getEventoNombre();
			}else {
				eventoDatos = "Registrado para participar en el evento: "+alumEventoDao.mapeaRegId(alumEgreso.getEventoId()).getEventoNombre();
			}			 
		}	
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("nivelId", nivelId);
		modelo.addAttribute("eventoDatos", eventoDatos);
		modelo.addAttribute("tieneCarta", tieneCarta);
		modelo.addAttribute("tieneEvento", tieneEvento);
		modelo.addAttribute("crdPlan", mapaCreditoDao.creditosPlan(planId));
		modelo.addAttribute("crdAlumno",alumnoCursoDao.creditosAprobados(matricula, planId));
		modelo.addAttribute("compPlan",mapaCursoDao.cuentaMaterias(planId, "3"));
		modelo.addAttribute("compAlumno",alumnoCursoDao.materiasAlumno(matricula, planId, "3"));
		modelo.addAttribute("avance",alumPlanDao.getAvanceId(matricula, planId));
		modelo.addAttribute("registro",catAvanceDao.getNombreAvance(alumPlanDao.getAvanceId(matricula, planId)));
		modelo.addAttribute("permiso",alumPlanDao.getPermiso(matricula, planId));
		modelo.addAttribute("evento",alumPlanDao.getEvento(matricula, planId));
		modelo.addAttribute("parametros",parametrosDao.mapeaRegId("1"));
		modelo.addAttribute("carrera",catCarreraDao.getNombreCarrera(mapaPlanDao.getCarreraId(planId)));
		modelo.addAttribute("nombreAlumno", alumPersonalDao.getNombreAlumno(matricula, "NOMBRE"));
		modelo.addAttribute("modalidad", alumAcademicoDao.getModalidad(matricula));
		modelo.addAttribute("residencia", alumAcademicoDao.getResidencia(matricula));
		modelo.addAttribute("documentos", archivoDao.autorizaAlumno(matricula, alumPlanDao.getPlanActual(matricula)));
		modelo.addAttribute("finSaldo", finSaldo);
		
		modelo.addAttribute("lisAvance", lisAvance);		
		modelo.addAttribute("lisPlanes", lisPlanes);	
		modelo.addAttribute("mapaPlan", mapaPlanDao.mapPlanes("'A','V','I'"));
		
		return "notas/avance/avance";
	}
	
	@RequestMapping("/notas/avance/grabar")
	public String notasAvanceGrabar(HttpServletRequest request, Model modelo){	
		
		String matricula 			= "0";
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String permiso				= request.getParameter("Permiso")==null?"0":request.getParameter("Permiso");
		String mensaje				= "-";
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			matricula 				= (String) sesion.getAttribute("codigoAlumno");
			if (alumPlanDao.updatePermiso(matricula, planId, permiso)){
				mensaje = "¡Grabado!";
			}else {
				mensaje = "¡Error al grabar!";
			}			
		}		
		return "redirect:/notas/avance/avance?Mensaje="+mensaje;
	}	

	@RequestMapping("/notas/avance/grabarEvento")
	public String notasAvanceGrabarEvento(HttpServletRequest request, Model modelo){	
		
		String matricula 			= "0";
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String evento				= request.getParameter("Evento")==null?"0":request.getParameter("Evento");
		String mensaje				= "-";
		
		HttpSession sesion 			= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			matricula 				= (String) sesion.getAttribute("codigoAlumno");
			if (alumPlanDao.updateEvento(matricula, planId, evento)){
				mensaje = "¡Grabado!";
			}else {
				mensaje = "¡Error al grabar!";
			}			
		}		
		return "redirect:/notas/avance/avance?Mensaje="+mensaje;
	}	
}
