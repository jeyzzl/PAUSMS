package aca.web.admision;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.pdf.qrcode.Mode;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.admision.spring.AdmEncuesta;
import aca.admision.spring.AdmEncuestaDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.alumno.spring.AlumUbicacionDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEscuela;
import aca.catalogo.spring.CatEscuelaDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatNivel;
import aca.catalogo.spring.CatNivelDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.log.spring.LogProceso;
import aca.log.spring.LogProcesoDao;
import aca.plan.spring.MapaMayorMenor;
import aca.plan.spring.MapaMayorMenorDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.residencia.spring.ResCandadoDao;
import aca.residencia.spring.ResDatos;
import aca.residencia.spring.ResDatosDao;
import aca.util.Fecha;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContAdmisionPlan {
	
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatEscuelaDao catEscuelaDao;
	
	@Autowired
	private MaestrosDao maestrosDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	private MapaPlanDao mapaPlanDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	private AlumUbicacionDao alumUbicacionDao;
	
	@Autowired
	private ResCandadoDao resCandadoDao;
	
	@Autowired
	private CatNivelDao catNivelDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private LogProcesoDao logProcesoDao;	
	
	@Autowired
	private ResDatosDao resDatosDao;
	
	@Autowired
	AdmEncuestaDao admEncuestaDao; 

	@Autowired
	MapaMayorMenorDao mapaMayorMenorDao; 
	
	
	@RequestMapping("/admision/plan/accion")
	public String admisionPlanAccion(HttpServletRequest request, Model modelo){
		
		String planId 			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String codigoAlumno 	= "-";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno = (String)sesion.getAttribute("codigoAlumno");
		}
		
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String planNombre 		= mapaPlanDao.getNombrePlan(planId); 
		int materias = alumnoCursoDao.getNumMaterias(codigoAlumno, planId, "'M','I','1','2','3','4','5','6'");
		
		List<CatEscuela> lisEscuelas = catEscuelaDao.getListAll("ORDER BY 2");

		ResDatos resDatos = new ResDatos();
		
		if(resDatosDao.existeReg(codigoAlumno)) {
			resDatos = resDatosDao.mapeaRegId(codigoAlumno);
		}

		AlumAcademico alumAcademico = new AlumAcademico();
		
		if(alumAcademicoDao.existeReg(codigoAlumno)) {
			alumAcademico = alumAcademicoDao.mapeaRegId(codigoAlumno);
		}
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("resDatos", resDatos);
		modelo.addAttribute("alumAcademico", alumAcademico);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("planNombre", planNombre);
		modelo.addAttribute("materias", materias);
		modelo.addAttribute("lisEscuelas", lisEscuelas);
		
		return "admision/plan/accion";
	}
	@RequestMapping("/admision/plan/facultad")
	public String admisionPlanFacultad(HttpServletRequest request, Model modelo){
		String codigoAlumno = "-";
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno = (String)sesion.getAttribute("codigoAlumno");
		}
		
		List<CatFacultad> lisFacultades				= catFacultadDao.getListAll(" ORDER BY 1");
		HashMap<String,String> mapaDirectores		= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapaDirectores", mapaDirectores);
		
		LogProceso proceso = new LogProceso();
		
		proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
		proceso.setCodigoPersonal(codigoAlumno);
		proceso.setModulo("ASIGNA PLAN");
		proceso.setFecha(Fecha.getHoy());
		proceso.setEvento("INICIA-INSERTAR");
		
		logProcesoDao.insertReg(proceso);
		
		return "admision/plan/facultad";
	}
	@RequestMapping("/admision/plan/listado")
	public String admisionPlanListado(HttpServletRequest request, Model modelo){
		
		String facultad 				= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultad);
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			if (!facultad.equals("0")){
				sesion.setAttribute("fac",facultad);
			}else{
				facultad = (String)sesion.getAttribute("fac");
			}
		}
		
		List<CatCarrera> lisCarreras				= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");
		List<MapaPlan> lisPlanes					= mapaPlanDao.getListPlanFac(facultad,"ORDER BY PLAN_ID");
		HashMap<String,String> mapaCoordinadores	= maestrosDao.mapaCoordinadores();
		HashMap<String,String> mapaPlanNombre		= mapaPlanDao.mapNombrePlan();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("lisCarreras", lisCarreras);		
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("mapaCoordinadores", mapaCoordinadores);
		modelo.addAttribute("mapaPlanNombre", mapaPlanNombre);
		
		return "admision/plan/listado";
	}
	@RequestMapping("/admision/plan/planes")
	public String admisionPlanPlanes(HttpServletRequest request, Model modelo){
		
		String codigoAlumno 	= "-";
		String codigoPersonal	= "-";
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 		= (String)sesion.getAttribute("codigoAlumno");
			codigoPersonal 		= (String)sesion.getAttribute("codigoPersonal");
		}
		
		
		String nombreAlumno 	= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		String carreraId 		= alumPlanDao.getCarreraId(codigoAlumno);
		boolean esInscrito 		= alumPersonalDao.esInscrito(codigoAlumno);
		boolean datosAcademicos = alumAcademicoDao.existeReg(codigoAlumno);
	    boolean datosPersonales = alumPersonalDao.existeReg(codigoAlumno);
	    boolean datosUbicacion 	= alumUbicacionDao.existeReg(codigoAlumno);
	    boolean resCandado		= resCandadoDao.existeReg(codigoAlumno);
		Acceso acceso 			= accesoDao.mapeaRegId(codigoPersonal);
		
		List<AlumPlan> lisPlanes 					= alumPlanDao.getLista(codigoAlumno, " ORDER BY SUBSTR(F_INICIO,7,4),SUBSTR(F_INICIO,4,2),SUBSTR(F_INICIO,1,2)");
		List<String> lisPlanesInscritos				= inscritosDao.lisPlanesInscritos(codigoAlumno);
		TreeMap<String, Inscritos> mapInscritos 	= inscritosDao.getMapAll("");
		HashMap<String,MapaPlan> mapaPlanes			= mapaPlanDao.mapPlanes("'A','V','I'");
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String,CatNivel> mapaNiveles		= catNivelDao.getMapAll("");
		HashMap<String,String> mapaMateriasAlumno	= alumnoCursoDao.mapaMateriasPorAlumno(codigoAlumno);
		HashMap<String,MapaMayorMenor> mapMayores 	= mapaMayorMenorDao.mapMayores(" ORDER BY FOLIO");
		HashMap<String,MapaMayorMenor> mapMenores 	= mapaMayorMenorDao.mapMenores(" ORDER BY FOLIO");
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("esInscrito", esInscrito);
		modelo.addAttribute("datosAcademicos", datosAcademicos);
		modelo.addAttribute("datosPersonales", datosPersonales);
		modelo.addAttribute("datosUbicacion", datosUbicacion);
		modelo.addAttribute("resCandado", resCandado);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisPlanesInscritos", lisPlanesInscritos);
		modelo.addAttribute("mapInscritos", mapInscritos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaNiveles", mapaNiveles);
		modelo.addAttribute("mapaMateriasAlumno", mapaMateriasAlumno);
		modelo.addAttribute("mapMayores", mapMayores);
		modelo.addAttribute("mapMenores", mapMenores);
		
		return "admision/plan/planes";
	}
	
	@RequestMapping("/admision/plan/editarCiclo")
	public String admisionPlanEditarCiclo(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String alumnoNombre			= "-";
		String ciclo				= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");
		String grado				= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");

		}		
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("ciclo", ciclo);
		modelo.addAttribute("grado", grado);
		
		return "admision/plan/editarCiclo";
	}
	
	@RequestMapping("/admision/plan/grabarCiclo")
	public String admisionPlanGrabarCiclo(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String alumnoNombre			= "-";
		String mensaje 				= "-";
		String ciclo				= request.getParameter("Ciclo")==null?"0":request.getParameter("Ciclo");
		String grado				= request.getParameter("Grado")==null?"0":request.getParameter("Grado");
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");

		}		
		
		if(alumPlanDao.existeReg(codigoAlumno, planId)) {
			AlumPlan alumPlan = alumPlanDao.mapeaRegId(codigoAlumno, planId);
			alumPlan.setGrado(grado);
			alumPlan.setCiclo(ciclo);
			
			if(alumPlanDao.updateReg(alumPlan)) {
				mensaje = "Grade and Cycle have been updated succesfully";
			}else {
				mensaje = "There has been an error updating the Grade and Cycle";
			}
		}
		
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}

	@RequestMapping("/admision/plan/editarPrimerMatricula")
	public String admisionPlanEditarFechaInicio(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String alumnoNombre			= "-";
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");

		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}	
		
		AlumPlan plan = new AlumPlan();
		
		if(alumPlanDao.existeReg(codigoAlumno, planId)){
			plan = alumPlanDao.mapeaRegId(codigoAlumno);
		}
		
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("plan", plan);
		
		return "admision/plan/editarPrimerMatricula";
	}

	@RequestMapping("/admision/plan/grabarPrimerMatricula")
	public String admisionPlanGrabarFechaInicio(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje 				= "-";
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		String fInicio				= request.getParameter("PrimerMatricula")==null?"0":request.getParameter("PrimerMatricula");
		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
		}		
		
		if(alumPlanDao.existeReg(codigoAlumno, planId)) {
			AlumPlan alumPlan = alumPlanDao.mapeaRegId(codigoAlumno, planId);
			alumPlan.setPrimerMatricula(fInicio);
			
			if(alumPlanDao.updateReg(alumPlan)) {
				mensaje = "Start date updated succesfully";
			}else {
				mensaje = "There has been an error updating the Start date";
			}
		}
		
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/plan/grabarPlan")
	public String admisionPlanGrabarPlan(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String newPlanId   			= request.getParameter("PlanId");
		String newFInicio   		= request.getParameter("FInicio");				
		String newEscuela			= request.getParameter("Escuela");
		String newEstado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
		String newPrincipal 		= request.getParameter("Principal")==null?"0":request.getParameter("Principal");
		String escala				= request.getParameter("Escala");
		AlumAcademico alumAcademico = new AlumAcademico();
		AlumPlan alumPlan			= new AlumPlan();

		MapaMayorMenor mayor 		= mapaMayorMenorDao.getMayorPorDefecto(newPlanId);
		MapaMayorMenor menor 		= mapaMayorMenorDao.getMayorPorDefecto(newPlanId);

		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			String genero 			= alumPersonalDao.getGenero(codigoAlumno);
			String nivel 			= mapaPlanDao.getNivelId(newPlanId);	
			if (alumAcademicoDao.existeReg(codigoAlumno)) {
				alumAcademico 			= alumAcademicoDao.mapeaRegId(codigoAlumno);
				// Residencia externa para los alumnos a distancia 1=Presencial y 4=Nocturna
				if( (alumAcademico.getResidenciaId().equals("I")||alumAcademico.getResidenciaId().equals("i")) && alumAcademico.getDormitorio().equals("0")){
					if ( nivel.equals("1")){
						if (genero.equals("M"))
							alumAcademico.setDormitorio("1");
						else
							alumAcademico.setDormitorio("2");
					}else{
						if (genero.equals("M"))
							alumAcademico.setDormitorio("3");
						else
							alumAcademico.setDormitorio("4");
					}					
					alumAcademicoDao.updateReg(alumAcademico);
				}
			}	
		}		
		alumPlan.setCodigoPersonal(codigoAlumno);
		alumPlan.setPlanId(newPlanId);
		alumPlan.setFInicio(newFInicio);
		alumPlan.setEstado(newEstado);
		alumPlan.setPrincipal(newPrincipal);
		alumPlan.setEscuelaId(newEscuela);
		alumPlan.setAvanceId("1");
		alumPlan.setPermiso("N");
		alumPlan.setEvento("N");
		alumPlan.setGrado("1");
		alumPlan.setCiclo("1");
		alumPlan.setEscala(escala);
		alumPlan.setActualizado("N");
		alumPlan.setCicloSep("0");
		alumPlan.setPrimerMatricula(aca.util.Fecha.getHoy());
		alumPlan.setMayor(mayor.getFolio()==null?"0":mayor.getFolio());
		alumPlan.setMenor(menor.getFolio()==null?"0":menor.getFolio());
		
		// Inicializar los estados de los planes
		alumPlanDao.updatePrincipal(codigoAlumno);
		alumPlanDao.updateEstado("0", codigoAlumno);
		
		LogProceso proceso = new LogProceso();
		
		if (alumPlanDao.insertReg(alumPlan)){	
			
			proceso.setFolio(String.valueOf(logProcesoDao.maximoReg()));
			proceso.setCodigoPersonal(codigoAlumno);
			proceso.setModulo("ASIGNA PLAN");
			proceso.setFecha(Fecha.getHoy());
			proceso.setEvento("TERMINA-INSERTAR");
			
			logProcesoDao.insertReg(proceso);
			
			mensaje = "New Plan Assigned";
		}else{
			mensaje = "Error assigning plan";			
		}
		
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
	
	
	@RequestMapping("/admision/plan/borrarPlan")
	public String admisionPlanBorrarPlan(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String planId   			= request.getParameter("PlanId");		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");			
		}		
		if(krdxCursoActDao.getListAll("WHERE CODIGO_PERSONAL = '"+codigoAlumno+"' AND SUBSTR(CURSO_ID, 1,8) = '"+planId+"'").size() == 0){
			if (alumPlanDao.deleteReg(codigoAlumno, planId)) {				
				mensaje = "Deleted Plan!";					
			}else{
				mensaje = "Error deleting plan";
			}
		}else{
		  	mensaje = "Unable to delete plan. The student has subjects enrolled in this plan";
		}		
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/plan/activarPlan")
	public String admisionPlanActivarPlan(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String planId   			= request.getParameter("PlanId");
		AlumPlan alumPlan			= new AlumPlan();
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			if (alumPlanDao.updateEstado("0", codigoAlumno)){
		    	alumPlan = alumPlanDao.mapeaRegId(codigoAlumno,planId);    	
		    	alumPlan.setEstado("1");		    	
		    	if (alumPlanDao.updateReg(alumPlan)){    		
		    		mensaje	= "Active Plan";
		    	}else{
		    		mensaje	= "Error activating plan";
		    	}	
			}
		}	
			
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/plan/activarPrincipal")
	public String admisionPlanActivarPrincipal(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String planId   			= request.getParameter("PlanId");
		AlumPlan alumPlan			= new AlumPlan();
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			if (alumPlanDao.updatePrincipal(codigoAlumno)){
		    	alumPlan = alumPlanDao.mapeaRegId(codigoAlumno,planId);    	
		    	alumPlan.setPrincipal("1");		    	
		    	if (alumPlanDao.updateReg(alumPlan)){    		
		    		mensaje	= "Plan set as main";
		    	}else{
		    		mensaje	= "Unable to set as main plan";
		    	}	
			}
		}	
			
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
	
	@RequestMapping("/admision/plan/grabarEscala")
	public String admisionPlanGrabarEscala(HttpServletRequest request, Model modelo){		
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String planId   			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		AlumPlan alumPlan			= new AlumPlan();
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			if (alumPlanDao.existeReg(codigoAlumno, planId)){
		    	alumPlan = alumPlanDao.mapeaRegId(codigoAlumno,planId);
		    	if (alumPlan.getEscala().equals("10")) alumPlan.setEscala("100"); else alumPlan.setEscala("10");
		    	if (alumPlanDao.updateReg(alumPlan)){    		
		    		mensaje	= "Updated scale";
		    	}else{
		    		mensaje	= "Unable to update scale";
		    	}	
			}
		}	
			
		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}	

	@RequestMapping("/admision/plan/editarMayorMenor")
	public String admisionPlanEditarMayorMenor(HttpServletRequest request, Model modelo){
		String codigoAlumno 		= "-";
		String alumnoNombre			= "-";
		String mayor				= request.getParameter("Mayor")==null?"0":request.getParameter("Mayor");
		String menor				= request.getParameter("Menor")==null?"0":request.getParameter("Menor");
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");

		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
			alumnoNombre 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
		}		
		
		List<MapaMayorMenor> lisMayores = mapaMayorMenorDao.getListaMayores(planId, " ORDER BY NOMBRE");
		List<MapaMayorMenor> lisMenores = mapaMayorMenorDao.getListaMenores(planId, " ORDER BY NOMBRE");

		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("alumnoNombre", alumnoNombre);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("mayor", mayor);
		modelo.addAttribute("menor", menor);
		modelo.addAttribute("lisMayores", lisMayores);
		modelo.addAttribute("lisMenores", lisMenores);

		return "admision/plan/editarMayorMenor";
	}

	@RequestMapping("/admision/plan/grabarMayorMenor")
	public String admisionPlanGrabarMayorMenor(HttpServletRequest request, Model modelo){
		String codigoAlumno 		= "-";
		String mensaje				= "-";
		String mayor				= request.getParameter("Mayor")==null?"0":request.getParameter("Mayor");
		String menor				= request.getParameter("Menor")==null?"0":request.getParameter("Menor");
		String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		AlumPlan alumPlan			= new AlumPlan();

		HttpSession sesion 			= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno 			= (String)sesion.getAttribute("codigoAlumno");
		}	

		if (alumPlanDao.existeReg(codigoAlumno, planId)){
			alumPlan = alumPlanDao.mapeaRegId(codigoAlumno,planId);
			alumPlan.setMayor(mayor);
			alumPlan.setMenor(menor);

			if(alumPlanDao.updateReg(alumPlan)){
				mensaje = "Updated Major / Minor";
			}else{
				mensaje = "Error Updating Major / Minor";
			}
		}

		return "redirect:/admision/plan/planes?Mensaje="+mensaje;
	}
}