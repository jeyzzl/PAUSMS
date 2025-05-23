package aca.web.matricula;

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

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAcademico;
import aca.alumno.spring.AlumAcademicoDao;
import aca.alumno.spring.AlumPersonalDao;
import aca.carga.spring.Carga;
import aca.carga.spring.CargaAlumno;
import aca.carga.spring.CargaAlumnoDao;
import aca.carga.spring.CargaBloqueDao;
import aca.carga.spring.CargaDao;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatCarreraDao;
import aca.catalogo.spring.CatFacultad;
import aca.catalogo.spring.CatFacultadDao;
import aca.kardex.spring.KrdxCursoActDao;
import aca.plan.spring.MapaPlanDao;

@Controller
public class ContMatriculaProceso {
	
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
	
	@RequestMapping("/matricula/proceso/proceso")
	public String matriculaCargaAlumno(HttpServletRequest request, Model modelo){
		
		String codigoPersonal		= "0";
		String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		
		HttpSession sesion 			= ((HttpServletRequest)request).getSession();		
		if (sesion != null){
			codigoPersonal 	= (String) sesion.getAttribute("codigoPersonal");
        }
		
		// Lista de cargas
		List<Carga> lisCargas 		= cargaDao.getCargaAlumProceso("ORDER BY ORDEN DESC");
		if (cargaId.equals("0") && lisCargas.size() > 1) {
			cargaId = lisCargas.get(0).getCargaId();
		}
		
		Acceso acceso 				= accesoDao.mapeaRegId(codigoPersonal);
		boolean esAdmin				= accesoDao.esAdministrador(codigoPersonal);
		boolean cargaActiva 		= cargaBloqueDao.CargaActiva(cargaId);
		
		// Lista de cargas
		List<CargaAlumno> lisMaterias 					= cargaAlumnoDao.lisAlumnosConMaterias(cargaId);
		
		// Lista de cargas
		List<CargaAlumno> lisCalculos 					= cargaAlumnoDao.lisAlumnosConCalculo(cargaId);
		
		HashMap<String,String> mapaPlanes				= mapaPlanDao.mapCarreraPlan();
		HashMap<String,CatFacultad> mapaFacultades		= catFacultadDao.getMapFacultad("");
		HashMap<String,CatCarrera> mapaCarreras			= catCarreraDao.getMapAll("");
		HashMap<String,String> mapaAlumnos				= alumPersonalDao.mapaAlumnosEnProceso(cargaId);
		HashMap<String,AlumAcademico> mapaAcademicos	= alumAcademicoDao.mapaAlumnosEnProceso(cargaId);
		
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("esAdmin", esAdmin);
		modelo.addAttribute("cargaActiva", cargaActiva);
		
		modelo.addAttribute("lisCargas", lisCargas);
		modelo.addAttribute("lisMaterias", lisMaterias);
		modelo.addAttribute("lisCalculos", lisCalculos);
		modelo.addAttribute("mapaPlanes", mapaPlanes);
		modelo.addAttribute("mapaFacultades", mapaFacultades);
		modelo.addAttribute("mapaCarreras", mapaCarreras);
		modelo.addAttribute("mapaAlumnos", mapaAlumnos);
		modelo.addAttribute("mapaAcademicos", mapaAcademicos);
		
		return "matricula/proceso/proceso";
	}
	
	@RequestMapping("/matricula/proceso/borrarCarga")
	public String matriculaProcesoBorrarCarga(HttpServletRequest request){
		
		String cargaId		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String mensaje 		= "0";
		
		if (krdxCursoActDao.deleteAlumnosEnProceso(cargaId)){
			mensaje = "1";
		}else {
			mensaje = "2";
		}
		
		return "redirect:/matricula/proceso/proceso?CargaId="+cargaId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/matricula/proceso/borrarAlumno")
	public String matriculaProcesoBorrarAlumno(HttpServletRequest request){
		
		String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
		String codigoAlumno		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		
		String mensaje 		= "0";
		
		if (krdxCursoActDao.deleteAlumnosEnProceso(cargaId, codigoAlumno)){
			mensaje = "1";
		}else {
			mensaje = "2";
		}
		
		return "redirect:/matricula/proceso/proceso?CargaId="+cargaId+"&Mensaje="+mensaje;
	}
}