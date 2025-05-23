package aca.web.musica;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatCiudad;
import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatReligion;
import aca.musica.spring.MusiAlumno;

@Controller
public class ContMusicaEstadistica {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	aca.musica.spring.MusiAlumnoDao musiAlumnoDao;
	
	@Autowired
	aca.catalogo.spring.CatReligionDao catReligionDao;
	
	@Autowired
	aca.catalogo.spring.CatPaisDao catPaisDao;
	
	@Autowired
	aca.catalogo.spring.CatEstadoDao catEstadoDao;
	
	@Autowired
	aca.catalogo.spring.CatCiudadDao catCiudadDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/musica/estadistica/alumnos")
	public String musicaEstadisticaAlumnos(HttpServletRequest request, Model modelo){
		
		String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
		List<MusiAlumno> lisAlumnos	= musiAlumnoDao.lisAdmision(estado, "ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
		
		HashMap<String,CatReligion> mapaReligiones = catReligionDao.getMapAll(""); 
		HashMap<String,CatPais> mapaPaises = catPaisDao.getMapAll();
		HashMap<String,CatEstado> mapaEstados = catEstadoDao.getMapAll();
		HashMap<String,CatCiudad> mapaCiudades = catCiudadDao.getMapAll("");
		
		modelo.addAttribute("lisAlumnos",lisAlumnos);
		modelo.addAttribute("mapaReligiones",mapaReligiones);
		modelo.addAttribute("mapaPaises",mapaPaises);
		modelo.addAttribute("mapaEstados",mapaEstados);
		modelo.addAttribute("mapaCiudades",mapaCiudades);		
		
		return "musica/estadistica/alumnos";
	}
	
	@RequestMapping("/musica/estadistica/alumnosProfesor")
	public String musicaEstadisticaAlumnosProfesor(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|AlumnosProfesor:");
		return "musica/estadistica/alumnosProfesor";
	}		
	
	@RequestMapping("/musica/estadistica/cobros")
	public String musicaEstadisticaCobros(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Cobros:");
		return "musica/estadistica/cobros";
	}		
	
	@RequestMapping("/musica/estadistica/contado")
	public String musicaEstadisticaContado(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Contado:");
		return "musica/estadistica/contado";
	}		
	
	@RequestMapping("/musica/estadistica/inscrito")
	public String musicaEstadisticaInscrito(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Inscrito:");
		return "musica/estadistica/inscrito";
	}		
	
	@RequestMapping("/musica/estadistica/opcion")
	public String musicaEstadisticaOpcion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Opcion:");
		return "musica/estadistica/opcion";
	}		
	
	@RequestMapping("/musica/estadistica/pagare")
	public String musicaEstadisticaPagare(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Pagare:");
		return "musica/estadistica/pagare";
	}		
	
	@RequestMapping("/musica/estadistica/pendiente")
	public String musicaEstadisticaPendiente(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMusicaEstadistica|Pendiente:");
		return "musica/estadistica/pendiente";
	}		
	
}