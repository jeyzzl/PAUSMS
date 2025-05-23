package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.InscritosDao;

@Controller
public class ContInscritosCiclos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao; 
	
	@Autowired 
	private AccesoDao accesoDao;
	
	@Autowired
	private AlumPlanDao alumPlanDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	
	@Autowired
	ServletContext context;
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	

	@RequestMapping("/inscritos/ciclos/cargasActivas")
	public String inscritosCiclosCargasActivas(HttpServletRequest request, Model modelo){
		
		String fInscripcion					= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		List<Carga> lisCarga		= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas	= cargaDao.getListCargasActivas( " ORDER BY CARGA_ID");
		HashMap<String,String> mapaPrimerDia 	= cargaDao.mapaPrimerDia();
		HashMap<String,String> mapaUltimoDia 	= cargaDao.mapaPrimerDia();
		
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisActivas", lisActivas);
		modelo.addAttribute("mapaPrimerDia", mapaPrimerDia);
		modelo.addAttribute("mapaUltimoDia", mapaUltimoDia);	
				
		return "inscritos/ciclos/cargasActivas";
	}
	
	@RequestMapping("/inscritos/ciclos/inscritos")
	public String inscritosCiclosInscritos(HttpServletRequest request, Model modelo){
		HttpSession sesion = ((HttpServletRequest) request).getSession();
		String fecha = "20/02/24";
		
		String periodoId = catPeriodoDao.getPeriodo(fecha);
		String codigoPersonal = "0";
		String modalidadString = "";
		String fechaIni = "01/01/99";
		String fechaFin = "01/02/99";
		if (sesion != null){
	    	codigoPersonal = sesion.getAttribute("codigoPersonal").toString();
	    	fechaIni = sesion.getAttribute("fechaIni").toString();
	    	fechaFin = sesion.getAttribute("fechaFin").toString();	    }
		
		Acceso acceso = accesoDao.mapeaRegId(codigoPersonal);
		boolean tieneAcceso = accesoDao.existeReg(codigoPersonal);
		
		
		String lisCargas = cargaDao.getCargasActivas(fecha);
		List<CatModalidad> lisModalidad = catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		
		
		
		int row = 1;
		for(CatModalidad mod : lisModalidad){		
			if (row>1) modalidadString += ",";
			modalidadString += "'"+mod.getModalidadId()+"'";
			row++;
		}
		
		
		int maximoCiclo = alumPlanDao.getMaximoCicloCargas(lisCargas);
		List<String> listaCarreraPorCiclo 					= inscritosDao.getListaCarreraPorCiclo(lisCargas, modalidadString, fechaIni, fechaFin, "ORDER BY ENOC.FACULTAD(CARRERA_ID), 1");
		HashMap<String, String> mapaInscritosPorCiclo 		= inscritosDao.getMapaCantidadPorCiclo(lisCargas, modalidadString, "I");
		HashMap<String, String> mapaFacultadPorCarrera 	= catCarreraDao.mapaFacultadPorCarrera();
		HashMap<String, CatFacultad> mapaFacultades			= catFacultadDao.getMapFacultad("");
		HashMap<String, CatCarrera> mapaCarrera				= catCarreraDao.getMapAll("");
		
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("tieneAcceso", tieneAcceso);
		modelo.addAttribute("maximoCiclo", maximoCiclo);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("listaCarreraPorCiclo", listaCarreraPorCiclo);
		modelo.addAttribute("mapaInscritosPorCiclo", mapaInscritosPorCiclo);
		modelo.addAttribute("mapaFacultadPorCarrera", mapaFacultadPorCarrera);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarrera", mapaCarrera);
		
		
		return "inscritos/ciclos/inscritos";
	}
	
	@RequestMapping("/inscritos/ciclos/modalidades")
	public String inscritosCiclosModalidades(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }		
	    
	    List<CatModalidad> lisModalidad	= catModalidadDao.getListAll( "ORDER BY MODALIDAD_ID");
	    HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas);
	    
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("mapa", mapa);
	    
		//enviarConArchivo(request,"Error-aca.ControllerDatosAlumno|credencialEmpDatoDep:");		
		return "inscritos/ciclos/modalidades";
	}
}
