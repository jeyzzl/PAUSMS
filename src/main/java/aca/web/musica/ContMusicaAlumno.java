package aca.web.musica;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aca.catalogo.spring.CatEstado;
import aca.catalogo.spring.CatEstadoDao;

@Controller
public class ContMusicaAlumno {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	CatEstadoDao catEstadoDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/musica/alumno/getEstados")
	public String musicaAlumnoGetEstados(HttpServletRequest request, Model modelo){
		
		enviarConEnoc(request,"Error-aca.ContMusicaAlumno|musicaAlumnoGetEstados");
		/*
		String paisId = request.getParameter("paisId")==null?"0":request.getParameter("paisId");
		String respuesta = "<div class='edos'>";		
		List<CatEstado> lisEstados = catEstadoDao.getLista(paisId," ORDER BY 1,3");	
		for(CatEstado edo: lisEstados){
			respuesta += " <option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>";
		}	
		respuesta += "</div>";		
		*/
		return "musica/alumno/getEstados";
	}
	
	@RequestMapping("/musica/alumno/getCiudades")
	public String musicaAlumnoGetCiudades(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ContMusicaAlumno|musicaAlumnoGetCiudades");
		return "musica/alumno/getCiudades";
	}
	
	@RequestMapping("/musica/alumno/borrar")
	public String musicaAlumnoBorrar(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoBorrar");
		return "musica/alumno/borrar";
	}
	
	@RequestMapping("/musica/alumno/buscar")
	public String musicaAlumnoBuscar(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoBuscar");
		return "musica/alumno/buscar";
	}
	
	@RequestMapping("/musica/alumno/datos")
	public String musicaAlumnoGetDatos(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoGetCiudades");
		return "musica/alumno/datos";
	}
	
	@RequestMapping("/musica/alumno/guardar")
	public String musicaAlumnoGuardar(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoGuardar");
		return "musica/alumno/guardar";
	}
	
	@RequestMapping("/musica/alumno/subir")
	public String musicaAlumnoSubir(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoSubir");
		return "musica/alumno/subir";
	}
	
	@RequestMapping("/musica/alumno/tomarfoto")
	public String musicaAlumnoTomarFoto(HttpServletRequest request, Model modelo){			
		enviarConEnoc(request,"Error-aca.ControllerMusica|musicaAlumnoTomarFoto");
		return "musica/alumno/tomarfoto";
	}
	
}