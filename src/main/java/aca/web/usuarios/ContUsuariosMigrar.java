package aca.web.usuarios;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.menu.spring.Menu;
import aca.menu.spring.MenuDao;
import aca.menu.spring.Modulo;
import aca.menu.spring.ModuloDao;
import aca.menu.spring.ModuloOpcion;
import aca.menu.spring.ModuloOpcionDao;
import aca.menu.spring.OpcionDao1;
import aca.vista.spring.ModOpcion;
import aca.vista.spring.ModOpcionDao;

@Controller
public class ContUsuariosMigrar {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
	@Autowired
	ServletContext context;
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	public void enviarConArchivo(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conArchivo", archivo.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/usuarios/migrar/migrar")
	public String usuariosMigrarMigrar(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContUsuariosMigrarMigrar|usuariosMigrarMigrar:");
		return "usuarios/migrar/migrar";
	}
	
}