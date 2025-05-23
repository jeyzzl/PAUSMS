package aca.web.inscritos;

import java.util.ArrayList;
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

import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.parametros.spring.ParametrosDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContInscritosEstadistica{
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired
	private CatFacultadDao catFacultadDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired
	private ParametrosDao parametrosDao;
	
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
	
	@RequestMapping("/inscritos/estadistica/cargas")
	public String inscritosEstadisticasCargas(HttpServletRequest request, Model modelo){
		
		String fechaInscripcion		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
		
		List<Carga> lisCargas		= cargaDao.getListPorFecha(fechaInscripcion, "ORDER BY CARGA_ID");
		List<Carga> lisActivas		= cargaDao.getListCargasActivas(" ORDER BY CARGA_ID");
		
		HashMap<String,String> mapaPrimerDia 	= cargaDao.mapaPrimerDia();
		HashMap<String,String> mapaUltimoDia 	= cargaDao.mapaPrimerDia();
				
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisActivas", lisActivas);
		modelo.addAttribute("mapaPrimerDia", mapaPrimerDia);
		modelo.addAttribute("mapaUltimoDia", mapaUltimoDia);
		
		return "inscritos/estadistica/cargas";
	}
	
	@RequestMapping("/inscritos/estadistica/modalidades")
	public String inscritosEstadisticaModalidades(HttpServletRequest request, Model modelo){
		
		String cargas			= "0";
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();            
	    if (sesion != null){
	    	cargas = sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    }			
		
		List<CatModalidad> lisModalidades		= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		HashMap<String,String> mapaModalidades 	= estadisticaDao.mapaModalidadesEnCargas(cargas);
		
		modelo.addAttribute("lisModalidades", lisModalidades);
		modelo.addAttribute("mapaModalidades", mapaModalidades);
				
		return "inscritos/estadistica/modalidades";
	}
	
	@RequestMapping("/inscritos/estadistica/facEstadistica")
	public String inscritosEstadisticaFacEstadistica(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContinscritosEstadisticaFacEstadistica|inscritosEstadisticaFacEstadistica:");
				
		return "inscritos/estadistica/facEstadistica";
	}
	
	@RequestMapping("/inscritos/estadistica/facinscritos")
	public String inscritosEstadisticaFacInscritos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContinscritosEstadisticaFacInscritos|inscritosEstadisticaFacInscritos:");				
		return "inscritos/estadistica/facinscritos";
	}
	@RequestMapping("/inscritos/estadistica/inscritos")
	public String inscritosEstadisticaInscritos(HttpServletRequest request, Model modelo){
//		enviarConEnoc(request,"Error-aca.ContinscritosCiclosInscritos|inscritosEstadisticaInscritos:");
		
		String cargas 		= "0";
		String modalidades 	= "0";
		String nacionalidad = "0";
		
		double pInt=0,pH=0,pMex=0,pAcfe=0,PCV=0;
		
		HttpSession sesion 		= ((HttpServletRequest)request).getSession();  
	    if (sesion != null){
	    	cargas 		= sesion.getAttribute("cargas")==null?"0":sesion.getAttribute("cargas").toString();
	    	modalidades = sesion.getAttribute("modalidades")==null?"0":sesion.getAttribute("modalidades").toString();
	    }
	    
	    if(parametrosDao.existeReg("1")) {
	    	nacionalidad = parametrosDao.mapeaRegId("1").getPaisId();
	    }
	    
	    String muestraGra	= request.getParameter("Muestra")==null?"0":request.getParameter("Muestra");
	    
		String fechaIni 	= request.getParameter("FechaIni")==null?(String) sesion.getAttribute("fechaIni"):request.getParameter("FechaIni");
		String fechaFin		= request.getParameter("FechaFin")==null?(String) sesion.getAttribute("fechaFin"):request.getParameter("FechaFin");
		
		if (request.getParameter("FechaIni")!=null) sesion.setAttribute("fechaIni", request.getParameter("FechaIni"));
		if (request.getParameter("FechaFin")!=null) sesion.setAttribute("fechaFin", request.getParameter("FechaFin"));
		
		if ((String)sesion.getAttribute("cargas") == null){
			sesion.setAttribute("cargas", cargaDao.getCargasActivas(aca.util.Fecha.getHora()));
		}
		
		if(cargas.equals(""))cargas="''";
		
		List<CatModalidad> lisModalidad 		= catModalidadDao.getListInscCargas(cargas, modalidades, "ORDER BY NOMBRE_MODALIDAD DESC");
		List<CatFacultad> lisFacultades			= catFacultadDao.getListAll(" ORDER BY 1");
		HashMap<String, String> lisFacInsc 		= catFacultadDao.getAlumnosPorFacultad(cargas, modalidades, fechaIni, fechaFin);
		List<Carga> lisCargas					= cargaDao.getListCargas(cargas);
		
		String lisModo[] = modalidades.replace("'", "").split(",");
		String nombresModalidades[] = new String[lisModo.length];;
		
		for(int i = 0; i < lisModo.length; i++) {
			 String mod = lisModo[i];
			    String nombreModalidad = catModalidadDao.getNombreModalidad(mod);
			    nombresModalidades[i] = nombreModalidad;
		}
		
		int total[] 	= estadisticaDao.estadisticaPorCarga(cargas, modalidades, fechaIni, fechaFin, nacionalidad);
		int TI 			= estadisticaDao.totalInscritosPorCarga(cargas, modalidades, fechaIni, fechaFin);	
		int TCM			= estadisticaDao.totalCargaMateriasPorCarga(cargas, modalidades, fechaIni, fechaFin);
		int TCC			= estadisticaDao.totalCalculoCorbroPorCarga(cargas, modalidades, fechaIni, fechaFin);
//		int TCC 		= Matricula.totalCCobro(conEnoc,cargas, modalidades);
//		int TCM			= Matricula.totalCarga(conEnoc,cargas, modalidades);
		
		if (total[0]+total[1]>0) pInt 	= Double.valueOf( (double) total[0]*100 / (double)(total[0]+total[1]) ); else pInt 	= 0;
		if (total[2]+total[3]>0) pH  	= Double.valueOf( (double) total[2]*100 / (double)(total[2]+total[3]) ); else pH	= 0;
		if (total[4]+total[5]>0) pMex 	= Double.valueOf( (double) total[4]*100 / (double)(total[4]+total[5]) ); else pMex	= 0;
		if (total[6]+total[7]>0) pAcfe	= Double.valueOf( (double) total[6]*100 / (double)(total[6]+total[7]) ); else pAcfe	= 0;
		
		if ( TI>0 ) PCV	= Double.valueOf( (double) total[8] /(double)TI); else PCV = 0;	
		
		HashMap<String, String> mapCarga = estadisticaDao.mapaTotalInscritosPorCarga(cargas, modalidades, fechaIni, fechaFin, "");
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("pInt", pInt);
		modelo.addAttribute("pH", pH);
		modelo.addAttribute("pMex", pMex);
		modelo.addAttribute("pAcfe", pAcfe);
		modelo.addAttribute("PCV", PCV);
		modelo.addAttribute("total", total);
		modelo.addAttribute("TI", TI);
		modelo.addAttribute("TCM", TCM);
		modelo.addAttribute("TCC", TCC);
		modelo.addAttribute("muestraGra", muestraGra);
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("lisFacInsc", lisFacInsc);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("nombresModalidades", nombresModalidades);
		
		modelo.addAttribute("mapCarga", mapCarga);
				
		return "inscritos/estadistica/inscritos";
	}
	
	
}
