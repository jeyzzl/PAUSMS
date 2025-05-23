package aca.web.clinicos;

import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.rotaciones.spring.RotEspecialidad;
import aca.rotaciones.spring.RotEspecialidadDao;
import aca.rotaciones.spring.RotHospitalEspecialidadDao;

@Controller
public class ContClinicosEspecialidad {
	
	@Autowired
	@Qualifier("dsEnoc")
	private DataSource enoc;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	@Autowired
	RotEspecialidadDao rotEspecialidadDao;
	
	@Autowired
	RotHospitalEspecialidadDao rotHospitalEspecialidadDao;
	
	@RequestMapping("/clinicos/especialidad/especialidad")
	public String clinicosHospitalHospital(HttpServletRequest request, Model modelo){
		
		List<RotEspecialidad> lisEspecialidades = rotEspecialidadDao.getListAll("ORDER BY PLAN_ID,ESPECIALIDAD_NOMBRE");
		HashMap<String, String> mapaTotHospitales = rotHospitalEspecialidadDao.mapTotHospitales();
		
		modelo.addAttribute("lisEspecialidades", lisEspecialidades);
		modelo.addAttribute("mapaTotHospitales", mapaTotHospitales);
		
		return "clinicos/especialidad/especialidad";
	}
	
	@RequestMapping("/clinicos/especialidad/editar")
	public String clinicosHospitalEditar(HttpServletRequest request, Model modelo){
		
		String especialidadId = request.getParameter("EspecialidadId")==null?"0": request.getParameter("EspecialidadId");
		RotEspecialidad especialidad = new RotEspecialidad();
		if(rotEspecialidadDao.existeReg(especialidadId)){
			especialidad = rotEspecialidadDao.mapeaRegId(especialidadId);			
		}		
		modelo.addAttribute("especialidad", especialidad);
		
		return "clinicos/especialidad/editar";
	}
	
	@RequestMapping("/clinicos/especialidad/grabar")
	public String clinicosHospitalGrabar(HttpServletRequest request, Model modelo){
		
		String especialidadId 		= request.getParameter("EspecialidadId")==null?"0": request.getParameter("EspecialidadId");
		String especialidadNombre	= request.getParameter("EspecialidadNombre")==null?"0": request.getParameter("EspecialidadNombre");
		String cursoId		 		= request.getParameter("CursoId")==null?"0": request.getParameter("CursoId");
		String semanas				= request.getParameter("Semanas")==null?"0": request.getParameter("Semanas");
		String planId				= request.getParameter("PlanId")==null?"0": request.getParameter("PlanId");
		String mensaje 				= "-";
		
		RotEspecialidad especialidad = new RotEspecialidad();
		if(rotEspecialidadDao.existeReg(especialidadId)) {
			especialidad.setEspecialidadId(especialidadId);
			especialidad.setEspecialidadNombre(especialidadNombre);
			especialidad.setCursoId(cursoId);
			especialidad.setSemanas(semanas);
			especialidad.setPlanId(planId);
			if (rotEspecialidadDao.updateReg(especialidad)) {
				mensaje = "Modificado...";
			}else {
				mensaje = "Error al modificar...";
			}
		}else	{
			especialidadId=rotEspecialidadDao.maximoReg();
			especialidad.setEspecialidadId(especialidadId);
			especialidad.setEspecialidadNombre(especialidadNombre);
			especialidad.setCursoId(cursoId);
			especialidad.setSemanas(semanas);
			especialidad.setPlanId(planId);
			if (rotEspecialidadDao.insertReg(especialidad)) {
				mensaje = "Grabado...";
			}else {
				mensaje = "Error al grabar...";
			}
		}
		
		return "redirect:/clinicos/especialidad/editar?EspecialidadId="+especialidadId+"&Mensaje="+mensaje;
	}
	
	@RequestMapping("/clinicos/especialidad/borrar")
	public String clinicosEspecialidadBorrar(HttpServletRequest request, Model modelo){
		
		String especialidadId			= request.getParameter("EspecialidadId")==null?"0":request.getParameter("EspecialidadId");
		
		if (rotEspecialidadDao.existeReg(especialidadId)){			
			rotEspecialidadDao.deleteReg(especialidadId);
		}
		
		return "redirect:/clinicos/especialidad/especialidad";
	}
	
}	