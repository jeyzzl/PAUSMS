package aca.web.inscritos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.matricula.spring.MatriculaDao;
import aca.parametros.spring.Parametros;
import aca.parametros.spring.ParametrosDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContInscritosTabla {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private MatriculaDao matriculaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;

	@Autowired
	private ParametrosDao parametrosDao;
	
	
	@RequestMapping("/inscritos/tabla/bestadistica")
	public String inscritosTablaBestAdistica(HttpServletRequest request, Model modelo){
		
		String cargaId			= "0";
		String cargas			= "''";
		String modalidades		= "'1'";
		String fechaIni			= request.getParameter("FechaIni")==null?"0":request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?"0":request.getParameter("FechaFin");		
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
		Parametros parametros = parametrosDao.mapeaRegId("1");

		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	cargas		= (String)sesion.getAttribute("cargas") == null ? cargaDao.getCargasActivas(aca.util.Fecha.getHoy()) : (String)sesion.getAttribute("cargas");
        	modalidades	= (String)sesion.getAttribute("modalidades") == null ? "'1'" : (String)sesion.getAttribute("modalidades");
        	
        	cargaId = (String)sesion.getAttribute("cargaId");
        	
        	if (fechaIni.equals("0")) fechaIni = (String) sesion.getAttribute("fechaIni");
        	if (fechaFin.equals("0")) fechaFin = (String) sesion.getAttribute("fechaFin");        	
        }
        
        if (accion.equals("1")){		
    		sesion.setAttribute("fechaIni", fechaIni);
    		sesion.setAttribute("fechaFin", fechaFin);
    	} 
        
        List<String> lista = matriculaDao.InscritosPorCargas(cargas, modalidades, fechaIni, fechaFin, "ORDER BY FACULTAD_ID, CARRERA_ID");
        
        HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) catModalidadDao.getMapAll(""); 
        HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) catFacultadDao.getMapFacultad("");
        HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) catCarreraDao.getMapAll("");
        
		modelo.addAttribute("parametros", parametros);
        modelo.addAttribute("cargas", cargas);
        modelo.addAttribute("modalidades", modalidades);
        modelo.addAttribute("carga", cargaDao.mapeaRegId(cargaId));
        modelo.addAttribute("fechaIni", fechaIni);
        modelo.addAttribute("fechaFin", fechaFin);
        modelo.addAttribute("accion", accion);
        modelo.addAttribute("lista", lista);
        modelo.addAttribute("mapaModalidades", mapaModalidades);
        modelo.addAttribute("mapaFacultades", mapaFacultades);
        modelo.addAttribute("mapaCarreras", mapaCarreras);
        
		return "inscritos/tabla/bestadistica";
	}
	
	@RequestMapping("/inscritos/tabla/cargas")
	public String inscritosTablaCargas(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 	= "0";	 
		String cargas			= "";
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String fInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		HttpSession sesion = ((HttpServletRequest)request).getSession();
        if (sesion != null){
        	codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");	
        	cargas			= (String) sesion.getAttribute("cargas");
        }
        
        List<Carga> lisCarga		= cargaDao.getListPorFecha(fInscripcion, "ORDER BY CARGA_ID");
    	List<Carga> lisActivas		= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
    	
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

        return "inscritos/tabla/cargas";
	}

	@RequestMapping("/inscritos/tabla/modalidades")
	public String inscritosTablaModalidaes(HttpServletRequest request, Model modelo){
		
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

		return "inscritos/tabla/modalidades";
	}

}
