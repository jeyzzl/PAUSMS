package aca.web.rendimiento;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.Carga;
import aca.carga.spring.CargaBloque;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContRendimientoBoletas {
	
	
	@Autowired	
	private CargaDao cargaDao;
	
	@Autowired	
	private CatCarreraDao catCarreraDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	
	@RequestMapping("/rendimiento/boletas/boletaPdf")
	public String rendimientoBoletasBoletaPdf(HttpServletRequest request, Model modelo){
		
		String cargaId 					= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId 				= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String rangoIni 				= request.getParameter("RangoIni")==null?"0000000":request.getParameter("RangoIni");
		String rangoFin 				= request.getParameter("RangoFin")==null?"9999999":request.getParameter("RangoFin");
		
		List<Estadistica> lisAlumnos 				= estadisticaDao.lisEnBoletas(cargaId, carreraId, rangoIni, rangoFin, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");		
		List<AlumnoCurso> lisMaterias 				= alumnoCursoDao.lisPorCargayCarrera(cargaId, carreraId, "ORDER BY CODIGO_PERSONAL, NOMBRE_CURSO");
		HashMap<String, CargaBloque> mapaBloques 	= cargaBloqueDao.mapaBloques();
		
		modelo.addAttribute("lisAlumnos", lisAlumnos);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaBloques", mapaBloques);
		
		return "rendimiento/boletas/boletaPdf";
	}	
	
	@RequestMapping("/rendimiento/boletas/rangos")
	public String rendimientoBoletasRangos(HttpServletRequest request, Model modelo){	
		
		String cargaId 					= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId 				= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String rangoIni 				= request.getParameter("RangoIni")==null?"0000000":request.getParameter("RangoIni");
		String rangoFin 				= request.getParameter("RangoFin")==null?"9999999":request.getParameter("RangoFin");
		
		List<Carga> lisCargas 			= cargaDao.lisTodas(" ORDER BY PERIODO DESC, PRIORIDAD, CARGA_ID");
		if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListaEnCarga(cargaId," ORDER BY NOMBRE_CARRERA");
		if (carreraId.equals("0") && lisCarreras.size() >= 1) {
			carreraId = lisCarreras.get(0).getCarreraId();
		}
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("rangoIni", rangoIni);
		modelo.addAttribute("rangoFin", rangoFin);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);
		
		return "rendimiento/boletas/rangos";
	}
}