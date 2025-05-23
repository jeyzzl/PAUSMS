	package aca.web.mentores;


import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import aca.carga.spring.CargaGrupoHoraDao;
import aca.catalogo.spring.CatEdificioDao;

import aca.vista.spring.MaestrosDao;

@Controller
public class ContMentoresBitacora {	
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	

	@Autowired
	CatEdificioDao catEdificioDao;
	
	@Autowired
	MaestrosDao maestrosDao;
	
	@Autowired
	CargaGrupoHoraDao cargaGrupoHoraDao;
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
			//System.out.println("Abrir conEnoc"+url);
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/mentores/bitacora/alumnos")
	public String mentoresBitacoraAlumnos(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContMentoresBitacora|mentoresBitacoraAlumnos:");
		return "mentores/bitacora/alumnos";
	}
	
	@RequestMapping("/mentores/bitacora/carrera")
	public String mentoresBitacoraCarrera(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBitacoraCarrera|mentoresBitacoraCarrera:");
		return "mentores/bitacora/carrera";
	}
	
	@RequestMapping("/mentores/bitacora/mentores")
	public String mentoresBitacoraMentores(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContBitacoraMentores|mentoresBitacoraMentores:");
		return "mentores/bitacora/mentores";
	}
	
}