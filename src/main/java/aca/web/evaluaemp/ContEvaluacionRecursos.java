package aca.web.evaluaemp;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.edo.spring.Edo;
import aca.edo.spring.EdoAlumnoPreg;
import aca.edo.spring.EdoAlumnoPregDao;
import aca.edo.spring.EdoArea;
import aca.edo.spring.EdoAreaDao;
import aca.edo.spring.EdoDao;
import aca.edo.spring.EdoPeriodo;
import aca.edo.spring.EdoPeriodoDao;

@Controller
public class ContEvaluacionRecursos {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;	

	@Autowired
	ServletContext context;
	
	@Autowired	
	EdoDao edoDao;	
	
	@Autowired	
	EdoAlumnoPregDao edoAlumnoPregDao;	
	
	@Autowired	
	EdoAreaDao edoAreaDao;	
	
	@Autowired	
	EdoPeriodoDao edoPeriodoDao;	
	
	public void enviarConEnoc(HttpServletRequest request, String url){
		// Enviar de la conexión
		try{ 
			request.setAttribute("conEnoc", enoc.getConnection());
		}catch(Exception ex){ 
			System.out.println(url+" "+ex);
		}
	}
	
	@RequestMapping("/evalua_emp/recursos/accionPuestoRH")
	public String evaluaEmpRecursosAccionPuestoRH(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionRecursos|evaluaEmpRecursosAccionPuestoRH:");
    	
		return "evalua_emp/recursos/accionPuestoRH";
	}	
	
	@RequestMapping("/evalua_emp/recursos/accionFunciones")
	public String evaluaEmpRecursosAccionFunciones(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionRecursos|evaluaEmpRecursosAccionFunciones:");
    	
		return "evalua_emp/recursos/accionFunciones";
	}	
	
	@RequestMapping("/evalua_emp/recursos/humanos")
	public String evaluaEmpRecursosHumanos(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionRecursos|evaluaEmpRecursosHumanos:");
    	
		return "evalua_emp/recursos/humanos";
	}	
	
}
