package aca.web.rendimiento;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
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
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContRendimientoNotas {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	EstadisticaDao estadisticaDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@RequestMapping("/rendimiento/notas/reporte")
	public String rendimientoNotasReporte(HttpServletRequest request, Model modelo){		
		
		String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId	= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		
		List<Carga> lisCargas 			= cargaDao.getListAllActivas(" ORDER BY NOMBRE_CARGA DESC");
		if (cargaId.equals("0") && lisCargas.size() >= 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		List<CatCarrera> lisCarreras 	= catCarreraDao.getListCarga(cargaId, " ORDER BY NOMBRE_CARRERA");	
		if (carreraId.equals("0") && lisCarreras.size() >=1) {
			carreraId = lisCarreras.get(0).getCarreraId();
		}
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("carreraId", carreraId);
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisCarreras", lisCarreras);
		
		return "rendimiento/notas/reporte";
	}
	
	@RequestMapping("/rendimiento/notas/alumnos")
	public String rendimientoNotasAlumnos(HttpServletRequest request, Model modelo){		
		
		String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
		String nombreCarga 		= cargaDao.getNombreCarga(cargaId);
		String nombreCarrera	= catCarreraDao.getNombreCarrera(carreraId);
		
		List<Estadistica> listaEstadistica 		 = estadisticaDao.getListInscritosCargaCarrera(cargaId, carreraId, " ORDER BY ENOC.ALUMNO_CICLO_HIST(CODIGO_PERSONAL, CARGA_ID, PLAN_ID), APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		List<CargaAcademica> lisMaterias		 = cargaAcademicaDao.getListaCargaCarrera(cargaId, carreraId," ORDER BY CICLO");
		HashMap<String, AlumnoCurso> mapaNotas 	 = alumnoCursoDao.mapaNotas(cargaId, carreraId);
		
		modelo.addAttribute("nombreCarga", nombreCarga);
		modelo.addAttribute("nombreCarrera", nombreCarrera);
		modelo.addAttribute("listaEstadistica", listaEstadistica);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("mapaNotas", mapaNotas);
		
		return "rendimiento/notas/alumnos";
	}
	
}