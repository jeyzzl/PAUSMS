package aca.web.datosalumno;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.acceso.spring.Acceso;
import aca.acceso.spring.AccesoDao;
import aca.alumno.spring.AlumAsesor;
import aca.alumno.spring.AlumAsesorDao;
import aca.alumno.spring.AlumPersonal;
import aca.alumno.spring.AlumPersonalDao;
import aca.alumno.spring.AlumPlan;
import aca.alumno.spring.AlumPlanDao;
import aca.catalogo.spring.CatCarreraDao;
import aca.plan.spring.MapaPlan;
import aca.plan.spring.MapaPlanDao;
import aca.vista.spring.Maestros;
import aca.vista.spring.MaestrosDao;

@Controller
public class ContDatosAlumnoVacaciones {
	
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
	
	@RequestMapping("/datos_alumno/vacaciones/vacaciones")
	public String datosAlumnoVacacionesVacaciones(HttpServletRequest request, Model modelo){
		enviarConEnoc(request,"Error-aca.ContDatosAlumnoVacaciones|datosAlumnoVacacionesVacaciones:");
        
		return "datos_alumno/vacaciones/vacaciones";
	}
	
}