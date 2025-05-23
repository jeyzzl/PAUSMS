package aca.web.inscritos;

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
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatReligionDao;
import aca.mentores.spring.MentAlumno;
import aca.mentores.spring.MentAlumnoDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContInscritosMentor {
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private CatReligionDao catReligionDao;
	
	@Autowired	
	private AlumEstadoDao alumEstadoDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired	
	private MentAlumnoDao mentAlumnoDao;
	
	@Autowired	
	private MaestrosDao maestrosDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	

	@RequestMapping("/inscritos/mentor/inscritos")
	public String inscritosTablaBestAdistica(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String cargas			= "''";
		String modalidades		= "'1'";		
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		Acceso acceso 			= new Acceso();
		boolean tieneAcceso		= false;
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal	= (String)sesion.getAttribute("codigoPersonal");
        	cargas			= (String)sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : (String)sesion.getAttribute("cargas");
        	modalidades		= (String)sesion.getAttribute("modalidades") == null ? "'1'" : (String)sesion.getAttribute("modalidades");   	
        	if (fechaIni.equals("0")){ 
				fechaIni = (String) sesion.getAttribute("fechaIni");
			}else {
				sesion.setAttribute("fechaIni", fechaIni);				
			}
			if (fechaFin.equals("0")) {
				fechaFin = (String) sesion.getAttribute("fechaFin");
			}else {
				sesion.setAttribute("fechaFin", fechaFin);
			}
			
			if (accesoDao.existeReg(codigoPersonal)) {
				tieneAcceso = true;
				acceso = accesoDao.mapeaRegId(codigoPersonal);
			}
        }
        
        List<Inscritos> lisInscritos 	= inscritosDao.getListAllUM(" WHERE CARGA_ID IN("+cargas+")" + 
				" AND MODALIDAD_ID IN ("+modalidades+")"+
				" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
				" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, MODALIDAD_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
        
        HashMap<String, CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarreras		= catCarreraDao.getMapAll("");
		HashMap<String, CatPais> mapaPaises				= catPaisDao.getMapAll();
		HashMap<String, CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String, CatReligion> mapaReligiones		= catReligionDao.getMapAll("");
		HashMap<String, CatEstado> mapaEstados			= catEstadoDao.getMapAll();
		HashMap<String, String> mapaGrados				= alumEstadoDao.mapGradoyCiclo(cargas, "I");
		HashMap<String, String> mapaEdades				= alumPersonalDao.mapaEdades(cargas);
		HashMap<String, MentAlumno> mapaMentores		= mentAlumnoDao.mapaMentores(cargas);
		HashMap<String, String> mapaMaestros			= maestrosDao.mapMaestroNombre("NOMBRE");

		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("lisInscritos", lisInscritos);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("modalidades", modalidades);
        modelo.addAttribute("fechaIni", fechaIni);
        modelo.addAttribute("fechaFin", fechaFin);
        
        modelo.addAttribute("mapaFacultades", mapaFacultades);
        modelo.addAttribute("mapaCarreras", mapaCarreras);
        modelo.addAttribute("mapaPaises", mapaPaises);
        modelo.addAttribute("mapaModalidades", mapaModalidades);
        modelo.addAttribute("mapaReligiones", mapaReligiones);
        modelo.addAttribute("mapaEstados", mapaEstados);
        modelo.addAttribute("mapaGrados", mapaGrados);
        modelo.addAttribute("mapaEdades", mapaEdades);
        modelo.addAttribute("mapaMentores", mapaMentores);
        modelo.addAttribute("mapaMaestros", mapaMaestros);
        
		return "inscritos/mentor/inscritos";
	}
	
	@RequestMapping("/inscritos/mentor/modalidades")
	public String inscritosMentorModalidades(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String cargas			= "";
		String modalidades		= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			cargas			= (String) sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
			modalidades		= (String)sesion.getAttribute("modalidades") == null ? "" : sesion.getAttribute("modalidades").toString();
		}
		
		HashMap<String, String> mapaModalidadesEnCargas 	= estadisticaDao.mapaModalidadesEnCargas(cargas);
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapaModalidadesEnCargas", mapaModalidadesEnCargas);
		
		return "inscritos/mentor/modalidades";
	}
	
	@RequestMapping("/inscritos/mentor/cargas")
	public String inscritosMentorCargas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal	= "";
		String cargas			= "";
		String modalidades		= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			cargas			= (String) sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : sesion.getAttribute("cargas").toString();
			modalidades		= (String)sesion.getAttribute("modalidades") == null ? "" : sesion.getAttribute("modalidades").toString();
		}
		
		HashMap<String, String> mapaModalidadesEnCargas 	= estadisticaDao.mapaModalidadesEnCargas(cargas);
		HashMap<String, String> mapaPrimerInscripcion 		= estadisticaDao.mapaPrimerInscripcion();
		HashMap<String, String> mapaUltimaInscripcion 	 	= estadisticaDao.mapaUltimaInscripcion();
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		List<Carga> lisCarga			= cargaDao.getListPorFecha(fInscripcion,  "ORDER BY CARGA_ID");
		List<Carga> lisActivas			= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID") ;
		
		modelo.addAttribute("codigoPersonal", codigoPersonal);
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("accion", accion);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapaModalidadesEnCargas", mapaModalidadesEnCargas);
		modelo.addAttribute("mapaPrimerInscripcion", mapaPrimerInscripcion);
		modelo.addAttribute("mapaUltimaInscripcion", mapaUltimaInscripcion);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisActivas", lisActivas);
		
		return "inscritos/mentor/cargas";
	}
}
