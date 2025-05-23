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
public class ContInternadoCalificado {	
	
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
	
	@RequestMapping("/internado/calificado/cargas")
	public String internadoCalificadoCargas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoCalificado|internadoCalificadoCargas:");
		return "internado/calificado/cargas";
	}
	
	@RequestMapping("/internado/calificado/comidas")
	public String internadoCalificadoComidas(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoCalificado|internadoCalificadoComidas:");
		return "internado/calificado/comidas";
	}
	
	@RequestMapping("/internado/calificado/modalidades")
	public String internadoCalificadoModalidades(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoCalificado|internadoCalificadoModalidades:");
		return "internado/calificado/modalidades";
	}
	
	@RequestMapping("/internado/calificado/reporte")
	public String internadoCalificadoReporte(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContInternadoCalificado|internadoCalificadoReporte:");
		return "internado/calificado/reporte";
	}
}