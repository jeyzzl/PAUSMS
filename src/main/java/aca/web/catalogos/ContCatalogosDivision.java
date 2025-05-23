package aca.web.catalogos;


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
public class ContCatalogosDivision {	
	
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
	
	@RequestMapping("/catalogos/division/accion_a")
	public String catalogosDivisionAccion_a(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionAccion_a:");
		return "catalogos/division/accion_a";
	}
	
	@RequestMapping("/catalogos/division/accion_d")
	public String catalogosDivisionAccion_d(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionAccion_d:");
		return "catalogos/division/accion_d";
	}
	
	@RequestMapping("/catalogos/division/accion_u")
	public String catalogosDivisionAccion_u(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionAccion_u:");
		return "catalogos/division/accion_u";
	}
	
	@RequestMapping("/catalogos/division/asociacion")
	public String catalogosDivisionAsociacion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionAsociacion:");
		return "catalogos/division/asociacion";
	}
	
	@RequestMapping("/catalogos/division/division")
	public String catalogosDivisionDivision(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionDivision:");
		return "catalogos/division/division";
	}
	
	@RequestMapping("/catalogos/division/union")
	public String catalogosDivisionUnion(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContCatalogosDivision|catalogosDivisionUnion:");
		return "catalogos/division/union";
	}
	
	
	
}