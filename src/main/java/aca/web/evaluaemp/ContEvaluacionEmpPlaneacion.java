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
public class ContEvaluacionEmpPlaneacion {
	
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
	
	@RequestMapping("/evalua_emp/planeacion/accion_actividad")
	public String evaluaEmpPlaneacionAccionActividad(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionActividad:");
    	
		return "evalua_emp/planeacion/accion_actividad";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_meta")
	public String evaluaEmpPlaneacionAccionMeta(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionMeta:");
    	
		return "evalua_emp/planeacion/accion_meta";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_mivi")
	public String evaluaEmpPlaneacionAccionMivi(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionMivi:");
    	
		return "evalua_emp/planeacion/accion_mivi";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_objetivo")
	public String evaluaEmpPlaneacionAccionObjetivo(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionObjetivo:");
    	
		return "evalua_emp/planeacion/accion_objetivo";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_polo")
	public String evaluaEmpPlaneacionAccionPolo(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionPolo:");
    	
		return "evalua_emp/planeacion/accion_polo";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_proyecto")
	public String evaluaEmpPlaneacionAccionProyecto(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionProyecto:");
    	
		return "evalua_emp/planeacion/accion_proyecto";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_recfinan")
	public String evaluaEmpPlaneacionAccionRecfinan(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionRecfinan:");
    	
		return "evalua_emp/planeacion/accion_recfinan";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_respproy")
	public String evaluaEmpPlaneacionAccionRespproy(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionRespproy:");
    	
		return "evalua_emp/planeacion/accion_respproy";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/accion_respact")
	public String evaluaEmpPlaneacionAccionRespact(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionAccionRespact:");
    	
		return "evalua_emp/planeacion/accion_respact";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/centroCostos")
	public String evaluaEmpPlaneacionCentroCostos(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionCentroCostos:");
    	
		return "evalua_emp/planeacion/centroCostos";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/metas")
	public String evaluaEmpPlaneacionMetas(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionMetas:");
    	
		return "evalua_emp/planeacion/metas";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/metas_empleados")
	public String evaluaEmpPlaneacionMetasEmpleados(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionMetasEmpleados:");
    	
		return "evalua_emp/planeacion/metas_empleados";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/proyectos")
	public String evaluaEmpPlaneacionProyectos(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionProyectos:");
    	
		return "evalua_emp/planeacion/proyectos";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/proyectos_depto")
	public String evaluaEmpPlaneacionProyectosDetpo(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionProyectosDepto:");
    	
		return "evalua_emp/planeacion/proyectos_depto";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/proyectos_meta")
	public String evaluaEmpPlaneacionProyectosMeta(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionProyectosMeta:");
    	
		return "evalua_emp/planeacion/proyectos_meta";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/testColapseTable")
	public String evaluaEmpPlaneacionTestColapseTable(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionTestColapseTable:");
    	
		return "evalua_emp/planeacion/testColapseTable";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/eligeCCosto")
	public String evaluaEmpPlaneacionEligeCCosto(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionEligeCCosto:");
    	
		return "evalua_emp/planeacion/eligeCCosto";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/misionvision")
	public String evaluaEmpPlaneacionMisionVision(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionMisionVision:");
    	
		return "evalua_emp/planeacion/misionvision";
	}	
	
	@RequestMapping("/evalua_emp/planeacion/proyectos_empleado")
	public String evaluaEmpPlaneacionProyectosEmpleado(HttpServletRequest request, Model modelo){		
		enviarConEnoc(request,"Error-aca.ContEvaluacionEmpPlaneacion|evaluaEmpPlaneacionProyectosEmpleado:");
    	
		return "evalua_emp/planeacion/proyectos_empleado";
	}	
	
}
