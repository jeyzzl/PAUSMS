package aca.web.archivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.archivo.spring.ArchDocAlum;
import aca.archivo.spring.ArchDocAlumDao;
import aca.archivo.spring.ArchDocumentos;
import aca.archivo.spring.ArchDocumentosDao;
import aca.archivo.spring.ArchRevalida;
import aca.archivo.spring.ArchRevalidaDao;
import aca.archivo.spring.ArchivoDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.pg.archivo.spring.PosArchGeneralDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;

@Controller
public class ContArchivoReportes {
		
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	private ArchDocumentosDao archDocumentosDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private ArchDocAlumDao archDocAlumDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private ArchRevalidaDao archRevalidaDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private ArchivoDao archivoDao;
	
	@Autowired
	private PosArchGeneralDao posArchGeneralDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/archivo/reportes/doc_existe")
	public String archivoReportesDocExiste(HttpServletRequest request,  Model modelo){
		
		String documentoId		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		
		List<ArchDocumentos> lisDoc		= archDocumentosDao.getListAll(" ORDER BY 2");
		List<Inscritos> lisAlumnos		= inscritosDao.getListAll(" ORDER BY CARRERA_ID, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String, String> mapDocumento				= archDocAlumDao.mapAlumnosEnDocumento(documentoId);
		HashMap<String, String> mapImagenes					= archDocAlumDao.mapAlumnosEnDocumento(documentoId);
		HashMap<String, CatCarrera> mapCarrera 				= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("documentoId", documentoId);
		modelo.addAttribute("lisDoc", lisDoc);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapDocumento", mapDocumento);
		modelo.addAttribute("mapImagenes", mapImagenes);
		modelo.addAttribute("mapCarrera", mapCarrera);
		
		return "archivo/reportes/doc_existe";
	}
	
	@RequestMapping("/archivo/reportes/fecha")
	public String archivoReportesFecha(HttpServletRequest request){		
		return "archivo/reportes/fecha";
	}
	
	@RequestMapping("/archivo/reportes/listado")
	public String archivoReportesListado(HttpServletRequest request, Model modelo){
		
		List<String> lisAlumnos 				= posArchGeneralDao.lisAlumnosPendientes(" ORDER BY 1");		
		HashMap<String,String> mapaAlumnos 		= new HashMap<String,String>();
		HashMap<String,String> mapaImagenes		= posArchGeneralDao.mapaNumImagenes();
		
		for (String alumno : lisAlumnos) {
			String alumnoNombre = alumPersonalDao.getNombreAlumno(alumno, "NOMBRE");
			mapaAlumnos.put(alumno, alumnoNombre);			
		}
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaImagenes", mapaImagenes);
		
		return "archivo/reportes/listado";
	}
	
	@RequestMapping("/archivo/reportes/servicio")
	public String archivoReportesServcico(HttpServletRequest request, Model modelo){
		
		String planId 					= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
		
		List<MapaPlan> lisPlanes 					= mapaPlanDao.listPlanes("'A','V','I'", " ORDER BY PLAN_ID");
		
		if (planId.equals("0") && lisPlanes.size()>=1) planId = lisPlanes.get(0).getPlanId();
		
		List<AlumPersonal> lisAlumnos 				= alumPersonalDao.lisAlumnosConDocumentos(planId,"7,14", "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<aca.Mapa> lisPlanesPorAlumno 			= alumPlanDao.lisPlanesPorNivelYDocumentos("2", "7,14", " ORDER BY CODIGO_PERSONAL, PLAN_ID");
		HashMap<String,String> mapaEnServicio 		= archDocAlumDao.mapaAlumnosEnDocumento("14");
		HashMap<String,String> mapaEnLicenciatura	= archDocAlumDao.mapaAlumnosEnDocumento("7");
		
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("lisPlanes", lisPlanes);
		modelo.addAttribute("lisPlanesPorAlumno", lisPlanesPorAlumno);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaEnServicio", mapaEnServicio);
		modelo.addAttribute("mapaEnLicenciatura", mapaEnLicenciatura);
		
		return "archivo/reportes/servicio";
	}
	
	
	@RequestMapping("/archivo/reportes/buscarImagenes")
	public String archivoReportesBuscarImagenes(HttpServletRequest request, Model modelo){	
		
		
		return "archivo/reportes/buscarImagenes";
	}
	
	@RequestMapping("/archivo/reportes/listadoIncorrectos")
	public String archivoReportesListadoIncorrectos(HttpServletRequest request, Model modelo){
		
		List<ArchDocAlum> lisAlumnos 			= archDocAlumDao.lisIncorrectos("");
		HashMap<String,String> mapaAlumnos 		= new HashMap<String,String>();
		HashMap<String,String> mapaDocumentos	= archDocumentosDao.mapaTodos();
		
		for (ArchDocAlum alumno : lisAlumnos) {
			String alumnoNombre = alumPersonalDao.getNombreAlumno(alumno.getMatricula(), "NOMBRE");
			mapaAlumnos.put(alumno.getMatricula(), alumnoNombre);			
		}
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "archivo/reportes/listadoIncorrectos";
	}
	
	@RequestMapping("/archivo/reportes/borrarImagenes")
	public String archivoReportesBorrarImagenes(HttpServletRequest request){
		String matricula = request.getParameter("Matricula")==null?"0":request.getParameter("Matricula");
		
		posArchGeneralDao.deletePorAlumno(matricula);
		
		return "redirect:/archivo/reportes/listado";
	}
	
	@RequestMapping("/archivo/reportes/menu")
	public String archivoReportesMenu(HttpServletRequest request){		
		return "archivo/reportes/menu";
	}
	
	@RequestMapping("/archivo/reportes/reporte")
	public String archivoReportesReporte(HttpServletRequest request, Model modelo){
		
		List<Inscritos> lisInscritos = inscritosDao.getListAllModalidades("1,4", " ORDER BY ENOC.FACULTAD(CARRERA_ID),CARRERA_ID,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
		
		HashMap<String,CatFacultad> mapaFacultades 	= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.getMapAll("");	
		HashMap<String,String> mapaAutorizados 		= new HashMap<String,String>();
		
		for (Inscritos inscrito : lisInscritos){
			String auto = archivoDao.autorizaAlumno(inscrito.getCodigoPersonal(), inscrito.getPlanId());
			mapaAutorizados.put(inscrito.getCodigoPersonal(), auto);
		}
		
		modelo.addAttribute("lisInscritos", lisInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAutorizados", mapaAutorizados);
		
		return "archivo/reportes/reporte";
	}

	@RequestMapping("/archivo/reportes/alumnoSinDoc")
	public String archivoReportesAlumnoSinDoc(HttpServletRequest request, Model modelo){
		
		String documentoId 		= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String facultadId 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
		
		List<ArchDocumentos> lisDoc		= archDocumentosDao.getListAll(" ORDER BY 2");
		
		List<Inscritos> lisAlumnos		= new ArrayList<Inscritos>();
		if(cargaId.equals("0")) {
			lisAlumnos		= inscritosDao.getListAll(" ORDER BY CARRERA_ID, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		}else {
			lisAlumnos		= inscritosDao.getListAll(" WHERE CARGA_ID = '"+cargaId+"' ORDER BY CARRERA_ID, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		}
		
		List<Carga> lisCargas			= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
		List<CatFacultad> lisFacultades	= catFacultadDao.getListAll(" ORDER BY FACULTAD_ID");

		if(documentoId.equals("0")) {
			documentoId = lisDoc.get(0).getIdDocumento();
		}
		
		HashMap<String, String> mapDocumento	= archDocAlumDao.mapAlumnosEnDocumento(documentoId);
		HashMap<String, CatCarrera> mapCarrera 	= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("documentoId", documentoId);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("facultadId", facultadId);
		modelo.addAttribute("lisDoc", lisDoc);
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapDocumento", mapDocumento);
		modelo.addAttribute("mapCarrera", mapCarrera);
		
		return "archivo/reportes/alumnoSinDoc";
	}
	
	@RequestMapping("/archivo/reportes/alumnoConRevalida")
	public String archivoReportesAlumnoConRevalida(HttpServletRequest request, Model modelo){		
		
		
		List<ArchRevalida> lisRevalida				= archRevalidaDao.getListAll("");
		HashMap<String,AlumPersonal> mapaAlumnos 	= alumPersonalDao.mapaAlumnoRevalida();
		HashMap<String,String> mapaAutorizados 		= new HashMap<String,String>();
		
		for (ArchRevalida revalida : lisRevalida) {
			String planId 		=  alumPlanDao.getPlanActual(revalida.getCodigoPersonal());
			String autorizado 	= archivoDao.autorizaAlumno(revalida.getCodigoPersonal(), planId);
			mapaAutorizados.put(revalida.getCodigoPersonal(), autorizado);
		}
		
		modelo.addAttribute("lisRevalida", lisRevalida);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);	
		modelo.addAttribute("mapaAutorizados", mapaAutorizados);
		
		return "archivo/reportes/alumnoConRevalida";
	}
	
	@RequestMapping("/archivo/reportes/borrarRevalida")
	public String archivoReportesBorrarRevalida(HttpServletRequest request){	
				
		String codigoAlumno				= request.getParameter("CodigoAlumno") == null ? "0" : request.getParameter("CodigoAlumno");
		
		if(archRevalidaDao.existeReg(codigoAlumno)) {
			archRevalidaDao.deleteReg(codigoAlumno);
		}
		
		return "redirect:/archivo/reportes/alumnoConRevalida";
	}
}
