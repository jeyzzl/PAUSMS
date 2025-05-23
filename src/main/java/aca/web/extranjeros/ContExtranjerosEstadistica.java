package aca.web.extranjeros;

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
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.leg.spring.LegExtdoctos;
import aca.leg.spring.LegExtdoctosDao;
import aca.leg.spring.LegExtranjero;
import aca.leg.spring.LegExtranjeroDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;

@Controller
public class ContExtranjerosEstadistica {
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private AccesoDao accesoDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private CatPaisDao catPaisDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private CatReligionDao catReligionDao;
	
	@Autowired	
	private LegExtdoctosDao legExtdoctosDao;
	
	@Autowired	
	private LegExtranjeroDao legExtranjeroDao;
	
	@Autowired	
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired	
	private InscritosDao inscritosDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private MapaPlanDao mapaPlanDao;
	
	

	@RequestMapping("/extranjeros/estadistica/elegir")
	public String extranjerosReportesElegir(HttpServletRequest request, Model modelo){
		
		List<CatModalidad> lisModalidades	 = catModalidadDao.getListAll(" ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("lisModalidades", lisModalidades);
		
		return "extranjeros/estadistica/elegir";
	}
	
	@RequestMapping("/extranjeros/estadistica/cambio")
	public String extranjerosReportesCambio(HttpServletRequest request, Model modelo){
		
		String modalidades = ""; 
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 	= (String) sesion.getAttribute("modalidadesReportes");
		}
		List<Estadistica> lisExtranjeros  					= estadisticaDao.lisExtInscModalidad(modalidades, " ORDER BY CODIGO_PERSONAL");
		HashMap<String,CatModalidad> mapaModalidades 		= catModalidadDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 					= catPaisDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarreras 			= catCarreraDao.getMapAll("");
		HashMap<String,LegExtranjero> mapaExtranjeros		= legExtranjeroDao.mapaExtranjeros();
		HashMap<String,Estadistica> mapaCarreraAnterior		= new HashMap<String,Estadistica>();
		
		String codigoAlumno 		= "0";
		String fechaInscripcion		= "0";
		String fechaAnterior 		= "0";
		Estadistica estAnt			= new Estadistica();
		for (Estadistica est : lisExtranjeros) {
			if (!est.getCodigoPersonal().equals(codigoAlumno)) {
				codigoAlumno 		= est.getCodigoPersonal();
				fechaInscripcion 	= est.getFechaInscripcion();
				fechaAnterior		= estadisticaDao.getFechaMaxima(codigoAlumno, fechaInscripcion);
				estAnt				= estadisticaDao.mapeaReg(codigoAlumno, fechaAnterior);
				mapaCarreraAnterior.put(codigoAlumno, estAnt);
			}
		}
		
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaExtranjeros", mapaExtranjeros);
		modelo.addAttribute("mapaCarreraAnterior", mapaCarreraAnterior);
		
		return "extranjeros/estadistica/cambio";
	}
	
	@RequestMapping("/extranjeros/estadistica/noAutorizadas")
	public String extranjerosExtranjeroNoAutorizados(HttpServletRequest request, Model modelo){
		
		String modalidades = ""; 
		
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 	= (String) sesion.getAttribute("modalidadesReportes");
		}
		
		List<Estadistica> lisExtranjeros				= estadisticaDao.lisExtInscModalidad(modalidades, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,CatPais> mapaPaises				= catPaisDao.getMapAll();
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,LegExtranjero> mapaExtranjeros	= legExtranjeroDao.mapaExtranjeros();	
		
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaExtranjeros", mapaExtranjeros);
		
		return "extranjeros/estadistica/noAutorizadas";
	}
	
	@RequestMapping("/extranjeros/estadistica/extranjerospais")
	public String extranjerosExtranjeroExtranjerosPais(HttpServletRequest request, Model modelo){
		
		String modalidades 	= "";
		String fechaIni		= "";
		String fechaFin 	= "";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 	= (String) sesion.getAttribute("modalidadesReportes");
			fechaIni 		= (String) sesion.getAttribute("fechaIni");
			fechaFin 		= (String) sesion.getAttribute("fechaFin");
		}
		
		List<CatPais> lisPaises 						= catPaisDao.lisPaisesExtranjeros(" ORDER BY NOMBRE_PAIS");		
		List<Estadistica> lisExtranjeros				= estadisticaDao.lisExtInscModalidad(modalidades,fechaIni, fechaFin," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocumentosExtranjeros();
		HashMap<String,MapaPlan> mapaPlanes				= mapaPlanDao.mapPlanes("'A','V','I'");
				
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		
		return "extranjeros/estadistica/extranjerospais";		
	}
	
	@RequestMapping("/extranjeros/estadistica/extranjerosorigen")
	public String extranjerosExtranjeroExtranjerosOrigen(HttpServletRequest request, Model modelo){
		
		String modalidades 	= "";
		String fechaIni		= "";
		String fechaFin 	= "";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 	= (String) sesion.getAttribute("modalidadesReportes");
			fechaIni 		= (String) sesion.getAttribute("fechaIni");
			fechaFin 		= (String) sesion.getAttribute("fechaFin");
		}
		
		List<CatPais> lisPaises 						= catPaisDao.lisPaisesOrigen(modalidades," ORDER BY NOMBRE_PAIS");
		List<Estadistica> lisExtranjeros				= estadisticaDao.lisExtInscritosOrigen(modalidades,fechaIni, fechaFin," ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocumentosExtranjeros();
				
		modelo.addAttribute("lisPaises", lisPaises);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);	
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);			
		
		return "extranjeros/estadistica/extranjerosorigen";		
	}
	
	@RequestMapping("/extranjeros/estadistica/meses")
	public String extranjerosExtranjeroMeses(HttpServletRequest request, Model modelo){
		String modalidades 		= "0";
		String codigoPersonal 	= "";
		String privilegios		= "";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			if (accesoDao.esAdministrador(codigoPersonal)||accesoDao.esSupervisor(codigoPersonal)) { 
				privilegios = "*";
			}else {
				privilegios = accesoDao.getAccesos(codigoPersonal);
			}
		}	
		
		List<Estadistica> lisExtranjeros = estadisticaDao.getListExtranjerosInscritosModalidad(modalidades, "ORDER BY TO_DATE(COALESCE(FECHA_PASAPORTE(CODIGO_PERSONAL),'01/01/1900'), 'DD/MM/YYYY'), APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 				= catPaisDao.getMapAll();
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocExtranjeros();
		
		modelo.addAttribute("privilegios", privilegios);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);	
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "extranjeros/estadistica/meses";		
	}
	
	@RequestMapping("/extranjeros/estadistica/venceFM3")
	public String extranjerosExtranjeroVenceFM3(HttpServletRequest request, Model modelo){
		String modalidades 		= "0";
		String codigoPersonal 	= "";
		String privilegios		= "";
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
			if (accesoDao.esAdministrador(codigoPersonal)||accesoDao.esSupervisor(codigoPersonal)) { 
				privilegios = "*";
			}else {
				privilegios = accesoDao.getAccesos(codigoPersonal);
			}
		}	
		
		List<Estadistica> lisExtranjeros				 = estadisticaDao.getListExtranjerosInscritosModalidad(modalidades, "ORDER BY TO_DATE(COALESCE(FECHA_FM3_REVERSA(CODIGO_PERSONAL),'2000/01/01'), 'YYYY/MM/DD'), APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 				= catPaisDao.getMapAll();
		HashMap<String,String> mapaDocumentos			= legExtdoctosDao.mapaDocExtranjeros();
		
		modelo.addAttribute("privilegios", privilegios);
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);	
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaDocumentos", mapaDocumentos);
		
		return "extranjeros/estadistica/venceFM3";		
	}
	
	@RequestMapping("/extranjeros/estadistica/inscritos")
	public String extranjerosExtranjeroInscritos(HttpServletRequest request, Model modelo){
		String modalidades 		= "0";	
		HttpSession sesion 	= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			modalidades 		= (String) sesion.getAttribute("modalidadesReportes");		
		}	
		
		List<Estadistica> lisExtranjeros = estadisticaDao.getListExtranjerosInscritosModalidad(modalidades, "ORDER BY TO_DATE(COALESCE(FECHA_FM3_REVERSA(CODIGO_PERSONAL),'2000/01/01'), 'YYYY/MM/DD'), APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		HashMap<String,CatModalidad> mapaModalidades	= catModalidadDao.getMapAll("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,CatPais> mapaPaises 				= catPaisDao.getMapAll();
		HashMap<String,CatReligion> mapaReligion		= catReligionDao.getMapAll("");
		HashMap<String,CatEstado> mapaEstados 			= catEstadoDao.getMapAll();
	 	HashMap<String,CatTipoAlumno> mapaTipos 	  	=catTipoAlumnoDao.getMapAll("");
	
		
		modelo.addAttribute("lisExtranjeros", lisExtranjeros);		
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);	
		modelo.addAttribute("mapaPaises", mapaPaises);	
		modelo.addAttribute("mapaReligion", mapaReligion);	
		modelo.addAttribute("mapaEstados", mapaEstados);
		modelo.addAttribute("mapaTipos", mapaTipos);
		
		return "extranjeros/estadistica/inscritos";
	}
}