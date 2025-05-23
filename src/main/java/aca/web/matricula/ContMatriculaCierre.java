package aca.web.matricula;

import java.util.ArrayList;
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

import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatModalidadDao;
import aca.financiero.spring.FesCcobro;
import aca.financiero.spring.FesCcobroDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.AlumnoCursoDao;

@Controller
public class ContMatriculaCierre {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	AlumPersonalDao alumPersonalDao;
	
	@Autowired
	AlumAcademicoDao alumAcademicoDao;
	
	@Autowired
	CargaDao cargaDao;
	
	@Autowired
	CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	AccesoDao accesoDao;
	
	@Autowired
	KrdxCursoActDao krdxCursoActDao;
	
	@Autowired
	CargaBloqueDao cargaBloqueDao;
	
	@Autowired
	MapaPlanDao mapaPlanDao;
	
	@Autowired
	CatFacultadDao catFacultadDao;
	
	@Autowired
	CatCarreraDao catCarreraDao;
	
	@Autowired
	FesCcobroDao fesCcobroDao;
	
	@Autowired
	CatModalidadDao catModalidadDao;
	
	@Autowired
	private AlumnoCursoDao alumnoCursoDao;
	
	@RequestMapping("/matricula/cierre/listado")
	public String matriculaCierreListado(HttpServletRequest request, Model modelo){
		
		String cargaId 		= "0";
		String bloques 		= "'1'";
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			bloques = (String)sesion.getAttribute("bloques");
			cargaId = (String)sesion.getAttribute("cargaId");
		}
		
		List<CargaAlumno> lisMaterias  	= new ArrayList<CargaAlumno>();
		List<CatFacultad> lisFacultades =  catFacultadDao.getListAll("ORDER BY NOMBRE_FACULTAD");		
		
		List<CargaAlumno> lisCargasAlumnos 			= cargaAlumnoDao.lisCargasEnLinea(" ORDER BY ALUM_APELLIDO(CODIGO_PERSONAL)");
		HashMap<String,String> mapaPagos			= fesCcobroDao.mapaPagoInicial();
		HashMap<String,String> mapaAlumnos			= alumPersonalDao.mapaAlumnosProcesoEnLinea();
		HashMap<String,FesCcobro> mapaCobros		= fesCcobroDao.mapaAlumnosEnLinea();
		HashMap<String,MapaPlan> mapaPlanes 		= mapaPlanDao.mapPlanes("'V','I','A'");
		HashMap<String, Carga> mapaCargas	 		= cargaDao.mapaCargas();
		HashMap<String,String> mapaMaterias 		= alumnoCursoDao.mapaMateriasCierreAlumno();
		HashMap<String,AlumAcademico> mapAcademico 	= alumAcademicoDao.mapaAlumnosConCargaEnLinea();
		HashMap<String,CatModalidad> mapaModalidad 	= catModalidadDao.getMapAll("");
		HashMap<String, String> mapaTotalCreditosConfirmadas = krdxCursoActDao.mapaTotalCreditosConfirmadas();
		
		modelo.addAttribute("lisCargasAlumnos", lisCargasAlumnos);
		modelo.addAttribute("mapaPagos", mapaPagos);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaCobros", mapaCobros);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaCargas", mapaCargas);
		modelo.addAttribute("mapaMaterias", mapaMaterias);
		modelo.addAttribute("mapAcademico", mapAcademico);
		modelo.addAttribute("mapaModalidad", mapaModalidad);	
		modelo.addAttribute("mapaTotalCreditosConfirmadas", mapaTotalCreditosConfirmadas);	
		
		return "matricula/cierre/listado";
	}
	
}