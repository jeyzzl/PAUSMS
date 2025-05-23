package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.carga.spring.Carga;
import aca.catalogo.spring.CatCarrera;
import aca.catalogo.spring.CatModalidad;
import aca.catalogo.spring.CatTipoCal;
import aca.kardex.spring.KrdxAlumnoTitulo;
import aca.kardex.spring.KrdxAlumnoTituloDao;
import aca.vista.spring.AlumnoCurso;
import aca.vista.spring.Usuarios;

@Controller
public class ContDatosAlumnoTitulo {
	
	@Autowired
	@Qualifier("dsEnoc")	
	private DataSource enoc;
	
	@Autowired
	private aca.vista.spring.AlumnoCursoDao alumnoCursoDao;

	@Autowired
	private aca.alumno.spring.AlumPlanDao alumPlanDao;

	@Autowired
	private aca.catalogo.spring.CatCarreraDao catCarreraDao;

	@Autowired
	private aca.catalogo.spring.CatTipoCalDao catCatTipoCalDao;

	@Autowired
	private aca.catalogo.spring.CatModalidadDao catModalidadDao;
	
	@Autowired
	private aca.carga.spring.CargaDao cargaDao;

	@Autowired
	private aca.alumno.spring.AlumPersonalDao alumPersonalDao;

	@Autowired
	private aca.vista.spring.UsuariosDao usuariosDao;
	
	@Autowired
	private aca.vista.spring.MaestrosDao maestrosDao;
	
	@Autowired
	private KrdxAlumnoTituloDao krdxAlumnoTituloDao; 
	
	@Autowired
	private AccesoDao accesoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/datos_alumno/titulo/formato")
	public String datosAlumnoTituloFormato(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoTitulo|datosAlumnoTituloFormato:");
        
		return "datos_alumno/titulo/formato";
	}
	
	@RequestMapping("/datos_alumno/titulo/titulo")
	public String datosAlumnoTituloTitulo(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoTitulo|datosAlumnoTituloTitulo:");
        
		return "datos_alumno/titulo/titulo";
	}
	
	@RequestMapping("/datos_alumno/titulo/borrar")
	public String datosAlumnoTituloBorrar(HttpServletRequest request, Model modelo){
		
		String codigoPersonal 		= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
		String cursoCargaId 		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
		
		if(krdxAlumnoTituloDao.existeReg(codigoPersonal, cursoCargaId)){
			krdxAlumnoTituloDao.deleteReg(codigoPersonal, cursoCargaId);
		}
        
		return "redirect:/datos_alumno/titulo/materias";
	}
	
	@RequestMapping("/datos_alumno/titulo/materias")
	public String datosAlumnoTituloMaterias(HttpServletRequest request, Model modelo){
						
		String codigoAlumno		= "0";
		String codigoPersonal	= "0";
		
		HttpSession sesion		= ((HttpServletRequest)request).getSession();
		if (sesion!=null){
			codigoAlumno	 	= (String) sesion.getAttribute("codigoAlumno");
			codigoPersonal		= (String) sesion.getAttribute("codigoPersonal");
		}
		
		List<Carga> lisCarga = cargaDao.getListAlumno(codigoAlumno, " ORDER BY 1 DESC");

		String cargaId					= request.getParameter("CargaId")==null?"X":request.getParameter("CargaId");	 
		String planId					= alumPlanDao.getPlanActual(codigoAlumno);
		String carrera					= catCarreraDao.getNombreCarrera(alumPlanDao.getCarreraId(codigoAlumno));

		if(cargaId.equals("X") && lisCarga.size() > 0) {
			cargaId = lisCarga.get(0).getCargaId();
		}
		
		Acceso acceso = new Acceso();
		if(accesoDao.existeReg(codigoPersonal)){
			acceso = accesoDao.mapeaRegId(codigoPersonal);
		}
		
		
		List<AlumnoCurso> listMateriasAlumnoPorCarga = alumnoCursoDao.getListMateriasAlumnoPorCarga(codigoAlumno, cargaId);
		HashMap<String, KrdxAlumnoTitulo> mapaTitulos = krdxAlumnoTituloDao.mapaMateriasTitulo(codigoAlumno);
		HashMap<String,String> mapaMaestros 		= maestrosDao.mapaMaestrosAlumno(codigoAlumno);
		HashMap<String,CatCarrera> mapaCarreta 		= catCarreraDao.getMapAll("");
		HashMap<String,CatModalidad> mapaModalidad 	= catModalidadDao.getMapAll("");
		HashMap<String,CatTipoCal> mapaTipoCal 		= catCatTipoCalDao.getMapAll("");

		modelo.addAttribute("nombreAlumno", alumPersonalDao.getNombreAlumno(codigoAlumno, "NOMBRE"));
		modelo.addAttribute("cargaId", cargaId);
		modelo.addAttribute("planId", planId);
		modelo.addAttribute("carrera", carrera);
		modelo.addAttribute("acceso", acceso);
		modelo.addAttribute("listMateriasAlumnoPorCarga", listMateriasAlumnoPorCarga);
		modelo.addAttribute("lisCarga", lisCarga);
		modelo.addAttribute("mapaMaestros", mapaMaestros);
		modelo.addAttribute("mapaCarreta", mapaCarreta);
		modelo.addAttribute("mapaModalidad", mapaModalidad);
		modelo.addAttribute("mapaTipoCal", mapaTipoCal);
		modelo.addAttribute("mapaTitulos", mapaTitulos);
		
		return "datos_alumno/titulo/materias";
	}

}
