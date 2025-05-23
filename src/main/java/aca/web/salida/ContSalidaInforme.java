package aca.web.salida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.salida.spring.SalAuto;
import aca.salida.spring.SalBitacora;
import aca.salida.spring.SalClub;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumEstado;
import aca.alumno.spring.AlumEstadoDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;
import aca.salida.spring.SalAlumno;
import aca.salida.spring.SalConsejero;
import aca.salida.spring.SalSolicitud;
import aca.salida.spring.SalSolicitudDao;
import aca.salida.spring.SalGrupo;
import aca.salida.spring.SalGrupoDao;
import aca.salida.spring.SalInforme;
import aca.salida.spring.SalInformeDao;
import aca.salida.spring.SalInvitado;
import aca.vista.spring.Maestros;
import aca.vista.spring.UsuariosDao;

@Controller
public class ContSalidaInforme {
	
	@Autowired
	SalSolicitudDao salSolicitudDao;	
	
	@Autowired
	aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	CatPaisDao catPaisDao;
	
	@Autowired
	CatEstadoDao catEstadoDao;	
	
	@Autowired
	UsuariosDao usuariosDao;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumPlanDao alumPlanDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;

	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	SalInformeDao salInformeDao;	
	
	@Autowired
	SalGrupoDao salGrupoDao;
	
	@RequestMapping("/salida/informe/listado")
	public String salidaInformeListado(HttpServletRequest request, Model modelo){
		
		String fechaIni			= request.getParameter("FechaIni") == null ? "01/01/"+aca.util.Fecha.getHoy().substring(6,10) : request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin") == null ? "31/12/"+aca.util.Fecha.getHoy().substring(6,10) : request.getParameter("FechaFin");
		
		List<SalInforme> lisInformes 	= salInformeDao.lisPorFechas(fechaIni, fechaFin, " ORDER BY ENOC.SAL_INFORME.FECHA DESC");
		
		HashMap<String,String> mapaMaestros 			= maestrosDao.mapaMaestroCorto("NOMBRE");
		HashMap<String,SalSolicitud> mapaSolicitudes	= salSolicitudDao.mapaConInforme();
		HashMap<String,SalGrupo> mapaGrupos				= salGrupoDao.mapaTodos();
		
		modelo.addAttribute("fechaIni", fechaIni);
		modelo.addAttribute("fechaFin", fechaFin);
		modelo.addAttribute("lisInformes", lisInformes);
		modelo.addAttribute("mapaMaestros", mapaMaestros);		
		modelo.addAttribute("mapaSolicitudes", mapaSolicitudes);
		modelo.addAttribute("mapaGrupos", mapaGrupos);
		
		return "salida/informe/listado";
	}
	
}