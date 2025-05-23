package aca.web.convalida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonalDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPeriodoDao;
import aca.conva.spring.ConvEvento;
import aca.conva.spring.ConvEventoDao;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContConvalidaProceso {	
	
	@Autowired
	CatPeriodoDao catPeriodoDao;
	
	@Autowired
	ConvEventoDao convEventoDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/convalida/proceso/catalogo")
	public String convalidaProcesoCatalogo(HttpServletRequest request, Model modelo){		
		
		String tipo 			= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");
		String periodoId 		= catPeriodoDao.getPeriodo();
		String carreraId		= request.getParameter("carreraId");
		String nombreCarrera    = catCarreraDao.getNombreCarrera(carreraId);
		String convalidaciones	= request.getParameter("convalidaciones")==null?"0":request.getParameter("convalidaciones");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
		String queryCarrera		= " ENOC.CARRERA(PLAN_ID) = '"+carreraId+"' ";
		if (tipo.equals("T")) tipo = "'I','E','-'"; else tipo = "'"+tipo+"'";
		
		List<ConvEvento> lisEventos = new ArrayList<ConvEvento>();
		if(!convalidaciones.equals("0")){								
			lisEventos = convEventoDao.getListPorFechaTipo(fechaInicio,fechaFinal,tipo, " AND "+queryCarrera+" AND ESTADO = '"+convalidaciones+"' ORDER BY SUBSTR(FECHA,7,4),SUBSTR(FECHA,4,2),SUBSTR(FECHA,1,2)");			
		}else{								
			lisEventos = convEventoDao.getListPorFechaTipo(fechaInicio,fechaFinal,tipo, " AND "+queryCarrera+" ORDER BY SUBSTR(FECHA,7,4),SUBSTR(FECHA,4,2),SUBSTR(FECHA,1,2)");				
		}
		
		HashMap<String,String> mapAlumnos		= alumPersonalDao.mapaAlumnoEnConvalidacion(fechaInicio, fechaFinal);		
		HashMap<String,String> mapMaterias		= convEventoDao.mapMateriasEnConvalidacion();
		HashMap<String,String> mapPorEstado		= convEventoDao.mapMateriasPorEstado();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("lisEventos", lisEventos);
		modelo.addAttribute("mapAlumnos", mapAlumnos);
		modelo.addAttribute("mapMaterias", mapMaterias);
		modelo.addAttribute("mapPorEstado", mapPorEstado);

		return "convalida/proceso/catalogo";
	}	
	
	@RequestMapping("/convalida/proceso/facultad")
	public String convalidaProcesoFacultad(HttpServletRequest request, Model modelo){	
		
		String tipo 			= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);	
		String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
		String periodoId 		= catPeriodoDao.getPeriodo();
		String mensaje 			= "-";			
		
		List<ConvEvento> lista = convEventoDao.getConvaVacio();	
		if(accion.equals("1")){		
			int success 	= 0;
			int noElimino 	= 0;			
			for(ConvEvento evento: lista){
				if(convEventoDao.deleteReg(evento.getConvalidacionId())){
					++success;
				}else{
					++noElimino;
				}
			}
			mensaje ="Se eliminaron: "+success+" errores "+noElimino;
		}
		
		List<CatFacultad> lisFacultades		= catFacultadDao.getListAll(" ORDER BY 1");
		HashMap<String,String> mapEstado 	= new java.util.HashMap<String,String>();
		if(tipo.equals("T")){
			mapEstado 		= convEventoDao.mapConvPorFacultad(fechaInicio,fechaFinal,"'-','I','E'");
		}else{
			mapEstado 		= convEventoDao.mapConvPorFacultad(fechaInicio,fechaFinal, "'"+tipo+"'");
		}
		HashMap<String,String> mapMaestros	= maestrosDao.mapaDirectores();
		
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("mensaje", mensaje);
		modelo.addAttribute("lisFacultades", lisFacultades);
		modelo.addAttribute("mapEstado", mapEstado);
		modelo.addAttribute("mapMaestros", mapMaestros);
		
		return "convalida/proceso/facultad";
	}	
	
	@RequestMapping("/convalida/proceso/listado")
	public String convalidaProcesoListado(HttpServletRequest request, Model modelo){		
		
		String tipo 			= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");
		String facultad 		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
		String periodoId 		= catPeriodoDao.getPeriodo();
		String facultadNombre 	= catFacultadDao.getNombreFacultad(facultad);
		
		List<CatCarrera> lisCarreras	= catCarreraDao.getLista(facultad,"ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), NOMBRE_CARRERA");

		HashMap<String,String> mapEstado 		= new java.util.HashMap<String,String>();
		if(tipo.equals("T")){
			mapEstado 		= convEventoDao.mapConvPorFechaTipo(fechaInicio,fechaFinal,"'-','I','E'");
		}else{
			mapEstado 		= convEventoDao.mapConvPorFechaTipo(fechaInicio,fechaFinal, "'"+tipo+"'");
		}
		
		HashMap<String,String> mapMaestros	= maestrosDao.mapaCoordinadores();
		
		modelo.addAttribute("facultadNombre", facultadNombre);
		modelo.addAttribute("periodoId", periodoId);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapEstado", mapEstado);
		modelo.addAttribute("mapMaestros", mapMaestros);
		
		return "convalida/proceso/listado";
	}	
	
}