package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
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
import aca.catalogo.spring.CatPeriodoDao;
import aca.catalogo.spring.CatReligion;
import aca.catalogo.spring.CatReligionDao;
import aca.catalogo.spring.CatTipoAlumno;
import aca.catalogo.spring.CatTipoAlumnoDao;
import aca.vista.spring.EstInscritos;
import aca.vista.spring.EstInscritosDao;
import aca.vista.spring.EstadisticaDao;
import aca.vista.spring.Inscritos;
import aca.vista.spring.InscritosDao;


@Controller
public class ContInscritosInscritos {
	
	
	@Autowired
	private CargaDao cargaDao;
	
	@Autowired
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private EstInscritosDao estInscritosDao;
	
	@Autowired
	private AlumPersonalDao alumPersonalDao;
	
	@Autowired
	private InscritosDao inscritosDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	private CatReligionDao catReligionDao;
	
	@Autowired
	private CatTipoAlumnoDao catTipoAlumnoDao;
	
	@Autowired
	private CatPaisDao catPaisDao;
	
	@Autowired
	private CatEstadoDao catEstadoDao;	
	
	
	@RequestMapping("/inscritos/inscritos/inscritos")
	public String inscritosInscritosInscritos(HttpServletRequest request, Model modelo){
		
		String cargas 		= "''";
		String modalidades	= "'1'";
		String cargaId 		= "0";
		String fechaIni		= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin		= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	cargas			= (String)sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : (String)sesion.getAttribute("cargas");
        	modalidades		= (String)sesion.getAttribute("modalidades") == null ? "'1'" : (String)sesion.getAttribute("modalidades");
        	cargaId 		= (String)sesion.getAttribute("cargaId");
        	
        	if (fechaIni.equals("0")) fechaIni = (String) sesion.getAttribute("fechaIni");
        	if (fechaFin.equals("0")) fechaFin = (String) sesion.getAttribute("fechaFin");        	
        }
        
        HashMap<String,CatModalidad> mapaModalidades = catModalidadDao.getMapAll("");
       
        String periodoId 	= catPeriodoDao.getPeriodo();        
        if (accion.equals("1")){		
    		sesion.setAttribute("fechaIni", fechaIni);
    		sesion.setAttribute("fechaFin", fechaFin);
    	} 
        
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("modalidades", modalidades);
        modelo.addAttribute("cargaId", cargaId);
        modelo.addAttribute("fechaIni", fechaIni);
        modelo.addAttribute("fechaFin", fechaFin);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("periodoId", periodoId);
        modelo.addAttribute("mapaModalidades", mapaModalidades);
        
		return "inscritos/inscritos/inscritos";
	}
	
	@RequestMapping("/inscritos/inscritos/cargas")
	public String inscritosInscritosCargas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";	 
		String cargas			= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 		= (String) sesion.getAttribute("codigoPersonal");	
        	cargas				= (String) sesion.getAttribute("cargas");
        }
        
        List<Carga> lisCarga	= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
    	List<Carga> lisActivas	= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
    	
    	 HashMap<String,String> mapaFechaIni = estadisticaDao.mapaFechaInicialCargas();
    	 HashMap<String,String> mapaFechaFin = estadisticaDao.mapaFechaFinalCargas();
    	
    	if (accion.equals("1")){
    		cargas = "";
     		for(Carga carga : lisCarga){
    			if(request.getParameter(carga.getCargaId()) != null ){
    				if(cargas.equals(""))
    					cargas = "'"+carga.getCargaId()+"'";
    				else
    					cargas += ",'"+carga.getCargaId()+"'";					
    			}
    		}
    		sesion.setAttribute("cargas", cargas);
    	}	
		
        modelo.addAttribute("codigoPersonal", codigoPersonal);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("fInscripcion", fInscripcion);
        modelo.addAttribute("lisCarga", lisCarga);
        modelo.addAttribute("lisActivas", lisActivas);
        modelo.addAttribute("mapaFechaIni", mapaFechaIni);
        modelo.addAttribute("mapaFechaFin", mapaFechaFin);

        return "inscritos/inscritos/cargas";
	}
	
	@RequestMapping("/inscritos/inscritos/modalidades")
	public String inscritosInscritosModalidaes(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";	 
		String cargas			= "";
		String modalidades		= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");	
        	cargas			= (String) sesion.getAttribute("cargas");
        	modalidades		= (String) sesion.getAttribute("modalidades");
        }
        
        List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
    	
        if (accion.equals("1")){
    		modalidades = "";
    		for(CatModalidad modalidad : lisModalidad){
    			if(request.getParameter(modalidad.getModalidadId()) != null ){			
    				if(modalidades.equals(""))
    					modalidades = "'"+modalidad.getModalidadId()+"'";
    				else
    					modalidades += ",'"+modalidad.getModalidadId()+"'";						
    			}
    		}
    		sesion.setAttribute("modalidades", modalidades);	
    	}		
        
        HashMap<String,String> mapa = estadisticaDao.mapaModalidadesEnCargas(cargas);
		
        modelo.addAttribute("codigoPersonal", codigoPersonal);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("modalidades", modalidades);
        modelo.addAttribute("lisModalidad", lisModalidad);
        modelo.addAttribute("mapa", mapa);

		return "inscritos/inscritos/modalidades";
	}
	
	@RequestMapping("/inscritos/inscritos/reporte1")
	public String inscritosInscritosReporte1(HttpServletRequest request, Model modelo){
		
		String cargas 			= request.getParameter("cargas")==null?"''":request.getParameter("cargas");
		String modalidades 		= request.getParameter("modalidades")==null?"''":request.getParameter("modalidades");
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		List<EstInscritos> lisEstInscritos 			= estInscritosDao.getListAll("WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD IN ("+modalidades+") AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') ORDER BY 2 ");
		HashMap<String, AlumPersonal> mapAlumnos	= alumPersonalDao.getMapInscritos();
		HashMap<String, CatTipoAlumno> mapaTipoAlumno 	= catTipoAlumnoDao.getMapAll("ORDER BY NOMBRE_TIPO");
		HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("ORDER BY NOMBRE_MODALIDAD");
		HashMap<String, CatPais> mapaNacionalidades = catPaisDao.getMapAll();
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("lisEstInscritos", lisEstInscritos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapaTipoAlumno", mapaTipoAlumno);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaNacionalidades", mapaNacionalidades);

		return "inscritos/inscritos/reporte1";
	}
	
	@RequestMapping("/inscritos/inscritos/reporte2")
	public String inscritosInscritosReporte2(HttpServletRequest request, Model modelo){
		
		String cargas 			= request.getParameter("cargas")==null?"''":request.getParameter("cargas");
		String modalidades 		= request.getParameter("modalidades")==null?"''":request.getParameter("modalidades");
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");
		
		List<Inscritos> listaInscritos = inscritosDao.getListaReporte2(" WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+") AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') ORDER BY 2");
		
		HashMap<String,CatFacultad> mapaFacultades 		= catFacultadDao.getMapFacultad("ORDER BY NOMBRE_FACULTAD");
		HashMap<String,CatCarrera> mapaCarreras 		= catCarreraDao.getMapAll("ORDER BY NOMBRE_CARRERA");
		HashMap<String, CatReligion> mapaReligiones 	= catReligionDao.getMapAll("ORDER BY NOMBRE_RELIGION");
		HashMap<String, CatTipoAlumno> mapaTipoAlumno 	= catTipoAlumnoDao.getMapAll("ORDER BY NOMBRE_TIPO");
		HashMap<String, CatModalidad> mapaModalidades 	= catModalidadDao.getMapAll("ORDER BY NOMBRE_MODALIDAD");
		HashMap<String, CatPais> mapaPaises 			= catPaisDao.getMapAll();
		HashMap<String, CatEstado> mapaEstados 			= catEstadoDao.getMapAll();
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("listaInscritos", listaInscritos);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaReligiones", mapaReligiones);
		modelo.addAttribute("mapaTipoAlumno", mapaTipoAlumno);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
		modelo.addAttribute("mapaPaises", mapaPaises);
		modelo.addAttribute("mapaEstados", mapaEstados);

		return "inscritos/inscritos/reporte2";
	}

}
