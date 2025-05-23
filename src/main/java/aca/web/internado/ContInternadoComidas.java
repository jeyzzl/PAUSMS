package aca.web.internado;


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
public class ContInternadoComidas {	
	
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
	
	@RequestMapping("/internado/comidas/cargas")
	public String internadoComidasCargas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoComidas|internadoComidasCargas:");
		return "internado/comidas/cargas";
	}
	
	@RequestMapping("/internado/comidas/modalidades")
	public String internadoComidasModalidades(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoComidas|internadoComidasModalidades:");
		return "internado/comidas/modalidades";
	}
	
	@RequestMapping("/internado/comidas/reporte")
	public String internadoComidasReporte(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoComida|internadoComidasReporte:");
		return "internado/comidas/reporte";
	}
	

}