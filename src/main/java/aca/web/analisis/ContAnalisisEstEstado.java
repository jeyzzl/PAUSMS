package aca.web.analisis;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContAnalisisEstEstado {
	
	
	@Autowired	
	private CatPeriodoDao catPeriodoDao;
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatEstadoDao catEstadoDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired	
	private EstadisticaDao estadisticaDao;
	
	@Autowired	
	private CatModalidadDao catModalidadDao;
	
	@Autowired	
	private CatFacultadDao catFacultadDao;
	
	
	@RequestMapping("/analisis/est_estado/modalidades")
	public String analisisEstEstadoModalidades(HttpServletRequest request, Model modelo){
		
		String modalidades				= "0";
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			modalidades 	= sesion.getAttribute("modalidades") == null?"'1'":sesion.getAttribute("modalidades").toString();
		}
		
		List<CatModalidad> lisModalidad	= catModalidadDao.getListAll("ORDER BY MODALIDAD_ID");
		
		modelo.addAttribute("lisModalidad", lisModalidad);
		modelo.addAttribute("modalidades", modalidades);		
		
		return "analisis/est_estado/modalidades";
	}	

	@RequestMapping("/analisis/est_estado/reporte")
	public String analisisEstEstadoReporte(HttpServletRequest request, Model modelo){
		
		String modalidades			= "0";
		String periodoId 			= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
		String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion 		= ((HttpServletRequest) request).getSession();
		if (sesion != null) {
			modalidades = sesion.getAttribute("modalidades") == null?"'1'":sesion.getAttribute("modalidades").toString();
		}
		
		// Lista de periodos
		List<CatPeriodo> listaPeriodos 		= catPeriodoDao.getListAll("ORDER BY PERIODO_ID DESC");
		
		if (periodoId.equals("0") && listaPeriodos.size() >= 1) {
			periodoId = listaPeriodos.get(0).getPeriodoId();
		}
		// Lista de cargas
		List<Carga> lisCarga 				= cargaDao.getListAll("WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID"); 
		
		// Lista de estados de México
		List<CatEstado> lisEstados 			= catEstadoDao.getLista("91", "ORDER BY NOMBRE_ESTADO");
		
		// Lista de carreras
		List<CatCarrera> lisCarreras 		= catCarreraDao.getListCarga(cargaId, "ORDER BY FACULTAD_ID, NIVEL_ID, NOMBRE_CARRERA");
		
		// Cuenta los alumnos inscritos en una carrera y estado
		HashMap<String,String> mapTotCarrera 		= estadisticaDao.mapaInscEstadoyCarrera(cargaId, modalidades, "I");
		
		// Cuenta los alumnos inscritos en un estado
		HashMap<String,String> mapTotEstado 		= estadisticaDao.mapaInscEstado(cargaId, modalidades, "I");
		
		HashMap<String, String> mapNombreModalidad		 	= catModalidadDao.mapModalidades("");
		HashMap<String, CatFacultad> mapNombreFacultad 		= catFacultadDao.getMapFacultad(""); 
				
		modelo.addAttribute("modalidades", modalidades);
		modelo.addAttribute("listaPeriodos", listaPeriodos);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("lisEstados", lisEstados);
		modelo.addAttribute("lisCarreras", lisCarreras);
		modelo.addAttribute("mapTotCarrera", mapTotCarrera);
		modelo.addAttribute("mapTotEstado", mapTotEstado);
		modelo.addAttribute("mapNombreModalidad", mapNombreModalidad);
		modelo.addAttribute("mapNombreFacultad", mapNombreFacultad);
		
		return "analisis/est_estado/reporte";
	}	
}
