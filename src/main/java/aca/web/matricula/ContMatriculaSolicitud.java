package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.calcula.spring.CalAlumnoDao;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.carga.spring.CargaEnLinea;
import aca.carga.spring.CargaEnLineaDao;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.carga.spring.CargaPracticaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.kardex.spring.KrdxCursoAct;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaCurso;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContMatriculaSolicitud {
	
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	CargaEnLineaDao cargaEnLineaDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	FesCcobroDao fesCCobroDao;
	
	@Autowired
	AlumEstadoDao alumEstadoDao;
	
	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CargaPracticaAlumnoDao cargaPracticaAlumnoDao; 
	
	@Autowired
	private CargaPracticaDao cargaPracticaDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private CalAlumnoDao calAlumnoDao;

	@Autowired
	private MapaCursoDao mapaCursoDao;
	
	
	@RequestMapping("/matricula/solicitud/listado")
	public String matriculaSolicitudListado(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques 		= "'1'";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			bloques = (String)sesion.getAttribute("bloques");
		}
		
		List<CargaEnLinea> lisCargasActivas = cargaEnLineaDao.getListActivas("ORDER BY CARGA_ID DESC");
		List<CatFacultad> lisFacultades =  catFacultadDao.getListAll("ORDER BY NOMBRE_FACULTAD");
				
		if(cargaId.equals("0")) {
			cargaId = lisCargasActivas.get(0).getCargaId();
		}		
		
//		HashMap<String,String> mapaAlumnosEnfacultad	= cargaAlumnoDao.mapaAlumnoPorFacultadEnCarga(cargaId, bloques);
		HashMap<String,String> mapaAlumnosEnCarga		= cargaAlumnoDao.mapaAlumnosEnCarga(cargaId,bloques, "N");
		HashMap<String,String> mapaAlumnosEnCalculo		= cargaAlumnoDao.mapaAlumnosEnCalculo(cargaId,bloques);
//		HashMap<String,String> mapaAlumnosEnCalculo		= cargaAlumnoDao.mapaAlumnosEnCalculo(cargaId,bloques,"S");
		HashMap<String,String> mapaCargasConfirmadas	= cargaAlumnoDao.mapaAlumnosEnCargaConfirmada(cargaId, bloques);
//		HashMap<String,String> mapaCargasPagadas		= cargaAlumnoDao.mapaAlumnosEnCargaPagada(cargaId, bloques);
		HashMap<String,String> mapaInscritos			= cargaAlumnoDao.mapaAlumnosInscritos(cargaId, bloques);		
		HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
//		HashMap<String, String> mapaPracticas			= alumnoCursoDao.mapaMateriasEnPracticas(cargaId, bloques);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisCargasActivas",lisCargasActivas);
		modelo.addAttribute("lisFacultades",lisFacultades);
//		modelo.addAttribute("mapaAlumnosEnfacultad",mapaAlumnosEnfacultad);
		modelo.addAttribute("mapaAlumnosEnCarga",mapaAlumnosEnCarga);
		modelo.addAttribute("mapaAlumnosEnCalculo",mapaAlumnosEnCalculo);
		modelo.addAttribute("mapaCargasConfirmadas",mapaCargasConfirmadas);
//		modelo.addAttribute("mapaCargasPagadas",mapaCargasPagadas);
		modelo.addAttribute("mapaInscritos",mapaInscritos);
		modelo.addAttribute("mapaBloques",mapaBloques);		
//		modelo.addAttribute("mapaPracticas",mapaPracticas);
		
		return "matricula/solicitud/listado";
	}
	
	@RequestMapping("/matricula/solicitud/cargaAcademica")
	public String matriculaSolicitudCargaAcademica(HttpServletRequest request, Model modelo){
		
		String cargaId 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques 		= "'1'";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			bloques = (String)sesion.getAttribute("bloques");
		}
		
		List<CargaAlumno> listaCargaAcademica 			= cargaAlumnoDao.lisEnMaterias(cargaId, bloques, "ORDER BY PLAN_ID");		
		HashMap<String, String> mapaNombreAlumno		= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaPlanes				= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 		= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		HashMap<String,String> mapaPagares				= calAlumnoDao.mapaPagares(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("listaCargaAcademica", listaCargaAcademica);
		modelo.addAttribute("mapaNombreAlumno", mapaNombreAlumno);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaEnCarga", mapaEnCarga);
		modelo.addAttribute("mapaCreditosConfirmadas", mapaCreditosConfirmadas);
		modelo.addAttribute("mapaPagares", mapaPagares);
		
		return "matricula/solicitud/cargaAcademica";
	}
	
	@RequestMapping("/matricula/solicitud/bloques")
	public String matriculaSolicitudBloques(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String nombreCarga		= cargaDao.getNombreCarga(cargaId);
		
		List<CargaBloque> lisBloques 				= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");		
		
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("lisBloques",lisBloques);	
		
		return "matricula/solicitud/bloques";
	}
	
	@RequestMapping("/matricula/solicitud/subirBloques")
	public String matriculaSolicitudSubirBloques(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");	
		String bloques 			= "0";
		List<CargaBloque> lisBloques 				= cargaBloqueDao.getLista(cargaId, " ORDER BY BLOQUE_ID");
		int row=0;
		for (CargaBloque bloque : lisBloques){			
			if (request.getParameter(bloque.getBloqueId()) != null) {
				row++;
				if (row==1) bloques = bloque.getBloqueId(); else bloques += ","+bloque.getBloqueId(); 
			}
		}
		if (bloques.equals("0")) bloques = "'1'";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			sesion.setAttribute("bloques",bloques);
		}
		
		return "redirect:/matricula/solicitud/bloques?CargaId="+cargaId;
	}
	
	@RequestMapping("/matricula/solicitud/materias")
	public String matriculaSolicitudMaterias(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CargaAlumno> lisMaterias 					= cargaAlumnoDao.lisEnMaterias(cargaId, facultadId, bloques, " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");		
		
		HashMap<String, MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
		HashMap<String, String> mapaMaterias			= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		HashMap<String,String> mapaPagares				= calAlumnoDao.mapaPagares(cargaId);
		
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("lisMaterias",lisMaterias);
		
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaCreditosConfirmadas",mapaCreditosConfirmadas);
		modelo.addAttribute("mapaPagares",mapaPagares);
		
		return "matricula/solicitud/materias";
	}
	
	@RequestMapping("/matricula/solicitud/practicas")
	public String matriculaSolicitudPracticas(HttpServletRequest request, Model modelo){
		
		String cargaId 					= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 				= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 				= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre			= catFacultadDao.getNombreFacultad(facultadId);
		
		List<AlumnoCurso> lisMaterias 			= alumnoCursoDao.lisAlumnosEnPracticas(cargaId, bloques, facultadId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL), NOMBRE_CURSO");
		HashMap<String, String> mapaAlumnos		= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String,CatCarrera> mapaCarreras = catCarreraDao.getMapAll("");
		HashMap<String,String> mapaFechas		= cargaPracticaDao.mapaFechasPracticas(cargaId, facultadId);
		HashMap<String,String> mapaLibres		= cargaPracticaAlumnoDao.mapaLibresPorCarga(cargaId, "L");
		HashMap<String,AlumAcademico> mapaRes   = alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, String> mapaPracticas	= alumnoCursoDao.mapaMateriasEnPracticas(cargaId, bloques);
		HashMap<String,CatFacultad> mapaFacultad   = catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaFechas",mapaFechas);
		modelo.addAttribute("mapaLibres",mapaLibres);
		modelo.addAttribute("mapaRes",mapaRes);
		modelo.addAttribute("mapaPracticas",mapaPracticas);
		modelo.addAttribute("mapaFacultad",mapaFacultad);


		return "matricula/solicitud/practicas";
	}
	
	@RequestMapping("/matricula/solicitud/listaPracticas")
	public String matriculaSolicitudListaPracticas(HttpServletRequest request, Model modelo){
		
		String cargaId 	= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		
		List<AlumnoCurso> lisMaterias 				= alumnoCursoDao.lisAlumnosEnPracticas(cargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL), NOMBRE_CURSO");
		
		HashMap<String, String> mapaNombreAlumno	= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaPlanes			= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 	= alumAcademicoDao.mapaEnCarga(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("mapaNombreAlumno",mapaNombreAlumno);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		
		return "matricula/solicitud/listaPracticas";
	}
	
	@RequestMapping("/matricula/solicitud/listaAlumnos")
	public String matriculaSolicitudListaAlumnos(HttpServletRequest request, Model modelo){
		
		String cargaId 	= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		
		List<AlumnoCurso> lisMaterias 				= alumnoCursoDao.lisAlumnosEnPracticas(cargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL), NOMBRE_CURSO");
		
		HashMap<String, String> mapaNombreAlumno	= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaPlanes			= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 	= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.mapaCarreras();
		HashMap<String, CatFacultad> mapaFacultades = catFacultadDao.getMapFacultad("");
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisMaterias",lisMaterias);
		modelo.addAttribute("mapaNombreAlumno",mapaNombreAlumno);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		
		return "matricula/solicitud/listaAlumnos";
	}
	
	@RequestMapping("/matricula/solicitud/calculos")
	public String matriculaSolicitudCalculos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CargaAlumno> lisCalculos 					= cargaAlumnoDao.lisEnCalculo(cargaId, facultadId, bloques, " ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		HashMap<String, MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
		HashMap<String, FesCcobro> mapaCalculo			= fesCCobroDao.mapaCalculosPorCarga(cargaId);
		HashMap<String, String> mapaPagos				= fesCCobroDao.mapaPagosEnCarga(cargaId);		
		HashMap<String, String> mapaMaterias			= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String, FesCcobro> mapaAlumnosEnLinea 	= fesCCobroDao.mapaAlumnosEnLinea();
		HashMap<String,AlumAcademico> mapaEnCarga		= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, String> mapaMateriasConfirmadas	= krdxCursoActDao.mapaMateriasConfirmadas(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("lisCalculos",lisCalculos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaCalculo",mapaCalculo);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaAlumnosEnLinea",mapaAlumnosEnLinea);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		modelo.addAttribute("mapaMateriasConfirmadas",mapaMateriasConfirmadas);
		modelo.addAttribute("mapaCreditosConfirmadas",mapaCreditosConfirmadas);
		
		return "matricula/solicitud/calculos";
	}
	
	@RequestMapping("/matricula/solicitud/listaCalculos")
	public String matriculaSolicitudListaCalculos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
	


		
		List<CargaAlumno> listaCalculos					= cargaAlumnoDao.lisEnCalculo(cargaId, bloques, "ORDER BY PLAN_ID");
		HashMap<String, String> mapaNombreAlumno		= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaPlanes				= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 		= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, String> mapaMateriasConfirmadas	= krdxCursoActDao.mapaMateriasConfirmadas(cargaId);
		HashMap<String, FesCcobro> mapaCalculo			= fesCCobroDao.mapaCalculosPorCarga(cargaId);
		HashMap<String, String> mapaPagos				= fesCCobroDao.mapaPagosEnCarga(cargaId);
		HashMap<String, String> mapaMaterias			= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		
		modelo.addAttribute("listaCalculos",listaCalculos);
		modelo.addAttribute("mapaNombreAlumno",mapaNombreAlumno);
		modelo.addAttribute("mapaCalculo",mapaCalculo);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		modelo.addAttribute("mapaMateriasConfirmadas",mapaMateriasConfirmadas);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaCreditosConfirmadas",mapaCreditosConfirmadas);
		
		return "matricula/solicitud/listaCalculos";
	}
	
	@RequestMapping("/matricula/solicitud/confirmados")
	public String matriculaSolicitudConfirmados(HttpServletRequest request, Model modelo){  
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);		
		
		List<CargaAlumno> lisConfirmar 				= cargaAlumnoDao.lisEnConfirmar(cargaId, facultadId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos			= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques	= cargaBloqueDao.mapaBloques();
		HashMap<String,AlumAcademico> mapaEnCarga	= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String, FesCcobro> mapaCalculo			= fesCCobroDao.mapaCalculosPorCarga(cargaId);
		HashMap<String, String> mapaPagos				= fesCCobroDao.mapaPagosEnCarga(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("lisConfirmar",lisConfirmar);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);    
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);    
		modelo.addAttribute("bloques",bloques);   
		modelo.addAttribute("mapaCalculo",mapaCalculo);
		modelo.addAttribute("mapaPagos",mapaPagos);
		
		return "matricula/solicitud/confirmados";
	}
	
	@RequestMapping("/matricula/solicitud/listaConfirmados")
	public String matriculaSolicitudListaConfirmados(HttpServletRequest request, Model modelo){  
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		
		List<CargaAlumno> listaConfirmar			= cargaAlumnoDao.lisEnConfirmar(cargaId, bloques, "ORDER BY PLAN_ID");		
		HashMap<String, String> mapaNombreAlumno	= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, String> mapaPlanes			= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 	= alumAcademicoDao.mapaEnCarga(cargaId);
		
		modelo.addAttribute("listaConfirmar",listaConfirmar);
		modelo.addAttribute("mapaNombreAlumno",mapaNombreAlumno);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		
		return "matricula/solicitud/listaConfirmados";
	}
	
	@RequestMapping("/matricula/solicitud/pagos")
	public String matriculaSolicitudPagos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CargaAlumno> lisPagos 					= cargaAlumnoDao.lisEnPago(cargaId, facultadId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos			= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques	= cargaBloqueDao.mapaBloques();
		HashMap<String, FesCcobro> mapaCalculo			= fesCCobroDao.mapaCalculosPorCarga(cargaId);
		HashMap<String, String> mapaPagos			= fesCCobroDao.mapaPagosEnCarga(cargaId);
		HashMap<String, String> mapaMaterias		= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String,AlumAcademico> mapaEnCarga	= alumAcademicoDao.mapaEnCarga(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("lisPagos",lisPagos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaCalculo",mapaCalculo);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		
		return "matricula/solicitud/pagos";
	}
	
	@RequestMapping("/matricula/solicitud/listaPagos")
	public String matriculaSolicitudListaPagos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		
		List<CargaAlumno> lisPagos 					= cargaAlumnoDao.lisEnPago(cargaId, bloques, "ORDER BY PLAN_ID");
		HashMap<String, String> mapaNombreAlumno	= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "NOMBRE");
		HashMap<String, FesCcobro> mapaCalculo			= fesCCobroDao.mapaCalculosPorCarga(cargaId);
		HashMap<String, String> mapaPagos				= fesCCobroDao.mapaPagosEnCarga(cargaId);
		HashMap<String, String> mapaPlanes			= mapaPlanDao.mapNombrePlan();
		HashMap<String,AlumAcademico> mapaEnCarga 	= alumAcademicoDao.mapaEnCarga(cargaId);
		
		modelo.addAttribute("lisPagos", lisPagos);
		modelo.addAttribute("mapaNombreAlumno", mapaNombreAlumno);
		modelo.addAttribute("mapaCalculo",mapaCalculo);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		
		return "matricula/solicitud/listaPagos";
	}
	
	@RequestMapping("/matricula/solicitud/total")
	public String matriculaSolicitudTotal(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CargaAlumno> lisTotal					= cargaAlumnoDao.lisTotal(cargaId, facultadId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos			= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques	= cargaBloqueDao.mapaBloques();
		HashMap<String, String> mapaPagos			= fesCCobroDao.mapaPagosEnCarga(cargaId);
		HashMap<String, String> mapaMaterias		= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String,AlumAcademico> mapaEnCarga	= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String,AlumEstado> mapaInscritos	= alumEstadoDao.mapaInscritosEnCarga(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("lisTotal",lisTotal);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		modelo.addAttribute("mapaInscritos",mapaInscritos);
		
		return "matricula/solicitud/total";
	}
	
	@RequestMapping("/matricula/solicitud/inscritos")
	public String matriculaSolicitudInscritos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadNombre	= catFacultadDao.getNombreFacultad(facultadId);
		
		List<CargaAlumno> lisInscritos 					= cargaAlumnoDao.lisInscritos(cargaId, facultadId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		
		HashMap<String, MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
		HashMap<String, AlumEstado> mapaInscritos		= alumEstadoDao.mapaInscritosEnCarga(cargaId);
		HashMap<String, String> mapaMateriasConfirmadas	= krdxCursoActDao.mapaMateriasConfirmadas(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("facultadNombre",facultadNombre);
		modelo.addAttribute("lisInscritos",lisInscritos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaInscritos",mapaInscritos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMateriasConfirmadas",mapaMateriasConfirmadas);
		modelo.addAttribute("mapaCreditosConfirmadas",mapaCreditosConfirmadas);
		
		return "matricula/solicitud/inscritos";
	}

	@RequestMapping("/matricula/solicitud/listaInscritos")
	public String matriculaSolicitudlistaInscritos(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		
		List<CargaAlumno> lisInscritos 					= cargaAlumnoDao.lisInscritos(cargaId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		HashMap<String, MapaPlan> mapaPlanes 			= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques		= cargaBloqueDao.mapaBloques();
		HashMap<String, AlumEstado> mapaInscritos		= alumEstadoDao.mapaInscritosEnCarga(cargaId);
		HashMap<String, String> mapaMateriasConfirmadas	= krdxCursoActDao.mapaMateriasConfirmadas(cargaId);
		HashMap<String, String> mapaCreditosConfirmadas = krdxCursoActDao.mapaCreditosConfirmadas(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);
		modelo.addAttribute("lisInscritos",lisInscritos);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaInscritos",mapaInscritos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMateriasConfirmadas",mapaMateriasConfirmadas);
		modelo.addAttribute("mapaCreditosConfirmadas",mapaCreditosConfirmadas);
		
		return "matricula/solicitud/listaInscritos";
	}
	
	@RequestMapping("/matricula/solicitud/listaTotal")
	public String matriculaSolicitudListaTotal(HttpServletRequest request, Model modelo){
		
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");		
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");		
		
		List<CargaAlumno> lisTotal					= cargaAlumnoDao.lisTotal(cargaId, bloques, "ORDER BY ENOC.PLAN_NOMBRE(PLAN_ID)");
		HashMap<String, MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, String> mapaAlumnos			= alumPersonalDao.mapaAlumnosEnCarga(cargaId, "APELLIDO");
		HashMap<String, CargaBloque> mapaBloques	= cargaBloqueDao.mapaBloques();
		HashMap<String, String> mapaPagos			= fesCCobroDao.mapaPagosEnCarga(cargaId);
		HashMap<String, String> mapaMaterias		= alumnoCursoDao.mapaMateriasEnCarga(cargaId);
		HashMap<String,AlumAcademico> mapaEnCarga	= alumAcademicoDao.mapaEnCarga(cargaId);
		HashMap<String,CatFacultad> mapaFacultades	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, String> mapaEdades			= alumPersonalDao.mapaEdadesAlumnosEnCarga(cargaId);
		
		modelo.addAttribute("cargaId",cargaId);		
		modelo.addAttribute("lisTotal",lisTotal);
		modelo.addAttribute("mapaPlanes",mapaPlanes);
		modelo.addAttribute("mapaAlumnos",mapaAlumnos);
		modelo.addAttribute("mapaBloques",mapaBloques);
		modelo.addAttribute("mapaPagos",mapaPagos);
		modelo.addAttribute("bloques",bloques);
		modelo.addAttribute("mapaMaterias",mapaMaterias);
		modelo.addAttribute("mapaEnCarga",mapaEnCarga);
		modelo.addAttribute("mapaFacultades",mapaFacultades);
		modelo.addAttribute("mapaCarreras",mapaCarreras);
		modelo.addAttribute("mapaEdades",mapaEdades);
		
		return "matricula/solicitud/listaTotal";
	}
	
	@RequestMapping("/matricula/solicitud/materiasAlumno")
	public String matriculaSolicitudMateriasAlumno(HttpServletRequest request, Model modelo){
		String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");		
		String bloques	 		= request.getParameter("Bloques") == null ? "0" : request.getParameter("Bloques");
		String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");
		String codigoPersonal 	= request.getParameter("CodigoPersonal") == null ? "0" : request.getParameter("CodigoPersonal");

		AlumPersonal alumno 	= alumPersonalDao.mapeaRegId(codigoPersonal);
		AlumAcademico academico = alumAcademicoDao.mapeaRegId(codigoPersonal);
		List<AlumnoCurso> listaMaterias = alumnoCursoDao.getListMateriasAlumnoPorCarga(codigoPersonal, cargaId);
		HashMap<String,MapaCurso> mapaCurso = mapaCursoDao.mapaCursoEnCarga(cargaId);

		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("bloques", bloques);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("alumno", alumno);
		modelo.addAttribute("academico", academico);
		modelo.addAttribute("listaMaterias", listaMaterias);
		modelo.addAttribute("mapaCurso", mapaCurso);

		return "matricula/solicitud/materiasAlumno"; 
	}
}