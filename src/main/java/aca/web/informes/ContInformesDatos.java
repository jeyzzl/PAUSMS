package aca.web.informes;

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

import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
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
import aca.catalogo.spring.CatPeriodo;
import aca.catalogo.spring.CatPeriodoDao;
import aca.plan.spring.MapaCursoDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.sep.spring.SepAlumno;
import aca.sep.spring.SepAlumnoDao;
import aca.sep.spring.SepDatos;
import aca.sep.spring.SepDatosDao;
import aca.vista.spring.AlumnoCursoDao;
import aca.vista.spring.Estadistica;
import aca.vista.spring.EstadisticaDao;

@Controller
public class ContInformesDatos {
	
	
	@Autowired	
	SepDatosDao sepDatosDao;
	
	@Autowired	
	SepAlumnoDao sepAlumnoDao;
	
	
	
	@RequestMapping("/informes/datos/datos")
	public String informesDatosDatos(HttpServletRequest request, Model modelo){
		
		String fecha 		= request.getParameter("Fecha")==null?"0":request.getParameter("Fecha");		
		String filtro 		= request.getParameter("Filtro") == null ? "'ISCO2018'" : request.getParameter("Filtro");
		
		List<aca.Mapa> lisFechas	= sepAlumnoDao.listFechaSubio();
		if (fecha.equals("0") && lisFechas.size() >= 1){
			fecha = lisFechas.get(0).getLlave();
		}		
		List<SepDatos> lisDatos	= sepDatosDao.lisDatos(fecha, filtro, " ORDER BY CARRERA");
		
		modelo.addAttribute("lisFechas", lisFechas);
		modelo.addAttribute("lisDatos", lisDatos);
		
		return "informes/datos/datos";
	}
	
}