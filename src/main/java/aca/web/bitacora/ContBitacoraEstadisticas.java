package aca.web.bitacora;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.archivo.spring.ArchGruposCarrera;
import aca.archivo.spring.ArchStatus;
import aca.bitacora.spring.BitArea;
import aca.bitacora.spring.BitEstado;
import aca.bitacora.spring.BitTramite;
import aca.bitacora.spring.BitTramiteAlumno;

@Controller
public class ContBitacoraEstadisticas {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.bitacora.spring.BitTramiteAlumnoDao bitTramiteAlumnoDao;
	
	@Autowired
	aca.bitacora.spring.BitAreaDao bitAreaDao;
	
	@Autowired
	aca.bitacora.spring.BitTramiteDao bitTramiteDao;
	
	@Autowired
	aca.bitacora.spring.BitEtiquetaDao bitEtiquetaDao;
	
	@Autowired
	aca.bitacora.spring.BitEstadoDao bitEstadoDao;
	
	@Autowired
	aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired
	aca.archivo.spring.ArchGruposCarreraDao archGruposCarreraDao;
	
	@RequestMapping("/bitacora/estadistica/estadistica")
	public String bitacoraEstadisticaEstadistica(HttpServletRequest request, Model modelo){	
			
		return "bitacora/estadistica/estadistica";
	}	
	

	@RequestMapping("/bitacora/estadistica/tramitesArea")
	public String bitacoraEstadisticaTramitesArea(HttpServletRequest request, Model modelo){
		
		String areaId			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String estadoId  		= request.getParameter("EstadoId")==null?"0":request.getParameter("EstadoId");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
		String hoy 				= aca.util.Fecha.getHoy();
		
		List<BitEstado> lisEstados 		 = bitEstadoDao.lisEstados("ORDER BY ESTADO");
		List<BitArea> lisAreas			= bitAreaDao.lisAreas("ORDER BY AREA_ID");
		List<BitTramite> lisTramites 	= bitTramiteDao.lisTramites(areaId, "ORDER BY TRAMITE_ID");
		List<BitTramiteAlumno> lisTramitesAlumnos = bitTramiteAlumnoDao.lisTramiteFiltros(areaId, tramiteId, estadoId, fechaInicio, fechaFinal, "ORDER BY ENOC.BIT_TRAMITE_ALUMNO.FECHA_INICIO");
		
		HashMap<String,BitTramite> mapaTramite = bitTramiteDao.mapTramites();
		HashMap<String,String> mapaEstado = bitEstadoDao.mapEstados();
	//	HashMap<String,String> mapaNombreUsuario	= usuariosDao.mapaUsuarios();
		
		
		modelo.addAttribute("areaId", areaId);
		modelo.addAttribute("tramiteId", tramiteId);
		modelo.addAttribute("estadoId", estadoId);
		modelo.addAttribute("year", year);
		modelo.addAttribute("fechaInicio", fechaInicio);
		modelo.addAttribute("fechaFinal", fechaFinal);
		modelo.addAttribute("hoy", hoy);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisAreas", lisAreas);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("lisTramitesAlumnos", lisTramitesAlumnos);
		modelo.addAttribute("mapaTramite", mapaTramite);
		modelo.addAttribute("mapaEstado", mapaEstado);
	//modelo.addAttribute("mapaNombreUsuario", mapaNombreUsuario);
		
		return "bitacora/estadistica/tramitesArea";
	}
	
	@RequestMapping("/bitacora/estadistica/tramites")
	public String bitacoraEstadisticaTramites(HttpServletRequest request, Model modelo){	
		
		String areaId			= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
		String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
		String year 			= aca.util.Fecha.getHoy().substring(6, 10);
		String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
		String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
		String hoy 				= aca.util.Fecha.getHoy();
		
		List<BitTramite> lisTramites = bitTramiteDao.lisTramites(areaId, "ORDER BY TRAMITE_ID");
		List<BitArea> lisAreas		 = bitAreaDao.lisAreas("ORDER BY AREA_ID");
		List<BitEstado> lisEstados	= bitEstadoDao.lisEstados("2,3,4,5,6"," ORDER BY ESTADO");
		
		HashMap<String,String> mapaTramiteEstado 	= bitTramiteAlumnoDao.mapTramitesPorEstado(fechaInicio, fechaFinal);
	
		modelo.addAttribute("areaId", areaId);
		modelo.addAttribute("tramiteId", tramiteId);
		modelo.addAttribute("year", year);
		modelo.addAttribute("fechaInicio", fechaInicio);
		modelo.addAttribute("fechaFinal", fechaFinal);
		modelo.addAttribute("hoy", hoy);
		modelo.addAttribute("lisTramites", lisTramites);
		modelo.addAttribute("lisAreas", lisAreas);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("mapaTramiteEstado", mapaTramiteEstado);
		
		return "bitacora/estadistica/tramites";
	}	
	
	@RequestMapping("/bitacora/estadistica/analisis")
	public String bitacoraEstadisticaAnalisis(HttpServletRequest request, Model modelo){	
		return "bitacora/estadistica/analisis";
	}	
	
}