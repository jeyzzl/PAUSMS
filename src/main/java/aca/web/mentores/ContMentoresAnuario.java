package aca.web.mentores;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.carga.spring.CargaAlumno;

@Controller
public class ContMentoresAnuario {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private aca.carga.spring.CargaAlumnoDao cargaAlumnoDao;
	
	@Autowired
	aca.catalogo.spring.CatCarreraDao catCarreraDao;
	
	@Autowired
	aca.alumno.spring.AlumPersonalDao alumPersonalDao;
	
	@Autowired
	aca.alumno.spring.AlumPlanDao alumPlanDao;
	
	@Autowired
	aca.plan.spring.MapaPlanDao mapaPlanDao;
	
	@Autowired
	aca.alumno.spring.AlumAcademicoDao alumAcademicoDao;
	
	@RequestMapping("/mentores/anuario/alumno")
	public String mentoresAnuarioAlumno(HttpServletRequest request, Model modelo){
		//enviarConEnoc(request,"Error-aca.ControllerPortales|portalesAlumnoFaltantes");
		
		HttpSession sesion		= null;		
		String matricula 		= "0";
		String carreraNombre	= "-";
		String nombreAlumno 	= "-";
		String planId 			= "-";
		String carreraId 		= "-";
		String grado 	 		= "0";
	    String semestre	 		= "0";
	    String residencia		= "I";
		
		sesion = ((HttpServletRequest)request).getSession();
        if (sesion!=null){
        	matricula = (String) sesion.getAttribute("codigoUltimo");
        	
        	if (matricula.subSequence(0, 1).equals("0") || matricula.subSequence(0, 1).equals("1") || matricula.subSequence(0, 1).equals("2")){
	        	nombreAlumno 		= alumPersonalDao.getNombreAlumno(matricula, "NOMBRE");
	        	planId 				= alumPlanDao.getPlanActual(matricula);
	        	carreraId			= mapaPlanDao.getCarreraId(planId);
	        	carreraNombre		= catCarreraDao.getNombreCarrera(carreraId);
	        	grado 	 			= alumPlanDao.getGrado(matricula, planId);
	        	semestre	 		= alumPlanDao.getSem(matricula, planId);
	        	residencia 			= alumAcademicoDao.getResidencia(matricula);
        	}
        }
        
        boolean inscrito = alumAcademicoDao.esInscrito(matricula);
       
        List<CargaAlumno> lisPlanes = cargaAlumnoDao.lisCargasActivas(matricula);
		
		modelo.addAttribute("matricula", matricula);
		modelo.addAttribute("inscrito", inscrito);
		modelo.addAttribute("grado", grado);
		modelo.addAttribute("semestre", semestre);
		modelo.addAttribute("carreraNombre", carreraNombre);
		modelo.addAttribute("nombreAlumno", nombreAlumno);	
		modelo.addAttribute("residencia", residencia);
		modelo.addAttribute("lisPlanes", lisPlanes);
		
		return "mentores/anuario/alumno";
	}
}
