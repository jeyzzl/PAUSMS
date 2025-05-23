package aca.web.matricula;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaEnLinea;
import aca.carga.spring.CargaEnLineaDao;
import aca.carga.spring.CargaPracticaAlumno;
import aca.carga.spring.CargaPracticaAlumnoDao;
import aca.kardex.spring.KrdxCursoActDao;

@Controller
public class ContMatriculaPracticas {	
	
	@Autowired
	private CargaEnLineaDao cargaEnLineaDao;
	
	@Autowired
	private KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	private CargaPracticaAlumnoDao cargaPracticaAlumnoDao;	
	
	
	@RequestMapping("/matricula/practicas/listado")
	public String matriculaAlumnoInscripcion(HttpServletRequest request, Model modelo){
		
		String cargaId = request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
		
		List<CargaEnLinea> cargas = cargaEnLineaDao.getListCargasCartas("S", "");
		
		List<aca.Mapa> lisAlumnosCarga = krdxCursoActDao.lisAlumnosCarga(cargaId);
		
		HashMap<String,CargaPracticaAlumno> mapaPracticaAlumnos = cargaPracticaAlumnoDao.mapaPracticaAlumnoEnCarga(cargaId);
		
		HashMap<String,List<aca.Mapa>> mapaFechas =  cargaPracticaAlumnoDao.mapaFechasPorAlumno(cargaId); 
		
		modelo.addAttribute("cargas", cargas);
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("lisAlumnosCarga", lisAlumnosCarga);
		modelo.addAttribute("mapaPracticaAlumnos", mapaPracticaAlumnos);
		modelo.addAttribute("mapaFechas", mapaFechas);
		
		return "matricula/practicas/listado";
	}
	
}