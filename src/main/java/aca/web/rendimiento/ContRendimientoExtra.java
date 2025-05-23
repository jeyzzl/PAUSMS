package aca.web.rendimiento;

import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatTipoCal;
import aca.catalogo.spring.CatTipoCalDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.CargaAcademica;
import aca.vista.spring.CargaAcademicaDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContRendimientoExtra {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	

	@Autowired
	CargaDao cargaDao;	
	
	@Autowired
	CatTipoCalDao catTipoCalDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	CargaAcademicaDao cargaAcademicaDao;
	
	@Autowired
	AlumnoCursoDao alumnoCursoDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	
	@RequestMapping("/rendimiento/extra/extra")
	public String rendimientoNotasReporte(HttpServletRequest request, Model modelo){		
		
		String cargaId 			= request.getParameter("cargaId")==null?("0"):request.getParameter("cargaId");
		
		List<Carga> listaCarga 						= cargaDao.getListAll("ORDER BY CARGA_ID DESC"); 
		if(cargaId.equals("0") && listaCarga.size() >= 1) {
			cargaId = listaCarga.get(0).getCargaId();
		}
		List<AlumnoCurso> listaAlumnos				= alumnoCursoDao.getListCargaconExtra(cargaId, "ORDER BY CARRERA_ID");
		HashMap<String,String> mapaTipo 			= catTipoCalDao.mapTipoCal();
		HashMap<String,CatCarrera> mapaCarreras 	= catCarreraDao.mapaCarreras(); 
		HashMap<String,AlumPersonal> mapaAlumnos	= alumPersonalDao.mapAlumnosEnCargas("'"+cargaId+"'");
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("listaAlumnos", listaAlumnos);
		modelo.addAttribute("listaCarga", listaCarga);
		modelo.addAttribute("mapaTipo", mapaTipo);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		
		return "rendimiento/extra/extra";
	}
	
}