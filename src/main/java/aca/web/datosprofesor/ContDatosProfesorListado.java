package aca.web.datosprofesor;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.emp.spring.EmpMaestro;
import aca.emp.spring.EmpMaestroDao;

@Controller
public class ContDatosProfesorListado {
	
	@Autowired
	EmpMaestroDao empMaestroDao;	
	
	@RequestMapping("/datos_profesor/listado/maestro")
	public String datosProfesorListadoMaestro(HttpServletRequest request, Model modelo){
		
		List<EmpMaestro> lista = empMaestroDao.getListAll("ORDER BY NOMBRE");
		
		modelo.addAttribute("lista", lista);
		
		return "datos_profesor/listado/maestro";
	}	
	
}