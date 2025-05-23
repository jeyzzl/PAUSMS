package aca.web.admision;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.musica.spring.MusiAlumno;
import aca.musica.spring.MusiPadres;
import aca.catalogo.spring.CatReligion;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumUbicacion;


@Controller
public class ContAdmisionCodigo {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}	
	
	@RequestMapping("/admision/codigo/registro")
	public String admisionCodigoRegistro(HttpServletRequest request){
		enviarConEnoc(request,"Error-aca.ContAdmisionCodigo|admisionCodigoRegistro");
		return "admision/codigo/registro";
	}
		
}