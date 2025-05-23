package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaEnLinea;
import aca.carga.spring.CargaEnLineaDao;
import aca.carga.spring.CargaPracticaAlumno;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidadDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.matricula.spring.MatAlumno;
import aca.matricula.spring.MatAlumnoDao;
import aca.matricula.spring.MatEvento;
import aca.matricula.spring.MatEventoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContMatriculaCandidatos {
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumAcademicoDao alumAcademicoDao;	
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	MatEventoDao matEventoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	MatAlumnoDao matAlumnoDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;

	@Autowired
	MapaPlanDao mapaPlanDao;
	
	
	@RequestMapping("/matricula/candidatos/listado")
	public String datosAlumnoCedulaListado(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";
		String codigoAlumno 	= "0";
		String nombreAlumno		= "-";
		String modalidadAlumno	= "0";
		String modalidadNombre 	= "-";
		String esEnLinea		= "N";
		String mensaje	 		= request.getParameter("Mensaje") == null ? "0" : request.getParameter("Mensaje");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		Acceso acceso 			= new Acceso();
		
		boolean muestraAgregar	= false;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	codigoPersonal 	   	= (String) sesion.getAttribute("codigoPersonal");
        	codigoAlumno 	   	= (String) sesion.getAttribute("codigoAlumno") == null ? "0" : (String) sesion.getAttribute("codigoAlumno");
        	nombreAlumno 		= alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE");
        	modalidadAlumno 	= alumAcademicoDao.getModalidadId(codigoAlumno);
			modalidadNombre 	= catModalidadDao.getNombreModalidad(modalidadAlumno);
			esEnLinea			= catModalidadDao.getEnLinea(modalidadAlumno);
        	acceso 				= accesoDao.mapeaRegId(codigoPersonal);
        }      
        
        List<MatEvento> lisMatEvento = matEventoDao.lisMatEvento("A"," ORDER BY EVENTO_ID DESC");

        MatEvento evento = new MatEvento();
        if(eventoId.equals("0")) {
        	eventoId = lisMatEvento.get(0).getEventoId();
        }
        
        if(matEventoDao.existeReg(eventoId)) {
        	evento = matEventoDao.mapeaRegId(eventoId);
        }
		
		HashMap<String, String> mapaAlumnos 		= alumPersonalDao.mapaAlumnosMatricula(eventoId);
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("ORDER BY NOMBRE_FACULTAD");
		HashMap<String, CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");		
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, MatAlumno> mapaMatAlumno	= matAlumnoDao.mapaMatAlumno(eventoId);
		
		List<MatAlumno> lisMatAlumno = matAlumnoDao.lisPorEvento(evento.getEventoId(),"ORDER BY FACULTAD(CARRERA(PLAN_ID))");
		List<AlumPlan> lisPlanes 	 = alumPlanDao.lisPlanesAlumno(codigoAlumno," ORDER BY ENOC.ALUM_PLAN.F_INICIO DESC");
		
		if(!codigoAlumno.equals("0") && !codigoAlumno.equals(codigoPersonal)) {
			muestraAgregar	= true;
		}
		
		modelo.addAttribute("nombreAlumno", nombreAlumno);
		modelo.addAttribute("modalidadNombre", modalidadNombre);
		modelo.addAttribute("esEnLinea", esEnLinea);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("lisMatAlumno", lisMatAlumno);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaMatAlumno", mapaMatAlumno);
		modelo.addAttribute("muestraAgregar", muestraAgregar);
		modelo.addAttribute("codigoAlumno", codigoAlumno);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisMatEvento", lisMatEvento);
		modelo.addAttribute("evento", evento);
		modelo.addAttribute("mensaje", mensaje);
		
		return "matricula/candidatos/listado";
	}
	
	@RequestMapping("/matricula/candidatos/grabar")
	public String matriculaCandidatosGrabar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");
		String eventoId	 		= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
		String planId	 		= request.getParameter("PlanId") == null ? "0" : request.getParameter("PlanId");
		String estado	 		= request.getParameter("Estado") == null ? "0" : request.getParameter("Estado");	

        if(matAlumnoDao.existeReg(eventoId, codigoPersonal, planId)) {
			matAlumnoDao.updateEstado(eventoId, codigoPersonal, estado, planId);
        }
		
		return "redirect:/matricula/candidatos/listado";
	}
	
}